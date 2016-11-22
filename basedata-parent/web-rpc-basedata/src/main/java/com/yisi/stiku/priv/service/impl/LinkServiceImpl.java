package com.yisi.stiku.priv.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import com.esotericsoftware.minlog.Log;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.yisi.stiku.basedata.rpc.bean.MenuInfo;
import com.yisi.stiku.basedata.rpc.bean.PrivilegeInfo;
import com.yisi.stiku.cache.constant.DefaultJedisKeyNS;
import com.yisi.stiku.cache.redis.ShardJedisTool;
import com.yisi.stiku.common.bean.EntityState;
import com.yisi.stiku.common.bean.GlobalResultCode;
import com.yisi.stiku.common.bean.PageInfo.OrderDirection;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.BeanUtils;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.priv.constant.LinkType;
import com.yisi.stiku.priv.dao.impl.LinkDaoImpl;
import com.yisi.stiku.priv.entity.Link;
import com.yisi.stiku.priv.entity.LinkRoleVO;
import com.yisi.stiku.priv.entity.RoleR2Link;
import com.yisi.stiku.priv.rpc.service.LinkMgrRpcService;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(LinkMgrRpcService.class)
public class LinkServiceImpl extends BaseServiceImpl<Integer, Link> implements
		LinkMgrRpcService {

	private final static Logger LOG = LoggerFactory
			.getLogger(LinkServiceImpl.class);

	@Resource
	private LinkDaoImpl linkDaoImpl;

	@Resource
	private RoleR2LinkServiceImpl roleR2LinkServiceImpl;

	@Resource
	private UserR2RoleServiceImpl userR2RoleServiceImpl;

	private static AntPathMatcher urlMatcher = new AntPathMatcher();

	@Override
	protected BaseDaoImpl<Integer, Link> getBaseDaoImpl() {

		return linkDaoImpl;
	}

	@Override
	public int save(Link link) {

		link.setLastUptime(new Date());
		if (link.getId() == null) {
			if (StringUtils.isBlank(link.getCode())) {
				link.setCode(getCode(link.getDbType()));
			}
			if (link.getOrderRank() == null) {
				link.setOrderRank(100);
			}
			link.setDbState(EntityState.NORMAL);
			this.insertReturnPK(link);

			if (DefaultJedisKeyNS.lk.needRemoveAllCacheAfterModify()) {
				ShardJedisTool.delHKey(DefaultJedisKeyNS.lk);
			}
			return link.getId();
		}

		Link preLink = this.findById(link.getId());
		if (preLink == null) {
			throw new BaseRuntimeException(GlobalResultCode.NOT_EXISTS);
		}

		if (link.getIsShare() != null
				&& (preLink.getIsShare() == null || preLink.getIsShare()
						.byteValue() != link.getIsShare().byteValue())) {
			LinkRoleVO lrv = searchByLinkId(preLink.getProjectName(),
					link.getId());
			List<Integer> linkIds = parseLinkIds(lrv);
			linkDaoImpl.updateSharable(link.getIsShare(), linkIds);
		}

		if (link.getProjectName() != preLink.getProjectName()) {
			LinkRoleVO lrv = searchByLinkId(preLink.getProjectName(),
					link.getId());
			List<Integer> linkIds = parseLinkIds(lrv);
			linkDaoImpl.updateChildProjectName(link.getProjectName(), linkIds);
		}

		boolean result = this.update(link);
		if (!result) {
			throw new BaseRuntimeException(GlobalResultCode.DB_OPER_ERROR);
		}
		if (DefaultJedisKeyNS.lk.needRemoveAllCacheAfterModify()) {
			ShardJedisTool.delHKey(DefaultJedisKeyNS.lk);
		}
		return link.getId();
	}

	@Override
	// @Transactional
	public boolean saveLink2Roles(int linkId, List<Integer> roleIds) {

		Link link = this.findById(linkId);

		LinkRoleVO lrv = searchByLinkId(link.getProjectName(), linkId);
		if (lrv == null) {
			throw new BaseRuntimeException(GlobalResultCode.NOT_EXISTS);
		}
		// RoleR2Link query = new RoleR2Link();
		// query.setLinkId(linkId);
		// List<RoleR2Link> preRrlList =
		// roleR2LinkServiceImpl.findByEntity(query);
		Set<Integer> existRoleIds = lrv.getRoleIds();

		List<Integer> delRoleIds = parseDelRoleIds(existRoleIds, roleIds);

		List<Integer> linkIdList = parseLinkIds(lrv);
		roleR2LinkServiceImpl.deleteByRrl(linkIdList, delRoleIds);

		List<Integer> insertRoleIds = parseInsertRoleIds(existRoleIds, roleIds);
		if (insertRoleIds != null && !insertRoleIds.isEmpty()) {
			List<RoleR2Link> rrlList = new ArrayList<RoleR2Link>();

			Map<Integer, LinkRoleVO> lrMap = buildChildMap(lrv);
			for (int lkId : linkIdList) {
				for (int roleId : insertRoleIds) {
					LinkRoleVO childLR = lrMap.get(lkId);
					if (childLR != null
							&& !childLR.getRoleIds().contains(roleId)) {
						RoleR2Link rrl = new RoleR2Link();
						rrl.setLinkId(lkId);
						rrl.setRoleId(roleId);

						rrlList.add(rrl);
					}
				}
			}

			roleR2LinkServiceImpl.insertBatch(rrlList);
		}
		return true;
	}

	private Map<Integer, LinkRoleVO> buildChildMap(LinkRoleVO lrv) {

		Map<Integer, LinkRoleVO> lrMap = new HashMap<Integer, LinkRoleVO>();
		lrMap.put(lrv.getId(), lrv);
		if (lrv.getChildren() != null && !lrv.getChildren().isEmpty()) {
			for (LinkRoleVO lrVo : lrv.getChildren()) {
				Map<Integer, LinkRoleVO> childLRMap = buildChildMap(lrVo);
				if (childLRMap != null && !childLRMap.isEmpty()) {
					lrMap.putAll(childLRMap);
				}
			}
		}

		return lrMap;
	}

	private List<Integer> parseLinkIds(LinkRoleVO lrv) {

		List<Integer> linkIdList = new ArrayList<Integer>();
		linkIdList.add(lrv.getId());
		if (lrv.getChildren() != null && !lrv.getChildren().isEmpty()) {
			for (LinkRoleVO child : lrv.getChildren()) {
				List<Integer> childIdList = parseLinkIds(child);
				if (!childIdList.isEmpty()) {
					linkIdList.addAll(childIdList);
				}
			}
		}

		return linkIdList;
	}

	/**
	 * 
	 * @param preRrlList
	 * @param roleIds
	 * @return 把preRrlList中有而roleIds中没有的角色ID跳出来加入待删除列表(key为delList);
	 */
	private List<Integer> parseDelRoleIds(Set<Integer> existRoleIds,
			List<Integer> roleIds) {

		List<Integer> delList = new ArrayList<Integer>();
		for (Integer existRoleId : existRoleIds) {
			if (roleIds == null || roleIds.isEmpty()
					|| !roleIds.contains(existRoleId)) {
				delList.add(existRoleId);
			}
		}

		return delList;
	}

	/**
	 * 
	 * @param preRrlList
	 * @param roleIds
	 * @return 把preRrlList没有而roleIds中有的角色ID挑出来加入待插入列表(key为insertList)
	 */
	private List<Integer> parseInsertRoleIds(Set<Integer> existRoleIds,
			List<Integer> roleIds) {

		if (existRoleIds == null || existRoleIds.isEmpty()) {
			return roleIds;
		}

		// List<Integer> preRoleIds = parseRoleIds(preRrlList);
		List<Integer> insertList = new ArrayList<Integer>();
		for (Integer roleId : roleIds) {
			if (!existRoleIds.contains(roleId)) {
				insertList.add(roleId);
			}
		}

		return insertList;
	}

	// private List<Integer> parseRoleIds(List<RoleR2Link> preRrlList){
	// List<Integer> roleIds = new ArrayList<Integer>();
	// for(RoleR2Link rrl : preRrlList){
	// roleIds.add(rrl.getRoleId());
	// }
	//
	// return roleIds;
	// }

	@Override
	public boolean invalid(int id, String projectName) {

		LinkRoleVO lrv = searchByLinkId(projectName, id);
		List<Integer> linkIds = parseLinkIds(lrv);
		boolean result = false;
		if (linkIds != null && !linkIds.isEmpty()) {
			linkDaoImpl.invalid(linkIds);
			if (DefaultJedisKeyNS.lk.needRemoveAllCacheAfterModify()) {
				ShardJedisTool.delHKey(DefaultJedisKeyNS.lk);
			}
		}

		return result;
	}

	@Override
	public List<Link> findRootLinks(String projectName) {

		Link link = new Link();
		link.setProjectName(projectName);
		link.setDbType(LinkType.MENU);

		return linkDaoImpl.findRootLinks(link);
	}

	@Override
	public List<Link> findLinkByParentId(int parentId) {

		Link link = new Link();
		link.setParentId(parentId);
		link.setDbState(EntityState.NORMAL);

		return this.findByEntity(link, "order_rank", OrderDirection.desc);
	}

	@Override
	public List<MenuInfo> loadMenu(String projectName, long userId) {

		List<LinkRoleVO> linkRoleList = initLinkForProject(projectName); // 初始化链接(菜单)树

		List<MenuInfo> rootMenuList = new ArrayList<MenuInfo>();

		List<Integer> roleIds = userR2RoleServiceImpl.loadByUserId(userId);
		if (linkRoleList != null && !linkRoleList.isEmpty()) {
			Collections.sort(linkRoleList, new Comparator<LinkRoleVO>() {

				@Override
				public int compare(LinkRoleVO link1, LinkRoleVO link2) {

					if (link1 != null && link2 != null) {
						if (link1.getOrderRank() > link2.getOrderRank()) {
							return -1;
						} else if (link1.getOrderRank() > link2.getOrderRank()) {
							return 1;
						}
					}

					return 0;
				}
			});

			List<String> projNameList = getRelatedProjectNames(projectName);
			for (LinkRoleVO linkRole : linkRoleList) {
				boolean canshow = canShow(projNameList, linkRole, roleIds, true);
				if (canshow) {
					MenuInfo menuInfo = convertMenu(linkRole);
					rootMenuList.add(menuInfo);
				}
			}
		}

		return rootMenuList;
	}

	private MenuInfo convertMenu(LinkRoleVO linkRole) {

		MenuInfo menuInfo = new MenuInfo();
		menuInfo.setCode(linkRole.getCode());
		menuInfo.setId(linkRole.getId() + "");
		menuInfo.setName(linkRole.getName());
		menuInfo.setUrl(linkRole.getLink());
		menuInfo.setProjectDomain(ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, linkRole.getProjectName() + ".web.domain",
				ConfigOnZk.getValue("web-passport/application.properties", linkRole.getProjectName() + ".web.domain", "")));

		List<MenuInfo> childMenuList = new ArrayList<MenuInfo>();
		List<LinkRoleVO> linkRoleList = linkRole.getChildren();
		if (linkRoleList != null && !linkRoleList.isEmpty()) {
			for (LinkRoleVO currLR : linkRoleList) {
				MenuInfo currMenu = convertMenu(currLR);
				childMenuList.add(currMenu);
			}
		}

		menuInfo.setChildren(childMenuList);

		return menuInfo;
	}

	private synchronized List<LinkRoleVO> initLinkForProject(String projectName) {

		// 先从缓存中读取菜单权限树
		List<String> valList = getRelatedProjectNames(projectName);
		String projKey = buildProjKey(valList, projectName);

		String linkTreeStr = ShardJedisTool.hget(DefaultJedisKeyNS.lk, "lfp"
				+ projKey);
		if (StringUtils.isNotBlank(linkTreeStr)) {
			List<LinkRoleVO> cacheList = new Gson().fromJson(linkTreeStr,
					new TypeToken<List<LinkRoleVO>>() {
					}.getType());
			return cacheList;
		}

		List<LinkRoleVO> rootLinkRoleList = initLinkForProj(projectName);
		for (String val : valList) {
			if (!projectName.equals(val)) {
				List<LinkRoleVO> otherProjList = initLinkForProj(val);

				rootLinkRoleList.addAll(otherProjList);
			}
		}

		if (!rootLinkRoleList.isEmpty()) {
			ShardJedisTool.hset(DefaultJedisKeyNS.lk, "lfp" + projKey,
					new Gson().toJson(rootLinkRoleList,
							new TypeToken<List<LinkRoleVO>>() {
							}.getType()));
		}

		return rootLinkRoleList;
	}

	private List<String> getRelatedProjectNames(String projectName) {

		String[] valArr = ConfigOnZk.getValueArr(ZkConstant.APP_ZK_PATH, projectName + ".related.projectName");

		return Arrays.asList(valArr == null || valArr.length <= 0 ? new String[] { projectName } : valArr);
	}

	private String buildProjKey(List<String> valList, String projectName) {

		Collections.sort(valList);

		String ret = "";
		for (String val : valList) {
			ret += val + "_";
		}

		return ret.substring(0, ret.length() - 1);
	}

	private List<LinkRoleVO> initLinkForProj(String projectName) {

		List<LinkRoleVO> rootLinkRoleList = new ArrayList<LinkRoleVO>(); // 从顶级菜单开始，构建树形结构

		List<Link> links = linkDaoImpl.loadForProject(projectName);

		if (links != null && !links.isEmpty()) {
			List<LinkRoleVO> linkRoleList = convert(links);
			Map<Integer, LinkRoleVO> linkMap = BeanUtils
					.buildPK2BeanMap(linkRoleList);
			LinkRoleVO virtualMenu = new LinkRoleVO(true);
			rootLinkRoleList.add(virtualMenu); // 先将虚拟菜单加入根菜单
			for (LinkRoleVO linkRole : linkRoleList) {

				if (linkRole.getParentId() == null) {
					if (LinkType.MENU.equals(linkRole.getType())) {
						rootLinkRoleList.add(linkRole);// 如果是根根菜单，先加入根菜单列表
					} else {
						// 如果没有父节点，且链接类型不是菜单，就加入虚拟链接列表中
						virtualMenu.addChild(linkRole);
					}
				} else {
					LinkRoleVO parentLinkRole = linkMap.get(linkRole
							.getParentId());
					if (parentLinkRole == null) {
						if (!linkRole.isShare()
								&& projectName
										.equals(linkRole.getProjectName())) {
							Log.warn("not found parent Link with parentId '"
									+ linkRole.getParentId()
									+ "' for current link '" + linkRole.getId()
									+ "'");
						}
						virtualMenu.addChild(linkRole);
					} else {
						parentLinkRole.addChild(linkRole);
					}
				}
			}
		}

		List<RoleR2Link> rrlList = roleR2LinkServiceImpl.findAll();
		Map<Integer/* linkId */, Set<Integer /* roleId */>> link2RoleIds = parseLink2Roles(rrlList);
		configRole(rootLinkRoleList, link2RoleIds);

		return rootLinkRoleList;
	}

	/**
	 * 
	 * @param projectName
	 *            工程名
	 * @param linkId
	 *            菜单ID
	 * @return 从菜单树中查找指定的菜单，如果该菜单下有子菜单，则其children属性将不会为空
	 */
	private LinkRoleVO searchByLinkId(String projectName, int linkId) {

		List<LinkRoleVO> rootLinkRoleList = initLinkForProject(projectName);

		return searchByLinkId(rootLinkRoleList, linkId);
	}

	private LinkRoleVO searchByLinkId(List<LinkRoleVO> rootLinkRoleList,
			int linkId) {

		if (rootLinkRoleList != null && !rootLinkRoleList.isEmpty()) {
			for (LinkRoleVO lrv : rootLinkRoleList) {
				if (lrv.getId() == linkId) {
					return lrv;
				}
				LinkRoleVO target = searchByLinkId(lrv.getChildren(), linkId);
				if (target != null) {
					return target;
				}
			}
		}

		return null;
	}

	private void configRole(List<LinkRoleVO> rootLinkRoleList,
			Map<Integer/* linkId */, Set<Integer /* roleId */>> link2RoleIds) {

		if (rootLinkRoleList == null || rootLinkRoleList.isEmpty()
				|| link2RoleIds == null || link2RoleIds.isEmpty()) {
			return;
		}
		for (LinkRoleVO linkRole : rootLinkRoleList) {
			Set<Integer /* roleId */> roleIds = link2RoleIds.get(linkRole
					.getId());
			if (roleIds != null && !roleIds.isEmpty()) {
				linkRole.addRoleIds(roleIds);
			}

			// Set<Integer> parentRoleIds = link2RoleIds.get(linkRole
			// .getParentId());
			// if (parentRoleIds != null && !parentRoleIds.isEmpty()) {
			// linkRole.addRoleIds(parentRoleIds);
			// }

			configRole(linkRole.getChildren(), link2RoleIds); // 递归配置子菜单的角色
		}

	}

	private Map<Integer/* linkId */, Set<Integer /* roleId */>> parseLink2Roles(
			List<RoleR2Link> rrlList) {

		Map<Integer/* linkId */, Set<Integer /* roleId */>> link2RoleIds = new HashMap<Integer, Set<Integer>>();
		if (rrlList != null && !rrlList.isEmpty()) {
			for (RoleR2Link rrl : rrlList) {
				Set<Integer> roleIds = link2RoleIds.get(rrl.getLinkId());
				if (roleIds == null) {
					roleIds = new HashSet<Integer>();
					link2RoleIds.put(rrl.getLinkId(), roleIds);
				}
				roleIds.add(rrl.getRoleId());
			}
		}

		return link2RoleIds;
	}

	private List<LinkRoleVO> convert(List<Link> links) {

		List<LinkRoleVO> linkRoleList = new ArrayList<LinkRoleVO>();
		for (Link link : links) {
			LinkRoleVO linkRole = convert(link);
			linkRoleList.add(linkRole);
		}

		return linkRoleList;
	}

	private LinkRoleVO convert(Link link) {

		LinkRoleVO linkRole = new LinkRoleVO();
		linkRole.setCode(link.getCode());
		linkRole.setId(link.getId());
		linkRole.setLink(link.getLink());
		linkRole.setProjectName(link.getProjectName());
		linkRole.setName(link.getName());
		linkRole.setParentId(link.getParentId());
		linkRole.setOrderRank(link.getOrderRank());
		linkRole.setShare(link.getIsShare() != null
				&& link.getIsShare() != (byte) 0);
		linkRole.setNeedDataCheck(link.getNeedDataCheck() != null
				&& link.getNeedDataCheck() != (byte) 0);
		linkRole.setType(link.getDbType());

		return linkRole;
	}

	private boolean canShow(List<String> projNameList, LinkRoleVO linkRole,
			List<Integer> roleIds, boolean isRoot) {

		if (linkRole.isVirtualMenu()
				|| !projNameList.contains(linkRole.getProjectName())
				|| !LinkType.MENU.equals(linkRole.getType())) {
			return false;
		}
		boolean rootCanshow = isRoot ? checkAuth(linkRole, roleIds) : false;
		// if (rootCanshow) { // 如果有当前菜单的权限，则会有该菜单和其子菜单的权限
		// filterChildMenu(linkRole);
		// return true;
		// }

		List<LinkRoleVO> children = linkRole.getChildren();
		if (children == null || children.isEmpty()) {
			return rootCanshow;
		}

		List<LinkRoleVO> authChildList = new ArrayList<LinkRoleVO>();
		for (LinkRoleVO child : children) {
			if (!LinkType.MENU.equals(child.getType())) {
				// 忽略掉功能链接，因为不需要显示在菜单上
				continue;
			}
			boolean canshow = checkAuth(child, roleIds);
			// if (canshow) {
			// rootCanshow = true;
			// filterChildMenu(child);
			// authChildList.add(child);
			// continue;
			// }

			// 检查可以显示的子菜单
			boolean hasChildShow = canShow(projNameList, child, roleIds, false);

			if (hasChildShow || canshow) {
				authChildList.add(child);
				rootCanshow = true;
			}
		}

		linkRole.clearChildren();
		linkRole.addChilren(authChildList);

		return rootCanshow;
	}

	/**
	 * 
	 * @param linkRole
	 * @return 剔除掉功能菜单
	 */
	// private void filterChildMenu(LinkRoleVO linkRole) {
	// List<LinkRoleVO> children = linkRole.getChildren();
	// if (children == null || children.isEmpty()) {
	// return;
	// }
	//
	// List<LinkRoleVO> needRemovedList = new ArrayList<LinkRoleVO>();
	// for (LinkRoleVO child : children) {
	// if (!LinkType.MENU.equals(child.getType())) {
	// needRemovedList.add(child);
	// continue;
	// }
	//
	// filterChildMenu(child);
	// }
	//
	// for (LinkRoleVO removed : needRemovedList) {
	// linkRole.removeChild(removed);
	// }
	// }

	private boolean checkAuth(LinkRoleVO child, List<Integer> roleIds) {

		boolean matched = false;
		if (CollectionUtils.isEmpty(roleIds)) {
			LOG.debug("No authority granted for current User");
		} else {
			Set<Integer> needRoleIds = child.getRoleIds();
			for (int roleId : roleIds) {
				if (needRoleIds.contains(roleId)) {
					LOG.debug(
							"Found matched and granted authority for URL: {}, roleId: {}",
							StringUtils.isBlank(child.getLink()) ? child
									.getName() : child.getLink(), roleId);
					matched = true;
					break;
				}
			}
			if (!matched) {
				LOG.debug("Required but NO granted authority for URL: {}",
						StringUtils.isBlank(child.getLink()) ? child.getName()
								: child.getLink());
			}
		}

		return matched;
	}

	private String getCode(LinkType linkType) {

		return ""
				+ (linkType == null ? LinkType.MENU.name().charAt(0) : linkType
						.name().charAt(0))
				+ (org.apache.commons.lang.math.RandomUtils.nextInt(1000000) + 100);
	}

	@Override
	public List<Integer> findRoleIdByLinkId(int linkId) {

		RoleR2Link rrl = new RoleR2Link();
		rrl.setLinkId(linkId);
		List<RoleR2Link> rrlList = roleR2LinkServiceImpl.findByEntity(rrl);
		List<Integer> roleIds = new ArrayList<Integer>();
		if (rrlList != null && !rrlList.isEmpty()) {
			for (RoleR2Link currRrl : rrlList) {
				roleIds.add(currRrl.getRoleId());
			}
		}
		return roleIds;
	}

	@Override
	public Map<String, Collection<ConfigAttribute>> loadProjectResource(
			String projectName) {

		List<String> valList = getRelatedProjectNames(projectName);
		String projKey = buildProjKey(valList, projectName);

		String attrStr = ShardJedisTool.hget(DefaultJedisKeyNS.lk, "lpr"
				+ projKey);
		if (StringUtils.isNotBlank(attrStr)) {
			Map<String, Collection<SecurityConfig>> currResMap = new Gson()
					.fromJson(
							attrStr,
							new TypeToken<Map<String, Collection<SecurityConfig>>>() {
							}.getType());

			LOG.debug("load Spring Security Resource from cache");
			return convert(currResMap);
		}

		Map<String, Collection<SecurityConfig>> currResMap = new LinkedHashMap<String, Collection<SecurityConfig>>();

		List<LinkRoleVO> linkRoleList = initLinkForProject(projectName);

		List<LinkRoleVO> haslinkList = grabLinkRoleHasLink(linkRoleList);
		// 根据链接的字母顺序倒序
		Collections.sort(haslinkList, new Comparator<LinkRoleVO>() {

			@Override
			public int compare(LinkRoleVO o1, LinkRoleVO o2) {

				if (StringUtils.isBlank(o1.getLink())
						|| StringUtils.isBlank(o2.getLink())) {
					return Integer.valueOf(o2.getOrderRank()).compareTo(
							o1.getOrderRank());
				}
				return o2.getLink().compareTo(o1.getLink());
			}
		});

		for (LinkRoleVO linkRole : haslinkList) {
			addURL2Role(currResMap, linkRole.getLink(), linkRole.getRoleIds());
		}

		if (currResMap != null && !currResMap.isEmpty()) {
			ShardJedisTool
					.hset(DefaultJedisKeyNS.lk,
							"lpr" + projKey,
							new Gson()
									.toJson(currResMap,
											new TypeToken<Map<String, Collection<SecurityConfig>>>() {
											}.getType()));
		}

		return convert(currResMap);
	}

	private Map<String, Collection<ConfigAttribute>> convert(
			Map<String, Collection<SecurityConfig>> currResMap) {

		Map<String, Collection<ConfigAttribute>> resMap = new LinkedHashMap<String, Collection<ConfigAttribute>>();
		Set<String> keyset = currResMap.keySet();
		for (String key : keyset) {
			Collection<ConfigAttribute> attrList = new ArrayList<ConfigAttribute>();
			attrList.addAll(currResMap.get(key));

			resMap.put(key, attrList);
		}

		return resMap;
	}

	@Override
	public Map<String, PrivilegeInfo> loadUrl2PrivilegeMap() {

		// TODO Auto-generated method stub
		return new HashMap<String, PrivilegeInfo>();
	}

	/**
	 * 
	 * @param linkRoleList
	 * @return 从树形结构linkRoleList中提取带有链接的菜单或功能
	 */
	private List<LinkRoleVO> grabLinkRoleHasLink(List<LinkRoleVO> linkRoleList) {

		List<LinkRoleVO> hasLinkList = new ArrayList<LinkRoleVO>();

		if (linkRoleList != null && !linkRoleList.isEmpty()) {
			for (LinkRoleVO linkRole : linkRoleList) {
				if (StringUtils.isNotBlank(linkRole.getLink())) {
					try {
						hasLinkList.add((LinkRoleVO) linkRole.clone());
					} catch (CloneNotSupportedException e) {
						LOG.error(e.getMessage(), e);
					}
				}
				List<LinkRoleVO> children = linkRole.getChildren();
				if (children != null && !children.isEmpty()) {
					List<LinkRoleVO> childHasLinkList = grabLinkRoleHasLink(children);
					hasLinkList.addAll(childHasLinkList);
				}
			}
		}

		return hasLinkList;
	}

	private void addURL2Role(Map<String, Collection<SecurityConfig>> map,
			String url, Set<Integer> roleIds) {

		for (String splitUrl : url.split("\n")) {
			if (StringUtils.isNotBlank(splitUrl)) {
				splitUrl = splitUrl.replace("?", "*");
				if (!"/".equals(splitUrl) && !splitUrl.endsWith("/**")) {
					splitUrl = splitUrl + "*/**";
				}
				Collection<SecurityConfig> configAttributes = map.get(splitUrl);
				if (configAttributes == null) {
					configAttributes = new ArrayList<SecurityConfig>();
					map.put(splitUrl, configAttributes);

				}
				if (roleIds != null && !roleIds.isEmpty()) {
					for (Integer roleId : roleIds) {
						configAttributes.add(new SecurityConfig(roleId + ""));
					}
				}
			}
		}
	}

	@Override
	public Collection<ConfigAttribute> loadResource(String projectName,
			String uri) {

		return loadResource(projectName, uri, false);
	}

	@Override
	public Collection<ConfigAttribute> loadResource(String projectName,
			String uri, boolean exactEqual) {

		if (StringUtils.isNotBlank(uri)) {
			Map<String, Collection<ConfigAttribute>> currResMap = loadProjectResource(projectName);
			Iterator<String> ite = currResMap.keySet().iterator();
			while (ite.hasNext()) {
				String resURL = ite.next();
				if (exactEqual) {
					resURL = resURL.replaceAll("\\**/\\*+", "");
					if (uri.equals(resURL) || uri.equals(resURL + "/")) {
						return currResMap.get(resURL);
					}
				} else if (urlMatcher.match(resURL, uri)) {
					return currResMap.get(resURL);
				}
			}
		}
		return Collections.emptyList();
	}

	@Override
	public boolean checkUserPriv(long userId, String projectName, String uri) {

		Collection<ConfigAttribute> needRoleList = loadResource(projectName,
				uri);
		if (needRoleList == null || needRoleList.isEmpty()) {
			return false;
		}

		List<Integer> roleIds = userR2RoleServiceImpl.loadByUserId(userId);
		if (roleIds == null || roleIds.isEmpty()) {
			return false;
		}
		for (ConfigAttribute config : needRoleList) {
			String needRole = config.getAttribute();
			if (RegexUtil.isInt(needRole)
					&& roleIds.contains(Integer.valueOf(needRole))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Integer> findLinkByRoleId(int roleId) {

		this.findLinkByParentId(1);
		RoleR2Link rrl = new RoleR2Link();
		rrl.setRoleId(roleId);
		List<RoleR2Link> rrlList = roleR2LinkServiceImpl.findByEntity(rrl);
		List<Integer> links = new ArrayList<Integer>();
		if (rrlList != null && !rrlList.isEmpty()) {
			for (RoleR2Link currRrl : rrlList) {
				links.add(currRrl.getLinkId());
			}
		}
		return links;
	}

	@Override
	public List<MenuInfo> findAllLinks(String projectName) {

		List<LinkRoleVO> linkRoleList = initLinkForProject(projectName); // 初始化链接(菜单)树
		List<MenuInfo> rootMenuList = new ArrayList<MenuInfo>();
		// List<Integer> roleIds = userR2RoleServiceImpl.loadByUserId(userId);
		if (linkRoleList != null && !linkRoleList.isEmpty()) {
			for (LinkRoleVO linkRole : linkRoleList) {
				if (!projectName.equals(linkRole.getProjectName())) {
					continue;
				}

				MenuInfo menuInfo = convertMenu(linkRole);
				rootMenuList.add(menuInfo);
			}
		}

		return rootMenuList;

	}

	@Override
	public int findCountByLinkURL(String urlLink) {

		Link link = new Link();
		link.setLink(urlLink);
		link.setDbState(EntityState.NORMAL);
		List<Link> result = linkDaoImpl.findByEntity(link);
		return result.size();
	}

}
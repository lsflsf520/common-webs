package com.yisi.stiku.intg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.intg.entity.Integration;

/**
 * @author shangfeng
 *
 */
@Service
public class CommonServiceHelper implements InitializingBean {

	@Resource
	private UserRpcService userRpcService;

	@Resource
	private IntegrationServiceImpl integrationServiceImpl;

	public Map<Integer, String> queryIntgUsers() {

		TblAuthUser query = new TblAuthUser();
		query.setType(ConfigOnZk.getInt(ZkConstant.APP_ZK_PATH, "intg.user.type", 1043));

		Page<TblAuthUser> page = userRpcService.searchUser(query, null, 1, Integer.MAX_VALUE);
		Map<Integer, String> userMap = new HashMap<Integer, String>();
		if (page.getContent() != null && page.getContent().size() > 0) {
			for (TblAuthUser user : page.getContent()) {
				userMap.put(user.getId().intValue(), user.getRealName());
			}
		}

		return userMap;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		Integration query = new Integration();
		query.setInRelease((byte) 1);

		List<Integration> intgList = integrationServiceImpl.findByEntity(query, " order by last_release_time desc ");
		if (intgList != null && !intgList.isEmpty()) {
			for (Integration intg : intgList) {
				integrationServiceImpl.releaseEnv(intg);
			}
		}
	}

}

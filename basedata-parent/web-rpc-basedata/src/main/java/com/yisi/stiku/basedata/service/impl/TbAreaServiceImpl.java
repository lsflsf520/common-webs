package com.yisi.stiku.basedata.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.dao.impl.TbAreaDaoImpl;
import com.yisi.stiku.basedata.entity.TbArea;
import com.yisi.stiku.basedata.rpc.service.AreaRpcService;
import com.yisi.stiku.cache.constant.DefaultCacheNS;
import com.yisi.stiku.cache.eh.EhCacheTool;
import com.yisi.stiku.common.utils.BeanUtils;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(AreaRpcService.class)
public class TbAreaServiceImpl extends BaseServiceImpl<Long, TbArea> implements InitializingBean, AreaRpcService{
	
	private final static Logger LOG = LoggerFactory.getLogger(TbAreaServiceImpl.class);
	
    @Resource
    private TbAreaDaoImpl tbAreaDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbArea> getBaseDaoImpl() {
        return tbAreaDaoImpl;
    }
    
    private synchronized void initAreaTree(){
    	List<TbArea> areaList = this.findAll();
    	Map<Long, TbArea> areaMap = BeanUtils.buildPK2BeanMap(areaList);
    	
    	List<TbArea> rootArea = new ArrayList<TbArea>(50);
    	for(TbArea area : areaList){
    		if(area.getpId() == null || area.getpId() == 0l){
    			rootArea.add(area);
    		}else{
    			TbArea parentArea = areaMap.get(area.getpId());
    			if(parentArea == null){
    				LOG.warn("not found parent area with parentId '" + area.getpId() + "' for curr area '" + area.getId() + "' and will be aborted");
    			}else{
    				parentArea.addChild(area);
    			}
    		}
    		
    		EhCacheTool.put(DefaultCacheNS.BASE_AREA, buildKey(area.getId()), area);
    	}
    	
    	EhCacheTool.put(DefaultCacheNS.BASE_AREA, rootArea); //保存树
    }
    
//    @Override
//    protected CacheNameSpace getCacheNameSpace() {
//    	return DefaultCacheNS.BASE_AREA;
//    }

	@Override
	public void afterPropertiesSet() throws Exception {
		initAreaTree(); //初始化树结构
	}
	
	@Override
	public TbArea findById(Long pk) {
		TbArea area = findByIdRecursive(pk);
		
		if(area != null){
			area.clearChildren();
		}
		
		return area;
	}
	
	@Override
	public TbArea findByIdRecursive(Long id) {
		TbArea area = EhCacheTool.getValue(DefaultCacheNS.BASE_AREA, buildKey(id));
		if(area == null){
			initAreaTree();
			area = EhCacheTool.getValue(DefaultCacheNS.BASE_AREA, buildKey(id));
		}
		return area;
	}

	@Override
	public List<TbArea> findByPId(Long id) {
		
		List<TbArea> childs = findByPIdRecursive(id);
		if(childs != null && !childs.isEmpty()){
			for(TbArea area : childs){
				area.clearChildren();
			}
		}
		
		return childs == null ? new ArrayList<TbArea>() : childs;
	}
	
	public List<TbArea> findByPIdRecursive(Long id){
		TbArea parentArea = EhCacheTool.getValue(DefaultCacheNS.BASE_AREA, buildKey(id));
		if(parentArea == null){
			initAreaTree();
			parentArea = EhCacheTool.getValue(DefaultCacheNS.BASE_AREA, buildKey(id));
		}
		
		return parentArea == null ? new ArrayList<TbArea>() : parentArea.getChildren();
	}
	
	@Override
	public boolean hasChild(Long id) {
		List<TbArea> childs = findByPIdRecursive(id);
		
		return childs != null && !childs.isEmpty();
	}
	
	@Override
	public List<TbArea> findAllProvinces() {
		List<TbArea> rootAreaList = findAllProvincesRecursive();
		
		if(rootAreaList != null && !rootAreaList.isEmpty()){
			for(TbArea province : rootAreaList){
				province.clearChildren();
			}
		}
		
		return rootAreaList;
	}
	
	private List<TbArea> findAllProvincesRecursive(){
		List<TbArea> rootAreaList = EhCacheTool.getValue(DefaultCacheNS.BASE_AREA);
		if(rootAreaList == null){
			initAreaTree();
			rootAreaList = EhCacheTool.getValue(DefaultCacheNS.BASE_AREA);
		}
		return rootAreaList;
	}
	
	@Override
	public List<TbArea> findListByIds(Long... areaIds) {
		List<TbArea> areaList = new ArrayList<TbArea>();
		if(areaIds != null && areaIds.length > 0){
			for(Long areaId : areaIds){
				TbArea area = findById(areaId);
				
				areaList.add(area);
			}
		}
		
		return areaList;
	}
	
	@Override
	public List<TbArea> findSchoolProvinces() {
		return tbAreaDaoImpl.getSchoolProvinces();
	}
}

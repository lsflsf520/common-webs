package com.yisi.stiku.basedata.rpc.service;

import java.util.List;

import com.yisi.stiku.basedata.entity.TbArea;

/**
 * 
 * @author shangfeng
 *
 */
public interface AreaRpcService {

	/**
	 * 
	 * @param id 
	 * @return 
	 */
	public TbArea findById(Long id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TbArea findByIdRecursive(Long id);
	
	/**
	 * 
	 * @param id 
	 * @return 
	 */
	public List<TbArea> findByPId(Long id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasChild(Long id);
	
	/**
	 * 
	 * @param id 
	 * @return 
	 */
	public List<TbArea> findByPIdRecursive(Long id);
	
	/**
	 * 
	 * @return
	 */
	public List<TbArea> findAllProvinces();
	
	public List<TbArea> findListByIds(Long... areaIds);
	
	/**
	 * 查询所有与公司合作的省份
	 * @return
	 */
	public List<TbArea> findSchoolProvinces();
}

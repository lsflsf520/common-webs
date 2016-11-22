package com.yisi.stiku.basedata.rpc.service;

import java.util.List;

import com.yisi.stiku.basedata.entity.TbConstant;

public interface ConstantRpcService {

	public TbConstant findById(Long id);

	public TbConstant findOneByTypeAndName(String constType, String name);

	public List<TbConstant> findByTypeAndName(String constType, String name);

	public List<TbConstant> findByTypeAndValue(String constType, String value);

	public TbConstant findOneByTypeAndValue(String constType, String value);

	public List<TbConstant> findByType(String constType);

	/**
	 * 如果tbContant的id为空，则执行插入操作；否则为更新操作；如果tbContant为空，则抛出BaseRuntimeException异常
	 * 
	 * @param tbContant
	 * @return 返回tbContant的id
	 */
	public long saveConstant(TbConstant tbContant);

}

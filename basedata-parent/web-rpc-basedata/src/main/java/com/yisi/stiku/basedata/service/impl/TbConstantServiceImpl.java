package com.yisi.stiku.basedata.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.dao.impl.TbConstantDaoImpl;
import com.yisi.stiku.basedata.entity.TbConstant;
import com.yisi.stiku.basedata.rpc.service.ConstantRpcService;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(ConstantRpcService.class)
public class TbConstantServiceImpl extends BaseServiceImpl<Long, TbConstant> implements ConstantRpcService {

	@Resource
	private TbConstantDaoImpl tbConstantDaoImpl;

	@Override
	protected BaseDaoImpl<Long, TbConstant> getBaseDaoImpl() {

		return tbConstantDaoImpl;
	}

	@Override
	public long saveConstant(TbConstant tbContant) {

		if (tbContant == null) {
			throw new BaseRuntimeException("NULL_OBJ", "对象不能为空");
		}

		Long id = tbContant.getId();
		if (tbContant.getId() != null) {
			tbConstantDaoImpl.updateByPK(tbContant);
		} else {
			id = tbConstantDaoImpl.insertReturnPK(tbContant);
		}

		return id;
	}

	@Override
	public TbConstant findOneByTypeAndName(String constType, String name) {

		TbConstant query = new TbConstant();
		query.setConstType(constType);
		query.setName(name);

		return this.findOneByEntity(query);
	}

	@Override
	public List<TbConstant> findByType(String constType) {

		TbConstant query = new TbConstant();
		query.setConstType(constType);

		return this.findByEntity(query);
	}

	@Override
	public List<TbConstant> findByTypeAndName(String constType, String name) {

		TbConstant query = new TbConstant();
		query.setConstType(constType);
		query.setName(name);

		return this.findByEntity(query);
	}

	@Override
	public List<TbConstant> findByTypeAndValue(String constType, String value) {

		TbConstant query = new TbConstant();
		query.setConstType(constType);
		query.setValue(value);

		return this.findByEntity(query);
	}

	@Override
	public TbConstant findOneByTypeAndValue(String constType, String value) {

		TbConstant query = new TbConstant();
		query.setConstType(constType);
		query.setValue(value);

		return this.findOneByEntity(query);
	}

}
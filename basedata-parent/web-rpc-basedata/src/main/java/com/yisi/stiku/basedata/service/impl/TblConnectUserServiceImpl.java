package com.yisi.stiku.basedata.service.impl;

import java.util.List;

import com.yisi.stiku.basedata.dao.impl.TblConnectUserDaoImpl;
import com.yisi.stiku.basedata.entity.TblConnectUser;
import com.yisi.stiku.basedata.rpc.constant.ThirdLoginType;
import com.yisi.stiku.basedata.rpc.service.ConnectRpcService;
import com.yisi.stiku.common.bean.EntityState;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
@RpcService(ConnectRpcService.class)
public class TblConnectUserServiceImpl extends
		BaseServiceImpl<Integer, TblConnectUser> implements ConnectRpcService {

	@Resource
	private TblConnectUserDaoImpl tblConnectUserDaoImpl;

	@Override
	protected BaseDaoImpl<Integer, TblConnectUser> getBaseDaoImpl() {
		return tblConnectUserDaoImpl;
	}

	@Override
	public int saveConnectUser(TblConnectUser connUser) {
		TblConnectUser pConnUser = loadConnectUser(connUser.getOpenId(),
				connUser.getLoginType());
		if (pConnUser != null) {
			if (EntityState.NORMAL.equals(pConnUser.getDbState())) {
				// 如果已经绑定，且状态为正常，则返回直接true
				return connUser.getId();
			}
			//如果用户已经解绑，则更新状态，重新绑定
			connUser.setId(pConnUser.getId());
			connUser.setDbState(EntityState.NORMAL);

			this.update(connUser);
			return connUser.getId();
		}

		connUser.setDbState(EntityState.NORMAL);
		this.insertReturnPK(connUser);
		
		return connUser.getId();
	}

	@Override
	public boolean unbind(long userId, ThirdLoginType loginType) {
		return tblConnectUserDaoImpl.delete(userId, loginType);
	}

	@Override
	public TblConnectUser loadConnectUser(String openId,
			ThirdLoginType loginType) {
		TblConnectUser queryBean = new TblConnectUser();
		queryBean.setOpenId(openId);
		//queryBean.setDbState(EntityState.NORMAL);
		queryBean.setSource(loginType.name());

		return this.findOneByEntity(queryBean);
	}
	
	@Override
	public TblConnectUser loadConnectUser(long userId, ThirdLoginType loginType) {
		TblConnectUser queryBean = new TblConnectUser();
		queryBean.setUserId(userId);
		queryBean.setDbState(EntityState.NORMAL);
		queryBean.setSource(loginType.name());
		
		return this.findOneByEntity(queryBean);
	}
	
	@Override
	public List<TblConnectUser> loadConnectUsers(long userId) {
		TblConnectUser queryBean = new TblConnectUser();
		queryBean.setUserId(userId);
		queryBean.setDbState(EntityState.NORMAL);
		
		return this.findByEntity(queryBean);
	}

	@Override
	public boolean bindUser(int id, long userId) {
		TblConnectUser connUser = new TblConnectUser();
		connUser.setId(id);
		connUser.setUserId(userId);
		
		return this.update(connUser);
	}

}
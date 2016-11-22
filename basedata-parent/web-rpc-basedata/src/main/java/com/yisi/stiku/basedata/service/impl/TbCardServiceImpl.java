package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbCardDaoImpl;
import com.yisi.stiku.basedata.entity.TbCard;
import com.yisi.stiku.basedata.rpc.service.TbCardRpcService;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
@RpcService(TbCardRpcService.class)
public class TbCardServiceImpl extends BaseServiceImpl<String, TbCard> implements TbCardRpcService{
    @Resource
    private TbCardDaoImpl tbCardDaoImpl;

    @Override
    protected BaseDaoImpl<String, TbCard> getBaseDaoImpl() {
        return tbCardDaoImpl;
    }

	@Override
	public boolean insert(long userId, String cardNo) {
		TbCard card = new TbCard();
		card.setId(cardNo);
		card.setPassword("123456");
		card.setActivationFlag(1);
		card.setStudentId(userId);
		card.setLogoutFlag(0);
		
		this.insert(card);
		
		return true;
	}
}
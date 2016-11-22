package com.yisi.stiku.passport.util;

import com.yisi.stiku.wallet.entity.TransactionContext;
import com.yisi.stiku.wallet.rpc.constant.TransType;

public class TransactionContextUtils {

	public static TransactionContext newRechargeContext(Long userId, String actNo)
	{

		TransactionContext context = new TransactionContext();
		context.setInitUserId(userId);
		context.setTransType(TransType.CHARGE);
		context.setRechargeActNo(actNo);
		return context;
	}

	public static TransactionContext newFrozonContext(Long userId, Long frozonAmount)
	{

		TransactionContext context = new TransactionContext();
		context.setInitUserId(userId);
		context.setTransType(TransType.FROZEN);
		context.setPreAmount(frozonAmount);
		return context;
	}

}

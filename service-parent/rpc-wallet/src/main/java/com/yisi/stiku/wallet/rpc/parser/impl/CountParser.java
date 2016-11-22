package com.yisi.stiku.wallet.rpc.parser.impl;

import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.wallet.entity.ResUser;
import com.yisi.stiku.wallet.rpc.parser.EffectParser;

public class CountParser implements EffectParser{

	@Override
	public boolean isEffect(String resUserFeature) {
		if(RegexUtil.isInt(resUserFeature)){
			return Integer.valueOf(resUserFeature) > 0;
		}
		
		throw new IllegalArgumentException("feature in res_user table is invalid for CountParser.");
	}

	@Override
	public String getBindFeature(String resourceInfoFeature) {
		if(RegexUtil.isInt(resourceInfoFeature)){
			return resourceInfoFeature;
		}
		
		throw new IllegalArgumentException("feature in resource_info table is invalid for CountParser.");
	}
	
	@Override
	public long getEndTime(String resUserFeature) {
		throw new BaseRuntimeException("NOT_SUPPORT", "不支持的操作");
	}

	@Override
	public long getStartTime(String resUserFeature) {
		throw new BaseRuntimeException("NOT_SUPPORT", "不支持的操作");
	}

	@Override
	public String getBindFeature(ResUser preResUser, String resourceInfoFeature) {
		throw new BaseRuntimeException("NOT_SUPPORT", "不支持的操作");
	}

	
}

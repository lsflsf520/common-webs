package com.yisi.stiku.wallet.rpc.parser.impl;

import com.yisi.stiku.wallet.entity.ResUser;
import com.yisi.stiku.wallet.rpc.parser.EffectParser;


public class DefaultEffectTimeParser implements EffectParser{

	@Override
	public boolean isEffect(String resUserFeature) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getBindFeature(String resourceInfoFeature) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getEndTime(String resUserFeature) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getStartTime(String resUserFeature) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getBindFeature(ResUser preResUser, String resourceInfoFeature) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

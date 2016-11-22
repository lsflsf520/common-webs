package com.yisi.stiku.wallet.rpc.parser.impl;

import java.util.Date;




import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.common.utils.FeatureUtil;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.wallet.entity.ResUser;

public class MonthParser extends WeekTimeParser{

	@Override
	public String getBindFeature(String resourceInfoFeature) {
		if(RegexUtil.isInt(resourceInfoFeature)){
			int months = Integer.valueOf(resourceInfoFeature);
			
			Date currTime = new Date();
			
			return EFFECT_TIME_KEY + "=" + currTime.getTime() + FeatureUtil.KEY_VAL_SPLITER + UNEFFECT_TIME_KEY + "=" + DateUtil.timeAddByMonth(currTime, months).getTime();
		}
		
		throw new IllegalArgumentException("feature in resource_info table is invalid for " + this.getClass().getSimpleName());
	}

	@Override
	public String getBindFeature(ResUser preResUser, String resourceInfoFeature) {
		if(RegexUtil.isInt(resourceInfoFeature)){
			if(preResUser == null){
				return getBindFeature(resourceInfoFeature);
			}
			
			long endTime = getEndTime(preResUser.getFeature());
			long startTime = getStartTime(preResUser.getFeature());
			int months = Integer.valueOf(resourceInfoFeature);
			
			return EFFECT_TIME_KEY + "=" + startTime + FeatureUtil.KEY_VAL_SPLITER + UNEFFECT_TIME_KEY + "=" + DateUtil.timeAddByMonth(new Date(endTime), months).getTime();
		}
		
		throw new IllegalArgumentException("feature in resource_info table is invalid for " + this.getClass().getSimpleName());
	}

}

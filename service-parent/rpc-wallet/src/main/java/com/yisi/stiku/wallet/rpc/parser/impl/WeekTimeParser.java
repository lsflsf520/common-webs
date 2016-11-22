package com.yisi.stiku.wallet.rpc.parser.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.common.utils.FeatureUtil;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.wallet.entity.ResUser;
import com.yisi.stiku.wallet.rpc.parser.EffectParser;

public class WeekTimeParser implements EffectParser{
	
//	private final static Logger LOG = LoggerFactory.getLogger(WeekTimeParser.class);

	@Override
	public boolean isEffect(String feature) {
		Map<String, String> keyvalMap = FeatureUtil.parse2Map(feature);
		String effectTimeStr = keyvalMap.get(EFFECT_TIME_KEY);
		String uneffectTimeStr = keyvalMap.get(UNEFFECT_TIME_KEY);
		if(StringUtils.isBlank(effectTimeStr) || StringUtils.isBlank(uneffectTimeStr) || !RegexUtil.isInt(effectTimeStr) || !RegexUtil.isInt(uneffectTimeStr)){
			throw new IllegalArgumentException("feature '" + feature + "' in res_user is not supported by "  + this.getClass().getSimpleName());
		}
		
		long currTime = new Date().getTime();
		return currTime > Long.valueOf(effectTimeStr) && currTime < Long.valueOf(uneffectTimeStr);
	}

	@Override
	public String getBindFeature(String resourceInfoFeature) {
		if(RegexUtil.isInt(resourceInfoFeature)){
			int weeks = Integer.valueOf(resourceInfoFeature);
			
			Date currTime = new Date();
			
			return EFFECT_TIME_KEY + "=" + currTime.getTime() + FeatureUtil.KEY_VAL_SPLITER + UNEFFECT_TIME_KEY + "=" + DateUtil.timeAddByDays(currTime, weeks * 7).getTime();
		}
		
		throw new IllegalArgumentException("feature in resource_info table is invalid for "  + this.getClass().getSimpleName());
	}
	
	@Override
	public long getEndTime(String resUserFeature) {
		Map<String, String> keyvalMap = FeatureUtil.parse2Map(resUserFeature);
		String effectTimeStr = keyvalMap.get(EFFECT_TIME_KEY);
		String uneffectTimeStr = keyvalMap.get(UNEFFECT_TIME_KEY);
		if(StringUtils.isBlank(effectTimeStr) || StringUtils.isBlank(uneffectTimeStr) || !RegexUtil.isInt(effectTimeStr) || !RegexUtil.isInt(uneffectTimeStr)){
			throw new IllegalArgumentException("feature '" + resUserFeature + "' in res_user is not supported by " + this.getClass().getSimpleName());
		}
		
		return Long.valueOf(uneffectTimeStr);
	}

	@Override
	public long getStartTime(String resUserFeature) {
		Map<String, String> keyvalMap = FeatureUtil.parse2Map(resUserFeature);
		String effectTimeStr = keyvalMap.get(EFFECT_TIME_KEY);
		String uneffectTimeStr = keyvalMap.get(UNEFFECT_TIME_KEY);
		if(StringUtils.isBlank(effectTimeStr) || StringUtils.isBlank(uneffectTimeStr) || !RegexUtil.isInt(effectTimeStr) || !RegexUtil.isInt(uneffectTimeStr)){
			throw new IllegalArgumentException("feature '" + resUserFeature + "' in res_user is not supported by " + this.getClass().getSimpleName());
		}
		
		return Long.valueOf(effectTimeStr);
	}

	@Override
	public String getBindFeature(ResUser preResUser, String resourceInfoFeature) {
		if(RegexUtil.isInt(resourceInfoFeature)){
			if(preResUser == null){
				return getBindFeature(resourceInfoFeature);
			}
			
			long endTime = getEndTime(preResUser.getFeature());
			long startTime = getStartTime(preResUser.getFeature());
			int weeks = Integer.valueOf(resourceInfoFeature);
			
			return EFFECT_TIME_KEY + "=" + startTime + FeatureUtil.KEY_VAL_SPLITER + UNEFFECT_TIME_KEY + "=" + DateUtil.timeAddByDays(new Date(endTime), weeks * 7).getTime();
		}
		
		throw new IllegalArgumentException("feature in resource_info table is invalid for " + this.getClass().getSimpleName());
	}

}

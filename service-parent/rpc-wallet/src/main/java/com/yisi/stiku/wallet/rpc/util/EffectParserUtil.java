package com.yisi.stiku.wallet.rpc.util;

import java.util.HashMap;
import java.util.Map;

import com.yisi.stiku.wallet.entity.ResUser;
import com.yisi.stiku.wallet.rpc.constant.EffectParserKey;
import com.yisi.stiku.wallet.rpc.parser.EffectParser;
import com.yisi.stiku.wallet.rpc.parser.impl.CountParser;
import com.yisi.stiku.wallet.rpc.parser.impl.DefaultEffectTimeParser;
import com.yisi.stiku.wallet.rpc.parser.impl.MonthParser;
import com.yisi.stiku.wallet.rpc.parser.impl.WeekTimeParser;


public class EffectParserUtil {
	
    private final static Map<EffectParserKey, EffectParser> timeParserMap = new HashMap<EffectParserKey, EffectParser>();
	
	static{
		timeParserMap.put(EffectParserKey.DefaultEffectTimeParser, new DefaultEffectTimeParser());
		timeParserMap.put(EffectParserKey.WeekTimeParser, new WeekTimeParser());
		timeParserMap.put(EffectParserKey.CountParser, new CountParser());
		timeParserMap.put(EffectParserKey.MonthParser, new MonthParser());
	}


	/**
	 * 
	 * @return 根据resource_info中的feature规则，动态计算出绑定资源生效范围的字符串，用于存入 res_user表中
	 */
	public static String getBindFeature(EffectParserKey parserKey, String resInfoFeature){
		EffectParser parser = timeParserMap.get(parserKey);
		if(parser == null){
			throw new IllegalArgumentException("EffectParser '" + parserKey + "' is not supported");
		}
		
		return parser.getBindFeature(resInfoFeature);
	}
	
	/**
	 * 
	 * @param resUser
	 * @param parserKey
	 * @param resInfoFeature
	 * @return 根据resUser中的feature生成一个续费后的feature
	 */
	public static String getBindFeature(ResUser resUser, EffectParserKey parserKey, String resInfoFeature){
		EffectParser parser = timeParserMap.get(parserKey);
		if(parser == null){
			throw new IllegalArgumentException("EffectParser '" + parserKey + "' is not supported");
		}
		
		return parser.getBindFeature(resUser, resInfoFeature);
	}

	/**
	 * 
	 * @param feature res_user表中的feature
	 * @return 判断res_user中的feature是否有效
	 */
	public static boolean isEffect(EffectParserKey parserKey, String resUserFeature){
		EffectParser parser = timeParserMap.get(parserKey);
		if(parser == null){
			throw new IllegalArgumentException("EffectParser '" + parserKey + "' is not supported");
		}
		
		return parser.isEffect(resUserFeature);
	}
	
	/**
	 * 
	 * @param parserKey
	 * @param resUserFeature
	 * @return 返回feature中的截止时间，以毫秒为单位
	 */
	public static long getEndTime(EffectParserKey parserKey, String resUserFeature){
		EffectParser parser = timeParserMap.get(parserKey);
		if(parser == null){
			throw new IllegalArgumentException("EffectParser '" + parserKey + "' is not supported");
		}
		
		return parser.getEndTime(resUserFeature);
	}
	
	/**
	 * 
	 * @param parserKey
	 * @param resUserFeature
	 * @return 返回feature中的截止时间，以毫秒为单位
	 */
	public static long getStartTime(EffectParserKey parserKey, String resUserFeature){
		EffectParser parser = timeParserMap.get(parserKey);
		if(parser == null){
			throw new IllegalArgumentException("EffectParser '" + parserKey + "' is not supported");
		}
		
		return parser.getStartTime(resUserFeature);
	}
	
}

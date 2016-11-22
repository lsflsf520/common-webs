package com.yisi.stiku.wallet.rpc.parser;

import com.yisi.stiku.wallet.entity.ResUser;

/**
 * 
 * @author shangfeng
 *
 */
public interface EffectParser {

	public static final String EFFECT_TIME_KEY = "effectTime";
	public static final String UNEFFECT_TIME_KEY = "uneffectTime";
	
	/**
	 * 
	 * @param feature res_user表中的feature
	 * @return 判断res_user中的feature是否有效
	 */
	boolean isEffect(String resUserFeature);
	
	/**
	 * 
	 * @param resUserFeature
	 * @return 返回feature中的截止时间，以毫秒为单位
	 */
	long getEndTime(String resUserFeature);
	
	/**
	 * 
	 * @param resUserFeature
	 * @return 返回feature中的起始时间，以毫秒为单位
	 */
	long getStartTime(String resUserFeature);
	
	/**
	 * 
	 * @param resourceInfoFeature resource_info表中的feature
	 * @return 根据resource_info中的feature规则，动态计算出绑定资源生效范围的字符串，用于存入 res_user表中
	 */
	String getBindFeature(String resourceInfoFeature);
	
	/**
	 * 
	 * @param preResUser 指定一个起始时间
	 * @param resourceInfoFeature resource_info表中的feature
	 * @return 根据已经存在的资源信息和resource_info中的feature规则，动态计算出绑定资源生效范围的字符串，用于存入 res_user表中
	 */
	String getBindFeature(ResUser preResUser, String resourceInfoFeature);
	
}

package com.yisi.stiku.msg.rpc.service;

import java.util.Map;

/**
 * 
 * @author shangfeng
 *
 */
public interface EmailSenderRpcService {

	public final static String TO = "to";
	public final static String USER_NAME = "userName";
	
	/**
	 * 
	 * @param emailTmplId
	 * @param paramMap
	 * @param userIds
	 * @return 如果指定userIds，那么系统会自动往paramMap中增加 
	 *      to、userName 两个参数(如果paramMap中已经有这两个参数，则将会被覆盖)，邮件模板中可以直接使用
	 */
	public boolean sendEmail(int emailTmplId, Map<String, String> paramMap, long... userIds);
	
}

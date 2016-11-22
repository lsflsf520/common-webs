package com.yisi.stiku.msg.service.impl;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.service.impl.TblAuthUserServiceImpl;
import com.yisi.stiku.common.bean.EntityState;
import com.yisi.stiku.common.bean.GlobalResultCode;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.StringUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.msg.entity.EmailTmpl;
import com.yisi.stiku.msg.rpc.service.EmailSenderRpcService;
import com.yisi.stiku.rpc.annotation.RpcService;

@RpcService(EmailSenderRpcService.class)
public class EmailSenderImpl implements EmailSenderRpcService{
	
	private final static Logger LOG = LoggerFactory.getLogger(EmailSenderImpl.class);

	@Resource
	private EmailTmplServiceImpl emailTmplServiceImpl;
	
	@Resource
	private TblAuthUserServiceImpl TblAuthUserServiceImpl;
	
	@Override
	public boolean sendEmail(int emailTmplId, Map<String, String> paramMap, long... userIds) {
		EmailTmpl tmpl = emailTmplServiceImpl.findById(emailTmplId);
		if(tmpl == null || !EntityState.NORMAL.equals(tmpl.getDbState())){
			throw new BaseRuntimeException(GlobalResultCode.ILLEGAL_STATE);
		}
		
		HtmlEmail email = new HtmlEmail();// 可以发送html类型的邮件

		String zkConfig = ZkConstant.ALIAS_PROJECT_NAME + "/email.properties";
		
		email.setHostName(ConfigOnZk.getValue(zkConfig, "sender.email.smtp.host", "smtp.exmail.qq.com"));// 指定要使用的邮件服务器
		email.setAuthentication(ConfigOnZk.getValue(zkConfig, "sender.email.address"), 
				ConfigOnZk.getValue(zkConfig, "sender.email.password"));// 使用163的邮件服务器需提供在163已注册的用户名、密码
		email.setCharset(ConfigOnZk.getValue(zkConfig, "sender.email.charset", "UTF-8"));
//		email.setFrom(ConfigOnZk.getValue(zkConfig, "sender.email.address", "gerrit@17daxue.com"));// 设置发件人
		try {
			email.setFrom(ConfigOnZk.getValue(zkConfig, "sender.email.address"),
					ConfigOnZk.getValue(zkConfig, "sender.email.showName", "北京创数教育"),
					ConfigOnZk.getValue(zkConfig, "sender.email.charset", "UTF-8"));
			if(userIds != null && userIds.length > 0){
				for(long userId : userIds){
					TblAuthUser user = TblAuthUserServiceImpl.getUserInfoById(userId);
					if(user == null || !EntityState.NORMAL.equals(user.getDbState())){
						throw new BaseRuntimeException(GlobalResultCode.ILLEGAL_STATE);
					}
					
					email.addTo(user.getEmail());// 设置收件人
					email.setSubject(tmpl.getTitle());// 设置主题
					
					email.setMsg(parseContent(tmpl.getContent(), paramMap, user));// 设置邮件内容
					
					email.send();
					LOG.info("success send email for emailTmplId:" + emailTmplId + ",paramMap:" + paramMap);
				}
			}else if(StringUtils.isNotBlank(paramMap.get(TO))){
				email.addTo(paramMap.get(TO));// 设置收件人
				email.setSubject(tmpl.getTitle());// 设置主题
				
				email.setMsg(parseContent(tmpl.getContent(), paramMap));// 设置邮件内容
				
				email.send();
				LOG.info("success send email for emailTmplId:" + emailTmplId + ",paramMap:" + paramMap);
			}else{
				throw new BaseRuntimeException(GlobalResultCode.PARAM_ERROR);
			}

//			File file = new File("C:\\testEmail.txt");// 要发送的附件
//
//			EmailAttachment attachment = new EmailAttachment();
//			attachment.setPath(file.getPath());
//			attachment.setName(file.getName());
//			attachment.setDescription("附件描述");
//			attachment.setDisposition(EmailAttachment.ATTACHMENT);// 附件的类型
//			email.attach(attachment);

			
			return true;
		} catch (EmailException e) {
			LOG.error("emailTmplId:" + emailTmplId + ",paramMap:" + paramMap, e);
		}
		return false;
	}
	
	private String parseContent(String content, Map<String, String> paramMap, TblAuthUser user){
		if(paramMap == null){
			paramMap = new HashedMap<String, String>();
		}
		
		paramMap.put(TO, user.getEmail());
		paramMap.put(USER_NAME, user.getShowName());
		
		return parseContent(content, paramMap);
	}

	private String parseContent(String content, Map<String, String> paramMap){
		if(StringUtils.isBlank(content)){
			throw new BaseRuntimeException(GlobalResultCode.PARAM_ERROR);
		}
		Set<String> vars = StringUtil.searchVar("\\$\\{\\s*(\\w+)\\s*\\}", content, 1);
		for(String var : vars){
			String value = paramMap.get(var);
			if(StringUtils.isBlank(value)){
				value = "unknown";
			}
			content = content.replaceAll("\\$\\{\\s*" + var + "\\s*\\}", value);
		}
		return content;
	}
	
	public static void main(String[] args) throws EmailException {
//		Set<String> vars = StringUtil.searchVar("\\$\\{\\s*(\\w+)\\s*\\}", "我爱你${ var1}中国，你是${var2 }好样的，我们都${ var3 }爱你", 1);
	
//		Map<String, String> paramMap = new HashedMap<String, String>();
//		paramMap.put("var1", 123 + "");
//		paramMap.put("var2", 321 + "");
//		paramMap.put("var3", 1234567 + "");
		
//		String content = parseContent("我爱你${ var1}中国，你是${var2 }好${var1}样的，${ var4 }我们${var1 }都${ var3 }爱你", paramMap);
//		
//	    System.out.println(content);
		
	}
}

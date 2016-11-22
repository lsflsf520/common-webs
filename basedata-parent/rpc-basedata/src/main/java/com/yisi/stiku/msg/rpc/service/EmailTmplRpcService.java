package com.yisi.stiku.msg.rpc.service;

import org.springframework.data.domain.Page;

import com.yisi.stiku.msg.entity.EmailTmpl;

/**
 * 
 * @author shangfeng
 *
 */
public interface EmailTmplRpcService {

	
	public boolean save(EmailTmpl emailTmpl);
	
	public boolean invalid(int tmplId);
	
	public EmailTmpl findById(Integer tmplId);
	
	public Page<EmailTmpl> searchTmplList(EmailTmpl query, int currPage, int maxRows);
	
	
}

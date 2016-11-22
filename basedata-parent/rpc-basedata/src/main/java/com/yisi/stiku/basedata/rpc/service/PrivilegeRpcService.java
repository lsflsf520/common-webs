package com.yisi.stiku.basedata.rpc.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

import com.yisi.stiku.basedata.rpc.bean.PrivilegeInfo;


public interface PrivilegeRpcService {

	 public Map<String, Collection<ConfigAttribute>> loadResourceDefine();
	 
	 public Map<String/*url*/, PrivilegeInfo> loadUrl2PrivilegeMap();
	 
	 List<PrivilegeInfo> findPrivilegesByUserId(long userId);
	
}

package com.yisi.stiku.wallet.rpc.service;

import org.springframework.data.domain.PageImpl;

import com.yisi.stiku.common.bean.PagerControl;
import com.yisi.stiku.wallet.entity.JiesuanData;
import com.yisi.stiku.wallet.rpc.constant.JieSuanState;


public interface JiesuanRpcService {

	boolean jiesuanByAgent(int jiesuanId, long operUserId);
	
	
	PageImpl<JiesuanData> findJiesuanData(JieSuanState jiesuanState, int page, int maxRows);
	
}

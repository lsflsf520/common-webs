package com.yisi.stiku.statbg.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yisi.stiku.rpc.annotation.RpcService;
import com.yisi.stiku.statbg.util.BootUtil;

/**
 * @author shangfeng
 *
 */
@Service
@RpcService(BgStatRpcService.class)
public class BgStatRpcService {

	private final static Logger LOG = LoggerFactory.getLogger(BgStatRpcService.class);

	public void execStat(String argStr) {

		argStr = StringUtils.isNotBlank(argStr) ? argStr.trim() : null;
		LOG.debug(LOG.isDebugEnabled() ? "received arg str '" + argStr + "'" : null);
		String[] args = StringUtils.isNotBlank(argStr) ? argStr.split("\\s+") : null;

		BootUtil.execStat(args);
	}

}

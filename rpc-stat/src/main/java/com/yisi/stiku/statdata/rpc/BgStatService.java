package com.yisi.stiku.statdata.rpc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yisi.stiku.rpc.bean.RpcRequest;
import com.yisi.stiku.rpc.client.netty.RpcClientUtil;
import com.yisi.stiku.rpc.cluster.impl.NettyClientRouter;

/**
 * @author shangfeng
 *
 */
@Service
public class BgStatService {

	private final static Logger LOG = LoggerFactory.getLogger(BgStatService.class);

	public final static String CONFIG_FILE_KEY = "configFile";

	@Resource
	private NettyClientRouter nettyClientRouter;

	public void execWeekTestStat(long weektestId, long classId) {

		Map<String, Serializable> paramMap = new HashMap<String, Serializable>();
		paramMap.put(CONFIG_FILE_KEY, "context/problemdata/spring-weektest-stat.xml");
		paramMap.put("weektestId", weektestId);
		paramMap.put("classId", classId);

		execStat(paramMap);
	}

	/**
	 * 
	 * @param paramMap
	 */
	public void execStat(Map<String, Serializable> paramMap) {

		String paramStr = " ";
		if (paramMap != null && !paramMap.isEmpty()) {
			for (Entry<String, Serializable> entry : paramMap.entrySet()) {
				paramStr += "-" + entry.getKey() + " " + entry.getValue() + " ";
			}
		}

		RpcRequest request = new RpcRequest("com.yisi.stiku.statbg.service.BgStatRpcService", "1.0.0", "execStat", paramStr);
		request.setParameterTypes(new Class[] { String.class });

		try {
			RpcClientUtil.sendAsyncRequest(nettyClientRouter, request);
		} catch (Throwable e) {
			LOG.error("bgstat error paramMap " + paramMap, e);
		}
	}

}

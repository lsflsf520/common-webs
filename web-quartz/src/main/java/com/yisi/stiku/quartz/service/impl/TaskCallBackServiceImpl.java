package com.yisi.stiku.quartz.service.impl;

import org.springframework.stereotype.Component;

import com.yisi.stiku.quartz.service.rpc.TaskLogRpcService;
import com.yisi.stiku.rpc.annotation.RpcService;
import com.yisi.stiku.rpc.bean.AsynRpcRequest;

@Component
@RpcService(TaskLogRpcService.class)
public class TaskCallBackServiceImpl implements TaskLogRpcService {

	@Override
	public void updateLogStateBack(AsynRpcRequest rpcRequest) {
		System.out.println("1111111111111111111111");
	}

}

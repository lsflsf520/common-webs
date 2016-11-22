package com.yisi.stiku.quartz.service.rpc;

import com.yisi.stiku.rpc.bean.AsynRpcRequest;

public interface TaskLogRpcService {

	public void updateLogStateBack(AsynRpcRequest rpcRequest);

}

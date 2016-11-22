package com.yisi.stiku.quartz.job;

import java.sql.Date;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.quartz.dao.TaskDao;
import com.yisi.stiku.quartz.dao.entity.TaskEntity;
import com.yisi.stiku.quartz.dao.entity.TaskInfo;
import com.yisi.stiku.quartz.dao.entity.TaskPK;
import com.yisi.stiku.quartz.dao.entity.TaskResult;
import com.yisi.stiku.quartz.dao.entity.TaskState;
import com.yisi.stiku.rpc.bean.AsynRpcRequest;
import com.yisi.stiku.rpc.client.netty.RpcClientUtil;
import com.yisi.stiku.rpc.cluster.Router;

public class RPCJobParam implements Job {

	private static final boolean DEFAULT_LOG_OPEN = false;
	private static final Logger LOG = LoggerFactory.getLogger(RPCJob.class);
	@Resource
	private Router nettyClientRouter;

	@Resource
	private TaskDao taskDao;

	public Router getNettyClientRouter() {
		return nettyClientRouter;
	}

	public void setNettyClientRouter(Router nettyClientRouter) {
		this.nettyClientRouter = nettyClientRouter;
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		try {
			boolean openLog = DEFAULT_LOG_OPEN;
			String logOpen = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH,
					"quartz.log.open");

			if (logOpen != null) {
				openLog = Boolean.parseBoolean(logOpen);
			}
			if (openLog) {
				taskDao.insert(newTaskEntity(context));
			}

		}

		catch (Exception e1) {
			LOG.error("日志记录失败" + e1);
		}

		String className = context.getJobDetail().getJobDataMap()
				.getString("className");
		String methodName = context.getJobDetail().getJobDataMap()
				.getString("methodName");

		String inputParam = context.getJobDetail().getJobDataMap()
				.getString("inputParm");

		boolean isAysn = context.getJobDetail().getJobDataMap()
				.getBoolean("isAsyn");
		if (isAysn) {
			try {
				RpcClientUtil.sendRequest(nettyClientRouter, className,
						"1.0.0", methodName, new Object[] { inputParam },
						new Class[] { String.class });
			} catch (Throwable e) {
				LOG.error("同步服务调用失败" + e.getMessage(), e);
			}
		}

		else {
			try {
				AsynRpcRequest request = null;
				request = AsynRpcRequest.newInstance(className, "1.0.0",
						methodName, new Object[] { inputParam },
						new Class[] { String.class }).buildCallBackRequest(
						false,
						"com.yisi.stiku.quartz.service.rpc.TaskLogRpcService",
						"updateLogStateBack", new Object[] { request },
						new Class<?>[] { AsynRpcRequest.class });
				RpcClientUtil.sendAsyncRequest(nettyClientRouter, request);
			} catch (Throwable e) {
				LOG.error("异步服务调用失败" + e.getMessage(), e);

			}
		}

	}

	private TaskEntity newTaskEntity(JobExecutionContext context)
			throws SchedulerException {
		TaskEntity entity = new TaskEntity();
		TaskPK taskPK = null;
		taskPK = new TaskPK(context.getScheduler().getSchedulerName(),
				new JobKey(context.getJobDetail().getKey().getName(), context
						.getJobDetail().getKey().getGroup()));
		entity.setTaskPK(taskPK);
		entity.setInfo(new TaskInfo(new Date(context.getJobRunTime())));
		entity.setResult(new TaskResult(TaskState.RUNNING));
		return entity;
	}
}

package com.yisi.stiku.passport.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yisi.stiku.cache.constant.DefaultJedisKeyNS;
import com.yisi.stiku.cache.redis.ShardJedisTool;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.wallet.entity.FuncCard;
import com.yisi.stiku.wallet.rpc.service.FuncCardRpcService;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.OperationResult;
import com.yisi.stiku.web.util.WebUtils;

/**
 * 
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("/funccard")
public class FuncCardController {

	private final static Logger LOG = LoggerFactory.getLogger(FuncCardController.class);
	
	@Resource
	private FuncCardRpcService funcCardRpcService;
	
	/**
	 * 升级
	 * @param request
	 * @param response
	 * @param actCode
	 */
	@RequestMapping("/upgrade")
	public void upgradeFuncCard(HttpServletRequest request, HttpServletResponse response, String actCode){
		if(StringUtils.isBlank(actCode)){
			WebUtils.writeJson(OperationResult.buildFailureResult("激活码不能为空!"), request, response);
			return;
		}
		
		ShardJedisTool.del(DefaultJedisKeyNS.index, LoginSesionUtil.getUserId()); //激活卡的时候，优先去除缓存中的学生套餐页面信息
		
		try{
			String countStr = ShardJedisTool.get(DefaultJedisKeyNS.ei, LoginSesionUtil.getToken(request) + "actCode");
			if(RegexUtil.isInt(countStr) && Integer.valueOf(countStr) > Integer.valueOf(ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "error.input.actcode.maxcount", "10"))){
				WebUtils.writeJson(OperationResult.buildFailureResult("错误次数过多，请明天再试!"), request, response);
				return;
			}
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
		}
		
		try{
			funcCardRpcService.upgrade(LoginSesionUtil.getUserId(), actCode);
		}catch(BaseRuntimeException e){
			ShardJedisTool.incr(DefaultJedisKeyNS.ei, LoginSesionUtil.getToken(request) + "actCode"); //记录错误次数
			throw e;
		}
		
		FuncCard funcCard = funcCardRpcService.loadByUserId(LoginSesionUtil.getUserId());
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("cardCode", funcCard.getDbType().getDbCode());
		dataMap.put("desc", funcCard.getDbType().getDesc());
		dataMap.put("animationImg", funcCard.getDbType().getAnimationImg());
		WebUtils.writeJson(OperationResult.buildSuccessResult("操作成功!", dataMap), request, response);
	}
	
	/**
	 * 续费
	 * @param request
	 * @param response
	 * @param actCode
	 */
	@RequestMapping("/continueFee")
	public void continueFee(HttpServletRequest request, HttpServletResponse response, String actCode){
		if(StringUtils.isBlank(actCode)){
			WebUtils.writeJson(OperationResult.buildFailureResult("激活码不能为空!"), request, response);
			return;
		}
		
		ShardJedisTool.del(DefaultJedisKeyNS.index, LoginSesionUtil.getUserId());  //激活卡的时候，优先去除缓存中的学生套餐页面信息
		
		try{
			String countStr = ShardJedisTool.get(DefaultJedisKeyNS.ei, LoginSesionUtil.getToken(request) + "actCode");
			if(RegexUtil.isInt(countStr) && Integer.valueOf(countStr) > Integer.valueOf(ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "error.input.actcode.maxcount", "10"))){
				WebUtils.writeJson(OperationResult.buildFailureResult("错误次数过多，请明天再试!"), request, response);
				return;
			}
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
		}
		try{
		    funcCardRpcService.continueFee(LoginSesionUtil.getUserId(), actCode);
		}catch(BaseRuntimeException e){
			ShardJedisTool.incr(DefaultJedisKeyNS.ei, LoginSesionUtil.getToken(request) + "actCode"); //记录错误次数
			throw e;
		}
		
		FuncCard funcCard = funcCardRpcService.loadByUserId(LoginSesionUtil.getUserId());
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("cardCode", funcCard.getDbType().getDbCode());
		dataMap.put("desc", funcCard.getDbType().getDesc());
		dataMap.put("animationImg", funcCard.getDbType().getAnimationImg());
		WebUtils.writeJson(OperationResult.buildSuccessResult("操作成功!", dataMap), request, response);
	}
}

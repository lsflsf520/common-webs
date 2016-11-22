package com.yisi.stiku.passport.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yisi.stiku.common.bean.PageList;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.wallet.entity.OrderRecord;
import com.yisi.stiku.wallet.rpc.service.OrderRecordRpcService;
import com.yisi.stiku.wallet.rpc.service.TransactionRpcService;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.OperationResult;
import com.yisi.stiku.web.util.WebUtils;

/**
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("/order")
public class OrderRecordController {

	@Resource
	private OrderRecordRpcService orderRecordRpcService;

	@Resource
	private TransactionRpcService transactionRpcService;

	// @RequestMapping("createOrder")
	// public void createOrder(HttpServletRequest request, HttpServletResponse
	// response, String busiKey) {
	//
	// ResultModel resultModel =
	// orderRecordRpcService.createOrderWithDou(LoginSesionUtil.getUserId(),
	// busiKey, 15,
	// "举一反三-基础巩固",
	// OrderType.MAGAZINE_PRINT);
	//
	// WebUtils.writeJson(
	// OperationResult.buildSuccessResult("操作成功！orderId:" +
	// resultModel.getModel()),
	// request, response);
	// }

	// @RequestMapping("completeOrder")
	// public void completeOrder(HttpServletRequest request, HttpServletResponse
	// response, long mgId, int pageNum) {
	//
	// boolean result = orderRecordRpcService.completeOrder(mgId, pageNum);
	//
	// WebUtils.writeJson(
	// result ? OperationResult.buildSuccessResult("操作成功！") :
	// OperationResult.buildFailureResult("操作失败"),
	// request, response);
	// }

	// @RequestMapping("exportExcel")
	// public void exportExcel(HttpServletResponse response) {
	//
	// HSSFWorkbook wb = ExportUtil.genExcel(Arrays.asList("姓名", "性别", "年龄"),
	// Arrays.asList(Arrays.asList("刘尚风", "男", "28"), Arrays.asList("王珏", "女",
	// "29")), "名单");
	//
	// ExportUtil.writeHttpResponse(response, wb, "测试文件");
	// }

	/**
	 * 
	 * @return
	 */
	@RequestMapping("loadMyOrderList")
	public String loadMyOrderList(HttpServletRequest request, Integer pageNo) {

		pageNo = pageNo == null ? 1 : pageNo;
		int maxRow = 10;
		Page<OrderRecord> recordPage = orderRecordRpcService.loadMyOrderList(LoginSesionUtil.getUserId(),
				pageNo, maxRow);

		PageList<OrderRecord> pageList = new PageList<OrderRecord>(pageNo, maxRow, (int) recordPage.getTotalElements(),
				recordPage.getTotalPages(), "/order/loadMyOrderList", recordPage.getContent());
		request.setAttribute("title", "订单记录");
		request.setAttribute("pageList", pageList);
		request.setAttribute(
				"backUrl", getBackUrl(request));

		return "order/order_list";
	}

	private String getBackUrl(HttpServletRequest request) {

		return StringUtils.isNotBlank(request.getParameter("backUrl"))
				&& request.getParameter("backUrl").startsWith("http://") ? request.getParameter("backUrl") :
				// (StringUtils
				// .isNotBlank(request.getHeader("referer")) &&
				// request.getHeader("referer").startsWith("http://") ? request
				// .getHeader("referer") :
				request.getContextPath() + "/priv/info/account"
		// )
		;
	}

	@RequestMapping("cancelOrder")
	public void cancelOrder(HttpServletRequest request, HttpServletResponse response, long orderNo) {

		boolean canceled = orderRecordRpcService.cancelOrder(orderNo, LoginSesionUtil.getUserId());

		WebUtils.writeJson(
				canceled ? OperationResult.buildSuccessResult("操作成功！") : OperationResult.buildFailureResult("操作失败！"),
				request, response);
	}

	@RequestMapping("prePayOrder")
	public void prePayOrder(HttpServletRequest request, HttpServletResponse response, long orderNo, String payPasswd) {

		boolean result = false;
		try {
			result = orderRecordRpcService.payOrder(orderNo, payPasswd);
			WebUtils.writeJson(
					result ? OperationResult.buildSuccessResult("支付成功！") : OperationResult
							.buildFailureResult("支付失败！"),
					request, response);
		} catch (BaseRuntimeException e) {
			WebUtils.writeJson(
					OperationResult
							.buildFailureResult(e.getResultCode().getFriendlyMsg()),
					request, response);
		} catch (Throwable th) {
			WebUtils.writeJson(
					OperationResult
							.buildFailureResult("系统错误！"),
					request, response);
		}

	}

	@RequestMapping("loadWaitPayOrderNum")
	public void loadWaitPayOrderNum(HttpServletRequest request, HttpServletResponse response) {

		int orderNum = orderRecordRpcService.loadWaitPayOrderNum(LoginSesionUtil.getUserId());

		WebUtils.writeJson(OperationResult.buildSuccessResult("查询成功！", orderNum), request, response);
	}

}

package com.yisi.stiku.passport.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.entity.TblConnectUser;
import com.yisi.stiku.basedata.rpc.constant.ThirdLoginType;
import com.yisi.stiku.basedata.rpc.service.ConnectRpcService;
import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.common.bean.UserType;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.passport.service.impl.UserLoginHelper;
import com.yisi.stiku.web.constant.WebConstant;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.LoginUtils;
import com.yisi.stiku.web.util.OperationResult;
import com.yisi.stiku.web.util.WebUtils;

/**
 * 
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("/user/flogin")
public class QQConnectController {

	private final static Logger LOG = LoggerFactory
			.getLogger(QQConnectController.class);

	@Resource
	private ConnectRpcService connectRpcService;

	@Resource
	private UserRpcService userRpcService;

	@Resource
	private UserLoginHelper userHelper;

	@RequestMapping(value = "{loginType}", method = RequestMethod.GET)
	public void toThirdPage(HttpServletRequest request,
			HttpServletResponse response, @PathVariable ThirdLoginType loginType) {

		response.setContentType("text/html;charset=utf-8");
		try {
			String authUrl = new Oauth().getAuthorizeURL(request);
			String callback = "http%3A%2F%2Fwww.51zhenduan.com";

			String referer = StringUtils.isNotBlank(request.getParameter("referer")) ? request.getParameter("referer")
					: request.getHeader("referer");
			if (StringUtils.isNotBlank(referer)) {
				try {
					// 这里两次encode，是为了避免丢失参数
					callback = URLEncoder.encode(referer, "UTF-8");
					callback = URLEncoder.encode(callback, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					LOG.warn("encode referer '" + referer + "' failure.");
				}
			}
			response.sendRedirect(authUrl.replace("#referer#", callback));
		} catch (QQConnectException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@RequestMapping(value = "after{loginType}Login")
	public String afterQQLogin(HttpServletRequest request,
			HttpServletResponse response, @PathVariable ThirdLoginType loginType, RedirectAttributes attr)
			throws QQConnectException {

		response.setContentType("text/html; charset=utf-8");

		AccessToken accessTokenObj = (new Oauth())
				.getAccessTokenByRequest(request);

		if (accessTokenObj == null
				|| StringUtils.isBlank(accessTokenObj.getAccessToken())) {
			// 我们的网站被CSRF攻击了或者用户取消了授权
			// 做一些数据统计工作
			request.setAttribute("errorMsg", "对不起，您已取消授权或授权失败，请重新操作！");
			LOG.warn("login from " + loginType + " failed.");
		} else {
			String accessToken = accessTokenObj.getAccessToken();
			// long tokenExpireIn = accessTokenObj.getExpireIn(); //token失效的时间

			OpenID openIDObj = new OpenID(accessToken);
			String openID = openIDObj.getUserOpenID();

			UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
			UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
			if (userInfoBean.getRet() == 0) {
				TblConnectUser pConnUser = connectRpcService.loadConnectUser(
						openID, loginType);

				if (pConnUser == null) {
					request.setAttribute("avatar", userInfoBean.getAvatar()
							.getAvatarURL100());
					request.setAttribute("nick", userInfoBean.getNickname());
					try {
						TblAuthUser authUser = userHelper.createUser(userInfoBean.getNickname(), null);

						TblConnectUser connUser = new TblConnectUser();
						connUser.setUserId(authUser.getId());
						connUser.setAvatar(userInfoBean.getAvatar()
								.getAvatarURL100());
						connUser.setNick(userInfoBean.getNickname());
						connUser.setOpenId(openID);
						connUser.setAccessToken(accessToken);
						connUser.setLoginType(loginType);

						int fid = connectRpcService.saveConnectUser(connUser);

						try {
							// 如果用户当前处于登录状态，则意味着用户在执行qq绑定操作
							long userId = LoginSesionUtil.getUserId();
							connectRpcService.bindUser(fid, userId);
							String referer = request.getParameter("referer");
							return "redirect:"
									+ (StringUtils.isNotBlank(referer) ? referer : WebConstant.getLogonUrl(
											LoginSesionUtil.getUserTypeProjectName(),
											WebConstant.getSchoolIdsForCurrUser(authUser.getType())));
						} catch (BaseRuntimeException e) {
							// 如果用户当前处于未登录状态，则自动以第三方用户的身份登录网站
							userHelper.doLogon(request, response, authUser);

							attr.addAttribute("avatar", connUser.getAvatar());
							attr.addAttribute("nick", connUser.getNick());
							attr.addAttribute("fid", fid);
							return "redirect:/user/flogin/toBindPage";
						}
					} catch (BaseRuntimeException e) {
						request.setAttribute("errorMsg", e.getFriendlyMsg());
					}
				} else {
					TblAuthUser authUser = userRpcService
							.getUserInfoById(pConnUser.getUserId());
					if (LoginSesionUtil.hasLogon()) {
						// 如果用户当前处于登录状态，则意味着用户在执行qq绑定操作
						if (LoginSesionUtil.getUserId() == pConnUser.getUserId()) {
							// 如果当前登录用户与qq授权账号绑定的用户是同一个用户，则直接跳转到个人信息页 或 首页
							String referer = request.getParameter("referer");
							return "redirect:"
									+ (StringUtils.isNotBlank(referer) ? referer : WebConstant.getLogonUrl(
											LoginSesionUtil.getUserTypeProjectName(),
											WebConstant.getSchoolIdsForCurrUser(authUser.getType())));
						}
						// 在此需要跳到另一个页面询问用户是否需要将已存在的第三方用户解绑，然后绑定到当前登录用户
						String referer = request.getParameter("referer");
						try {
							referer = URLEncoder.encode(referer, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							LOG.warn("encode referer error");
						}

						attr.addAttribute("bindUserName", authUser.getShowName());
						attr.addAttribute("currUserName", LoginSesionUtil.getUserName());
						attr.addAttribute("fid", pConnUser.getId());
						attr.addAttribute("referer", (StringUtils.isNotBlank(referer) ? referer : ""));
						return "redirect:/user/flogin/toHasBindPage";
					} else {
						// 如果用户已经通过该第三方网站登录过创数，则设置登录信息，并跳转到指定页面(默认为首页)
						if (authUser == null) {
							throw new BaseRuntimeException("ILLEGAL_STATE",
									"对不起，用户不存在或已被禁用，请联系管理员！");
						}
						userHelper.doLogon(request, response, authUser);

						return "redirect:"
								+ LoginUtils.getLogonRedirectUrl(request, LoginSesionUtil.getUserTypeProjectName(),
										WebConstant.getSchoolIdsForCurrUser(authUser.getType()));
					}
				}
			} else {
				request.setAttribute("errorMsg",
						"QQ授权失败，原因：" + userInfoBean.getMsg());
			}
		}

		return "redirect:" + WebConstant.getStudentDomain(null) + "/error/500";
	}

	@RequestMapping("toBindPage")
	public String toBindPage(HttpServletRequest request,
			HttpServletResponse response) {

		request.setAttribute("avatar", request.getParameter("avatar"));
		request.setAttribute("nick", request.getParameter("nick"));
		request.setAttribute("fid", request.getParameter("fid"));
		return "connector/bind";
	}

	@RequestMapping("toHasBindPage")
	public String toHasBindPage(HttpServletRequest request) {

		request.setAttribute("currUserName", request.getParameter("currUserName"));
		request.setAttribute("bindUserName", request.getParameter("bindUserName"));
		request.setAttribute("referer", request.getParameter("referer"));
		request.setAttribute("fid", request.getParameter("fid"));

		return "connector/hasbind";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @return 根据用户名和密码来绑定17大学的用户
	 */
	@RequestMapping("binduser")
	public String bindUser(HttpServletRequest request,
			HttpServletResponse response, int fid) {

		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");

		try {
			TblAuthUser authUser = userRpcService.checkPasswd(loginName,
					password);

			connectRpcService.bindUser(fid, authUser.getId());

			userHelper.doLogon(request, response, authUser);

			return "redirect:"
					+ WebConstant.getLogonUrl(LoginSesionUtil.getUserTypeProjectName(),
							WebConstant.getSchoolIdsForCurrUser(authUser.getType()));
		} catch (BaseRuntimeException e) {
			request.setAttribute("errorMsg", e.getFriendlyMsg());
			request.setAttribute("fid", fid);
			request.setAttribute("nick", request.getParameter("nick"));
			request.setAttribute("avatar", request.getParameter("avatar"));
			request.setAttribute("loginName", request.getParameter("loginName"));
		}

		return "connector/bind";
	}

	@RequestMapping("bind2AnotherUser")
	public String bind2AnotherUser(HttpServletRequest request,
			HttpServletResponse response, int fid) {

		connectRpcService.bindUser(fid, LoginSesionUtil.getUserId());

		String referer = request.getParameter("referer");

		return "redirect:"
				+ (StringUtils.isNotBlank(referer) ? referer : WebConstant.getLogonUrl(
						LoginSesionUtil.getUserTypeProjectName(),
						WebConstant.getSchoolIdsForCurrUser(LoginSesionUtil.getUserType())));
	}

	@RequestMapping("unbindUser")
	public void unbindUser(HttpServletRequest request,
			HttpServletResponse response, ThirdLoginType loginType) {

		connectRpcService.unbind(LoginSesionUtil.getUserId(), loginType);

		WebUtils.writeJson(OperationResult.buildSuccessResult("操作成功！"), request, response);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @return 创建并绑定用户
	 */
	@RequestMapping("cbuser")
	public String cbUser(HttpServletRequest request,
			HttpServletResponse response) {

		String realName = request.getParameter("realName");
		String password = request.getParameter("password");

		request.setAttribute("showDiv", "cbDiv");
		request.setAttribute("avatar", request.getParameter("avatar"));
		request.setAttribute("nick", request.getParameter("nick"));
		request.setAttribute("fid", request.getParameter("fid"));
		if (StringUtils.isBlank(realName)) {
			request.setAttribute("errorMsg1", "姓名不能为空");
			return "connector/bind";
		}

		if (StringUtils.isBlank(password) || !password.equals(request.getParameter("retype"))) {
			request.setAttribute("errorMsg1", "密码不能为空或两次密码输入不一致！");
			return "connector/bind";
		}

		TblAuthUser authUser = new TblAuthUser();
		authUser.setId(LoginSesionUtil.getUserId());
		authUser.setRealName(realName);
		authUser.setPassword(password);
		authUser.setType(UserType.REG_STUDENT);
		try {
			userRpcService.save(authUser);

			authUser = userRpcService.getUserInfoById(authUser.getId());
			request.setAttribute("currUser", authUser);
			request.setAttribute("password", password);

			return "redirect:/user/reg/success?acc=" + authUser.getSignName() + "&pw=" + password;
		} catch (BaseRuntimeException e) {
			request.setAttribute("errorMsg1", e.getFriendlyMsg());
		}

		return "connector/bind";
	}

}

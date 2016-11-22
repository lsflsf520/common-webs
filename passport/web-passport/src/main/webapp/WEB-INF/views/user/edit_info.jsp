<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/global.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改资料</title>
<style>
.change-info-con ul li dl.sel-disable{
	background-color: #EEE;
	opacity: 1;
	cursor: not-allowed;
	background-image: none;
}
</style>
</head>

<body>
<!--wrapper start-->
<div class="wrapper">
	<!--header start-->
    <%@include file="../common/_header.jsp" %>
    <!--header end-->
    
    <!--change-tip start-->
    <div class="change-tip" id="chgTip" style="${tbStudent == null ? "" : "display:none;"}">
    	<div class="change-tip-con">请填写真实的个人信息，我们会根据你的实际情况定制个性化学习方案。我们将对你填写的信息严格保密。  </div>
    </div>
    <!--change-tip end-->
       
    <!--change-info start-->
    <div class="change-info">
    <div class="change-info-title">个人信息</div>
    <div class="change-info-con-preview" id="previewDiv" style='${preview ? "" : "display:none;"}'>
            <ul>
                <li style="text-align:center;">
                  <a class="header-pic1">
                    <img src='${commonResDomain}${base }${userIcon!=null && userIcon!="" ? userIcon : "/images/header-pic.png"}' width="108" height="108" id="pvw_headImg" alt="用户头像"/>
                  </a>
                </li>
                <li>
            		<span>卡号:</span><p id="pvw_card">${signName}</p>
            	</li>
                <li>
            		<span>姓名:</span><p id="pvw_name">${userName  }</p>
            	</li>
                <li>
            		<span>性别:</span><p id="pvw_sex">${tbStudent == null || tbStudent.sex==1 ? "男" : "女"}</p>
            	</li>
            	<li>
            		<span>手机:</span><p id="pvw_mobile">${tbStudent==null || tbStudent.phoneNum==null ? userInfo.mobile : tbStudent.phoneNum}</p>
            	</li>
            	<li>
            		<span>地区:</span><p id="pvw_area">${province.name}${city.name}${county.name}</p>
            	</li>
                <li>
            		<span>学校:</span><p id="pvw_school">${school.name}</p>
                </li>
                <li>
            		<span>入学时间:</span><p id="pvw_gradeyear">${tbStudent.gradeYear}年</p>
                </li>
                <li>
            		<span>班级:</span><p id="pvw_class">${tbClass.name}</p>
            	</li>
            	<c:if test="${gradeId != null && gradeId != 1 }">
            	  <li>
            		<span>文理科:</span><p id="pvw_stype">${tbStudent == null || tbStudent.sType==2 ? "理科" : "文科"}</p>
            	  </li>
            	</c:if>
                <li>
            		<span>学习程度:</span><p id="pvw_scoreSeg">${userScoreSegment}</p>
            	</li>
                <li>
            		<span>目标分数:</span><p id="pvw_aim_score">${tbStudent.target}分</p>
            	</li>
                <li>
            		<span>家长姓名:</span><p id="pvw_parent_name">${tbStudent.parentName}</p>
            	</li>
                <li>
            		<span>家长电话:</span><p id="pvw_parent_mobile">${tbStudent==null || tbStudent.parentTelephone==null ? userInfo.mobile : tbStudent.parentTelephone}</p>
            	</li>
                <li>
            		<span>座右铭:</span><p id="pvw_motto">${tbStudent.motto }</p>
            	</li>
                 <li><button class="edit-btn" type="button" onclick="$('#previewDiv').css('display', 'none');$('#chgTip').css('display', 'block');$('#editDiv').css('display', 'block');">编辑资料</button></li>
            </ul>
        </div>
        
    <div class="change-info-con" id="editDiv" style='margin-top:40px;${!preview ? "" : "display:none;"}'>
    	<ul>
        	<li style="text-align:center;">
            	<a href="javascript:void(0)" onclick="javascript:globalJs.showDiv('popDiv');" class="header-pic1">
            	  <img id="userIcon" src='${commonResDomain}${base }${userIcon!=null && userIcon!="" ? userIcon : "/images/header-pic.png"}' width="108" height="108" alt="用户头像" />
            	  <i>编辑头像</i>
            	</a>
            </li>
        	<li>
            	<span><i></i>卡号:</span><input type="text" name="cardNum" id="cardNum" readonly class="form-control" value="${signName}" />
            </li>
            <li id="realNameArea">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>姓名:</span><input type="text" id="realName" ${!canClose  ? "" : "readonly"} name="realName" value="${userName  }" class="form-control"/>
            </li>
            <li class="red-tip1" style="display:none" id="realNameWarning">姓名为2到10个汉字</li>
            <li class="radio-style" id="sex">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>性别:</span>
            	<a href="javascript:void(0)" onclick="user_info_modify.chooseSex(this)" name="1"
            	<c:if test="${tbStudent == null || tbStudent.sex==1}">
            	  class="selectd"
            	</c:if>            	
            	 >
            	 男
            	 </a>
            	
            	<a href="javascript:void(0)" onclick="user_info_modify.chooseSex(this)" name="0"
            	<c:if test="${tbStudent.sex==0}">
            	  class="selectd"
            	</c:if>
            	>
            	女
            	</a>
            </li>
            <li id="telePhoneArea">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>手机:</span><input type="text" id="telePhone" name="telePhone" value="${tbStudent==null || tbStudent.phoneNum==null ? userInfo.mobile : tbStudent.phoneNum}" class="form-control" />
            </li>
            <li class="red-tip1" style="display:none" id="telePhoneWarning">请输入正确的手机号码</li>
            <li>
            	<span><i></i>QQ:</span><input type="text" name="qq" class="form-control" value="${tbStudent.qqNumber}" />
            </li>
            <li class="red-tip1" style="display:none" id="qqWarning">QQ号格式不正确！</li>
            <li id="proviceArea">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>地区:</span>
                <dl class='${!canClose  ? "" : "sel-disable"} select select-box' style="width:120px; float:left; margin-right:5px;">
                     <dt class="f1" id="province" >${province.name}<input type="hidden" value="${province.id}"/></dt>
                   <c:if test="${!canClose  }">
                     <dd>
                          <ul style="width:210px; overflow-y:auto; overflow-x:hidden;">
							<c:forEach items="${provinceList}" var="province">
									<li><a href="javascript:void(0)" onclick="user_info_modify.chooseProvince(this)">${province.name}<input type="hidden" value="${province.id}"/></a></li>
							</c:forEach>
                          </ul>
                      </dd>
                    </c:if>
                </dl>
                <dl class='${!canClose  ? "" : "sel-disable"} select select-box' style="width:110px; float:left; margin-right:5px;">
                     <dt class="f1" id="city">${city.name}<input type="hidden" value="${city.id}"/></dt>
                   <c:if test="${!canClose  }">
                     <dd>
                          <ul style="width:210px; overflow-y:auto; overflow-x:hidden;">
                          	<c:forEach items="${cityList}" var="city">
									<li>
										<a href="javascript:void(0)" onclick="user_info_modify.chooseCity(this)">
										${city.name}
										<input type="hidden" value="${city.id}"/>										
										</a>
									</li>                          	
                          	</c:forEach>
                          </ul>
                      </dd>
                    </c:if>
                </dl>
                <dl class='${!canClose  ? "" : "sel-disable"} select select-box' style="width:100px; float:left;">
                     <dt class="f1" id="county">${county.name}<input type="hidden" value="${county.id}"/></dt>
                   <c:if test="${!canClose  }">
                     <dd style=" left:-50px;">
                          <ul style="width:170px; overflow-y:auto; overflow-x:hidden;">
                          	<c:forEach items="${countyList}" var="county">
									<li>
										<a href="javascript:void(0)" onclick="user_info_modify.chooseCounty(this)">
										${county.name}
										<input type="hidden" value="${county.id}"/>
										</a>
									</li>                          	
                          	</c:forEach>                          
                          </ul>
                      </dd>
                    </c:if>
                </dl>
            </li>
            <li id="schoolArea">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>学校:</span>
                <dl class='${!canClose  ? "" : "sel-disable"} select select-box'>
                     <dt class="f1" id="school">${school.name}<input type="hidden" value="${school.id}"/></dt>
                   <c:if test="${!canClose  }">
                     <dd>
                          <ul>
                          	<c:forEach items="${schoolList}" var="school">
									<li>
										<a href="javascript:void(0)" onclick="user_info_modify.chooseSchool(this)">
										${school.name}
										<input type="hidden" value="${school.id}"/>										
										</a>
									</li>                          	
                          	</c:forEach>                          
                          </ul>
                      </dd>
                    </c:if>
                </dl>
            </li>
            <li class="red-tip1" style="display:none" id="schoolWarning">请选择你的学校</li>                
            <li id="gradeYearArea">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>入学时间</span>
                <dl class='${!canClose  ? "" : "sel-disable"} select select-box'>
                     <dt class="f1" id="gradeYear">${tbStudent.gradeYear}<input type="hidden" value="${tbStudent.gradeYear}"/></dt>
                   <c:if test="${!canClose  }">
                     <dd>
                          <ul>
                           <c:if test="${currentMonth <= 6 }">
                          	<li><a href="javascript:void(0)" onclick="user_info_modify.chooseGradeYear(this)">${currentYear-3}<input type="hidden" value="${currentYear-3}"/></a></li> 
                           </c:if>
                          	<li><a href="javascript:void(0)" onclick="user_info_modify.chooseGradeYear(this)">${currentYear-2}<input type="hidden" value="${currentYear-2}"/></a></li>
                            <li><a href="javascript:void(0)" onclick="user_info_modify.chooseGradeYear(this)">${currentYear-1}<input type="hidden" value="${currentYear-1}"/></a></li>
                            <li><a href="javascript:void(0)" onclick="user_info_modify.chooseGradeYear(this)">${currentYear}<input type="hidden" value="${currentYear}"/></a></li>
                          </ul>
                      </dd>
                    </c:if>
                </dl>
            </li>
            <li class="red-tip1" style="display:none" id="gradeYearWarning">请选择你的入学时间</li>            
            <li id="clazzArea">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>班级:</span>
                <dl class='${!canClose  || canEditClass ? "" : "sel-disable"} select select-box'>
                     <dt class="f1" id="clazz" onchange="user_info_modify.chooseClass(this)">
                     ${tbClass.name}
                     <input type="hidden" value="${tbClass.id}"/>
                     </dt>
                   <c:if test="${!canClose  || canEditClass}">
                     <dd>
                          <ul>
                          	<c:forEach items="${classList}" var="clazz">
									<li>
										<a href="javascript:void(0) onclick='user_info_modify.chooseClass(this)'">
										${clazz.name}
										<input type="hidden" value="${clazz.id}"/>										
										</a>
									</li>                          	
                          	</c:forEach>                           	
                          </ul>
                      </dd>
                    </c:if>
                </dl>
            </li>
            <li class="red-tip1" style="display:none" id="classWarning">请选择你的班级</li>
            <li class="radio-style" id="chooseSubject" style='display:${gradeId == null || gradeId != 1 ? "block" : "none"}'>
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>文理科:</span>
            	<a href="javascript:void(0)" id="sType1" onclick="user_info_modify.chooseStype(this)" name="1"
            	<c:if test="${tbStudent.sType==1}">
            	class="selectd"
            	</c:if>
            	style='${(!canClose || canEditClass) ||  tbStudent.sType==1 ? "" : "display:none" }'
            	>文科</a>
            	<a href="javascript:void(0)" id="sType2" onclick="user_info_modify.chooseStype(this)" name="2"
            	<c:if test="${tbStudent == null || tbStudent.sType==2}">
            	  class="selectd"
            	</c:if>
            	style='${(!canClose || canEditClass) || (tbStudent == null || tbStudent.sType==2) ? "" : "display:none" }'
            	>理科</a>
            </li>
            <li id="scoreSegArea">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>学习程度:</span>
            	
                 <dl id="scoreSegDl" class='${!canClose || scoreSegment == null ? "" : "sel-disable"} select select-box'>
                     <dt class="f1" id="scoreSegment">${userScoreSegment}<input type="hidden" value="${scoreSegment}"/></dt>
                   <c:if test="${!canClose || scoreSegment == null}">
                     <dd>
                          <ul id="scoreSegmentUl">
                    <c:choose>
                   <c:when test="${scoreSegList!=null}">
            	   <c:forEach items="${scoreSegList}" var="scoreSegment" varStatus="status">
                          		<li><a href="javascript:void(0)" onclick="user_info_modify.chooseScoreSegment(this)">
                          		${scoreSegment}
                          		<input type="hidden" value="${status.index}"/>
                          		</a>
                          		</li>
                    </c:forEach>
                    </c:when>
                   <c:otherwise>        
                          	<li><a href="javascript:void(0)" onclick="user_info_modify.chooseScoreSegment(this)">
                          		（学沫）40分以下<input type="hidden" value="0"/>
                          		</a>
                          		</li>
                          	<li><a href="javascript:void(0)" onclick="user_info_modify.chooseScoreSegment(this)">
                          		（学弱）40~80分<input type="hidden" value="1"/>
                          		</a>
                          		</li>
                          	<li><a href="javascript:void(0)" onclick="user_info_modify.chooseScoreSegment(this)">
                          		（学民）80~100分<input type="hidden" value="2"/>
                          		</a>
                          		</li>
                          	<li><a href="javascript:void(0)" onclick="user_info_modify.chooseScoreSegment(this)">
                          		（学霸）100~120分<input type="hidden" value="3"/>
                          		</a>
                          		</li>
                          	<li><a href="javascript:void(0)" onclick="user_info_modify.chooseScoreSegment(this)">
                          		（学神）120分以上<input type="hidden" value="4"/>
                          		</a>
                          		</li> 	
                  </c:otherwise>
                  </c:choose>     	 
   </ul>
                      </dd>
                   </c:if>
                  </dl>
                  
                  <!-- 
                  <c:if test="${tbStudent.scoreSagmentStatus != 1}">
                 </c:if>
                 <c:if test="${tbStudent.scoreSagmentStatus == 1}">
                   <dl class="select select-box">
                     <dt class="f1" id="scoreSegment" title="为了给你提供更准确的个性化学习服务根据你的学习反馈数据，系统已自动修正你的学习程度">${userScoreSegment}<input type="hidden" value="${scoreSegment}"/></dt>
                   </dl>
                 </c:if>
                  -->
            </li>
            <li class="red-tip1" style="display:none" id="scoreSegmentWarning">请选择你的学习程度</li>
            <li id="parentNameArea">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>联系人:</span><input type="text" name="parentName" id="parentName" value="${tbStudent.parentName}" class="form-control" />
            </li>
            <li class="red-tip1" style="display:none" id="parentNameWarning">联系人必须为2到10个汉字</li>
            <li id="parentPhoneArea">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>联系人电话:</span><input type="text" name="parentPhone" id="parentPhone" class="form-control" value="${tbStudent==null || tbStudent.parentTelephone==null ? userInfo.mobile : tbStudent.parentTelephone}"/>
            </li>
            <li class="red-tip1" style="display:none" id="parentPhoneWarning">请输入你的联系人电话</li>
            <li id="targetArea">
            	<span><i><img src="${commonResDomain}${base}/images/asterisk.png" width="10" height="10" alt="星号"/></i>目标分数:</span><input type="text" name="targetScore" id="targetScore" class="form-control" value="${tbStudent.target}"/>
            </li>
             <li class="red-tip1" style="display:none" id="targetScoreWarning">请输入你要提高的分数</li>
            <li><span><i></i>座右铭:</span><textarea class="form-control" id="motto" value="${tbStudent.motto}">${tbStudent.motto}</textarea></li>
            <li class="red-tip1" style="display:none" id="mottoWarning">座右铭长度不能大于100个字符</li>
            <li>
             <button onclick='user_info_modify.submitOld()' type="button" style="margin-left:150px;" id="saveBtn" class="save-btn">保存</button>
             <button onclick="jumpTo('javascript:location.reload();')" type="button" style="height: 45px; padding: 0px 50px; background-color: rgb(255, 255, 255); border: 1px solid rgb(153, 153, 153);border-radius: 6px;margin-left: 10px;">取消</button>
            </li>
        </ul>
    </div>
    </div>
    <!--change-info end-->
    
    <!--account-bound start-->
    <%-- <div class="account-bound">
    	<div class="change-info-title">账号绑定</div>
        <div class="account-bound-box">
        	<div class="account-bound-box-title">
            	<span class="icon1"><img src="${commonResDomain}${base}/images/mail.png" width="32" height="22" alt="邮箱" /></span> <span >邮箱：<p style="display:inline-block;" id="showEm">${email != null ? email : "未绑定" }</p></span>
            	 <a href="JavaScript:void(0)" id="emOperBtn" onclick="toggleEmail('${email != null ? "email_bind_div1" : "email_bind_div"}')">${email != null ? "修改" : "绑定" }</a>
            </div>
            <div id="email_bind_div" class="account-bound-box-con" >
            	<ul class="tel-box">
                	<li><span>邮箱：</span><input type="text" id="email" name="email" value="${email }" placeholder="目前只支持QQ邮箱"><a href="javascript:user_info_modify.sendEmailCode($('#email').val(), '#errEmTip', '#goEm', true);" class="a-style">获取验证码</a></li>
                    <li id="errEmTip" class="red-tip"></li>
                    <li id="goEm" style="display:none;"><a href="http://mail.qq.com" target="_blank">前往邮箱查看</a></li>
                    <li><span>验证码：</span><input type="text" id="emailCode" ></li>
                    <li id="cdTip" class="red-tip"></li>
                    <li><button style="margin-top:0;" type="button" class="save-btn" onclick="user_info_modify.bindEmail($('#email').val(), $('#emailCode').val(), '#errEmTip', '#cdTip');">提交</button></li>
                </ul>
            </div>
            <div id="email_bind_div_s" class="account-bound-box-con">
            	<ul class="tel-box">
                	<li>恭喜你！邮箱绑定成功</li>
                    <li><button type="button" onclick="toggleEmail('email_bind_div_s')" class="save-btn">确定</button></li>
                </ul>
            </div>
            
            <div id="email_bind_div1" class="account-bound-box-con">
            	<ul class="tel-box">
                	<li class="m-bottom">
                    	<div class="tel-step green"><i>1</i>验证身份 ---------</div><div class="tel-step"><i>2</i>修改绑定邮箱 ---------</div><div class="tel-step"><i>3</i>修改完成</div>
                    </li>
                    <li><span>验证方式：</span>
                    	 <dl class="select select-box1">
                             <dt class="f1">邮箱验证</dt>
                             <dd>
                                  <ul>
                                    <li><a href="javascript:void(0);">邮箱验证</a></li>
                                  </ul>
                              </dd>
                            </dl>
                    </li>
                	<li><span>原邮箱：</span><div id="preEmail" class="phonenumber">${email }</div><a  href="javascript:user_info_modify.sendEmailCode(null, '#errEmailTip', '#goEmail');" class="a-style">获取验证码</a></li>
                    <li id="errEmailTip" class="red-tip"></li>
                    <li id="goEmail" style="display:none;"><a href="http://mail.qq.com" target="_blank">前往邮箱查看</a></li>
                    <li><span>验证码：</span><input type="text" id="emailCode1" name=""></li>
                    <li id="errEmailCodeTip" class="red-tip"></li>
                    <li><button style="margin-top:0;" class="save-btn" onclick="user_info_modify.checkEmailAvail($('#emailCode1').val(), '#errEmailCodeTip');">下一步</button></li>
                </ul>
            </div>
            
            <div id="email_bind_div2" class="account-bound-box-con">
            	<ul class="tel-box">
                	<li class="m-bottom">
                    	<div class="tel-step green"><i>1</i>验证身份 ---------</div><div class="tel-step green"><i>2</i>修改绑定邮箱 ---------</div><div class="tel-step"><i>3</i>修改完成</div>
                    </li>
                	<li><span>新邮箱：</span><input type="text" id="newEmail" name="" placeholder="目前只支持QQ邮箱"><a href="javascript:user_info_modify.sendEmailCode($('#newEmail').val(), '#newEmTip', '#goEmail2', true);" class="a-style">获取验证码</a></li>
                    <li id="newEmTip" class="red-tip"></li>
                    <li id="goEmail2" style="display:none;"><a href="http://mail.qq.com" target="_blank">前往邮箱查看</a></li>
                    <li><span>验证码：</span><input type="text" id="emCode2" name=""></li>
                    <li id="newCdTip" class="red-tip"></li>
                    <li><button style="margin-top:0;" class="save-btn" onclick="user_info_modify.bindEmail($('#newEmail').val(), $('#emCode2').val(), '#newEmTip', '#newCdTip');">提交</button></li>
                </ul>
            </div>
            
            <div id="email_bind_div3" class="account-bound-box-con">
            	<ul class="tel-box">
                	<li class="m-bottom">
                    	<div class="tel-step green"><i>1</i>验证身份 ---------</div><div class="tel-step green"><i>2</i>修改绑定邮箱 ---------</div><div class="tel-step green"><i>3</i>修改完成</div>
                    </li>
                	<li>恭喜你！邮箱绑定成功</li>
                    <li><button type="button" onclick="toggleEmail('email_bind_div3')" class="save-btn">确定</button></li>
                </ul>
            </div>
            
        </div>
        
        <div class="change-info-title">会员等级</div>
        <div class="account-bound-box">
        	<div class="account-bound-box-title">
            	<span class="icon1"><img src="${commonResDomain}${base}/images/upgrade.png" width="32" height="22" alt="会员" /></span> <span >当前会员等级：<p style="display:inline-block;" id="showTCLevel">${taocanLevelStr }</p></span>
            	 <c:if test='${cardCode == null || "D" != cardCode }'>
            	  <a href="JavaScript:void(0)" id="tcLevelOperBtn" onclick='globalJs.showDiv("upgradePopDiv")'>升级</a>
            	 </c:if>
            	 <c:if test='${cardCode != null}'>
            	  <a href="JavaScript:void(0)" id="continueFeeOperBtn" style="margin-right:10px" onclick='globalJs.showDiv("continueFeePopDiv")'>续费</a>
            	 </c:if>
            </div>
        </div>
    </div> --%>
    <!--account-bound end-->
    
</div>
<!--wrapper end-->

<div id="popDiv" class="choose-avatar" style="display:none;">
     <div class="choose-avatar-title">选择头像</div>
     <div class="choose-avatar-con" id="editIconDiv">
     	<a href="javascript:void(0)" onclick="user_info_modify.chooseIcon(this)" class="selected"><img src="${commonResDomain}${base}/images/header-pic.png" width="67" height="67" alt="用户头像" /></a>
     	<a href="javascript:void(0)" onclick="user_info_modify.chooseIcon(this)"><img src="${commonResDomain}${base}/images/header-pic1.png" width="67" height="67" alt="用户头像" /></a>
     	<a href="javascript:void(0)" onclick="user_info_modify.chooseIcon(this)"><img src="${commonResDomain}${base}/images/header-pic2.png" width="67" height="67" alt="用户头像" /></a>
     	<a href="javascript:void(0)" onclick="user_info_modify.chooseIcon(this)"><img src="${commonResDomain}${base}/images/header-pic3.png" width="67" height="67" alt="用户头像" /></a>
     	<a href="javascript:void(0)" onclick="user_info_modify.chooseIcon(this)"><img src="${commonResDomain}${base}/images/header-pic4.png" width="67" height="67" alt="用户头像" /></a>
        <a href="javascript:void(0)" onclick="user_info_modify.chooseIcon(this)"><img src="${commonResDomain}${base}/images/header-pic5.png" width="67" height="67" alt="用户头像" /></a>
        <a href="javascript:void(0)" onclick="user_info_modify.chooseIcon(this)"><img src="${commonResDomain}${base}/images/header-pic6.png" width="67" height="67" alt="用户头像" /></a>
        <a href="javascript:void(0)" onclick="user_info_modify.chooseIcon(this)"><img src="${commonResDomain}${base}/images/header-pic7.png" width="67" height="67" alt="用户头像" /></a>
        <a href="javascript:void(0)" onclick="user_info_modify.chooseIcon(this)"><img src="${commonResDomain}${base}/images/header-pic8.png" width="67" height="67" alt="用户头像" /></a>
        <a href="javascript:void(0)" onclick="user_info_modify.chooseIcon(this)"><img src="${commonResDomain}${base}/images/header-pic9.png" width="67" height="67" alt="用户头像" /></a>
     </div>
     <div class="choose-avatar-bottom">
     	<a href="javascript:void(0)" class="selected" onclick="user_info_modify.confirmIcon()">确定</a><a href="javascript:void(0)" onclick="user_info_modify.hideEditIcon()">取消</a>
     </div>
</div>
    
 <div id="upgradePopDiv" class="upgrade-box" style="display:none;">
  <div class="upgrade-box-title">升级<a href="javascript:globalJs.closeDiv('upgradePopDiv')">&times;</a></div>
  <div class="upgrade-box-con">
  	<ul>
    	<li style="line-height:30px;">当前会员等级：${taocanLevelStr }</li>
    	<c:if test='${cardCode == null}'>
    	  <li>可以升级到：银牌速学客、金牌魔数师、钻石易数家</li>
    	</c:if>
    	<c:if test='${cardCode == "S"}'>
    	  <li>可以升级到：金牌魔数师、钻石易数家</li>
    	</c:if>
    	<c:if test='${cardCode == "G"}'>
    	  <li>可以升级到：钻石易数家</li>
    	</c:if>
        
        <li><input type="text" class="form-control selected" id="actCode" placeholder="请输入激活码"/></li>
        <li class="error-tip" id="errorLi"></li>
        <li><a href="javascript:upgrade();" id="applyBtn">确认</a></li>
        <li class="upgrade-tip">如果没有激活码请到创数教育代理商处购买升级激活码。</li>
    </ul>
  </div>
</div>

<div id="continueFeePopDiv" class="upgrade-box" style="display:none;">
  <div class="upgrade-box-title">续费<a href="javascript:globalJs.closeDiv('continueFeePopDiv')">&times;</a></div>
  <div class="upgrade-box-con">
  	<ul>
    	<li style="line-height:30px;">当前会员等级：${taocanLevelStr }</li>
        <li>续费即可继续享受该等级会员服务。</li>
        <li><input type="text" class="form-control selected" id="conActCode" placeholder="请输入激活码"/></li>
        <li class="error-tip" id="conErrorLi"></li>
        <li><a href="javascript:continueFee();" id="conApplyBtn">确认</a></li>
        <li class="upgrade-tip">如果没有激活码请到创数教育代理商处购买续费激活码。</li>
    </ul>
  </div>
</div>

<!-- 
<div id="successPopDivS" class="upgrade-box" style="display:none;">
  <div class="upgrade-box-con1">
  	<ul id="Sul">
    	<li><img src="${commonResDomain}${base}/images/silver-medal1.png" width="181" height="215" id="animImg" /></li>
    	<li class="f-weight" id="typeName">银牌速学客</li>
        <li id="successTipS">恭喜您，升级成功！</li>
        <li><a href="${studentIndex }">点击进入</a></li>
    </ul>
  </div>
</div>
 -->
 
<div id="successPopDiv" class="upgrade-box" style="display:none; background-color:#4499ee;">
  <div class="upgrade-box-con1">
  	<ul>
    	<li><img src="${commonResDomain}${base}/images/silver.gif" width="310" height="339" id="animImg"/></li>
        <li id="successTip">恭喜您，升级成功！</li>
        <li><a href="${studentIndex }">点击进入</a></li>
    </ul>
  </div>
</div>

</body>
</html>

<script type="text/javascript" src="${projectResDomain}${base}/js/user/edit_info.js?v=${buildVersion}"></script>
<script type="text/javascript">
var baseDataDomain = "${baseDataDomain}";
var score_common = new Array();
var score_jiangsu_wen = new Array();
var score_jiangsu_li = new Array();
<%
String[] SCORE_SAGMENT_COMMON_STR = (String[])request.getAttribute("SCORE_SAGMENT_COMMON_STR");
//String[] SCORE_SAGMENT_COMMON_STR = (String[])request.getAttribute("SCORE_SAGMENT_BASE_STR");
String[] SCORE_SAGMENT_JIANGSU_1_STR = (String[])request.getAttribute("SCORE_SAGMENT_JIANGSU_1_STR");
String[] SCORE_SAGMENT_JIANGSU_2_STR = (String[])request.getAttribute("SCORE_SAGMENT_JIANGSU_2_STR");
for(int i=0;i<SCORE_SAGMENT_COMMON_STR.length;i++)
{%>
score_common[<%=i%>] = "<%=SCORE_SAGMENT_COMMON_STR[i]%>";
score_jiangsu_wen[<%=i%>] = "<%=SCORE_SAGMENT_JIANGSU_1_STR[i]%>";
score_jiangsu_li[<%=i%>] = "<%=SCORE_SAGMENT_JIANGSU_2_STR[i]%>";
<%}%>

/* $(function(){
	$(".select").each(function(){
		var s=$(this);
		var z=parseInt(s.css("z-index"));
		var dt=$(this).children("dt");
		var dd=$(this).children("dd");
		var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
		var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
		dt.click(function(){dd.is(":hidden")?_show():_hide();});
		dd.find("a").click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的"value"值 ）
		$("body").click(function(i){ !$(i.target).parents(".select").first().is(s) ? _hide():"";});
	})
}) */
</script>
<!--弹出框的js-->
<script type="text/javascript">
    var hasStdInfo = ${tbStudent != null ? true : false}; 
    function jumpTo(href){
    	if(hasComplete()){
    		location.href = href;
    	}else{
    		//$("#jumpA").attr("href", href);
    		//globalJs.showDiv("noSavePopDiv");
    		globalJs.confirm("个人修改信息还未保存，是否继续编辑？", null, null, href);
    	}
    }
    
    function hasComplete(){
    	return $('#editDiv').css('display') == 'none';
    }
    
    var homeHref = $("#homeA").attr("href");
    $("#homeA").click(function(){
    	jumpTo(homeHref);
    });
    /* $("#goback").click(function(){
    	jumpTo("javascript:history.go(-1)");
    }); */
    $("#editLi").css("display", "none");
    var pwdHref = $("#pwdLi > a").attr("href");
    $("#pwdLi").click(function(){
    	jumpTo(pwdHref);
    });
    
    $("#homeA").attr("href", "javascript:void(0);");
   // $("#goback").attr("href", "javascript:void(0);");
    $("#pwdLi > a").attr("href", "javascript:void(0);");
</script>
<script language="javascript" type="text/javascript">
    function toggle(div_id){
    	var divCss = $("#"+div_id).css("display");
    	$(".account-bound-box-con").css("display", "none");
    	if(divCss == "block"){
    	    $("#"+div_id).css("display", "none");
    	}else{
    		$("#"+div_id).css("display", "block");
    	}
    }
	function toggleQQ(div_id){
		toggle(div_id);
		var text = $("#qqoperBtn").text();
		if("解除绑定" == text){
			$("#qqoperBtn").text("取消");
		}else{
			$("#qqoperBtn").text("解除绑定");
			$("#"+div_id).css("display", "none");
		}
	}
	
	function toggleEmail(div_id){
		toggle(div_id);
		var text = $("#emOperBtn").text();
		if("修改" == text || "绑定" == text){
			$("#emOperBtn").text("取消");
			$("#goEm").css("display", "none");
		}else if("email_bind_div" == div_id){
			$("#emOperBtn").text("绑定");
			$("#"+div_id).css("display", "none");
		}else{
			$("#emOperBtn").text("修改");
			$("#"+div_id).css("display", "none");
		}
	}
	
	$(function(){
		user_info_modify.init();
	});
</script>
<script>
function upgrade(){
	/* if(!hasComplete()){
		alert("为了确保你能获得更适合自己的学习方案，请先提供真实的个人信息。");
		return;
	} */
	if($("#applyBtn").hasClass("disableA")){
		globalJs.alert("请求正在处理，请不要重复点击");
		return;
	}
	var actCode = $("#actCode").val();
	if(/^\s*$/.test(actCode)){
	    $("#errorLi").text("激活码不能为空！");
		return;
	}
	$("#errorLi").text("");
	$("#applyBtn").addClass("disableA");
	$.ajax({
	    url : "${base }/funccard/upgrade",
	    type: "POST",
	    data : {actCode : actCode},
	  	success:function(data){
	  		$("#applyBtn").removeClass("disableA");
			if(data && data.type &&  data.type != "success"){
				$("#errorLi").text(data.message);
				return;
			}
			//$("#typeName").text(data.userdata.desc);
			$("#showTCLevel").text(data.userdata.desc);
			$("#animImg").attr("src", "${commonResDomain}${base}" + data.userdata.animationImg);
			if(data.userdata.cardCode && "D" == data.userdata.cardCode){
			  $("#tcLevelOperBtn").css("display", "none");
			}
			/* $("#tcLevelOperBtn").removeAttr("onclick");
			$("#tcLevelOperBtn").click(function(){
			  showDiv("continueFeePopDiv");
			});
			$("#tcLevelOperBtn").text("续费"); */
			$("#successTip").text("恭喜您，升级成功！");
			globalJs.closeDiv("upgradePopDiv");
			globalJs.showDiv("successPopDiv");
	  	}
	});
}

function continueFee(){
	/* if(!hasComplete()){
		alert("为了确保你能获得更适合自己的学习方案，请先提供真实的个人信息。");
		return;
	} */
	if($("#conApplyBtn").hasClass("disableA")){
		globalJs.alert("请求正在处理，请不要重复点击");
		return;
	}
	var actCode = $("#conActCode").val();
	if(/^\s*$/.test(actCode)){
	    $("#conErrorLi").text("激活码不能为空！");
		return;
	}
	$("#conErrorLi").text("");
	$("#conApplyBtn").addClass("disableA");
	$.ajax({
	    url : "${base }/funccard/continueFee",
	    type: "POST",
	    data : {actCode : actCode},
	  	success:function(data){
	  		$("#conApplyBtn").removeClass("disableA");
			if(data && data.type &&  data.type != "success"){
				$("#conErrorLi").text(data.message);
				return;
			}
			//$("#typeName").text(data.userdata.desc);
			$("#showTCLevel").text(data.userdata.desc);
			$("#animImg").attr("src", "${commonResDomain}${base}" + data.userdata.animationImg);
			$("#successTip").text("恭喜您，续费成功！");
			globalJs.closeDiv("continueFeePopDiv");
			globalJs.showDiv("successPopDiv");
	  	}
	});
}

</script>

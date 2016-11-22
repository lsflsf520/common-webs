<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8"%>
<style>
/*头部*/
.header {
	width:100%;
	height:80px;
	display:block;
	background-image: url(${commonResDomain}${base}/images/border-bottom.png);
	background-repeat: repeat-x;
	background-position: left bottom;
	background-color: #fff;
	padding:0;
}
.logo {
	height:69px;
	float:left;
	margin:0 10px 0 0;
}
.logo a {
	height:69px;
	display:inline-block;
	padding:0 15px;
	margin-top:0;
	background-color:#66a7ff;
}
.logo a img {
	float:left;
	margin-top:12px;
}
/*返回*/
.return {
	height:67px;
	font-size:20px;
	float:left;
	color:#66a7ff;
}
.return a {
	color:#66a7ff;
	display:inline-block;
	line-height:67px;
}
.return a img {
	float:left;
	margin:20px 5px 0 0;
}
/*标题*/
.title {
	display:block;
	text-align:center;
	font-size:20px;
	height:69px;
	line-height:69px;
}
/*用户*/
.user {
	float:right;
	height:67px;
	display:inline-block;
}
.user ul {
	float:right;
	margin-top:4px;
}
.user ul li {
	float:left;
	height:67px;
	line-height:55px;
	margin:0 10px;
}
.user ul li a{
	color:#333;
	display: inline-block;
}
/*用户头像*/
.user ul li.user-pic {
	margin-top:2px;
}
.user ul li.user-pic a{
	float:left;
}
.user ul li.user-pic img {
	border-radius:50%;
	height:55px;
	height:55px;
	background-color:#fcfcfc;
	border:1px solid #e3e3e3;
}
/*用户信息*/
.user ul li.user-info .select_index {
	border:0;
	height:67px;
	background-position: 88% 47px;
	background-image: url(${commonResDomain}${base}/images/select-icon.png);
	position:relative;
	display:inline-block;
	color:#333;
	background-repeat: no-repeat;
}
.user ul li.user-info .select_index dt{
	padding:0 30px 0 0;
	display:inline-block;
	cursor:pointer;
	white-space:nowrap;
	text-overflow:ellipsis;
	overflow:hidden;
	position:relative;
	z-index:99;
	line-height:33px;
	font-weight:normal;
	font-size: 16px
}
.user ul li.user-info .select_index dt span{
	display:block;
	font-family:"黑体";
	width:100%;
	height:25px;
}
.user ul li.user-info .select_index dt span.s1 {
	color:#777;
	font-size:16px;
	padding-top:3px;
}
.user ul li.user-info .select_index dt span.s2 {
	color:#aaa;
	font-size:14px;
}
.user ul li.user-info .select_index dd {
	position:absolute;
	left:-50px;
	top:60px;
	box-shadow:0 0 8px #d2ccc4;
	background:#fff;
	display:none;
	border-radius:5px;
	z-index:999;
}
.user ul li.user-info .select_index dd ul {
	width:120px;
	float:left;
	margin:0;
	max-height: 250px;
	overflow: auto;
}
.user ul li.user-info .select_index dd ul li{
	display:block;
	float:left;
	width:100%;
	margin:0;
	height:50px;
}
.user ul li.user-info .select_index dd ul li a {
	line-height:45px;
	display:block;
	font-size:16px;
	padding:0 8px;
	color:#333;
	text-align:left;
	text-indent:20px;
	border-top:1px solid #ebeff3;
}

</style>
<div class="header">
        <!--row start-->
        <div class="row">
            <div class="col-sm-4">
                <div class="logo"><a href="${studentIndex }" id="homeA"><img src="${commonResDomain}${base}/images/home.png" width="40" height="34"  alt="17大学"/></a></div>
               <c:if test="${canClose == null || (tbStudent != null && scoreSegment != null)}">
                <div class="return">
                  <a href='${backUrl == null ? "javascript:history.go(-1)" : backUrl }' id="goback">               
                    <img src="${commonResDomain}${base}/images/return_03.png" height="25"  alt="返回"/>返回
                  </a>
                </div>
               </c:if>
            </div>
            <div class="col-sm-4">
                <div class="title">${title}</div>
            </div>
            <div class="col-sm-4">
                <!--user start-->
                <div class="user">
                    <ul>
                        <li class="user-pic">                       
                        <a href="${passportDomain }/priv/info/account">
            			   <img src='${commonResDomain}${base }${userIcon!=null && userIcon!="" ? userIcon : "/images/header-pic.png"}' width="55" height="55" alt="用户头像" />
            	        </a>
                         </li>
                        <li><span style="width: 1px; height: 22px;float: left;margin-top: 18px; border-left: 1px solid #999;"></span></li>
                        <li>
                            <a style="margin-right:20px; font-size:16px;" href="javascript:logout()">退出</a>
                        </li>

                         
                        <%-- <li class="user-info">
                               <dl class="select_index" >
                                    <dt class="f1">
                                      <a href="javascript:showUserMenu()">
                                        <span class="s1" id="headerUserNm">${userName}</span><span class="s2" id="headerSchName">${currStdSchoolName}</span>
                                      </a>
                                    </dt>
                                    <dd>
                                        <ul>
                                            <li id="editLi"><a href="${passportDomain }/priv/info/edit" >修改资料</a></li>
                                            <li id="pwdLi"><a href="${passportDomain }/user/pwd/toresetpage" >修改密码</a></li> 
                                            <li><a href="javascript:logout()">退出</a></li>
                                        </ul>
                                    </dd>
                               </dl>
                        </li> --%>
                    </ul>
                </div>
                <!--user end-->
            </div>
        </div>
      </div>
<script>

function showUserMenu()
{
	if($(".select_index dd").is(":hidden"))
		{
			$(".user_info .f1").addClass(".cur"); 
			$(".select_index dd").slideDown(200);
		}
}

$(function(){
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
})

$(function(){
    $(".select_index").each(function(){
        var s=$(this);
        var z=parseInt(s.css("z-index"));
        var dt=$(this).children("dt");
        var dd=$(this).children("dd");
        var _show=function(){dd.slideDown(200);dt.addClass("cur");s.css("z-index",z+1);};   //展开效果
        var _hide=function(){dd.slideUp(200);dt.removeClass("cur");s.css("z-index",z);};    //关闭效果
        dt.click(function(){dd.is(":hidden")?_show():_hide();});
        dd.find("a").click(function(){_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的"value"值 ）
        $("body").click(function(i){ !$(i.target).parents(".select_index").first().is(s) ? _hide():"";});
    })
})

function logout(){
	 if (confirm("确认退出?"))  {
	 	//window.location.href="${base}/MainController/slogout";
		 window.location.href = "${passportDomain}/user/login/doLogout";
	 }
}
</script>


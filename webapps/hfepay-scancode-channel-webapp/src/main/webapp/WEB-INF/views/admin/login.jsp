<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<!-- saved from url=(0025)https://www.juhe.cn/login -->
<html lang="en-US">
<head>
<title>${channelName }运营系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="keywords" content="${channelName }运营系统">
<meta name="description" content="${channelName }运营系统">
<link type="text/css" href="${path }/resources/styles/reset.css" rel="stylesheet">
<link type="text/css" href="${path }/resources/styles/public.css" rel="stylesheet">
<link type="text/css" href="${path }/resources/styles/register.css" rel="stylesheet">
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>
<script src="${path}/resources/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<style>
#loginTips a{color:#FFF;}
</style>
</head>
<body>
	<div id="header">
		<div class="header">
		<input id="currentChannelInfoCode" value="${currentChannelInfo.channelNo }" type="hidden"/>
<%-- 			<h1 class="png_bg" style="background:url(${currentChannelInfo.indexTopImg}) 0 0 no-repeat;"></h1> --%>
			<img alt="官网" src="${currentChannelInfo.indexTopImg}" style="height:48px;position: absolute;left: 0;top: 30px;text-indent: -999em;">
		</div>
	</div>

    <div id="loginBanner">
        <div class="loginBanner">
            <form name="loginForm" id="login_form" method="post" action="${path }/index/dologin">
            	<h2>${channelName }运营系统</h2>
				<h2><div style="width: 4px;background-color: #d30b0b;float: left;">&nbsp;</div><div style="float: left;margin-left: 10px;">登录</div></h2>
				
				<div class="div_user" style="margin-top: 20px;"><span></span><input class="username" id="userName" name="userName" type="text" placeholder="请输入用户名"></div>
				<div class="div_pw"><span></span><input class="pw" name="password" id="passWord" type="password" placeholder="请输入密码"></div>
				<div style="padding-bottom:5px;margin-top: 20px;"><input class="login_btn" id="loginBtn" type="submit" value="登录"></div>
				<div style="line-height:30px; text-indent:5px;color:#FFF; background:#FD5583; font-family:&#39;Microsoft Yahei&#39; ;margin-top:3px; border-radius:3px; width:300px; display:none;" id="loginTips">&nbsp;</div>
				<c:if test="${err != null && err != ''}">
					<div class="div_err"><span>${err}</span></div>
				</c:if>
			</form>
	   </div>
	   <div id="bannerUl">
	       <ul>
	           <li style="background:#24dde5;">
	               <div class="banDivContent">
	                   <img style="width: 100%;height: 501px;" src="${currentChannelInfo.loginBackgroundImg }">
	               </div>
	           </li>
	       </ul>
	   </div>
	   <div class="hd"><ul class="on"></ul></div>
   </div>
<!-- footer start -->
<div id="footer" class="clear">
    <p>Copyright © 2016 ${currentChannelInfo.companyName }&nbsp;&nbsp;&nbsp;&nbsp;${currentChannelInfo.recordNumber }</p>
</div>
<!-- footer end -->
<script type="text/javascript">
jQuery(document).ready(function(){
	var path = window.location.host;
	var menuUrl=window.location.href.replace(window.location.search,'');//替换掉多余的参数
	var index = menuUrl.lastIndexOf(path)+path.length;
	var url = menuUrl.substr(index);
	if(url.indexOf("login") < 0){
		parent.window.location.reload();
		window.location.reload(); 
	}
	sessionStorage.clear();
});
</script>
</body>
</html>
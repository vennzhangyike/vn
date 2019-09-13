<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<html lang="zh-cmn-Hans">
<head>
<title>基本信息管理</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>

<body ontouchstart>

<div class="weui-cells">
	<%-- <div class="weui-cell">
		<div class="weui-cell__bd"><p>商户名称</p></div>
		<div class="weui-cell__ft">${info.merchantName }</div>
	</div> --%>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>姓名</p></div>
		<div class="weui-cell__ft">${info.name }</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>身份证号码</p></div>
		<div class="weui-cell__ft">${info.idCard }</div>
	</div>
	<%-- <div class="weui-cell">
		<div class="weui-cell__bd"><p>门店名称</p></div>
		<div class="weui-cell__ft">${info.shortName }</div>
	</div> --%>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>联系电话</p></div>
		<div class="weui-cell__ft">${info.phone }</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>身份证正面</p></div>
		<div class="weui-cell__ft">
		<c:choose> 
		  <c:when test="${empty info.idcardImg1}">   
		    	未上传
		  </c:when> 
		  <c:otherwise>   
		 		  已上传
		  </c:otherwise> 
		</c:choose> 
	</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>身份证反面</p></div>
		<div class="weui-cell__ft">
			<c:choose> 
			  <c:when test="${empty info.idcardImg2}">   
			    	未上传
			  </c:when> 
			  <c:otherwise>   
			    	已上传
			  </c:otherwise> 
			</c:choose> 
		</div>
	</div>
	<%-- <div class="weui-cell">
		<div class="weui-cell__bd"><p>门店地址</p></div>
		<div class="weui-cell__ft" style="width: 80%;">${info.address }</div>
	</div> --%>
	<%-- <div class="weui-cell">
		<div class="weui-cell__bd"><p>商户营业执照号</p></div>
		<div class="weui-cell__ft">${info.merchantLicense }</div>
	</div> --%>
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

</body></html>
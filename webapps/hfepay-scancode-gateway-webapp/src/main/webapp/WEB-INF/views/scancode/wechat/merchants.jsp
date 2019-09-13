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
<title>商户信息管理</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

<div class="weui-cells">
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>商户名称</p></div>
		<div class="weui-cell__ft">${merchant.merchantName }</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>服务电话</p></div>
		<div class="weui-cell__ft">${store.servicePhone }</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>店铺地址</p></div>
		<div class="weui-cell__ft">${store.storeAddress }</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>营业执照</p></div>
		<div class="weui-cell__ft">
			<c:choose> 
			  <c:when test="${empty store.merchantLicense}">   
			    	无
			  </c:when> 
			  <c:otherwise>   
			 		有
			  </c:otherwise> 
			</c:choose> 
		</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>店铺营业执照原件照片</p></div>
		<div class="weui-cell__ft">
			<c:choose> 
			  <c:when test="${empty store.merchantLicenseImg}">   
			    	未上传
			  </c:when> 
			  <c:otherwise>   
			 		已上传
			  </c:otherwise> 
			</c:choose> 
		</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>手持身份证在店铺收银台照</p></div>
		<div class="weui-cell__ft">
			<c:choose> 
			  <c:when test="${empty store.storePhotosImg}">   
			    	未上传
			  </c:when> 
			  <c:otherwise>   
			 		已上传
			  </c:otherwise> 
			</c:choose> 
		</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>本人与店铺门头合照</p></div>
		<div class="weui-cell__ft">
			<c:choose> 
			  <c:when test="${empty store.groupPhotoImg}">   
			    	未上传
			  </c:when> 
			  <c:otherwise>   
			 		已上传
			  </c:otherwise> 
			</c:choose> 
		</div>
	</div>
	<div class="weui-cell">
		<div class="weui-cell__bd"><p>店铺内景照</p></div>
		<div class="weui-cell__ft">
			<c:choose> 
			  <c:when test="${empty store.storeImg}">   
			    	未上传
			  </c:when> 
			  <c:otherwise>   
			 		已上传
			  </c:otherwise> 
			</c:choose> 
		</div>
	</div>
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js" type="text/javascript" charset="utf-8"></script>

</body></html>
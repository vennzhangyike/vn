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
<title>审核进度</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart class="applyBg">

<div class="register">
	<img src="${path}/resources/images/wechat/progress.png" height="90px" alt="审核进度">
	<h1 style="margin:0">审核进度</h1>
</div>

<ul class="timeline">
	<li>
		<div class="timeline-image">
			<img class="img-circle img-responsive" src="${path}/resources/images/wechat/progress1.png" alt="">
		</div>
		<div class="timeline-panel">
			<div class="timeline-heading"><!-- 是否有效：MERCHANT_STATUS_0("申请中", "0"),
	MERCHANT_STATUS_1("上级审核通过", "1"),
	MERCHANT_STATUS_2("上级审核拒绝", "2"),
	MERCHANT_STATUS_3("平台审核通过", "3"),
	MERCHANT_STATUS_4("平台审核拒绝", "4"),
	MERCHANT_STATUS_5("停用", "5"),
	MERCHANT_STATUS_6("民生审核中", "6"),
	MERCHANT_STATUS_7("民生审核拒绝", "7"),
	MERCHANT_STATUS_8("初始状态", "8"); -->
				<h4>1、申请人基本信息</h4>
			</div>
			<div class="timeline-body">
				<c:if test="${basic=='0' or basic=='1' or basic=='6'}">
					<p class="review">审核中</p>
				</c:if>
				<c:if test="${basic=='3' }">
				<p class="through">通过验证</p>
				</c:if>
				<c:if test="${basic=='5' }">
				<p class="through">商户被停用</p>
				</c:if>
				<c:if test="${basic=='2' or basic=='4' or basic=='7'}">
				<p class="not-through">未通过，<a href="apply1">重新验证</a></p>
				</c:if>
				<c:if test="${basic=='8'}">
					<p class="review">资料未完善，<a href="apply1">去完善</a></p>
				</c:if>
			</div>
		</div>
	</li>
	<li>
		<div class="timeline-image">
			<img class="img-circle img-responsive" src="${path}/resources/images/wechat/progress2.png" alt="">
		</div>
		<div class="timeline-panel">
			<div class="timeline-heading">
				<h4>2、申请人店铺信息</h4><!-- '门店状态 0 初始 1 待审核 2 审核中 3 审核通过 4审核拒绝', -->
			</div>
			<div class="timeline-body">
				<c:if test="${basic=='0' or basic=='1' or basic=='6'}">
					<p class="review">审核中</p>
				</c:if>
				<c:if test="${basic=='3' }">
				<p class="through">通过验证</p>
				</c:if>
				<c:if test="${basic=='5' }">
				<p class="through">商户被停用</p>
				</c:if>
				<c:if test="${basic=='2' or basic=='4' or basic=='7'}">
				<p class="not-through">未通过，<a href="apply1">重新验证</a></p>
				</c:if>
				<c:if test="${basic=='8'}">
					<p class="review">资料未完善，<a href="apply1">去完善</a></p>
				</c:if>
			</div>
		</div>
	</li>
	<li style="margin-bottom:15px">
		<div class="timeline-image">
			<img class="img-circle img-responsive" src="${path}/resources/images/wechat/progress3.png" alt="">
		</div>
		<div class="timeline-panel">
			<div class="timeline-heading">
				<h4>3、申请人结算信息</h4>
			</div>
			<div class="timeline-body">
				<c:if test="${basic=='0' or basic=='1' or basic=='6'}">
					<p class="review">审核中</p>
				</c:if>
				<c:if test="${basic=='3' }">
				<p class="through">通过验证</p>
				</c:if>
				<c:if test="${basic=='5' }">
				<p class="through">商户被停用</p>
				</c:if>
				<c:if test="${basic=='2' or basic=='4' or basic=='7'}">
				<p class="not-through">未通过，<a href="apply1">重新验证</a></p>
				</c:if>
				<c:if test="${basic=='8'}">
					<p class="review">资料未完善，<a href="apply1">去完善</a></p>
				</c:if>
			</div>
		</div>
	</li>
</ul>
<c:if test="${basic=='2'or basic=='4' or basic=='7'}">
<div class="not-through" style="text-align:center;font-size:18px;padding-top: 5px;">没通过原因:${errorMsg }</div>
</c:if>
<div class="weui-btn-area"  style="margin-bottom:20px">
	<a href="my" class="weui-btn weui-btn_primary">返回个人中心</a>
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

</body></html>
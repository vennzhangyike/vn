<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
	request.setAttribute("time", new Date().getTime());
%>
<html lang="zh-cmn-Hans">
<head>
<title>信用卡申请</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

<div class="weui-panel weui-panel_access creditCard">
	<div class="weui-panel__hd">信用卡申请</div>
	<div class="weui-panel__bd">
		<a id="cashier01" href="https://creditcard.cmbc.com.cn/online/mobile/index.aspx?tradeFrom=YX-NBMD&EnStr=6DD21A4E0F6B36396A17AD4595CCDC98&jg=619000002&jgEnStr=3C40400E1BC40391691969A5E8752961" class="weui-media-box weui-media-box_appmsg">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb weui-media-box__thumb2" src="${path}/resources/images/wechat/msyh.png" alt="民生银行">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title" style="color:#fff">民生银行</h4>
				<p class="weui-media-box__desc" style="color:#fff">信用卡</p>
				<p class="skz"><span class="yellow">热销中</span></p>
			</div>
		</a>
		<a id="cashier02" href="https://ccshop.cib.com.cn:8010/application/cardapp/cappl/ApplyCard/toSelectCard?id=71e14642d14e428b968af17cd7f8f335" class="weui-media-box weui-media-box_appmsg">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb weui-media-box__thumb2" src="${path}/resources/images/wechat/xyyh.png" alt="兴业银行">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title" style="color:#fff">兴业银行</h4>
				<p class="weui-media-box__desc" style="color:#fff">信用卡</p>
				<p class="skz"><span class="yellow"></span></p>
			</div>
		</a>
	</div>
	
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
</body></html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<html>
<head>
<title>扫码付款</title>
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="renderer" content="webkit">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=1.0">
<meta name="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta name="format-detection" content="email=no">
<link rel="stylesheet"
	href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet"
	href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
</head>
<body>

	<div
		style="background-color: #ffffff; margin-top: 10px; margin-left: 10px; margin-right: 10px; padding-top: 20px; padding-bottom: 20px">
		<c:if test="${empty returnInfo}">

		</c:if>
		<%-- <c:if test="${returnInfo.returnCode!='000000'&& not empty returnInfo}">
		<div align="center" style="margin-top: 10px;margin-bottom 10px;">${returnInfo.returnMsg}</div>
	</c:if> --%>
		<c:if test="${returnInfo.returnCode=='000000'}">
			<div align="center">商户名称：${returnInfo.merchantName}</div>
			<div align="center" style="font-size: 36px; margin-top: 15px;">
				<b>￥${actualPayCash}</b>
			</div>
			<c:if test="${discountCash!='0'}">
				<div class="tip"
					style="text-align: center; font-size: 16px; padding: 0px;">
					消费金额<span style="font-size: 24px; font-weight: bold;">${payCash}</span>元,优惠金额<span
						style="font-size: 24px; font-weight: bold;">${discountCash}</span>元
				</div>
			</c:if>
			<div id="qrcode" style="margin: 0 auto; margin-top: 20px;"
				align="center"></div>
			<div align="center" style="margin-top: 15px;">
				<img src="${path}/resources/images/${payCode }.png" height="35px">
			</div>
			<div class="tip" style="text-align: center;">
				<a href="sweepcollection">清除金额</a>
			</div>

			<div class="tip tradeNo">
				订单编号：${returnInfo.tradeNo}<span style="float: right;">等待付款</span>
			</div>
		</c:if>
	</div>
</body>
<form action="${path }/scan/pay/sweepcollection" method="post"
	id="subForm">
	<input type="hidden" value="ZFBSMZF" id="payCode" name="payCode" /> <input
		type="hidden" value="" id="money" name="money" />
</form>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}"
	type="text/javascript"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${path}/resources/scripts/qrcode/jquery.qrcode.js?rnd=${version}"
	type="text/javascript"></script>
<script src="${path}/resources/scripts/qrcode/qrcode.js?rnd=${version}"
	type="text/javascript"></script>
<script
	src="${path}/resources/scripts/qrcode/drawQrCode.js?rnd=${version}"
	type="text/javascript"></script>

<script type="application/javascript"
	src="${path}/resources/scripts/wechat/sweepPay.js?rnd=${version}"></script>
<script type="text/javascript">
	if ('${returnInfo.returnCode}' == '000000') {
		generateQRCode("canvas", 200, 200, '${returnInfo.payStr}');
		//获取网页中的canvas对象 
		var mycanvas1 = document.getElementsByTagName('canvas')[0];
		//将转换后的img标签插入到html中 
		var img = convertCanvasToImage(mycanvas1);
		$('canvas').hide()
		$('#qrcode').append(img);//imagQrDiv表示你要插入的容器id
	}
	var timeout = false; //启动及关闭按钮  
	function payResult() {
		if (timeout) {
			return;
		}
		$.ajax({
			type : "POST",
			url : basePath + "/scancode/payresult",
			data : {
				"tradeNo" : "${returnInfo.tradeNo}"
			},
			success : function(msg) {
				msg = JSON.parse(msg);
				if (msg.orderStatus == '01') {
					timeout = true;
					$(".tradeNo").find("span").text("交易成功");
					$.modal({
						title : "交易结果",
						text : "交易成功",
						buttons : [ {
							text : "继续收款",
							onClick : function() {
								//确认跳转
								location.href = "sweepcollection";
							}
						},

						]
					});
				} else if (msg.orderStatus == '02') {
					timeout = true;
					$(".tradeNo").find("span").text("交易失败");
				}
			}
		});
		setTimeout(payResult, 3000); //time是指本身,延时递归调用自己,3000为间隔调用时间,单位毫秒  
	}
	payResult();
</script>
</html>


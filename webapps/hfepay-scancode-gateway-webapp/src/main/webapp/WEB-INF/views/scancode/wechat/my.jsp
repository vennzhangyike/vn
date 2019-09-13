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
<title>我的</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache" />
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet"
	href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet"
	href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

	<div class="my_info">
		<div class="bg_box" style="display: none;">
			<img src="${path}/resources/images/wechat/d_face.png" alt="头像"
				class="b_face">
		</div>
		<div class="log_box" id="unlogin"
			style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
			<a <c:if test="${currentUser.identityFlag!='4' }">href="set"</c:if>
				class="s_face"><img
				src="${path}/resources/images/wechat/d_face.png"
				dsrc="${path}/resources/images/wechat/d_face.png" alt="头像"></a>
			<div class="loginName">
				<p>${name}</p>
				<p>${nickName}</p>
			</div>
			<a <c:if test="${currentUser.identityFlag!='4' }">href="set"</c:if>
				class="certification"> <img
				src="${path}/resources/images/wechat/certification.png"
				dsrc="${path}/resources/images/wechat/certification.png"
				height="18px" alt="已认证"> <c:choose>
					<c:when test="${currentUser.identityFlag=='4'}">   
			    	已认证
			  </c:when>
					<c:when test="${currentUser.identityFlag=='3' and status=='1'}">   
			    	已认证
			  </c:when>
					<c:otherwise>   
			    	未认证
			  </c:otherwise>
				</c:choose>
			</a>
		</div>
		<div class="info_box">
			<p class="txt_info">
				<a
					<c:if test="${currentUser.identityFlag!='4' }">href="balance"</c:if>>今日交易总额<strong
					id="oneUserChances"> <c:if test="${!empty balance  }">
							￥<fmt:formatNumber type="number"
								value="${balance le 0?0:balance }" pattern="0.00"
								maxFractionDigits="2" />
						</c:if> <c:if test="${empty balance  }">￥0.00</c:if>
				</strong></a>
			</p>
			<c:if test="${currentUser.identityFlag!='4' }">
				<p class="txt_info">
					<a href="withdrawlsbill">今日可提现金额<strong id="onelotteryTotal">
							<c:if test="${!empty todayTotal}">
								￥<fmt:formatNumber type="number"
									value="${todayTotal le 0?0:todayTotal }" pattern="0.00"
									maxFractionDigits="2" />
							</c:if> <c:if test="${empty todayTotal}">￥0.00</c:if>
					</strong></a>
				</p>
			</c:if>
		</div>
		<%-- <c:if test="${currentUser.identityFlag!='4' }"><!-- 收银员不显示 -->
		<a href="set" class="btn_set">设置</a>
	</c:if> --%>
	</div>

	<div class="weui-grids">
		<a href="javascript:;" class="weui-grid js_grid open-popup"
			data-target="#full" id="codeClick">
			<div class="weui-grid__icon">
				<img src="${path}/resources/images/wechat/code.png" alt="">
			</div>
			<p class="weui-grid__label">我的收款码</p>
		</a> <a href="sweepcollection?d=${time }" class="weui-grid js_grid">
			<div class="weui-grid__icon">
				<img src="${path}/resources/images/wechat/swsk.png" alt="">
			</div>
			<p class="weui-grid__label">扫我收款</p>
		</a> <a href="toScan?d=${time }" class="weui-grid js_grid">
			<div class="weui-grid__icon">
				<img src="${path}/resources/images/wechat/wysk.png" alt="">
			</div>
			<p class="weui-grid__label">我要收款</p>
		</a>

		<c:if
			test="${currentUser.identityFlag!='4' && isOpenWithdrawls != 'N'}">
			<a href="balance?d=${time }" class="weui-grid js_grid" data-id="">
				<div class="weui-grid__icon">
					<img src="${path}/resources/images/wechat/n_exchange.png" alt="">
				</div>
				<p class="weui-grid__label">余额账户</p>
			</a>
		</c:if>
		<a href="bill?d=${time }" class="weui-grid js_grid" data-id="">
			<div class="weui-grid__icon">
				<img src="${path}/resources/images/wechat/n_recharge.png" alt="">
			</div>
			<p class="weui-grid__label">交易查询</p>
		</a>

		<c:if test="${currentUser.identityFlag!='4'}">
			<!-- 商户审核通过之后才有收银员 -->
			<a href="cashier?d=${time }" class="weui-grid js_grid">
				<div class="weui-grid__icon">
					<img src="${path}/resources/images/wechat/cashier.png" alt="">
				</div>
				<p class="weui-grid__label">收银员管理</p>
			</a>
		</c:if>

		<c:if test="${currentUser.identityFlag=='4' }">
			<a href="selectCode?d=${time }" class="weui-grid js_grid">
				<div class="weui-grid__icon">
					<img src="${path}/resources/images/wechat/cashier.png" alt="">
				</div>
				<p class="weui-grid__label">关联二维码</p>
			</a>
		</c:if>

		<%-- <a href="help" class="weui-grid js_grid open-popup">
		<div class="weui-grid__icon">
			<img src="${path}/resources/images/wechat/help.png" alt="">
		</div>
		<p class="weui-grid__label">
			使用帮助
		</p>
	</a> --%>
		<a href="about?d=${time }" class="weui-grid js_grid open-popup">
			<div class="weui-grid__icon">
				<img src="${path}/resources/images/wechat/about.png" alt="">
			</div>
			<p class="weui-grid__label">关于我们</p>
		</a> <a href="tel:${phone }" class="weui-grid js_grid open-popup">
			<div class="weui-grid__icon">
				<img src="${path}/resources/images/wechat/contact.png" alt="">
			</div>
			<p class="weui-grid__label">联系我们</p>
		</a>
		
		<a href="creditCard" class="weui-grid js_grid open-popup">
			<div class="weui-grid__icon">
				<img src="${path}/resources/images/wechat/credit_card.png" alt="">
			</div>
			<p class="weui-grid__label">
				信用卡申请
			</p>
		</a>
	</div>

	<c:if test="${!empty adviertisementImg && adviertisementImg !='' }">
		<div class="ad">
			<a href="${adviertisementUrl }" target="_blank"><img
				src="${adviertisementImg }" alt="广告" width="100%"></a>
		</div>
	</c:if>
	<div class="weui-footer weui-footer_fixed-bottom">
		<p class="weui-footer__text">©${channelName }</p>
	</div>

	<input type="hidden" id="${type }">

	<div id="full" class="weui-popup__container">
		<div class="weui-popup__overlay"></div>
		<div class="weui-popup__modal swskFull">
			<div class="toolbar">
				<div class="toolbar-inner">
					<a href="javascript:;" class="picker-button close-popup">关闭</a>
					<h1 class="title">收款码</h1>
				</div>
			</div>
			<div class="sh_code">
				<c:if test="${currentUser.identityFlag=='4'&&empty qrCodeSrc}">
				二维码尚未分配或者不在收款状态，请检查再重试。
			</c:if>
				<c:if test="${!empty qrCodeSrc }">
					<h2>扫一扫向商家付款</h2>
					<img id="secondImg" />
					<div id="qrcode">
						<div id="codeico">
							<img src="${icon }" height="60px" width="60px">
						</div>
					</div>
					<p>${qrCodeName }</p>
				</c:if>
				<div class="sh_code_zc">
					<img src="${path}/resources/images/wechat/zc_wx.png" height="54px" title="微信"> 
					<img src="${path}/resources/images/wechat/zc_zfb.png" height="54px" title="支付宝">
					<img src="${path}/resources/images/wechat/zc_qqqb.png" height="54px" title="QQ钱包">
					<%-- <img src="${path}/resources/images/wechat/zc_bdqb.png" height="54px" title="百度钱包">
					<img src="${path}/resources/images/wechat/zc_jdqb.png" height="54px" title="京东钱包"> --%>
				</div>
				<%-- <div class="weui-footer weui-footer_fixed-bottom">
					<img src="${path}/resources/images/wechat/technical.png"
						height="30px">
				</div> --%>
			</div>
		</div>
	</div>
	<!-- var canvas= document.getElementById('mycanvas'); var dataURL = canvas.toDataURL();console.log(dataURL); -->
	<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}"
		type="text/javascript" charset="utf-8"></script>
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
		src="${path}/resources/scripts/qrcode/drawQrCode.js?rnd=${version}&t=${time}"
		type="text/javascript"></script>
	<script>
		if ('${not empty qrCodeSrc}') {
			init('${qrCodeSrc}');
		}

		if ('${status=="0"}' == 'true') {
			$.modal({
				title : "完善信息",
				text : "完善信息，即可申请开通支付",
				buttons : [ {
					text : "取消",
					className : "default",
					onClick : function() {
						//点击取消
					}
				}, {
					text : "去完善信息",
					onClick : function() {
						//确认跳转完善信息页面
						location.href = "apply1";
					}
				},

				]
			});
		}
		if ('${type=="receivables"}' == 'true') {
			$("#codeClick").click();//弹出二维码
		}
	</script>
</body>
</html>
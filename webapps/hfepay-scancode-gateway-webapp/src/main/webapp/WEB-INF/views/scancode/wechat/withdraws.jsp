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
<title>余额提现</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart>

<div class="weui-panel weui-panel_access">
	<div class="weui-panel__hd">请选择提现类型</div>
	<div class="weui-panel__bd">
		<%-- <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg open-popup" data-target="#full">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/WXGZHZF.png" alt="微信">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title">微信扫码付提现</h4>
				<p class="weui-media-box__desc">余额：&yen; <fmt:formatNumber type="number" value="${wxbalance }" pattern="0.00" maxFractionDigits="2"/>  </p>
			</div>
		</a> --%>
		<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg open-popup" data-target="#full">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/WXGZHZF.png" alt="微信公众号">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title">微信钱包</h4>
				<p class="weui-media-box__desc">余额：&yen; <fmt:formatNumber type="number" value="${wxgzhbalance }" pattern="0.00" maxFractionDigits="2"/>  </p>
			</div>
		</a>
		<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg open-popup" data-target="#full">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/ZFBSMZF.png" alt="支付宝">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title">支付宝钱包</h4>
				<p class="weui-media-box__desc">余额：&yen; <fmt:formatNumber type="number" value="${zfbBalance }" pattern="0.00" maxFractionDigits="2"/> </p>
			</div>
		</a>
		<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg open-popup" data-target="#full">
			<div class="weui-media-box__hd">
				<img class="weui-media-box__thumb" src="${path}/resources/images/wechat/QQZF.png" alt="QQ">
			</div>
			<div class="weui-media-box__bd">
				<h4 class="weui-media-box__title">QQ钱包</h4>
				<p class="weui-media-box__desc">余额：&yen; <fmt:formatNumber type="number" value="${qqbalance }" pattern="0.00" maxFractionDigits="2"/> </p>
			</div>
		</a>
	</div>
</div>

<div id="full" class="weui-popup__container popup-bottom">
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal">
		<div class="toolbar">
          <div class="toolbar-inner">
            <a href="javascript:;" class="picker-button close-popup">取消</a>
            <h1 class="title">钱包提现</h1>
          </div>
        </div>
		<div class="weui-cells" style="margin-top:44px">
			<div class="weui-cell">
				<div class="weui-cell__bd">
					提现类型
				</div>
				<div class="yellow">
					全部钱包提现
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					到账银行卡
				</div>
				<div class="yellow">
					${accounts.bankName }(${accounts.bankCard})
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					提现金额(元)
				</div>
				<div class="yellow">
					提现金额以实时到账为准
				</div>
			</div>
		</div>
		
		<div class="weui-btn-area" style="margin-bottom:20px">
			<a href="javascript:void(0);" id="doWithDraw" class="weui-btn weui-btn_primary">确认提现</a>
		</div>
		<form action="" style="display: none" id="toSubmit" method="post"></form>
	</div>
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/withdraws.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

</body></html>
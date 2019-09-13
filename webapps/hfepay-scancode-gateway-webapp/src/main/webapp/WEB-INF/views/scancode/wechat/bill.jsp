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
<title>交易查询</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart style="position:relative">

<div class="lscx_title">
	<div class="lscx_data">
		<h2>${month }</h2>
		<p>支付成功&yen;${total.trade }&nbsp;&nbsp;&nbsp;&nbsp;提现成功&yen;${total.withdraw }</p>
	</div>
	<div class="lscx_screen">
	<a href="javascript:void(0);" id="store_screen" value=""><img src="${path}/resources/images/wechat/progress2.png" height="24px"></a>
	<a href="javascript:void(0);" id="type_screen" value=""><img src="${path}/resources/images/wechat/type_screen.png" height="24px"></a>
	<%-- <a href="javascript:void(0);"><img src="${path}/resources/images/wechat/lscx_screen.png" height="24px"></a> --%></div>
</div>

<div id="list" class="weui-cells weui-cells_access" style="margin-top:60px">
	
</div>

<div class="weui-loadmore" style="display: none;">
  <i class="weui-loading"></i>
  <span class="weui-loadmore__tips">正在加载</span>
</div>

<div id="details" class="weui-popup__container popup-bottom">
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal">
		<div class="toolbar">
          <div class="toolbar-inner">
            <a href="javascript:;" class="picker-button close-popup">关闭</a>
            <h1 class="title">交易详情</h1>
          </div>
        </div>
		<div class="weui-cells" style="margin-top:44px">
			<div class="weui-cell">
				<div class="weui-cell__bd"><p>付款金额</p></div>
				<div class="weui-cell__ft" style="font-size:24px;color:#eeb026">&yen; <span class="orderAmt"></span></div>
			</div>
			<!-- <div class="weui-cell">
				<div class="weui-cell__bd"><p>手续费</p></div>
				<div class="weui-cell__ft">&yen; <span class="merchantCost"></span></div>
			</div> -->
			<div class="weui-cell">
				<div class="weui-cell__bd"><p>优惠金额</p></div>
				<div class="weui-cell__ft" style="font-size:24px;color:#eeb026">&yen; <span class="discountAmt"></span></div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd"><p>交易时间</p></div>
				<div class="weui-cell__ft"><span class="beginTime"></span></div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd"><p>流水号</p></div>
				<div class="weui-cell__ft"><span class="tradeNo"></span></div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd"><p>支付方式</p></div>
				<div class="weui-cell__ft"><span class="payCode"></span></div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd"><p>交易状态</p></div>
				<div class="weui-cell__ft"><span class="payStatus"></span></div>
			</div>
		</div>
		<input type="hidden" id="tradeType" value=""/>
		<input type="hidden" id="storeNo" value=""/>
		<div class="weui-btn-area" style="margin-bottom:20px">
			<a href="javascript:;" class="weui-btn weui-btn_primary close-popup">关闭</a>
		</div>

	</div>
	
</div>


<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/bill.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
 
</script>
</body></html>
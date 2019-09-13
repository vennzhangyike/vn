<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="zh-cmn-Hans">
<head>
<title>确定支付</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<script src="${path }/resources/scripts/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" href="${path }/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path }/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path }/resources/styles/wechat/style.css">
</head>
<body ontouchstart>

<input type="hidden" id="appId" value="${wechat.appId }"/>
<input type="hidden" id="timeStamp" value="${wechat.timeStamp }"/>
<input type="hidden" id="nonceStr" value="${wechat.nonceStr }"/>
<input type="hidden" id="package" value="${wechat.packageContent }"/>
<input type="hidden" id="signType" value="${wechat.signType }"/>
<input type="hidden" id="paySign" value="${wechat.paySign }"/>
<div class="weui_msg">
  <div class="weui_text_area">
    
    <p class="weui_msg_desc">商品金额</p><h1 class="doc-head amount">0.01</h1>
  </div>
</div>

<div class="weui_cells">
	<div class="weui_cell">
		<div class="weui_cell_bd weui_cell_primary">
			<p>商品名称</p>
		</div>
		<div class="weui_cell_ft">
			abc
		</div>
	</div>
	<div class="weui_cell">
		<div class="weui_cell_bd weui_cell_primary">
			<p>商户名称</p>
		</div>
		<div class="weui_cell_ft">
			zy
		</div>
	</div>
	<div class="weui_cell">
		<div class="weui_cell_bd weui_cell_primary">
			<p>订单编号</p>
		</div>
		<div class="weui_cell_ft">
			${order.tradeNo }
		</div>
	</div>
	<div class="weui_cell">
		<div class="weui_cell_bd weui_cell_primary">
			<p>创建时间</p>
		</div>
		<div class="weui_cell_ft">
			<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</div>
	</div>
</div>

<script src="${path }/resources/scripts/fastclick.js" type="text/javascript" charset="utf-8"></script>
<script src="${path }/resources/scripts/jquery-weui.min.js" type="text/javascript" charset="utf-8"></script>

</body>
<script type="application/javascript">

 	var fn = function(){
 		alert("1");
 		if (typeof WeixinJSBridge == "undefined"){
 		   if( document.addEventListener ){
 		       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
 		      alert("2");
 		   }else if (document.attachEvent){
 		       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
 		       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
 		      alert("3");
 		   }
 		}else{
 			alert("4");
 			onBridgeReady();
 		} 
 	}
	
 	function onBridgeReady(){
 		alert("7");
 		var appId = $("#appId").val();
 		var timeStamp =$("#timeStamp").val();
 		var nonceStr = $("#nonceStr").val();
 		var packageContent = $("#package").val();
 		var signType = $("#signType").val();
 		var paySign = $("#paySign").val();
 		alert(appId);
 	   	WeixinJSBridge.invoke(
 	       'getBrandWCPayRequest',{  
 	           "appId" : appId,     //公众号名称，由商户传入     
 	           "timeStamp" : timeStamp,         //时间戳，自1970年以来的秒数     
 	           "nonceStr" : nonceStr, //随机串     
 	           "package" : packageContent,     
 	           "signType" : signType,         //微信签名方式：目前只支持MD5
 	           "paySign" : paySign //微信签名 
 	       },function(res){
 	    	   alert(res);
 	    	   if (res.err_msg == "get_brand_wcpay_request:ok") {
 	                //支付成功之后，请求渠道前台地址
 	                $.toptip('支付成功', 'success');
 	                //setTimeout(returnFun,1000);
 	            }
 	            if (res.err_msg == "get_brand_wcpay_request:cancel") {
 	            	 $.toptip('取消支付', 'warning');
 	            	 //setTimeout(returnFun,1000);
 	            }
 	            if (res.err_msg == "get_brand_wcpay_request:fail") {
 	            	$.toptip('支付失败', 'error');
 	            	//setTimeout(returnFun,1000);
 	            } // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
 	       }
 	   ); 
 	}
 	$(function(){
 		setTimeout(fn,500);
 	})
</script>
</html>
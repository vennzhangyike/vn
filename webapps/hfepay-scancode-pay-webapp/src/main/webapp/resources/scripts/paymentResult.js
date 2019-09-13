$(function(){
	//隐藏微信浏览器右上角菜单
	document.addEventListener("WeixinJSBridgeReady",function(){
		WeixinJSBridge.call('hideOptionMenu');
	});
	
	//显示结果
	if($("#payResult").val()=="000000"){
		$(".success").show();
	}else{
		$(".failure").show();
	}
	//监听返回按钮
	window.history.pushState("", "", "#");
	window.addEventListener("popstate", function(e) {
	 WeixinJSBridge.call('closeWindow');
	}, false);
	
	
});

//关闭按钮
function closed(){
	var ua = navigator.userAgent.toLowerCase();
	var paymentType = "";
	if(ua.match(/MicroMessenger/i)=="micromessenger") {//微信
		WeixinJSBridge.call('closeWindow');
	} else if(ua.indexOf("alipay")!=-1){//支付宝
		AlipayJSBridge.call('closeWebview');
	}
	else{
		window.close();
	}
}
$(function(){
	//判断浏览器类型
	var ua = navigator.userAgent.toLowerCase();
	var paymentType = "";
	if(ua.match(/MicroMessenger/i)=="micromessenger") {//微信
		paymentType = "WX_JSAPI";
	} else if(ua.match(/AlipayClient/i)=="alipayclient"){//支付宝
		paymentType = "ZFB";
	}else{
		alert("不支持浏览器");
		$("#content").show();
	}
	
	var merchantSettledUrl = $("#merchantSettledUrl").val();
	var qrCode = $("#qrCode").val();
	
	//跳转到商户入驻
	if(null == merchantSettledUrl || merchantSettledUrl == ""){
		location.href = basePath+merchantSettledUrl+"?qrCode"+qrCode;
	}else{
		//监听返回按钮
		window.history.pushState("", "", "#");
		window.addEventListener("popstate", function(e) {
			WeixinJSBridge.call('closeWindow');
		}, false);
		
		$.ajax({
		   type: "POST",
		   url: basePath+"/scancode/inputAmt",
		   data:{"qrCode":qrCode.trim(),"paymentType":paymentType.trim()},
		   success: function(msg){
			   $("#tablec").html(msg);
		   }
		});
	}
	
});

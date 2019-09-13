$(function(){
	$("#logout").click(function(){
		$.confirm("亲，我很舍不得你哦！", "确认退出?", function() {
			$.ajax({
				url:basePath+"/user/logout",
				type:"POST",
				success:function(json)
				{
					$.toast("我会想念你的!");
					closed();
				},
				error:function(){
					$.toast("退出失败!","cancel",function(){
						//closed();
					});
				}
			});
			
		}, function() {
			//取消操作
		});
	});
});

//关闭按钮
function closed(){
	var ua = navigator.userAgent.toLowerCase();
	var paymentType = "";
	if(ua.match(/MicroMessenger/i)=="micromessenger") {//微信
		WeixinJSBridge.call('closeWindow');
	} else if(ua.match(/AlipayClient/i)=="alipayclient"){//支付宝
		window.close();
	}
	else{
		window.close();
	}
}
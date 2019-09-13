	$(function(){
		//隐藏微信浏览器右上角菜单
		document.addEventListener("WeixinJSBridgeReady",function(){
			WeixinJSBridge.call('hideOptionMenu');
		});
		
		//监听返回按钮
		window.history.pushState("", "", "#");
		window.addEventListener("popstate", function(e) {
		 WeixinJSBridge.call('closeWindow');
		}, false);
		
		new FastClick(document.body);
		
		$(".key").click(function(){
			var key = $(this).text();
			
			$(this).addClass('active');
			$(".amount").addClass('deactive');
			var t = setTimeout(clearActive, 50);
			
			switch(key){
				case '':
					break;
				case '下一步':
					var tmp = $(".amount").text().split(' ');
					getPayLink(tmp[1]);
					break;
				case '清除':
					num = 0;
					dec = 0;
					stp = 0;
					pnt = false;
					$(".message").text('');
					break;
				case '.':
					pnt = true;
					break;
				default:
					$(".message").text('');
					if(pnt){
						if(stp == 0){
							dec = Number(key) * 10;
							stp++;
						}else if(stp == 1){
							dec += Number(key);
							stp++;
						}
					}else{
						if(num.toString().length < 10){
							num = num * 10 + Number(key);
						}else{
							$.toast('输入金额过大','cancel');
						}
					}
					break;
			}
			formatAmount();
		})
	});
	
	var num = 0;
	var dec = 0;
	var pnt = false;
	var stp = 0;
	var isLock = false;
	
	function formatAmount(){
		var tmp = num.toString();
		if(pnt){
			tmp += '.' + (dec + 100).toString().substr(1);
		}else{
			tmp += '.00';
		}
		$(".amount").text('￥ ' + tmp);
	}
	
	function getPayLink(amt){
		if(isLock){
			return;
		}
		//最小金额限制
		if(Number(amt) == 0){
			$.toast('金额不能为零',text);
			num = 0;
			dec = 0;
			stp = 0;
			pnt = false;
			return;
		}
		
		//最大金额限制
		if(Number(amt) <1){
			$.toast('金额最低不能小于1.00元','cancel');
			return;
		}
		
		//var tmp = amt.split('.');
		
		//amt = tmp[0] + tmp[1];
		
		//amt = parseInt(amt);
		
		isLock = true;
		$('.enter').addClass('locked');
		
		if($("#payType").val() == "WX_JSAPI"){
			//监听点击微信浏览器返回按钮
			window.onpopstate = function(e) {
				WeixinJSBridge.call('closeWindow');
		    };
		}
		
		//var qrCode = $("#qrCode").val();//二维码编号
		$("#money").val(amt);
		$("#subForm").submit();
		//var paymentType = $("#payCode").val();//支付编码：支付宝还是微信
		
		/*$.ajax({
		   type: "POST",
		   url: basePath+"/scancode/pay",
		   data:{"qrCode":qrCode.trim(),"price":amt},
		   success: function(msg){
			   msg = JSON.parse(msg);
			   if(msg.returnCode=='000000'){
				   
			   }
			   else{
				   
			   }
			  
		   }
		});*/
	}
	
	function clearActive(){
		$(".active").removeClass('active');
		$(".amount").removeClass('deactive');
	}	
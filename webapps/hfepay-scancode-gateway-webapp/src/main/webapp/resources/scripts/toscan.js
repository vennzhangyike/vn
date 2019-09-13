	$(function(){
		//隐藏微信浏览器右上角菜单
		document.addEventListener("WeixinJSBridgeReady",function(){
			WeixinJSBridge.call('hideOptionMenu');
		});
		
		//监听返回按钮
//		window.history.pushState("", "", "#");
//		window.addEventListener("popstate", function(e) {
//		 WeixinJSBridge.call('closeWindow');
//		}, false);
		
		new FastClick(document.body);
		$.hideLoading();//隐藏进度条
		$(".empty").click(function(){
			num = 0;
			dec = 0;
			stp = 0;
			pnt = false;
			$("#amount").removeClass("number-size20");
			$(".message").text('');
			formatAmount();
		});
		
		$(".key").click(function(){
			var key = $(this).text();
			
			$(this).addClass('active');
			$(".amount").addClass('deactive');
			var t = setTimeout(clearActive, 50);
			
			switch(key){
				case '':
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
						if(num.toString().length < 9){
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
		if(tmp.length>7){//如果数字的长度超过8（整数5位，小数2位，1位小数点）
			$("#amount").addClass("number-size20");
		}
		$("#amount").text(tmp);
		/*var money = $.trim(tmp);
		if(money&&money!=''&&money!="0.00"){
			var merchantNo = $.trim($("#merchantNo").val());
			var dataJson = {"merchantNo":merchantNo,"orderAmt":money};
			$.ajax({
				   type: "POST",
				   url: "merchantcashingActive",
				   dataType:"json",
				   data:dataJson
			}).done(function(msg){
				if(msg&&msg.actualPayCash){
					$("#discount").show().html("可优惠：<sapn class='red'>"+msg.discountCash+"</sapn>元，实际支付 <span id='actmoney' class='red'>"+msg.actualPayCash+"</span>元");
				}else{
					$("#discount").hide();
				}
				//console.log(msg);
			}).error(function(msg){
				console.log(msg);
			})
		}
		else{
			$("#discount").hide();
		}*/
	}
	
	function getPayLink(amt){
		if(isLock){
			$.hideLoading();//隐藏进度条
			return false;
		}
		//最小金额限制
		if(Number(amt) == 0||!Number(amt)){
			$(".message").text('金额不能为零');
			num = 0;
			dec = 0;
			stp = 0;
			pnt = false;
			$.hideLoading();//隐藏进度条
			return false;
		}
		if(num.toString().length > 8){
			$(".message").text('金额过大');
			return false;
		}
		//最大金额限制
//		if(Number(amt) <2.5||Number(amt) >3000){
//			$(".message").text('单笔交易金额2.5元-3000元');
//			$.hideLoading();//隐藏进度条
//			return false;
//		}
		
		//var tmp = amt.split('.');
		
		//amt = tmp[0] + tmp[1];
		
		//amt = parseInt(amt);
		
		isLock = true;
		$('.enter').addClass('locked');
		
//		if($("#payType").val() == "WX_JSAPI"){
//			//监听点击微信浏览器返回按钮
//			window.onpopstate = function(e) {
//				WeixinJSBridge.call('closeWindow');
//		    };
//		}
		
		//var qrCode = $("#qrCode").val();//二维码编号
//		var length = $('#discount:hidden').length;
//		if(length==0){//显示出优惠那么取优惠后的价格
//			var act = $('#actmoney');
//			if(act.length!=0){
//				amt = $.trim($('#actmoney').text());
//			}
//		}
//		
		$("#amt").val(amt);
		return true;
		//get();
		//$("#subForm").submit();
	}
	
	function clearActive(){
		$(".active").removeClass('active');
		$(".amount").removeClass('deactive');
	}	
	
	
	
	
	
	
	var timer;
	/***微信图片上传***/
	var appId=$("#appId").val();//'wxcd45db31731fce73';
	var timestamp=$("#timestamp").val(); //'1477034918';
	var nonceStr=$("#nonceStr").val(); //'ab6a98ec-abc1-4036-92dc-362eaa0add7e';
	var signature=$("#signature").val(); //'6fbb2e3f2ce6b6974a3e9bf5b955471186966de4';
	wx.config({
	    debug: false, 
	    appId: appId, 
	    timestamp: timestamp, 
	    nonceStr: nonceStr, 
	    signature: signature,
	    jsApiList: ['scanQRCode'] 
	});
	wx.ready(function(){
		var enter = $(".enter");
		enter.click(function(){
			if(timer){
				clearTimeout(timer);
			}
			timer = setTimeout(function(){
				if($('.weui-mask_transparent').length!=0){
					$.hideLoading();//隐藏进度条
					clearActive();
					$('.enter').removeClass('locked');
					isLock = false;
				}
			}, "5000");
			$.showLoading("数据处理中...");//显示进度条
			var tmp = $("#amount").text();
			if(getPayLink(tmp)){
				get();
			}else{
				$.hideLoading();//隐藏进度条
			};
		})
		
	});

	function get(){
		wx.scanQRCode({
		    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
		    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
		    success: function (res) {
		    	var amt = $("#amt").val();
		    	var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
		    	var scanPayUrl = $("#scanPayUrl").val();
		    	var url = "&orderAmt="+$("#amt").val()+"&authCode="+result+"&fromH5=yes&identityNo="+$("#identityNo").val();
		    	$.ajax({
		 		   type: "POST",
		 		   url: scanPayUrl+url,
		 		   dataType:'jsonp',
		 		   jsonp:"jsoncallback",
		 		   processData: false
		 		  // data:dataJson
		 	}).done(function(msg){//成功提交
		 		
		 	});
			},
			cancel: function () { 
				$.hideLoading();//隐藏进度条
				clearActive();
				$('.enter').removeClass('locked');
				isLock = false;
			}
		});
	}

	
	
	function jsonpCallback(data){
		$.hideLoading();//隐藏进度条
		if(data.errorCode=='0'){
			$.toast("支付成功",function(){
				location.reload();
			});
		}
		else{
			$.toast(data.errorMsg,'cancel',function(){
				location.reload();
			});
		}
		
	}
	
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
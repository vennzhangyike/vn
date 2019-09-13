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
		$(".empty").click(function(){
			num = 0;
			dec = 0;
			stp = 0;
			pnt = false;
			$("#amount").removeClass("number-size20");
			$(".message").text('');
			formatAmount();
		});
		
		$(".enter").click(function(){
			var tmp = $("#amount").text();
			var payCode = $("#payCode").val();
			var organLimit = getOrganLimit(payCode);
			var minLimit;
		   	var maxLimit;
		   	if(!!organLimit){
		   		minLimit = organLimit.minLimit;
		   		maxLimit = organLimit.maxLimit;
		   	}
			getPayLink(tmp,minLimit,maxLimit);
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
							$.toast('输入金额过大', 'cancel');
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
	
	function getOrganLimit(payCode){	
		var limitPayCode;
		var organLimit;
		if('ZFBSMZF' == payCode){
			limitPayCode = "2";
		}else if('WXGZHZF' == payCode){
			limitPayCode = "1";
		}
		if(!limitPayCode){
			return ;
		}
		var merchantNo = $.trim($("#merchantNo").val());
		if(!merchantNo){
			return ;
		}
		$.ajax({
			   url:"getOrganLimit",
	 		   data:{"merchantNo":merchantNo,"limitType":"1","limitPayCode":limitPayCode,"limitMode":"0"}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(data){
	 			 data = $.parseJSON(data);
	 			 if(data.executeStatus == '0'){
	 				organLimit = data.organLimit;
	 				if(organLimit&&organLimit.status=='1'){//被禁止的限额不处理
	 					//return organLimit
	 				}else{
	 					organLimit=undefined;
	 				}
 				 }else{
 			   		return ;
 				 }
	 			  
	 		   }
	 		});	
		return organLimit;
	}
	
	function formatAmount(){
		var tmp = num.toString();
		if(pnt){
			tmp += '.' + (dec + 100).toString().substr(1);
		}else{
			tmp += '.00';
		}
		if(tmp.length>7){//如果数字的长度超过8（整数4位，小数2位，1位小数点）
			$("#amount").addClass("number-size20");
		}
		$("#amount").text(tmp);
//		var money = $.trim(tmp);
//		if(money&&money!=''&&money!="0.00"){
//			var merchantNo = $.trim($("#merchantNo").val());
//			var dataJson = {"merchantNo":merchantNo,"orderAmt":money,"minLimit":minLimit};
//			$.ajax({
//				   type: "POST",
//				   url: "merchantcashingActive",
//				   dataType:"json",
//				   data:dataJson
//			}).done(function(msg){
//				if(msg&&msg.actualPayCash){
//					$("#discount").show().html("可优惠："+msg.discountCash+"元,实际支付<span id='actmoney'>"+msg.actualPayCash+"</span>元");
//				}else{
//					$("#discount").hide();
//				}
//				//console.log(msg);
//			}).error(function(msg){
//				console.log(msg);
//			})
//		}
//		else{
//			$("#discount").hide();
//		}
	}
	
	function getPayLink(amt,minLimit,maxLimit){
		if(isLock){
			return;
		}
		//最小金额限制
		if(Number(amt) == 0){
			$.toast('金额不能为零',"cancel");
			num = 0;
			dec = 0;
			stp = 0;
			pnt = false;
			return;
		}
		if(num.toString().length > 8){
			$.toast('金额过大',"cancel");
			return;
		}
		//最大金额限制
		if(Number(amt) < Number(minLimit) || Number(amt) > Number(maxLimit)){
			$.toast("单笔交易金额" + minLimit + "元-" + maxLimit +"元！","cancel");
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
//		var length = $('#discount:hidden').length;
//		if(length==0){//显示出优惠那么取优惠后的价格
//			var act = $('#actmoney');
//			if(act.length!=0){
//				amt = $.trim($('#actmoney').text());
//			}
//		}
		
		$("#amt").val(amt);
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
		$("#amount").removeClass('deactive');
	}	
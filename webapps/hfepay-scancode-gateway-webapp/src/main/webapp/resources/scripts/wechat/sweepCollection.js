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
			getPayLink(tmp);
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
		
		$(".zffs").find("li").click(function(){
			$(".zffs").find("li").attr("class","");
			$(this).attr("class","cur");
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
	}
	
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
		
		$.ajax({
			   url:basePath+"/scancode/getOrganLimit",
	 		   data:{"limitType":"1","limitPayCode":limitPayCode,"limitMode":"0"}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(data){
	 			  data = $.parseJSON(data);
	 			 if(data.executeStatus == '0'){
	 				organLimit = data.organLimit;
 				 }else{
 			   		return ;
 				 }
	 			  
	 		   }
	 		});	
		return organLimit;
	}
	
	function getPayLink(amt){
		if(isLock){
			return;
		}
		//最小金额限制
		if(Number(amt) == 0){
			$.toast('金额不能为零', 'cancel');
			num = 0;
			dec = 0;
			stp = 0;
			pnt = false;
			return;
		}
		if(num.toString().length > 8){
			$.toast('金额过大', 'cancel');
			return;
		}
		
		//支付方式
		var licur = $(".zffs").find("li.cur");
		if(licur.length == 0){
			$.toast('支付方式未选择', 'cancel');
			return;
		}
		
		var organLimit = getOrganLimit(licur.attr('payCode'));
	   	var minLimit;
	   	var maxLimit;
	   	if(!!organLimit){
	   		minLimit = organLimit.minLimit;
	   		maxLimit = organLimit.maxLimit;
	   		$("#minLimit").val(minLimit);
	   	}
		//最大金额限制
		if(Number(amt) < Number(minLimit) || Number(amt) > Number(maxLimit)){
			$.toast("单笔交易金额" + minLimit + "元-" + maxLimit +"元！",'cancel');
			return;
		}
		
		
		isLock = true;
		$('.enter').addClass('locked');
		
		if($("#payType").val() == "WX_JSAPI"){
			//监听点击微信浏览器返回按钮
			window.onpopstate = function(e) {
				WeixinJSBridge.call('closeWindow');
		    };
		}
		
		$("#orderAmt").val(amt);
		$("#payCode").val(licur.attr('payCode'));		
		$("#subForm").submit();
		
	}
	
	function clearActive(){
		$(".active").removeClass('active');
		$(".amount").removeClass('deactive');
	}	
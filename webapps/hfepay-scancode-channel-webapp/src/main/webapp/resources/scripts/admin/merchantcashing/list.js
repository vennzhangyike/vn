$(function(){
	//获取焦点
	$("#money").focus();
	$("#money").blur(function(){
		var money = $.trim($("#money").val());
		var dataJson = {"money":money};
		if(money&&money!=''){
			$.ajax({
				   type: "POST",
				   url: "merchantcashingActive",
				   data:dataJson,
				   dataType:"json"
			}).done(function(msg){
				if(msg&&msg.actualPayCash){
					$("#discount").show().html("可优惠： <span>"+msg.discountCash+"</span> 元，实际支付：<span id='actmoney'>"+msg.actualPayCash+"</span> 元");
				}else{
					$("#discount").hide();
				}
				console.log(msg);
			}).error(function(msg){
				console.log(msg);
			})
		}
		else{
			$("#discount").hide();
		}
	});
	
	//按钮绑定one事件，保证最后的授权码和或者按钮只有一个有效不能同时点击
	bindOneClick();
	//支付方式选中效果
	$(".w-pay-type").focus(function() {
        $(".w-pay-type").removeClass("w-pay-selected");
        $(this).addClass("w-pay-selected")
    }).keydown(function(e){
    	if(e.keyCode==13){
			$("#code").focus();
		}
    	else if(e.keyCode==39){//右箭头
			var next = $(this).next('.w-pay-type');
			if(next.length!=0){
				setTimeout(function(){
					$(".w-pay-type").removeClass("w-pay-selected");
	    			$(next).focus().addClass("w-pay-selected");
				},100);
				
    		}
		}
    	else if(e.keyCode==37){//左箭头
    		var prev = $(this).prev('.w-pay-type')
    		if(prev.length!=0){
    			setTimeout(function(){
    				$(".w-pay-type").removeClass("w-pay-selected");
    				$(prev).focus().addClass("w-pay-selected");
    			},100);
    		}
		}
    });
	
	//回车键触发金额计算和支付方式聚焦
	 $("#money").keydown(function(e){
		var value = $(this).val();
		if(e.keyCode==13){
			$(this).val(eval(value));
			$("#payways .w-pay-type:eq(0)").focus();
		}
	});
	 //code触发回车事件
	 $("body").delegate("#code","keydown",function(e){
			if(e.keyCode==13){
				$("#save").click();
			}
		});
	 //save按钮触发回车事件
	 function bindOneClick(isReset){
		 $('button').removeAttr('disabled');
		 $("#save").one("click",function(){
			 $('button').attr('disabled','disabled');
			 toSubmit();
		 });
	 }
	 function toSubmit(){
			var money = $.trim($("#money").val());
			var code=$.trim($("#code").val());
			var payway = $.trim($('.w-pay-type.w-pay-selected',$('#payways')).attr('paytype'));
		   	if(!payway){
		   		bootbox.alert("请选择支付方式！<br>",function(){bindOneClick();});
		   		return;
		   	}
			if(Number(money)<2.5){
		   		bootbox.alert("消费金额不能小于2.5！<br>",function(){bindOneClick();});
		   		return;
		   	}
		   	if(money==""||!/^\d+(\.\d{1,2})?$/.test(money)) {
		   		bootbox.alert("消费金额有误,最多保留两位小数！<br>",function(){bindOneClick();});
		   		return;
		   	}
		   	if(!code||code==""){
		   		bootbox.alert("付款授权码不能为空.<br>",function(){bindOneClick();});
		   		return;
		   	}
		   	$('body').showLoading();
		   	var length = $('#discount:hidden').length;
			if(length==0){//显示出优惠那么取优惠后的价格
				money = $.trim($('#actmoney').text());
			}
		   	var dataJson = {"orderAmt":money,"authCode":code,"payCode":payway};
			$.ajax({
				   type: "POST",
				   url: "pay",
				   dataType:"json",
				   data:dataJson
			}).done(function(msg){//成功提交
				$('body').hideLoading();
				bindOneClick();
				if(msg.errorCode=='0'){
					bootbox.alert("支付成功.<br>",function(){
						location.reload();
					});
				}
				else{
					bootbox.alert(msg.errorMsg+".<br>",function(){
						location.reload();
					});
				}
			}).error(function(msg){//提交失败
				$('body').hideLoading();
				bootbox.alert("系统错误.<br>",function(){bindOneClick();});
			});
		}
});
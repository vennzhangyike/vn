$(function(){
	
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

	//支付方式选中效果
	$(".w-pay-type").focus(function() {
        $(".w-pay-type").removeClass("w-pay-selected");
        $(this).addClass("w-pay-selected");
    }).keydown(function(e){
    	if(e.keyCode==13){
    		if($(".bootbox-alert").length==0){
    			toSubmit();			
    		}
		}
    	if(e.keyCode==39){
			$(".w-pay-type").removeClass("w-pay-selected");
			if($(this).next().length!= 0){
				$(this).next().focus();
				$(this).next().addClass("w-pay-selected");
			}else{
				$(this).addClass("w-pay-selected");
			}
			
		}
    	if(e.keyCode==37){
    		$(".w-pay-type").removeClass("w-pay-selected");
    		if($(this).prev().length!= 0){
    			$(this).prev().focus();
    			$(this).prev().addClass("w-pay-selected");
    		}else{
				$(this).addClass("w-pay-selected");
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
	 
	$("#save").click(function(e){
		var isSubmit = toSubmit();
		if(isSubmit){
			$("#money").val("");
			$("#money").focus();
		}
	});
	
		 
	function toSubmit(){
			var money = $.trim($("#money").val());
			if($("#actmoney").length >0){
				money = $.trim($("#actmoney").text());
			}
			
			var code=$.trim($("#code").val());
			var payway = $.trim($('.w-pay-type.w-pay-selected',$('#payways')).attr('paytype'));
		   	if(!payway){
		   		bootbox.alert("请选择支付方式！<br>",function(){
		   			$("#payways .w-pay-type:eq(0)").focus();
		   			$("#payways .w-pay-type:eq(0)").addClass("w-pay-selected");
		   		});
		   		return;
		   	}
		    
		   	if(Number(money)<2.5){
		   		bootbox.alert("消费金额必须大于等于2.5！<br>",function(){
		   			$(".bootbox-alert").remove();
		   			$(".modal-backdrop").remove();
					$("#money").focus();
		   		});
		   		return;
		   	}
		   	if(money==""||!/^\d+(\.\d{1,2})?$/.test(money)) {
		   		bootbox.alert("消费金额有误,最多保留两位小数！<br>",function(){
		   			$(".bootbox-alert").remove();
		   			$(".modal-backdrop").remove();
					$("#money").focus();
		   		});
		   		return;
		   	}
		   	var dataJson = {"orderAmt":money,"authCode":code,"payCode":payway};
			$.ajax({
				   type: "POST",
				   url: "pay/sweepcollection",
				   async : false, 
				   dataType:"json",
				   data:dataJson
			}).done(function(msg){//成功提交
				$("#qrcode").children().remove();
				if(msg.executeStatus=='0'){
//					$("#qrcode").children().remove();
//					var qrcode = new QRCode($("#qrcode")[0], {
//						width : 240,
//						height : 240
//					});
//					qrcode.makeCode(msg.payStr);
					if(msg.returnCode=='000000'){
						init(msg.payStr);
						var imgStr = "<div align='center' style='margin-bottom: 20px;margin-top: 10px;' ><h2><b>收款金额:<span style='color:#ff0000'>"+ Number(money).toFixed(2) +"</span>元</b></h2></div>" + 
						"<div align='center' ><img src="+ $("#qrcode").find("canvas")[0].toDataURL("image/png") + "></div>" +
						"<div align='center' style='margin-top: 10px;' >" + $('.w-pay-type.w-pay-selected',$('#payways')).html() + "</div>";
						bootbox.alert(imgStr,function(){
							$(".bootbox-alert").remove();
				   			$(".modal-backdrop").remove();
				   			$("#money").val("")
							$("#money").focus();
						});
					}else{
						bootbox.alert(msg.returnMsg,function(){
							$(".bootbox-alert").remove();
				   			$(".modal-backdrop").remove();
							$("#money").focus();
						});
					}
					
					
				}else{
					bootbox.alert(msg.values,function(){
						$(".bootbox-alert").remove();
			   			$(".modal-backdrop").remove();
			   			$("#money").val("")
						$("#money").focus();
					});
				}
			}).error(function(msg){
				bootbox.alert("系统错误.<br>",function(){
					$(".bootbox-alert").remove();
		   			$(".modal-backdrop").remove();
		   			$("#money").val("")
					$("#money").focus();
				});
			});
		}
});
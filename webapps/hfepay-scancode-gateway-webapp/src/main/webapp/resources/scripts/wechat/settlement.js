$(function(){
	$("#sureChange").click(function(){
		var bankName = $("#bankName").val();
		var bankCard = $("#bankCard").val();
		var mobile = $("#mobile").val();
		var validateCode =$("#validateCode").val();
		var clearBankChannelNo = $("#clearBankChannelNo").val();
		if(!bankName ||bankName==''){
			$.toast("开户行不能为空", "cancel");
			return;
		}
		if(!/^\d+$/.test(bankCard)){
			$.toast("银行卡格式有误", "cancel");	
			return;
		}
		if(!/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/.test(mobile) || mobile.length != 11){
			$.toast("手机号码格式有误", "cancel");
			return;
		}
		if(validateCode==""||validateCode.length!=6){
			$.toast("请输入正确的验证码", "cancel");
			return;
		}
		var dataJson = {"bankName":bankName,"bankCard":bankCard,"mobile":mobile,"bankCode":clearBankChannelNo,"validateCode":validateCode};
		$.ajax({
			   type: "POST",
			   url: basePath+"/scancode/settlement/update",
			   data:dataJson,
			   dataType:"json",
			   success: function(msg){
				   var errorCode = msg.errorCode;
				   if(errorCode=='0'){
					   $.toast("变更成功");
				   }
				   else{
					   $.toast("变更失败", "cancel");
				   }
				   setTimeout("location.reload()",
						   1000);
			   }
			});
	});
	
	$("#phone").on("keyup afterpaste",function(){
		var value = $(this).val();
		 $(this).val(value.replace(/\D/g,"").replace(/^[^1]*/,""));
	});
	//验证码框
	$("#validateCode").on("blur",function(){
		var value = $(this).val();
		if(value==""||value.length!=6){
			$.toast("请输入正确的验证码", "cancel");
		}
	});
	$("#validateCode").on("keyup afterpaste",function(){
		var value = $(this).val();
		$(this).val(value.replace(/\D/g,""));
	});
	
	$("#bankCard").on("keyup afterpaste",function(){
		var value = $(this).val();
		$(this).val(value.replace(/\D/g,""));
		//输入超过六位开始条用查询
		if(value.length<2){
			$("#bankName").val("");
			$("#clearBankChannelNo").val("");
		}
		if(value.length>=2&&value.length<11){
			$.ajax({
  				type: "POST",
  				url: basePath+"/scancode/getBankInfo",
  				data: {"bankCard":value},
  				success:function(msg){
  					if(msg && msg!="" && msg!=null){
  						var card = JSON.parse(msg);
  	  					$("#bankName").val(card.bankName);
  	  					$("#clearBankChannelNo").val(card.clearBankChannelNo);
  					}
  					else{
  						$("#bankName").val("");
  	  					$("#clearBankChannelNo").val("");
  					}
  				}
  			});
		}
	});
});



//验证码
//调用一个之后解绑
bindOne();

//控制获取验证码
var authTime = 60;
function checkSendAuthCodeAgain(){
var obj = $("#code");
if(authTime == 0){
$("#code").removeClass("pweui_vcode").addClass("aweui_vcode")
obj.html("获取验证码");
obj.css("cursor","pointer");
	obj.css("color","#ff5256");
authTime = 60;
bindOne();
}else{
	obj.css("color","#999");
obj.html("等待"+authTime+"秒");
obj.css("text-decoration","none");
	obj.css("cursor","default");
authTime--;
setTimeout("checkSendAuthCodeAgain()",
1000);
}
}

function bindOne(){
$("#code").one("click",function(){
	//校验手机号码
	var phone = $("#mobile").val();
	var merchantNo = $("#merchantNo").val();
	if(!/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/.test(phone) || phone.length != 11){
		$("#errorDiv").show();
		$.toast("请输入正确的手机号码","cancel");
		bindOne();
	}else{
		$("#code").removeClass("aweui_vcode").addClass("pweui_vcode");
		//发送验证码
		checkSendAuthCodeAgain();
		$.ajax({
			type: "POST",
			url: basePath+"/user/send/validate/code",
			data: {"phone":phone,"merchantNo":merchantNo,"sendValidateType":"33"},//发送验证码类型 00注册 11登陆 22找回密码
			success:function(msg){
			}
		});
	}
});
}
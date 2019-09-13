$(function(){
	reset();
	//注册按钮
	$("#show-confirm").click(function(){
		var userPhoneVal = $("#phone").val();
		var validateCodeVal = $("#validateCode").val();
		var passWordVal = $("#password").val();
		var againPwdVal = $("#confirmpassword").val();
		
		//有错误信息显示不提交
		if($("#errorDiv").is(":visible")){
			return;
		}
		if(!/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/.test(userPhoneVal) || userPhoneVal.length != 11){
			$.toast("请输入正确的手机号码",'cancel');
			return;
		}
		if(validateCodeVal==""||validateCodeVal.length!=6){
			$.toast("请输入正确的验证码",'cancel');
			return;
		}
		if(!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/.test(passWordVal)){
			$.toast("密码格式不正确，6-20位",'cancel');
			return;
		}
		if(againPwdVal!=passWordVal){
			$.toast("密码不一致",'cancel');
			return;
		}
		var dataJson = {"userName":userPhoneVal,"phone":userPhoneVal,"validateCode":validateCodeVal,"againPwd":againPwdVal,"password":passWordVal};
		//注册
		$.ajax({
				url:basePath+"/user/reset/password",
				data:dataJson,
				dataType:"json",
				type:"POST",
				success:function(json)
				{
					var executeStatus = json.errorCode; 
					if(executeStatus == "0"){
						$.toast("重置成功!");
						location.href = basePath+"/scancode/my";
					}
					else{
						$.toast(json.errorMsg,'cancel');
						return;
					}
				}
			});
	});
	//手机号码文本框
	$("#phone").on("blur",function(){
		var value = $(this).val();
		if(!/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/.test(value) || value.length != 11){
			$.toast("请输入正确的手机号码",'cancel');
		}else{
			reset();
		}
	});
	$("#phone").on("keyup afterpaste",function(){
		var value = $(this).val();
		 $(this).val(value.replace(/\D/g,"").replace(/^[^1]*/,""));
	});
	//验证码框
	$("#validateCode").on("blur",function(){
		var value = $(this).val();
		if(value==""||value.length!=6){
			$.toast("请输入正确的验证码",'cancel');
		}
		else{
			reset();
		}
	});
	$("#validateCode").on("keyup afterpaste",function(){
		var value = $(this).val();
		$(this).val(value.replace(/\D/g,""));
	})
	//密码文本框
	$("#password").on("blur",function(){
		var value = $(this).val();
		if(!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/.test(value)){
			$.toast("密码格式不正确，6-20位",'cancel');
		}
		else{
			reset();
		}
		
	});
	
	$("#confirmpassword").on("blur",function(){
		var value = $(this).val();
		if(value!=$("#password").val()){
			$.toast("密码不一致",'cancel');
		}
		else{
			reset();
		}
	});
	
	function reset(){
		$("#errorDiv").hide();
		$("#errorMsg").text("");
	}
	
	function showDiv(msg){
		$("#errorDiv").show();
		$("#errorMsg").text(msg);
	}
	
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
	var phone = $("#phone").val();
	if(!/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/.test(phone) || phone.length != 11){
		$("#errorDiv").show();
		$("#errorMsg").text("请输入正确的手机号码");
		bindOne();
	}else{
		//校验手机是否被使用
		$.ajax({
			type: "POST",
			url: basePath+"/user/validate/phone",
			data: {"phone":phone,"type":"1"}//type 0 代表注册1找回密码
		}).done(function(json){
			json = JSON.parse(json);
			if(json.executeStatus == "0"){
				$("#code").removeClass("aweui_vcode").addClass("pweui_vcode");
				//发送验证码
				checkSendAuthCodeAgain();
    			$.ajax({
    				type: "POST",
    				url: basePath+"/user/send/validate/code",
    				data: {"phone":phone,"sendValidateType":"22"},//发送验证码类型 00注册 11登陆 22找回密码
    				success:function(msg){
    				}
    			});
			}else{
				$("#errorDiv").show();
				$("#errorMsg").text("手机号码不存在");
				bindOne();
			}
		});
	}
});
}

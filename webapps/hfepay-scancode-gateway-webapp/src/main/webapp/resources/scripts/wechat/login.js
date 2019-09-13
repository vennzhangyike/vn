$(function(){
	reset();
	//注册按钮
	$("#login").click(function(){
		var userPhoneVal = $("#phone").val();
		var passWordVal = $("#password").val();
		//有错误信息显示不提交
		if($("#errorDiv").is(":visible")){
			return;
		}
		if(!/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/.test(userPhoneVal) || userPhoneVal.length != 11){
			$.toast("请输入正确的手机号码",'cancel');
			return;
		}
		if(!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/.test(passWordVal)){
			$.toast("密码格式不正确，6-20位",'cancel');
			return;
		}
		var dataJson = {"userName":userPhoneVal,"phone":userPhoneVal,"password":passWordVal};
		//登录
		$.ajax({
			url:basePath+"/user/do/login",
			data:dataJson,
			dataType:"json",
			type:"POST",
			success:function(json)
			{
				var executeStatus = json.errorCode; 
				if(executeStatus == "0"){
					location.href = basePath+"/scancode/my";
				}else if(executeStatus == "1"){//失败
					location.href = basePath+"/user/login";
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
	
	function reset(){
		$("#errorDiv").hide();
		$("#errorMsg").text("");
	}
	
	function showDiv(msg){
		$("#errorDiv").show();
		$("#errorMsg").text(msg);
	}
	
});



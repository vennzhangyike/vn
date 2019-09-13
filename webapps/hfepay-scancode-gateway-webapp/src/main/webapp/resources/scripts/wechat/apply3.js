$(function(){
	reset();
	
	$("#tabChange a").click(function(){
		$("#tabChange a").removeClass('weui-bar__item--on');
		$(this).addClass('weui-bar__item--on');//上边变化
		//图片位置变化
		var index = $('#tabChange a').index($(this));//获取当前是第几个tab被点击
		$("#accountType").val(index);
		if(index==0){//个人
			$(".enterprise").hide();
			$(".person").show();
		}else{//企业
			$(".enterprise").show();
			$(".person").hide();
		}
		
	});
	//个人企业tab切换
	if($.trim($("#temp1").val())!=''){//企业
		$('#tabChange a:eq(1)').click();//通过触发点击事件构造无执照的商户资料完善效果
	}else{
		$('#tabChange a:eq(0)').click();//通过触发点击事件构造无执照的商户资料完善效果
	}
	//注册按钮
	$("#next").click(function(){
		var name = $("#realName").val();//法人名称
		var idCard = $("#idCard").val();//身份证号码
		var temp1 =  $("#temp1").val();//企业名称
		
		var bankName = $("#bankName").val();
		var bankCard = $("#bankCard").val();
		var phone =$("#phone").val();
		//var validateCode =$("#validateCode").val();
		var agree =$("#agree").is(":checked");//协议
		var clearBankChannelNo = $("#clearBankChannelNo").val();
		var accountType=$('#accountType').val();
		
		if(!name || name==""){
			$.toast("法人名称不能为空",'cancel');
			return;
		}
		if(accountType=='0'&&(!idCard||idCard=='')){//个人
			$.toast("身份证号码不能为空",'cancel');
			return;
		}
		if(accountType=='1'&&(!temp1||temp1=='')){//企业
			$.toast("企业名称不能为空",'cancel');
			return;
		}
		
		if(!/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/.test(phone) || phone.length != 11){
			$.toast("请输入正确的手机号码",'cancel');
			return;
		}
//		if(validateCode==""||validateCode.length!=6){
//			$.toast("请输入正确的验证码",'cancel');
//			return;
//		}
		if(!bankName || bankName==""){
			$.toast("银行名称不能为空",'cancel');
			return;
		}
		if(!bankCard || bankCard==""){
			$.toast("银行卡号码不能为空",'cancel');
			return;
		}
		if(!accountType||accountType==''){
			$.toast("请选择账户类型",'cancel');
			return;
		}
		if(!agree){
			$.toast("请先同意协议",'cancel');
			return;
		}
		//有错误信息显示不提交
		/*if($("#errorDiv").is(":visible")){
			return;
		}*/
		$.showLoading("数据处理中...");//显示进度条
		var dataJson = {"bankName":bankName,"bankCard":bankCard,"mobile":phone,/*"validateCode":validateCode,*/"agree":agree,"name":name,"idCard":idCard,
				"bankCode":clearBankChannelNo,"accountType":accountType,"temp1":temp1};
		$('#next').attr('disabled','disabled');
		//注册
		$.ajax({
				url:basePath+"/scancode/apply/step3",
				data:dataJson,
				type:"POST",
				success:function(json)
				{
					$('#next').removeAttr('disabled');
					$.hideLoading();//隐藏进度条
					json = JSON.parse(json);
					var executeStatus = json.errorCode; 
					if(executeStatus == "0"){
						location.href=basePath+"/scancode/progress";
					}
					else{
						$.toast(json.errorMsg,'cancel');
						return;
					}
					
				},
				error:function(){
					$('#next').removeAttr('disabled');
					$.toast("内部错误",'cancel');
					$.hideLoading();//隐藏进度条
					return;
				}
			});
	});
	
	$("#bankCard").on("blur",function(){
		var value = $(this).val();
		if(!/\d{16,}$/.test(value)){
			$.toast("请输入正确的银行卡",'cancel');
		}
		else{
			reset();
		}
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
	/*$("#validateCode").on("blur",function(){
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
	});*/
	
	function reset(){
		$("#errorDiv").hide();
		$("#errorMsg").text("");
	}
	
	function showDiv(msg){
		$("#errorDiv").show();
		$("#errorMsg").text(msg);
	}
	
	$("#agree").change(function(){
		var temp = $(this).is(":checked");
		if(!temp){
			$.toast("请同意协议",'cancel');
		}
		else{
			reset();
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
	var phone = $("#phone").val();
	var merchantNo = $("#merchantNo").val();
	if(!/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/.test(phone) || phone.length != 11){
		$("#errorDiv").show();
		$("#errorMsg").text("请输入正确的手机号码");
		bindOne();
	}else{
		//校验手机是否被使用
		/*$.ajax({
			type: "POST",
			url: basePath+"/user/validate/phone",
			data: {"phone":phone,"type":"0"}//type 0 代表注册
		}).done(function(json){
			json = JSON.parse(json);
			if(json.executeStatus == "0"){*/
				$("#code").removeClass("aweui_vcode").addClass("pweui_vcode");
				//发送验证码
				checkSendAuthCodeAgain();
  			$.ajax({
  				type: "POST",
  				url: basePath+"/user/send/validate/code",
  				data: {"phone":phone,"merchantNo":merchantNo,"sendValidateType":"00"},//发送验证码类型 00注册 11登陆 22找回密码
  				success:function(msg){
  				}
  			});
			/*}else{
				$("#errorDiv").show();
				$("#errorMsg").text("手机号码已被注册");
				bindOne();
			}
		});*/
	}
});
}
$(function(){
	//提交按钮事件
	$("#sure").click(function(){
		$.showLoading("数据处理中...");//显示进度条
		var realName=$.trim($("#realName").val());
		var idCardNo=$.trim($("#idCardNo").val());
		var mobile=$.trim($("#mobile").val());
		var openId=$.trim($("#openId").val());
		var organNo = $.trim($("#organNo").val());
		if(!realName){
			$.toast('真实姓名不能为空!', 'cancel');
			return;
		}
		if(!idCardNo||(idCardNo.length!=15&&idCardNo.length!=18)){
			$.toast('身份证号码有误!', 'cancel');
			return;
		}
		if(!/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/.test(mobile) || mobile.length != 11){
			$.toast('手机号码有误!', 'cancel');
			return;
		}
		
		$.ajax({
			url:basePath+"/cashier/registerCashierOperator",
			data:{"openId":openId,"userName":realName,"idCard":idCardNo,"mobile":mobile},
			type:"POST",
			dataType:"json"
		}).done(function(msg){
			$.hideLoading();//隐藏进度条
			var errorCode = msg.errorCode;
			if(errorCode=='0'){
				$.toast(msg.errorMsg,function(){
					location.href=basePath+"/cashier/cashierSuccess/"+organNo;
					});
			}
			else{
				$.toast(msg.errorMsg, 'cancel');
			}
		}).error(function(){
			$.hideLoading();//隐藏进度条
		});
	});
});
	
	
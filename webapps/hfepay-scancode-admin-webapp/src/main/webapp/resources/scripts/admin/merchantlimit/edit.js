$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$('.date-picker').datepicker({
	    rtl: Metronic.isRTL(),
	    orientation: "left",
	    autoclose: true
	});
	
	$.validator.addMethod("isNum", function(value, element) { 
		   var tel = /^(([0]{1})|([0-9]+)|([0-9]+\.[0]{1,2}))$/;
		   return this.optional(element) || (tel.test(value)); 
		   }, "只能输入整数!");
	
	$("#cancle").click(function(){
		history.go(-1);
	});
	
	function checkMerchantNo(){
		var merchantNo = $("#merchantNo").val();
		var bool = false;
		$.ajax({
			   url:"checkMerchantNo",
	 		   data:{"merchantNo":merchantNo}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(msg){
	 			  msg = $.parseJSON(msg);
	 			  bool = msg.check;
	 		   }
	 		});
		return bool;
	}
	
	function checkIsSame(){
		var merchantNo = $("#merchantNo").val();
		var limitPayCode = $("#limitPayCode").val();
		var limitType = $("#limitType").val();
		var id = $("#id").val();
		var bool = false;
		$.ajax({
			   url:"check",
	 		   data:{"merchantNo":merchantNo,"limitPayCode":limitPayCode,"limitType":limitType,"id":id}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(msg){
	 			  msg = $.parseJSON(msg);
	 			  bool = msg.check;
	 		   }
	 		});
		return bool;
	}
	
	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
		    merchantNo: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    limitPayCode: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    limitType: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    dayTransLimit: {
	   	        required: true,
	   	        maxlength: 25,
	   	        isNum:true
	   	      },
		    dayWithdrawalsLimit: {
	   	        required: true,
	   	        maxlength: 25,
	   	        isNum:true
	   	      },
		    singleTransLimit: {
	   	        required: true,
	   	        maxlength: 25,
	   	        isNum:true
	   	      },
		    singleWithdrawalsLimit: {
	   	        required: true,
	   	        maxlength: 25,
	   	        isNum:true
	   	      }
   	    },
   	    messages: {
		    merchantNo: {
	   	        required: "请输入【商户编号】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    limitPayCode: {
	   	        required: "请选择【限额通道】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    limitType: {
	   	        required: "请选择【限额类型】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    dayTransLimit: {
	   	        required: "请输入【全日交易限额】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    dayWithdrawalsLimit: {
	   	        required: "请输入【全日提现限额】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    singleTransLimit: {
	   	        required: "请输入【单笔交易限额】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    singleWithdrawalsLimit: {
	   	        required: "请输入【单笔提现限额】",
	   	        maxlength: "长度不能超过25"
	   	      }
		},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';
   			
   			if(checkMerchantNo()!=true){
   				flag++;
   				errMsg+="商户编号不存在<br>";
   			}
   			
   			if(checkIsSame()!=true){
   				flag++;
   				errMsg+="此商户已存在通道和类型的限额标准<br>";
   			}
   			
   			if(flag == 0){
   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/merchantlimit/save",
   					data:datajson,
   					type:"POST",
   					success:function(msg)
   					{
   						msg = $.parseJSON(msg);
   			 			  bootbox.alert(msg.values,function(){
   			 				 if(msg.executeStatus == '0'){
   			 					location.href=baseUrl+msg.url;
   			 				 }
   			 			  });
   					}
   				});
   			}else{
   				bootbox.alert(errMsg,function(){});
   			}
			return false;
	    },
		errorPlacement: function(error, element) { 
		     error.appendTo(element.parent().parent()); 
		}
	}); 
	
	var text = $("select.form-control");
	for(var i = 0; i < text.size();i++){
		var $this = $(text[i]);
		var id = $this.attr("id");
		var val = $("."+id).val();
		var ops = $this.find("option");
		for(var j = 0; j < ops.size();j++){
			var opval = $(ops[j]).val();
			if(opval == val){
				$(ops[j]).attr("selected","selected");
			}else{
				$(ops[j]).removeClass("selected");
			}
		}
	}
		
});


//1		required:true	                  必须输入的字段。
//2		remote:"check.php"	          使用 ajax 方法调用 check.php 验证输入值。
//3		email:true	                          必须输入正确格式的电子邮件。
//4		url:true	                          必须输入正确格式的网址。
//5		date:true	                          必须输入正确格式的日期。日期校验 ie6 出错，慎用。
//6		dateISO:true	                  必须输入正确格式的日期（ISO），例如：2009-06-23，1998/01/22。只验证格式，不验证有效性。
//7		number:true	                          必须输入合法的数字（负数，小数）。
//8		digits:true				  必须输入整数。
//9		creditcard				  必须输入合法的信用卡号。
//10	equalTo:"#field"	          输入值必须和 #field 相同。
//11	accept:					  输入拥有合法后缀名的字符串（上传文件的后缀）。
//12	maxlength:5				  输入长度最多是 5 的字符串（汉字算一个字符）。
//13	minlength:10			  输入长度最小是 10 的字符串（汉字算一个字符）。
//14	rangelength:[5,10]		  输入长度必须介于 5 和 10 之间的字符串（汉字算一个字符）。
//15	range:[5,10]		  	  输入值必须介于 5 和 10 之间。
//16	max:5		   			  输入值不能大于 5。
//17	min:10					  输入值不能小于 10
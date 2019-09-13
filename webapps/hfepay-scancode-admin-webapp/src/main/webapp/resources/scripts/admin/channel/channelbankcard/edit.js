$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$("#form").find("#cancle").click(function(){
		history.go(-1);
	});
	
	$("#bankCard").on("keyup afterpaste",function(){
		var value = $(this).val();
		$(this).val(value.replace(/\D/g,""));
		//输入超过六位开始条用查询
		if(value.length<2){
			$("#bankName").val("");
			$("#bankCode").val("");
		}
		var baseUrl = $("#baseUrl").text().trim();
		if(value.length>=2){
			$.ajax({
  				type: "POST",
  				url: baseUrl+"/adminManage/merchantbankcard/getBankInfo",
  				data: {"bankCard":value},
  				success:function(msg){
  					if(msg && msg!="" && msg!=null){
  						var card = JSON.parse(msg);
  	  					$("#bankName").val(card.bankName);
  	  					if(card.clearBankChannelNo == null){
  	  						$("#bankCode").removeAttr("readonly");
  	  					}else{
  	  						$("#bankCode").val(card.clearBankChannelNo);
  	  						$("#bankCode").attr("readonly","readonly");
  	  					}
  	  					$("#accountType").val("0");
  	  					$("#accountType").attr("disabled","disabled");
  	  					
  					}
  					else{
  						$("#bankName").val("");
  	  					$("#bankCode").val("");
  	  					$("#accountType").val("");
  	  					$("#bankCode").removeAttr("readonly");
  	  					$("#bankName").removeAttr("readonly");
  	  					$("#accountType").removeAttr("disabled");
  					}
  				}
  			});
		}
	});
	
	jQuery.validator.addMethod("ischina", function(value, element) { 
		   var tel =  /^[u4E00-u9FA5]+$/;
		   return this.optional(element) || (tel.test(value)); 
		   }, "不允许输入中文字符，请重新填写");
	$.validator.addMethod("isNumTwo", function(value, element) { 
		   var tel =  /^[0-9]+(\.[0-9]{1,2})?$/;
		   return this.optional(element) || (tel.test(value)); 
		   }, "请输入整数、小数，且最多两位小数!");
	$.validator.addMethod("isMobile", function(value, element) { 
		   var length = value.length; 
		   var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/; 
		   return this.optional(element) || (length == 11 && mobile.test(value)); 
		 }, "请正确填写您的手机号码"); 

	$.validator.addMethod("isCard", function(value, element) { 
	   var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/; 
	   return this.optional(element) || (tel.test(value)); 
	   }, "身份证格式错误");
	$.validator.addMethod("isNumber", function(value, element) { 
		   var length = value.length; 
		   var number = /^[0-9]*$/; 
		   return this.optional(element) || (number.test(value)); 
		 }, "只能输入数字");
	
	
	/*验证表格*/
	$("#form").validate({
		event:"submit",
	   	    rules: {
	   		channelName: {
		   	        required: true,
		   	        maxlength: 100
		   	      },
		   	   bankCode: {
		   	        required: true,
		   	        maxlength: 20
		   	      },
		   	   bankName: {
		   		    required: true,
		   	        maxlength: 100
		   	      },
		   	   bankCard: {
		   		    required: true,
		   	        maxlength: 50,
		   	        isNumber:true
		   	      },
		   	   idCard: {
		   		    required: true,
		   	        maxlength: 30,
		   	        isCard:true
		   	      },
		   	   name: {
		   		    required: true,
		   	        maxlength: 100
		   	      },
		   	   mobile: {
		   	        maxlength: 20,
		   	        isNumber:true,
		   	        isMobile:true
		   	      },
		   	   balance: {
		   	        isNumTwo:true
		   	      }
	   	    },
	   	    messages: {
		   	   channelName: {
		   	        required: "请输入【渠道名称】",
		   	        maxlength: "长度不能超过100"
		   	      },
		   	   bankCode: {
		   		    required: "请输入【银行开户行代码】",
		   	        maxlength: "长度不能超过20"
		   	      },
		   	   bankName: {
		   		    required: "请输入【开户行银行名称】",
		   	        maxlength: "长度不能超过100"
		   	      },
		   	   bankCard: {
		   	        required: "请输入【银行卡号码】",
		   	        maxlength: "长度不能超过50"
		   	      },
		   	   idCard: {
		   	        required: "请输入【身份证号码】",
		   	        maxlength: "长度不能超过30"
		   	      },
		   	   name: {
		   	        required: "请输入【姓名】",
		   	        maxlength: "长度不能超过100"
		   	      },
		   	   mobile: {
		   	        maxlength: "长度不能超过20"
		   	      },
		   	   balance: {
		   	        maxlength: "长度不能超过10"
		   	      }
			},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';
   			var text = $(".form-horizontal input[val='hidden']");
   			for(var i = 0; i < text.size();i++){
   				var $this = $(text[i]);
   				var val = $this.val();
   				if(val == null || val == ''){
   					flag ++ ;
   					errMsg += $this.attr("name") + "不可为空!";
   				}
   			}
   			if(flag == 0){
   				var baseUrl = $("#baseUrl").text().trim();
   				var isDisabled = $("#accountType").attr("disabled");
   				$("#accountType").removeAttr("disabled");
   				var datajson = $('#form').serialize();
   				$("#accountType").attr("disabled",isDisabled);
   				$.ajax({
   					url:baseUrl+"/adminManage/channelbankcard/save",
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
});



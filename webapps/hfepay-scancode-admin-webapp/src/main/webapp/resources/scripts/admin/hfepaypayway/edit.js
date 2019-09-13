$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$("#cancle").click(function(){
		history.go(-1);
	});
	
	$("#payName").change(function(t){
		var payCode = $("#payName").find("option:selected").attr("payCode");
		$("#form").find("#payCode").val(payCode);
	});
	
	
	$.validator.addMethod("isNumOne", function(value, element) { 
		   var length = value.length; 
		   var number = /^[0-9]+(\.[0-9]+)?$/; 
		   return this.optional(element) || (number.test(value)); 
		 }, "只能输入数字");
	$.validator.addMethod("isNumTwo", function(value, element) { 
		   var tel =  /^[0]+(\.[0-9]{1,5})?$/;
		   var bool = true;
		   if(element.id == 't0Rate'){
			   bool = tel.test(value);
		   }else{
			   bool = tel.test(value) && value!=0;
		   }
		   return this.optional(element) || bool; 
		   }, "请输入0~1之间的小数，且最多五位小数!");
	$.validator.addMethod("isnotSpace", function(value, element) { 
		   var length = value.trim().length; 
		   return this.optional(element) || (length!=0); 
		 }, "不能是空白字符");
	
	function checkIsSame(){
		var payType = $("#payType").val();
		var payCode = $("#payCode").val();
		var id = $("#id").val();
		var bool = false;
		$.ajax({
			   url:"check",
	 		   data:{"payType":payType,"payCode":payCode,"id":id}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(msg){
	 			  msg = $.parseJSON(msg);
	 			  bool = msg.check;
//	 			   if(msg.check==true){
//	 				   return true;
//	 			   }else{
//	 				   return false;
//	 			   }
	 		   }
	 		});
		return bool;
	}
	
	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
   		    payName: {
	   	        required: true
	   	      },
	   	    payType: {
	   	        required: true
	   	      },
	   	    payDesc: {
	   	        required: true,
	   	        isnotSpace: true,
	   	        maxlength: 100
	   	      },
	   	    t0Rate: {
	   	        required: true,
	   	        maxlength: 10,
 			    isNumTwo:true
	   	      },
	   	    t1Rate: {
	   	        required: true,
	   	        maxlength: 10,
 			    isNumTwo:true
	   	      },
	   	    rate: {
	   	        required: true,
	   	        maxlength: 10,
 			    isNumOne:true
	   	      }
   	    },
   	    messages: {
   	    	payName: {
	   	        required: "请输入【支付通道】"
	   	      },
	   	    payType: {
	   	        required: "请输入【通道类型】"
	   	      },
	   	    payDesc: {
	   	        required: "请输入【支付通道描述】",
	   	        maxlength: "长度不能超过100"
	   	      },
	   	    t0Rate: {
	   	        required: "请输入【垫资费率】",
	   	        maxlength: "长度不能超过10"
	   	      },
	   	    t1Rate: {
	   	        required: "请输入【交易手续费】",
	   	        maxlength: "长度不能超过10"
	   	      },
	   	    rate: {
	   	        required: "请输入【提现手续费】",
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
   					errMsg += $this.attr("name") + "不可为空!<br>";
   				}
   			}
   			
   			if(checkIsSame()!=true){
   				flag++;
   				errMsg+="该支付方式已存在";
   			}
   			
   			if(flag == 0){
   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/hfepaypayway/save",
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

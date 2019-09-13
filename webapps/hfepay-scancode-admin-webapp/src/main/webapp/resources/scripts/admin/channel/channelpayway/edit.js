$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$("#channelName").change(function(t){
		var channelNo = $("#channelName").find("option:selected").attr("channelNo");
		$("#form").find("#channelNo").val(channelNo);
	});
	
	$("#payName").change(function(t){
		var payCode = $("#payName").find("option:selected").attr("payCode");
		$("#form").find("#payCode").val(payCode);
	});
	
	$("#form").find("#cancle").click(function(){
		history.go(-1);
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

	
	function checkIsSame(){
		var channelNo = $("#channelNo").val();
		var payCode = $("#payCode").val();
		var id = $("#id").val();
		var bool = false;
		$.ajax({
			   url:"check",
	 		   data:{"channelNo":channelNo,"payCode":payCode,"id":id}, 
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
	
	function checkRate(){
		var payCode = $("#payCode").val();
		var t1Rate = $("#t1Rate").val();
		var t0Rate = $("#t0Rate").val();
		var rate = $("#rate").val();
		var baseUrl = $("#baseUrl").text().trim();
		var bool = false;
		var errMsg = '';
		$.ajax({
			   url:baseUrl+"/adminManage/hfepaypayway/list",
	 		   data:{"payType":2,"payCode":payCode}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(data){	 			 
	 			  var data = $.parseJSON(data);
	 			  var objList = data.objList;
	 			  var obj = data.objList[0];
	 			  if(obj.t1Rate>Number(t1Rate)){
	 				bool = true;
	 				errMsg+="渠道的T1交易费率不能比平台的低<br>";
	 			  }
	 			  if((obj.t0Rate + obj.t1Rate)> (Number(t0Rate) + Number(t1Rate))){
	 				bool = true;
	 				errMsg+="渠道的T0垫资成本、T1交易费率之和不能比平台的低<br>";
	 			  }
	 			  
	 		   }
	 		});
		if(bool){
			bootbox.alert(errMsg,function(){});
		}
		return bool;
	}
	
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
		   	        required: true
		   	      },
		   	   payName: {
		   	        required: true
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
		   	      },
		   	   rateAmount: {
		   	        required: true,
		   	        maxlength: 10,
	 			    isNumOne:true
		   	      }
		   	  
	   	    },
	   	    messages: {
	   	    	channelName: {
	   	    		required: "请选择【渠道编号】",
		   	      },
		   	    payName: {
		   	    	required: "请选择【支付通道】",
		   	      },
	   	    	t0Rate: {
	   	    		required: "请输入【提现手续费率】",
		   	        maxlength: "长度不能超过10"
		   	      },
		   	    t1Rate: {
		   	    	required: "请输入【交易手续费率】",
		   	        maxlength: "长度不能超过10"
		   	      },
		   	    rate: {
		   	        required: "请输入【提现手续费】",
		   	        maxlength: "长度不能超过10"
		   	      },
		   	   rateAmount: {
		   	        required: "请输入【清算手续费】",
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
   				errMsg+="渠道下已存在该支付方式<br>";
   			}
//   			$("#channelName").removeAttr("disabled"); 
   			if(checkRate()){
   				flag++;
   				return; 				
   			}

   			if(flag == 0){
   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/channelpayway/save",
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



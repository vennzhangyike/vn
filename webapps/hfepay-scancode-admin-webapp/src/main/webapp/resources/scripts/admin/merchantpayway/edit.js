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
	
	$("#cancle").click(function(){
		history.go(-1);
	});
	
	function checkIsSame(){
		var merchantNo = $("#merchantNo").val();
		var payCode = $("#payCode").val();
		var id = $("#id").val();
		var baseUrl = $("#baseUrl").text().trim();
		var bool = false;
		$.ajax({
			   url:baseUrl+"/adminManage/merchantpayway/check",
	 		   data:{"merchantNo":merchantNo,"payCode":payCode,"id":id}, 
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
		var merchantNo = $("#merchantNo").val();
		var payCode = $("#payCode").val();
		var t1Rate = $("#t1Rate").val();
		var t0Rate = $("#t0Rate").val();
		var rate = $("#rate").val();
		var baseUrl = $("#baseUrl").text().trim();
		var bool = false;
		var errMsg = '';
		$.ajax({
			   url:baseUrl+"/adminManage/merchantinfo/getMerchantInfo",
	 		   data:{"merchantNo":merchantNo}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(data){	
	 			  var obj = $.parseJSON(data);	 			  
	 			  $.ajax({
	 				   url:baseUrl+"/adminManage/agentpayway/list",
	 		 		   data:{"agentNo":obj.agentNo,"payCode":payCode}, 
	 		 		   type: "POST",
	 		 		   async:false,
	 		 		   success: function(data){
	 		 			  var data = $.parseJSON(data);
	 		 			  var objList = data.objList;
	 		 			  var obj = data.objList[0];
	 		 			  if(obj.t1Rate>Number(t1Rate)){
	 		 				bool = true;
	 		 				errMsg+="商户的T1交易费率不能比代理商的低<br>";
	 		 			  }
	 		 			  if((obj.t0Rate + obj.t1Rate)> (Number(t0Rate) + Number(t1Rate))){
	 		 				bool = true;
	 		 				errMsg+="商户的T0垫资成本、T1交易费率之和不能比平台的低<br>";
	 		 			  }
	 		 			  if(obj.rate>Number(rate)){
	 		 				  bool = true;
	 		 				  errMsg+="商户的提现手续费不能比代理商的低<br>";
	 		 			  }
	 		 		   }
	 		 		});
	 		   }
	 		});
		if(bool){
			bootbox.alert(errMsg,function(){});
		}
		return bool;
	}
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

	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
		    merchantNo: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    payCode: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    payName: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    t0Rate: {
	   	        required: true,
	   	        maxlength: 25,
	   			isNumTwo:true
	   	      },
		    t1Rate: {
	   	        required: true,
	   	        maxlength: 25,
	   			isNumTwo:true
	   	      },
		    rate: {
	   	        required: true,
	   	        maxlength: 25,
 			    isNumOne:true
	   	      },
		    rateAmount: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    temp1: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    temp2: {
	   	        required: true,
	   	        maxlength: 25
	   	      }
   	    },
   	    messages: {
		    merchantNo: {
	   	        required: "请输入【商户编号】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    payCode: {
	   	        required: "请输入【支付通道代码】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    payName: {
	   	        required: "请输入【支付通道名称】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    t0Rate: {
	   	        required: "请输入【T0费率】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    t1Rate: {
	   	        required: "请输入【T1费率】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    rate: {
	   	        required: "请输入【提现手续费率】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    rateAmount: {
	   	        required: "请输入【固定手续费金额】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    temp1: {
	   	        required: "请输入【temp1】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    temp2: {
	   	        required: "请输入【temp2】",
	   	        maxlength: "长度不能超过25"
	   	      }
		},
   	    submitHandler:function(form){   	    	
   			var flag = 0;
   			var errMsg = '';
//   			var text = $(".form-horizontal select");
//   			for(var i = 0; i < text.size();i++){
//   				var $this = $(text[i]);
//   				var val = $this.val();
//   				if(val == null || val == ''){
//   					flag ++ ;
//   				}
//   			}
   			
   			if(checkIsSame()!=true){
   				flag++;
   				errMsg+="商户下已存在该支付方式<br>";
   			}
   			
   			if(checkRate()==true){
   				flag++;
   				return;
   			}
   			
   			if(flag == 0){
   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/merchantpayway/save",
   					data:datajson,
   					type:"POST",
   					success:function(msg)
   					{
   						msg = $.parseJSON(msg);
   						sweetRedirect(msg,msg.url);
   					},
   					error: function(XMLHttpRequest, textStatus, errorThrown) {
   						$("#save").removeAttr("disabled");
   				    }
   				});
   			}else{
   				$("#save").removeAttr("disabled");
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
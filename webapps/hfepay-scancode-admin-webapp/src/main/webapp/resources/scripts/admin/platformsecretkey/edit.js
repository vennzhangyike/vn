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

//	$("#payCode").change(function(t){
//		var payName = $("#payCode").find("option:selected").attr("payName");
//		$("#form").find("#payName").val(payName);
//	});
	
	$("#cancle").click(function(){
		history.go(-1);
	});
	
	$.validator.addMethod("isMobile", function(value, element) { 
		   var length = value.length; 
		   var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/; 
		   return this.optional(element) || (length == 11 && mobile.test(value)); 
		 }, "请正确填写您的手机号码"); 

	$.validator.addMethod("isLegalCard", function(value, element) { 
	   var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/; 
	   return this.optional(element) || (tel.test(value)); 
	   }, "身份证格式错误");

	function checkIsSame(){
		var payCode = $("#payCode").val();
		/*var payName = $("#payName").val();*/
		var id = $("#id").val();
		var bool = false;
		$.ajax({
			   url:"check",
	 		   data:{"payCode":payCode,"id":id}, 
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
		    payCode: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    joinUserPublicKey: {
	   	        required: true,
	   	        maxlength: 4096
	   	      },
		    joinPublicKey: {
	   	        required: true,
	   	        maxlength: 4096
	   	      },
		    joinPrivateKey: {
	   	        required: true,
	   	        maxlength: 4096
	   	      },
		    joinKey: {
	   	        required: true,
	   	        maxlength: 25
	   	      }
   	    },
   	    messages: {
		    payCode: {
	   	        required: "请输入【华付通道编号】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    joinUserPublicKey: {
	   	        required: "请输入【用户上传公钥】",
	   	        maxlength: "长度不能超过4096"
	   	      },
		    joinPublicKey: {
	   	        required: "请输入【平台公钥】",
	   	        maxlength: "长度不能超过4096"
	   	      },
		    joinPrivateKey: {
	   	        required: "请输入【平台私钥】",
	   	        maxlength: "长度不能超过4096"
	   	      },
		    joinKey: {
	   	        required: "请输入【接入加解密KEY】",
	   	        maxlength: "长度不能超过25"
	   	      }
		},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';
   			if(checkIsSame()!=true){
   				flag++;
   				errMsg+="该通道编号已存在";
   			}
   			if(flag == 0){
   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/platformsecretkey/save",
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
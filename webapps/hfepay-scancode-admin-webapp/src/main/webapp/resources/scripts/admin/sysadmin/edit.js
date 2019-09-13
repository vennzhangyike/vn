$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$("#cancle").click(function(){
		history.go(-1);
	});
		
	$(".ajaxCheck").blur(function(){
		var $this = $(this);
		var name = $this.attr("id");
		if($("#" + name+ "-error").text() == ''){		 				  
			$this.parent().parent().find("label:gt(0)").remove();
		
			var val = $this.val();
			var placeholder = $this.attr("placeholder");
			var flag = 0;
			var errMsg = '';
			if(val == null || val == ''){
				flag++;
				errMsg = '为空';
			}
			var tel =  /\w{0,32}$/;
			if(!tel.test(val)){
				flag++;
				errMsg = '需是数字、字母、下划线，且长度不可超过32';
			}
			tel =  /[@#\$%\^&\*]+/g;
			if(tel.test(val)){
				flag++;
				errMsg = '不能包含特殊字符';
			}
			if(flag == 0){
				$.ajax({
			 		   type: "POST",
			 		   url: "list",
			 		   data: name + "=" + val,
			 		   async:false,
			 		   success: function(msg){
			 			  
			 			  msg = $.parseJSON(msg);
			 			  if(msg.objList.length > 1){
			 				 flag++;
			 				  errMsg = '已存在';
			 			  }
			 			  if(msg.objList.length == 1){
			 				 var idVal = $("#id").val();
			 				 if(idVal == null || idVal == ''){
				 				  flag++;
				 				  errMsg = '已存在';
				 			 }else{
				 				 var off = msg.objList[0];
				 				 if(idVal != off.id){
				 					flag++;
				 					errMsg = '已存在';
				 				 }
				 			 }
			 			  }
			 		   }
			 		});
			}
			if(flag == 0){
				 $this.parent().parent().append('<label id="' + name + '-success" class="success" for="' + name + '">' + placeholder + '可用！</label>');
			  }else{
				 $this.parent().parent().append('<label id="' + name + '-error" class="error" for="' + name + '">' + placeholder + errMsg +'，不可用！</label>');
			  }
		}else{
			$("#" + name+ "-success").remove();
		}
		 
	});
	
	$.validator.addMethod("isNumChar", function(value, element) { 
		   var tel =  /^[\w_]*$/;
		   return this.optional(element) || (tel.test(value)); 
		   }, "请输入字母、数字、下划线!");
	$.validator.addMethod("isIllegalString", function(value, element) { 
		   var tel = /[@#\$%\^&\*]+/g; 
		   return this.optional(element) || (!tel.test(value)); 
		   }, "不能使用非法字符");
	$.validator.addMethod("isMobile", function(value, element) { 
		   var length = value.length; 
		   var mobile = /(^\d{3,4}-?\d{3,4}-?\d{3,4}$)|(^1[34578]{1}[0-9]{9}$)/;
		   return this.optional(element) || (mobile.test(value)); 
		 }, "请正确填写您的电话号码");
	$.validator.addMethod("isnotSpace", function(value, element) { 
		   var length = value.trim().length; 
		   return this.optional(element) || (length!=0); 
		 }, "不能是空白字符");

	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
   		    userName:{
   		    	required: true,
   		    	maxlength: 25,
   		    	isNumChar:true
   		    },
		    shortName: {
	   	        required: true,
	   	        isIllegalString: true,
	   	        isnotSpace: true,
	   	        maxlength: 25
	   	      },
		    email: {
	   	        email:true,
	   	        maxlength: 50
	   	      },
		    phone: {
		    	required:false,
	   	        isIllegalString:true,
	   	        maxlength: 32,
	   	        isMobile: true
	   	      },
		    password: {
	   	        maxlength: 25
	   	      },
	   	   passwordstr: {
	   	        maxlength: 25,
	   	      }
   	    },
   	    messages: {
   	    	userName:{
   		    	required: "请输入【账户】",
   		    	maxlength: "长度不能超过25"
   		    },
		    shortName: {
	   	        required: "请输入【账户简称】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    email: {
	   	        email:"请输入正确的邮箱格式",
	   	        maxlength: "长度不能超过50"
	   	      },
		    phone: {
		    	required:"请输入【联系电话】",
	   	        maxlength: "长度不能超过32"
	   	      },
		    password: {
	   	        maxlength: "长度不能超过25"
	   	      },
	   	   passwordstr: {
	   	        maxlength: "长度不能超过25"
	   	      }
		},
   	    submitHandler:function(form){$("#save").attr("disabled","disabled");
   			var flag = 0;
   			var errMsg = '';
   			var id = $("#id").val();
   			var password = $("#password").val();
   			if((id == '' || id == null) && (password == '' || password == null) ){
   				flag++;
   				errMsg="请输入密码！";
   			}
   			
   			if(password != '' && password != null ){
   				var tel =  /\w$/;
   			    if(!tel.test(password)){
   				   flag++;
   				   errMsg="密码请输入字母、数字、下划线!";
   			    }else{
   			    	var passwordstr = $("#passwordstr").val();
   	   			    if(password.trim() != passwordstr.trim()){
   	   			    	flag++;
   	    				errMsg="两次输入的密码需要一样!";
   	   			    }
   			    }
   			}else{
   				flag++;
   				errMsg="请输入密码！";
   			}
   			
   			if($('#id').val()==""){
   				$.ajax({
 		 		   type: "POST",
 		 		   url: "list",
 		 		   data: "userName=" + $('#userName').val(),
 		 		   async:false,
 		 		   success: function(msg){
 		 			  msg = $.parseJSON(msg);
 		 			  if(msg.objList.length > 0){
 		 				 flag++;
 		 				  errMsg = '账号已存在';
 		 			  }
 		 		   }
 		 		});
   			}
   			
   			if(flag == 0){
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/sys/admin/save",
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
   				$("#save").removeAttr("disabled");bootbox.alert(errMsg,function(){});
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
		var flag = 0;
		for(var j = 0; j < ops.size();j++){
			var opval = $(ops[j]).val();
			var opval1 = $(ops[j]).attr("val");
			if(opval == val || opval1 == val){
				$(ops[j]).attr("selected","selected");
				flag++;
			}else{
				$(ops[j]).removeClass("selected");
			}
		}
		if(flag == 0 && val != ''){
			$this.parent().parent().parent().parent().hide();
			$this.attr("name","xxx");
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
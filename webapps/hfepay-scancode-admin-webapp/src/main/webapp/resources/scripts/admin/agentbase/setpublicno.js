$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	var ue = UE.getEditor('editor');
	var helpInfoEditor = UE.getEditor('helpInfoEditor');
	var agreementEditor = UE.getEditor('agreementEditor');
	
	$("#cancle").click(function(){
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
	
	function checkChannelCode(channelCode){
		var channelCode = channelCode;
		var bool = false;
		var baseUrl = $("#baseUrl").text().trim();
		$.ajax({
			   url:baseUrl+"/adminManage/channelexpand/list",
	 		   data:{"channelCode":channelCode}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(data){
	 			  var json = JSON.parse(data); 
	 			  if(json.objList.length>0){
	 				  var id = $("#id").val();
	 				  if(id != "" && id != json.objList[0].id){
	 					 bool = true;
	 				  }	 
	 				  if(id == ""){
	 					 bool = true;
	 				  }
	 			  }
	 			  
	 		   }
	 		});
		return bool;
	}
	
	$("#channelCode").blur(function(){
		var $this = $(this);
		var name = $this.attr("id");
		var errMsg = '二级域名编号已存在';
		if($("#channelCode").val()!="" && checkChannelCode($("#channelCode").val())){
			 $("#" + name+ "-error").remove();
			 $this.parent().parent().append('<label id="' + name + '-error" class="error" for="' + name + '">' + errMsg +'，不可用！</label>');
		}
	});
	
	$(".ajaxCheck").blur(function(){
		var $this = $(this);
		$this.parent().parent().find("label:gt(0)").remove();
		var val = $this.val();
		var name = $this.attr("id");
		var placeholder = $this.attr("placeholder");
		var flag = 0;
		var errMsg = '';
		if(val == null || val == ''){
			flag++;
			errMsg = '为空';
		}
		var tel =  /\w$/;
		if(!tel.test(val)){
			flag++;
			errMsg = '需是数字、字母、下划线';
		}
		tel =  /[@#\$%\^&\*]+/g;
		if(tel.test(val)){
			flag++;
			errMsg = '不能包含特殊字符';
		}
		$.ajax({
	 		   type: "POST",
	 		   url: "list",
	 		   data: name + "=" + val,
	 		   async:false,
	 		   success: function(msg){
	 			  $this.parent().parent().find("label:gt(0)").remove();
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
		 if(flag == 0){
			 $this.parent().parent().append('<label id="' + name + '-success" class="success" for="' + name + '">' + placeholder + '可用！</label>');
		  }else{
			 $this.parent().parent().append('<label id="' + name + '-error" class="error" for="' + name + '">' + placeholder + errMsg +'，不可用！</label>');
		  }
	});
	
	$(".ajaxCheck2").blur(function(){
		var $this = $(this);
		$this.parent().parent().find("label:gt(0)").remove();
		var val = $this.val();
		var name = $this.attr("id");
		var placeholder = $this.attr("placeholder");
		var flag = 0;
		var errMsg = '';
		if(val == null || val == ''){
			flag++;
			errMsg = '为空';
		}
		var tel =  /^\w{4}$/;
		if(!tel.test(val)){
			flag++;
			errMsg = '需是数字、字母、下划线,且只能是四位数';
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
		 			  $this.parent().parent().find("label:gt(0)").remove();
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
	});
	
	$(".ajaxCheck1").blur(function(){
		var $this = $(this);
		$this.parent().parent().find("label:gt(0)").remove();
		var val = $this.val();
		var name = $this.attr("id");
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
		 		   url: "../sys/admin/list",
		 		   data: name + "=" + val,
		 		   async:false,
		 		   success: function(msg){
		 			  $this.parent().parent().find("label:gt(0)").remove();
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
	});
	
//	$.validator.addMethod("isChar", function(value, element) { 
//		   var length = value.length; 
//		   var mobile = /[A-Za-z]/; 
//		   return this.optional(element) || (mobile.test(value)); 
//		 }, "只能输入字母");
	
	jQuery.validator.addMethod("ischina", function(value, element) { 
		   var tel =  /^[^\u4e00-\u9fa5]{0,}$/;
		   return this.optional(element) || (tel.test(value)); 
		   }, "不允许输入中文字符，请重新填写");
	$.validator.addMethod("isNumTwo", function(value, element) { 
		   var tel =  /^[0-9]+(\.[0-9]{1,2})?$/;
		   return this.optional(element) || (tel.test(value)); 
		   }, "请输入整数、小数，且最多两位小数!");
	$.validator.addMethod("isMobile", function(value, element) { 
		   var length = value.length; 
		   var mobile = /(^\d{3,4}-?\d{3,4}-?\d{3,4}$)|(^1[34578]{1}[0-9]{9}$)/;
		   return this.optional(element) || (mobile.test(value)); 
		 }, "请正确填写您的电话号码"); 

	$.validator.addMethod("isCard", function(value, element) { 
	   var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/; 
	   return this.optional(element) || (tel.test(value)); 
	   }, "身份证格式错误");
	$.validator.addMethod("isNumber", function(value, element) { 
		   var length = value.length; 
		   var number = /^[0-9]*$/; 
		   return this.optional(element) || (number.test(value)); 
		 }, "只能输入数字");
	$.validator.addMethod("isnotSpace", function(value, element) { 
		   var length = value.trim().length; 
		   return this.optional(element) || (length!=0); 
		 }, "不能是空白字符");

	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
   		   	channelName: {
	   	        required: true,
	   	        isnotSpace: true,	   	        
	   	        maxlength: 50
	   	      },
	   	    channelCode: {
	   	        required: true,
	   	        isnotSpace: true,
	   	        maxlength: 30
	   	      },
	   	    channelPreCode: {
	   	        required: true,
	   	        isnotSpace: true,
	   	        maxlength: 4
	   	      },
		    nickName: {
	   	        required: true,
	   	        isnotSpace: true,
	   	        maxlength: 30
	   	      },
		    address: {
//	   	        required: true,
	   	        maxlength: 100
	   	      },
		    phone: {
	   	        required: true,
	   	        isnotSpace: true,
	   	        maxlength: 25,
	   	        isMobile: true
	   	      },
		    technicalSupportEmail: {
//	   	        required: true,
	   	        maxlength: 30,
	   	        email:true
	   	      },
		    qqGroup: {
//	   	        required: true,
	   	        maxlength: 100
	   	      },
		    customServiceQq: {
//	   	        required: true,
	   	        maxlength: 100
	   	      },
		    businessCooperationQq: {
	   	        required: true,
	   	        isnotSpace: true,
	   	        maxlength: 100
	   	      },
		    businessCooperationEmail: {
//	   	        required: true,
	   	        maxlength: 100,
	   	        email:true
	   	      },
		    microblogUrl: {
//	   	        required: true,
	   	        maxlength: 200
	   	      },
	   	    companyName: {
	   	        required: true,
	   	        isnotSpace: true,
	   	        maxlength: 250
	   	      },
		    preCode: {
	   	        required: true,
	   	        isnotSpace: true,
	   	        isChar:true
	   	      },
		    recordNumber: {
//	   	        required: true,
	   	        maxlength: 50
	   	      },
		    remark: {
	   	        maxlength: 500
	   	      },
	   	    userName:{
	   	    	required: true
	   	      },
	   	    roleId:{
	   	    	required: true
	   	      },
	   	    password:{
	   	    	required: true
	   	      },
	   	    bankCode: {
	   	        required: true,
	   	        isnotSpace: true,
	   	        maxlength: 20
	   	      },
	   	    bankName: {
	   		    required: true,
	   	        isnotSpace: true,
	   	        maxlength: 100
	   	      },
	   	    bankCard: {
	   		    required: true,
	   	        isnotSpace: true,
	   	        maxlength: 25,
	   	        isNumber:true
	   	      },
	   	    idCard: {
	   		    required: true,
	   	        isnotSpace: true,
	   	        maxlength: 30,
	   	        isCard:true
	   	      },
	   	    name: {
	   		    required: true,
	   	        isnotSpace: true,
	   	        maxlength: 10
	   	      },
	   	    mobile: {
	   	        maxlength: 20,
	   	        isMobile: true
	   	      },
	   	   telephone: {
	   	        maxlength: 20,
	   	        isMobile: true
	   	      },
	   	   accountType: {
	   		    required: true
	   	      },
	   	   appid: {
	   		    required: true,
	   	        isnotSpace: true,
	   	        maxlength: 100
	   	      },
	   	   secret: {
	   		    required: true,
	   	        isnotSpace: true,
	   	        maxlength: 100
	   	      },
	   	   registerTemplateId: {
	   		    required: true,
	   	        isnotSpace: true,
	   	        maxlength: 100
	   	      },
	   	   payTemplateId: {
	   		    required: true,
	   	        isnotSpace: true,
	   	        maxlength: 100
	   	      },
	   	   withDrawsTemplateId: {
	   		    required: true,
	   	        isnotSpace: true,
	   	        maxlength: 100
	   	      }
   	    },
   	    messages: {
   	    	channelName: {
	   	        required: "请输入【渠道名称】",
	   	        maxlength: "长度不能超过50"
	   	      },
	   	    channelCode: {
	   	        required: "请输入【二级域名编号】",
	   	        maxlength: "长度不能超过30"
	   	      },
	   	    channelPreCode: {
	   	        required: "请输入【编码抬头】",
	   	        maxlength: "长度不能超过4"
	   	      },
		    number: {
	   	        required: "请输入【渠道编号】",
	   	        maxlength: "长度不能超过50"
	   	      },
		    nickName: {
	   	        required: "请输入【昵称】",
	   	        maxlength: "长度不能超过30"
	   	      },
		    address: {
	   	        required: "请输入【地址】",
	   	        maxlength: "长度不能超过100"
	   	      },
		    phone: {
	   	        required: "请输入【电话】",
	   	        maxlength: "长度不能超过30"
	   	      },
		    technicalSupportEmail: {
//	   	        required: "请输入【技术支持邮箱】",
	   	        maxlength: "长度不能超过100",
	   	        email:"请输入正确的邮箱格式"
	   	      },
		    qqGroup: {
//	   	        required: "请输入【QQ群 多个逗号隔开】",
	   	        maxlength: "长度不能超过100"
	   	      },
		    customServiceQq: {
	   	        required: "请输入【客服QQ】",
	   	        maxlength: "长度不能超过100"
	   	      },
		    businessCooperationQq: {
	   	        required: "请输入【商务QQ】",
	   	        maxlength: "长度不能超过100"
	   	      },
		    businessCooperationEmail: {
//	   	        required: "请输入【商务邮箱】",
	   	        maxlength: "长度不能超过100",
	   	        email:"请输入正确的邮箱格式"
	   	      },
		    microblogUrl: {
//	   	        required: "请输入【官方微博URL】",
	   	        maxlength: "长度不能超过100"
	   	      },
	   	    companyName: {
	   	        required: "请输入【公司名称】",
	   	        maxlength: "长度不能超过250"
	   	      },
		    preCode: {
	   	        required: "请输入【编码前缀】"
	   	      },
		    recordNumber: {
//	   	        required: "请输入【备案号】",
	   	        maxlength: "长度不能超过50"
	   	      },
		    remark: {
	   	        maxlength: "长度不能超过500"
	   	      },
	   	    userName:{
	   	    	required: "请输入【账号】"
	   	      },
	   	    roleId:{
	   	    	required: "请选择【角色】"
	   	      },
	   	    password:{
	   	    	required: "请输入【密码】"
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
	   	        maxlength: "长度不能超过25"
	   	      },
	   	    idCard: {
	   	        required: "请输入【身份证号码】",
	   	        maxlength: "长度不能超过30"
	   	      },
	   	    name: {
	   	        required: "请输入【姓名】",
	   	        maxlength: "长度不能超过10"
	   	      },
	   	    mobile: {
	   	        maxlength: "长度不能超过20"
	   	      },
	   	   telephone: {
	   	        maxlength: "长度不能超过20"
	   	      },
	   	   accountType: {
	   		   required: "请选择【是否对公对私】"
	   	      },
	   	    appid: {
		   		   required: "请选择【公众号ID】",
		   	       maxlength: "长度不能超过100"
		   	      },
		   	secret: {
		   		   required: "请选择【公众号key】",
		   	       maxlength: "长度不能超过100"
		   	      },
		   	registerTemplateId: {
		   		   required: "请选择【注册消息模板ID】",
		   	       maxlength: "长度不能超过100"
		   	      },
		   	payTemplateId: {
		   		   required: "请选择【支付消息模板ID】",
		   	       maxlength: "长度不能超过100"
		   	      },
		   	withDrawsTemplateId: {
		   		   required: "请选择【提现模版ID】",
		   	       maxlength: "长度不能超过100"
		   	      }
		},
   	    submitHandler:function(form){
   	    	
   			var flag = 0;
   			var errMsg = '';
   			if(checkChannelCode($("#channelCode").val())){
   				flag++;
				errMsg = "该二级域名编号已在渠道中使用！";
   			}
   			
//   			var text = $(".form-horizontal input[val='hidden']");
//   			for(var i = 0; i < text.size();i++){
//   				var $this = $(text[i]);
//   				var val = $this.val();
//   				if(val == null || val == ''){
//   					flag ++ ;
//   					errMsg += $this.attr("name") + "不可为空!";
//   				}
//   			}
   			
//   			var idNum = $("#id").val();
//   			if(idNum == '' || idNum == null){
//   				var password = $("#password").val();
//   	   			if(password == '' || password == null ){
//   	   				flag++;
//   	   				errMsg="请输入密码！";
//   	   			}
//   	   			
//   	   			if(password != '' && password != null ){
//   	   				var tel =  /\w$/;
//   	   			    if(!tel.test(password)){
//   	   				   flag++;
//   	   				   errMsg="请输入字母、数字、下划线!";
//   	   			    }
//   	   			}
//   			}
   			
   			if(!UE.getEditor('editor').hasContents()){
   				flag++;
   				errMsg = "请输入关于我们！";
   			}else{
   				$("#aboutUs").val(UE.getEditor('editor').getContent());
   			}
   			
   			if(!UE.getEditor('helpInfoEditor').hasContents()){
   				flag++;
   				errMsg = "请输入帮助中心！";
   			}else{
   				$("#helpInfo").val(UE.getEditor('helpInfoEditor').getContent());
   			}
   			
   			if(!UE.getEditor('agreementEditor').hasContents()){
   				flag++;
   				errMsg = "请输入电子协议内容！";
   			}else{
   				$("#agreement").val(UE.getEditor('agreementEditor').getContent());
   			}
   			
   			
   			if(flag == 0){
   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				$("#accountType").removeAttr("disabled");
   				var datajson = $('#form').serialize();
   				$("#accountType").attr("disabled","disabled");
   				$.ajax({
   					url:baseUrl+"/adminManage/agentbase/savepublicno",
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
		
	$("#editor").html($("#aboutUs").val());
	$("#helpInfoEditor").html($("#helpInfo").val());
	$("#agreementEditor").html($("#agreement").val());
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
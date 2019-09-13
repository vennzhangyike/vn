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
	
	selectUtil.initAgent();
	
	handfunction();
	initSelect();
	initRate();
	
	
	
	function initRate(){
		hasParentRate();
	}
	
	$("#agentNo").change(function(){
		$("#-error").remove();
		handfunction();
	});
	
	$("#payCode").change(function(){
		hasParentRate();
	});
	
	function handfunction(){
		var select = $("#payCode option")[0];
		$("#payCode option").remove();
		$("#payCode").append(select);
		var agentNo =  $("#agentNo").val();
		if(agentNo==''){
			return;
		}
		$.ajax({
			url : "hasParentPayway",
			data : "agentNo=" + agentNo,
			type : "POST",
			async : false,
			success: function(msg){
	 			msg = $.parseJSON(msg);
	 			if(msg.executeStatus == '0'){
	 				var list = msg.resultList[0];
		 			var select = $("#payCode");
		 			$.each(list, function(key, value) { 
		 				var option = $('<option value=\''+key+'\'>'+value+'</option>');
 						select.append(option);
		 			});
		 			
	 			}else{
	 				$("#agentNo").parent().parent().append('<label id="' + name + '-error" class="error" for="' + name + '">当前代理商的上级机构没有设置支付方式</label>');
	 			}
	 		}
		});
	}
	
	function initSelect(){
		var payCode = $("#payCodeVal").val();
		$('#payCode option[value="'+payCode+'"]').attr('selected','selected');
		if(payCode != ""){
			$('#payCode').attr('disabled','disabled');
		}
	}
	
	function hasParentRate(){
		var agentNo =  $("#agentNo").val();
		var payCode = $("#payCode").val();
		var id = $("#id").val();
		if(agentNo=='' || payCode ==''){
			return;
		}
		$.ajax({
			url : "hasParentRate",
			data:{"agentNo":agentNo,"payCode":payCode}, 
			type : "POST",
			async : false,
			success: function(msg){
	 			msg = $.parseJSON(msg);
	 			if(msg.executeStatus == '0'){
	 				var list = msg.resultMap;
		 			$.each(list, function(key, value) { 
		 				if(key=="t0"){
		 					$("#t0").val(value);
		 				}
		 				if(key=="t1"){
		 					$("#t1").val(value);
		 				}
		 				if(key=="rat"){
		 					$("#rat").val(value);
		 				}
		 			});
		 			if(id==''){
		 				$('#payCode').attr('disabled',false);
			 			$('#agentNo').attr('disabled',false);
		 			}else{
		 				$('#payCode').attr('disabled','disabled');
			 			$('#agentNo').attr('disabled','disabled');
		 			}
	 			}
	 		}
		});
	}
	
	
	function checkIsSame(){
		var agentNo = $("#agentNo").val();
		var payCode = $("#payCode").val();
		var id = $("#id").val();
		var bool = false;
		$.ajax({
			   url:"check",
	 		   data:{"agentNo":agentNo,"payCode":payCode,"id":id}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(msg){
	 			  msg = $.parseJSON(msg);
	 			  bool = msg.check;
	 		   }
	 		});
		return bool;
	}
	
	function checkMerchantRate(){
		var agentNo = $("#agentNoVal").val();
		var t0Rate = $("#t0Rate").val();
		var t1Rate = $("#t1Rate").val();
		var rate = $("#rate").val();
		var payCode = $("#payCode").val();
		var bool = false;
		$.ajax({
			url:"checkMerchantRate",
			data:{"agentNo":agentNo,"t0Rate":t0Rate,"t1Rate":t1Rate,"rate":rate,"payCode":payCode}, 
			type:"POST",
			async:false,
			success:function(msg){
				msg = $.parseJSON(msg);
		 		bool = msg.check;
			}
		});
		return bool;
	}
	
	
	$.validator.addMethod("isMobile", function(value, element) { 
		   var length = value.length; 
		   var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/; 
		   return this.optional(element) || (length == 11 && mobile.test(value)); 
		 }, "请正确填写您的手机号码"); 

	$.validator.addMethod("isLegalCard", function(value, element) { 
	   var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/; 
	   return this.optional(element) || (tel.test(value)); 
	   }, "身份证格式错误");
	
	$.validator.addMethod("isNumTwo", function(value, element) { 
		   var tel = /^[0]$|^0\.[0-9]{1,5}$/;
		   return this.optional(element) || (tel.test(value)); 
		   }, "请输入0~1之间的小数，且最多五位小数!");
	$.validator.addMethod("isNum", function(value, element) { 
		   var tel = /^0\.[0-9]{1,5}$/;
		   return this.optional(element) || (tel.test(value)); 
		   }, "请输入0~1之间的小数，且最多五位小数!");
	/*验证表格*/
	$("#form").validate({
        event:"blur",
   	    rules: {
		    agentNo: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    agentName: {
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
	   	        isNum:true
	   	      },
		    rate: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    rateAmount: {
	   	        required: true,
	   	        maxlength: 25
	   	      }
   	    },
   	    messages: {
		    agentNo: {
	   	        required: "请输入【代理商编号】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    agentName: {
	   	        required: "请输入【代理商名称】",
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
	   	      }
		},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';
//   			if(selectUtil.isNullAgentNo()){
//   				return ;
//   			}
   			
   			if(checkIsSame()!=true){
   				flag++;
   				errMsg+="代理商已存在该支付方式<br>";
   			}
   			var t0 = $("#t0").val();
   			var t1 = $("#t1").val();
   			var rat = $("#rat").val();
   			var t0Rate = $("#t0Rate").val();
   			var t1Rate = $("#t1Rate").val();
   			var rate = $("#rate").val();
   			if(t1Rate<t1){
   				flag++;
   				errMsg+="t1交易费率必须大于等于上级机构<br>";
   			}
   			if((parseFloat(t0Rate)+parseFloat(t1Rate)).toFixed(5)<(parseFloat(t0)+parseFloat(t1)).toFixed(5)){
   				flag++;
   				errMsg+="t0,t1之和必须大于等于上级机构之和<br>";
   			}
   			if(rate<rat){
   				flag++;
   				errMsg+="提现手续费必须大于等于上级机构<br>";
   			}
   			if(checkMerchantRate()!=true){
   				flag++;
   				errMsg+="代理商的交易费率和垫资成本、提现手续费不能大于商戶的<br>";
   			}
   			if(flag == 0){
   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/agentpayway/save",
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
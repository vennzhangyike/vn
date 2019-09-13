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

	handfunction();
	initSelect();
	$("#agentLevel").change(function(){
		$("#form").find("#agentNo").val('');
		$("#-error").remove();
		handfunction();
	});
	
	function handfunction(){
		var select = $("#parentNo option")[0];
		$("#parentNo option").remove();
		$("#parentNo").append(select);
		var agentLevel =  $("#agentLevel").val();
		var channelNo =  $("#channelNo").val();
		var id =  $("#id").val();
		if(agentLevel>1){
			$.ajax({
				url : "checkAgentLevel",
				data : {"agentLevel":agentLevel,"channelNo":channelNo},
				type : "POST",
				async : false,
				success: function(msg){
		 			msg = $.parseJSON(msg);
		 			if(msg.executeStatus == '0'){
		 				if(id==''){
		 					$("#form").find("#parentNo").attr("disabled", false);
		 				}else{
		 					$("#form").find("#parentNo").attr("disabled", true);
		 				}
		 				var list = msg.resultList[0];
			 			var select = $("#parentNo");
			 			$.each(list, function(key, value) { 
			 				var option = $('<option value=\''+key+'\'>'+key+'+'+value+'</option>');
	 						select.append(option);
			 			});
		 			}else{
		 				$("#agentLevel").parent().parent().append('<label id="' + name + '-error" class="error" for="' + name + '">代理商级别没有相对应的上级机构!</label>');
		 			}
		 		}
			});
		}else if(agentLevel==1){
			if(id==''){
				$("#form").find("#parentNo").attr("disabled", false);
			}else{
				$("#form").find("#parentNo").attr("disabled", true);
			}
			var channelName = $("#channelNo").find("option:selected").attr("channelName");
			var channelNo = $("#form").find("#channelNo").val();
			var select = $("#parentNo");
			var option = $('<option value=\''+channelNo+'\' selected="selected" >'+channelNo+'+'+channelName+'</option>');
			select.append(option);
			if(id==''){
				$.ajax({
					url : "hasPreCode",
					data : {"agentLevel":agentLevel,"parentNo":channelNo},
					type : "POST",
					async : false,
					success: function(msg){
			 			msg = $.parseJSON(msg);
			 			if(msg.executeStatus == '0'){
			 				var result = msg.result;
			 				$("#form").find("#agentNo").val(result);
			 			}
			 		}
				});
			}
		}
	}
	
	function initSelect(){
		var parentNo = $("#parentNoVal").val();
		$('#parentNo option[value="'+parentNo+'"]').attr('selected','selected')
	}
	
	$("#channelNo").change(function(){
		$("#-error").remove();
		var select = $("#parentNo option")[0];
		$("#parentNo option").remove();
		$("#parentNo").append(select);
		$("#form").find("#agentLevel").val('');
		var channelNo =  $("#channelNo").val();
		if(channelNo != ''){
			$("#form").find("#agentLevel").attr("disabled", false);
		}else{
			$("#form").find("#agentLevel").attr("disabled", true);
		}
//		var agentLevel =  $("#agentLevel").val();
//		var channelNo =  $("#channelNo").val();
//		if(agentLevel==1){
//			$("#form").find("#parentNo").val(channelNo);
//		}
	});
	
	
	$("#parentNo").change(function(){
		var parentNo = $('#parentNo option:selected').val();
		var agentLevel = $('#agentLevel option:selected').val();
		$.ajax({
			url : "hasPreCode",
			data : {"agentLevel":agentLevel,"parentNo":parentNo},
			type : "POST",
			async : false,
			success: function(msg){
	 			msg = $.parseJSON(msg);
	 			if(msg.executeStatus == '0'){
	 				var result = msg.result;
	 				$("#form").find("#agentNo").val(result);
	 			}
	 		}
		});
	});
	
//	
//	$("#agentLevel").change(function(){
//		var agentLevel =  $("#agentLevel").val();
//		var bool = false;
//		if(agentLevel>1){
//			$.ajax({
//				url : "checkAgentLevel",
//				data : "agentLevel=" + agentLevel,
//				type : "POST",
//				async : false,
//				success: function(msg){
//					$("#-error").remove();
//		 			msg = $.parseJSON(msg);
//		 			bool = msg.check;
//		 		}
//			});
//			
//			if(!bool){
//				$("#agentLevel").parent().parent().append('<label id="' + name + '-error" class="error" for="' + name + '">代理商级别没有相对应的上级机构！</label>');
//				$("#form").find("#parentNo").val('');
//			 }else{
//				 $("#-error").remove();
//				 $("#form").find("#parentNo").attr("readonly", false);
//			 }
//		}else{
//			$("#-error").remove();
//			var channelNo =  $("#channelNo").val();
//			if(channelNo == ''){
//				$("#agentLevel").parent().parent().append('<label id="' + name + '-error" class="error" for="' + name + '">请先选择渠道信息！</label>');
//			}
//			$("#form").find("#parentNo").val(channelNo);
//		}
//	});
	
	$.validator.addMethod("isMobile", function(value, element) { 
		   var length = value.length; 
		   var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/; 
		   return this.optional(element) || (length == 11 && mobile.test(value)); 
		 }, "请正确填写您的手机号码"); 

	$.validator.addMethod("isLegalCard", function(value, element) { 
	   var tel = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/; 
	   return this.optional(element) || (tel.test(value)); 
	   }, "身份证格式错误");
	
	$.validator.addMethod("isEnglish", function(value, element) { 
		   var tel = /^[A-Za-z ]+$/; 
		   return this.optional(element) || (tel.test(value)); 
		   }, "只能输入英文字母");

	$.validator.addMethod("isIllegalString", function(value, element) { 
		   var tel = /[@#\$%\^&\*]+/g; 
		   return this.optional(element) || (!tel.test(value)); 
		   }, "不能使用非法字符");

	$.validator.addMethod("isBlank", function(value, element) { 
		 return $.trim(value) != '';
		   }, "不能为空");
	
	$.validator.addMethod("isPhone", function(value, element) { 
		   var length = value.length; 
		   var mobile =/(^\d{3,4}-?\d{3,4}-?\d{3,4}$)|(^1[34578]{1}[0-9]{9}$)/;
		   return this.optional(element) || (mobile.test(value)); 
		 }, "请正确填写您的手机号码"); 


	/*验证表格*/
	$("#form").validate({
        event:"submit",
   	    rules: {
   		   channelNo: {
   			   required: true,
   			   maxlength: 25
   		   	  },
		    agentLevel: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
			parentNo: {
		   	    required: true,
		   	    maxlength: 25
		   	  },
		    agentNo: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    agentName: {
	   	        required: true,
	   	        maxlength: 25,
	   	        isIllegalString:true,
	   	        isBlank:true
	   	      },
		    agentPreCode: {
	   	        required: true,
	   	        maxlength: 5,
	   	        isEnglish:true,
	   	        isIllegalString:true,
	   	        isBlank:true
	   	      },
		    agentType: {
	   	        required: true,
	   	        maxlength: 25
	   	      },
		    name: {
	   	        required: true,
	   	        maxlength: 25,
	   	        isIllegalString:true,
	   	        isBlank:true
	   	      },
		    mobile: {
	   	        required: true,
	   	        maxlength: 13,
	   	        isPhone:true,
	   	        isIllegalString:true,
	   	        isBlank:true
	   	      }
   	    },
   	    messages: {
   	    	channelNo: {
	   	        required: "请选择【渠道名称】",
	   	        maxlength: "长度不能超过25"
	   	      },
			agentLevel: {
		   	    required: "请选择【代理商级别】",
		   	    maxlength: "长度不能超过25"
		   	  },
			parentNo: {
		   	    required: "请输入【上级机构】",
		   	    maxlength: "长度不能超过25"
		   	  },
		    agentNo: {
	   	        required: "请输入【代理商编号】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    agentName: {
	   	        required: "请输入【代理商名称】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    agentPreCode: {
	   	        required: "请输入【编码抬头】",
	   	        maxlength: "长度不能超过5"
	   	      },
		    agentType: {
	   	        required: "请输入【代理商类型：0 个人、1 机构】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    name: {
	   	        required: "请输入【联系人姓名】",
	   	        maxlength: "长度不能超过25"
	   	      },
		    mobile: {
	   	        required: "请输入【联系人手机号码】",
	   	        maxlength: "长度不能超过13"
	   	      }
		    
		},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';

   			if(flag == 0){
   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/agentbase/save",
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


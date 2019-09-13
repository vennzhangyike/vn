$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目		
	
	$('.date-picker').datetimepicker({
		format: 'yyyy-mm-dd hh:ii',
        weekStart: 0,
        autoclose: true,
        todayHighlight: true,
        defaultDate : new Date(),
        startDate:new Date(),
        keyboardNavigation: true,
        todayBtn: 'linked',
        clearBtn: 'linked',
        language: 'zh-CN'
	});
	
	$("#form").find("#cancle").click(function(){
		var baseUrl = $("#baseUrl").text().trim();
		window.location.href= baseUrl + "/adminManage/organlimit";
	});	
		
	$('#organNo').selectpicker({
    	noneSelectedText:"  --  请选择  --  ",
		noneResultsText:"查无数据！ {0}",
		style: 'btn-select'
    }).init(function () {
		$("#organNo").next().find("input").on('input', function (e) {
			$("#organNo").empty();
			$("#organNo").append("<option value='0'>  --  不限  --  </option>"); 
			var organName = $("#organNo").next().find("input").val().trim();
			var baseUrl = $("#baseUrl").text().trim();
			if(organName != ''){
				$.ajax({
					url:baseUrl + "/adminManage/adviertisementinfo/getorganlist",
					data:{"organName":organName},
					async: false,
					type:"POST",
					success:function(data)
					{
						var data = JSON.parse(data);
						var organlist = data.objList;
						for(var i in organlist){
							var option = "<option value='" + organlist[i].organNo + "'>" + organlist[i].organName + "</option>"
							$("#organNo").append(option); 
						}
					}
				});
				
			}
	        $("#organNo").selectpicker('refresh');
		});
		$('#organNo').selectpicker('val', $(".organNo").val());
	});
	
	function initLimitType(){
		var limitType = $("#limitType").val();
		if(limitType == '0'){
			$(".maxLimitDiv").hide();
			$("#maxLimit").val("");
		}else{
			$(".maxLimitDiv").show();
		}
	}
	
	$('#limitType').change(function(){
		initLimitType();
	});
	
	function initPage(){
		initLimitType();
	}
		
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
	
	$.validator.addMethod("isAmount", function(value, element) { 
		   var length = value.length; 
		   var number = /^[0-9]+(\.[0-9]{1,2})?$/; 
		   return this.optional(element) || (number.test(value)); 
		 }, "请输入有效金额,最多两位小数");
	
	initPage();
	
	function checkAmount(){
		var limitType = $("#limitType").val();
		if(limitType != '0'){
			var minLimit = $("#minLimit").val();
			var maxLimit = $("#maxLimit").val();
			if(Number(minLimit) > Number(maxLimit)){
				return false;
			}
		}
		return true;
	}
	
	//校验限额是否存在
	function checkUnique(){
		var id =$("#id").val();
		var bool = false;
		var baseUrl = $("#baseUrl").text().trim();
		$.ajax({
			   url:baseUrl+"/adminManage/organlimit/list",
	 		   data:{"organNo":$("#organNo").val(),"limitType":$("#limitType").val(),"limitPayCode":$("#limitPayCode").val(),"limitMode":$("#limitMode").val()}, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(data){
	 			  var data = $.parseJSON(data);
	 			  var objList = data.objList;
	 			  if(objList.length  == 0){
	 				 bool = true;
	 			  }else if(objList.length  == 1){
	 				  if(id == ''){
	 					 bool = false;//已存在一条
	 				  }else if(id != objList[0].id){
	 					 bool = false;
	 				  }else{
	 					 bool = true;//同一条
	 				  }
	 			  }	 			  
	 		   }
	 		});
		return bool;
	}
	
	//校验最低、最高限额比上级底比下级高
	function checkLimit(){
		var bool = false;
		var limitType = $("#limitType").val();
		if(limitType != '1'){
			bool = true;
			return bool;
		}
		var organNo = $("#organNo").val();
		if(organNo == '0'){
			bool = true;
			return bool;
		}
		var baseUrl = $("#baseUrl").text().trim();
		var datajson = $('#form').serialize();
		$.ajax({
			   url:baseUrl+"/adminManage/organlimit/checkLimit",
	 		   data:datajson, 
	 		   type: "POST",
	 		   async:false,
	 		   success: function(msg){
	 			  msg = $.parseJSON(msg);
		 			if(msg.executeStatus == '1'){
		 				bool = false;
		 				bootbox.alert(msg.values);
		 			}else{
		 				bool = true;
		 			}
	 			  
	 		   }
	 		});
		return bool;
	}
	
	/*验证表格*/
	$("#form").validate({
		event:"submit",
	   	    rules: {
	   		   limitType: {
		   	        required: true
		   	      },
		   	   limitPayCode: {
		   	        required: true
		   	      },
		   	   limitMode: {
	   			   required: true
		   	      },
		   	   minLimit: {
		   		   required: true,
		   		   isAmount:true
		   	      },
		   	   maxLimit: {
		   	        required: true,
			   		isAmount:true
		   	      }		   	  
	   	    },
	   	    messages: {
	   	    	limitType: {
	   	    		required: "请选择【限额类型】",
		   	      },
		   	   limitPayCode: {
		   	    	required: "请选择【限额通道】",
		   	      },
		   	   limitMode: {
	   	    		required: "请选择【限制方式】"
		   	      },
		   	   minLimit: {
		   	    	required: "请输入【最低限额】"
		   	      },
		   	   maxLimit: {
		   	        required: "请输入【最高限额】"
		   	      }		   	      
			},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';
   			
   			var isSubmit = true;
   			
   			var organNo = $("#organNo").val();
   			if(organNo == ""){
   				$("#organNo").parent().parent().append('<label class="control-label error">请选择【机构名称】</label>')
   				isSubmit = false;
   			}
   			
   			if(!isSubmit){
   				return;
   			}
   			
   			if(!checkAmount()){
   				flag++;
	   			errMsg += "最低限额不能比最高限额大<br>";
   			}
   			if(!checkUnique()){
   				flag++;
   				errMsg += "已存在该机构限额数据<br>";
   			}
   			
   			if(!checkLimit()){
   				isSubmit = false;
   			}   			
   			
   			if(!isSubmit){
   				return;
   			}
   			
   			if(flag == 0){
//   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/organlimit/save",
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

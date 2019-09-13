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
		window.location.href= baseUrl + "/adminManage/messagepushrule";
	});	
		
	$('#channelNo').selectpicker({
    	noneSelectedText:"  --  请选择  --  ",
		noneResultsText:"查无数据！ {0}",
		style: 'btn-select'
    }).init(function () {
		$("#channelNo").next().find("input").on('input', function (e) {
			$("#channelNo").empty();
			$("#channelNo").append("<option value=''>  --  请选择  --  </option>"); 
			var channelName = $("#channelNo").next().find("input").val().trim();
			var baseUrl = $("#baseUrl").text().trim();
			if(channelName != ''){
				$.ajax({
					url:baseUrl + "/adminManage/channelbase/list",
					data:{"channelName":channelName},
					async: false,
					type:"POST",
					success:function(data)
					{
						var data = JSON.parse(data);
						var objList = data.objList;
						for(var i in objList){
							var option = "<option value='" + objList[i].channelNo + "'>" + objList[i].channelName + "</option>"
							$("#channelNo").append(option); 
						}
					}
				});
				
			}
	        $("#channelNo").selectpicker('refresh');
		});
		$('#channelNo').selectpicker('val', $(".channelNo").val());
	});
	
//	$('#agentNo').selectpicker({
//		    	noneSelectedText:"  --  请选择  --  ",
//				noneResultsText:"查无数据！ {0}",
//				style: 'btn-select'
//		    }).init(function () {
//				$("#agentNo").next().find("input").on('input', function (e) {
//					var channelNo = $("#channelNo").val();
//					if(channelNo == ''){
//						bootbox.alert("请先选择渠道名称",function(){});
//						return ;
//					}
//					$("#agentNo").empty();
//					$("#agentNo").append("<option value=''>  --  请选择  --  </option>"); 
//					var agentName = $("#agentNo").next().find("input").val().trim();
//					var baseUrl = $("#baseUrl").text().trim();
//					if(agentName != ''){
//						$.ajax({
//							url:baseUrl + "/adminManage/agentbase/list",
//							data:{"agentName":agentName,"channelNo":channelNo},
//							async: false,
//							type:"POST",
//							success:function(data)
//							{
//								var data = JSON.parse(data);
//								var objList = data.objList;
//								for(var i in objList){
//									var option = "<option value='" + objList[i].agentNo + "'>" + objList[i].agentName + "</option>"
//									$("#agentNo").append(option); 
//								}
//							}
//						});
//						
//					}
//			        $("#agentNo").selectpicker('refresh');
//				});
//				$('#agentNo').selectpicker('val', $(".agentNo").val());
//			});
	
	function initMessageType(){
		var messageType = $("#messageType").val();
		if(messageType == '0'){
			$("#pushRule").attr("placeholder","请输入天数(天)");
			$("#pushRule").parent().parent().find("label.tip").remove();
			$("#pushRule").parent().after('<label class="control-label tip" style="text-align:left;">天</label>');
			$("#pushWay").val("0");
			$("#pushWay").attr("disabled","disabled");
		}else if(messageType == '1'){
			$("#pushRule").attr("placeholder","请输入金额(元)");
			$("#pushRule").parent().parent().find("label.tip").remove();
			$("#pushRule").parent().after('<label class="control-label tip" style="text-align:left;">元</label>');
			$("#pushWay").val("1");
			$("#pushWay").attr("disabled","disabled");
		}else if(messageType == '2'){
			$("#pushRule").attr("placeholder","请输入数量(个)");
			$("#pushRule").parent().parent().find("label.tip").remove();
			$("#pushRule").parent().after('<label class="control-label tip" style="text-align:left;">个</label>');
			$("#pushWay").val("1");
			$("#pushWay").attr("disabled","disabled");
		}else{
			$("#pushRule").attr("placeholder","");
			$("#pushRule").parent().parent().find("label.tip").remove();
			$("#pushWay").removeAttr("disabled");
		}	
	}
	
	
	$("#messageType").change(function(t){
		initMessageType();	
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
	
	//初始化
	initMessageType();	
	
	/*验证表格*/
	$("#form").validate({
		event:"submit",
	   	    rules: {
	   		   channelNo: {
		   	        required: true
		   	      },
		   	   messageType: {
		   	        required: true
		   	      },
		   	   pushRule: {
	   			   required: true
		   	      },
		   	   pushTime: {
		   		   required: true
		   	      },
		   	   pushWay: {
		   	        required: true
		   	      },
		   	   firstPushDateStr: {
		   	        required: true
		   	      }		   	  
	   	    },
	   	    messages: {
	   	    	channelNo: {
	   	    		required: "请选择【渠道名称】",
		   	      },
		   	   messageType: {
		   	    	required: "请选择【消息类型】",
		   	      },
		   	   pushRule: {
	   	    		required: "请输入【推送规则】"
		   	      },
		   	   pushTime: {
		   	    	required: "请选择【推送频率】"
		   	      },
		   	   pushWay: {
		   	        required: "请输入【推送方式】"
		   	      },
		   	   firstPushDateStr: {
		   	        required: "请选择【首次推送日期】"
		   	      }	
			},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';
   			
   			var isSubmit = true;
   			
   			var channelNo = $("#channelNo").val();
   			if(channelNo == ""){
   				$("#channelNo").parent().parent().append('<label class="control-label error">请选择【渠道名称】</label>')
   				isSubmit = false;
   			}
   			
   			var messageType = $("#messageType").val();
   			var temp1 = $("#temp1").val();
   			if(messageType == 2){
   				if(temp1 == ""){
   					$("#temp1").parent().parent().append('<label class="control-label error">请选择【代理商级别】</label>')
   					isSubmit = false;
   				}
   			}
   			if(!isSubmit){
   				return;
   			}
   			
   			
   			if(messageType ==0 || messageType == 1){
   				if(isNaN($("#pushRule").val())){
   					flag++;
   	   				errMsg += "消息类型对应的推送规则必须是数字<br>";
   				}
   			}
   			
   			if(flag == 0){
//   				$("#save").attr("disabled","disabled");
   				var isDisabled = $("#pushWay").attr("disabled");
   				$("#pushWay").removeAttr("disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$("#pushWay").attr("disabled",isDisabled);
   				$.ajax({
   					url:baseUrl+"/adminManage/messagepushrule/save",
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

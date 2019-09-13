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
		window.location.href= baseUrl + "/adminManage/adviertisementinfo";
	});	
		
	$('#organNo').selectpicker({
    	noneSelectedText:"  --  请选择  --  ",
		noneResultsText:"查无数据！ {0}",
		style: 'btn-select'
    }).init(function () {
		$("#organNo").next().find("input").on('input', function (e) {
			$("#organNo").empty();
			$("#organNo").append("<option value=''>  --  请选择  --  </option>"); 
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
		$('.selectpicker').selectpicker('val', $(".organNo").val());
	});
		
	$("#province").change(function(t){
		var pid = $("#province").val();
		$("#adviertScope").val(pid);
		var baseUrl = $("#baseUrl").text().trim();
		$("#city").empty();
		
		if(pid != ''){
			$.ajax({
				url:baseUrl + "/adminManage/adviertisementinfo/getcitylist",
				data:{"pid":pid},
				async: false,
				type:"POST",
				success:function(data)
				{
					var data = JSON.parse(data);
					var cityList = data.cityList;
					if(cityList.length != 1){
						$("#city").append("<option value=''>  --  请选择  --  </option>"); 
					}else{
						$("#adviertScope").val(cityList[0].cid);
					}
					var option="";
					for(var i in cityList){
						option += "<option value='" + cityList[i].cid + "'>" + cityList[i].cname + "</option>";						
					}
					$("#city").append(option); 
				}
			});
			
		}		
	});
	
	$("#city").change(function(t){
		var cid = $("#city").val();
		if(cid != ''){
			$("#adviertScope").val(cid);	
		}else{
			$("#adviertScope").val($("#province").val());	
		}				
	});
	
	function checkImg(){
		var flag = 0;
		var errMsg = '';		
		if($("#imgUrl").val() == ''){
			flag++;
			errMsg = "请上传广告图片<br>"
		}
		if(!!$("#imgUrlFile")[0].files[0]){
			if($("#imgUrlFile")[0].files[0].size > 500*1024){
				flag++;
				errMsg += "广告图片不大于500kb<br>"
			}
		}
		if(flag == 0){
			return true;
		}else{
			bootbox.alert(errMsg,function(){});
			return false;
		}
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
	
	/*验证表格*/
	$("#form").validate({
		event:"submit",
	   	    rules: {
	   		   beginTimeStr: {
		   	        required: true
		   	      },
		   	   endTimeStr: {
		   	        required: true
		   	      },
		   	   province: {
	   			   required: false
		   	      },
		   	   preImgUrl: {
		   		   required: true
		   	      },
		   	   adviertLink: {
		   	        required: true
		   	      }		   	  
	   	    },
	   	    messages: {
	   	    	beginTimeStr: {
	   	    		required: "请选择【生效日期】",
		   	      },
		   	   endTimeStr: {
		   	    	required: "请选择【结束日期】",
		   	      },
		   	   province: {
	   	    		required: "请选择【推广范围】"
		   	      },
		   	   preImgUrl: {
		   	    	required: "请上传【广告图片】"
		   	      },
		   	   adviertLink: {
		   	        required: "请输入【广告链接】"
		   	      }		   	      
			},
   	    submitHandler:function(form){
   			var flag = 0;
   			var errMsg = '';
   			
   			if(!checkImg()){
   				return false;
   			};
   			
   			if(flag == 0){
//   				$("#save").attr("disabled","disabled");
   				var baseUrl = $("#baseUrl").text().trim();
   				var datajson = $('#form').serialize();
   				$.ajax({
   					url:baseUrl+"/adminManage/adviertisementinfo/save",
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

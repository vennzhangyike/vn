$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$('.date-picker').datepicker({
		format: 'yyyy-mm-dd',
        weekStart: 0,
        autoclose: true,
        todayHighlight: true,
        defaultDate : new Date(),
        keyboardNavigation: true,
        todayBtn: 'linked',
        clearBtn: 'linked',
        language: 'zh-CN'
	});
	
	$("#cancle").click(function(){
		$(".form-horizontal input").val('');
		$(".form-horizontal select").val('');
		$("#channelNo").selectpicker('refresh');
		$("#agentNo").selectpicker('refresh');
	});
	
	
	$("#query").click(function(){
		toPage(1);
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
	});
	
	$('#agentNo').selectpicker({
    	noneSelectedText:"  --  请选择  --  ",
		noneResultsText:"查无数据！ {0}",
		style: 'btn-select'
    }).init(function () {
		$("#agentNo").next().find("input").on('input', function (e) {
			$("#agentNo").empty();
			$("#agentNo").append("<option value=''>  --  请选择  --  </option>"); 
			var agentName = $("#agentNo").next().find("input").val().trim();
			var baseUrl = $("#baseUrl").text().trim();
			if(agentName != ''){
				$.ajax({
					url:baseUrl + "/adminManage/agentbase/list",
					data:{"agentName":agentName},
					async: false,
					type:"POST",
					success:function(data)
					{
						var data = JSON.parse(data);
						var objList = data.objList;
						for(var i in objList){
							var option = "<option value='" + objList[i].agentNo + "'>" + objList[i].agentName + "</option>"
							$("#agentNo").append(option); 
						}
					}
				});
				
			}
	        $("#agentNo").selectpicker('refresh');
		});
	});
	
	toPage(1);
	
	$(".export").click(function(){
		var datajson = $('#form').serialize();
		var baseUrl = $("#baseUrl").text().trim();
		$.ajax({
		   url:baseUrl+"/adminManage/hierarchicalsettlementtotal/export/check?"+datajson,
 		   type: "GET",
 		   success: function(msg){
 			msg = $.parseJSON(msg);
			if (msg.errorCode == '1') {// 校验导出不通过
				bootbox.alert(msg.errorMsg, function() {
				});
			} else {
				window.open(baseUrl+"/adminManage/hierarchicalsettlementtotal/export/excel?"+datajson);
			}
 			  
 		   }
 		});
	});
});


function toPage(pageNumber){
	var datajson=$("#form").serialize();
	$.ajax({
		   type: "POST",
		   url: "hierarchicalsettlementtotal/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#tablec").html(msg);
			    
		   }
		});
}
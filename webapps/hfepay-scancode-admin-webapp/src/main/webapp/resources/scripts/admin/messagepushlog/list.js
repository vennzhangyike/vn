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
        keyboardNavigation: true,
        todayBtn: 'linked',
        clearBtn: 'linked',
        language: 'zh-CN'
	});
	
	$("#query").click(function(){
		var datajson = $('#form').serialize();
		$.ajax({
			   type: "POST",
			   url: "messagepushlog/content",
			   data:datajson,
			   success: function(msg){
				   $("#tablec").html(msg);
			   }
			});
	});
	
	$("#cancle").click(function(){
		$(".form-horizontal input").val('');
		$(".form-horizontal select").val('');
		$("#channelNo").selectpicker('refresh');
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
	
	toPage(1);
});


function toPage(pageNumber){
	$.ajax({
		   type: "POST",
		   url: "messagepushlog/content?pageNo="+pageNumber,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
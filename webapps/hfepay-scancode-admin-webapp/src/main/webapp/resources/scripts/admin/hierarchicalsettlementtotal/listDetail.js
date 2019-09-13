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
	
	
	$("#queryDetail").click(function(){
		toPageDetail(1);
	});
	
	toPageDetail(1);
	
	$('#merchantNo').selectpicker({
		    	noneSelectedText:"请选择！",
				noneResultsText:"查无数据！ {0}",
				style: 'btn-select'
		    }).init(function () {
				$("#merchantNo").next().find("input").on('input', function (e) {
					$("#merchantNo").empty();
					$("#merchantNo").append("<option value=''>  --  请选择  --  </option>"); 
					var merchantName = $("#merchantNo").next().find("input").val().trim();
					var baseUrl = $("#baseUrl").text().trim();
					if(merchantName != ''){
						$.ajax({
							url:baseUrl + "/adminManage/merchantinfo/merchantinfo/list",
							data:{"merchantName":merchantName},
							async: false,
							type:"POST",
							success:function(data)
							{
								var objList = JSON.parse(data);
								for(var i in objList){
									var option = "<option value='" + objList[i].merchantNo + "'>" + objList[i].merchantName + "</option>"
									$("#merchantNo").append(option); 
								}
							}
						});
						
					}
			        $("#merchantNo").selectpicker('refresh');
				});
			});
});


function toPageDetail(pageNumber){
	var datajson=$("#formDetail").serialize();
	$.ajax({
		   type: "POST",
		   url: "hierarchicalsettlementtotal/content/detail?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#tablecDetail").html(msg);
			    
		   }
		});
}
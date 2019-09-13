$(function(){
	
	$("#query").click(function(){
		toPage(1);
	});
	
	toPage(1);
	
	var nowDate = new Date();
	var startDate = nowDate.getFullYear()+"-"+(nowDate.getMonth() + 1)+"-"+nowDate.getDate();
	var endDate = nowDate.getFullYear()+"-"+(nowDate.getMonth() + 1)+"-"+nowDate.getDate();
//	$("#beginTimeStr").val(startDate);
//	$("#endTimeStr").val(endDate);
	
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
	
	$(".export").click(function(){
		var datajson = $('#form').serialize();
		var baseUrl = $("#baseUrl").text().trim();
		$.ajax({
		   url:baseUrl+"/adminManage/orderpay/export/check?"+datajson,
 		   type: "GET",
 		   success: function(msg){
 			msg = $.parseJSON(msg);
			if (msg.errorCode == '1') {// 校验导出不通过
				bootbox.alert(msg.errorMsg, function() {
				});
			} else {
				window.open(baseUrl+"/adminManage/orderpay/export/excel?"+datajson);
			}
 			  
 		   }
 		});
	});
});

function toPage(pageNumber){
	var datajson = $('#form').serialize();
	$.ajax({
		   type: "POST",
		   url: "orderpay/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
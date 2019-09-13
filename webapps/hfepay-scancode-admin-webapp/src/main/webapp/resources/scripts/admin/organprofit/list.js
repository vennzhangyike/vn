$(function(){
	
	/*$('.date-picker').datepicker({
	    rtl: Metronic.isRTL(),
	    orientation: "left",
	    autoclose: true
	});*/
	
	$("#query").click(function(){
		toPage(1);
	});
	
	toPage(1);
	
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
	
	//校验和打印报表
	$(".export").click(function(){
		var datajson = $('#form').serialize();
		var baseUrl = $("#baseUrl").text().trim();
		$.ajax({
		   url:baseUrl+"/adminManage/organprofit/export/check?"+datajson,
 		   type: "GET",
 		   success: function(msg){
 			msg = $.parseJSON(msg);
			if (msg.errorCode == '1') {// 校验导出不通过
				bootbox.alert(msg.errorMsg, function() {
				});
			} else {
				window.open(baseUrl+"/adminManage/organprofit/export/excel?"+datajson);
			}
 			  
 		   }
 		});
	});
	
	
});

function toPage(pageNumber){
	var transDate = $.trim($("#transDate").val());
	var organNo = $.trim($("#organNo").val());
	var type = $.trim($("#type").val());
	var payCode = $.trim($("#payCode").val());
	
	var orgName = $.trim($("#orgName").val());
	var identityFlag = $.trim($("#identityFlag").val());
	
	var datajson = $('#form').serialize();
	$.ajax({
		   type: "POST",
		   url: "organprofit/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
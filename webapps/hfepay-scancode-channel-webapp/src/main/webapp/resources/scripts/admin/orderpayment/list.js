$(function(){
	
	$("#query").click(function(){
		var startAmtStr = $("#startAmtStr").val().trim();
		var endAmtStr = $("#endAmtStr").val().trim();
		if(isNaN(Number(startAmtStr))){
			bootbox.alert("查询金额只能是数字",function(){
 			});
			return false;
		}
		if(isNaN(Number(endAmtStr))){
			bootbox.alert("查询金额只能是数字",function(){
 			});
			return false;
		}
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
});

function toPage(pageNumber){
	var datajson = $('#form').serialize();
	$.ajax({
		   type: "POST",
		   url: "orderpayment/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
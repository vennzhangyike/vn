$(function(){
	
	$("#query").click(function(){
		toPage(1);
	});
	
	toPage(1);
});

function toPage(pageNumber){
	var baseUrl = $("#baseUrl").text().trim();
	var datajson = $('#form').serialize();
	$.ajax({
		   type: "POST",
		   url: baseUrl+"/adminManage/merchantbankcardchange/bankContent?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
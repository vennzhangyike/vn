$(function(){
	
	$("#query").click(function(){
		toPage(1);
	});
	
	toPage(1);
});

function toPage(pageNumber){
	var datajson = $('#form').serialize();
	$.ajax({
		   type: "POST",
		   url: "channelbankcard/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
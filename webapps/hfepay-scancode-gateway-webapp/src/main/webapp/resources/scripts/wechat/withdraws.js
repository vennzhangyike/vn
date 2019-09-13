$(function(){
	$("#doWithDraw").click(function(){
//		alert("doWithDraw");
//		var withMoney =  $("#withMoney").val();
//		if(!/^\d+[\.]?\d+$/.test(withMoney)){
//			$.toast("金额有误，请检查","cancle");
//			return;
//		}
//		if(withMoney<=0){
//			$.toast("金额必须大于0","cancle");
//		}
//		$("#balanceid").val(withMoney);
		$("#toSubmit").attr("action",basePath+"/scancode/withdraws").submit();
		
	});
	
	$("#outAll").click(function(){
		$("#withMoney").val($("#total").text());
	});
});

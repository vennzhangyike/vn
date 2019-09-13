var baseUrl = $("#baseUrl").text().trim();

$(function(){
	
	$("#cancle1").on("click",function(){
		$("div.bootbox,div.modal-backdrop").fadeOut(function(){
			$(this).remove();
		});
	});
	
	$(".allApis span").on("click",allApi);
	
	$(".ownApis span").on("click",ownApi);
	
	var ownspans = $(".ownApis span");
	var codes = ",";
	for(var i = 0; i < ownspans.size();i++){
		var $this = $(ownspans[i]);
		var val = $this.attr("value").trim();
		codes += val + ",";
	}
	var ownspans = $(".allApis span");
	for(var i = 0; i < ownspans.size();i++){
		var $this = $(ownspans[i]);
		var code = $this.attr("value").trim();
		if(codes.indexOf(","+code+",") > -1){
			$this.remove();
		}
	}
	
	function allApi(){
		var code = $(this).attr("value").trim();		
		var ids = ",";
		var text = $(".ownApis span");
		for(var i = 0; i < text.size();i++){
			var $this = $(text[i]);
			var val = $this.attr("value").trim();
			ids += val + ",";
		}
		if(ids.indexOf(","+code+",") < 0 || ids == ''){
			var span = $(this).clone();
			span.bind("click",ownApi);
			$(".ownApis").append(span);
			$(this).remove();
		}else{
			bootbox.alert("已选二维码中已存在此二维码，请选择其他二维码!",function(){});
		}
	}
	
	function ownApi(){
		var span = $(this).clone();
		span.bind("click",allApi);
		$(".allApis").append(span);
		$(this).remove();
	}
		
	$("#save1").on("click",function(){
		var text = $(".allApis .ownApi");
		for(var i = 0; i < text.size();i++){
			var $this = $(text[i]);
			var val = $this.attr("val");
			delapi(val);
		}
		text = $(".ownApis span");
		var ids = "";
		for(var i = 0; i < text.size();i++){
			var $this = $(text[i]);
			var val = $this.attr("value");
			if(ids.indexOf(val) < 0){
				ids += val;
				if(i<text.size()-1){
					ids+=",";
				}
			}
		}
		var storeNo = $("#storeNo1").val();
		var cashierNo = $("#cashierNo1").val();
		var merchantNo = $("#merchantNo1").val();
		$.ajax({
			   type: "POST",
			   url: baseUrl + "/adminManage/merchantcashierqr/save",
			   data:{"ids":ids,"storeNo":storeNo,"cashierNo":cashierNo,"merchantNo":merchantNo},
			   async:false,
			   success: function(msg){
				  msg = $.parseJSON(msg);
				 bootbox.alert(msg.values,function(){
					 if(msg.executeStatus == 0){
						 window.location.reload();
					 }
				 });
			   }
			});
	});
});
//删除
function delapi(obj){
	$.ajax({
	   type: "POST",
	   url: baseUrl + "/adminManage/merchantcashierqr/del",
	   data:{"qrCode":obj},
	   async:false,
	   success: function(msg){
		  msg = $.parseJSON(msg);
		 bootbox.alert(msg.values,function(){
			 if(msg.executeStatus == 0){
				 window.location.reload();
			 }
		 });
	   }
	});
}

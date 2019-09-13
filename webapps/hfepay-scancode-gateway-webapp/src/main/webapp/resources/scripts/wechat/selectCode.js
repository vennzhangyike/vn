$(function(){
	toPage(1);
	$("#codes").delegate(".weui-switch","click",function(){//checkbox点击事件
		$('div.weui-cell_switch .weui-switch').removeClass('currentC');
		var $this = $(this).addClass("currentC");
		var temp = $this.is(":checked");
		$.showLoading("数据处理中..");
		var id = $this.val();//二维码编号
		//点击触发ajax事件
		$.ajax({
			   type: "POST",
			   url: basePath+"/scancode/bindSelectCode",
			   data:{"id":id,"isCashier":temp?"2":"1","qrCode":$this.attr("attr")},
			   dataType:"json"
			}).done(function(result){
				$.hideLoading();
				if(result.errorCode=='0'){//操作成功
					$(':checkbox:not(".currentC")').removeAttr('checked');
					$(".codeSkz").text("");
					if(temp){
						$.toast("二维码已关联!");
						$this.parent().parent().find('.codeSkz').text("（收款中）");
					}else{
						$.toast("二维码已失联!");
						//$(".codeSkz").text("");
					};
				}
				else{
					$this.removeAttr('checked');
					$.toast(result.errorMsg,"cancel",function(){
						location.reload();
					});	
				}
			}).error(function(msg){
				$.hideLoading();
				$.toast("内部错误,操作失败","cancel");
			});
	});
	
	$("#codes").delegate(".moreDiv","click",function(){//“更多”的点击事件
		var page=$(this).attr("nextPage");
		toPage(page);
	});
});

function toPage(pageNumber){
	$.ajax({
		   type: "POST",
		   url: basePath+"/scancode/cashierQrContent?pageNo="+pageNumber,
		   success: function(msg){
			   $("#moreDiv").remove();
			   $("#codes").append(msg);
		   }
		});
}
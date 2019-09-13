$(function(){
	
	$("#query").click(function(){
		toPage(1);
	});
	
	toPage(1);
});

//更新状态
function updateStatus(btn,qrType){
	var $this = $(btn);
	var obj = $this.attr("value");
	var status = $this.attr("status");
	var code = $this.attr("code");
	bootbox.setLocale("zh_CN");  //设置按钮为中文
    bootbox.confirm("确定执行该操作吗?", function(result) {
  	  if(result){
  		$.ajax({
 		   type: "POST",
 		   url: "merchantqrcode/updateStatus",
 		   data:{"id":obj,"qrStatus":status,"qrType":qrType},
 		   success: function(msg){
 			  msg = $.parseJSON(msg);
 			  bootbox.alert(msg.message,function(){
 				 if(msg.status == 1){
	 				 status = status == 1 ? 2 : 1 ;
	 				 $this.parent().prev().prev().html($this.text());
	 				 //修改按钮文本
	 				 var text = $this.text().trim() == "启用" ? '<span class="label label-danger">禁用</span>' : '<span class="label label-success">启用</span>';	 				 
	 				 //修改按钮状态属性
	 				 $this.attr("status",status);
	 				 $this.html(text);
 				 }
 			  });
 		   }
 		});
  	  }
    }); 
}


//删除
function del(obj){
	bootbox.setLocale("zh_CN");  //设置按钮为中文
    bootbox.confirm("确定执行该操作吗?", function(result) {
  	  if(result){
  		$.ajax({
 		   type: "POST",
 		   url: "merchantqrcode/del",
 		   data:{"id":obj},
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
    }); 
}

function toPage(pageNumber){
	var datajson = $('#form').serialize();
	var doEle = "tablec";
	refreshData(doEle);
	$.ajax({
		   type: "POST",
		   url: "merchantqrcode/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#" + doEle).html(msg);
		   },
		   error: function(XMLHttpRequest, textStatus, errorThrown) {
			   hasError(XMLHttpRequest, textStatus, errorThrown,doEle);
		   }
		});
}
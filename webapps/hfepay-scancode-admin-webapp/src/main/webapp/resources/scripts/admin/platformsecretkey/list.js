$(function(){
	
	$("#query").click(function(){
		toPage(1);
	});
	
	toPage(1);
});

//更新状态
function updateStatus(btn){
	var $this = $(btn);
	var obj = $this.attr("value");
	var status = $this.attr("status");
	bootbox.setLocale("zh_CN");  //设置按钮为中文
    bootbox.confirm("确定执行该操作吗?", function(result) {
  	  if(result){
  		$.ajax({
 		   type: "POST",
 		   url: "platformsecretkey/updateStatus",
 		   data:{"id":obj,"status":status},
 		   success: function(msg){
 			  msg = $.parseJSON(msg);
 			  bootbox.alert(msg.values,function(){
 				 if(msg.executeStatus == 0){
	 				 status = status == 1 ? 0 : 1 ;
	 				 //修改状态
	 				 $this.parent().prev().html($this.text());
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
	

function toPage(pageNumber){
	var datajson = $('#form').serialize();
	$.ajax({
		   type: "POST",
		   url: "platformsecretkey/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
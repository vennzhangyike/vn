$(function(){
	
	$("#query").click(function(){
		toPage(1);
	});
	
	toPage(1);
});


	


//删除
function del(obj){
	bootbox.setLocale("zh_CN");  //设置按钮为中文
    bootbox.confirm("确定执行该操作吗?", function(result) {
  	  if(result){
  		$.ajax({
 		   type: "POST",
 		   url: "qrcodegoods/del",
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
		   url: "qrcodegoods/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#" + doEle).html(msg);
		   },
		   error: function(XMLHttpRequest, textStatus, errorThrown) {
			   hasError(XMLHttpRequest, textStatus, errorThrown,doEle);
		   }
		});
}
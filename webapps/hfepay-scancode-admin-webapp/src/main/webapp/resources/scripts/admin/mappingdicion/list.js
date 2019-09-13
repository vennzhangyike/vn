$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$('.date-picker').datepicker({
	    rtl: Metronic.isRTL(),
	    orientation: "left",
	    autoclose: true
	});
	
	$("#query").click(function(){
		var datajson=$("#form").serialize();
		$.ajax({
			   type: "POST",
			   url: "mappingdicion/content",
			   data:datajson,
			   success: function(msg){
				   $("#tablec").html(msg);
			   }
			});
	});
	
	$("#cancle").click(function(){
		$(".form-horizontal input").val('');
		$(".form-horizontal select").val('');
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
 		   url: "mappingdicion/del",
 		   data:{"id":obj},
 		   success: function(msg){
 			  /*  if(msg.isDenied){//没有权限
 				  bootbox.alert(msg.message); 
 			   	  return;
 			   } */
 			  msg = $.parseJSON(msg);
 			  bootbox.alert(msg.message,function(){
 				 if(msg.status == 1){
 					 window.location.reload();
 				 }
 			  });
 		   }
 		});
  	  }
    }); 
}

function toPage(pageNumber){
	$.ajax({
		   type: "POST",
		   url: "mappingdicion/content?pageNo="+pageNumber,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
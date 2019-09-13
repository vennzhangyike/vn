$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$('.date-picker').datepicker({
	    rtl: Metronic.isRTL(),
	    orientation: "left",
	    autoclose: true,
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
	
	$("#query").click(function(){
		toPage(1);
	});
	
	$("#cancle").click(function(){
		$(".form-horizontal input").val('');
		$(".form-horizontal select").val('');
	});
	
	toPage(1);
	//报表检验和导出
	$(".export").click(function(){
		var datajson = $('#form').serialize();
		var baseUrl = $("#baseUrl").text().trim();
		$.ajax({
		   url:baseUrl+"/adminManage/merchantcost/export/check?"+datajson,
 		   type: "GET",
 		   success: function(msg){
 			msg = $.parseJSON(msg);
			if (msg.errorCode == '1') {// 校验导出不通过
				bootbox.alert(msg.errorMsg, function() {
				});
			} else {
				window.open(baseUrl+"/adminManage/merchantcost/export/excel?"+datajson);
			}
 			  
 		   }
 		});
	});
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
 		   url: "merchantcost/updateStatus",
 		   data:{"id":obj,"status":status},
 		   success: function(msg){
 			  msg = $.parseJSON(msg);
 			  bootbox.alert(msg.message,function(){
 				 if(msg.status == 1){
	 				 status = status == 1 ? 0 : 1 ;
	 				 //修改状态
	 				 $this.parent().prev().html($this.text());
	 				 //修改按钮文本
	 				 var text = $this.text().trim() == "有效" ? "无效" : "有效";
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
 		   url: "merchantcost/del",
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
	var datajson = $('#form').serialize();
	$.ajax({
		   type: "POST",
		   url: "merchantcost/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
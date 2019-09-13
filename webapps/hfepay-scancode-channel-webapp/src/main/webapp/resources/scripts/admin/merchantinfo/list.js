$(function(){
	
	$("#query").click(function(){
		toPage(1);
	});
	
	$("#channelNo").on("change",function(){
		var apiCode = $(this).val();
		var options = $("#agentNo option");
		$("#agentNo").val("");
		for(var i = 0; i < options.size();i++){
			var $this = $(options[i]);
			$this.css("display","block");
			var opval = $this.attr("val");
			if(opval != apiCode && apiCode != '' && opval != '----'){
				$this.css("display","none");
			}
		}
	});
	
	$('.date-picker').datepicker({
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
	
	toPage(1);
});
function toPage(pageNumber){
	var datajson = $('#form').serialize();
	var doEle = "tablec";
	refreshData(doEle);
	$.ajax({
		   type: "POST",
		   url: "merchantinfo/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#" + doEle).html(msg);
		   },
		   error: function(XMLHttpRequest, textStatus, errorThrown) {
			   hasError(XMLHttpRequest, textStatus, errorThrown,doEle);
		   }
		});
}
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
 		   url: "merchantinfo/updateStatus",
 		   data:{"id":obj,"status":status},
 		   success: function(msg){
 			  msg = $.parseJSON(msg);
 			  bootbox.alert(msg.values,function(){
 				 if(msg.executeStatus == 0){
 					status = status == 3 ? 5 : 3 ;
	 				 //修改状态
	 				 $this.parent().prev().prev().html($this.text().trim() == "启用" ? '平台审核通过' : $this.text().trim());
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
	
//更新信用卡状态
function updateCreditPayStatus(btn){
	var $this = $(btn);
	var obj = $this.attr("value");
	var status = $this.attr("status");
	bootbox.setLocale("zh_CN");  //设置按钮为中文
    bootbox.confirm("确定执行该操作吗?", function(result) {
  	  if(result){
  		$.ajax({
 		   type: "POST",
 		   url: "merchantinfo/updateStatus",
 		   data:{"id":obj,"creditPayStatus":status},
 		   success: function(msg){
 			  msg = $.parseJSON(msg);
 			  bootbox.alert(msg.values,function(){
 				 if(msg.executeStatus == 0){
	 				 status = status == 1 ? 2 : 1 ;
//	 				 //修改状态
//	 				 $this.parent().prev().html($this.text().trim() == "启用" ? '平台审核通过' : $this.text().trim());
//	 				 //修改按钮文本
	 				 var text = $this.text().trim() == "启用信用卡" ? '<span class="label label-danger">禁用信用卡</span>' : '<span class="label label-success">启用信用卡</span>';
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
 		   url: "merchantinfo/del",
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
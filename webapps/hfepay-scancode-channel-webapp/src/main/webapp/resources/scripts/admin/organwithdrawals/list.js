$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	toPage(1);
	
	$("#query").click(function(){
		var datajson=$("#form").serialize();
		$.ajax({
			   type: "POST",
			   url: "organwithdrawals/content",
			   data:datajson,
			   success: function(msg){
				   $("#tablec").html(msg);
			   }
			});
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
	
	$("#cancle").click(function(){
		$(".form-horizontal input").val('');
		$(".form-horizontal select").val('');
	});
	
	$(".export").click(function(){
		var datajson = $('#form').serialize();
		var baseUrl = $("#baseUrl").text().trim();
		$.ajax({
		   url:baseUrl+"/adminManage/organwithdrawals/export/check?"+datajson,
 		   type: "GET",
 		   success: function(msg){
 			msg = $.parseJSON(msg);
			if (msg.errorCode == '1') {// 校验导出不通过
				bootbox.alert(msg.errorMsg, function() {
				});
			} else {
				window.open(baseUrl+"/adminManage/organwithdrawals/export/excel?"+datajson);
			}
 			  
 		   }
 		});
	});
	
	$("#add").on("click",function(){
		var box = '<form class="form-horizontal"><div class="form-body row">'+
					'<div class="form-group col-md-7">'+
						'<label class="control-label col-md-4">提现金额</label>'+
						'<div class="col-md-8">'+
							'<input type="text" class="form-control" id="balance" placeholder="提现金额">'+
						'</div>'+
					'</div>'+
					'<div class="form-group col-md-7">'+
						'<label class="control-label col-md-4">备注</label>'+
						'<div class="col-md-8">'+
							'<input type="text" class="form-control" id="remark" placeholder="备注">'+
						'</div>'+
					'</div>'+
				   '</div></form>';
	    var btn = {
	    		success:{   
			       label: "申请提现",
			       className: "btn-success",
			       callback: function() {
			    	   var errMsg = '';
			    	   	var balance = $('#balance').val().trim();
			    	   	var remark = $('#remark').val().trim();
			    	   	if(balance.length==""||!/^\d+(\.\d{2})?$/.test(balance)) {
			    	   		bootbox.alert("提现金额有误,只能保留两位小数！<br>",function(){});
			    	   		return;
			    	   	}
			    	   	else{
			    	   		$.ajax({
								   type: "POST",
								   url: "organwithdrawals/addWithDraw",
								   data:{"remark":remark,"balance":balance}
							}).done(function(msg){
								msg = $.parseJSON(msg);
								if(msg.errorCode=='0'){//成功
									bootbox.alert("提现申请成功",function(){
						 				location.reload();
						 			});
								}
								else{
									bootbox.alert(msg.errorMsg,function(){});
								}
					 			
							}).error(function(msg){
								bootbox.alert("申请提现失败",function(){});
							});
			    	   	}
			       }
			     },
			     "取消": {
			        className: "btn-danger",
			        callback: function() {
			        
			        	}
					}
			 };
	    bootbox.dialog({
		   message: box,
		   title: "提现申请",
		   onEscape: function() {},
		   className: "green-haze",
		   buttons: btn
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
 		   url: "organwithdrawals/updateStatus",
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



function toPage(pageNumber){
	var dataJson = {/*"organNo":$.trim($("#organNo").val()),*/"queryStartDate":$.trim($("#queryStartDate").val()),"queryEndDate":$.trim($("#queryEndDate").val()),"organNo":$.trim($("#organNo").val())};
	$.ajax({
		   type: "POST",
		   url: "organwithdrawals/content?pageNo="+pageNumber,
		   data:dataJson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
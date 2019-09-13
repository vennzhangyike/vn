$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
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
	
	$("#query").click(function(){
		toPage(1);
	});
	
	toPage(1);
	
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



function toPage(pageNumber){
	var dataJson = $('#form').serialize();
	$.ajax({
		   type: "POST",
		   url: "organwithdrawals/content?pageNo="+pageNumber,
		   data:dataJson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
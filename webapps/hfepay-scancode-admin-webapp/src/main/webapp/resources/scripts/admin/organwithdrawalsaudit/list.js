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
	$("#tablec").delegate(".audit","click",function(){
		var id=$(this).attr("attr");
		var organNo=$(this).attr("organNo");
		var box = '<form class="form-horizontal"><div class="form-body row">'+
		'<div class="form-group col-md-7">'+
			'<label class="control-label col-md-4">审核状态</label>'+
			'<div class="col-md-8">'+
				'<span>通过:</span><input type="radio"  name="agree" value="2" checked="checked">&nbsp;&nbsp;&nbsp;&nbsp;'+
				'<span>拒绝:</span><input type="radio"  name="agree" value="3">'+
			'</div>'+
		'</div>'+
		'<div class="form-group col-md-7">'+
			'<label class="control-label col-md-4">原因</label>'+
			'<div class="col-md-8">'+
				'<input type="text" class="form-control" id="remark" placeholder="原因">'+
			'</div>'+
		'</div>'+
	   '</div></form>';
		 var btn = {
		    		success:{   
				       label: "提现审核",
				       className: "btn-success",
				       callback: function() {
				    	   var errMsg = '';
				    	   	var status = $(":radio:checked").val();
				    	   	var remark = $('#remark').val().trim();
				    	   	if(!status ||status.length=="") {
				    	   		bootbox.alert("请选择审核状态！<br>",function(){});
				    	   		return;
				    	   	}
				    	   	else{
				    	   		$.ajax({
									   type: "POST",
									   url: "addAuditWithDraw",
									   data:{"remark":remark,"id":id,"status":status,"organNo":organNo}
								}).done(function(msg){
									msg = $.parseJSON(msg);
									if(msg.errorCode=='0'){//成功
										bootbox.alert("审核成功",function(){
							 				location.reload();
							 			});
									}
									else{
										bootbox.alert(msg.values,function(){});
									}
						 			
								}).error(function(msg){
									bootbox.alert("审核失败",function(){});
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
			   title: "提现审核",
			   onEscape: function() {},
			   className: "green-haze",
			   buttons: btn
			 });
		 
	});
	//报表校验和导出
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
	
});



function toPage(pageNumber){
	var dataJson = {/*"organNo":$.trim($("#organNo").val()),*/"queryStartDate":$.trim($("#queryStartDate").val()),"queryEndDate":$.trim($("#queryEndDate").val())};
	$.ajax({
		   type: "POST",
		   url: "auditlistcontent?pageNo="+pageNumber,
		   data:dataJson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
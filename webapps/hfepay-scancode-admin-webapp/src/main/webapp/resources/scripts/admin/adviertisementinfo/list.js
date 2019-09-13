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
		var datajson = $('#form').serialize();
		$.ajax({
			   type: "POST",
			   url: "adviertisementinfo/content",
			   data:datajson,
			   success: function(msg){
				   $("#tablec").html(msg);
			   }
			});
	});
	
	$("#cancle").click(function(){
		$(".form-horizontal input").val('');
		$(".form-horizontal select").val('');
		$("#organNo").selectpicker('refresh');
	});
	
	$('#organNo').selectpicker({
		    	noneSelectedText:"请选择！",
				noneResultsText:"查无数据！ {0}",
				style: 'btn-select'
		    }).init(function () {
				$("#organNo").next().find("input").on('input', function (e) {
					$("#organNo").empty();
					$("#organNo").append("<option value=''>  --  请选择  --  </option>"); 
					var organName = $("#organNo").next().find("input").val().trim();
					var baseUrl = $("#baseUrl").text().trim();
					if(organName != ''){
						$.ajax({
							url:baseUrl + "/adminManage/adviertisementinfo/getorganlist",
							data:{"organName":organName},
							async: false,
							type:"POST",
							success:function(data)
							{
								var data = JSON.parse(data);
								var organlist = data.objList;
								for(var i in organlist){
									var option = "<option value='" + organlist[i].organNo + "'>" + organlist[i].organName + "</option>"
									$("#organNo").append(option); 
								}
							}
						});
						
					}
			        $("#organNo").selectpicker('refresh');
				});
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
 		   url: "adviertisementinfo/updateStatus",
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
 		   url: "adviertisementinfo/del",
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
		   url: "adviertisementinfo/content?pageNo="+pageNumber,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
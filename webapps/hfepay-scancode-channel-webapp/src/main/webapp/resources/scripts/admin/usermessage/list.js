$(function(){
	
	$("#query").click(function(){
		toPage(1);
	});
	
	$("#tablec").delegate("#allItem","click",function(){
		 
		if($('#allItem').is(':checked')) {
			$("input[name='item']").prop("checked", "checked");
		} else {
			$("input[name='item']").removeAttr("checked");
		}
	});
	
	$("#tablec").delegate(".subItem","click",function(){
		var a = $('input:checkbox[name=item]:checked').size();
		var b = $('input:checkbox[name=item]').size();
		if(a==b){
			$("input[name='allItem']").prop("checked", "checked");
		}else{
			$("input[name='allItem']").removeAttr("checked");
		}
	});
	$("#tablec").delegate("#delete","click",function(){
		del();
	});
	
	toPage(1);
});

//删除
function del(){
	var list = new Array();
	$.each($('input:checkbox[class=subItem]:checked'),function(i,item){
		var value = $(item).val();
		list[i]=value;
	})
	if(list.length==0){
		bootbox.alert("请选择要删除的数据！");
		return;
	}
	var json = JSON.stringify(list);
	bootbox.setLocale("zh_CN");  //设置按钮为中文
    bootbox.confirm("确定执行该操作吗?", function(result) {
  	  if(result){
  		$.ajax({
 		   type: "POST",
 		   url: "usermessage/del",
 		   data:{"list":json},
 		   dataType:"json",
 		   success: function(msg){
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
	$.ajax({
		   type: "POST",
		   url: "usermessage/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
			     $("#tablec").html(msg);
		   }
		});
}
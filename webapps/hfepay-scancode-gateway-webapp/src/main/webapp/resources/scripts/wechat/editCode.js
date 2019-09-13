$(function(){
	//var url=$("#qrCodeUrl").val();
	$("#list").delegate(".qrcodes","click",function() {
		var $this = $(this);
		var id=$this.find(".idid").text();//二维码id
		//var storeNo =storeId;//门店编号
		var storeName = $("#storeName").text();//门店名称
		var qrName = $this.find(".qrName").text();//二维码名称
		var qrCode = $this.find(".qrCode").text();//二维码名称
		 var actionsItem=[];
		 actionsItem[0]={
			  text: "编辑二维码",
			  className: "color-primary",
			  onClick: function() {
				$.prompt({
					title: '编辑门店二维码',
					text: '所属门店：'+storeName,
					input: qrName,
					empty: false, // 是否允许为空
					onOK: function (input) {
						$.ajax({
							   type: "POST",
							   url: basePath+"/scancode/addOrUpdateMerchantQrcode",
							   data:{"id":id,"storeId":storeId,"qrName":input},
							   dataType:"json"
						}).done(function(msg){
							if(msg.errorCode!='1'){
								$.toast(msg.errorMsg,function(){
										location.reload();
								});
							}else{
								$.toast(msg.errorMsg,"cancel");
							}
							
						}).error(function(msg){
							$.toast("操作失败!",'cancel');
						});
					},
					onCancel: function () {
					//点击取消
					}
				});
				
			  }
			};
		 
		 actionsItem[1]={
				  text: "删除二维码",
				  className: 'color-danger',
				  onClick: function() {
					$.confirm("确定要删除该二维码？", function() {
						$.ajax({
							   type: "POST",
							   url: basePath+"/scancode/delMerchantQrcode",
							   data:{"id":id,"qrCode":qrCode},
							   dataType:"json"
						}).done(function(msg){
							if(msg.errorCode!='1'){
								$.toast(msg.errorMsg,function(){
									location.reload();
								});
							}else{
								$.toast(msg.errorMsg,"cancel");
							}
						}).error(function(msg){
							$.toast("操作失败!",'cancel');
						});
					  }, function() {
					  //点击取消后的回调函数
					});
				  }
				};
		 
			$.actions({
				 actions: actionsItem
			});
		  });
	
	//加载数据
	toPage(1);
	//更多
	$("#list").delegate("#moreDiv","click",function(){
		var page=$(this).attr("nextPage");
		toPage(page);
	});
});	
var storeId=$("#storeId").val();//门店编号
function toPage(pageNumber){
	$.ajax({
		   type: "POST",
		   url: basePath+"/scancode/editcodeContent/"+storeId+"/?pageNo="+pageNumber,
		   success: function(msg){
			   $("#moreDiv").remove();
			   $("#list").append(msg);
		   }
		});
}

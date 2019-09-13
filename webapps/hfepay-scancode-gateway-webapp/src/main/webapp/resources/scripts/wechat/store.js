$(function(){
	//var url=$("#qrCodeUrl").val();
	$("#list").delegate(".stores","click",function() {
		var $this = $(this);
		var storeNo =$this.find(".storeNo").text()
		var status=$this.find(".status").text();
		var storeName = $this.find(".storeName").text();
		 var actionsItem=[];
		 actionsItem[0]={
				  text: "添加二维码",
				  className: "color-primary",
				  onClick: function() {
					$.prompt({
						title: '添加门店二维码',
						text: '所属门店：'+storeName,
						input: '',
						empty: false, // 是否允许为空
						onOK: function (input) {
							$.ajax({
								   type: "POST",
								   url: basePath+"/scancode/addOrUpdateMerchantQrcode",
								   data:{"storeId":storeNo,"qrName":input},
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
				  text: "编辑二维码",
				  className: "color-primary",
				  onClick: function() {
					location.href='editcode/'+storeNo;
				  }
				};
		 actionsItem[2]= {
			  text: "查看门店",
			  className: "color-warning",
			  onClick: function() {
				  $("#clickSee").removeClass("open-popup");
				  $("#seeStore .storeName").text(storeName);
				  $("#seeStore .servicePhone").text($this.find('.servicePhone').text());
				  $("#seeStore .storeAddress").text($this.find('.storeAddress').text());
				  $.ajax({
					  type: "POST",
					   url: basePath+"/scancode/seeStoreQrCode",
					   data:{"storeId":storeNo},
					   dataType:"json"
				  }).done(function(result){
					  $("#clickSee").addClass("open-popup");
					  $("#seeStore .qrcodeName").text(result.qrcodeName);
					  $("#clickSee").click();
				  }).error(function(error){
					  $("#clickSee").addClass("open-popup");
					  $("#seeStore .qrcodeName").text("");
					  $("#clickSee").click();
				  }); 
			  }
			};
		 actionsItem[3]= {
			  text: "编辑门店",
			  className: "color-warning",
			  onClick: function() {
				  $("#clickEdit").removeClass("open-popup");
				  $("#editStore .storeName").val(storeName);
				  $("#editStore .servicePhone").val($this.find('.servicePhone').text());
				  $("#editStore .storeAddress").val($this.find('.storeAddress').text());
				  $("#editStore .idid").val($this.find('.idid').text());
				  $("#clickEdit").addClass("open-popup");
				 $("#clickEdit").click();
			  }
			};
		 //未审核通过的可以删除
		 if(status!='3'){
			 actionsItem[4]= {
					  text: "删除门店",
					  className: 'color-danger',
					  onClick: function() {
						$.confirm("确定要删除该门店？", function() {
							$.ajax({
								   type: "POST",
								   url: basePath+"/scancode/delStore",
								   data:{"storeNo":storeNo},
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
								$.toast("删除失败!",'cancel');
							});
						  }, function() {
						  //点击取消后的回调函数
						  
						});
					  }
					};
		 }
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
	//编辑门店按钮
	$("#sureChange").click(function(){
		var storeName = $("#editStore .storeName").val();
		var servicePhone = $("#editStore .servicePhone").val();
		var storeAddress = $("#editStore .storeAddress").val();
		var id = $("#editStore .idid").val();
		if(!storeName||storeName==''){
			$.toast("操作失败,门店名称为空","cancel");
			return;
		}
		if(!/\d+$/.test(servicePhone)){
			$.toast("操作失败,电话号码有误","cancel");
			return;		
		}
		if(!storeAddress||storeAddress==''){
			$.toast("操作失败,门店名称为空","cancel");
			return;
		}
		var data = {"id":id,"storeName":storeName,"storeAddress":storeAddress,"servicePhone":servicePhone};
		$.ajax({
			   type: "POST",
			   url: basePath+"/scancode/saveOrUpdateStore",
			   data:data,
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
	});
	//添加门店按钮
	$("#sureAddChange").click(function(){
		var storeName = $("#store .storeName").val();
		var servicePhone = $("#store .servicePhone").val();
		var storeAddress = $("#store .storeAddress").val();
		if(!storeName||storeName==''){
			$.toast("操作失败,门店名称为空","cancel");
			return;
		}
		if(!/\d+$/.test(servicePhone)){
			$.toast("操作失败,电话号码有误","cancel");
			return;		
		}
		if(!storeAddress||storeAddress==''){
			$.toast("操作失败,门店名称为空","cancel");
			return;
		}
		var data = {"storeName":storeName,"storeAddress":storeAddress,"servicePhone":servicePhone};
		$.ajax({
			   type: "POST",
			   url: basePath+"/scancode/saveOrUpdateStore",
			   data:data,
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
	});
});	
var startNo = 1;//其实页
function toPage(pageNumber){
	$.ajax({
		   type: "POST",
		   url: basePath+"/scancode/storeContent?pageNo="+pageNumber,
		   success: function(msg){
			   $("#moreDiv").remove();
			   $("#list").append(msg);
		   }
		});
}

$(function(){
	var cashier = $("#cashier").val();
	var orignDatas = $("#xzmd").attr('data-values'); //最开始页面存在值的话需要添加select事件.
	var stores = [];
	var qrcodes = [];
	$.ajax({
	   type: "POST",
	   url: basePath+"/scancode/getStoreQrcodes/"+cashier,
	   dataType:"json"
	}).done(function(result){
		stores = result.storeList;
		qrcodes = result.codeList;
		var storeItems=[];
		var qrcodeItems=[];
		$.each(stores,function(i,item){
			storeItems.push({title: item.storeName,value: item.storeNo});
		});
		
		//页面存在门店等信息需要构造select
		if(orignDatas){
			var orignQrcodeItems=[];
			$.each(qrcodes,function(i,item){
				if(item.storeId==orignDatas){
					orignQrcodeItems.push({title: item.qrName,value: item.qrCode});
				}
			});
			if(orignQrcodeItems.length!=0){
				$("#xzewm").unbind(".org");
				$("#xzewm").select({
					title: "选择二维码",
					multi: true,
					min: 1,
					max: 100,
					onOpen:function(){
						$('.weui-picker-container.weui-picker-container-visible:not(:last)').remove();
						$('.weui-picker-container.weui-picker-container-visible').find("input:checked").click().click();//触发两次click构造选中效果
					},
					items:orignQrcodeItems
				});
			}else{
				$("#xzewm").bind("click.org",function(){
					$.toast("该门店不存在二维码，请联系管理员","cancel");
				});
			}
		}
		
		
		//构造列表
		if(stores.length!=0){
			$("#xzmd").unbind("click");
			$("#xzmd").select({
				title: "选择门店",
				items:storeItems,
				onChange:function(d){
					qrcodeItems=[];
					 $("#xzewm").attr('data-values',"");
					 $("#xzewm").val('');//value
					var storeNo = d.values;//单选触发联动
					$.each(qrcodes,function(i,item){
						if(item.storeId==storeNo){
							qrcodeItems.push({title: item.qrName,value: item.qrCode});
						}
					});
					
					//二维码选择
					if(qrcodeItems.length!=0){
						$("#xzewm").unbind(".select");
						if(!$("#xzewm").data("weui-select")){
							$("#xzewm").unbind(".org");
							$("#xzewm").select({
								title: "选择二维码",
								multi: true,
								min: 1,
								max: 100,
								onOpen:function(){
									$('.weui-picker-container.weui-picker-container-visible:not(:last)').remove();
									$('.weui-picker-container.weui-picker-container-visible').find("input:checked").click().click();//触发两次click构造选中效果
								},
								items:qrcodeItems
							});
						}else{
							$("#xzewm").data("weui-select").config.items=qrcodeItems;
						}

					}else{
						$("#xzewm").bind("click.select",function(){
							$.toast("该门店不存在二维码，请联系管理员","cancel");
						});
					}
				}
			});
		}else{
			$("#xzmd").click(function(e){
				$.toast("门店不存在或者尚未审核通过,请联系管理员",'cancel');
			});
		}
		
	}).error(function(){
		
	});
	
	$("#xzewm").click(function(){
		if($("#xzmd").val()==''){
			$.toast("请先选择门店","cancel");
		}
	});
	
	//绑定按钮事件处理
	$("#sure").click(function(){
		var codes = $("#xzewm").attr('data-values');
		var stores = $("#xzmd").attr('data-values');
		if(!stores){
			$.toast("请选择门店信息","cancel");
			return;
		}
		if(!codes){
			$.toast("请选择二维码","cancel");
			return;
		}
		$.ajax({
			   type: "POST",
			   url: basePath+"/scancode/bindCasiherOperator",
			   data:{"cashierNo":cashier,"qrCode":codes,"storeNo":stores},
			   dataType:"json"
			}).done(function(result){
				$.toast(result.errorMsg,function() {
					location.href=basePath+"/scancode/cashier";
				});
				
			});
	});
	
});

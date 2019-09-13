$(function(){
	//var url=$("#qrCodeUrl").val();
	$("#list").delegate(".users","click",function() {
		var $this = $(this);
		var cashier =$(this).attr("cashier");
		var status=$this.find(".status").text();
		var actionsItem=[];
		actionsItem[0]={
				  text: "查看",
				  className: "color-primary",
				  onClick: function() {
					  $("#clickSee").removeClass("open-popup");
					  $("#detail .userName").text($this.find('.userName').text());
					  $("#detail .mobile").text($this.find('.mobile').text());
					  $("#detail .storeName").text($this.find('.storeName').text());
					  $.ajax({
						  type: "POST",
						   url: basePath+"/scancode/seeBindedQrCode",
						   data:{"cashierNo":cashier},
						   dataType:"json"
					  }).done(function(result){
						  $("#clickSee").addClass("open-popup");
						  $("#detail .qrcodeName").text(result.qrcodeName);
						  $("#clickSee").click();
					  }).error(function(error){
						  $("#clickSee").addClass("open-popup");
						  $("#detail .qrcodeName").text("");
						  $("#clickSee").click();
					  });
				  }
				};
		
		if(status==1){//收银中
			actionsItem[1]={
					  text: "编辑",
					  className: "color-warning",
					  onClick: function() {
						 location.href=basePath+'/scancode/bindingCashier/'+cashier;
					  }
					};
		}
		else{//待绑定
			actionsItem[1]={
				  text: "绑定",
				  className: "color-primary",
				  onClick: function() {
					 location.href=basePath+'/scancode/bindingCashier/'+cashier;
				  }
				};
		}
		actionsItem[2]={
				  text: "删除",
				  className: 'color-danger',
				  onClick: function() {
					$.confirm("确定要删除收银员？", function() {
						$.ajax({
							   type: "POST",
							   url: basePath+"/scancode/delCashier/"+cashier,
							   dataType:"json"
						}).done(function(msg){
							$.toast(msg.errorMsg);
							$this.remove();
							if($('#list a').length==0){//删除最后一个改变页面布局
								$("#noCashier").show();
								$("#hasCashier").hide();
							}
						}).error(function(msg){
							$.toast("删除失败!",'cancel');
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
	
	//二维码
	/*$("#code").qrcode({ 
	    //render: "table", //table方式 
	    width: 200, //宽度 
	    height:200, //高度 
	    text: url //任意内容 
	}); */
	
	//加载数据
	toPage(startNo);
	//更多
	$("#more").click(function(){
		startNo++;
		toPage(startNo);
		if(startNo>=totalNum){
			 $("#more").hide();
		}
	});
});	
var startNo = 1;//其实页
var totalNum =0;//页码总数
function toPage(pageNumber){
	$.ajax({
		   type: "POST",
		   url: basePath+"/scancode/cashierContent?pageNo="+pageNumber,
		   dataType:"json",
		   success: function(msg){
			   totalNum = msg.totalPages;
			   if(totalNum==0){
				   $("#noCashier").show();
				   $("#hasCashier").hide();
			   }else{
				   $("#hasCashier").show();
				   $("#noCashier").hide();
			   }
			   if(totalNum<=1){
				   $("#more").hide();
			   }
			   var content=msg.result;
			   if(content){
				   var length=content.length;
				   $.each(content,function(i,item){
					   $("#list").append(setContent(item));
				   });
			   }
			   
		   }
		});
}


function setContent(obj){
	return '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg users" cashier="'+obj.cashierNo+'">                       '+
	'	<div class="weui-media-box__hd">                                                                               '+
	'		<img class="weui-media-box__thumb" src="'+basePath+'/resources/images/wechat/cashier2.png" alt="收银员"> '+
	'	</div>                                                                                                    '+
	'	<div class="weui-media-box__bd">                                                                               '+
	'		<h4 class="weui-media-box__title userName">'+obj.userName+'(今日收款:'+convertMoney(obj.cashierAmt)+')</h4>                                                                '+
	'		<p class="weui-media-box__desc storeName">'+obj.storeName+'</p>                                                            '+
	'		<p class="skz">'+convertStatus(obj.bindStatus)+'</p>                                                                             '+
	'		<p style="display:none" class="status">'+obj.bindStatus+'</p>                                                                             '+
	'		<p style="display:none" class="mobile">'+obj.mobile+'</p>                                                                             '+
	'	</div>                                                                                                    '+
	'</a>                                                                                                         ';
}
function convertStatus(status){
	return status=='1'?"已绑定":"待绑定";
}
function convertMoney(money){
	if(!money){
		return "0.00";
	}else{
		return money.toFixed(2);
	}
}
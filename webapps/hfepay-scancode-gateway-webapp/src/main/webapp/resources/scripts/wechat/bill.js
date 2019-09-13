$(function(){
	toPage(startNo);
	
	$.ajax({
		   type: "POST",
		   url: basePath+"/scancode/storeList",
		   //data:dataJson,
		   success: function(msg){
			   var stores = JSON.parse(msg);
			   var items = [{title: "全部",value: ""}];
			   $.each(stores,function(i,item){
				   items.push({title: item.storeName,value: item.storeNo});
			   });
			   $("#store_screen").select({
					title: "选择交易类型",
					items: items,
					onChange:function(val){
						var type=val.values;//选中的类型
						$("#storeNo").val(type);
						$("#list").html('');
						startNo=1;
						totalNum=1;
						loading=false;
						toPage(startNo);
					}
				});
			   
		   },
		   error:function(){
			   
		   }
		});
	
	$("#list").delegate(".weui-cell","click",function(){
		var $this = $(this);
		var spans=$("#details span");
		$.each(spans,function(i,item){
			var classAtt = $(item).attr("class");
			var val = $this.find("."+classAtt).text();
			$(item).text(val);
		});
		$(this).addClass("open-popup");
	});
	
	$("#type_screen").select({
		title: "选择交易类型",
		items: [
		  {
			title: "全部",
			value: "",
		  },
		  {
			title: "收款",
			value: "02",
		  },
		  {
			title: "提现",
			value: "01",
		  }
		],
		onChange:function(val){
			var type=val.values;//选中的类型
			$("#tradeType").val(type);
			$("#list").html('');
			startNo=1;
			totalNum=1;
			loading=false;
			toPage(startNo);
		}
	});
});

var loading = false;//等待加载，在数据未加载完毕之前不允许再次加载
var startNo = 1;//其实页
var totalNum =1;//页码总数
var timer;//当前定时器
var lastTimer;//上一个定时器
$(document.body).infinite().on("infinite", function() {
	if(loading) return;
	loading = true;
	if(lastTimer){
		clearTimeout(lastTimer);
	}
	lastTimer = timer;
	if(startNo<totalNum){
		$(".weui-loadmore").show();
		startNo++;
		timer = setTimeout(function() {
			if(timer){
				 clearTimeout(timer);
			 }
			 $(".weui-loadmore").hide();
			toPage(startNo);
			loading=false;
		}, 2000);
	}else{
		loading=true;
		$(".weui-loadmore").hide();
		return;
	}
});


function toPage(pageNumber){
	var dataJson = {"pageNo":pageNumber,"tradeType":$("#tradeType").val(),"storeNo":$("#storeNo").val()}
	$.ajax({
		   type: "POST",
		   url: basePath+"/scancode/billContent",
		   data:dataJson,
		   success: function(msg){
			   $(".weui-loadmore").hide();
			   if(totalNum==0){
				   $("#list").html(msg);
				   return;
			   }
					   $("#list").append(msg);
					   totalNum = $("#totalPagescon").val();//当具备筛选条件的时候页码变化可能出现问题
			   
		   },
		   error:function(){
			   $(".weui-loadmore").hide();
		   }
		});
}

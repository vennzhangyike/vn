$(function(){
	toPage(startNo);
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
	var dataJson = {"pageNo":pageNumber}
	$.ajax({
		   type: "POST",
		   url: basePath+"/scancode/withdrawlsBillContent",
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

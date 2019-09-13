$(function(){
	Metronic.init(); // 初始化框架核心组件
	Layout.init(); // 初始化网格
	QuickSidebar.init(); // 初始化快捷操作栏
	Demo.init(); // 初始化项目
	
	$("#cancle").click(function(){
		$(".form-horizontal input").val('');
		$(".form-horizontal select").val('');
		$(".form-horizontal textarea").val('');
	});
});
//获取到项目路径
var localObj = window.location;
var contextPath = localObj.pathname.split("/")[1];
var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;

//查询列表前置操作
function refreshData(doEle){
	$("#" + doEle).find("tbody").remove();
}

//查询列表异常操作
function hasError(XMLHttpRequest, textStatus, errorThrown,doEle){
	//$("#" + doEle).empty();
}

//数据处理
function dataOpr() {
	//时间数据转换格式
	var text = $(".date_time_parse");
	for(var i = 0; i < text.size();i++){
		var parse = dateTimeParse($(text[i]).text());
		$(text[i]).attr("title",$(text[i]).text());
		$(text[i]).removeClass("date_time_parse");
		$(text[i]).html(parse);
	}
	
  function toDecimal2(x) { 
		var f = parseFloat(x); 
		if (isNaN(f)) { 
		  return false; 
		} 
		var f = Math.round(x*100)/100; 
		var s = f.toString(); 
		var rs = s.indexOf('.'); 
		if (rs < 0) { 
		  rs = s.length; 
		  s += '.'; 
		} 
		while (s.length <= rs + 2) { 
		  s += '0'; 
		} 
		return s; 
  }
  
  //强制保留2位小数，如：2，会在2后面补上00.即2.00 
	text = $(".fee_two_parse");
	for(var i = 0; i < text.size();i++){
		var val = $(text[i]).text().trim();
		$(text[i]).attr("title",val);
		$(text[i]).html(toDecimal2(val));
	}
	
	
	//数据转换（只能转换为本页存在的数据）
	text = $(".parent_id_parse");
	for(var i = 0; i < text.size();i++){
		var parse = $(text[i]).attr("title");
		var val = $(text[i]).text().trim();
		var ops=$("#"+parse).find("option");
		for(var k = 0; k < ops.size();k++){
			var opval = $(ops[k]).val();
			$(text[i]).attr("title",val);
			if(opval == val && val != '' && val != null){
				$(text[i]).html($(ops[k]).text());
			}
		}
	}
	
	//下拉框数据回选
	text = $("select.form-control");
	for(var i = 0; i < text.size();i++){
		var $this = $(text[i]);
		var id = $this.attr("id");
		var val = $("."+id).val();
		var ops = $this.find("option");
		for(var j = 0; j < ops.size();j++){
			var opval = $(ops[j]).val();
			if(opval == val){
				$(ops[j]).attr("selected","selected");
			}else{
				$(ops[j]).removeClass("selected");
			}
		}
	}
	
	$(".pageboxBtn").unbind ('click');
	$(".pageboxBtn").on("click",function(){
		var maxPage = $(this).attr("val");
		var inputPage = $("#pageboxInpute").val();
		if(inputPage == null || inputPage == ''){
			bootbox.alert("请输入正整数！",null);
			return false;
		}
		var t = parseInt(inputPage);
		if(isNaN(t) || t <= 0){
		       bootbox.alert("请输入正整数！",null);
	    }else{
		   if(t > parseInt(maxPage)){
			   bootbox.alert("输入的数字不能大于最大页数！",null);
		   }else{
			   toPage(inputPage);
	   	   }
	    }
	});
}


//日期处理（结合时区函数）
function dateTimeParse(fmt) {
	if(fmt.length == 17 || fmt.trim() == ''){
		return "";
	}
  var now = timeOffset(fmt);
  return dateTimeFormat(now);
}

//当前日期处理
function dateTime() {
  var now = new Date();
  return dateTimeFormat(now);
}

//日期格式化
function dateTimeFormat(now) {
  var year = now.getFullYear();
  var month = now.getMonth();
  var date = now.getDate();
  var day = now.getDay();
  var hour = now.getHours();
  var minu = now.getMinutes();
  var sec = now.getSeconds();
  var week;
  month = month + 1;
  if (month < 10) month = "0" + month;
  if (date < 10) date = "0" + date;
  if (hour < 10) hour = "0" + hour;
  if (minu < 10) minu = "0" + minu;
  if (sec < 10) sec = "0" + sec;
  var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
  week = arr_week[day];
  var time = "";
  time = year + "-" + month + "-" + date + "" + " " + hour + ":" + minu + ":" + sec + " " + week;
  return time;
}

//时区处理
function timeOffset(fmt) {
	var timeket = 0;
	if(fmt.indexOf("HKT")>0){
		 //js处理HKT异常
	     timeket = 1;
	     fmt = fmt.replace("HKT","CST");
	}
  var now = new Date(fmt);
  //先将时间格式化到格林威治时区，正常来说是在中国区（GMT）打开界面
  //getTimezoneOffset是-480（分钟为单位），负数是中国在东边，比格林威治早，中国更早见到太阳
  var timeval = now.getTime()+now.getTimezoneOffset()*60*1000;
  if(fmt.indexOf("CST")>0){
  	//服务器时区是CST(中部标准时区)，比格林威治晚了6个小时，所以获取格式化之后的毫秒数减去6个小时的毫秒数，即为正确时间
  	timeval -= 6*60*60*1000;
  }
  if(timeket == 1){
	    //js处理HKT异常
  	//timeval -= 2*60*60*1000;
	}
	now = new Date(timeval);
  return now;
}
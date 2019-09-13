$(function(){
	var basePath = $("#baseUrl").text().trim();
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

   getDate();
    
    $("#query").click(function(){
		getDate();
	});
    
	$("#cancle").click(function(){
		$(".form-horizontal input").val('');
	});
	
	//tab切换
	$(".dateStr").click(function(){		
		//先给所有一级块设置默认色
		$(".dateStr").removeClass("label-success");
		$(".dateStr").addClass("label-info");
		$(this).removeClass("label-info");
		$(this).addClass("label-success");
		//$(".date-picker").val("");
		getDate();
	});
	
	$(".export").click(function(){
		var startDateVal = $("#startDate").val();//开始时间
		var type = $(".dateStr.label-success").attr("val");//时间维度
		var channelNo = $("#channelNo").val();
		var agentNo = $("#agentNo").val();
		var merchantNo = $("#merchantNo").val();
		var datajson ="startTime="+startDateVal+"&type="+type+"&channelNo="+channelNo+"&agentNo="+agentNo+"&merchantNo="+merchantNo;
		window.open(basePath+"/adminManage/statistic/export/countexcel?"+datajson);
	});
	
	function getDate(){
		var startDateVal = $("#startDate").val();//开始时间
		var type = $(".dateStr.label-success").attr("val");//时间维度
		var channelNo = $("#channelNo").val();
		var agentNo = $("#agentNo").val();
		var merchantNo = $("#merchantNo").val();
		
		var datajson = {"startTime":startDateVal,"type":type,"channelNo":channelNo,"agentNo":agentNo,"merchantNo":merchantNo};
		var myChart = echarts.init(document.getElementById('main'),'macarons');
		myChart.showLoading();
		$.ajax({
			   type: "POST",
			   url:basePath+'/adminManage/statistic/orderStaticContent',
			   data:datajson,
			   dataType:"json"
		}).done(function(dataRe){
			var data = dataRe.values;
			var option = {
			        title: {
			            text: data.title//startTime[0] + '-' + startTime[1] + '  ~  ' + endTime[0] + '-' + endTime[1]
			        },
			        tooltip: {
			        	trigger: 'axis',
			            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			            }
			        },
			        toolbox: {
			            feature: {
			                dataView: {show: false, readOnly: false},
			                magicType: {show: true, type: ['line', 'bar']},
			                restore: {show: true},
			                saveAsImage: {show: true}
			            }
			        },
			        legend: {
			            data:['支付宝成功笔数','支付宝失败笔数','微信成功笔数','微信失败笔数','QQ成功笔数','QQ失败笔数']
			        },
			        xAxis: {
			            data: data.hCoordinateList
			        },
			        yAxis: {},
			        series: [{
			            name: '支付宝成功笔数',
			            type:'line',
			            data: data.orderPaySuccessAliData
			        },{
			            name: '支付宝失败笔数',
			            type:'bar',
			            data: data.orderPayFailAliData
			        },{
			            name: '微信成功笔数',
			            type:'line',
			            data: data.orderPaySuccessWechatData
			        },{
			            name: '微信失败笔数',
			            type:'bar',
			            data: data.orderPayFailWechatData
			        },{
			            name: 'QQ成功笔数',
			            type:'line',
			            data: data.orderPaySuccessQQData
			        },{
			            name: 'QQ失败笔数',
			            type:'bar',
			            data: data.orderPayFailQQData
			        }]
			     };

				 myChart.hideLoading();
				 // 填入数据
			     myChart.setOption(option);
		});
			  
	}
});


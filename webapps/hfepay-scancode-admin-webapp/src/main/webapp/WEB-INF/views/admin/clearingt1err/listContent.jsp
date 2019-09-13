<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="page"%>
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered table-hover"
	id="sample_6">
	<thead>
		<tr>
			<th>交易流水</th>
			<th>渠道名称</th>
			<th>代理商名称</th>
			<th>商户名称</th>
			<th>交易金额</th>
			<th>交易类型</th>
			<th>支付代码</th>
			<th>错账类型</th>
			<th>处理标示</th>
			<th>对账日期</th>
			<th>交易日期</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody id="tableContent">

		<c:forEach var="item" items="${pager.result}">
			<tr>
				<td>${item.tradeNo}</td>
				<td>${item.channelName}</td>
				<td>${item.agentName}</td>
				<td>${item.merchantName}</td>
				<td>${item.transAmt}</td>
				<td>${item.tradeType}</td>
				<td>
						<c:if test="${item.payCode=='WXGZHZF' }">
							微信公众号支付
						</c:if>
						<c:if test="${item.payCode=='WXSMZF' }">
						微信扫码支付
						</c:if>
						<c:if test="${item.payCode=='ZFBSMZF' }">
						支付宝扫码支付
						</c:if>
				</td>
				<td align="center"><c:if test="${item.checkFlag == '01'}">
										对方有本地无
									</c:if> <c:if test="${item.checkFlag == '10'}">
										<span style="color: #f00">本地有对方无</span>
									</c:if> <c:if test="${item.checkFlag == '11'}">
										双方不一致
									</c:if></td>
				<td><c:if test="${item.processingStatus == '0'}">
										未处理
									</c:if> <c:if test="${item.processingStatus == '1'}">
										处理成功
									</c:if> <c:if test="${item.processingStatus == '2'}">
										处理失败
									</c:if></td>
				<td class="date_time_parsed">${item.clearDate}</td>
				<td class="date_time_parsed">${item.tradeDate}</td>
				<td>
				 <a onclick="javascript:todetail('${item.id}');"> <span class="label label-default">查看详情</span></a>
				 <c:if test="${item.processingStatus=='1' }">
				 			<a href="javascript:void(0)"><span class="label label-none">调账</span></a>
				 </c:if>
				 <c:if test="${item.processingStatus !='1' }">
				 			<a href="javascript:update('${item.id }','${item.checkFlag }');"><span class="label label-danger">调账</span></a>
				 </c:if>
				 </td>
			</tr>
		</c:forEach>
		<c:if test="${pager.result.size() == 0}">
			<tr>
				<td class="page-nodata" colspan="14">查无数据！</td>
			</tr>
		</c:if>
	</tbody>
</table>
<script type="text/javascript">
dataOpr();
						//显示详情
						function todetail(id){
							$.ajax({
								   type: "POST",
								   url: "clearingt1err/detail/"+id,
								   success: function(msg){
									   var btn = {success:{   
									       label: "关闭",
									       className: "btn-default",
									       callback: function() {
									       }
									     }};
									   bootbox.dialog({
										   message: msg,
										   title: "详情列表",
										   onEscape: function() {},
										   className: "dialog-class",
										   buttons: btn
										 });
								   }
							});
						}
						
						function dateParse(){
							var text = $(".date_time_parsed");
							for(var i = 0; i<text.size();i++){
								var date=$(text[i]).text();
								var year=date.substring(0,4);
								var month=date.substring(4,6);
								var day=date.substring(6,8);
								var date_week = new Date(year,month,day).getDay();
								    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
								    week = arr_week[date_week];
								    var time = "";
								    time = year + "-" + month + "-" + day + ""  + " " + week;
								    $(text[i]).attr("title",$(text[i]).text());
								    $(text[i]).html(time);
							}
						}
						dateParse();
// 						//日期处理（结合时区函数）
// 						function dateTimeParse(fmt) {
// 							if(fmt.length == 17 || fmt.trim() == ''){
// 								return "";
// 							}
// 						    var now = timeOffset(fmt);
// 						    var year = now.getFullYear(); //getFullYear getYear
// 						    var month = now.getMonth();
// 						    var date = now.getDate();
// 						    var day = now.getDay();
// 						    var hour = now.getHours();
// 						    var minu = now.getMinutes();
// 						    var sec = now.getSeconds();
// 						    var week;
// 						    month = month + 1;
// 						    if (month < 10) month = "0" + month;
// 						    if (date < 10) date = "0" + date;
// 						    if (hour < 10) hour = "0" + hour;
// 						    if (minu < 10) minu = "0" + minu;
// 						    if (sec < 10) sec = "0" + sec;
// 						    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
// 						    week = arr_week[day];
// 						    var time = "";
// 						    time = year + "-" + month + "-" + date + "" + " " + hour + ":" + minu + ":" + sec + " " + week;
// 						    return time;
// 						}
						
						//时区处理
						function timeOffset(fmt) {
						    var now = new Date(fmt);
						    //先将时间格式化到格林威治时区，正常来说是在中国区（GMT）打开界面
						    //getTimezoneOffset是-480（分钟为单位），负数是中国在东边，比格林威治早，中国更早见到太阳
						    var timeval = now.getTime()+now.getTimezoneOffset()*60*1000;
						    if(fmt.indexOf("CST")>0){
						    	//服务器时区是CST，比格林威治晚了6个小时，所以获取格式化之后的毫秒数减去6个小时的毫秒数，即为正确时间
						    	timeval -= 6*60*60*1000;
						    }
					    	now = new Date(timeval);
						    return now;
						}
			</script>
<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}" />
<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条</b>

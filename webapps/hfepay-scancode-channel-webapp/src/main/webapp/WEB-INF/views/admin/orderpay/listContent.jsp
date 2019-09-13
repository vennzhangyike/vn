<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th>支付订单编号</th>
								<th>交易订单编号</th>
								<!-- <th>渠道编号</th> -->
								<!-- <th>渠道名称</th> -->
								<!-- <th>代理商编号</th> -->
								<th>代理商名称</th>
								<!-- <th>商户编号</th> -->
								<th>商户名称</th>
								<!-- <th>支付二维码编号</th> -->
								<th>交易类型</th>
								<th>交易通道</th>
								<th>订单金额(元)</th>
								<!-- <th>商品名称</th> -->
								<th>支付状态</th>
								<th>交易时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.payNo}</td>
									<td>${item.tradeNo}</td>
									<%-- <td>${item.channelNo}</td> --%>
									<%-- <td>${item.channelName}</td> --%>
									<%-- <td>${item.agentNo}</td> --%>
									<td>${item.agentName}</td>
									<%-- <td>${item.merchantNo}</td> --%>
									<td>${item.merchantName}</td>
									<%-- <td>${item.qrCode}</td> --%>
									<td>
									<c:if test="${item.tradeType=='01'}">
											提现
										</c:if>
										<c:if test="${item.tradeType=='02' }">
											收款
										</c:if>	
									</td>
									<td class="parent_id_parse" title="payCode">${item.payCode}</td>
									<td>${item.orderAmt}</td>
									<%-- <td>${item.productName}</td> --%>
									<td>
									<c:if test="${item.payStatus=='00'}">
										初始状态
									</c:if>
									<c:if test="${item.payStatus=='01' }">
										渠道支付中
									</c:if>	
									<c:if test="${item.payStatus=='02' }">
										支付成功
									</c:if>	
									<c:if test="${item.payStatus=='03' }">
										支付失败
									</c:if>	
									</td>
									<td class="date_time_parse">${item.beginTime}</td>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
								<tr>
									<td class="page-nodata" colspan="11">查无数据！</td>
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
								   url: "orderpay/detail/"+id,
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
						
						$(function(){
							var text = $(".date_time_parse");
							for(var i = 0; i < text.size();i++){
								var parse = dateTimeParse($(text[i]).text());
								$(text[i]).attr("title",$(text[i]).text());
								$(text[i]).attr("class","date_time_parsed");
								$(text[i]).html(parse);
							}
							
							text = $(".parent_id_parse");
							for(var i = 0; i < text.size();i++){
								var parse = $(text[i]).attr("title");
								var val = $(text[i]).text().trim();
								var ops=$("#"+parse).find("option");
								for(var k = 0; k < ops.size();k++){
									var opval = $(ops[k]).val();
									$(text[i]).attr("title",val);
									if(opval == val){
										$(text[i]).html($(ops[k]).text());
									}
								}
							}
						});
						
						//日期处理（结合时区函数）
						function dateTimeParse(fmt) {
							if(fmt.length == 17 || fmt.trim() == ''){
								return "";
							}
						    var now = timeOffset(fmt);
						    var year = now.getFullYear(); //getFullYear getYear
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"  totalCounts="${pager.totalCount }"/>				
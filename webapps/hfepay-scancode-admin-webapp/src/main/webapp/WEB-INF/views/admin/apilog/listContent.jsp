<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th>第三方支付通道代码</th>
								<th>交易订单编号</th>
								<th>渠道编号</th>
								<th>代理商编号</th>
								<th>商户编号</th>
								<th>创建时间</th>
								<th>更新时间</th>
								<th>回调时间</th>
								<!-- <th>备注</th> -->
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td class="parent_id_parse" title="payCode">${item.payCode}</td>
									<td class="parent_id_parse" title="tradeNo">${item.tradeNo}</td>
									<td class="parent_id_parse" title="channelNo">${item.channelNo}</td>
									<td class="parent_id_parse" title="agentNo">${item.agentNo}</td>
									<td class="parent_id_parse" title="merchantNo">${item.merchantNo}</td>
									<td class="date_time_parse">${item.createTime}</td>
									<td class="date_time_parse">${item.updateTime}</td>
									<td class="date_time_parse">${item.notifyTime}</td>
									<%-- <td class="parent_id_parse" title="remark">${item.remark}</td> --%>

							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
					<tr>
						<td class="page-nodata" colspan="10">查无数据！</td>
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
								   url: "apilog/detail/"+id,
								   success: function(msg){
									   var btn = {success:{   
									       label: "关闭",
									       className: "btn-success",
									       callback: function() {
									       }
									     }};
									   bootbox.dialog({
										   message: msg,
										   title: "详情列表",
										   onEscape: function() {},
										   className: "green-haze",
										   buttons: btn
										 });
								   }
							});
						}
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>	<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条			
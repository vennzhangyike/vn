<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<div class="data_collect2">
	<table>
		<tbody>
			<tr>
				<td>
					<div class="icoStyle ico1"></div>
					<div class="data" id="bpos_money_s">
						<p>用户短信总条数</p>
						<strong>${smsMap.smsTotal}</strong>
					</div>
				</td>				
				<td>
					<div class="icoStyle ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>发送成功</p>
						<strong>${smsMap.smsSuccessTotal}</strong>
					</div>
				</td>
				<td>
					<div class="icoStyle ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>发送失败</p>
						<strong>${smsMap.smsFailTotal}</strong>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-globe"></i>用户短信记录列表
	</div>
</div>
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<!-- <th>渠道编号</th> -->
								<th>渠道名称</th>
								<th>代理商编号</th>
								<th>代理商名称</th>
								<!-- <th>商户编号</th> -->
								<th>商户名称</th>
								<th>手机号</th>
								<th>发送结果</th>
								<th>发送时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<%-- <td class="parent_id_parse" title="channelNo">${item.channelNo}</td> --%>
									<td class="parent_id_parse" title="channelNo">${item.channelName}</td>
									<td class="parent_id_parse" title="agentNo">${item.agentNo}</td>
									<td class="parent_id_parse" title="agentNo">${item.agentName}</td>
									<%-- <td class="parent_id_parse" title="merchantNo">${item.merchantNo}</td> --%>
									<td class="parent_id_parse" title="merchantNo">${item.merchantName}</td>
									<td class="parent_id_parse" title="phone">${item.phone}</td>
									<td class="parent_id_parse" title="sendResult">
										<c:if test="${item.sendResult=='1' }">发送成功</c:if>
										<c:if test="${item.sendResult=='0' }">发送失败</c:if>
									</td>
									<td class="date_time_parse">${item.createTime}</td>

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
								   url: "usersms/detail/"+id,
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>	<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条</b>			
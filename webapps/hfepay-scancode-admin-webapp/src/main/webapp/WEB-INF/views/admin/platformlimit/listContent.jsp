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
								<th>限额通道</th>
								<th>限额类型</th>
								<th>全日交易限额(元)</th>
								<th>全日提现限额(元)</th>
								<th>单笔交易限额(元)</th>
								<th>单笔提现限额(元)</th>
								<th>创建时间</th>
								<th>修改时间</th>
								<!-- <th>备注</th> -->
								<th>状态</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td class="parent_id_parse" title="limitPayCode">
									<c:if test="${item.limitPayCode=='1' }">不限</c:if>
									<c:if test="${item.limitPayCode!='1' }">${item.paraName}</c:if>
									</td>
									<td class="parent_id_parse" title="limitType">
										<c:if test="${item.limitType=='1' }">不限</c:if>
										<c:if test="${item.limitType=='2' }">信用卡</c:if>
										<c:if test="${item.limitType=='3' }">借记卡</c:if>
									</td>
									<td class="parent_id_parse" title="dayTransLimit">${item.dayTransLimit}</td>
									<td class="parent_id_parse" title="dayWithdrawalsLimit">${item.dayWithdrawalsLimit}</td>
									<td class="parent_id_parse" title="singleTransLimit">${item.singleTransLimit}</td>
									<td class="parent_id_parse" title="singleWithdrawalsLimit">${item.singleWithdrawalsLimit}</td>
									<td class="date_time_parse">${item.createTime}</td>
									<td class="date_time_parse">${item.updateTime}</td>
									<%-- <td class="parent_id_parse" title="remark">${item.remark}</td> --%>
									<td class="parent_id_parse" title="status">
										<c:if test="${item.status=='1' }">启用</c:if>
										<c:if test="${item.status=='2' }">禁用</c:if>
									</td>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/platformlimit/${item.id}"><span class="label label-info">修改</span></a>
								<a onclick="javascript:del('${item.id}');"><span class="label label-danger">删除</span></a>
						   		<c:if test="${item.status == 2}"><a onclick="javascript:updateStatus(this);" status="1" value="${item.id}"><span class="label label-success">启用</span></a></c:if>
								<c:if test="${item.status == 1}"><a onclick="javascript:updateStatus(this);" status="2" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>	
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
								   url: "platformlimit/detail/"+id,
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/><b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条				
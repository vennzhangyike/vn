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
								<th>渠道编号</th>
								<th>渠道名称</th>
								<th>开通支付通道</th>
								<th>T1交易费率</th>
								<th>T0垫资成本</th>
								<th>提现手续费(元)</th>
								<th>清算手续费(元)</th>
								<th>状态</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
						   			<td class="parent_id_parse">${item.channelNo}</td>
									<td class="parent_id_parse">${item.channelName}</td>
									<td class="parent_id_parse">${item.payName}</td>
									<td class="parent_id_parse"><fmt:formatNumber value="${item.t1Rate}" pattern="#0.#####"/></td>
									<td class="parent_id_parse"><fmt:formatNumber value="${item.t0Rate}" pattern="#0.#####"/></td>
									<td class="parent_id_parse"><fmt:formatNumber value="${item.rate}" pattern="#0.##"/></td>
									<td class="parent_id_parse"><fmt:formatNumber value="${item.rateAmount}" pattern="#0.##"/></td>
									<c:if test="${item.status=='1' || item.status==null}">
										<td class="parent_id_parse">启用</td>
									</c:if>
									<c:if test="${item.status=='2'}">
										<td class="parent_id_parse">禁用</td>
									</c:if>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/channelpayway/${item.id}"><span class="label label-info">修改</span></a>
								<a href="${path}/adminManage/channelpayway/link/${item.id}"><span class="label label-primary">变更记录</span></a>
								<c:if test="${item.status == 2}">
									<a onclick="javascript:updateStatus(this);" status="1" value="${item.id}">
										<span class="label label-success">启用</span>
									</a>
								</c:if>
								<c:if test="${item.status == 1 || item.status==null}">
									<a onclick="javascript:updateStatus(this);" status="2" value="${item.id}">
										<span class="label label-danger">禁用</span>
									</a>
								</c:if>
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
								<tr>
									<td class="page-nodata" colspan="9">查无数据！</td>
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
								   url: "channelpayway/detail/"+id,
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
						
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>	<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条			
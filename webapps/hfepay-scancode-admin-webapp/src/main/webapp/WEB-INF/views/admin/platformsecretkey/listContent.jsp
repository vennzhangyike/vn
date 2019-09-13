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
								<th>华付通道编号</th>
								<th>接入加解密KEY</th>
								<th>创建日期</th>
								<th>修改日期</th>
								<th>秘钥状态</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td class="parent_id_parse" title="payCode">${item.payCode}</td>
									<td class="parent_id_parse" title="joinKey">${item.joinKey}</td>
									<td class="date_time_parse" title="joinType">${item.createTime}</td>
									<td class="date_time_parse" title="joinType">${item.updateTime}</td>
									<c:if test="${item.status=='1'}">
										<td class="parent_id_parse">启用</td>
									</c:if>
									<c:if test="${item.status=='0' ||item.status==null}">
										<td class="parent_id_parse">禁用</td>
									</c:if>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/platformsecretkey/${item.id}"><span class="label label-info">修改</span></a>
								<%-- <a onclick="javascript:del('${item.id}');"><span class="label label-danger">删除</span></a> --%>
						   			<c:if test="${item.status == 0}"><a onclick="javascript:updateStatus(this);" status="1" value="${item.id}"><span class="label label-success">启用</span></a></c:if>
									<c:if test="${item.status == 1}"><a onclick="javascript:updateStatus(this);" status="0" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>	
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="20">查无数据！</td>
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
								   url: "platformsecretkey/detail/"+id,
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
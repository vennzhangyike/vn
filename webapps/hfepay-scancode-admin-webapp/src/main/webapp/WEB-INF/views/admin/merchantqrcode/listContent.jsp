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
								<th>二维码编号</th>
								<!-- <th>商户编号</th> -->
								<th>商户名称</th>
<!-- 								<th>渠道编号</th> -->
								<!-- <th>渠道名称</th> -->
<!-- 								<th>代理商编号</th> -->
								<!-- <th>代理商名称</th> -->
								<th>门店名称</th>
								<th>二维码名称</th>
								<th>二维码类型</th>
								<th>状态</th>
								<th>创建时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td class="parent_id_parse" title="qrCode">${item.qrCode}</td>
									<%-- <td class="parent_id_parse" title="merchantNo">${item.merchantNo}</td> --%>
									<td class="parent_id_parse" title="merchantName">${item.merchantName}</td>
<%-- 									<td class="parent_id_parse" title="channelNo">${item.channelNo}</td> --%>
									<%-- <td class="parent_id_parse" title="channelNo">${item.channelName}</td> --%>
<%-- 									<td class="parent_id_parse" title="agentNo">${item.agentNo}</td> --%>
									<%-- <td class="parent_id_parse" title="agentNo">${item.agentName}</td> --%>
									<td class="parent_id_parse" title="storeName">${item.storeName}</td>
									<td class="parent_id_parse" title="qrName">${item.qrName}</td>
									<td class="parent_id_parse" title="qrType">										
										<c:if test="${item.qrType=='1'}">
											主二维码
										</c:if>
										<c:if test="${item.qrType=='2'}">
											子二维码
										</c:if>
									</td>
									<td class="parent_id_parse" title="qrStatus">${item.qrStatus}</td>
									<td class="date_time_parse">${item.createTime}</td>
							<td>
								<a onclick="javascript:todetail('${item.id}','${item.qrType}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/merchantqrcode/edit/${item.id}/${item.storeId}/${item.qrType}"><span class="label label-info">修改</span></a>
								<c:if test="${not empty item.qrCode}"><a href="${path}/adminManage/qrcodegoods/edit/0/${item.merchantNo}/${item.qrCode}"><span class="label label-success">绑定商品</span></a></c:if>
								<c:if test="${item.qrType=='2'}"><a onclick="javascript:del('${item.id}');"><span class="label label-danger">删除</span></a></c:if>
								<c:if test="${not empty item.qrCode}"><a href="${path}/adminManage/merchantqrcode/print/${item.qrCode}" target="_black"><span class="label label-success">打印二维码</span></a></c:if>
								<c:if test="${item.qrStatus == 1}"><a onclick="javascript:updateStatus(this,${item.qrType});" status="2" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>
								<c:if test="${item.qrStatus == 2}"><a onclick="javascript:updateStatus(this,${item.qrType});" status="1" value="${item.id}"><span class="label label-success">启用</span></a></c:if>
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
						function todetail(id,qrType){
							$.ajax({
								   type: "POST",
								   url: "merchantqrcode/detail/"+id+'/'+qrType,
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>
			<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条</b>		
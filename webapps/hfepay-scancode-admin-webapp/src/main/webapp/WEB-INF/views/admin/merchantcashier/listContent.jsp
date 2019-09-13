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
								<th>收银员编号</th>
								<th>收银员姓名</th>
								<th>门店编号</th>
								<th>门店名称</th>
								<th>商户编号</th>
								<th>商户名称</th>
								<!-- <th>姓名</th> -->
								<th>身份证号</th>
								<th>手机号码</th>
								<!-- <th>收银员微信OPENID</th> -->
								<th>状态</th>
								<th>创建时间</th>
								<!-- <th>备注</th> -->
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td class="parent_id_parse" title="cashierNo">${item.cashierNo}</td>
									<td class="parent_id_parse" title="userName">${item.userName}</td>
									<td class="parent_id_parse" title="storeNo">${item.storeNo}</td>
									<td class="parent_id_parse" title="storeNo">${item.storeName}</td>
									<td class="parent_id_parse" title="merchantNo">${item.merchantNo}</td>
									<td class="parent_id_parse" title="merchantNo">${item.merchantName}</td>
									<td class="parent_id_parse" title="idCard">${item.idCard}</td>
									<td class="parent_id_parse" title="mobile">${item.mobile}</td>
									<%-- <td class="parent_id_parse" title="openId">${item.openId}</td> --%>
									<%-- <td class="parent_id_parse" title="remark">${item.remark}</td> --%>
									<td class="parent_id_parse" title="status">
										<c:if test="${item.status=='1' }">启用</c:if>
										<c:if test="${item.status=='2' }">禁用</c:if>
									</td>
									<td class="date_time_parse">${item.createTime}</td>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/merchantcashier/${item.id}"><span class="label label-info">修改</span></a>
								<a onclick="javascript:del('${item.cashierNo}');"><span class="label label-danger">删除</span></a>
						   		<c:if test="${item.status == 2}"><a onclick="javascript:updateStatus(this);" cashierNo="${item.cashierNo}" status="1" value="${item.id}"><span class="label label-success">启用</span></a></c:if>
								<c:if test="${item.status == 1}"><a onclick="javascript:updateStatus(this);" cashierNo="${item.cashierNo}" status="2" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>
								<c:if test="${not empty item.storeNo }"><a onclick="javascript:settingQrcode('${item.cashierNo}','${item.storeNo}','${item.merchantNo}');"><span class="label label-success">设置收款码</span></a>	</c:if>	
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="13">查无数据！</td>
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
								   url: "merchantcashier/detail/"+id,
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

						//设置收款码
						function settingQrcode(cashierNo,storeNo,merchantNo){
							$.ajax({
								   type: "POST",
								   url: "merchantcashier/setting/"+cashierNo,
								   data:{"storeNo":storeNo,"merchantNo":merchantNo},
								   success: function(msg){
										 var btn = {success:{   
										       label: "关闭",
										       className: "btn-success",
										       callback: function() {
										       }
										     }};
										  bootbox.dialog({
											   message: msg,
											   title: "设置收款码",
											   onEscape: function() {},
											   className: "green-haze",
											   buttons: btn
										  });
								   }
							});
						}
						
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/><b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条</b>			
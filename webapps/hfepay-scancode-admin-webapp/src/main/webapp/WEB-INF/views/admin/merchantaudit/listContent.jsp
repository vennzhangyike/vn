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
								
								<th>商户编号</th>
								<th>商户名称</th>
								<th>姓名</th>
								<th>银行开户行代码</th>
								<th>银行名称</th>
								<th>银行卡号</th>
								<th>手机号码</th>
								<td>账户类型</td>
								<!-- <td>是否开通提现</td> -->
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td class="parent_id_parse" title="merchantNo">${item.merchantNo}</td>
									<td class="parent_id_parse" title="merchantName">${item.merchantName}</td>
									<td class="parent_id_parse" title="name">${item.name}</td>
									<td class="parent_id_parse" title="bankCode">${item.bankCode}</td>
									<td class="parent_id_parse" title="bankName">${item.bankName}</td>
									<td class="parent_id_parse" title="bankCard">${item.bankCard}</td>
									<td class="parent_id_parse" title="mobile">${item.mobile}</td>
									<td>
									<c:if test="${item.accountType == '0'}">
										个人
									</c:if>
									<c:if test="${item.isRealAccount=='1' }">
										公司
									</c:if>
									</td>
									<%-- <td class="parent_id_parse" title="isRealAccount">
										<c:if test="${item.isRealAccount=='Y' }">开通</c:if>
										<c:if test="${item.isRealAccount=='N' }">不开通</c:if>
									</td> --%>

							<td>
								<a onclick="javascript:merReview('${item.merchantNo}','${item.id}');"><span class="label label-success">审核</span></a>
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
						
						//跳转到新增结算账户页面
						function todetail(id){
							$.ajax({
								   type: "POST",
								   url: "id/"+id,
								   success: function(msg){
									   var btn = {success:{   
									       label: "关闭",
									       className: "btn-success",
									       callback: function() {
									       }
									     }};
									   bootbox.dialog({
										   message: msg,
										   title: "结算账户审核",
										   onEscape: function() {},
										   className: "green-haze",
										   buttons: btn
										 });
								   }
							});
						}
						
						//审核
						function merReview(merchantNo,id){
							var errorMsg = "";
								$.ajax({
									   type: "POST",
									   url: "detail/"+id,
									   success: function(msg){
										   var btn = {success:{   
										       label: "审核通过",
										       className: "btn-success",
										       callback: function() {
										    	   var bankCode = $("#bankCode").val();
										    	   if(bankCode ==''){
										    		   alert("请输入银行开户行号！");
										    		   return false;
										    	   }else{
										    		 updDetail(id,"3");
										    	   }
										    	   
										       }
										     },
										     "审核拒绝": {
										        className: "btn-danger",
										        callback: function() {
										    	   updDetail(id,"4");
										        }
							  				  }
										 	};
										   bootbox.dialog({
											   message: msg+'<input type="text" class="form-control" id="auditRemark"  name = "auditRemark" placeholder="审核备注">',
											   title: "详情列表",
											   onEscape: function() {},
											   className: "green-haze",
											   buttons: btn
											 });
									   }
								});
						}
						
						//更新详情
						function updDetail(id,status){
							var remark = $("#auditRemark").val();
							var bankCode = $("#bankCode").val();
								$.ajax({
	 						 		   type: "POST",
	 						 		   url: "audit/platform",
	 						 		   data:{"id":id,"auditStatus":status,"reason":remark,"bankCode":bankCode},
	 						 		   success: function(msg){
	 						 			  msg = $.parseJSON(msg);
	 						 			  bootbox.alert(msg.values,function(){
	 						 				 if(msg.executeStatus == 0){
	 						 					window.location.reload();
	 						 				 }
	 						 			  });
	 						 		   }
	 						 	});						
						}
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/><b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条</b>				
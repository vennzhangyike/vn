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
								<!-- <th>商户编号</th> -->
								<th>门店编号</th>
								<th>商户名称</th>
								<th>门店名称</th>
								<th>门店级别</th>
								<th>服务电话</th>
								<th>门店地址</th>
								<!-- <th>门店介绍</th> -->
								<th>门店状态</th>
								<th>创建时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<%-- <td>${item.merchantNo}</td> --%>
									<td>${item.storeNo}</td>
									<td>${item.merchantName}</td>
									<td>${item.storeName}</td>
									<td class="parent_id_parse" title="storeType">${item.storeType}</td>
									<td>${item.servicePhone}</td>
									<td>${item.storeAddress}</td>
									<%-- <td>${item.storeDesc}</td> --%>
									<td class="parent_id_parse" title="storeStatus">${item.storeStatus}</td>
									<td class="date_time_parse">${item.createTime}</td>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/merchantqrcode/edit/0/${item.storeNo}/2"><span class="label label-info">创建二维码</span></a>
								<a href="${path}/adminManage/merchantstore/edit/${item.id}/${item.merchantNo}"><span class="label label-primary">修改</span></a>
								<c:if test="${item.storeStatus == 0 || item.storeStatus == 1}"><a onclick="javascript:review('${item.id}');"><span class="label label-success">审核</span></a></c:if>
								<c:if test="${item.storeStatus == 2}"><a onclick="javascript:showDetail('${item.id}');"><span class="label label-success">审核</span></a></c:if>
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
								   url: "merchantstore/detail/"+id,
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
						
						//审核
						function review(id){
							var flag = 0;
							var errorMsg = "";
							$.ajax({
								   type: "POST",
								   async:false,
								   url: "merchantstore/list?id="+id+"&storeStatus=2",
								   success: function(msg){
									   msg = $.parseJSON(msg);
									   var size = msg.objList.length;
									   if(size == 0){
										   flag++;
										   errorMsg = "此门店信息已处于审核中状态，请刷新界面再次审核<br/>";
									   }
								   }
							});
							
							$.ajax({
						 		   type: "POST",
						 		   url: "merchantstore/save",
						 		   async:false,
						 		   data:{"id":id,"storeStatus":2,"auditOperator":"XXXX"},
						 		   success: function(msg){
						 			  
						 		   }
							});
							if(flag == 0){
								showDetail(id);
							}else{
								bootbox.alert(errorMsg);
							}
						}
						
						//显示更新
						function showDetail(id){
							$.ajax({
								   type: "POST",
								   url: "merchantstore/detail/"+id,
								   success: function(msg){
									   var btn = {success:{   
									       label: "审核通过",
									       className: "btn-success",
									       callback: function() {
									    	   updDetail(id,"3");
									       }
									     },
									     "审核拒绝": {
									        className: "btn-danger",
									        callback: function() {
									    	   updDetail(id,"4");
									        }
						  				  },
									     "关闭":{
									        className: "btn-success",
									        callback: function() {
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
							$.ajax({
						 		   type: "POST",
						 		   url: "merchantstore/audit",
						 		   data:{"id":id,"storeStatus":status,"auditReson":remark},
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
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/>
			<b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条</b>		
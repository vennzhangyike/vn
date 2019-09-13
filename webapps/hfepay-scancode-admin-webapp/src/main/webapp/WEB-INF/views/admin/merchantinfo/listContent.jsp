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
<!-- 								<th>渠道编号</th> -->
								<th>商户编号</th>
								<th>渠道名称</th>
<!-- 								<th>代理商编号</th> -->
								<!-- <th>代理商编号</th> -->
								<th>代理商名称</th>
								
								<th>商户名称</th>
								<!-- <th>商户简称</th> -->
								<!-- <th>经营类型</th> -->
								<th>实名认证</th>
								<th>商户状态</th>
								<th>入网时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
<%-- 									<td>${item.channelNo}</td> --%>
									<td>${item.merchantNo}</td>
									<td>${item.channelName}</td>
<%-- 									<td>${item.agentNo}</td> --%>
									<%-- <td>${item.agentNo}</td> --%>
									<td>${item.agentName}</td>
									
									<td>${item.merchantName}</td>
									<%-- <td>${item.shortName}</td> --%>
									<%-- <td>${item.busType}</td> --%>
									<td class="parent_id_parse" title="authenStatus">${item.authenStatus}</td>
									<td class="parent_id_parse" title="status">${item.status}</td>
									<td class="date_time_parse">${item.createTime}</td>
							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/merchantinfo/${item.id}"><span class="label label-info">修改</span></a>
								<c:if test="${empty item.qrCode}"><a onclick="javascript:bindqrcode('${item.agentNo}','${item.id}','${item.merchantNo}');"><span class="label label-success">绑定二维码</span></a></c:if>
								<a href="${path}/adminManage/merchantpayway/edit/0/${item.merchantNo}"><span class="label label-info">创建费率</span></a>
								<c:if test="${item.status == 3}"><c:if test="${not empty item.qrCode}"><a href="${path}/adminManage/merchantstore/edit/0/${item.merchantNo}"><span class="label label-default">创建门店</span></a></c:if></c:if>
								<%-- <c:if test="${not empty item.qrCode}"><a href="${path}/adminManage/qrcodegoods/edit/0/${item.merchantNo}/${item.qrCode}"><span class="label label-success">绑定商品</span></a></c:if> --%>
						   		<c:if test="${not empty item.qrCode && (item.status == 0 || item.status == 1)}"><a onclick="javascript:merReview('${item.merchantNo}','${item.id}');" value="${item.id}"><span class="label label-success">审核</span></a></c:if>
								<c:if test="${item.status == 3}"><a onclick="javascript:updateStatus(this);" status="5" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>	
								<c:if test="${item.status == 5}"><a onclick="javascript:updateStatus(this);" status="3" value="${item.id}"><span class="label label-success">启用</span></a></c:if>	
								<%-- <c:if test="${not empty item.qrCode}"><a href="${path}/adminManage/merchantinfo/print/${item.qrCode}" target="_black"><span class="label label-success">打印二维码</span></a></c:if> --%>
<%-- 								<c:if test="${item.status == 3}"><a onclick="javascript:pushMerchant('${item.merchantNo}');"><span class="label label-primary">通知商户</span></a></c:if>	 --%>
								<c:if test="${item.status == 3}"><a href="${path}/adminManage/merchantadmin/0?merchantNo=${item.merchantNo}"><span class="label label-info">新增账号</span></a></c:if>	
								<c:if test="${item.status == 3}">
									<c:if test="${item.creditPayStatus == 1}"><a onclick="javascript:updateCreditPayStatus(this);" status="2" value="${item.id}"><span class="label label-danger">禁用信用卡</span></a></c:if>	
									<c:if test="${item.creditPayStatus == 2}"><a onclick="javascript:updateCreditPayStatus(this);" status="1" value="${item.id}"><span class="label label-success">启用信用卡</span></a></c:if>	
								</c:if>	
								<a onclick="javascript:del('${item.id}');" ><span class="label label-danger">删除</span></a>
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
								   url: "merchantinfo/detail/1/"+id,
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
						
						//通知商户
						function pushMerchant(merchantNo){
							$.ajax({
								   type: "POST",
								   url: "merchantinfo/push/"+merchantNo,
								   success: function(msg){
									   msg = $.parseJSON(msg);
									   sweetReload(msg);
								   }
							});
						}
						
						//显示详情
						function bindqrcode(agentNo,id,merchantNo){
							$.ajax({
								   type: "POST",
								   url: "merchantinfo/bindqrcode?agentNo="+agentNo+"&id="+id+"&merchantNo="+merchantNo,
								   success: function(msg){
									   msg = $.parseJSON(msg);
									   sweetReload(msg);
								   }
							});
						}
						
						//审核
						function merReview(merchantNo,id){
							var flag = 0;
							var errorMsg = ""; 
							$.ajax({
								   type: "POST",
								   async:false,
								   url: "merchantbankcard/list?merchantNo="+merchantNo,
								   success: function(msg){
									   msg = $.parseJSON(msg);
									   var size = msg.objList.length;
									   if(size == 0){
										   flag++;
										   errorMsg = "商户银行卡信息未绑定<br/>";
									   }
								   }
							});
							
							$.ajax({
								   type: "POST",
								   async:false,
								   url: "merchantpayway/list?merchantNo="+merchantNo,
								   success: function(msg){
									   msg = $.parseJSON(msg);
									   var size = msg.objList.length;
									   if(size == 0){
										   flag++;
										   errorMsg = "商户费率信息未绑定<br/>";
									   }
								   }
							});
							
							if(flag == 0){
								$.ajax({
									   type: "POST",
									   url: "merchantinfo/detail/2/"+id,
									   success: function(msg){
										   var btn = {success:{   
										       label: "认证通过",
										       className: "btn-success",
										       callback: function() {
										    	   updDetail(id,"3");
										       }
										     },
										     "认证拒绝": {
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
							}else{
								bootbox.alert(errorMsg);
							}
						}
						
						//更新详情
						function updDetail(id,status){
							var flag = 0;
							var errorMsg = "";
							var remark = $("#auditRemark").val();
							var storeNo = $("#storeNoValue").text().trim();
							var categoryNo = $("#categoryNo").val();
							var address = $("#address").val().trim();
							var isRealAccount = $("#isRealAccount").val();
							if(categoryNo == ''){
								flag++;
								errorMsg = "经营类型必须选择！<br/>";
							}
							if(address == ''){
								flag++;
								errorMsg = errorMsg + "地址不能为空！<br/>";
							}
							if(remark == '' &&  status =='4'){
								flag++;
								errorMsg = errorMsg + "审核备注不能为空！<br/>";
							}
							if(remark.length>200){
								flag++;
								errorMsg = errorMsg + "审核备注长度不能超过200！<br/>";
							}
							if(flag != 0){
								bootbox.alert(errorMsg,function(){
					 			  });
								return;
							}
							
							$.ajax({
						 		   type: "POST",
						 		   url: "merchantinfo/audit/platform",
						 		   data:{"id":id,"status":status,"remark":remark,"storeNo":storeNo,"busType":categoryNo,"address":address,"isRealAccount":isRealAccount},
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
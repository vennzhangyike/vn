<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>申请提现金额(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.sqtxTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>
				<td>
					<div class="ico ico2"></div>
					<div class="data" id="bpos_money_s">
						<p>实际到账金额(元)</p>
						<strong><fmt:formatNumber value="${amtStatic.sjdzTotalAmt}" pattern="#0.##"/></strong>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>	

<div class="portlet-title">
	<div class="caption">
		<i class="fa fa-globe"></i>提现记录列表
	</div>
</div>		
		
	<table class="table table-striped table-bordered table-hover" id="sample_6">
  <thead>
  <tr>
								<th>机构编号</th>
								<th>机构名称</th>
								<th>申请提现金额(元)</th>
								<th>实际到账金额(元)</th>
								<!-- <th>银行开户行代码</th>
								<th>开户行银行名称（支行）</th>
								<th>银行卡号码</th>
								<th>身份证号码</th>
								<th>姓名</th>
								<th>手机号码</th>
								<th>账户类型</th> -->
								<th>提现状态</th>
								<!-- <th>操作人账号</th> -->
								<th>操作人</th>
								<th>申请时间</th>
								<!-- <th>备注</th> -->
								<th>
									 操作
								</th>
							</tr>
						</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.organNo}</td>
									<td>${item.organName}</td>
									<td>${item.balance}</td>
									<td>${item.actualAmount}</td>
									<%-- <td>${item.bankCode}</td>
									<td>${item.bankName}</td>
									<td>${item.bankCard}</td>
									<td>${item.idCard}</td>
									<td>${item.name}</td>
									<td>${item.mobile}</td>
									<td>${item.accountType}</td> --%>
									<td>
										<c:if test="${item.status == 1}">申请中</c:if>
										<c:if test="${item.status == 2}">审核通过</c:if>	
										<c:if test="${item.status == 3}">审核拒绝</c:if>	
									</td>
								    <td>${item.userName}</td>
									<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd E HH:mm:ss"/></td>
									<%-- <td>${item.remark}</td> --%>
									
								<td>
									<a onclick="javascript:todetail('${item.id}');"><span class="label label-default">查看详情</span></a>
									<%-- <a onclick="javascript:printView('${item.id}');"><span class="label label-primary">打印</span></a> --%>
								</td>  
									
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
								<tr>
									<td class="page-nodata" colspan="14">查无数据！</td>
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
								   url: "organwithdrawals/detail/"+id,
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
						 //打印
						function printView(id){ 
							$.ajax({
								   type: "GET",
								   url: "organwithdrawals/print/"+id,
								   success: function(msg){
									   alert(111);
									   msg = $.parseJSON(msg);
									   alert(msg.bankName+msg.executeStatus);
									   if(msg.executeStatus == 0){
										   
										   var oper = 1;
										   html=window.document.body.innerHTML;
										  
										   if(!!msg.bankName){
											   $("#payCodeStr").text($("#payCodeStr").text() + msg.bankName);
										   }
										   if(!!msg.organWithdrawals.organNo){
											   $("#tradeNoStr").text($("#tradeNoStr").text() + msg.organWithdrawals.organNo);
										   }
										   if(!!msg.status){
											   $("#orderStatusStr").text($("#orderStatusStr").text() + msg.status);
										   }
										   if(!!msg.orderTimeStr){
											   $("#orderTimeStr").text($("#orderTimeStr").text() + msg.orderTimeStr);	
										   }										   									   
										   if(!!msg.organWithdrawals.operator){
											   $("#cashierNoStr").text($("#cashierNoStr").text() + msg.organWithdrawals.operator);
										   }
										   if(!!msg.organWithdrawals.actualAmount){
											   $("#orderAmtStr").text($("#orderAmtStr").text() + msg.organWithdrawals.actualAmount);
										   } 									   
										   bdhtml=window.document.body.innerHTML;//获取当前页的html代码 
									       sprnstr="<!--startprint"+oper+"-->";//设置打印开始区域 
									       eprnstr="<!--endprint"+oper+"-->";//设置打印结束区域 
									       prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html 
					                       
									       prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html 
									       window.document.body.innerHTML=prnhtml; 
									       window.print(); 
									       window.document.body.innerHTML=html; 
									   }
								   }
							}); 
								
						} 
			</script>	
			
			
			
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"  totalCounts="${pager.totalCount }"/>				
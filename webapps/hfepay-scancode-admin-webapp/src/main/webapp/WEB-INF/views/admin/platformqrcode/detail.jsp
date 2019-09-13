<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>

<table class="table table-striped table-bordered" id="sample_6">
	<tr>
		<td class="col-md-2 td0">二维码编号</td><td class="col-md-4 parent_id_parse" >${item.qrCode}</td>
		<td class="col-md-2 td0">二维码名称</td><td class="col-md-4 parent_id_parse" >${item.qrName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">渠道编号</td><td class="col-md-4 parent_id_parse" >${item.channelNo}</td>
		<td class="col-md-2 td0">渠道名称</td><td class="col-md-4 parent_id_parse" >${item.channelName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商编号</td><td class="col-md-4 parent_id_parse" >${item.agentNo}</td>
		<td class="col-md-2 td0">代理商名称</td><td class="col-md-4 parent_id_parse" >${item.agentName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" >${item.merchantNo}</td>
		<td class="col-md-2 td0">商户名称</td><td class="col-md-4 parent_id_parse" >${item.merchantName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">二维码图片路径</td><td class="col-md-4 parent_id_parse">
		 	<div id="qrcode">
			        <div id="codeico"><img src="${icon}" height="60px" width="60px"></div>
			 </div>
		</td>
		<td class="col-md-2 td0">二维码描述</td><td class="col-md-4 parent_id_parse" >${item.qrDesc}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户的门店编号</td><td class="col-md-4 parent_id_parse" >${item.storeId}</td>
		<td class="col-md-2 td0">商户的门店名称</td><td class="col-md-4 parent_id_parse" >${item.storeName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">使用状态</td><td class="col-md-4 parent_id_parse">
			<c:if test="${item.useStatus=='1'}">
				已使用 
			</c:if>
			<c:if test="${item.useStatus=='2'}">
				未使用
			</c:if>
		</td>
		<td class="col-md-2 td0">状态</td><td class="col-md-4 parent_id_parse">
			<c:if test="${item.qrStatus=='1'}">
				启用
			</c:if>
			<c:if test="${item.qrStatus=='2'}">
				禁用
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">记录状态</td><td class="col-md-4 parent_id_parse">${item.recordStatus}</td>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse">${item.createTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">修改时间</td><td class="col-md-4 date_time_parse" >${item.updateTime}</td>
		<td class="col-md-2 td0">操作人账号</td><td class="col-md-4 parent_id_parse" >${item.operatorName}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
init('${item.image}');
</script>

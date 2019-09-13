<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">二维码编号</td><td colspan="3">${item.qrCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户编号</td><td>${item.merchantNo}</td>
		<td class="col-md-2 td0">商户名称</td><td>${item.merchantName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">渠道编号</td><td>${item.channelNo}</td>
		<td class="col-md-2 td0">渠道名称</td><td>${item.channelName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商编号</td><td>${item.agentNo}</td>
		<td class="col-md-2 td0">代理商名称</td><td>${item.agentName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">二维码名称</td><td>${item.qrName}</td>
		<td class="col-md-2 td0">二维码描述</td><td>${item.qrDesc}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">二维码图片</td><td colspan="3">
		<c:if test="${not empty item.image}">
				 <div id="qrcode">
				        <div id="codeico"><img src="${icon}" height="60px" width="60px"></div>
				  </div>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">门店编号</td><td class="col-md-4 parent_id_parse" title="storeId">${item.storeId}</td>
		<td class="col-md-2 td0">门店名称</td><td class="col-md-4 parent_id_parse" title="storeName">${item.storeName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">状态</td><td colspan="3" class="col-md-4 parent_id_parse" title="qrStatus">${item.qrStatus}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
		<td class="col-md-2 td0">修改时间</td><td class="col-md-4 date_time_parse" title="updateTime">${item.updateTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">操作人名称</td><td class="col-md-4" title="${item.operator}">${item.operatorName}</td>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" title="remark">${item.remark}</td>
	</tr>
<!-- 	<tr> -->
<%-- 		<td class="col-md-2 td0">temp1</td><td class="col-md-4 parent_id_parse" title="temp1">${item.temp1}</td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<%-- 		<td class="col-md-2 td0">temp2</td><td class="col-md-4 parent_id_parse" title="temp2">${item.temp2}</td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<%-- 		<td class="col-md-2 td0">temp3</td><td class="col-md-4 parent_id_parse" title="temp3">${item.temp3}</td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<%-- 		<td class="col-md-2 td0">temp4</td><td class="col-md-4 parent_id_parse" title="temp4">${item.temp4}</td> --%>
<!-- 	</tr> -->
</table>
<script type="text/javascript">
dataOpr();
if('${not empty item.image}'){
	init('${item.image}');
}
</script>

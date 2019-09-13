<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">第三方支付通道代码</td><td class="col-md-4 parent_id_parse" title="payCode">${item.payCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">交易订单编号</td><td class="col-md-4 parent_id_parse" title="tradeNo">${item.tradeNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">渠道编号</td><td class="col-md-4 parent_id_parse" title="channelNo">${item.channelNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商编号</td><td class="col-md-4 parent_id_parse" title="agentNo">${item.agentNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户编号</td><td class="col-md-4 parent_id_parse" title="merchantNo">${item.merchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">请求参数</td><td class="col-md-4" val='${item.requestParams}' style="word-break: break-all; word-wrap:break-word;max-width: 600px;">
			<c:choose>  
			    <c:when test="${fn:length(item.requestParams) > 20}">  
			        <c:out value="${fn:substring(item.requestParams, 0, 20)}" /><b title="点击展开全部" class="clicktoall">......</b>
			    </c:when>  
			   <c:otherwise>  
			      <c:out value="${item.requestParams}" />  
			    </c:otherwise>  
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">返回参数</td><td class="col-md-4" val='${item.responseParams}' style="word-break: break-all; word-wrap:break-word;max-width: 600px;">
			<c:choose>  
			    <c:when test="${fn:length(item.responseParams) > 20}">  
			        <c:out value="${fn:substring(item.responseParams, 0, 20)}" /><b title="点击展开全部" class="clicktoall">......</b>
			    </c:when>  
			   <c:otherwise>  
			      <c:out value="${item.responseParams}" />  
			    </c:otherwise>  
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">回调参数</td><td class="col-md-4" val='${item.notifyParams}' style="word-break: break-all; word-wrap:break-word;max-width: 600px;">
			<c:choose>  
			    <c:when test="${fn:length(item.notifyParams) > 20}">  
			        <c:out value="${fn:substring(item.notifyParams, 0, 20)}" /><b title="点击展开全部" class="clicktoall">......</b>
			    </c:when>  
			   <c:otherwise>  
			      <c:out value="${item.notifyParams}" />  
			    </c:otherwise>  
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">更新时间</td><td class="col-md-4 date_time_parse" title="updateTime">${item.updateTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">回调时间</td><td class="col-md-4 date_time_parse" title="notifyTime">${item.notifyTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" title="remark">${item.remark}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
$(".clicktoall").click(function(){
	$(this).parent().text($(this).parent().attr("val"));
});
</script>

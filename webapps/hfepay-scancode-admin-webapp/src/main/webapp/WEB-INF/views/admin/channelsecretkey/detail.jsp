<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">渠道编号</td><td class="col-md-4 parent_id_parse" title="channelNo">${item.channelNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">渠道名称</td><td class="col-md-4 parent_id_parse" title="companyName">${item.companyName}</td>
	</tr>
<!-- 	<tr> -->
<%-- 		<td class="col-md-2 td0">渠道类型</td><td class="col-md-4 parent_id_parse" title="channelType">${item.channelType}</td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<%-- 		<td class="col-md-2 td0">优选通道</td><td class="col-md-4 parent_id_parse" title="firstPayobj">${item.firstPayobj}</td> --%>
<!-- 	</tr> -->
<%-- 	<tr>
		<td class="col-md-2 td0">支付通道</td><td class="col-md-4 parent_id_parse" title="payName">${item.payName}</td>
	</tr> --%>
	<tr>
		<%-- <td class="col-md-2 td0">授权IP</td><td class="col-md-4" val='${item.boundIp}' style="word-break: break-all; word-wrap:break-word;max-width: 600px;">
			<c:choose>  
			    <c:when test="${fn:length(item.boundIp) > 20}">  
			        <c:out value="${fn:substring(item.boundIp, 0, 20)}" /><b title="点击展开全部" class="clicktoall">......</b>
			    </c:when>  
			   <c:otherwise>  
			      <c:out value="${item.boundIp}" />  
			    </c:otherwise>  
			</c:choose>
		</td> --%>
	</tr>
	<tr>
		<td class="col-md-2 td0">渠道公钥</td><td class="col-md-4" val='${item.joinUserPublicKey}' style="word-break: break-all; word-wrap:break-word;max-width: 600px;">
			<c:choose>  
			    <c:when test="${fn:length(item.joinUserPublicKey) > 20}">  
			        <c:out value="${fn:substring(item.joinUserPublicKey, 0, 20)}" /><b title="点击展开全部" class="clicktoall">......</b>
			    </c:when>  
			   <c:otherwise>  
			      <c:out value="${item.joinUserPublicKey}" />  
			    </c:otherwise>  
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">平台公钥</td><td class="col-md-4" val='${item.joinPublicKey}' style="word-break: break-all; word-wrap:break-word;max-width: 600px;">
			<c:choose>  
			    <c:when test="${fn:length(item.joinPublicKey) > 20}">  
			        <c:out value="${fn:substring(item.joinPublicKey, 0, 20)}" /><b title="点击展开全部" class="clicktoall">......</b>
			    </c:when>  
			   <c:otherwise>  
			      <c:out value="${item.joinPublicKey}" />  
			    </c:otherwise>  
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">平台私钥</td><td class="col-md-4" val='${item.joinPrivateKey}' style="word-break: break-all; word-wrap:break-word;max-width: 600px;">
			<c:choose>  
			    <c:when test="${fn:length(item.joinPrivateKey) > 20}">  
			        <c:out value="${fn:substring(item.joinPrivateKey, 0, 20)}" /><b title="点击展开全部" class="clicktoall">......</b>
			    </c:when>  
			   <c:otherwise>  
			      <c:out value="${item.joinPrivateKey}" />  
			    </c:otherwise>  
			</c:choose>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建日期</td><td class="col-md-4" title="createTime"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd E"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">修改日期</td><td class="col-md-4" title="updateTime"><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd E"/></td>
	</tr>
	<%-- <tr>
		<td class="col-md-2 td0">操作员</td><td class="col-md-4 parent_id_parse" title="operatorName">${item.operatorName}</td>
	</tr> --%>
	<tr>
		<td class="col-md-2 td0">状态</td>
		<td class="col-md-4 parent_id_parse" title="status">
		
		<c:if test="${item.status=='1'||item.status==null}">
			启用
		</c:if>
		<c:if test="${item.status=='0'}">
			禁用
		</c:if>
		</td>
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

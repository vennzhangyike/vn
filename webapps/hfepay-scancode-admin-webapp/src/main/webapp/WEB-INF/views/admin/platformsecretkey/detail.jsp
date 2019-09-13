<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">华付通道编号</td><td class="col-md-4 parent_id_parse" title="payCode">${item.payCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">用户上传公钥</td><td class="col-md-4" val='${item.joinUserPublicKey}' style="word-break: break-all; word-wrap:break-word;max-width: 600px;">
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
		<td class="col-md-2 td0">接入加解密KEY</td><td class="col-md-4 parent_id_parse" title="joinKey">${item.joinKey}</td>
	</tr>
	<%-- <tr>
		<td class="col-md-2 td0">接入方式1：密文接入，2：明文接入</td><td class="col-md-4 parent_id_parse" title="joinType">${item.joinType}</td>
	</tr> --%>
	<tr>
		<td class="col-md-2 td0">秘钥状态</td>
		<c:if test="${item.status=='0'}">
			<td class="col-md-4 parent_id_parse" title="status">禁用</td>
		</c:if>
		<c:if test="${item.status=='1'}">
			<td class="col-md-4 parent_id_parse" title="status">启用</td>
		</c:if>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建日期</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">修改日期</td><td class="col-md-4 date_time_parse" title="updateTime">${item.updateTime}</td>
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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8"/>
<title>渠道账户信息变更记录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../../include/commoncss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<style type="text/css">
.td0{
	text-align: right;
	font-weight: bolder;
}
</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<%-- <jsp:include page="../../include/head.jsp"></jsp:include> --%>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<%-- <jsp:include page="../../include/menu.jsp"></jsp:include> --%>
	<section id="ucenterSection">
	<%-- <div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="${path}/adminManage/index">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">渠道账户信息变更记录</a>
						<i class="fa fa-angle-right"></i>
					</li>
				</ul>
			</div> --%>
			<div class="row">
				<div class="col-md-12">
					<div class="portlet box green-haze">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-globe"></i>渠道账户信息变更记录
							</div>
						</div>
						<div class="portlet-body" id="tablec">
							<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<td>渠道编号</td>
								<td>渠道名称</td>
								<td>联行号</td>
								<td>开户行名称</td>
								<td>银行卡号码</td>
								<td>身份证号码</td>
								<td>开户名</td>
								<td>手机号码</td>
								<td>账户类型</td>
								<td>修改时间</td>
								</tr>
							</thead>
							<tbody id="tableContent">
							
						   <c:forEach var="item" items="${pager.result}"> 
						   <tr>
								<td>${item.channelNo}</td>
								<td>${item.channelName}</td>
								<td>${item.bankCode}</td>
								<td>${item.bankName}</td>
								<td>${item.bankCard}</td>
								<td>${item.idCard}</td>
								<td>${item.name}</td>
								<td>${item.mobile}</td>
								<td class="parent_id_parse">
								<c:if test="${item.accountType=='1'}">
									公司
								</c:if>
								<c:if test="${item.accountType=='0' }">
									个人
								</c:if>	
								</td>
								<td class="date_time_parse">${item.createTime}</td>		
						  </tr>
						</c:forEach>
						</tbody>
						</table>
						</div>
						
						<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="form">
						<div class="form-body">
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="col-md-10"><page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"  totalCounts="${pager.totalCount }"/>	</label>
										<div class="col-md-2">
											<button type="button" class="btn label-info" id="return" style="float:right;">返回</button>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
						</div>
					</form>
					<!-- END FORM-->
				</div>
			</div>
					</div>
				</div>
			</div>
	</section>
</div>
<jsp:include page="../../include/footerjs.jsp"></jsp:include>

<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/base.js" type="text/javascript"></script>

<script src="${path}/resources/scripts/admin/channel/channelbankcard/link.js?rnd=${version}" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>
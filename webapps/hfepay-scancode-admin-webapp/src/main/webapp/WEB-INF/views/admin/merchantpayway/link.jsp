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
<title>渠道支付方式变更记录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
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
						<a href="#">渠道支付方式变更记录</a>
						<i class="fa fa-angle-right"></i>
					</li>
				</ul>
			</div> --%>
			<div class="row">
				<div class="col-md-12">
					<div class="portlet box green-haze">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-globe"></i>渠道支付方式变更记录
							</div>
						</div>
						<div class="portlet-body" id="tablec">
							<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th>商户编号</th>
								<th>支付通道代码</th>
								<th>提现手续费率</th>
								<th>固定手续费金额</th>
								<th>T0费率</th>
								<th>T1费率</th>
								<!-- <th>记录状态</th> -->
								<th>创建时间</th>
								<th>修改时间</th>
								<td>操作人账号</td>
								<td>备注</td>
								</tr>
							</thead>
							<tbody id="tableContent">
							
						   <c:forEach var="item" items="${pager.result}"> 
						   <tr>
								<td class="">${item.merchantNo}</td>
								<td class="">${item.payCode}</td>
								<td class=""><fmt:formatNumber value="${item.rate}" pattern="#0.#####"/></td>
								<td class=""><fmt:formatNumber value="${item.rateAmount}" pattern="#0.#####"/></td>
								<td class=""><fmt:formatNumber value="${item.t0Rate}" pattern="#0.#####"/></td>
								<td class=""><fmt:formatNumber value="${item.t1Rate}" pattern="#0.#####"/></td>
								<%-- <td class="">${item.recordStatus}</td> --%>
								<td class="date_time_parse">${item.createTime}</td>
								<td class="date_time_parse">${item.updateTime}</td>
								<td class="">${item.operator}</td>
								<td class="">${item.remark}</td>
								
						  </tr>
						</c:forEach>
							<c:if test="${pager.result.size() == 0 || empty pager}">
								<tr>
									<td class="page-nodata" colspan="20">查无数据！</td>
								</tr>
							</c:if>
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
										<label class="col-md-10"><page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/><b class="totalCountInPage">共&nbsp;${pager.totalCount}&nbsp;条	</label>
										<div class="col-md-2">
											<button type="button" class="btn btn-default" id="return" style="float:right;">返回</button>
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
<jsp:include page="../include/footerjs.jsp"></jsp:include>

<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/base.js" type="text/javascript"></script>

<script src="${path}/resources/scripts/admin/merchantpayway/link.js?rnd=${version}" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>
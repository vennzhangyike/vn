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
<title>AdviertisementInfo Edit</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../include/commoncss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${path}/resources/styles/bootstrap-select.css"/>
<link rel="stylesheet" type="text/css" href="${path}/resources/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
<link rel="shortcut icon" href="../favicon.ico"/>
<style type="text/css">
.hiddenRow{
	display: none;
}
</style>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<div class="page-container">
	<!-- BEGIN CONTENT -->
	<section id="ucenterSection">
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="save" class="form-horizontal" id="form" method="post">
						<input type="hidden" id="id"  name = "id" value="${ Obj.id}">
						<div class="form-body">
							
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">机构名称</label>
										<div class="col-md-4">
											<select id="organNo" name="organNo" class="selectpicker show-tick form-control" data-live-search="true">
												<option value = "0">  --  不限  --  </option>
												<c:forEach var="organ" items="${organList}">
													<option value = "${organ.organNo}">${organ.organName}</option>
												</c:forEach>													
									        </select>
									        <input type="hidden" class="form-control organNo" value="${Obj.organNo}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">限额类型</label>
										<div class="col-md-4">
											<select id="limitType" name="limitType" value="${Obj.limitType}" class="form-control">
												<option value = "">  --  请选择  --  </option>
												<c:forEach var="item" items="${limitType}">
													<option value = "${item.value}">${item.desc}</option>
												</c:forEach>
									        </select>
									        <input type="hidden" class="form-control limitType" value="${Obj.limitType}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">限额通道</label>
										<div class="col-md-4">
											<select id="limitPayCode" name="limitPayCode" value="${Obj.limitPayCode}" class="form-control">
												<option value = "">  --  请选择  --  </option>
												<c:forEach var="item" items="${limitPayCode}">
													<option value = "${item.value}">${item.desc}</option>
												</c:forEach>
									        </select>
									        <input type="hidden" class="form-control limitPayCode" value="${Obj.limitPayCode}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">限制方式</label>
										<div class="col-md-4">
											<select id="limitMode" name="limitMode" value="${Obj.limitMode}" class="form-control">
												<option value = "">  --  请选择  --  </option>
												<c:forEach var="item" items="${limitMode}">
													<option value = "${item.value}">${item.desc}</option>
												</c:forEach>
									        </select>
									        <input type="hidden" class="form-control limitMode" value="${Obj.limitMode}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">最低限额</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="minLimit"  name = "minLimit" value="${Obj.minLimit}" placeholder="最低限额">
										</div>
									</div>
								</div>
							</div>
							
							<div class="row maxLimitDiv">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">最高限额</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="maxLimit"  name = "maxLimit" value="${Obj.maxLimit}" placeholder="最高限额">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box green-haze"></div>
									<div class="form-group">
										<label class="control-label col-md-2"></label>
										<div class="col-md-10">
											<button type="submit" class="btn green" id="save">保存</button>
											<button type="button" class="btn default" id="cancle">取消</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
					<!-- END FORM-->
				</div>
			</div>
			<!-- END SEARCH CONDITION -->			
	</section>
</div>
<!-- END CONTENT -->
<!-- END CONTAINER -->
<jsp:include page="../include/footerjs.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<!-- 四级菜单不提供选中和三级菜单紧密绑定，在获取三级ID的时候相应的四级资源必须获取 -->
<div class="fade hide">
<c:forEach var="forth" items="${forthMap}" varStatus="statusforth">
	<div id="forth${forth.key}">
	<c:forEach var="forthMenu" items="${forth.value}" varStatus="statusFF"> 
		<input type="hidden" id="text${forthMenu.id}" value="${forthMenu.id}"/>
	</c:forEach>
	</div>
</c:forEach>
</div>

<script src="${path}/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path}/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/bootstrap-select.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js" type="text/javascript"></script>
<script src="${path}/resources/scripts/common.js" type="text/javascript"></script>
<script type="text/javascript">
	var baseurl = "${path}";
</script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${path}/resources/assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${path}/resources/scripts/jquery.validate.js" type="text/javascript"></script>

<script src="${path}/resources/scripts/admin/organlimit/edit.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>
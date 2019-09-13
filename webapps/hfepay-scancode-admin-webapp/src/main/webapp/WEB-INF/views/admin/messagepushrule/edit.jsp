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
										<label class="control-label col-md-2">渠道名称</label>
										<div class="col-md-4">
											<select id="channelNo" name="channelNo" class="selectpicker show-tick form-control" data-live-search="true">
												<option value = "">  --  请选择  --  </option>
												<c:forEach var="item" items="${channelList}">
													<option value = "${item.channelNo}">${item.channelName}</option>
												</c:forEach>	
									        </select>
									        <input type="hidden" class="form-control channelNo" value="${Obj.channelNo}">
										</div>
									</div>
								</div>
							</div>
							
							<%-- <div class="row">
								<div class="col-md-8">
									<div class="form-group">
										<label class="control-label col-md-3">代理商名称</label>
										<div class="col-md-3">
											<select id="agentNo" name="agentNo" class="selectpicker show-tick form-control" data-live-search="true">
												<option value = "">  --  请选择  --  </option>
												<c:forEach var="item" items="${agentList}">
													<option value = "${item.agentNo}">${item.agentName}</option>
												</c:forEach>	
									        </select>
									        <input type="hidden" class="form-control agentNo" value="${Obj.agentNo}">
										</div>
									</div>
								</div>
							</div> --%>
							
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">代理商级别</label>
										<div class="col-md-4">
											<select class="form-control" id="temp1" name="temp1">
												<option value = "">  --  请选择  --  </option>
												<c:forEach var="item" items="${temp1}">
													<option value = "${item.paraVal}">${item.paraVal}</option>
												</c:forEach>
									        </select>
									        <input type="hidden" class="form-control temp1" value="${Obj.temp1}">
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">消息类型</label>
										<div class="col-md-4">
											<select class="form-control" id="messageType" name="messageType">
												<option value = "">  --  请选择  --  </option>
												<c:forEach var="item" items="${messageType}">
													<option value = "${item.value}">${item.desc}</option>
												</c:forEach>
									        </select>
									        <input type="hidden" class="form-control messageType" value="${Obj.messageType}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">推送规则</label>
										<div class="col-md-4">
											<input type="text" class="form-control" id="pushRule"  name = "pushRule" value="${Obj.pushRule}" placeholder="">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">推送频率</label>
										<div class="col-md-4">
											<select class="form-control" id="pushTime" name="pushTime">
												<option value = "">  --  请选择  --  </option>
												<c:forEach var="item" items="${pushTime}">
													<option value = "${item.value}">${item.desc}</option>
												</c:forEach>
									        </select>
									        <input type="hidden" class="form-control pushTime" value="${Obj.pushTime}">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">推送方式</label>
										<div class="col-md-4">
											<select class="form-control" id="pushWay" name="pushWay">
												<option value = "">  --  请选择  --  </option>
												<c:forEach var="item" items="${pushWay}">
													<option value = "${item.value}">${item.desc}</option>
												</c:forEach>
									        </select>
									        <input type="hidden" class="form-control pushWay" value="${Obj.pushWay}">
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<label class="control-label col-md-2">首次推送日期</label>
										<div class="col-md-4">
											<input type="text" class="form-control date-picker" id="firstPushDateStr" name="firstPushDateStr" readonly="readonly" value="<fmt:formatDate value='${Obj.firstPushDate}' pattern='yyyy-MM-dd HH:mm'/>" >
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

<script src="${path}/resources/scripts/admin/messagepushrule/edit.js" type="text/javascript"></script>
<div style="display: none;" id="baseUrl">${path}</div>
</body>
<!-- END BODY -->
</html>
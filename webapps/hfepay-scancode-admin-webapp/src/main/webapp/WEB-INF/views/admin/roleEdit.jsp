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
<title>角色权限更新</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="include/commoncss.jsp"></jsp:include>
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<%-- <jsp:include page="include/head.jsp"></jsp:include> --%>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<%-- <jsp:include page="include/menu.jsp"></jsp:include> --%>
	<!-- BEGIN CONTENT -->
	<section id="ucenterSection">
			<%-- <div class="page-bar">
				<ul class="page-breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a href="${path }/adminManage/index">首页</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="javascript:history.go(-1);">角色列表</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">修改角色</a>
					</li>
				</ul>
			</div> --%>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal">
						<div class="form-body">
							
							<div class="row">
								<div class="col-md-8">
									<div class="form-group">
										<label class="control-label col-md-2">角色描述</label>
										<div class="col-md-4" style="margin-top: 8px;">
											${roleMsg.description}
											<input type="hidden" class="form-control" id="roleId" value="${roleMsg.id}">
										</div>
										<label class="control-label col-md-2">角色名称</label>
										<div class="col-md-4" style="margin-top: 8px;">
											${roleMsg.roleName}
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
			
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box green-haze">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-globe"></i>资源列表
							</div>
						</div>
						<div class="portlet-body" id="tablec">
							<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th>
									 一级菜单
								</th>
								<th class="hidden-xs">
									 二级菜单
								</th>
							</tr>
							</thead>
							<tbody>
								  <c:forEach var="rootScanAdminMenu" items="${rootScanAdminMenu1}" varStatus="statusroot">
								  <tr>
									<td>
										<input type="checkbox" id="root${rootScanAdminMenu.id }" value="${rootScanAdminMenu.id }" data="${rootScanAdminMenu.hasPerssion }"/><label for="root${rootScanAdminMenu.id }">${rootScanAdminMenu.menuName }</label><!-- 一级菜单菜单名称 -->
									</td>
									<td>
										<table id="secondResource${rootScanAdminMenu.id }"><!-- 二级菜单 -->
											<tbody>
											 	<c:forEach var="second" items="${secondScanAdminMenu1}" varStatus="statusSecond">
											 	 	<c:if test="${rootScanAdminMenu.id==second.key}">
											 	 		<c:forEach var="secondScanAdminMenu" items="${second.value}" varStatus="statusSs"> 
													 		<tr>
																<td>
																	<input type="checkbox" id="second${secondScanAdminMenu.id }" value="${secondScanAdminMenu.id }" data="${secondScanAdminMenu.hasPerssion }"/><label for="second${secondScanAdminMenu.id }">${secondScanAdminMenu.menuName }</label><!-- 二级菜单菜单名称 -->
																</td>
																<td>
																	<table id="thirdResource${secondScanAdminMenu.id }"><!-- 三级菜单 -->
																		<tbody>
																		 	<c:forEach var="third" items="${thirdMap1}" varStatus="statusthird">
																		 	 	<c:if test="${secondScanAdminMenu.id==third.key}">
																		 	 		<c:forEach var="thirdMenu" items="${third.value}" varStatus="statusTT"> 
																				 		<tr>
																							<td>
																								<input type="checkbox" id="third${thirdMenu.id }" value="${thirdMenu.id }" data="${thirdMenu.hasPerssion }"/><label for="third${thirdMenu.id }">${thirdMenu.menuName }</label><!-- 三级级菜单菜单名称 -->
																							</td>
																						</tr>
																					</c:forEach>
																				</c:if>
																		 	</c:forEach>
																		</tbody>
																	</table>
																</td>
															</tr>
														</c:forEach>
													</c:if>
											 	</c:forEach>
											</tbody>
										</table>
									</td>
								  </tr>
								  </c:forEach>
						</tbody>
						</table>
						</div>
						<form action="#" class="form-horizontal" id="form">
							<div class="form-body">
							<div class="row">
								<div class="col-md-12">
									<div class="portlet box green-haze"></div>
									<div class="form-group">
										<label class="control-label col-md-2"></label>
										<div class="col-md-10">
											<button type="button" class="btn green" id="save">保存</button>
											<button type="button" class="btn default" id="cancle">取消</button>
										</div>
									</div>
								</div>
								<!--/span-->
							</div>
							 </div>
						</form>
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			<!-- END PAGE CONTENT-->
	</section>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<jsp:include page="include/footerjs.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<!-- 四级菜单不提供选中和三级菜单紧密绑定，在获取三级ID的时候相应的四级资源必须获取 -->
<div class="fade hide">
<c:forEach var="forth" items="${forthMap1}" varStatus="statusforth">
	<div id="forth${forth.key }">
	<c:forEach var="forthMenu" items="${forth.value}" varStatus="statusFF"> 
		<input type="hidden" id="text${forthMenu.id }" value="${forthMenu.id }"/>
	</c:forEach>
	</div>
</c:forEach>
</div>

<script src="${path }/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path }/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path }/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path }/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>

<script>
jQuery(document).ready(function() {       
Metronic.init(); // init metronic core components
Layout.init(); // init current layout
QuickSidebar.init(); // init quick sidebar
Demo.init(); // init demo features

$("#cancle").click(function(){
	history.go(-1);
});

//已分配的选中
$("input[type='checkbox'][data!=0]").prop('checked',true).attr('checked','checked').parent('span').addClass('checked');
//保存
$("#save").click(function(){
	var checkboxs = $("input[type='checkbox'][checked='checked']");
	var resourceIds= "";
	$.each(checkboxs,function(i,item){
		var resourceId = item.value;
		resourceIds=resourceIds+resourceId+",";
		var id=item.id;
		if(id.indexOf('third')!=-1){
			var forthIds = $("#forth"+resourceId+" input");//四级菜单下的资源
			$.each(forthIds,function(j,it){
				resourceIds=resourceIds+it.value+","; 
			});
		}
	});
	resourceIds=resourceIds.substr(0,resourceIds.length-1);
	$.ajax({
		   type: "POST",
		   url: "saveRoles",
		   data:{"ids":resourceIds,"roleId":$("#roleId").val().trim()},
		   success: function(msg){
			   msg = $.parseJSON(msg);
			   if(msg.isDenied){//没有权限
				  bootbox.alert(msg.message); 
			   	  return;
			   }
			   bootbox.alert(msg.message,function(){
				   window.location.href='../roleList';
			   }); 
		   }
		});
});

$("input").on("click",function(){
	var id=$(this).attr("id");
	var isChecked = $(this).parent().attr("class") == 'checked';
	if(id.indexOf('root')!=-1){//一级资源的checkbox被点击
		if(isChecked){
			$("#secondResource"+id.replace('root','')+" span").addClass("checked").find(':checkbox').attr('checked','checked').prop('checked',true);//选中子资源
			$(this).attr('checked','checked').prop('checked',true);
		}else{
			$("#secondResource"+id.replace('root','')+" span").removeClass("checked").find(':checkbox').removeAttr('checked').prop('checked',false);//取消选中子资源
			$(this).removeAttr('checked').prop('checked',false);
		}
	}else if(id.indexOf('second')!=-1){//二级资源被点击
		var currentId = id.replace('second','');
		var allChild = $('#second'+currentId).parents('table').not('[id="sample_6"]');
		var resourceid=allChild.attr("id").replace("secondResource","");
		if(isChecked){//被选中
			$(this).attr('checked','checked').prop('checked',true);
			$("#thirdResource"+currentId+" span").addClass("checked").find(':checkbox').attr('checked','checked').prop('checked',true);//选中三级菜单
			//检查一级菜单是否选中
			$("#uniform-root"+resourceid).find("span").addClass("checked");
			$("#root"+resourceid).attr('checked','checked').prop('checked',true);
		}else{
			$(this).removeAttr('checked').prop('checked',false);
			$("#thirdResource"+currentId+" span").removeClass("checked").find(':checkbox').removeAttr('checked').prop('checked',false);//取消选中三级菜单
			//检查一级菜单是否选中
			var allSpan = allChild.find("span");
			var selectSpan = allChild.find("span :checked");
			if(selectSpan.length!=0){//至少一个选中那么选中根节点
				$("#uniform-root"+resourceid).find("span").addClass("checked");
				$("#root"+resourceid).attr('checked','checked').prop('checked',true);
			}else{
				$("#uniform-root"+resourceid).find("span").removeClass("checked");
				$("#root"+resourceid).removeAttr('checked').prop('checked',false);
			}
		}
		
	}else if(id.indexOf('third')!=-1){//三级资源被点击
		var parentTable = $(this).parents('table').not('[id="sample_6"]').filter('[id*="third"]');//父级
		var allChild = parentTable.parents('table').not('[id="sample_6"]');
		var resourceid=allChild.attr("id").replace("secondResource","");
		if(isChecked){
			$(this).attr('checked','checked').prop('checked',true);
			//检查二级资源是否可以被选中
				parentTable.parent('td').prev('td').find("span").addClass("checked").find(':checkbox').attr('checked','checked').prop('checked',true);
			//检查一级资源是否可以被选中
				$("#uniform-root"+resourceid).find("span").addClass("checked");
				$("#root"+resourceid).attr('checked','checked').prop('checked',true);
		}else{
			$(this).removeAttr('checked').prop('checked',false);
			//检查二级资源是否选中
			if(parentTable.find("span :checked").length==0){
				//二级资源取消选中
				parentTable.parent('td').prev('td').find("span").removeClass("checked").find(':checkbox').removeAttr('checked').prop('checked',false);
			}
			
			//检查一级资源是否选中
			var allSpan = allChild.find("span");
			var selectSpan = allChild.find("span :checked");
			if(selectSpan.length==0){ 
				//一级资源取消选中
				$("#uniform-root"+resourceid).find("span").removeClass("checked");
				$("#root"+resourceid).removeAttr('checked').prop('checked',false);
			}
			
		}
	}
});
});

</script>
</body>
<!-- END BODY -->
</html>
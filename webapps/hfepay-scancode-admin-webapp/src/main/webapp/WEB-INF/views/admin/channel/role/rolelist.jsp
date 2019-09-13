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
<title>渠道角色管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<jsp:include page="../../include/commoncss.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${path }/resources/assets/global/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/assets/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/assets/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css"/>
<link rel="stylesheet" type="text/css" href="${path }/resources/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
<!-- BEGIN PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<!-- END PAGE LEVEL STYLES -->
<link rel="shortcut icon" href="${currentChannelInfo.icon }"/>
</head>
<body class="page-header-fixed page-quick-sidebar-over-content">
<%-- <jsp:include page="../../include/head.jsp"></jsp:include> --%>
<!-- BEGIN CONTAINER -->
<div class="page-container">
<%-- <jsp:include page="../../include/menu.jsp"></jsp:include> --%>
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
						<a href="#">渠道角色管理</a>
						<i class="fa fa-angle-right"></i>
					</li>
					<li>
						<a href="#">角色列表</a>
					</li>
				</ul>
			</div> --%>
			<!-- END PAGE HEADER-->
			
			<!-- BEGIN SEARCH CONDITION -->
			<div class="portlet box ">
				<div class="portlet-body form">
					<!-- BEGIN FORM-->
					<form action="#" class="form-horizontal" id="form">
						<div class="form-body">
							<div class="row">
								<div class="col-md-3">
									<div class="form-group">
										<label class="control-label col-md-4">角色名称</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="roleName" maxlength="20">
										</div>
									</div>
								</div>
								<!-- <div class="col-md-4">
									<div class="form-group">
										<label class="control-label col-md-3">渠道名称</label>
										<div class="col-md-8">
											<input type="text" class="form-control" id="channelName">
										</div>
									</div>
								</div> -->
							</div>
							<div class="portlet box green-haze"></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label class="control-label col-md-2"></label>
										<div class="col-md-10">
											<button type="button" class="btn green" id="query">查询</button>
											<button type="button" class="btn default" id="cancle">重置</button>
											<button type="button" class="btn btn-default" id="newrole">新增角色</button>
											
<!-- 											 <input type="file" name="imageFile" id="imageCid" class="fileInput" style="display: none;" onchange="submitForm('imageCid','imageCid1','imgimageCid')"/> -->
<!-- 						 					<button type="button" onclick="clickUpload()" class="btn default">上传</button> -->
<!-- 						 					<a href="../attachManage/downLoadFile?attachId=7996d942-0323-11e6-98fc-5254008bbc2a" class="btn default">下载</a> -->
											
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
			<!-- END SEARCH CONDITION -->
			
			<!-- BEGIN PAGE CONTENT-->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
					<div class="portlet box green-haze">
						<div class="portlet-title">
							<div class="caption">
								<i class="fa fa-globe"></i>角色列表
							</div>
						</div>
						<div class="portlet-body" id="tablec">
							
						</div>
						<!-- <div class="portlet-body" id="tablec1">
							
						</div> -->
					</div>
					<!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			<!-- END PAGE CONTENT-->
	</section>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<jsp:include page="../../include/footerjs.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->

<script src="${path }/resources/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="${path }/resources/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="${path }/resources/assets/admin/layout/scripts/quick-sidebar.js" type="text/javascript"></script>
<script src="${path }/resources/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script>
jQuery(document).ready(function(){

	$("#query").click(function(){
		var name=$("#roleName").val();
		/* var channelName=$("#channelName").val(); */
		$.ajax({
		   type: "POST",
		   url: "roleList/content",
		   data:{"roleName":name.trim()},
		   success: function(msg){
			   $("#tablec").html(msg);
		   }
		});
	});

	$("#cancle").click(function(){
		$("#roleName").val('');
	});
	
	var baseUrl="${path}";

	var flag = 0;

	$("#newrole").on("click",function(){
		var box = '<form class="form-horizontal"><div class="form-body row">'+
					'<div class="form-group col-md-7">'+
						'<label class="control-label col-md-4">角色描述</label>'+
						'<div class="col-md-8">'+
							'<input type="text" class="form-control" id="description" placeholder="角色描述">'+
						'</div>'+
					'</div>'+
					'<div class="form-group col-md-7">'+
						'<label class="control-label col-md-4">角色名称</label>'+
						'<div class="col-md-8">'+
							'<input type="text" class="form-control" id="roleName1" placeholder="角色名称">'+
						'</div>'+
					'</div>'+
					'<div class="form-group col-md-7">'+
						'<label class="control-label col-md-4">角色类型</label>'+
						'<div class="col-md-8">'+
							'<select  id="roleLevel" class="form-control" name="roleLevel">'+
								'<option value="" selected = "selected">----请选择----</option>'+
								'<option value = "1">渠道 </option>'+
								'<option value = "2">代理商</option>'+
								'<option value = "3">商户</option>'+
								'<option value = "4">收银员</option>'+
							'</select>'+
						'</div>'+
					'</div>'+
					'<div class="form-group col-md-7">'+
						'<label class="control-label col-md-4">渠道名称</label>'+
						'<div class="col-md-8">'+
							'<select  id="channelCode" class="form-control" name="channelCode">'+
								'<option value="" selected = "selected">----请选择----</option>'+
								'<c:forEach var="channel" items="${channelBaseList}">'+
									'<option value = "${channel.channelNo}">${channel.channelName} </option>'+
								'</c:forEach>'+
							'</select>'+
						'</div>'+
					'</div>'+
				   '</div></form>';
	    var btn = {success:{   
			       label: "确认新增",
			       className: "btn-success",
			       callback: function() {
			    	    var description = $('#description').val().trim();
			    	   	var roleName = $('#roleName1').val().trim();
			    	   	var roleLevel = $('#roleLevel').val();
			    	   	var channelCode = $('#channelCode').val();
			    	   	var flag = 0;
			    	   	var errMsg = '';
			    	   	if(description == '' || roleName == '') {
			    	   		errMsg = '角色名称与描述不可为空！<br>';
			    	   		flag++;
			    	   	}
			    	   	if(roleLevel == ''){
			    	   		errMsg = errMsg + '角色类型必须选择！<br>';
			    	   		flag++;
			    	   	}
			    	   	if(channelCode == '') {
			    	   		errMsg = errMsg + '渠道名称必须选择！<br>';
			    	   		flag++;
			    	   	}
			    	   	if(description.length > 20) {
			    	   		flag++;
			    	   		errMsg+="角色描述长度不能超过20！<br>";
			    	   	}
			    	   	if(roleName.length > 20) {
			    	   		flag++;
			    	   		errMsg+="角色名称长度不能超过20！<br>";
			    	   	}
			    	   	tel =  /[@#\$%\^&\*]+/g;
			    	   	if(tel.test(roleName)){
			    			flag++;
			    			errMsg+='角色名称不能包含特殊字符<br>';
			    		}
			    		if(tel.test(description)){
			    			flag++;
			    			errMsg+='角色描述不能包含特殊字符<br>';
			    		}
			    	   	if(flag > 0){
			    	   		bootbox.alert(errMsg,function(){});
			    	   	}
			    	   	if(flag == 0){
			    	   		$.ajax({
								   type: "POST",
								   url: baseUrl+"/adminManage/channel/role/roleList/save",
								   data:{"description":description,"roleName":roleName,"roleLevel":roleLevel,"channelCode":channelCode},
								   success: function(msg){
									   	msg = $.parseJSON(msg);
							 			bootbox.alert(msg.values,function(){
							 				location.href=baseUrl+msg.url;
						 			  	});
								   }
							});
			    	   	}
			       }
			     },
			     "取消": {
			        className: "btn-danger",
			        callback: function() {
			        }
					  }
			 };
	    bootbox.dialog({
		   message: box,
		   title: "新增角色",
		   onEscape: function() {},
		   className: "green-haze",
		   buttons: btn
		 });
	});

	
	
	toPage(1);
});

function clickUpload(){
	$("#imageCid").click();
}

function del(obj){
	bootbox.setLocale("zh_CN");  //设置按钮为中文
    bootbox.confirm("确定执行该操作吗?", function(result) {
  	  if(result){
  		$.ajax({
 		   type: "POST",
 		   url: "roleList/delRole",
 		   data:{"roleId":obj},
 		   dataType:"json",
 		   success: function(msg){
 			  bootbox.alert(msg.message,function(){
 				  window.location.reload();
 			  });  //弹出框
 		   }
 		});
  		  
  	  }/* else{
  		  bootbox.alert("取消");
  	  } */
       //alert("Confirm result: "+result);
    }); 
}

function toPage(pageNumber){
	var datajson = $('#form').serialize();
	$.ajax({
		   type: "POST",
		   url: "roleList/content?pageNo="+pageNumber,
		   data:datajson,
		   success: function(msg){
				   $("#tablec").html(msg);
		   }
		});
}

</script>
<script src="${path}/resources/scripts/base.js" type="text/javascript"></script>
</body>
<!-- END BODY -->
</html>
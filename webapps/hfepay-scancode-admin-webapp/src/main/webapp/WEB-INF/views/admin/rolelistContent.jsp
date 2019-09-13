<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="page" %> 
<%
   String path =  request.getContextPath();
request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered table-hover" id="sample_6">
							<thead>
							<tr>
								<th>
									 序号
								</th>
								<th>
									 角色描述
								</th>
								<th>
									 角色名称
								</th>
								<!-- <th>
									 渠道名称
								</th> -->
								<th>
									 创建时间
								</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${roleList.result}" varStatus="status"> 
						   <tr>
							 <td>
								${status.index+1 }
							</td>
							<td>
								${item.description }
							</td>
							<td>
								${item.roleName }
							</td>
							<%-- <td title="${item.channelCode}">${item.channelName}</td> --%>
							<td class="date_time_parse">${item.createTime}</td>
							<td>
								<a href="${path }/adminManage/role/roleList/roleEdit?id=${item.id}"><span class="label label-primary">修改权限</span></a>
								<a class="editRole" name="${item.id}"><span class="label label-success">修改角色</span></a>
<%-- 								<a onclick="javascript:del('${item.id}');"><span class="label label-danger">删除</span></a> --%>
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${roleList.result.size() == 0}">
								<tr>
									<td class="page-nodata" colspan="6">查无数据！</td>
								</tr>
					</c:if>
			</tbody>
			</table>
			<script>
				dataOpr();
				var baseUrl="${path}";
				var flag = 0;
				$(".editRole").on("click",function(){
					var updBtn = $(this);
					var description = updBtn.parent().parent().find("td:eq(1)");
					var roleName = updBtn.parent().parent().find("td:eq(2)");
					var id = updBtn.attr("name");

					var box = '<form class="form-horizontal"><div class="form-body row">'+
								'<div class="form-group col-md-7">'+
									'<label class="control-label col-md-4">角色描述</label>'+
									'<div class="col-md-8">'+
										'<input type="text" class="form-control" id="description" value="'+description.text().trim()+'" placeholder="角色描述">'+
									'</div>'+
								'</div>'+
								'<div class="form-group col-md-7">'+
									'<label class="control-label col-md-4">角色名称</label>'+
									'<div class="col-md-8">'+
										'<input type="text" class="form-control" id="roleName1"  value="'+roleName.text().trim()+'" placeholder="角色名称">'+
									'</div>'+
								'</div>'+
							   '</div></form>';
				    var btn = {
				    		success:{   
						       label: "确认修改",
						       className: "btn-success",
						       callback: function() {
						    	    var flag = 0;
						    	   	var errMsg = '';
						    	    var description = $('#description').val().trim();
						    	   	var roleName = $('#roleName1').val().trim();
						    	   	if(description == '' || roleName == '') {
						    	   		flag++;
						    	   		errMsg = "角色名称与描述不可为空！<br>";
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
						    	   	}else{
							    	    if(id != null && id != ''){
							    	    	$.ajax({
												   type: "POST",
												   url: baseUrl+"/adminManage/role/roleList/save",
												   data:{"id":id,"description":description,"roleName":roleName},
												   success: function(msg){
													   	msg = $.parseJSON(msg);
											 			bootbox.alert(msg.values,function(){
											 				location.href=baseUrl+msg.url;
										 			  	});
												   }
											});
						            	}
						    	   	}
						       }
						     },
						     "取消": {
						        className: "btn-danger",
						        callback: function() {
						        }
						     }
						 }
				    bootbox.dialog({
					   message: box,
					   title: "编辑角色",
					   onEscape: function() {},
					   className: "green-haze",
					   buttons: btn
					 });
				});
			</script>
			<page:page curPage="${roleList.pageNo }" totalPages="${roleList.totalPages }"/>		
			<b class="totalCountInPage">共&nbsp;&nbsp;${roleList.totalCount}&nbsp;&nbsp;条</b>		
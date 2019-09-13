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
								<th>广告编号</th>
								<!-- <th>机构编号</th> -->
								<th>机构名称</th>
								<th>推广范围</th>
								<th>广告位</th>
								<!-- <th>广告图片</th>
								<th>广告链接地址</th> -->
								<th>生效日期</th>
								<th>结束日期</th>
								<th>优先级别</th>
								<th>状态</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.adviertNo}</td>
									<%-- <td>${item.organNo}</td> --%>
									<td>${item.organName}</td>
									<td>${item.adviertScope}</td>
									<td class="parent_id_parse"  title="adviertPosition">${item.adviertPosition}</td>
									<%-- <td><img style="max-width: 150px; max-height: 150px;" src="${item.imgUrl}"></td>
									<td style="word-break:break-all;width:"8%";>${item.adviertLink}</td> --%>
									<td class="date_time_parse">${item.startDate}</td>
									<td class="date_time_parse">${item.endDate}</td>
									<td class="parent_id_parse"  title="priority">${item.priority}</td>
									<td class="parent_id_parse"  title="status">${item.status}</td>

							<td>
								<a onclick="javascript:todetail('${item.id}');"><span class="label label-info">审核</span></a>
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
						<tr>
							<td class="page-nodata" colspan="88">查无数据！</td>
						</tr>
					</c:if>
			</tbody>
			</table>
			<script type="text/javascript">
				dataOpr();
				//显示详情
				function todetail(id){
					$.ajax({
						   type: "POST",
						   url: "adviertisementaudit/detail/"+id,
						   success: function(msg){
							   var btn = {success:{   
							       label: "审核通过",
							       className: "btn-success",
							       callback: function() {
							    	   audit(id,"2");
							       }
							     },
							     "审核拒绝": {
								        className: "btn-danger",
								        callback: function() {
								        	var remark = $("#auditRemark").val();
								        	if(remark.trim() != ""){
								        		audit(id,"3");
								        	}else{
								        		bootbox.alert("审核备注不能为空",function(){});
								        	}
								        	
								        }
					  			 },
							     "关闭": {
								        className: "btn-default",
								        callback: function() {
								        }
					  			  }
								 };
							   bootbox.dialog({
								   message: msg+'<div class="row"><div class="form-group"><div class="col-md-12"><label class="control-label col-md-2 td0" style="margin-top: 5px;">审核备注</label><div class="col-md-10"><input type="text" class="form-control" id="auditRemark"  name = "auditRemark" placeholder="审核备注"></div></div></div></div>',
								   title: "详情列表",
								   onEscape: function() {},
								   className: "dialog-class",
								   buttons: btn
								 });
						   }
					});
				}
				
				//审核
				function audit(id,status){
					var remark = $("#auditRemark").val();
						$.ajax({
					 		   type: "POST",
					 		   url: "adviertisementaudit/audit/platform",
					 		   data:{"id":id,"status":status,"reason":remark},
					 		   success: function(msg){
					 			  msg = $.parseJSON(msg);
					 			  bootbox.alert(msg.values,function(){
					 				 if(msg.executeStatus == 0){
					 					window.location.reload();
					 				 }
					 			  });
					 		   }
						 	});						
				}
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"  totalCounts="${pager.totalCount }"/>		
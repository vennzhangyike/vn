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
								<th>渠道编号</th>
								<th>渠道名称</th>
								<th>总二维码数量</th>
								<th>已使用二维码数量</th>
								<th>剩余二维码数量</th>
								<th>状态</th>
								<th>创建时间</th>
								<th>
									 操作
								</th>
							</tr>
							</thead>
							<tbody id="tableContent">
							
						  <c:forEach var="item" items="${pager.result}"> 
						   <tr>
									<td>${item.channelNo}</td>
									<td>${item.channelName}</td>
									<td>${item.qrTotal}</td>
									<td>${item.useTotal}</td>
									<td>${item.lessTotal}</td>									
									<td>
									<c:if test="${item.status == 1}">启用</c:if>
									<c:if test="${item.status == 2}">禁用</c:if>
									</td>
									<td class="date_time_parse">${item.createTime}</td>
							<td>
								<a onclick="javascript:todetail('${item.channelNo}');"><span class="label label-default">查看详情</span></a>
								<a href="${path}/adminManage/channelexpand/${item.channelNo}"><span class="label label-info">修改</span></a>
								<a class="addQrcode" name="${item.id}"><span class="label label-primary">分配二维码</span></a>	
								<%-- <a onclick="javascript:del('${item.id}');"><span class="label label-danger">删除</span></a>						
								<a href="${path}/adminManage/channelbase/channeladmin/${item.channelNo}"><span class="label label-info">设置用户</span></a> --%>		
					   			<c:if test="${item.status == 1}"><a onclick="javascript:updateStatus(this);" status="2" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>
								<c:if test="${item.status == 2}"><a onclick="javascript:updateStatus(this);" status="1" value="${item.id}"><span class="label label-success">启用</span></a></c:if>
								<a class="addLiquidationFee" name="${item.id}"><span class="label label-primary">清算手续费</span></a>	
							</td> 
						 </tr>
					</c:forEach>
					<c:if test="${pager.result.size() == 0}">
								<tr>
									<td class="page-nodata" colspan="8">查无数据！</td>
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
								   url: "channelexpand/detail/"+id,
								   success: function(msg){
									   var btn = {success:{   
									       label: "关闭",
									       className: "btn-success",
									       callback: function() {
									       }
									     }};
									   bootbox.dialog({
										   message: msg,
										   title: "详情列表",
										   onEscape: function() {},
										   className: "dialog-class",
										   buttons: btn
										 });
								   }
							});
						}
						//分配二维码
						$(".addQrcode").on("click",function(){
							var updBtn = $(this);
							var flag = 0;
							var channelName = updBtn.parent().parent().find("td:eq(1)");
							var channelNo = updBtn.parent().parent().find("td:eq(0)");
							

							var box = '<form class="form-horizontal"><div class="form-body row">'+
										'<div class="form-group col-md-7">'+
											'<label class="control-label col-md-4">渠道名称</label>'+
											'<div class="col-md-8">'+
												'<input type="text" class="form-control" id="description" value="'+channelName.text().trim()+'" placeholder="渠道名称" readonly="readonly" >'+
											'</div>'+
										'</div>'+
										'<div class="form-group col-md-7">'+
											'<label class="control-label col-md-4">二维码数量</label>'+
											'<div class="col-md-8">'+
												'<input type="text" class="form-control" id="qrcodeNum" name="qrcodeNum" placeholder="二维码数量">'+
											'</div>'+
										'</div>'+
									   '</div></form>';
						    var btn = {
						    		success:{   
								       label: "分配",
								       className: "btn-success",
								       callback: function() {
								    	    var qrcodeNum = $('#qrcodeNum').val();
								    	   	if(qrcodeNum == '') {
								    	   		flag++;
								    	   		bootbox.alert("二维码数量不可为空！",function(){});
								    	   	}else{
								    	   		var number = /^[0-9]*$/; 
								    	   		if(!number.test(qrcodeNum)){
								    	   			flag++;
								    	   			bootbox.alert("只能输入正数！",function(){});
								    	   		}
								    	   		if(qrcodeNum>10000){
								    	   			flag++;
								    	   			bootbox.alert("一次最大只能添加10000个！",function(){});
								    	   		}
								    	   	}
								    	   	
								    	   	if(flag == 0){
									    	    if(channelNo != null && channelNo != ''){
									    	    	$.ajax({
														   type: "POST",
														   url: "channelbase/addQrcode",
														   data:{"channelNo":channelNo.text().trim(),"qrcodeNum":qrcodeNum},
														   success: function(msg){
															   	msg = $.parseJSON(msg);
													 			bootbox.alert(msg.message,function(){
													 				toPage(1);
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
							   title: "分配二维码",
							   onEscape: function() {},
							   className: "green-haze",
							   buttons: btn
							 });
						});
						
						//清算手续费
						$(".addLiquidationFee").on("click",function(){
							var liquidationFee="";
							var updBtn = $(this);
							
							var channelName = updBtn.parent().parent().find("td:eq(1)");
							var channelNo = updBtn.parent().parent().find("td:eq(0)");
							
							$.ajax({
				 		 		   type: "POST",
				 		 		   url: "merchantpayway/getLiquidationFee",
				 		 		   data: "channelNo=" + channelNo.text().trim(),
				 		 		   async:false,
				 		 		   success: function(msg){
				 		 			  msg = $.parseJSON(msg);
				 		 			  if(msg.status== '1'){		
				 		 				if(!!msg.liquidationFee){
				 		 					liquidationFee=msg.liquidationFee; 
				 		 				} 
				 		 			  }
				 		 		   }
				 		 		});							

							var box = '<form class="form-horizontal"><div class="form-body row">'+
										'<div class="form-group col-md-7">'+
											'<label class="control-label col-md-6">结算手续费(元)</label>'+
											'<div class="col-md-6">'+
												'<input type="text" class="form-control" id="liquidationFee" name="liquidationFee" value="'+liquidationFee+'" placeholder="结算手续费(元)" >'+
											'</div>'+
										'</div>'+
									   '</div></form>';
						    var btn = {
						    		success:{   
								       label: "保存",
								       className: "btn-success",
								       callback: function() {
								    	    var flag = 0;
								    	    var errMsg = '';
								    	    var liquidationFee = $('#liquidationFee').val();
								    	   	if(liquidationFee == '') {
								    	   		flag++;
								    	   		errMsg += "结算手续费不可为空！<br>";
								    	   	}else{
								    	   		var number = /^[0-9]+(\.[0-9]+)?$/; 
								    	   		if(!number.test(liquidationFee)){
								    	   			flag++;
								    	   			errMsg += "结算手续费只能输入数字！<br>";
								    	   		}
								    	   		if(liquidationFee.length>10){
								    	   			flag++;
								    	   			errMsg += "结算手续费长度不能超过10！<br>";
								    	   		}
								    	   	}
								    	   	if(flag == 0){								    	   		
									    	    if(channelNo != null && channelNo != ''){
									    	    	$.ajax({
														   type: "POST",
														   url: "merchantpayway/addLiquidationFee",
														   data:{"channelNo":channelNo.text().trim(),"liquidationFee":liquidationFee},
														   success: function(msg){
															   	msg = $.parseJSON(msg);
													 			bootbox.alert("设置成功,次日生效。",function(){
													 				toPage(1);
												 			  	});
														   }
													});
								            	} 
								    	   	}else{
								    	   		bootbox.alert(errMsg,function(){});
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
							   title: "设置结算手续费",
							   onEscape: function() {},
							   className: "green-haze",
							   buttons: btn
							 });
						});
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}"/><b class="totalCountInPage">共&nbsp;&nbsp;${pager.totalCount}&nbsp;&nbsp;条</b>
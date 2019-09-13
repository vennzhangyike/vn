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
								<%-- <a href="${path}/adminManage/channelexpand/${item.channelNo}"><span class="label label-info">修改</span></a> --%>
								<%-- <a class="addQrcode" name="${item.id}"><span class="label label-primary">分配二维码</span></a>	 --%>
								<%-- <a onclick="javascript:del('${item.id}');"><span class="label label-danger">删除</span></a>						
								<a href="${path}/adminManage/channelbase/channeladmin/${item.channelNo}"><span class="label label-info">设置用户</span></a> --%>		
					   			<%-- <c:if test="${item.status == 1}"><a onclick="javascript:updateStatus(this);" status="2" value="${item.id}"><span class="label label-danger">禁用</span></a></c:if>
								<c:if test="${item.status == 2}"><a onclick="javascript:updateStatus(this);" status="1" value="${item.id}"><span class="label label-success">启用</span></a></c:if> --%>
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
						$(function(){
							var text = $(".date_time_parse");
							for(var i = 0; i < text.size();i++){
								var parse = dateTimeParse($(text[i]).text());
								$(text[i]).attr("title",$(text[i]).text());
								$(text[i]).attr("class","date_time_parsed");
								$(text[i]).html(parse);
							}
							
							text = $(".parent_id_parse");
							for(var i = 0; i < text.size();i++){
								var parse = $(text[i]).attr("title");
								var val = $(text[i]).text().trim();
								var ops=$("#"+parse).find("option");
								for(var k = 0; k < ops.size();k++){
									var opval = $(ops[k]).val();
									$(text[i]).attr("title",val);
									if(opval == val){
										$(text[i]).html($(ops[k]).text());
									}
								}
							}
							
							$.validator.addMethod("isNumber", function(value, element) { 
								   var length = value.length; 
								   var number = /^[0-9]*$/; 
								   return this.optional(element) || (number.test(value)); 
								 }, "只能输入数字");
						});
						
						//日期处理（结合时区函数）
						function dateTimeParse(fmt) {
							if(fmt.length == 17 || fmt.trim() == ''){
								return "";
							}
						    var now = timeOffset(fmt);
						    var year = now.getFullYear(); //getFullYear getYear
						    var month = now.getMonth();
						    var date = now.getDate();
						    var day = now.getDay();
						    var hour = now.getHours();
						    var minu = now.getMinutes();
						    var sec = now.getSeconds();
						    var week;
						    month = month + 1;
						    if (month < 10) month = "0" + month;
						    if (date < 10) date = "0" + date;
						    if (hour < 10) hour = "0" + hour;
						    if (minu < 10) minu = "0" + minu;
						    if (sec < 10) sec = "0" + sec;
						    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
						    week = arr_week[day];
						    var time = "";
						    time = year + "-" + month + "-" + date + "" + " " + hour + ":" + minu + ":" + sec + " " + week;
						    return time;
						}
						
						//时区处理
						function timeOffset(fmt) {
						    var now = new Date(fmt);
						    //先将时间格式化到格林威治时区，正常来说是在中国区（GMT）打开界面
						    //getTimezoneOffset是-480（分钟为单位），负数是中国在东边，比格林威治早，中国更早见到太阳
						    var timeval = now.getTime()+now.getTimezoneOffset()*60*1000;
						    if(fmt.indexOf("CST")>0){
						    	//服务器时区是CST，比格林威治晚了6个小时，所以获取格式化之后的毫秒数减去6个小时的毫秒数，即为正确时间
						    	timeval -= 6*60*60*1000;
						    }
					    	now = new Date(timeval);
						    return now;
						}
						
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
			</script>
			<page:page curPage="${pager.pageNo}" totalPages="${pager.totalPages}" totalCounts="${pager.totalCount }"/>
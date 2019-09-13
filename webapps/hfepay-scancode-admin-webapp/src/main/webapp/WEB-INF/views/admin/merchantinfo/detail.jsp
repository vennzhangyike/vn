<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">渠道编号</td><td>${item.channelNo}</td>
		<td class="col-md-2 td0">渠道名称</td><td class="col-md-4 parent_id_parse" title="channelName">${item.channelName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">代理商编号</td><td>${item.agentNo}</td>
		<td class="col-md-2 td0">代理商名称</td><td class="col-md-4 parent_id_parse" title="agentName">${item.agentName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户编号</td><td>${item.merchantNo}</td>
		<td class="col-md-2 td0">商户名称</td><td class="col-md-4 parent_id_parse" title="merchantName">${item.merchantName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户简称</td><td class="col-md-4 parent_id_parse" title="shortName">${item.shortName}</td>
		<td class="col-md-2 td0">转接平台商户编号</td><td class="col-md-4 parent_id_parse" title="platformMerchantNo">${item.platformMerchantNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">经营类型</td><td class="col-md-4 parent_id_parse"  colspan="3" title="busType">
		<input type="hidden" id="categoryNo"  name = "categoryNo">
		<c:if test="${pageType==1}">
		<label id="busTypeLabel"  name = "busTypeLabel">${item.busType}</label>
		</c:if>
		<c:if test="${pageType==2}">
		<table>
			<tr>
				<td class="col-md-2 td0">
					<select  id="categoryOne" class="form-control" name="categoryOne" style="width:200px;">
						<option value="" selected = "selected">----请选择----</option>
							<c:forEach var="category" items="${categoryOne}">
								<option value = "${category.id}" categoryNo = "${category.categoryNo}">${category.name} </option>
							</c:forEach>
					</select>
				</td>
				<td class="col-md-2 td0">
					<select  id="categoryTwo" class="form-control" name="categoryTwo" style="display:none;width:200px;" ></select>
				</td>
				
			</tr>
			<tr>
				<td class="col-md-2 td0">
					<select  id="categoryThree" class="form-control" name="categoryThree" style="display:none;width:200px;"></select>
				</td>
				<td class="col-md-2 td0">
					
				</td>
			</tr>
		</table>
		<label style="color:red;display:none;" id="categoryError">经营类目数据异常！</label>
		</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">法人姓名</td><td class="col-md-4 parent_id_parse" title="name">${item.name}</td>
		<td class="col-md-2 td0">法人身份证号码</td><td class="col-md-4 parent_id_parse" title="idCard">${item.idCard}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">身份证正面
		<c:if test="${not empty item.idcardImg1}">
			<br><br><a style="cursor:pointer;" class="refreshImg" attr="/${item.merchantNo}/idcardImg1">刷新图片</a>
		</c:if>
		</td>
		<td class="col-md-4 parent_id_parse" title="idcardImg1">
		<c:if test="${not empty item.idcardImg1}">
		<div class="small_pic">
			<a href="#pic_idcardImg1">
				<img style="max-width: 150px; max-height: 150px;" src="${item.idcardImg1}">
				<div id="pic_idcardImg1" style="display:none;"><img style="max-width: 800px; max-height: 600px;" src="${item.idcardImg1}"></div>
			</a>
		</div>
		
		</c:if>
		</td>
		<td class="col-md-2 td0">身份证反面
		<c:if test="${not empty item.idcardImg2}">	
			<br><br><a style="cursor:pointer;" class="refreshImg" attr="/${item.merchantNo}/idcardImg2">刷新图片</a>
		</c:if>
		</td>
		<td class="col-md-4 parent_id_parse" title="idcardImg2">
		<c:if test="${not empty item.idcardImg2}">		
		<div class="small_pic">
			<a href="#pic_idcardImg2">
				<img style="max-width: 150px; max-height: 150px;" src="${item.idcardImg2}">
				<div id="pic_idcardImg2" style="display:none;"><img style="max-width: 800px; max-height: 600px;" src="${item.idcardImg2}"></div>
			</a>
		</div>
		</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">手持身份证
		<c:if test="${not empty item.idcardImg3}">	
			<br><br><a style="cursor:pointer;" class="refreshImg" attr="/${item.merchantNo}/idcardImg3">刷新图片</a>
		</c:if>
		</td>
		<td class="col-md-4 parent_id_parse" title="idcardImg3">
		<c:if test="${not empty item.idcardImg3}">		
		<div class="small_pic">
			<a href="#pic_idcardImg3">
				<img style="max-width: 150px; max-height: 150px;" src="${item.idcardImg3}">
				<div id="pic_idcardImg3" style="display:none;"><img style="max-width: 800px; max-height: 600px;" src="${item.idcardImg3}"></div>
			</a>
		</div>
		</c:if>
		</td>
		<td class="col-md-2 td0">营业执照图片
		<c:if test="${not empty item.merchantLicenseImg}">	
		<br><br><a style="cursor:pointer;" class="refreshImg" attr="/${item.merchantNo}/merchantLicenseImg">刷新图片</a>
		</c:if>
		</td>
		<td class="col-md-4 parent_id_parse" title="merchantLicenseImg">
		<c:if test="${not empty item.merchantLicenseImg}">		
		<div class="small_pic">
			<a href="#pic_merchantLicenseImg">
				<img style="max-width: 150px; max-height: 150px;" src="${item.merchantLicenseImg}">
				<div id="pic_merchantLicenseImg" style="display:none;"><img style="max-width: 800px; max-height: 600px;" src="${item.merchantLicenseImg}"></div>
			</a>
		</div>
		</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">联系电话</td><td class="col-md-4 parent_id_parse" title="phone">${item.phone}</td>
		<td class="col-md-2 td0">邮箱</td><td class="col-md-4 parent_id_parse" title="email">${item.email}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">地址</td><td colspan="3">
			<c:if test="${pageType==1}">
			<label id="addressLabel"  name = "addressLabel">${item.address}</label>
			</c:if>
			<c:if test="${pageType==2}">
			<input type="text" class="form-control" id="address"  name = "address" value="${item.address}" placeholder="地址">
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商户营业执照号</td><td class="col-md-4 parent_id_parse" title="merchantLicense">${item.merchantLicense}</td>
		<td class="col-md-2 td0">手机号码</td><td class="col-md-4 parent_id_parse" title="mobile">${item.mobile}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">税务登记证号码</td><td class="col-md-4 parent_id_parse" title="taxNo">${item.taxNo}</td>
		<td class="col-md-2 td0">组织机构代码</td><td class="col-md-4 parent_id_parse" title="orgNo">${item.orgNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">税务登记证图片</td><td class="col-md-4 parent_id_parse" title="taxImg">
		<c:if test="${not empty item.taxImg}">
		<div class="small_pic">
			<a href="#pic_taxImg">
				<img style="max-width: 150px; max-height: 150px;" src="${item.taxImg}">
				<div id="pic_taxImg" style="display:none;"><img style="max-width: 800px; max-height: 600px;" src="${item.taxImg}"></div>
			</a>
		</div>
		</c:if>
		</td>
		<td class="col-md-2 td0">二维码编号</td><td class="col-md-4 parent_id_parse" title="qrCode">${item.qrCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">实名认证</td><td class="col-md-4 parent_id_parse" title="authenStatus">${item.authenStatus}</td>
		<td class="col-md-2 td0">商户状态</td><td class="col-md-4 parent_id_parse" title="status">${item.status}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">入网时间</td><td class="col-md-4 date_time_parse" title="createTime">${item.createTime}</td>
		<td class="col-md-2 td0">修改时间</td><td class="col-md-4 date_time_parse" title="updateTime">${item.updateTime}</td>
	</tr>
	<c:if test="${not empty item.qrCode }">
		<tr>
			<td class="col-md-2 td0">门店编号</td><td id="storeNoValue">${store.storeNo}</td>
			<td class="col-md-2 td0">门店名称</td><td>${store.storeName}</td>
		</tr>
	</c:if>
	<tr>
		<td class="col-md-2 td0">操作人账号</td><td <c:if test="${pageType==1}">colspan="3"</c:if> class="col-md-4 parent_id_parse" title="operator">${item.operatorName}</td>
		<c:if test="${pageType==2}">
		<td class="col-md-2 td0">是否开通提现</td><td class="col-md-4 parent_id_parse" >
		<select id = "isRealAccount" class="form-control" name="isRealAccount">
			<option value = "Y">开通</option>
			<option value = "N">不开通</option>
		</select>
		</td>
		</c:if>
	</tr>
	<c:if test="${pageType==1}">
	<tr>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" colspan="3">${item.remark}</td>		
	</tr>
	</c:if>
	
</table>
<script type="text/javascript">
dataOpr();
$('div.small_pic a').fancyZoom({scaleImg: true, closeOnClick: true});
function setCategory(obj,level){
	var baseUrl = $("#baseUrl").text().trim();
	var categoryNo = $(obj).find("option:selected").attr("categoryNo");	
	if(categoryNo == "null" || categoryNo == ''){
		var id = $(obj).val();
		$.ajax({
			url:baseUrl+"/adminManage/hfepaycategory/list",
			data:{"parentId":id,"level":level},
			type:"POST",
			success:function(data)
			{
				var json = JSON.parse(data); 
				var objList = json.objList;
				var item = "<option  value='' selected = 'selected'>----请选择----</option>"
				for(var i in objList){ 
					var option = "<option value='" + objList[i].id + "' categoryNo = '" + objList[i].categoryNo + "'>" + objList[i].name + "</option>"
					if(level == 2){
						if(i==0){
							$("#categoryTwo").css("display","block");
							$("#categoryTwo").empty();
							$("#categoryTwo").append(item); 
						}
						$("#categoryTwo").append(option); 
					}else if(level == 3){
						if(i==0){
							$("#categoryThree").css("display","block");
							$("#categoryThree").empty();
							$("#categoryThree").append(item); 
						}
						$("#categoryThree").append(option); 
					}
					
				}
				if(objList.length == 0){
					$('#categoryError').css("display","block");
					if(level == 2){
						$("#categoryTwo").empty();
					}else if(level == 3){
						$("#categoryThree").empty();
					}
				}else{
					$('#categoryError').css("display","none");
				}
			}
		});
	}else{
		$("#categoryNo").val(categoryNo);
		if(level == 2){
			$("#categoryTwo").css("display","none");
			$("#categoryThree").css("display","none");
		}else if(level == 3){
			$("#categoryThree").css("display","none");
		}
	}
}
$("#categoryOne").change(function(){
	setCategory(this,2);
});
$("#categoryTwo").change(function(){
	setCategory(this,3);
});
$("#categoryThree").change(function(){
	var categoryNo = $(this).find("option:selected").attr("categoryNo");	
	if(categoryNo == null || categoryNo == ''){
		$('#categoryError').css("display","block");
	}else{
		$("#categoryNo").val(categoryNo);
	}
});

$(".refreshImg").click(function(){
	var tail = $(this).attr("attr");//图片的刷新路径
	var baseUrl = $("#baseUrl").text().trim();
	var title = tail.split("/")[2]
	var imgs = $('td[title="'+title+'"]').find('img');
	$.ajax({
		url:baseUrl+"/adminManage/merchantinfo/freshImg/"+tail,
		type:"POST",
		dataType:'json'
		})
		.done(function(msg){
			if(msg.executeStatus=='0'){
				var img =msg.img;
				$.each(imgs,function(i,item){
					$(item).attr('src',img)
				});
			}
		}).error(function(msg){
			console.log(msg);
		});
});

</script>

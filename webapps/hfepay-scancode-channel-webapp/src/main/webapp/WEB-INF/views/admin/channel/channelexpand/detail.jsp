<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path =  request.getContextPath();
	request.setAttribute("path", path);
%>
<table class="table table-striped table-bordered" id="sample_6">

	<tr>
		<td class="col-md-2 td0">渠道名称</td><td class="col-md-4 parent_id_parse" >${item.channelName}</td>
		<td class="col-md-2 td0">渠道编号</td><td class="col-md-4 parent_id_parse" >${item.channelNo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">二级域名编号</td><td colspan="3">${item.channelCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">昵称</td><td class="col-md-4 parent_id_parse" >${item.nickName}</td>
		<td class="col-md-2 td0">编码抬头</td><td class="col-md-4 parent_id_parse" >${item.channelPreCode}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">公众号ID</td><td class="col-md-4 parent_id_parse" >${channelExpandCondition.appid}</td>
		<td class="col-md-2 td0">公众号key</td><td class="col-md-4 parent_id_parse" >${channelExpandCondition.secret}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">注册消息模板ID</td><td class="col-md-4 parent_id_parse" >${channelExpandCondition.registerTemplateId}</td>
		<td class="col-md-2 td0">支付消息模板ID</td><td class="col-md-4 parent_id_parse" >${channelExpandCondition.payTemplateId}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">公司名称</td><td class="col-md-4 parent_id_parse" >${item.companyName}</td>
		<td class="col-md-2 td0">地址</td><td class="col-md-4 parent_id_parse" >${item.address}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">备案号</td><td class="col-md-4 parent_id_parse" >${item.recordNumber}</td>
		<td class="col-md-2 td0">渠道图标</td><td class="col-md-4 parent_id_parse"><img id="imgimageId" src="${item.icon}" style="max-width: 200px;"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">首页上部logo</td><td class="col-md-4 parent_id_parse"><img id="imgimageId" src="${item.indexTopImg}" style="max-width: 200px;"/></td>
		<td class="col-md-2 td0">首页底部logo</td><td class="col-md-4 parent_id_parse"><img id="imgimageId" src="${item.indexBottomImg}" style="max-width: 200px;"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">个人中心LOGO</td><td class="col-md-4 parent_id_parse"><img id="imgimageId" src="${item.centerHeadImg}" style="max-width: 200px;"/></td>
		<td class="col-md-2 td0">官方微信二维码</td><td class="col-md-4 parent_id_parse"><img id="imgimageId" src="${item.indexWxImg}" style="max-width: 200px;"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">电话</td><td class="col-md-4 parent_id_parse" >${item.phone}</td>
		<td class="col-md-2 td0">技术支持邮箱</td><td class="col-md-4 parent_id_parse">${item.technicalSupportEmail}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">QQ群</td><td class="col-md-4 parent_id_parse" >${item.qqGroup}</td>
		<td class="col-md-2 td0">域名</td><td class="col-md-4 parent_id_parse" >${item.domainName}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">客服QQ</td><td class="col-md-4 parent_id_parse" >${item.customServiceQq}</td>
		<td class="col-md-2 td0">商务QQ</td><td class="col-md-4 parent_id_parse" >${item.businessCooperationQq}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">商务邮箱</td><td class="col-md-4 parent_id_parse">${item.businessCooperationEmail}</td>
		<td class="col-md-2 td0">官方微博</td><td class="col-md-4 parent_id_parse">${item.microblogUrl}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">联系地址图</td><td class="col-md-4 parent_id_parse"><img id="imgimageId" src="${item.contactAddressImg}" style="max-width: 200px;"/></td>
		<td class="col-md-2 td0">登陆背景图</td><td class="col-md-4 parent_id_parse"><img id="imgimageId" src="${item.loginBackgroundImg}" style="max-width: 200px;"/></td>
	</tr>
	<tr>
		<td class="col-md-2 td0">关于我们</td><td colspan="3">${item.aboutUs}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">帮助中心</td><td colspan="3">${item.helpInfo}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">状态</td><td class="col-md-4 parent_id_parse" colspan="3">
		<c:if test="${item.status=='1'}">
			启用
		</c:if>
		<c:if test="${item.status=='2'}">
			禁用
		</c:if>
		</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">创建时间</td><td class="col-md-4 date_time_parse">${item.createTime}</td>
		<td class="col-md-2 td0">修改时间</td><td class="col-md-4 date_time_parse">${item.updateTime}</td>
	</tr>
	<tr>
		<td class="col-md-2 td0">操作人账号</td><td class="col-md-4" >${item.operator}</td>
		<td class="col-md-2 td0">备注</td><td class="col-md-4 parent_id_parse" >${item.remark}</td>
	</tr>
</table>
<script type="text/javascript">
dataOpr();
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String path =  request.getContextPath();
   request.setAttribute("path", path);
%>
<html lang="zh-cmn-Hans">
<head>
<title>申请入驻</title>
<meta charset="UTF-8">
<meta http-equive="Cache-Control" content="no-cache"/>
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${path}/resources/styles/wechat/weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/jquery-weui.min.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/style.css">
<link rel="stylesheet" href="${path}/resources/styles/wechat/red.css">
</head>
<body ontouchstart class="applyBg">

<div class="apply">
	<h1>商户申请表</h1>
	
	<h2 class="zc_title">3、申请人结算信息</h2>
	<%-- <dl>
		<dt>法人姓名*</dt>
		<dd><span class="zc_span"  id="realName">${info.name }</span></dd>
		<dt>法人身份证号码*</dt>
		<dd><span class="zc_span"  id="idCard"">${info.idCard }</span></dd>
		
		<dt>账号类型*</dt>
		<dd>&nbsp;&nbsp;&nbsp;
		<input name="public" type="radio" value="1" id="pyes" <c:if test="${card.accountType=='1' }">checked="checked"</c:if>/>对公
		&nbsp;&nbsp;&nbsp;<input name="public" type="radio" value="0" <c:if test="${card.accountType=='0' }">checked="checked"</c:if> id="pno"/>对私</dd>
		
		<dt>银行卡号*</dt>
		<dd><input class="zc_input" type="tel" id="bankCard" value="${card.bankCard }"></dd>
		<dt>银行名称*(<span style="color:red;">由卡号自动识别</span>)</dt>
		<dd><input class="zc_input" type="text" id="bankName"  value="${card.bankName }"></dd>
		<input type="hidden" id="clearBankChannelNo" value="${card.bankCode }">
		<dt>银行预留手机号码*</dt>
		<dd><input class="zc_input" type="tel" id="phone" value="${card.mobile }"></dd>
		<dt>手机验证码*</dt>
		<dd><input class="zc_input" type="tel" placeholder="请输入验证码" style="width:70%" id="validateCode"><a href="#" class="hqyzm" id="code">获取验证码</a></dd>

		 <dd style="display: none;" id="errorDiv">
			<div class="weui_cell_hd"></div>
			<div class="weui_cell_bd weui_cell_primary">
				<span id="errorMsg" style="color: red;"></span>
			</div>
		</dd>
	</dl> --%>
	
	<div class="weui-tab sm-tab">
		<div class="weui-navbar" id="tabChange">
			<a class="weui-navbar__item weui-bar__item--on">个人</a><!-- 对私 -->
			<a class="weui-navbar__item">企业</a><!-- 对公 -->
		</div>
		<div class="weui-tab__bd">
			<div class="weui-tab__bd-item weui-tab__bd-item--active">
				<dl>
					<dt>法人姓名*</dt>
					<dd><input class="zc_input" type="text" id="realName" value="${info.name }" readonly="readonly"/></dd>
					<dt class="person">法人身份证号码*</dt>
					<dd class="person"><input class="zc_input" type="text" id="idCard" value="${info.idCard }" readonly="readonly"/></dd>
					<!-- 对公显示企业名称无身份证号码 -->
					<dt class="enterprise">企业名称*</dt>
					<dd class="enterprise"><input class="zc_input" type="text" id="temp1" value="${card.temp1 }"></dd>
					<input type="hidden" id="accountType" name="accountType" value="${card.accountType }">
					<%-- <dt>账号类型*</dt>
					<dd>&nbsp;&nbsp;&nbsp;
					<input name="public" type="radio" value="1" id="pyes" <c:if test="${card.accountType=='1' }">checked="checked"</c:if>/>对公
					&nbsp;&nbsp;&nbsp;<input name="public" type="radio" value="0" <c:if test="${card.accountType=='0' }">checked="checked"</c:if> id="pno"/>对私</dd>
 --%>					
					<dt>银行卡号*</dt>
					<dd><input class="zc_input" type="tel" id="bankCard" value="${card.bankCard }"></dd>
					<dt>银行名称*(<span style="color:red;">由卡号自动识别</span>)</dt>
					<dd><input class="zc_input" type="text" id="bankName"  value="${card.bankName }"></dd>
					<input type="hidden" id="clearBankChannelNo" value="${card.bankCode }">
					<dt>银行预留手机号码*</dt>
					<dd><input class="zc_input" type="tel" id="phone" value="${card.mobile }"></dd>
					<!-- <dt>手机验证码*</dt>
					<dd><input class="zc_input" type="tel" placeholder="请输入验证码" style="width:70%" id="validateCode"><a href="#" class="hqyzm" id="code">获取验证码</a></dd>
	 -->		
					 <dd style="display: none;" id="errorDiv">
						<div class="weui_cell_hd"></div>
						<div class="weui_cell_bd weui_cell_primary">
							<span id="errorMsg" style="color: red;"></span>
						</div>
					</dd>
				</dl>
			</div>
	</div>
	
	<input name="Fruit" type="checkbox" value="" id="agree"/> 同意<a href="javascript:;" class="open-popup" data-target="#dsywxy">《金融代收服务商户入网协议》</a>
	<input type="hidden" id="merchantNo" value="${currentUser.merchantNo }">
</div>

<div class="weui-btn-area" style="padding-bottom:20px">
	
		<a href="apply2" class="weui-btn weui-btn_primary"  style="width:45%;float:left;">上一步</a>
		<button href="javascript:void(0);" class="weui-btn weui-btn_primary" id="next" style="width:45%;">提交</button>
	
</div>

<div id="dsywxy" class="weui-popup__container"><!-- 入网协议 -->
	<div class="weui-popup__overlay"></div>
	<div class="weui-popup__modal">
		<div class="toolbar">
          <div class="toolbar-inner">
            <a href="javascript:;" class="picker-button close-popup">关闭</a>
            <h1 class="title">金融代收服务商户入网协议</h1>
          </div>
        </div>
		<article class="weui-article balanceC">
			<div class="weui-cells weui-cells_form" style="margin-top:50px;padding:15px;font-size:12px;text-align:left">
				
				${agreement }
				
				<!-- <div><p style="text-indent:16pt"><span><font>特别提示：</font></span><span><o:p></o:p></span></p><p style="text-indent:16pt"><span>
				<font>本协议为</font></span><span>通金融服务有限公司（以下简称金融）与您共同签订，具有合同法律效力。</span>
				<span><o:p></o:p></span></p><p style="text-indent:16pt">
				<span><font>请您务必审慎阅读并充分理解，确认无误后进行点击确认。协议确认后即表示您已充分阅读、理解并接受所有协议条款，届时您将必须遵守本协议约定并同意本协议的约束。</font></span><span><o:p></o:p></span></p>
				<p style="text-indent:16pt;text-align:left"><span><font>除非您已阅读并接受本协议所有条款，否则无权使用本服务。</font></span>
				<span><o:p></o:p></span></p><p><span><font>经平等友好协商，就本协议内容一致达成如下协议：</font></span>
				<span><o:p></o:p></span></p><p><span><font>一、服务内容</font></span><span><o:p></o:p></span></p>
				<p><span>1.1</span><span>&nbsp;</span>
				<span><font>本协议约定的服务是指您授权金融向您的收单机构收取交易待结算款，并将上述待结算交易款扣除服务费后于约定的支付时间支付到您的指定账户；金融同意接受您的委托进行代收业务处理并提供各项增值服务，包括但不限于为您提供点餐、打车、理财、支付、营销、账户管理等。</font></span><span><o:p></o:p>
				</span></p><p><span>1.2&nbsp;<font>本协议约定</font></span><span><font>：您交易的</font></span>
				<span><font>服务费</font></span><span><font>将</font></span><span><font>从您的待结算交易款中扣除。金融有权根据相关增值服务调整上述服务费额度。如您在额度调整后继续使用本服务，表示你同意相关调整。涉及金额如因小数点后两位进位而产生微小误差，各方均予以认可。无论何种原因，金融已经收取的结算服务费不再退还。</font></span><span><o:p></o:p></span></p><p><span>1.3</span><span>&nbsp;</span><span><font>应用场景：当用户购买您的商品或服务时，您向用户出示金融</font>&#8220;</span><span><font>民生银行</font></span><span>-</span><span><font>维码付</font>&#8221;二维码信息，用户使用用户端扫描该二维码发起交易即视为您接受本协议约定服务。</span><span><o:p></o:p></span></p><p><span>1.4</span><span>&nbsp;</span><span><font>本服务仅能用于您在商户平台申报并经核准的商品或服务，你了解并同意金融有权根据你经营的商品或服务类型，以及你经营活动的风险状况调整你的交易额度。</font></span><span><o:p></o:p></span></p><p><span>1.5&nbsp;<font>基于本协议约定服务，金融为您支付的起点是人民币</font><font>10.01</font><font>元。金融将严格按照双方约定的划款日期进行付款。遇特殊情况或法定节假日无法正常付款的，付款时间遇顺延。对于在途资金，金融不支付任何形式的利息或类似费用。你了解并同意金融有权根据你经营的商品或服务类型，以及你经营活动的风险状况调整你的支付起点。</font></span><span><o:p></o:p></span></p><p><span><font>二、双方的权利和义务</font></span><span><o:p></o:p></span></p><p><span>2.1</span><span>&nbsp;</span><span><font>金融按本协议约定扣除服务费及其他应扣款项后，按约定的支付方式向您指定账户付款。</font></span><span><o:p></o:p></span></p><p><span>2.2</span><span>&nbsp;</span><span><font>金融仅为您提供货币资金代收服务，您与用户或其他主体之间的纠纷由您自行负责处理，与金融无关。如您与用户或其他主体之间的纠纷给金融造成损失，金融有权从您后续代收资金中予以扣除，当代收资金不足时，由您承担赔偿责任。</font></span><span><o:p></o:p></span></p><p><span>2.3&nbsp;<font>您须保证代收资金的原始交易真实、正当、合法，不会违反法律法规，也不会侵犯任何第三方利益。</font></span><span><o:p></o:p></span></p><p><span>2.4</span><span>&nbsp;</span><span><font>您承诺代收资金均系其合法收入，即所收款的资金来源不涉及任何违法、违规行为，因收款资金来源不明导致的风险由您自行承担，因此给金融造成名誉或经济损失的，您应予以赔偿。</font></span><span><o:p></o:p></span></p><p><span>2.5</span><span>&nbsp;</span><span><font>您不得利用金融提供的本合同服务从事洗钱、赌博、金融诈骗、恐怖融资等各种违法犯罪活动，金融一旦发现您的产品销售或服务提供不符合国家法律、行政法规、部门规章及地方性法规的，有权立即终止本协议、停止服务并向有关监管机关或司法机关报告，您应承担因此导致的一切经济及法律责任。</font></span><span><o:p></o:p></span></p><p><span>2.6</span><span>&nbsp;</span><span><font>金融将代收款项成功支付于您后，如出现金融向您多付、错付款项等情况，则金融有权从您之后的代收资金中扣除相应金额。</font></span><span><o:p></o:p></span></p><p><span>2.7</span><span>&nbsp;</span><span><font>基于该系统服务的实时性和不可更改性，您向金融作出授权指令后，均为不可撤销或更改的，您对此无异议。</font></span><span><o:p></o:p></span></p><p><span>2.8</span><span>&nbsp;</span><span><font>您应妥善保管终端登录账户及密码，包括但不限于手机短信验证码等。您的商户号下的一切行为均视为您的真实意思表示。若您发现商户号被盗用，应立即通知金融。因您管理不善导致一切经济损失的，由您自行承担。</font></span><span><o:p></o:p></span></p><p><span>2.9</span><span>&nbsp;</span><span><font>若您需变更结算账户，应及时通知金融。因变更账户或指定收款人但未及时通知导致增加的额外成本或任何损失，由您承担。</font></span><span><o:p></o:p></span></p><p><span>2.10&nbsp;<font>金融代收金额应以您的收单方发起的数据为准，金融不对上述数据的真实性和准确性、合法性负责。您与金融对账数据不一致的，以随行付数据为准。</font></span><span><o:p></o:p></span></p><p><span>2.11&nbsp;<font>鉴于电子商务的特殊性质，金融对黑客攻击、网络病毒、银行系统故障、电信部门技术调整及线路故障、银行政策调整导致之影响、因政府管制而造成的暂时性关闭、电力系统故障或限制性供电等在内的任何影响网络正常经营之情形不承担责任。</font></span><span><o:p></o:p></span></p><p><span>2.12&nbsp;<font>金融有权在事先通知您的情况下调整本协议相关内容，通知方式包括但不限于网站公示、书面、邮件等，公告期限届满后即时生效。若您在本协议内容公告后无异议，表示您已充分阅读、理解并接受变更后的协议内容，也将遵循变更后的协议内容进行合作；若您不同意变更后的协议内容，您应在变更生效前与金融联系解决。</font></span><span><o:p></o:p></span></p><p><span><font>三、违约责任</font></span><span><o:p></o:p></span></p><p><span><font>各方中的任何一方违反本协议中所做约定的义务，均构成违约。违约方要承担守约方的相关经济损失。</font></span><span><o:p></o:p></span></p><p><span><font>四、协议变更和解除</font></span><span><o:p></o:p></span></p><p><span>4.1</span><span>&nbsp;</span><span><font>本协议的生效时间以您确认授权的时间为准，有效期一年。若各方均未在有效期届满前一个月以书面形式通知解约，则本协议自动续期一年，续期次数不限。</font></span><span><o:p></o:p></span></p><p><span>4.2&nbsp;<font>如本协议有任何变更，金融将对变更事项进行公告。如您不同意相关变更，必须立即以书面的方式通知金融终止服务。任何修订或新协议一经在公告期结束即生效。您登录或继续使用服务即表示接受经修订的协议。若双方无法就协议变更达成一致意见的，可终止本协议。</font></span><span><o:p></o:p></span></p><p><span>4.3&nbsp;<font>各方有权基于各自的营运需要，提前终止本协议，且无需承担任何法律责任。需提前终止本协议的，主动提出方应提前一个月以书面或电子邮件形式通知对方。</font></span><span><o:p></o:p></span></p><p><span><font>五、适用法律及争议解决</font></span><span><o:p></o:p></span></p><p><span>5.1&nbsp;<font>本协议之解释、适用、争议解决等一切事宜，均适用中华人民共和国大陆地区法律。</font></span><span><o:p></o:p></span></p><p class="MsoNormal"><span>5.2</span><span>&nbsp;</span><span><font>因本协议产生任何纠纷，各方应友好协商，协商不成的，各方应将争议提交金融所在地人民法院诉讼解决。</font></span><span><o:p></o:p></span></p><p><span><o:p>&nbsp;</o:p></span></p><p style="text-align:right"><span>深圳前海通金融服务有限公司</span></p><p style="text-align:right"><span>2016年10月15日</span></p></div> -->
			</div>
		</article>
		
		<div class="weui-btn-area" style="margin-bottom:20px">
				<a href="javascript:;" class="weui-btn weui-btn_primary close-popup">关闭</a>
		</div>
		
	</div>
</div>

<script src="${path}/resources/scripts/jquery-2.1.4.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/fastclick.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/jquery-weui.min.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/ajaxfileupload.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
<script src="${path}/resources/scripts/wechat/fileupload.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>

<script src="${path}/resources/scripts/wechat/apply3.js?rnd=${version}" type="text/javascript" charset="utf-8"></script>
</body></html>
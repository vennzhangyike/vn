package com.hfepay.scancode.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Dates.DF;
import com.hfepay.commons.base.lang.IpUtil;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.pay.service.WithdrawService;
import com.hfepay.pay.utils.IpUtil.GetCityByIpUtil;
import com.hfepay.pay.utils.IpUtil.LocationInfo;
import com.hfepay.scancode.api.condition.ApiChannelWxParamsCondition;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.BaseErrorMsg;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.ScanCodeGetWayErrorCode;
import com.hfepay.scancode.commons.WechatRedirectEnu;
import com.hfepay.scancode.commons.bo.AdviertismentBo;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.MerchantWalletCondition;
import com.hfepay.scancode.commons.contants.AdviertismentType;
import com.hfepay.scancode.commons.contants.DateFlagEnu;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.dto.OrderPayTotalDTO;
import com.hfepay.scancode.commons.entity.AdviertisementInfo;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.MerchantQrcode;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.commons.vo.MerchantCashierVo;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.operator.AdviertisementInfoService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.MerchantActivityService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;
import com.hfepay.scancode.service.operator.MerchantQrcodeService;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.operator.MerchantWalletService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;
import com.hfepay.scancode.service.order.OrderPayService;
import com.hfepay.scancode.signature.WechatSign;

import net.sf.json.JSONObject;

/**
 * 页面
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/scancode")
public class WechatController {
	public static final Logger log = LoggerFactory.getLogger(WechatController.class);
	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private MerchantWalletService merchantWalletService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private MerchantBankcardService merchantBankcardService;
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	@Autowired
	private MerchantQrcodeService merchantQrcodeService;
	@Autowired
	private ChannelExpandService channelExpandService;
//	@Autowired
//	private ChannelBaseService channelBaseService;
	@Autowired
	private MerchantCashierService merchantCashierService;	
	//@Autowired
	//private PayScanCodeService payScanCodeService;
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private MerchantPaywayService merchantPaywayService;
	@Autowired
	private AdviertisementInfoService adviertisementInfoService;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private MerchantActivityService merchantcashingActive;
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;
	@Autowired
	private MerchantStoreService merchantStoreService;
	@Autowired
	private WithdrawService withdrawService;
	@RequestMapping("/balance")
	public String balance(ModelMap model,HttpServletRequest request){
		//今日收入，月收入，总收入，可提现金额
		BigDecimal todayTotalIn = new BigDecimal(0);
		BigDecimal monthTotalIn = new BigDecimal(0);
		BigDecimal totalIn = new BigDecimal(0);
		BigDecimal canWithDraws = new BigDecimal(0);
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		//可提现金额和提现页面的总金额一致
		List<OrderPayTotalDTO> walletList = orderPayService.getDealMoney(channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());//余额和交易金额
		for (OrderPayTotalDTO orderDto : walletList) {//可提现金额
			canWithDraws = canWithDraws.add(orderDto.getBalance());
		}
		String date = Dates.format(DF.yyyy_MM_dd, new Date());//日期
		
		todayTotalIn = orderPayService.getTotalInMoney(date,DateFlagEnu.DAY.getCode(),channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());
		monthTotalIn = orderPayService.getTotalInMoney(date,DateFlagEnu.MONTH.getCode(),channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());
		totalIn = orderPayService.getTotalInMoney(date,"",channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());

		request.setAttribute("todayTotalIn",todayTotalIn);
		request.setAttribute("monthTotalIn",monthTotalIn);
		request.setAttribute("totalIn",totalIn);
		request.setAttribute("canWithDraws",canWithDraws);
		return "scancode/wechat/balance"; 
	}
	@RequestMapping("/withdrawpage")
	public String withdrawpage(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		
		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(channelAdmin.getMerchantNo());
		if (!merchantInfo.getStatus().equals(MerchantStatus.MERCHANT_STATUS_3.getCode())) {
			request.setAttribute("error", ScanCodeGetWayErrorCode.VALIDAT_100016.getDesc());
			return "scancode/wechat/scan_error";
		}
		
		MerchantBankcard accounts = merchantBankcardService.findByMerchantNo(channelAdmin.getMerchantNo());
		
		//默认为0
		request.setAttribute("zfbBalance",new BigDecimal("0"));
		request.setAttribute("wxbalance",new BigDecimal("0"));
		request.setAttribute("wxgzhbalance",new BigDecimal("0"));
		request.setAttribute("qqbalance",new BigDecimal("0"));
		request.setAttribute("merchantFlag",channelAdmin.getIdentityFlag());
		if (!accounts.getAccountType().equals("0") || !accounts.getIsRealAccount().equals(Constants.Y)) {
			request.setAttribute("merchantFlag","4");
		}
		
		List<OrderPayTotalDTO> walletList = orderPayService.getDealMoney(channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());//余额和交易金额
		if(walletList!=null){
			for (int i = 0; i < walletList.size(); i++) {
				OrderPayTotalDTO orderDto = walletList.get(i);
				if (null != orderDto.getPayCode()) {
					if (orderDto.getPayCode().equals("ZFBSMZF")) {
						request.setAttribute("zfbBalance",orderDto.getBalance());
					}else if(orderDto.getPayCode().equals("WXSMZF")){
						request.setAttribute("wxbalance",orderDto.getBalance());
					}else if(orderDto.getPayCode().equals("WXGZHZF")){
						request.setAttribute("wxgzhbalance",orderDto.getBalance());
					}else if(orderDto.getPayCode().equals("QQZF")){
						request.setAttribute("qqbalance",orderDto.getBalance());
					}
				}
			}
		}
		
		log.info("########zfbBalance["+request.getAttribute("zfbBalance")+"]#######");
		log.info("########wxbalance["+request.getAttribute("wxbalance")+"]#######");
		log.info("########wxgzhbalance["+request.getAttribute("wxgzhbalance")+"]#######");
		log.info("########wxgzhbalance["+request.getAttribute("qqbalance")+"]#######");
		
		accounts.setBankCard("**"+accounts.getBankCard().substring(accounts.getBankCard().length()-4, accounts.getBankCard().length()));
		request.setAttribute("accounts",accounts);
		return "scancode/wechat/withdraws";
	}
	
	@RequestMapping("/balanceSuccess")
	public String balanceSuccess(ModelMap model,HttpServletRequest request){
		return "scancode/wechat/balanceSuccess";
	}
	
	@RequestMapping("/doWithDraw/check")
	@ResponseBody
	public BaseErrorMsg doWithDrawCheck(ModelMap model,HttpServletRequest request,MerchantWalletCondition merchantWalletCondition){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		merchantWalletCondition.setMerchantNo(channelAdmin.getMerchantNo());
		BigDecimal balance = new BigDecimal("0");
		Map<String,BigDecimal> walletMap = orderPayService.getDealMoneyTotal(channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());//余额和交易金额
		if(walletMap!=null){
			balance = walletMap.get("balance")==null?balance:walletMap.get("balance");
		}
		int result = merchantWalletCondition.getBalance().compareTo(new BigDecimal(0));
		if(result<=0){
			return new BaseErrorMsg("-1","金额超过必须大于0");
		}
		if(balance.compareTo(merchantWalletCondition.getBalance())==-1){
			return new BaseErrorMsg("-1","金额超过余额");
		}
		return new BaseErrorMsg("0","校验通过");
	}
	
	@RequestMapping("/withdraws")
	public String withdraws(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		log.info("########channelAdmin["+JSONObject.fromObject(channelAdmin)+"]########");
		
		Map<String,String> returnInfo = new HashMap<String,String>();
		
		String merchantNo = channelAdmin.getMerchantNo();
		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantNo);
		if (null == merchantInfo) {
			log.info("########merchantNo["+merchantNo+"],errorMsg["+ScanCodeGetWayErrorCode.VALIDAT_100002.getDesc()+"]########");
			request.setAttribute("errorMsg", ScanCodeGetWayErrorCode.VALIDAT_100002.getDesc());
			return "scancode/wechat/balanceError";
		}
		
		if (!merchantInfo.getStatus().equals(MerchantStatus.MERCHANT_STATUS_3.getCode())) {
			log.info("########merchantNo["+merchantNo+"],errorMsg["+ScanCodeGetWayErrorCode.VALIDAT_100015.getDesc()+"]########");
			request.setAttribute("errorMsg", ScanCodeGetWayErrorCode.VALIDAT_100015.getDesc());
			return "scancode/wechat/balanceError";
		}
		
		//参数校验
//		String payCode = request.getParameter("payCode");
//		if (null == payCode || "".equals(payCode)) {
//			returnInfo = returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(), ScanCodeGetWayErrorCode.VALIDAT_100002.getDesc());
//			return "scancode/paymentResult";
//		}
		
		if (!merchantInfo.getStatus().equals(MerchantStatus.MERCHANT_STATUS_3.getCode())) {
			log.info("########merchantNo["+merchantNo+"],errorMsg["+ScanCodeGetWayErrorCode.VALIDAT_100003.getDesc()+"]########");
			request.setAttribute("errorMsg", ScanCodeGetWayErrorCode.VALIDAT_100003.getDesc());
			return "scancode/wechat/balanceError";
		}
		
		//判定是否开通提现
		MerchantBankcard merchantBankcard = merchantBankcardService.findByMerchantNo(merchantNo);
		if (!merchantBankcard.getIsRealAccount().equals(Constants.Y)) {
			log.info("########merchantNo["+merchantNo+"],errorMsg["+ScanCodeGetWayErrorCode.VALIDAT_100014.getDesc()+"]########");
			request.setAttribute("errorMsg", ScanCodeGetWayErrorCode.VALIDAT_100014.getDesc());
			return "scancode/wechat/balanceError";
		}
		if (!merchantBankcard.getAccountType().equals("0")) {
			log.info("########merchantNo["+merchantNo+"],errorMsg["+ScanCodeGetWayErrorCode.VALIDAT_100017.getDesc()+"]########");
			request.setAttribute("errorMsg", ScanCodeGetWayErrorCode.VALIDAT_100017.getDesc());
			return "scancode/wechat/balanceError";
		}
		
		//商户支付金额统计
		List<OrderPayTotalDTO> walletList = orderPayService.getDealMoney(channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());//余额和交易金额
		if(walletList == null || walletList.size() <= 0){
			log.info("########merchantNo["+merchantNo+"],errorMsg["+ScanCodeGetWayErrorCode.TRADE_300001.getDesc()+"]########");
			request.setAttribute("errorMsg", ScanCodeGetWayErrorCode.TRADE_300001.getDesc());
			return "scancode/wechat/balanceError";
		}
		
		//商户支付方式
//		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
//		merchantPaywayCondition.setMerchantNo(merchantNo);
//		List<MerchantPayway> wayList = merchantPaywayService.findAll(merchantPaywayCondition);
//		if (null == wayList || wayList.size() <=0) {
//			request.setAttribute("errorMsg", ScanCodeGetWayErrorCode.VALIDAT_100011.getDesc());
//			return "scancode/wechat/balanceError";
//		}
		
		for (int i = 0; i < walletList.size(); i++) {
			OrderPayTotalDTO orderDto = walletList.get(i);
			String payCode = orderDto.getPayCode();
			log.info("########payCode["+payCode+"]#######");
			if (payCode.equals(PayCode.PAYCODE_ZFBSMZF.getCode())) {
				BigDecimal balance = orderDto.getBalance();
				if (balance.compareTo(new BigDecimal(0)) <= 0) {
					log.info("########ZFBSMZF["+orderDto.getBalance()+"]账户无余额，不予以提现########");
					continue;
				}
			}else if(payCode.equals(PayCode.PAYCODE_WXSMZF.getCode())){
				BigDecimal balance = orderDto.getBalance();
				if (balance.compareTo(new BigDecimal(0)) <= 0) {
					log.info("########WXSMZF["+orderDto.getBalance()+"]账户无余额，不予以提现########");
					continue;
				}
			}else if(payCode.equals(PayCode.PAYCODE_WXGZHZF.getCode())){
				BigDecimal balance = orderDto.getBalance();
				if (balance.compareTo(new BigDecimal(0)) <= 0) {
					log.info("########WXGZHZF["+orderDto.getBalance()+"]账户无余额，不予以提现########");
					continue;
				}
			}else if(payCode.equals(PayCode.PAYCODE_QQZF.getCode())){
				BigDecimal balance = orderDto.getBalance();
				if (balance.compareTo(new BigDecimal(0)) <= 0) {
					log.info("########QQZF["+orderDto.getBalance()+"]账户无余额，不予以提现########");
					continue;
				}
			}
			OrderBo orderBo = new OrderBo();
			//调用提现
			orderBo.setAgentNo(merchantInfo.getAgentNo());
			orderBo.setMerchantNo(merchantNo);
			orderBo.setChannelNo(merchantInfo.getChannelNo());
			orderBo.setPayCode(payCode);
			orderBo.setPlatformMerchantNo(merchantInfo.getPlatformMerchantNo());
			returnInfo = withdrawService.withdraws(orderBo);
			log.info("########returnInfo["+JSONObject.fromObject(returnInfo)+"]提现结果########");
		}
		
		if(!returnInfo.get("returnCode").equals(ScanCodeGetWayErrorCode.SYSTEM_000000.getCode())){
			log.info("########提现失败returnMsg"+returnInfo.get("returnMsg")+"########");
			request.setAttribute("errorMsg", returnInfo.get("returnMsg"));
			return "scancode/wechat/balanceError";
		}
		return "scancode/wechat/balanceSuccess";
	}
	
	@RequestMapping("/doWithDraw")
	public String doWithDraw(ModelMap model,HttpServletRequest request,MerchantWalletCondition merchantWalletCondition){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		merchantWalletCondition.setMerchantNo(channelAdmin.getMerchantNo());
		
		BigDecimal balance = new BigDecimal("0");
		Map<String,BigDecimal> walletMap = orderPayService.getDealMoneyTotal(channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());//余额和交易金额
		if(walletMap!=null){
			balance = walletMap.get("balance")==null?balance:walletMap.get("balance");
		}
		int resultc = merchantWalletCondition.getBalance().compareTo(new BigDecimal(0));
		if(resultc<=0){
			request.setAttribute("errorMsg", "金额超过必须大于0");
			return "scancode/wechat/balanceError";
		}
		if(balance.compareTo(merchantWalletCondition.getBalance())==-1){
			request.setAttribute("errorMsg", "金额超过余额");
			return "scancode/wechat/balanceError";
		}
		long result = merchantWalletService.updateBalanceByMerchantNo(merchantWalletCondition);
		if(result<0){
			request.setAttribute("errorMsg", "内部错误，请联系管理员.");
			return "scancode/wechat/balanceError";
		}
		request.setAttribute("balance", merchantWalletCondition.getBalance());
		request.setAttribute("fees", 0);
		return "scancode/wechat/balanceSuccess";
	}
	
	@RequestMapping("/my")
	public String my(ModelMap model,HttpServletRequest request){
		String ip = IpUtil.getIpAddr(request);
		
		String type=request.getParameter("type");
		log.info("this is in my function the redirect type is ================================"+type);
		type=type==null?WechatRedirectEnu.MY.getCode():type;//默认为个人中心
		if(WechatRedirectEnu.BILL.getCode().equals(type)){//公众号进入是交易查询
			return "redirect:/scancode/bill";
		}
		
		if(WechatRedirectEnu.SWEEPCOLLECTION.getCode().equals(type)){//公众号进入是收银员收款
			return "redirect:/scancode/sweepcollection";
		}
		
		if(WechatRedirectEnu.CAMERALSCAN.getCode().equals(type)){//调起摄像头扫码收款
			return "redirect:/scancode/camera";
		}
		
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		BigDecimal balance = new BigDecimal("0");
		BigDecimal re = new BigDecimal("0");
		String isOpenWithdrawls = "Y";
		String date = Dates.format(DF.yyyy_MM_dd, new Date());//日期
		balance = orderPayService.getTotalInMoney(date,DateFlagEnu.DAY.getCode(),channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());
		//可提现金额和提现页面的总金额一致
		List<OrderPayTotalDTO> walletList = orderPayService.getDealMoney(channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());//余额和交易金额
		for (OrderPayTotalDTO orderDto : walletList) {//可提现金额
			re = re.add(orderDto.getBalance());
		}
		/*Map<String,BigDecimal> walletMap = orderPayService.getDealMoneyTotal(channelAdmin.getIdentityNo(),channelAdmin.getIdentityFlag());//余额和交易金额
		if(walletMap!=null){
			balance = walletMap.get("total")==null?balance:walletMap.get("total");
		}*/
		
		request.setAttribute("status", "1");//不完善
		PlatformQrcode qrCodeSrc = new PlatformQrcode();
		//商户需要检查信息完善程度
		log.info("用户类型为：IdentityFlag="+channelAdmin.getIdentityFlag()+"  IdentityNo="+channelAdmin.getIdentityNo());
		if(channelAdmin.getIdentityFlag().equals(IdentityType.Identity_Merchant.getCode())){
			//统计提现总金额
//			List<WithDrawsTotalDTO> withDrawlsList = withdrawalsService.getWithDrawsMoneyTotal(channelAdmin.getMerchantNo(),"");
//			if (withDrawlsList != null && withDrawlsList.size() > 0) {
//				for (int i = 0; i < withDrawlsList.size(); i++) {
//					log.info("##########账户金额信息：[total_amt:"+withDrawlsList.get(i).getTotalAmt()+"]##########");
//					re = re.add(withDrawlsList.get(i).getTotalAmt());
//				}
//			}
//			re = balance.subtract(re);
			log.info("##########账户金额信息：[balance:"+balance+",re:"+re+"]##########");
			
			//判定商户是否开通提现
			MerchantBankcard merchantBankcard = merchantBankcardService.findByMerchantNo(channelAdmin.getMerchantNo());
			if (null != merchantBankcard) {
				if (!Constants.Y.equals(merchantBankcard.getIsRealAccount()) || !"0".equals(merchantBankcard.getAccountType())) {
					re = new BigDecimal(0);
					log.info("##########账户账户未开通提现时账户信息：[balance:"+balance+",re:"+re+"]##########");
					isOpenWithdrawls = "N";
				}
			}
			
			MerchantInfo info = merchantInfoService.findByMerchantNo(channelAdmin.getMerchantNo());
			if(MerchantStatus.MERCHANT_STATUS_2.getCode().equals(info.getStatus())||MerchantStatus.MERCHANT_STATUS_4.getCode().equals(info.getStatus())
					||Strings.isEmpty(info.getName())||MerchantStatus.MERCHANT_STATUS_8.getCode().equals(info.getStatus())){
				request.setAttribute("status", "0");//完善信息
			}
			
			if(WechatRedirectEnu.WECHANT_IN.getCode().equals(type)){//商户入驻：新用户完善信息，老用户跳转到首页
				if(MerchantStatus.MERCHANT_STATUS_8.getCode().equals(info.getStatus())){//新用户
					return "redirect:/scancode/apply1";
				}else if(!MerchantStatus.MERCHANT_STATUS_3.getCode().equals(info.getStatus())){//老用户,不是审核通过的
					return "redirect:/scancode/progress";
				}
			}
			else if(WechatRedirectEnu.WECHANT_MSG.getCode().equals(type))//商户信息：审核通过到首页,其余的到审核进度
			{
				if(!MerchantStatus.MERCHANT_STATUS_3.getCode().equals(info.getStatus())){//不是审核通过的到审核进度
					return "redirect:/scancode/progress";
				}
			}
			
			try {
				log.info("二维码编号："+info.getQrCode());
				qrCodeSrc = platformQrcodeService.findByQrcode(info.getQrCode());
				log.info("二维码在try中为======："+qrCodeSrc);
			} catch (Exception e) {
				log.info("二维码获取失败："+e);
				e.printStackTrace();
			}
			//昵称不同角色显示不同，商户显示商户名称，收银员显示收银员姓名
			request.setAttribute("nickName", info.getMerchantName());
			request.setAttribute("name", info.getName());
		}
		else if(channelAdmin.getIdentityFlag().equals(IdentityType.Identity_Cashier.getCode())){//收银员
			MerchantCashier cashier = merchantCashierService.findByOpenId(channelAdmin.getOpenId()); 
			MerchantQrcode code = merchantQrcodeService.findQrcodeByIdentityNo(channelAdmin.getIdentityNo());
			if(null!=code&&cashier!=null&&cashier.getRecordStatus().equals(Constants.RECORD_STATUS_YES)&"1".equals(cashier.getStatus())){//收银员没有被禁用
				qrCodeSrc.setImage(code.getImage());
				qrCodeSrc.setQrName(code.getQrName());
			}
			request.setAttribute("nickName", cashier.getUserName());
			request.setAttribute("name", cashier.getUserName());
		}
		
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(channelAdmin.getAgentNo());
		ChannelExpand channelExpand = channelExpandService.findByChannelNo(vo.getOrganNo());
		//ChannelBase channelBase = channelBaseService.findByChannelNo(channelAdmin.getChannelCode());
		log.info("二维码图片为："+qrCodeSrc.getImage()+",isOpenWithdrawls:"+isOpenWithdrawls);
		
		//获取广告信息
		try {
			LocationInfo locationInfo = GetCityByIpUtil.getCityInfoByIP(ip);
			AdviertismentBo adviertismentBo = new AdviertismentBo();
			adviertismentBo.setAdviertPosition(AdviertismentType.HOME_BANNEL.getCode());
			adviertismentBo.setMerchantNo(channelAdmin.getMerchantNo());
			adviertismentBo.setPname(locationInfo.state);
			adviertismentBo.setCity(locationInfo.city);
			List<AdviertisementInfo> list = adviertisementInfoService.getAdviertisInfoByUser(adviertismentBo);
			if (null != list && list.size() > 0) {			
				request.setAttribute("adviertisementImg",list.get(0).getImgUrl());
				request.setAttribute("adviertisementUrl",list.get(0).getAdviertLink());
			}else {
				request.setAttribute("adviertisementImg","");
				request.setAttribute("adviertisementUrl","");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//防止前端显示金额为负数
		if (re.compareTo(new BigDecimal("0")) == -1) {
			re = new BigDecimal("0");
		}
		request.setAttribute("balance",balance );
		request.setAttribute("todayTotal", re);
		request.setAttribute("qrCodeSrc", qrCodeSrc.getImage());
		request.setAttribute("qrCodeName", qrCodeSrc.getQrName());
		request.setAttribute("type", type);//为收款的话，弹出收款二维码
		request.setAttribute("phone", channelExpand.getPhone());
		request.setAttribute("icon", channelExpand.getIcon());
		request.setAttribute("channelName", vo.getOrganName());
		request.setAttribute("isOpenWithdrawls",isOpenWithdrawls );
		return "scancode/wechat/my";
	}
	
	@RequestMapping("/password")
	public String password(ModelMap model,HttpServletRequest request){
		return "scancode/wechat/password";
	}
	
	@RequestMapping("/set")
	public String set(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		MerchantInfo info = merchantInfoService.findByMerchantNo(channelAdmin.getMerchantNo());
		request.setAttribute("status", info.getStatus());
		List list = getMerchantRate(channelAdmin.getMerchantNo());
		if(Lists.isNotEmpty(list)){//是否有费率
			request.setAttribute("merchantRate", "yes");
		}
		return "scancode/wechat/set";
	}
	@RequestMapping("/merchants")
	public String merchants(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		String merchantNo = channelAdmin.getMerchantNo();
		MerchantInfo info = merchantInfoService.findByMerchantNo(merchantNo);
		List<MerchantStore> storeList = merchantStoreService.findMerchantStore(merchantNo);//查询第一个入驻的时候的门店
		if(Lists.isNotEmpty(storeList)){
			request.setAttribute("store", storeList.get(0));
		}
		request.setAttribute("merchant", info);
		return "scancode/wechat/merchants";
	}
	
	@RequestMapping("/sure")
	public String sure(ModelMap model,HttpServletRequest request){
		return "scancode/wechat/sure";
	}
	
	@RequestMapping("/basic")
	public String basic(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		MerchantInfo info = merchantInfoService.findByMerchantNo(channelAdmin.getMerchantNo());
		request.setAttribute("info", info);
		return "scancode/wechat/basic";
	}
	
	@RequestMapping("/settlement")
	public String settlement(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		MerchantBankcard merchantAccounts = merchantBankcardService.findByMerchantNo(channelAdmin.getMerchantNo());
		request.setAttribute("info", merchantAccounts);
		return "scancode/wechat/settlement";
	}
	
	@RequestMapping("/settlement/update")
	@ResponseBody
	public BaseErrorMsg settlementUpdate(MerchantBankcardCondition condition,String validateCode,HttpServletRequest request){
		boolean flag = channelAdminService.validateRedisValidateCode(condition.getMobile(), validateCode);
		if(!flag){
			log.info("-------------验证码错误------------");
			return new BaseErrorMsg("验证码错误");
		}
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		condition.setMerchantNo(channelAdmin.getMerchantNo());
		condition.setStatus("3");//设置为审核中
		condition.setOperator(channelAdmin.getUserName());
		long result = merchantBankcardService.updateByMerchantNo(condition);
		if(result>0){
			return new BaseErrorMsg("0", "变更成功");
		}else{
			return new BaseErrorMsg("变更失败");
		}
	}

	@RequestMapping("/binding")
	public String binding(ModelMap model,HttpServletRequest request){
		return "scancode/wechat/binding";
	}
	
	@RequestMapping("/binding/mobile")
	@ResponseBody
	public BaseErrorMsg bindingMobile(String mobile,String validateCode,HttpServletRequest request){
		boolean flag = channelAdminService.validateRedisValidateCode(mobile, validateCode);
		if(!flag){
			log.info("-------------验证码错误------------");
			return new BaseErrorMsg("验证码错误");
		}
//		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute("currentUser");
		long result = 1L;//merchantBankcardService.updateByMerchantNo(condition);
		if(result>0){
			return new BaseErrorMsg("0", "变更成功");
		}else{
			return new BaseErrorMsg("变更失败");
		}
	}
	
	@RequestMapping("/about")
	public String about(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute("currentUser");
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(channelAdmin.getAgentNo());
		ChannelExpand channelExpand = channelExpandService.findByChannelNo(vo.getOrganNo());
		request.setAttribute("aboutUs", channelExpand.getAboutUs());
		return "scancode/wechat/about";
	}
	
	@RequestMapping("/help")
	public String help(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute("currentUser");
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(channelAdmin.getAgentNo());
		ChannelExpand channelExpand = channelExpandService.findByChannelNo(vo.getOrganNo());
		request.setAttribute("helpInfo", channelExpand.getHelpInfo());
		return "scancode/wechat/help";
	}
	//显示商户的费率
	@RequestMapping("/merchantRate")
	public String merchantRate(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute("currentUser");
		String merchantNo = channelAdmin.getMerchantNo();//商户编号
		request.setAttribute("paywayList", getMerchantRate(merchantNo));
		return "scancode/wechat/merchantrate";
	}
	
	private List<MerchantPayway> getMerchantRate(String merchantNo){
		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		merchantPaywayCondition.setMerchantNo(merchantNo);
		merchantPaywayCondition.setRecordStatus("Y");
		merchantPaywayCondition.setStatus("1");//状态正常的商户费率
		List<MerchantPayway> list = merchantPaywayService.findAll(merchantPaywayCondition);
		Map<String,String> map = PayCode.toMap();
		for (MerchantPayway merchantPayway : list) {
			merchantPayway.setPayCode(map.get(merchantPayway.getPayCode()));
		}
		return list;
	}
	
	@RequestMapping("/toScan")
	public String toScan(HttpServletRequest request){
		return "redirect:/scancode/camera";
	}
	/**
	* @Description 微信授权回调
	* @param
	* @author husain 
	* @Date 2016年10月7日 下午7:32:56
	 */
	@RequestMapping(value = "/camera")
	public String camera(HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		MerchantInfo info = merchantInfoService.findByMerchantNo(channelAdmin.getMerchantNo());
		if(!MerchantStatus.MERCHANT_STATUS_3.getCode().equals(info.getStatus())){
			request.setAttribute("error", "商户没有审核通过，不能收款");
			return "scancode/wechat/scan_error";
		}
		String storeName = null;
		//商户显示总店，收银员显示分店名称
		if(IdentityType.Identity_Merchant.getCode().equals(channelAdmin.getIdentityFlag())){
			MerchantStoreCondition storeCondition = new MerchantStoreCondition();
			storeCondition.setMerchantNo(channelAdmin.getMerchantNo());
			storeCondition.setStoreType("0");//总店
			storeCondition.setRecordStatus("Y");//有效的
			MerchantStore store = merchantStoreService.findByCondition(storeCondition);
			if(null==store){
				request.setAttribute("error", "商户不存在门店，不能收款");
				return "scancode/wechat/scan_error";
			}
			storeName=store.getStoreName();
		}
		else{
			MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
			merchantCashierCondition.setCashierNo(channelAdmin.getIdentityNo());
			merchantCashierCondition.setRecordStatus("Y");//有效的
			MerchantCashierVo vo = (MerchantCashierVo)merchantCashierService.findByCondition(merchantCashierCondition);
			if(null==vo||Strings.isEmpty(vo.getStoreName())){
				request.setAttribute("error", "收银员没绑定门店，不能收款");
				return "scancode/wechat/scan_error";
			}
			storeName = vo.getStoreName();
		}
		
		String ticket=null;
		JSONObject json = null;
		try {
			ticket = merchantBusinessService.getJsApiTicket(channelAdmin.getAgentNo());
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(channelAdmin.getAgentNo());
			ApiChannelWxParams param = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			json = JSONObject.fromObject(param.getWxParams());
		} catch (Exception e) {
			log.error("ticket get error",e);
			e.printStackTrace();
		}
		ParamsInfo Paraminfo = payCallBackOperatorService.findParamsKey(ScanCodeConstants.SYSTEM, ParamsType.PARAMS_CALLBACK_INFO.getCode());
		JSONObject callbackJson = JSONObject.fromObject(Paraminfo.getParamsValue());
		
		JSONObject paramsInfoJson = payCallBackOperatorService.getParamsInfoByDomain(channelAdmin.getChannelCode());
		log.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
		
		String url=paramsInfoJson.getString("cameraUrl");
		Map<String, String> map = WechatSign.sign(ticket, url);
		map.put("appId", json.getString("appid"));
		request.setAttribute("map", map);
		request.setAttribute("merchantNo", channelAdmin.getMerchantNo());
		request.setAttribute("identityNo", channelAdmin.getIdentityNo());
		request.setAttribute("scanPayUrl", callbackJson.getString("scanPayUrl"));
		request.setAttribute("storeName", storeName);
		return "scancode/toscan";
	}
	
	/**
	 * 商户收银活动优惠
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@RequestMapping("/merchantcashingActive")
	@ResponseBody
	public Map<String,BigDecimal> merchantcashingActive(HttpServletRequest request,OrderPayment pay){
		Map<String,BigDecimal> map = merchantcashingActive.calculateCheapCash(pay.getMerchantNo(), pay.getOrderAmt(),BigDecimal.ZERO);
		if(map==null||map.isEmpty()){
			return null;
		}
		return map;
	}
	
	@RequestMapping("/payment")
	public String payment(HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		request.setAttribute("payCode", "ZFBSMZF");
		request.setAttribute("merchantNo", channelAdmin.getMerchantNo());
		return "scancode/payment";
	}
	
	/**
	 * 信用卡申请页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/creditCard")
	public String applyCreditcard(HttpServletRequest request){
		return "scancode/wechat/creditCard";
	}
	
}

package com.hfepay.scancode.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.common.excel.jxls.DynamicColumnExporter;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.commons.condition.MerchantOrderStatisticCondition;
import com.hfepay.scancode.commons.vo.MerchantOrderStatisticVo;
import com.hfepay.scancode.service.operator.MerchantStatisticDataService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

/**
 * 数据统计
 * 
 * @author tanbiao
 *
 */
@Controller
@RequestMapping("/adminManage/statistic")
public class StatisticDataController extends BaseController {
	@Autowired
	private MerchantStatisticDataService merchantStatisticDataService;

	Logger logger = LoggerFactory.getLogger(StatisticDataController.class);

	/*@RequestMapping(value = "/order/page", method = { RequestMethod.GET })
	public String bigdata(HttpServletRequest request) {
		return "admin/statistic/list";
	}*/

	@RequestMapping(value = "/orderStaticPage", method = { RequestMethod.GET, RequestMethod.POST })
	public String listContent() {
		return "admin/statistic/orderStatic";
	}
	
	@RequestMapping(value = "/orderAmtStaticPage", method = { RequestMethod.GET, RequestMethod.POST })
	public String listAmtContent() {
		return "admin/statistic/orderAmtStatic";
	}
	

	@RequestMapping(value = "/orderStaticContent", method = { RequestMethod.POST })
	@ResponseBody
	public JSON orderStatistic(HttpServletRequest request, MerchantOrderStatisticCondition condition) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		//Subject currentMerchantInfo = SecurityUtils.getSubject();
		//MerchantInfo merchantInfo = (MerchantInfo) currentMerchantInfo.getSession().getAttribute("merchantInfo");
		try {
			//condition.setMerchantId(merchantInfo.getCode());
			if (Strings.isEmpty(condition.getStartTime())) {
				condition.setStartTime(Dates.format("yyyy-MM-dd", new Date()));
			}
			MerchantOrderStatisticVo vo = merchantStatisticDataService.orderStatistic(condition);
			map = Maps.mapByAarray(EXECUTE_STATUS, SUCCESS, VALUES, vo);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS, SUCCESS, VALUES, "查询失败");
		}
		return JSONSerializer.toJSON(map);
	}

	@RequestMapping(value = "/orderAmtStaticContent", method = { RequestMethod.POST })
	@ResponseBody
	public JSON orderAmtStatistic(HttpServletRequest request, MerchantOrderStatisticCondition condition) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		/*Subject currentMerchantInfo = SecurityUtils.getSubject();
		MerchantInfo merchantInfo = (MerchantInfo) currentMerchantInfo.getSession().getAttribute("merchantInfo");*/
		try {
			//condition.setMerchantId(merchantInfo.getCode());
			if (Strings.isEmpty(condition.getStartTime())) {
				condition.setStartTime(Dates.format("yyyy-MM-dd", new Date()));
			}
			MerchantOrderStatisticVo vo = merchantStatisticDataService.orderAmtStatistic(condition);
			map = Maps.mapByAarray(EXECUTE_STATUS, SUCCESS, VALUES, vo);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS, SUCCESS, VALUES, "查询失败");
		}
		return JSONSerializer.toJSON(map);
	}

	/**
	 * 导出excel 订单交易笔数
	 * @param request
	 * @param response
	 * @param condition
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "serial" })
	@RequestMapping(value = "/export/countexcel", method = RequestMethod.GET)
	public void counteExcel(HttpServletRequest request, HttpServletResponse response,
			MerchantOrderStatisticCondition condition) throws Exception {
		// 查询条件与名称的对应关系
		/*Subject currentMerchantInfo = SecurityUtils.getSubject();
		MerchantInfo merchantInfo = (MerchantInfo) currentMerchantInfo.getSession().getAttribute("merchantInfo");*/
		MerchantOrderStatisticVo vo = new MerchantOrderStatisticVo();
		//传给模板的数据列表数据
		LinkedList<Map<String,Object>> records = new LinkedList<Map<String,Object>>();
		try {
			//condition.setMerchantId(merchantInfo.getCode());
			if (Strings.isEmpty(condition.getStartTime())) {
				condition.setStartTime(Dates.format("yyyy-MM-dd", new Date()));
			}
			//查询的数据是按照列显示拼装的，导出excel的时候需要转换为按行的形式
			vo = merchantStatisticDataService.orderStatistic(condition);
			 List<String> hCoordinateList = vo.gethCoordinateList();
			 int size = hCoordinateList.size();//时间坐标，代表行数
			 List<String> orderPaySuccessAliData = vo.getOrderPaySuccessAliData();//支付宝支付成功数据
			 List<String> orderPaySuccessWechatData = vo.getOrderPaySuccessWechatData();//微信支付成功数据
			 List<String> orderPaySuccessQQData = vo.getOrderPaySuccessQQData();//QQ支付成功数据
			 List<String> orderPayFailAliData = vo.getOrderPayFailAliData();//支付宝支付失败数据
			 List<String> orderPayFailWechatData = vo.getOrderPayFailWechatData();//微信支付失败数据
			 List<String> orderPayFailQQData = vo.getOrderPayFailQQData();//微信支付失败数据
			for(int i=0;i<size;i++){
				TreeMap<String,Object> record = new TreeMap<String, Object>();
				record.put("时间", hCoordinateList.get(i));
				record.put("支付宝支付成功成功笔数", orderPaySuccessAliData.get(i));
				record.put("支付宝支付成功失败笔数", orderPayFailAliData.get(i));
				record.put("微信支付成功笔数", orderPaySuccessWechatData.get(i));
				record.put("微信支付失败笔数", orderPayFailWechatData.get(i));
				record.put("QQ支付成功笔数", orderPaySuccessQQData.get(i));
				record.put("QQ支付失败笔数", orderPayFailQQData.get(i));
				records.add(record);
			}
		} catch (Exception e) {

		}
		//表头
		 List<String> columns = Arrays.asList(new String[]{"时间","支付宝支付成功成功笔数","支付宝支付成功失败笔数","微信支付成功笔数","微信支付失败笔数","QQ支付成功笔数","QQ支付失败笔数"});
		//参数列表数据
		 List<String> props = columns;
		String title = null;// apiName +"详情统计"+Strings.getUUID();
		String downloadName = "交易笔数统计" + "(" + vo.getTitle() + ").xls";
		DynamicColumnExporter.exportWithDownload(downloadName, title, columns, props, records, response, true);
	}
	
	/**
	 * 导出excel 订单交易金额
	 * @param request
	 * @param response
	 * @param condition
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "serial" })
	@RequestMapping(value = "/export/amtexcel", method = RequestMethod.GET)
	public void amtExcel(HttpServletRequest request, HttpServletResponse response,
			MerchantOrderStatisticCondition condition) throws Exception {
		// 查询条件与名称的对应关系
		/*Subject currentMerchantInfo = SecurityUtils.getSubject();
		MerchantInfo merchantInfo = (MerchantInfo) currentMerchantInfo.getSession().getAttribute("merchantInfo");*/
		MerchantOrderStatisticVo vo = new MerchantOrderStatisticVo();
		//传给模板的数据列表数据
		LinkedList<Map<String,Object>> records = new LinkedList<Map<String,Object>>();
		try {
			//condition.setMerchantId(merchantInfo.getCode());
			if (Strings.isEmpty(condition.getStartTime())) {
				condition.setStartTime(Dates.format("yyyy-MM-dd", new Date()));
			}
			//查询的数据是按照列显示拼装的，导出excel的时候需要转换为按行的形式
			vo = merchantStatisticDataService.orderAmtStatistic(condition);
			 List<String> hCoordinateList = vo.gethCoordinateList();
			 int size = hCoordinateList.size();//时间坐标，代表行数
			 List<String> orderPaySuccessAliData = vo.getOrderPaySuccessAliData();//支付宝支付成功数据
			 List<String> orderPaySuccessWechatData = vo.getOrderPaySuccessWechatData();//微信支付成功数据
			 List<String> orderPaySuccessQQData = vo.getOrderPaySuccessQQData();//QQ支付成功数据
			 List<String> orderPayFailQQData = vo.getOrderPayFailQQData();//QQ支付失败数据
			 List<String> orderPayFailAliData = vo.getOrderPayFailAliData();//支付宝支付失败数据
			 List<String> orderPayFailWechatData = vo.getOrderPayFailWechatData();//微信支付失败数据
			for(int i=0;i<size;i++){
				TreeMap<String,Object> record = new TreeMap<String, Object>();
				record.put("时间", hCoordinateList.get(i));
				record.put("支付宝支付成功金额", orderPaySuccessAliData.get(i));
				record.put("支付宝支付失败金额", orderPayFailAliData.get(i));
				record.put("微信支付成功金额", orderPaySuccessWechatData.get(i));
				record.put("微信支付失败金额", orderPayFailWechatData.get(i));
				record.put("QQ支付成功金额", orderPaySuccessQQData.get(i));
				record.put("QQ支付失败金额", orderPayFailQQData.get(i));
				records.add(record);
			}
		} catch (Exception e) {

		}
		//表头
		 List<String> columns = Arrays.asList(new String[]{"时间","支付宝支付成功金额","支付宝支付失败金额","微信支付成功金额","微信支付失败金额","QQ支付成功金额","QQ支付失败金额"});
		//参数列表数据
		 List<String> props = columns;
		String title = null;// apiName +"详情统计"+Strings.getUUID();
		String downloadName = "交易金额统计" + "(" + vo.getTitle() + ").xls";
		DynamicColumnExporter.exportWithDownload(downloadName, title, columns, props, records, response, true);
	}
}

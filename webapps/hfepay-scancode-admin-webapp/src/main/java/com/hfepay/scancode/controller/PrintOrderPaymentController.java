/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

import java.awt.print.PrinterException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.scancode.commons.PrintOrderPaymentUtil;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/printorderpayment")
public class PrintOrderPaymentController extends BaseController{

	/** 打印交易订单 */
	@RequestMapping(value = "/print/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public JSON show(HttpServletRequest request, ModelMap model, @PathVariable String id) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		try {
			PrintOrderPaymentUtil.printOrderPayment(id);
		} catch (PrinterException e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"打印异常,请检查默认打印设备能否正常工作!");
		}
		
		return JSONSerializer.toJSON(map);
	}

}

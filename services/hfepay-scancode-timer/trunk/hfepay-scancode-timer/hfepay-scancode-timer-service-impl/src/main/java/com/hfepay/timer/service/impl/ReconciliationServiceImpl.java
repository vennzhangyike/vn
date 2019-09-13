/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.scancode.commons.condition.ClearingT0Condition;
import com.hfepay.scancode.commons.condition.ClearingT0ErrCondition;
import com.hfepay.scancode.commons.condition.ClearingT1Condition;
import com.hfepay.scancode.commons.condition.ClearingT1ErrCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.dao.MappingDicionDAO;
import com.hfepay.scancode.commons.dao.MerchantInfoDAO;
import com.hfepay.scancode.commons.entity.ClearingT0;
import com.hfepay.scancode.commons.entity.ClearingT0Err;
import com.hfepay.scancode.commons.entity.ClearingT1;
import com.hfepay.scancode.commons.entity.ClearingT1Err;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.timer.commons.BatchNoUtil;
import com.hfepay.timer.commons.ParamsConfig;
import com.hfepay.timer.service.ClearingT0ErrService;
import com.hfepay.timer.service.ClearingT0Service;
import com.hfepay.timer.service.ClearingT1ErrService;
import com.hfepay.timer.service.ClearingT1Service;
import com.hfepay.timer.service.ReconciliationService;

import net.sf.json.JSONSerializer;

@Service("reconciliationService")
public class ReconciliationServiceImpl implements ReconciliationService {
	public static final Logger logger = LoggerFactory.getLogger(ReconciliationServiceImpl.class);
	@Autowired
    private MappingDicionDAO mappingDicionDAO;
	@Autowired
    private MerchantInfoDAO merchantInfoDAO;
	@Autowired
    private ClearingT1Service clearingT1Service;
	@Autowired
	private ClearingT1ErrService clearingT1ErrService;
	@Autowired
	private RedisSharedCache redisSharedCache;
	@Autowired
    private ClearingT0Service clearingT0Service;
	@Autowired
	private ClearingT0ErrService clearingT0ErrService;
	@Autowired
	private ParamsConfig paramsConfig;
    
	/** 对账Job
	 * @throws Exception */
	@Override
	public void reconciliationJob() throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);		
		String dateStr = Dates.format(Dates.DF.yyyy_MM_dd, cal.getTime());
		logger.info("开始执行对账任务,对账日期：" + dateStr);
		
		CriteriaBuilder cbMappingDicion = Cnd.builder(MappingDicion.class);
		cbMappingDicion.andEQ("keyNo", "CHANNELNO");
		Criteria buildCriteriaMappingDicion = cbMappingDicion.buildCriteria();
		MappingDicion mappingDicion =  mappingDicionDAO.findOneByCriteria(buildCriteriaMappingDicion);
		
		String channelNo = "";
		if(mappingDicion != null){
			channelNo = mappingDicion.getParaVal();
		}else{
			logger.error("数据字典中未配置渠道编号！");
			throw new RuntimeException("数据字典中未配置渠道编号！");
		}
		
		String reconciliationPath = paramsConfig.getReconciliationPath();
		String t1FileStr = reconciliationPath + ScanCodeConstants.SPT + "HTF_"+channelNo+"_T1_"+dateStr+".txt";
		String t0FileStr = reconciliationPath + ScanCodeConstants.SPT + "HTF_"+channelNo+"_T0_"+dateStr+".txt";
//		String aqFile = reconciliationPath + ScanCodeConstants.SPT + "HTF_"+channelNo+"_ZQ_"+dateStr+".txt";
		
		logger.info("开始执行t1对账:" + Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		//t1对账数据插入
		long insertT1Begin=System.currentTimeMillis();
		File t1File = new File(t1FileStr);
		this.insertT1FileData(t1File);
		long insertT1End=System.currentTimeMillis();
		//t1对账数据对比
		long checkT1Begin=System.currentTimeMillis();
		List<ClearingT1Err> t1Err = new ArrayList<ClearingT1Err>();
		if(t1File.exists()){
			t1Err = clearingT1ErrService.checkT1Data();
		}
		long checkT1End=System.currentTimeMillis();
		//t1对账差错数据插入
		long insertT1ErrBegin=System.currentTimeMillis();
		if(t1File.exists()){			
			this.inserT1ErrData(t1Err);
		}
		long insertT1ErrEnd=System.currentTimeMillis();
		logger.info("完成执行t1对账:" + Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		
		logger.info("开始执行t0对账:" + Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		//t0对账数据插入
		long insertT0Begin=System.currentTimeMillis();
		File t0File = new File(t0FileStr);
		this.insertT0FileData(t0File);
		long insertT0End=System.currentTimeMillis();
		//t0对账数据对比
		long checkT0Begin=System.currentTimeMillis();
		List<ClearingT0Err> t0Err = new ArrayList<ClearingT0Err>();
		if(t0File.exists()){
			t0Err = clearingT0ErrService.checkT0Data();
		}
		long checkT0End=System.currentTimeMillis();
		//t0对账差错数据插入
		long insertT0ErrBegin=System.currentTimeMillis();
		if(t0File.exists()){
			this.inserT0ErrData(t0Err);
		}
		long insertT0ErrEnd=System.currentTimeMillis();
		logger.info("完成执行t0对账:" + Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		
		logger.info("t1对账数据插入,执行时间：" + (insertT1End-insertT1Begin)/1000f+" 秒 ");
		logger.info("t1对账数据对比,执行时间：" + (checkT1End-checkT1Begin)/1000f+" 秒 ");
		logger.info("t1对账差错数据插入,执行时间：" + (insertT1ErrEnd-insertT1ErrBegin)/1000f+" 秒 ");
		logger.info("t1对账,执行总时间：" + (insertT1ErrEnd-insertT1Begin)/1000f+" 秒 ");
		
		logger.info("t0对账数据插入,执行时间：" + (insertT0End-insertT0Begin)/1000f+" 秒 ");
		logger.info("t0对账数据对比,执行时间：" + (checkT0End-checkT0Begin)/1000f+" 秒 ");
		logger.info("t0对账差错数据插入,执行时间：" + (insertT0ErrEnd-insertT0ErrBegin)/1000f+" 秒 ");
		logger.info("t0对账,执行总时间：" + (insertT0ErrEnd-insertT0Begin)/1000f+" 秒 ");
		
		logger.info("完成执行对账任务,对账时间：" + dateStr);
	}
	
	/** 构建T1 */
	private ClearingT1 buildClearingT1(String [] arr){
		//示例
		//1701172230462130000|820956606442377216|32170116001115663463|20170116|
		//20170116073318|T1|ZFBSMZF|201701120001|
		//819398132994543616|500.00|1.20|1.20|
		ClearingT1 clearingT1 = new ClearingT1();
		clearingT1.setId(Strings.getUUID());
		clearingT1.setBatchNo(arr[0]);
		clearingT1.setHfTradeNo(arr[1]);
		clearingT1.setTradeNo(arr[2]);
		clearingT1.setClearDate(arr[3]);
		clearingT1.setTradeDate(arr[4]);
		clearingT1.setTradeType(arr[5]);
		clearingT1.setPayCode(arr[6]);
		clearingT1.setHfMerchantNo(arr[7]);
		clearingT1.setMerchantNo(arr[8]);
		clearingT1.setTransAmt(new BigDecimal(arr[9]));
		clearingT1.setMerchantFees(new BigDecimal(arr[10]));
		clearingT1.setChannelFees(new BigDecimal(arr[11]));
		if(arr.length == 13){
			clearingT1.setAccountType(arr[12]);
		}
		return clearingT1;
	}
	
	/** t1对账数据插入 */
	private void insertT1FileData(File t1File){
		try {
			if (t1File.exists()) {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(t1File));
				BufferedReader reader = new BufferedReader(new InputStreamReader(bis,"utf-8"),10*1024*1024);
				List<ClearingT1> t1List = new ArrayList<ClearingT1>();
				String line = "";
				int row = 0;
				while((line = reader.readLine()) != null){
					//组装数据
					++row;
					String [] arr = line.replace("\n", "").split("\\|");
					if(arr.length >= 12){
						ClearingT1 clearingT1 = this.buildClearingT1(arr);
						if(row == 1){
							ClearingT1Condition clearingT1Condition = new ClearingT1Condition();
							clearingT1Condition.setClearDate(clearingT1.getClearDate());
							clearingT1Condition.setTradeNo(clearingT1.getTradeNo());
							List<ClearingT1> list = clearingT1Service.findAll(clearingT1Condition);
							if(!list.isEmpty()){
								logger.error("t1对账文件数据数据库已存在" + JSONSerializer.toJSON(clearingT1));
								return ;
							}
						}
						t1List.add(clearingT1);
					}else{
						logger.error("t1对账文件数据" + row + "行异常：" + Arrays.asList(arr));
					}
					
					if(t1List.size()==1000){
						//批量保存数据
						clearingT1Service.batchInsert(t1List);
						t1List.clear();
						logger.info("t1对账文件数据插入到第" + row + "行");
					}
				}
				if(!t1List.isEmpty()){
					//最后不足一千条记录的数据
					clearingT1Service.batchInsert(t1List);
					t1List.clear();
					logger.info("t1对账文件数据最后一次插入到第" + row + "行");
				}
				reader.close();
				
			}else{
				logger.error("t1对账文件不存在:" + t1File);
			}
		} catch (Exception e) {
			logger.error("t1对账文件插入失败：" + e.getMessage());
		}
	}
	
	/** t1对账差错数据插入 */
	private void inserT1ErrData(List<ClearingT1Err> t1Err) {
		try {
			List<ClearingT1Err> t1ErrList = new ArrayList<ClearingT1Err>();
			int row = 0;
			for (ClearingT1Err clearingT1Err : t1Err) {
				++row;
				if(row == 1){
					ClearingT1ErrCondition clearingT1ErrCondition = new ClearingT1ErrCondition();
					clearingT1ErrCondition.setClearDate(clearingT1Err.getClearDate());
					clearingT1ErrCondition.setTradeNo(clearingT1Err.getTradeNo());
					List<ClearingT1Err> list = clearingT1ErrService.findAll(clearingT1ErrCondition);
					if(!list.isEmpty()){
						logger.error("t1对账差错数据数据库已存在" + JSONSerializer.toJSON(clearingT1Err));
						return ;
					}
				}
				clearingT1Err.setId(Strings.getUUID());
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+clearingT1Err.getMerchantNo()));
				if(merchantInfo != null){
					clearingT1Err.setChannelNo(merchantInfo.getChannelNo());
					clearingT1Err.setAgentNo(merchantInfo.getAgentNo());
				}else{
					CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
					cb.andEQ("merchantNo", clearingT1Err.getMerchantNo());
					Criteria buildCriteria = cb.buildCriteria();
					MerchantInfo merchant =  merchantInfoDAO.findOneByCriteria(buildCriteria);
					if(merchant != null){
						clearingT1Err.setChannelNo(merchant.getChannelNo());
						clearingT1Err.setAgentNo(merchant.getAgentNo());
					}
				}
				
				if(Strings.isEmpty(clearingT1Err.getBatchNo())){
					clearingT1Err.setBatchNo(BatchNoUtil.getBatchNo());
				}
				
				t1ErrList.add(clearingT1Err);
				if(t1ErrList.size()==1000){
					//批量保存数据
					clearingT1ErrService.batchInsert(t1ErrList);
					t1ErrList.clear();
					logger.info("t1对账差错数据插入到第" + row + "行");
				}
			}
			if(!t1ErrList.isEmpty()){
				//最后不足一千条记录的数据
				clearingT1ErrService.batchInsert(t1ErrList);
				t1ErrList.clear();
				logger.info("t1对账差错数据最后一次插入到第" + row + "行");
			}
		} catch (Exception e) {
			logger.error("t1对账差错数据插入失败：" + e.getMessage());
		}
		
	}
	
	
	/** 构建T0 */
	private ClearingT0 buildClearingT0(String [] arr){
		//示例
		//1701172058568490001|20170116090925|820800089374199808|201701120005|
		//819433407351230464|331715.00|330852.53|0.00|
		//862.47|0.50|20170116|20170116090926
		ClearingT0 clearingT0 = new ClearingT0();
		clearingT0.setId(Strings.getUUID());
		clearingT0.setBatchNo(arr[0]);
		clearingT0.setTradeNo(arr[1]);
		clearingT0.setHfTradeNo(arr[2]);
		clearingT0.setHfMerchantNo(arr[3]);
		clearingT0.setMerchantNo(arr[4]);
		clearingT0.setTransAmt(new BigDecimal(arr[5]));
		clearingT0.setArrivalAmt(new BigDecimal(arr[6]));
		clearingT0.setFees(new BigDecimal(arr[7]));
		clearingT0.setSingleFees(new BigDecimal(arr[8]));
		clearingT0.setChannelSingleFees(new BigDecimal(arr[9]));
		clearingT0.setClearDate(arr[10]);
		clearingT0.setTradeDate(arr[11]);
		
		return clearingT0;
	}
	
	/** t0对账数据插入 */
	private void insertT0FileData(File t0File){
		try {
			if (t0File.exists()) {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(t0File));
				BufferedReader reader = new BufferedReader(new InputStreamReader(bis,"utf-8"),10*1024*1024);
				List<ClearingT0> t0List = new ArrayList<ClearingT0>();
				String line = "";
				int row = 0;
				while((line = reader.readLine()) != null){
					//组装数据
					++row;
					String [] arr = line.replace("\n", "").split("\\|");
					if(arr.length >= 12){
						ClearingT0 clearingT0 = this.buildClearingT0(arr);
						if(row == 1){
							ClearingT0Condition clearingT0Condition = new ClearingT0Condition();
							clearingT0Condition.setClearDate(clearingT0.getClearDate());
							clearingT0Condition.setTradeNo(clearingT0.getTradeNo());
							List<ClearingT0> list = clearingT0Service.findAll(clearingT0Condition);
							if(!list.isEmpty()){
								logger.error("t0对账文件数据数据库已存在" + JSONSerializer.toJSON(clearingT0));
								return ;
							}
						}
						t0List.add(clearingT0);
					}else{
						logger.error("t0对账文件数据" + row + "行异常：" + Arrays.asList(arr));
					}
					
					if(t0List.size()==1000){
						//批量保存数据
						clearingT0Service.batchInsert(t0List);
						t0List.clear();
						logger.info("t0对账文件数据插入到第" + row + "行");
					}
				}
				if(!t0List.isEmpty()){
					//最后不足一千条记录的数据
					clearingT0Service.batchInsert(t0List);
					t0List.clear();
					logger.info("t0对账文件数据最后一次插入到第" + row + "行");
				}
				reader.close();
				
			}else{
				logger.error("t0对账文件不存在:" + t0File);
			}
		} catch (Exception e) {
			logger.error("t0对账文件插入失败：" + e.getMessage());
		}
	}
	
	/** t0对账差错数据插入 */
	private void inserT0ErrData(List<ClearingT0Err> t0Err) {
		try {
			List<ClearingT0Err> t0ErrList = new ArrayList<ClearingT0Err>();
			int row = 0;
			for (ClearingT0Err clearingT0Err : t0Err) {
				++row;
				if(row == 1){
					ClearingT0ErrCondition clearingT0ErrCondition = new ClearingT0ErrCondition();
					clearingT0ErrCondition.setClearDate(clearingT0Err.getClearDate());
					clearingT0ErrCondition.setTradeNo(clearingT0Err.getTradeNo());
					List<ClearingT0Err> list = clearingT0ErrService.findAll(clearingT0ErrCondition);
					if(!list.isEmpty()){
						logger.error("t0对账差错数据数据库已存在" + JSONSerializer.toJSON(clearingT0Err));
						return ;
					}
				}
				clearingT0Err.setId(Strings.getUUID());
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+clearingT0Err.getMerchantNo()));
				if(merchantInfo != null){
					clearingT0Err.setChannelNo(merchantInfo.getChannelNo());
					clearingT0Err.setAgentNo(merchantInfo.getAgentNo());
				}else{
					CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
					cb.andEQ("merchantNo", clearingT0Err.getMerchantNo());
					Criteria buildCriteria = cb.buildCriteria();
					MerchantInfo merchant =  merchantInfoDAO.findOneByCriteria(buildCriteria);
					if(merchant != null){
						clearingT0Err.setChannelNo(merchant.getChannelNo());
						clearingT0Err.setAgentNo(merchant.getAgentNo());
					}
				}
				
				if(Strings.isEmpty(clearingT0Err.getBatchNo())){
					clearingT0Err.setBatchNo(BatchNoUtil.getBatchNo());
				}
				
				t0ErrList.add(clearingT0Err);
				if(t0ErrList.size()==1000){
					//批量保存数据
					clearingT0ErrService.batchInsert(t0ErrList);
					t0ErrList.clear();
					logger.info("t0对账差错数据插入到第" + row + "行");
				}
			}
			if(!t0ErrList.isEmpty()){
				//最后不足一千条记录的数据
				clearingT0ErrService.batchInsert(t0ErrList);
				t0ErrList.clear();
				logger.info("t0对账差错数据最后一次插入到第" + row + "行");
			}
		} catch (Exception e) {
			logger.error("t0对账差错数据插入失败：" + e.getMessage());
		}
		
	}
}


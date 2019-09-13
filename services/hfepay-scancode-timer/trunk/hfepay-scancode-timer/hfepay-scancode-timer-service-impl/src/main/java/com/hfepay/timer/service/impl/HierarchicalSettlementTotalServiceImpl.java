/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.timer.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.dao.HierarchicalSettlementTotalDAO;
import com.hfepay.scancode.commons.dao.OrganProfitDAO;
import com.hfepay.scancode.commons.dao.OrganWalletDAO;
import com.hfepay.scancode.commons.dto.HierarchicalSettlementTotalDTO;
import com.hfepay.scancode.commons.entity.HierarchicalSettlementTotal;
import com.hfepay.timer.service.HierarchicalSettlementTotalService;
import com.hfepay.timer.service.utils.OrderIDUtils;


@Service("hierarchicalSettlementTotalService")
public class HierarchicalSettlementTotalServiceImpl implements HierarchicalSettlementTotalService {
	
	public static final Logger log = LoggerFactory.getLogger(HierarchicalSettlementTotalServiceImpl.class);
	
	@Autowired
    private HierarchicalSettlementTotalDAO hierarchicalSettlementTotalDAO;
	@Autowired
	private OrganWalletDAO organWalletDAO;
	@Autowired
	private OrganProfitDAO organProfitDAO;
	@Autowired
	private OrderIDUtils orderUtils;
    
	
	/**
	 * 根据利润表统计数据，同时更新钱包金额
	 * @Title: saveSummaryProfit
	 * @Description: TODO
	 * @param date
	 * @see com.hfepay.scancode.service.operator.HierarchicalSettlementTotalService#saveSummaryProfit(java.lang.String)
	 */
	@Override
	public void saveSummaryProfit(String date) {
		log.info("saveSummaryProfit执行开始："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		Map<String,HierarchicalSettlementTotal> map = new HashMap<>();
		String batchNo = orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "HST");
		Date createTime = new Date();
		//利润汇总
		List<HierarchicalSettlementTotalDTO> listDto = organProfitDAO.queryTotalProfit(date);
		for (HierarchicalSettlementTotalDTO dto : listDto) {
			String organNo = dto.getOrganNo();
			if(map.get(organNo)==null){//新的数据
				HierarchicalSettlementTotal total =new HierarchicalSettlementTotal();
				total.setBatchNo(batchNo);
				total.setSettleDate(date);
				total.setCreateTime(createTime);
				generateSettlement(total, dto);
				map.put(organNo, total);
			}
			else{
				HierarchicalSettlementTotal total = map.get(organNo);
				generateSettlementExist(total, dto);
			}
		}
		//将数据批量插入到汇总数据表中，批量插入
		List<HierarchicalSettlementTotal> list = new ArrayList<HierarchicalSettlementTotal>(map.values());
		hierarchicalSettlementTotalDAO.inserBatch(list);//批量插入到层级利润表
		//钱包金额修改
		organWalletDAO.updateBalanceToWallet(date);//修改钱包余额
		//将代理商的利润汇总添加到对应渠道
		organWalletDAO.updateBalanceToChannel(date);//修改钱包余额
		log.info("saveSummaryProfit执行结束："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
	}
	
	/**
	 * @Title: generateSettlementExist
	 * @Description: map中实体已存在，需要重新对原有实体进行改装
	 * @author: husain
	 * @param total
	 * @param dto
	 * @return: void
	 */
	private void generateSettlementExist(HierarchicalSettlementTotal total, HierarchicalSettlementTotalDTO dto) {
		BigDecimal totalAmount = dto.getTotalAmount()==null?new BigDecimal("0"):dto.getTotalAmount();
		BigDecimal totalProfit = dto.getTotalProfit()==null?new BigDecimal("0"):dto.getTotalProfit();
		if("1".equals(dto.getTradeType())){//交易
			total.setTranTotalAmount(totalAmount);
			total.setTranProfit(totalProfit);
		}else if("2".equals(dto.getTradeType())){//提现
			total.setOutTotalAmount(totalAmount);
			total.setOutProfit(totalProfit);
		}
		total.setTotalProfit(total.getTotalProfit().add(totalProfit));//累加，最终的利润总额
	}

	/**
	 * 
	 * @Title: generateSettlement
	 * @Description: 对于新添加到map的实体对象
	 * @author: husain
	 * @param total
	 * @param dto
	 * @return: void
	 */
	private void generateSettlement(HierarchicalSettlementTotal total,HierarchicalSettlementTotalDTO dto){
		total.setId(Strings.getUUID());
		total.setChannelNo(dto.getChannelNo());
		total.setAgentNo(dto.getAgentNo());
		total.setAgentLevel(dto.getAgentLevel());
		total.setParentNo(dto.getParentNo());
		BigDecimal totalAmount = dto.getTotalAmount()==null?new BigDecimal("0"):dto.getTotalAmount();
		BigDecimal totalProfit = dto.getTotalProfit()==null?new BigDecimal("0"):dto.getTotalProfit();
		if("1".equals(dto.getTradeType())){//交易
			total.setTranTotalAmount(totalAmount);
			total.setTranProfit(totalProfit);
		}else if("2".equals(dto.getTradeType())){//提现
			total.setOutTotalAmount(totalAmount);
			total.setOutProfit(totalProfit);
		}
		total.setTotalProfit(totalProfit);//如果不为空则累加，此处不是最终的利润总额，中间值而已
	}
}


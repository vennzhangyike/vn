package com.hfepay.scancode.service.operator.impl;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.scancode.commons.condition.BankcardNatureConfCondition;
import com.hfepay.scancode.commons.dao.BankcardNatureConfDAO;
import com.hfepay.scancode.commons.entity.BankcardNatureConf;
import com.hfepay.scancode.service.operator.BankcardNatureConfService;

@SuppressWarnings("rawtypes")
@Service("bankcardNatureConfService")
public class BankcardNatureConfServiceImpl implements BankcardNatureConfService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
    private BankcardNatureConfDAO bankcardNatureConfDAO;
	
	
	@Override
	public BankcardNatureConf invokeBankCardNature(String bankName,String bankCard,Map<Object, Object> confMap) throws Exception{
		Map<Object, Object> newConfMap = confMap;
		BankcardNatureConf resRecord = new BankcardNatureConf();
		 for ( Map.Entry entry : newConfMap.entrySet()) {
		    @SuppressWarnings("unused")
			String key = entry.getKey().toString();
		    BankcardNatureConf bankCardNatureConf = (BankcardNatureConf)entry.getValue();
		    //bankCard.indexOf(key)!=-1 && bankCardNatureConf.getAcountNum().equals(String.valueOf(bankCard.length()))
		    //匹配是否是同一个银行名称
		    if(bankCardNatureConf.getBankName().equals(bankName)){
		    	String mainAcount = bankCardNatureConf.getMainAcount();
		    	//String bankCardNatureConfName =bankCardNatureConf.getBankName();
		    	for(int i=0;i<=mainAcount.length()-1;i++) {  
		              String getstr=mainAcount.substring(i,i+1);  
		              if(getstr.equalsIgnoreCase("X")){  
		            	  String bankCardBin = bankCard.substring(0, i);
		            	  String mainAcountBin = mainAcount.substring(0, i);
		            	  //前6位匹配，以及银行名称匹配
		            	  if(bankCardBin.equals(mainAcountBin)){
		            		  BeanUtils.copyProperties(bankCardNatureConf, resRecord);
		            		  resRecord.setMainAcount(bankCard);
		            	  }
		            	  break;
		              }  
		          }  
		    	if(resRecord.getMainAcount()!=null){
		    		break;
		    	}
		    }
		 }
		 logger.info("***************银行卡属性*卡bin的Map长度："+confMap.size());
		 if(confMap.isEmpty()){
			 logger.info("***************银行卡属性*卡bin数据是空的");
		 }
		 if(resRecord.getMainAcount()==null){
			 logger.info("***************银行卡属性*resRecord==null");
			 throw new Exception();
		 }
		return resRecord;
	}
	
	
	/**
	 * 列表
	 * @param bankcardNatureConf 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-26 13:55:39
	 */
	@Override
	public List<BankcardNatureConf> findAll(BankcardNatureConfCondition bankcardNatureConfCondition){
		CriteriaBuilder cb = Cnd.builder(BankcardNatureConf.class);
		if(!Strings.isEmpty(bankcardNatureConfCondition.getId())){
			cb.andEQ("id", bankcardNatureConfCondition.getId());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getBankName())){
			cb.andEQ("bankName", bankcardNatureConfCondition.getBankName());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getBankCode())){
			cb.andEQ("bankCode", bankcardNatureConfCondition.getBankCode());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getBankCardName())){
			cb.andEQ("bankCardName", bankcardNatureConfCondition.getBankCardName());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getAtm())){
			cb.andEQ("atm", bankcardNatureConfCondition.getAtm());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getPos())){
			cb.andEQ("pos", bankcardNatureConfCondition.getPos());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getTrack())){
			cb.andEQ("track", bankcardNatureConfCondition.getTrack());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getTrackStartChar())){
			cb.andEQ("trackStartChar", bankcardNatureConfCondition.getTrackStartChar());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getTrackNum())){
			cb.andEQ("trackNum", bankcardNatureConfCondition.getTrackNum());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getAcountStartChar())){
			cb.andEQ("acountStartChar", bankcardNatureConfCondition.getAcountStartChar());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getAcountNum())){
			cb.andEQ("acountNum", bankcardNatureConfCondition.getAcountNum());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getMainAcount())){
			cb.andEQ("mainAcount", bankcardNatureConfCondition.getMainAcount());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getAcountReadTrack())){
			cb.andEQ("acountReadTrack", bankcardNatureConfCondition.getAcountReadTrack());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getIdentStartChar())){
			cb.andEQ("identStartChar", bankcardNatureConfCondition.getIdentStartChar());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getIdentNum())){
			cb.andEQ("identNum", bankcardNatureConfCondition.getIdentNum());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getIdentValue())){
			cb.andEQ("identValue", bankcardNatureConfCondition.getIdentValue());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getIdentReadTrack())){
			cb.andEQ("identReadTrack", bankcardNatureConfCondition.getIdentReadTrack());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getBankCardType())){
			cb.andEQ("bankCardType", bankcardNatureConfCondition.getBankCardType());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getAddCut())){
			cb.andEQ("addCut", bankcardNatureConfCondition.getAddCut());
		}
		if(!Strings.isEmpty(bankcardNatureConfCondition.getTelePhone())){
			cb.andEQ("telePhone", bankcardNatureConfCondition.getTelePhone());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return bankcardNatureConfDAO.findByCriteria(buildCriteria);
	}
}


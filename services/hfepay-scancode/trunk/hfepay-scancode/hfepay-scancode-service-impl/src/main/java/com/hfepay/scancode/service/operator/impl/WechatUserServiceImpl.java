/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.WechatUserCondition;
import com.hfepay.scancode.commons.dao.WechatUserDAO;
import com.hfepay.scancode.commons.entity.WechatUser;
import com.hfepay.scancode.service.operator.WechatUserService;

@Service("wechatUserService")
public class WechatUserServiceImpl implements WechatUserService {
	
	@Autowired
    private WechatUserDAO wechatUserDAO;
    
    /**
	 * 列表(分页)
	 * @param wechatUserCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
    @Override
	public PagingResult<WechatUser> findPagingResult(WechatUserCondition wechatUserCondition){
		CriteriaBuilder cb = Cnd.builder(WechatUser.class);
		if(!Strings.isEmpty(wechatUserCondition.getId())){
			cb.andEQ("id", wechatUserCondition.getId());
		}
		if(!Strings.isEmpty(wechatUserCondition.getIdentityNo())){
			cb.andEQ("identityNo", wechatUserCondition.getIdentityNo());
		}
		if(!Strings.isEmpty(wechatUserCondition.getUserType())){
			cb.andEQ("userType", wechatUserCondition.getUserType());
		}
		if(!Strings.isEmpty(wechatUserCondition.getSubscribe())){
			cb.andEQ("subscribe", wechatUserCondition.getSubscribe());
		}
		if(!Strings.isEmpty(wechatUserCondition.getOpenid())){
			cb.andEQ("openid", wechatUserCondition.getOpenid());
		}
		if(!Strings.isEmpty(wechatUserCondition.getNickname())){
			cb.andEQ("nickname", wechatUserCondition.getNickname());
		}
		if(!Strings.isEmpty(wechatUserCondition.getSex())){
			cb.andEQ("sex", wechatUserCondition.getSex());
		}
		if(!Strings.isEmpty(wechatUserCondition.getCity())){
			cb.andEQ("city", wechatUserCondition.getCity());
		}
		if(!Strings.isEmpty(wechatUserCondition.getCountry())){
			cb.andEQ("country", wechatUserCondition.getCountry());
		}
		if(!Strings.isEmpty(wechatUserCondition.getProvince())){
			cb.andEQ("province", wechatUserCondition.getProvince());
		}
		if(!Strings.isEmpty(wechatUserCondition.getLanguage())){
			cb.andEQ("language", wechatUserCondition.getLanguage());
		}
		if(!Strings.isEmpty(wechatUserCondition.getHeadimgurl())){
			cb.andEQ("headimgurl", wechatUserCondition.getHeadimgurl());
		}
		if(null != wechatUserCondition.getSubscribeTime()){
			cb.andEQ("subscribeTime", wechatUserCondition.getSubscribeTime());
		}
		if(!Strings.isEmpty(wechatUserCondition.getUnionid())){
			cb.andEQ("unionid", wechatUserCondition.getUnionid());
		}

		if(!Strings.isEmpty(wechatUserCondition.getRemark())){
			cb.andLike("remark", wechatUserCondition.getRemark());
		}
		if(!Strings.isEmpty(wechatUserCondition.getGroupid())){
			cb.andEQ("groupid", wechatUserCondition.getGroupid());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTagidList())){
			cb.andEQ("tagidList", wechatUserCondition.getTagidList());
		}
		if(null != wechatUserCondition.getCreateTime()){
			cb.andEQ("createTime", wechatUserCondition.getCreateTime());
		}

		if(!Strings.isEmpty(wechatUserCondition.getTemp1())){
			cb.andEQ("temp1", wechatUserCondition.getTemp1());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTemp2())){
			cb.andEQ("temp2", wechatUserCondition.getTemp2());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTemp3())){
			cb.andEQ("temp3", wechatUserCondition.getTemp3());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTemp4())){
			cb.andEQ("temp4", wechatUserCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(wechatUserCondition.getFirst()), Long.valueOf(wechatUserCondition.getLast()));
		return wechatUserDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param wechatUser 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	@Override
	public List<WechatUser> findAll(WechatUserCondition wechatUserCondition){
		CriteriaBuilder cb = Cnd.builder(WechatUser.class);
		if(!Strings.isEmpty(wechatUserCondition.getId())){
			cb.andEQ("id", wechatUserCondition.getId());
		}
		if(!Strings.isEmpty(wechatUserCondition.getIdentityNo())){
			cb.andEQ("identityNo", wechatUserCondition.getIdentityNo());
		}
		if(!Strings.isEmpty(wechatUserCondition.getUserType())){
			cb.andEQ("userType", wechatUserCondition.getUserType());
		}
		if(!Strings.isEmpty(wechatUserCondition.getSubscribe())){
			cb.andEQ("subscribe", wechatUserCondition.getSubscribe());
		}
		if(!Strings.isEmpty(wechatUserCondition.getOpenid())){
			cb.andEQ("openid", wechatUserCondition.getOpenid());
		}
		if(!Strings.isEmpty(wechatUserCondition.getNickname())){
			cb.andEQ("nickname", wechatUserCondition.getNickname());
		}
		if(!Strings.isEmpty(wechatUserCondition.getSex())){
			cb.andEQ("sex", wechatUserCondition.getSex());
		}
		if(!Strings.isEmpty(wechatUserCondition.getCity())){
			cb.andEQ("city", wechatUserCondition.getCity());
		}
		if(!Strings.isEmpty(wechatUserCondition.getCountry())){
			cb.andEQ("country", wechatUserCondition.getCountry());
		}
		if(!Strings.isEmpty(wechatUserCondition.getProvince())){
			cb.andEQ("province", wechatUserCondition.getProvince());
		}
		if(!Strings.isEmpty(wechatUserCondition.getLanguage())){
			cb.andEQ("language", wechatUserCondition.getLanguage());
		}
		if(!Strings.isEmpty(wechatUserCondition.getHeadimgurl())){
			cb.andEQ("headimgurl", wechatUserCondition.getHeadimgurl());
		}
		if(null != wechatUserCondition.getSubscribeTime()){
			cb.andEQ("subscribeTime", wechatUserCondition.getSubscribeTime());
		}
		if(!Strings.isEmpty(wechatUserCondition.getUnionid())){
			cb.andEQ("unionid", wechatUserCondition.getUnionid());
		}

		if(!Strings.isEmpty(wechatUserCondition.getRemark())){
			cb.andLike("remark", wechatUserCondition.getRemark());
		}
		if(!Strings.isEmpty(wechatUserCondition.getGroupid())){
			cb.andEQ("groupid", wechatUserCondition.getGroupid());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTagidList())){
			cb.andEQ("tagidList", wechatUserCondition.getTagidList());
		}
		if(null != wechatUserCondition.getCreateTime()){
			cb.andEQ("createTime", wechatUserCondition.getCreateTime());
		}

		if(!Strings.isEmpty(wechatUserCondition.getTemp1())){
			cb.andEQ("temp1", wechatUserCondition.getTemp1());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTemp2())){
			cb.andEQ("temp2", wechatUserCondition.getTemp2());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTemp3())){
			cb.andEQ("temp3", wechatUserCondition.getTemp3());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTemp4())){
			cb.andEQ("temp4", wechatUserCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return wechatUserDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	@Override
	public WechatUser findById(String id){
		return wechatUserDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param wechatUserCondition
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	@Override
	public long insert(WechatUserCondition wechatUserCondition){
		WechatUser wechatUser = new WechatUser();
		BeanUtils.copyProperties(wechatUserCondition, wechatUser);
		if(Strings.isEmpty(wechatUserCondition.getId())){
			wechatUser.setId(Strings.getUUID());
		}
		return wechatUserDAO.insert(wechatUser);
	}

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	@Override
	public long deleteById(String id){
		return wechatUserDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return wechatUserDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return wechatUserDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	@Override
	public long update(WechatUserCondition wechatUserCondition){
		WechatUser wechatUser = new WechatUser();
		BeanUtils.copyProperties(wechatUserCondition, wechatUser);
		return wechatUserDAO.update(wechatUser);
	}
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	@Override
	public long updateByCriteria(WechatUserCondition wechatUserCondition,Criteria criteria){
		WechatUser wechatUser = new WechatUser();
		BeanUtils.copyProperties(wechatUserCondition, wechatUser);
		return wechatUserDAO.updateByCriteria(wechatUser,criteria);
	}
	
	/**
	 * 单个实体对象
	 * @param wechatUserCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-03 15:54:28
	 */
	@Override
	public WechatUser findByCondition(WechatUserCondition wechatUserCondition){
		CriteriaBuilder cb = Cnd.builder(WechatUser.class);
		if(!Strings.isEmpty(wechatUserCondition.getId())){
			cb.andEQ("id", wechatUserCondition.getId());
		}
		if(!Strings.isEmpty(wechatUserCondition.getIdentityNo())){
			cb.andEQ("identityNo", wechatUserCondition.getIdentityNo());
		}
		if(!Strings.isEmpty(wechatUserCondition.getUserType())){
			cb.andEQ("userType", wechatUserCondition.getUserType());
		}
		if(!Strings.isEmpty(wechatUserCondition.getSubscribe())){
			cb.andEQ("subscribe", wechatUserCondition.getSubscribe());
		}
		if(!Strings.isEmpty(wechatUserCondition.getOpenid())){
			cb.andEQ("openid", wechatUserCondition.getOpenid());
		}
		if(!Strings.isEmpty(wechatUserCondition.getNickname())){
			cb.andEQ("nickname", wechatUserCondition.getNickname());
		}
		if(!Strings.isEmpty(wechatUserCondition.getSex())){
			cb.andEQ("sex", wechatUserCondition.getSex());
		}
		if(!Strings.isEmpty(wechatUserCondition.getCity())){
			cb.andEQ("city", wechatUserCondition.getCity());
		}
		if(!Strings.isEmpty(wechatUserCondition.getCountry())){
			cb.andEQ("country", wechatUserCondition.getCountry());
		}
		if(!Strings.isEmpty(wechatUserCondition.getProvince())){
			cb.andEQ("province", wechatUserCondition.getProvince());
		}
		if(!Strings.isEmpty(wechatUserCondition.getLanguage())){
			cb.andEQ("language", wechatUserCondition.getLanguage());
		}
		if(!Strings.isEmpty(wechatUserCondition.getHeadimgurl())){
			cb.andEQ("headimgurl", wechatUserCondition.getHeadimgurl());
		}
		if(null != wechatUserCondition.getSubscribeTime()){
			cb.andEQ("subscribeTime", wechatUserCondition.getSubscribeTime());
		}
		if(!Strings.isEmpty(wechatUserCondition.getUnionid())){
			cb.andEQ("unionid", wechatUserCondition.getUnionid());
		}

		if(!Strings.isEmpty(wechatUserCondition.getRemark())){
			cb.andLike("remark", wechatUserCondition.getRemark());
		}
		if(!Strings.isEmpty(wechatUserCondition.getGroupid())){
			cb.andEQ("groupid", wechatUserCondition.getGroupid());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTagidList())){
			cb.andEQ("tagidList", wechatUserCondition.getTagidList());
		}
		if(null != wechatUserCondition.getCreateTime()){
			cb.andEQ("createTime", wechatUserCondition.getCreateTime());
		}

		if(!Strings.isEmpty(wechatUserCondition.getTemp1())){
			cb.andEQ("temp1", wechatUserCondition.getTemp1());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTemp2())){
			cb.andEQ("temp2", wechatUserCondition.getTemp2());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTemp3())){
			cb.andEQ("temp3", wechatUserCondition.getTemp3());
		}
		if(!Strings.isEmpty(wechatUserCondition.getTemp4())){
			cb.andEQ("temp4", wechatUserCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return wechatUserDAO.findOneByCriteria(buildCriteria);
	}
}


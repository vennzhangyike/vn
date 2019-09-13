package com.hfepay.scancode.commons.dao.impl.channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.channel.ChannelAdminDAO;
import com.hfepay.scancode.commons.entity.ChannelAdmin;

@org.springframework.stereotype.Repository

    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public class ChannelAdminDAOImpl extends MybatisEntityDAO<ChannelAdmin, String> implements ChannelAdminDAO {

	/**
	 * 状态更新
	 */
	@Override
	public long updateStatus(String id,Integer status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlSession().update(sqlId("updateStatus"),map);
	}

	@Override
	public ChannelAdmin findByMerchantNoByIdentity(String merchantNo, String identityFlag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		map.put("identityFlag", identityFlag);
		return getSqlSession().selectOne("findByMerchantNoByIdentity", map);
	}
	@Override
	public List<ChannelAdmin> findUnregsit(int start,int end) {
		Map<String,Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("end", end);
		return getSqlSession().selectList(sqlId("findUnregsit"),map);
	}
	@Override
	public int countUnregsit() {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(sqlId("countUnregsit"));
	}
	
	@Override
	public List<ChannelAdmin> findMerchantInfoUnperfect(Map<String, Object> map) {		
		return getSqlSession().selectList(sqlId("findMerchantInfoUnperfect"),map);
	}
	
	@Override
	public ChannelAdmin findPushMsgAdmin(String merchantNo,boolean isMerchant) {
		Map<String,String> map = new HashMap<>();
		map.put("merchantNo", merchantNo);
		if(isMerchant){
			map.put("isMerchant", "yes");
		}
		return getSqlSession().selectOne(sqlId("findPushMsgAdmin"),map);
	}

	@Override
	public ChannelAdmin findByMerchantNoByIdentityGetOpenId(String merchantNo, String identityFlag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		map.put("identityFlag", identityFlag);
		return getSqlSession().selectOne("findByMerchantNoByIdentityGetOpenId", map);
	}
	
	@Override
	public ChannelAdmin findByIdentityNo(String identityNo) {
		
		return getSqlSession().selectOne("findByIdentityNo", identityNo);
	}
}
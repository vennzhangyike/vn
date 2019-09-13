/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.NodeRelationDAO;
import com.hfepay.scancode.commons.entity.NodeRelation;

@Repository(value="nodeRelationDAO")
@Generated("2017-01-22 15:16:03")
public class NodeRelationDAOImpl extends MybatisEntityDAO<NodeRelation, String> implements NodeRelationDAO {

	@Override
	public long deleteByCurrentNode(String currentNode) {
		return this.getSqlSession().delete(sqlId("deleteByCurrentNode"),currentNode);
	}

	@Override
	public List<NodeRelation> getParentsByCurrentNode(List<String> parentsSeq, String excludeIdentityFlag) {
		Map<String,Object> map = new HashMap<>();
		map.put("parentsSeq", parentsSeq);
		map.put("excludeIdentityFlag",excludeIdentityFlag );
		return this.getSqlSession().selectList(sqlId("getParentsByCurrentNode"),map);
	}

	@Override
	public List<NodeRelation> getChildrenByCurrentNode(String seq, String excludeIdentityFlag,Integer length,boolean include) {
		Map<String,Object> map = new HashMap<>();
		map.put("nodeSeq", seq);
		map.put("excludeIdentityFlag",excludeIdentityFlag);
		map.put("seqLength",length);
		if(!include){
			map.put("include","no");
		}
		return this.getSqlSession().selectList(sqlId("getChildrenByCurrentNode"),map);
	}
	
	@Override
	public void insertBatch(List<NodeRelation> list) {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(sqlId("insertBatch"),list);
	}
	
	@Override
	public void updateNextSeqByCurrentNode(String key, String nextSeq) {
		Map<String,String> map = new HashMap<>();
		map.put("currentNode", key);
		map.put("nextNodeSeq", nextSeq);
		this.getSqlSession().update(sqlId("updateNextSeqByCurrentNode"),map);
	}
}
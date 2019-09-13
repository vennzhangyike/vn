/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.NodeRelation;

@Generated("2017-01-22 15:16:03")
public interface NodeRelationDAO extends EntityDAO<NodeRelation, String> {

	long deleteByCurrentNode(String currentNode);

	/**
	 * 当前节点查找父节点
	 * @param parentsSeq 父节点seq List
	 * @param excludeIdentityFlag 要排除的节点标示，如不需要找到渠道，那么excludeIdentityFlag=1
	 * @author husain
	 * @return
	 */
	public List<NodeRelation> getParentsByCurrentNode(List<String> parentsSeq,String excludeIdentityFlag);
	
	/**
	 * 当前节点查找子节点
	 * @param seq 当前节点seq
	 * @param excludeIdentityFlag 要排除的节点标示，如不需要找商户，那么excludeIdentityFlag=3
	 * @param include 包含自身
	 * @param seqLength 直接父节点需要长度限制
	 * @author husain
	 * @return
	 */
	public List<NodeRelation> getChildrenByCurrentNode(String seq,String excludeIdentityFlag,Integer seqLength,boolean include);

	//批量插入
	void insertBatch(List<NodeRelation> list);

	void updateNextSeqByCurrentNode(String key, String nextSeq);

}

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.entity.NodeRelation;

public interface NodeRelationService {
	
	/**
	 * 列表(分页)
	 * @param nodeRelationCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	public PagingResult<NodeRelation> findPagingResult(NodeRelationCondition nodeRelationCondition);
	
	/**
	 * 列表
	 * @param nodeRelation 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	public List<NodeRelation> findAll(NodeRelationCondition nodeRelationCondition);
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	NodeRelation findById(String id);
	
	/**
	 * 新增
	 * @param nodeRelationCondition
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	long insert(NodeRelationCondition nodeRelationCondition);

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	long update(NodeRelationCondition nodeRelationCondition);
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	long updateByCriteria(NodeRelationCondition nodeRelationCondition,Criteria criteria);
	
	/**
	 * 单个实体对象
	 * @param nodeRelationCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	public NodeRelation findByCondition(NodeRelationCondition nodeRelationCondition);

	/**
	 * 保存节点关系
	 * @param 删除节点关系 
	 * @author husain
	 */
	public long deleteByCurrentNode(NodeRelationCondition dataNodeCondition);
	
	/**
	 * 当前节点查找父节点
	 * @param currentNode 当前节点
	 * @param excludeIdentityFlag 要排除的节点标示，如不需要找到渠道，那么excludeIdentityFlag=1
	 * @param isDirect 是否是直接父接点（true seq=substring(长度为自身seq长度-4)；false 迭代自身seq找出父节点）
	 * @param isIncludeSelf是否包含自身
	 * @author husain
	 * @return
	 */
	public List<NodeRelation> getParentsByCurrentNode(String currentNode,String excludeIdentityFlag,boolean isDirect,boolean isIncludeSelf);
	
	/**
	 * 当前节点查找子节点
	 * @param currentNode 当前节点
	 * @param excludeIdentityFlag 要排除的节点标示，如不需要找商户，那么excludeIdentityFlag=3
	 * @param isDirect 是否是直接子接点（true 直接直接点，seq长度为自身seq长度+4；false 直接like自身seq即可）
	 * @param isIncludeSelf是否包含自身
	 * @author husain
	 * @return
	 */
	public List<NodeRelation> getChildrenByCurrentNode(String currentNode,String excludeIdentityFlag,boolean isDirect,boolean isIncludeSelf);
	
	/**
	 * 根据currentNode查找
	 * @param currentNode
	 * @author husain
	 * @return
	 */
	public NodeRelation findByCurrentNode(String currentNode);
	
	/**
	 * 获取当前节点的seq
	 * @param currentNode
	 * @return
	 */
	public String getSeq(String currentNode);
	
}


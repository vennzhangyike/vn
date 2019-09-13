/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.channel;

public interface DataNodeService {
	
	/**
	 * 列表(分页)
	 * @param dataNodeCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	public PagingResult<DataNode> findPagingResult(DataNodeCondition dataNodeCondition);
	
	*//**
	 * 列表
	 * @param dataNode 
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	public List<DataNode> findAll(DataNodeCondition dataNodeCondition);
	
	*//**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	DataNode findById(String id);
	
	*//**
	 * 根据当前节点查询
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	public List<DataNode> findByCurrentNode(String currentNode);
	
	*//**
	 * 新增
	 * @param dataNodeCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	long insert(DataNodeCondition dataNodeCondition);

	*//**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	long deleteById(String id);
	
	*//**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	long deleteByCriteria(Criteria criteria);
	
	*//**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	long countByCriteria(Criteria criteria);
	
	*//**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	long update(DataNodeCondition dataNodeCondition);
	
	*//**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	long updateByCriteria(DataNodeCondition dataNodeCondition,Criteria criteria);

	*//**
	 * 
	 * @Title: doSaveNodeRelations
	 * @Description: 通过传入的条件批量保存(必需参数:identityFlag,currentNode,parentNode，operator)
	 * @author: husain
	 * @param dCondition
	 * @return: void
	 *//*
	public void doSaveNodeRelations(DataNodeCondition dCondition);

	*//**
	 * @Title: deleteByCurrentNode
	 * @Description: 删除父子关系，
	 * @author: husain
	 * @param dCondition
	 * @return: void
	 *//*
	public void deleteByCurrentNode(DataNodeCondition dCondition);

	*//**
	 * 
	 * @Title: findParents
	 * @Description: 查出当前节点的父级节点
	 * @author: husain
	 * @param agentNo
	 * @return
	 * @return: List<DataNode>
	 *//*
	public List<DataNode> findParents(String agentNo);*/
	
	
}


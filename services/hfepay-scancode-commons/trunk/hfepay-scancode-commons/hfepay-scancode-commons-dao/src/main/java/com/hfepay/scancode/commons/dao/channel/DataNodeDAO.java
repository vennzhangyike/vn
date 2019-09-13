/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.channel;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.scancode.commons.entity.DataNode;

@Generated("2016-10-18 13:53:00")
public interface DataNodeDAO/* extends EntityDAO<DataNode, String>*/ {

	/**
	 * @Title: batchInsert
	 * @Description: 级联关系批量插入
	 * @author: husain
	 * @param insertList
	 * @return: void
	 */
	void batchInsert(List<DataNode> insertList);

	/**
	 * @Title: deleteByCurrentNode
	 * @Description: 根据currentNode删除节点
	 * @author: husain
	 * @param currentNode
	 * @return: void
	 */
	void deleteByCurrentNode(String currentNode);
	
	/**
	 * @Title: findByCurrentNode
	 * @Description: 根据currentNode删除节点
	 * @author: husain
	 * @param currentNode
	 * @return: void
	 */
	List<DataNode> findByCurrentNode(String currentNode);

	/**
	 * @Title: findParents
	 * @Description: 查询父节点
	 * @author: husain
	 * @param agentNo
	 * @return
	 * @return: List<DataNode>
	 */
	List<DataNode> findParents(String agentNo);
}

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl.channel;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.scancode.commons.dao.channel.DataNodeDAO;
import com.hfepay.scancode.commons.entity.DataNode;

@Repository(value="dataNodeDAO")
@Generated("2016-10-18 13:53:00")
public class DataNodeDAOImpl /*extends MybatisEntityDAO<DataNode, String>*/ implements DataNodeDAO {

	@Override
	public void batchInsert(List<DataNode> insertList) {
		//this.getSqlSession().insert(sqlId("batchInsert"), insertList);
		
	}

	@Override
	public void deleteByCurrentNode(String currentNode) {
		//this.getSqlSession().insert(sqlId("deleteByCurrentNode"), currentNode);
	}

	@Override
	public List<DataNode> findByCurrentNode(String currentNode) {
		//return this.getSqlSession().selectList(sqlId("findByCurrentNode"), currentNode);
		return null;
	}

	@Override
	public List<DataNode> findParents(String agentNo) {
		// TODO Auto-generated method stub
		//return this.getSqlSession().selectList(sqlId("findParents"), agentNo);
		return null;
	}
	
	
}
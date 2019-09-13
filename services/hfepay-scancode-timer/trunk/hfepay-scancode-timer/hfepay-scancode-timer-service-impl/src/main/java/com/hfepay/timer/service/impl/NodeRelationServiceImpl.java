/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.NodeRelationDAO;
import com.hfepay.scancode.commons.entity.NodeRelation;
import com.hfepay.timer.service.NodeRelationService;

@Service("nodeRelationService")
public class NodeRelationServiceImpl implements NodeRelationService {
	public static final Logger log = LoggerFactory.getLogger(ProfitServiceImpl.class);
	@Autowired
    private NodeRelationDAO nodeRelationDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
	
    /**
	 * 列表(分页)
	 * @param nodeRelationCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
    @Override
	public PagingResult<NodeRelation> findPagingResult(NodeRelationCondition nodeRelationCondition){
		CriteriaBuilder cb = Cnd.builder(NodeRelation.class);
		if(!Strings.isEmpty(nodeRelationCondition.getId())){
			cb.andEQ("id", nodeRelationCondition.getId());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getCurrentNode())){
			cb.andEQ("currentNode", nodeRelationCondition.getCurrentNode());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getParentNode())){
			cb.andEQ("parentNode", nodeRelationCondition.getParentNode());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getCurrentNodeLevel())){
			cb.andEQ("currentNodeLevel", nodeRelationCondition.getCurrentNodeLevel());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getIdentityFlag())){
			cb.andEQ("identityFlag", nodeRelationCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getNodeSeq())){
			cb.andEQ("nodeSeq", nodeRelationCondition.getNodeSeq());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getNextNodeSeq())){
			cb.andEQ("nextNodeSeq", nodeRelationCondition.getNextNodeSeq());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getOperator())){
			cb.andEQ("operator", nodeRelationCondition.getOperator());
		}
		if(null != nodeRelationCondition.getCreateTime()){
			cb.andEQ("createTime", nodeRelationCondition.getCreateTime());
		}

		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(nodeRelationCondition.getFirst()), Long.valueOf(nodeRelationCondition.getLast()));
		return nodeRelationDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param nodeRelation 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	@Override
	public List<NodeRelation> findAll(NodeRelationCondition nodeRelationCondition){
		CriteriaBuilder cb = Cnd.builder(NodeRelation.class);
		if(!Strings.isEmpty(nodeRelationCondition.getId())){
			cb.andEQ("id", nodeRelationCondition.getId());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getCurrentNode())){
			cb.andEQ("currentNode", nodeRelationCondition.getCurrentNode());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getParentNode())){
			cb.andEQ("parentNode", nodeRelationCondition.getParentNode());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getCurrentNodeLevel())){
			cb.andEQ("currentNodeLevel", nodeRelationCondition.getCurrentNodeLevel());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getIdentityFlag())){
			cb.andEQ("identityFlag", nodeRelationCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getNodeSeq())){
			cb.andEQ("nodeSeq", nodeRelationCondition.getNodeSeq());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getNextNodeSeq())){
			cb.andEQ("nextNodeSeq", nodeRelationCondition.getNextNodeSeq());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getOperator())){
			cb.andEQ("operator", nodeRelationCondition.getOperator());
		}
		if(null != nodeRelationCondition.getCreateTime()){
			cb.andEQ("createTime", nodeRelationCondition.getCreateTime());
		}

		Criteria buildCriteria = cb.buildCriteria();
		return nodeRelationDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	@Override
	public NodeRelation findById(String id){
		return nodeRelationDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param nodeRelationCondition
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	@Override
	public long insert(NodeRelationCondition nodeRelationCondition){
		NodeRelation nodeRelation = new NodeRelation();
		BeanUtils.copyProperties(nodeRelationCondition, nodeRelation);
		if(Strings.isEmpty(nodeRelationCondition.getId())){
			nodeRelation.setId(Strings.getUUID());
		}
		return nodeRelationDAO.insert(nodeRelation);
	}

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	@Override
	public long deleteById(String id){
		return nodeRelationDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return nodeRelationDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return nodeRelationDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	@Override
	public long update(NodeRelationCondition nodeRelationCondition){
		NodeRelation nodeRelation = new NodeRelation();
		BeanUtils.copyProperties(nodeRelationCondition, nodeRelation);
		return nodeRelationDAO.update(nodeRelation);
	}
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	@Override
	public long updateByCriteria(NodeRelationCondition nodeRelationCondition,Criteria criteria){
		NodeRelation nodeRelation = new NodeRelation();
		BeanUtils.copyProperties(nodeRelationCondition, nodeRelation);
		return nodeRelationDAO.updateByCriteria(nodeRelation,criteria);
	}
	
	/**
	 * 单个实体对象
	 * @param nodeRelationCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	@Override
	public NodeRelation findByCondition(NodeRelationCondition nodeRelationCondition){
		CriteriaBuilder cb = Cnd.builder(NodeRelation.class);
		if(!Strings.isEmpty(nodeRelationCondition.getId())){
			cb.andEQ("id", nodeRelationCondition.getId());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getCurrentNode())){
			cb.andEQ("currentNode", nodeRelationCondition.getCurrentNode());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getParentNode())){
			cb.andEQ("parentNode", nodeRelationCondition.getParentNode());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getCurrentNodeLevel())){
			cb.andEQ("currentNodeLevel", nodeRelationCondition.getCurrentNodeLevel());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getIdentityFlag())){
			cb.andEQ("identityFlag", nodeRelationCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getNodeSeq())){
			cb.andEQ("nodeSeq", nodeRelationCondition.getNodeSeq());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getNextNodeSeq())){
			cb.andEQ("nextNodeSeq", nodeRelationCondition.getNextNodeSeq());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getOperator())){
			cb.andEQ("operator", nodeRelationCondition.getOperator());
		}
		if(null != nodeRelationCondition.getCreateTime()){
			cb.andEQ("createTime", nodeRelationCondition.getCreateTime());
		}

		Criteria buildCriteria = cb.buildCriteria();
		return nodeRelationDAO.findOneByCriteria(buildCriteria);
	}
	
	
	/**必要参数的校验**/
	private void validateParam(NodeRelationCondition dCondition){
		if(Strings.isEmpty(dCondition.getIdentityFlag())){
			throw new RuntimeException("节点类别IdentityFlag不能为空");
		}
		if(Strings.isEmpty(dCondition.getCurrentNode())){
			throw new RuntimeException("当前节点的值CurrentNode不能为空");
		}
		if(Strings.isEmpty(dCondition.getParentNode())){
			throw new RuntimeException("当前节点父节点ParentNode不能为空");
		}
		if(Strings.isEmpty(dCondition.getOperator())){
			throw new RuntimeException("操作人Operator不能为空");
		}
	}

	@Override
	public long deleteByCurrentNode(NodeRelationCondition dataNodeCondition) {
		return nodeRelationDAO.deleteByCurrentNode(dataNodeCondition.getCurrentNode());
	}

	@Override
	public List<NodeRelation> getParentsByCurrentNode(String currentNode, String excludeIdentityFlag,boolean isDirect,boolean isIncludeSelf) {
		List<String> parentsSeq = new ArrayList<>();
		String seq = getSeq(currentNode);
		int length = seq.length();
		if(length==4){//渠道不存在父节点
			return null;
		}
		if(isDirect){//直接父节点为子节点的开头到倒数第五位，默认四位为子节点变化位
			parentsSeq.add(seq.substring(0, length-4));
		}else{//所有父节点
			int level = length/4;
			for (int i = 1; i < level; i++) {
				parentsSeq.add(seq.substring(0,4*i));
			}
		}
		if(isIncludeSelf){//包含自身
			parentsSeq.add(seq);
		}
		return nodeRelationDAO.getParentsByCurrentNode(parentsSeq,excludeIdentityFlag);
	}
	@Override
	public String getSeq(String currentNode){
		RedisKey key = new RedisKey(RedisKeyEnum.ORGANNO_SEQ.getGroup(), RedisKeyEnum.ORGANNO_SEQ.getKey()+currentNode);
		String seq = null;
		try {
			seq = redisSharedCache.get(key);
			int length = 0;
			if(Strings.isEmpty(seq)){
				NodeRelation node = findByCurrentNode(currentNode);
				if(node==null||Strings.isEmpty(node.getNodeSeq())){
					return null;
				}
				seq = node.getNodeSeq();
				length = seq.length();//seq的长度必须大于=4且是4的倍数
				if(length<4||length%4!=0){
					throw new RuntimeException("seq长度有问题，请检查....");
				}
				redisSharedCache.set(key, seq);
			}
			return seq;
		} catch (Exception e) {
			log.error("get seq error....",e);
		}
		return null;
	}

	@Override
	public List<NodeRelation> getChildrenByCurrentNode(String currentNode, String excludeIdentityFlag,boolean isDirect,boolean isIncludeSelf) {
		String seq = getSeq(currentNode);
		Integer seqLength = null;
		if(isDirect){//直接子接点：like seq and length(seq)=length(seq)+4;子节点直接like seq
			seqLength = seq.length()+4;
		}
		return nodeRelationDAO.getChildrenByCurrentNode(seq,excludeIdentityFlag,seqLength,isIncludeSelf);
	}

	@Override
	public NodeRelation findByCurrentNode(String currentNode) {
		if(Strings.isEmpty(currentNode)){
			return null;
		}
		NodeRelationCondition condition = new NodeRelationCondition();
		condition.setCurrentNode(currentNode);
		return findByCondition(condition);
	}
	
}


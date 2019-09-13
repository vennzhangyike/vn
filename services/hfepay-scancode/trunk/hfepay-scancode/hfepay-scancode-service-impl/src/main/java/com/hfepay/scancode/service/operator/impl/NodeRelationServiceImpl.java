/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.NodeRelationDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.NodeRelation;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.NodeRelationService;
import com.hfepay.scancode.service.order.impl.ProfitServiceImpl;
import com.hfepay.scancode.service.utils.NodeRelationSeqUtils;

@Service("nodeRelationService")
public class NodeRelationServiceImpl implements NodeRelationService {
	public static final Logger log = LoggerFactory.getLogger(ProfitServiceImpl.class);
	@Autowired
    private NodeRelationDAO nodeRelationDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private AgentBaseService agentBaseService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private ChannelAdminService channelAdminService;
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
	
	@Override
	public long doSaveNodeRelations(NodeRelationCondition dCondition) {
		//参数校验
		validateParam(dCondition);
		//先删除在建立关系
		deleteByCurrentNode(dCondition);
		//查找父节点
		NodeRelationCondition parentCondition = new NodeRelationCondition();
		parentCondition.setCurrentNode(dCondition.getParentNode());
		NodeRelation node = findByCondition(parentCondition);
		if(node==null){
			throw new RuntimeException("当前节点的父节点不存在,请检查参数 currentNode="+dCondition.getCurrentNode()+" parentNode="+dCondition.getParentNode());
		}
		if(Strings.isEmpty(node.getCurrentNodeLevel())){//父节点层级，下级为该数据+1
			throw new RuntimeException("父节点CurrentNodeLevel不能为空 parentNode="+dCondition.getParentNode());
		}
		String seq = node.getNextNodeSeq();//父节点的下级子节点是当前节点的seq标示
		String parentNextSeq = NodeRelationSeqUtils.getNext(seq);//父节点下一个子节点的值，需要在子节点添加完毕之后更新
		dCondition.setNodeSeq(seq);
		String currentNodeLevel = (Integer.parseInt(node.getCurrentNodeLevel())+1)+"";
		dCondition.setCurrentNodeLevel(currentNodeLevel);
		dCondition.setNextNodeSeq(seq+NodeRelationSeqUtils.SEQ_START_STR);//当前节点的下一个节点是当前节点的seq加上0000
		dCondition.setId(Strings.getUUID());
		dCondition.setCreateTime(new Date());
		long result = insert(dCondition);
		if(result<=0){
			throw new RuntimeException("节点创建异常...");
		}
		node.setNextNodeSeq(parentNextSeq);
		node.setUpdateTime(new Date());
		result = nodeRelationDAO.update(node);
		if(result<=0){
			throw new RuntimeException("节点创建异常...");
		}
		return result;
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
	
	@Override
	public void insertBatch() {
		int batchSize = 500;
		List<ChannelBase> baseList = channelBaseService.findAll(new ChannelBaseCondition());//渠道
		List<NodeRelation> list = new ArrayList<>();
		Map<String,String> nextMap = new HashMap<>();//节点seq关系
		nextMap.put("1", "0000");//默认平台下一个渠道节点为0000
		Date d = new Date();
		for (ChannelBase base : baseList) {
			NodeRelation relation = new NodeRelation();
			relation.setParentNode("1");//根节点
			String currentSeq = nextMap.get("1");//当前节点seq
			String currentNexSeq = currentSeq+"0000";//当前节点的下一个节点
			String parentNextSeq = NodeRelationSeqUtils.getNext(currentSeq);//父节点下一个节点
			nextMap.put(relation.getParentNode(),parentNextSeq);//父节点的下一个节点
			nextMap.put(base.getChannelNo(),currentNexSeq);//当前节点的下一个节点
			relation.setId(Strings.getUUID());
			relation.setCurrentNode(base.getChannelNo());
			relation.setCurrentNodeLevel("0");//当前节点级别-1平台
			relation.setIdentityFlag("1");//1:渠道2代理商3商户0平台
			relation.setNodeSeq(currentSeq);//当前节点的标示符
			relation.setNextNodeSeq(currentNexSeq);//下一个子节点标示符
			relation.setCreateTime(d);//创建时间
			list.add(relation);
		}
		//代理商按照层级关系来创建节点
		for (int i=1;i<4;i++) {
			AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
			agentBaseCondition.setAgentLevel(i+"");
			List<AgentBase> listAgent = agentBaseService.findAll(agentBaseCondition);
			for (AgentBase agentBase : listAgent) {
				NodeRelation relation = new NodeRelation();
				relation.setParentNode(agentBase.getParentNo());//根节点
				String currentSeq = nextMap.get(relation.getParentNode());//当前节点seq
				String currentNexSeq = currentSeq+"0000";//当前节点的下一个节点
				String parentNextSeq = NodeRelationSeqUtils.getNext(currentSeq);//父节点下一个节点
				nextMap.put(relation.getParentNode(),parentNextSeq);//父节点的下一个节点
				nextMap.put(agentBase.getAgentNo(),currentNexSeq);//当前节点的下一个节点
				relation.setId(Strings.getUUID());
				relation.setCurrentNode(agentBase.getAgentNo());
				relation.setCurrentNodeLevel(agentBase.getAgentLevel());//当前节点级别-1平台
				relation.setIdentityFlag("2");//1:渠道2代理商3商户0平台
				relation.setNodeSeq(currentSeq);//当前节点的标示符
				relation.setNextNodeSeq(currentNexSeq);//下一个子节点标示符
				relation.setCreateTime(d);//创建时间
				list.add(relation);
				if(list.size()>=batchSize){
					nodeRelationDAO.insertBatch(list);//批量插入
					list.clear();
				}
				//查询当前代理商的下级商户
				MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
				merchantInfoCondition.setAgentNo(agentBase.getAgentNo());
				List<MerchantInfo> infoList = merchantInfoService.findAll(merchantInfoCondition);
				for (MerchantInfo merchantInfo : infoList) {
					NodeRelation relationM = new NodeRelation();
					relationM.setParentNode(merchantInfo.getAgentNo());//根节点
					String currentSeqM = nextMap.get(relationM.getParentNode());//当前节点seq
					String currentNexSeqM = currentSeqM+"0000";//当前节点的下一个节点
					String parentNextSeqM = NodeRelationSeqUtils.getNext(currentSeqM);//父节点下一个节点
					nextMap.put(relationM.getParentNode(),parentNextSeqM);//父节点的下一个节点
					nextMap.put(merchantInfo.getMerchantNo(),currentNexSeqM);//当前节点的下一个节点
					relationM.setId(Strings.getUUID());
					relationM.setCurrentNode(merchantInfo.getMerchantNo());
					relationM.setCurrentNodeLevel(Integer.parseInt(agentBase.getAgentLevel())+1+"");//当前节点级别-1平台
					relationM.setIdentityFlag("3");//1:渠道2代理商3商户0平台
					relationM.setNodeSeq(currentSeqM);//当前节点的标示符
					relationM.setNextNodeSeq(currentNexSeqM);//下一个子节点标示符
					relationM.setCreateTime(d);//创建时间
					list.add(relationM);
					if(list.size()>=batchSize){
						nodeRelationDAO.insertBatch(list);//批量插入
						list.clear();
					}
					//收银员关系
					ChannelAdminCondition channelAdminCondition = new ChannelAdminCondition();
					channelAdminCondition.setIdentityFlag("4");
					channelAdminCondition.setMerchantNo(merchantInfo.getMerchantNo());
					List<ChannelAdmin> listAdmin = channelAdminService.findAll(channelAdminCondition);
					for (ChannelAdmin channelAdmin : listAdmin) {
						NodeRelation cashierRelation = new NodeRelation();
						cashierRelation.setParentNode(channelAdmin.getMerchantNo());
						String currentSeqC = nextMap.get(cashierRelation.getParentNode());//当前节点seq
						String currentNexSeqC = currentSeqC+"0000";//当前节点的下一个节点
						String parentNextSeqC = NodeRelationSeqUtils.getNext(currentSeqC);//父节点下一个节点
						nextMap.put(cashierRelation.getParentNode(),parentNextSeqC);//父节点的下一个节点
						nextMap.put(channelAdmin.getIdentityNo(),currentNexSeqC);//当前节点的下一个节点
						
						cashierRelation.setId(Strings.getUUID());
						cashierRelation.setCurrentNode(channelAdmin.getIdentityNo());
						cashierRelation.setCurrentNodeLevel(Integer.parseInt(agentBase.getAgentLevel())+2+"");//当前节点级别-1平台
						cashierRelation.setIdentityFlag("4");//1:渠道2代理商3商户0平台
						cashierRelation.setNodeSeq(currentSeqC);//当前节点的标示符
						cashierRelation.setNextNodeSeq(currentNexSeqC);//下一个子节点标示符
						cashierRelation.setCreateTime(d);//创建时间
						list.add(cashierRelation);
						if(list.size()>=batchSize){
							nodeRelationDAO.insertBatch(list);//批量插入
							list.clear();
						}
					}
				}
			}
		}
		nodeRelationDAO.insertBatch(list);//批量插入
		list.clear();
		//更新下一个节点
		Set<String> keys = nextMap.keySet();
		for (String key : keys) {
			String current = key;
			String nextSeq = nextMap.get(current);
			nodeRelationDAO.updateNextSeqByCurrentNode(key,nextSeq);
		}
	}

}


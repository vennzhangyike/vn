/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.channel.impl;

import org.springframework.stereotype.Service;

import com.hfepay.scancode.service.channel.DataNodeService;

@Service("dataNodeService")
public class DataNodeServiceImpl implements DataNodeService {
	
	/*@Autowired
    private DataNodeDAO dataNodeDAO;
    
    *//**
	 * 列表(分页)
	 * @param dataNodeCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
    @Override
	public PagingResult<DataNode> findPagingResult(DataNodeCondition dataNodeCondition){
		CriteriaBuilder cb = Cnd.builder(DataNode.class);
		if(!Strings.isEmpty(dataNodeCondition.getId())){
			cb.andEQ("id", dataNodeCondition.getId());
		}
		if(!Strings.isEmpty(dataNodeCondition.getParentNode())){
			cb.andEQ("parentNode", dataNodeCondition.getParentNode());
		}
		if(!Strings.isEmpty(dataNodeCondition.getCurrentNode())){
			cb.andEQ("currentNode", dataNodeCondition.getCurrentNode());
		}
		if(!Strings.isEmpty(dataNodeCondition.getCurrentNodeLevel())){
			cb.andEQ("currentNodeLevel", dataNodeCondition.getCurrentNodeLevel());
		}
		if(!Strings.isEmpty(dataNodeCondition.getIdentityFlag())){
			cb.andEQ("identityFlag", dataNodeCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(dataNodeCondition.getOperator())){
			cb.andEQ("operator", dataNodeCondition.getOperator());
		}
		if(null != dataNodeCondition.getCreateTime()){
			cb.andEQ("createTime", dataNodeCondition.getCreateTime());
		}

		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(dataNodeCondition.getFirst()), Long.valueOf(dataNodeCondition.getLast()));
		return dataNodeDAO.findPagingResult(buildCriteria);
	}
	
	*//**
	 * 列表
	 * @param dataNode 
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	@Override
	public List<DataNode> findAll(DataNodeCondition dataNodeCondition){
		CriteriaBuilder cb = Cnd.builder(DataNode.class);
		if(!Strings.isEmpty(dataNodeCondition.getId())){
			cb.andEQ("id", dataNodeCondition.getId());
		}
		if(!Strings.isEmpty(dataNodeCondition.getParentNode())){
			cb.andEQ("parentNode", dataNodeCondition.getParentNode());
		}
		if(!Strings.isEmpty(dataNodeCondition.getCurrentNode())){
			cb.andEQ("currentNode", dataNodeCondition.getCurrentNode());
		}
		if(!Strings.isEmpty(dataNodeCondition.getCurrentNodeLevel())){
			cb.andEQ("currentNodeLevel", dataNodeCondition.getCurrentNodeLevel());
		}
		if(!Strings.isEmpty(dataNodeCondition.getIdentityFlag())){
			cb.andEQ("identityFlag", dataNodeCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(dataNodeCondition.getOperator())){
			cb.andEQ("operator", dataNodeCondition.getOperator());
		}
		if(null != dataNodeCondition.getCreateTime()){
			cb.andEQ("createTime", dataNodeCondition.getCreateTime());
		}
		cb.orderBy("currentNodeLevel", Order.DESC);//按照index排序
		Criteria buildCriteria = cb.buildCriteria();
		return dataNodeDAO.findByCriteria(buildCriteria);
	}
	
	*//**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	@Override
	public DataNode findById(String id){
		return dataNodeDAO.get(id);
	}
	
	*//**
	 * 新增
	 * @param dataNodeCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	@Override
	public long insert(DataNodeCondition dataNodeCondition){
		DataNode dataNode = new DataNode();
		BeanUtils.copyProperties(dataNodeCondition, dataNode);
		if(Strings.isEmpty(dataNodeCondition.getId())){
			dataNode.setId(Strings.getUUID());
		}
		return dataNodeDAO.insert(dataNode);
	}

	*//**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	@Override
	public long deleteById(String id){
		return dataNodeDAO.deleteById(id);
	}
	
	*//**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	@Override
	public long deleteByCriteria(Criteria criteria){
		return dataNodeDAO.deleteByCriteria(criteria);
	}
	
	*//**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	@Override
	public long countByCriteria(Criteria criteria){
		return dataNodeDAO.countByCriteria(criteria);
	}
	
	*//**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	@Override
	public long update(DataNodeCondition dataNodeCondition){
		DataNode dataNode = new DataNode();
		BeanUtils.copyProperties(dataNodeCondition, dataNode);
		return dataNodeDAO.update(dataNode);
	}
	
	*//**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-18 13:53:00
	 *//*
	@Override
	public long updateByCriteria(DataNodeCondition dataNodeCondition,Criteria criteria){
		DataNode dataNode = new DataNode();
		BeanUtils.copyProperties(dataNodeCondition, dataNode);
		return dataNodeDAO.updateByCriteria(dataNode,criteria);
	}

	*//**
	 * 新增节点的关系数据
	 * @Title: doSaveNodeRelations
	 * @Description: TODO
	 * @param dCondition：必需参数:identityFlag,currentNode,parentNode，operator
	 * @see com.hfepay.scancode.service.channel.DataNodeService#doSaveNodeRelations(com.hfepay.scancode.condition.DataNodeCondition)
	 *//*
	@Override
	public void doSaveNodeRelations(DataNodeCondition dCondition) {
		//先删除在建立关系
		deleteByCurrentNode(dCondition);
		validateParam(dCondition);
		if(dCondition.getIdentityFlag().equals(IdentityType.Identity_Channel.value())){//是渠道的话，就直接保存,不存在父节点
			dCondition.setId(Strings.getUUID());
			dCondition.setCreateTime(new Date());
			insert(dCondition);
		}
		else{
			DataNodeCondition dataNodeCondition = new DataNodeCondition();
			dataNodeCondition.setCurrentNode(dCondition.getParentNode());
			List<DataNode> insertList  = new ArrayList<>();
			List<DataNode> dataList = findAll(dataNodeCondition);//当前节点的父节点的父节点
			
			if(null!=dataList&&!dataList.isEmpty()){
				String parentlevel = dataList.get(0).getCurrentNodeLevel()==null?"0":dataList.get(0).getCurrentNodeLevel();
				String nextLevel = Integer.parseInt(parentlevel)+1+"";
				for (DataNode dataNode : dataList) {//遍历节点(父节点的父节点)形成父子节点的级联关系
					if(Strings.isEmpty(dataNode.getParentNode())){
						continue;
					}
					DataNode node = new DataNode();
					node.setCreateTime(new Date());
					node.setIdentityFlag(dCondition.getIdentityFlag());//1渠道2代理商3商户：必需参数
					node.setId(Strings.getUUID());
					node.setParentNode(dataNode.getParentNode());
					node.setCurrentNode(dCondition.getCurrentNode());//当前节点id//必须参数
					node.setCurrentNodeLevel(nextLevel);//当前节点级别：渠道是0级别,一级代理商为1
					node.setOperator(dCondition.getOperator());//操作人
					insertList.add(node);
				}
				//当前父节点
				DataNode node = new DataNode();
				node.setCreateTime(new Date());
				node.setIdentityFlag(dCondition.getIdentityFlag());//1渠道2代理商3商户：必需参数
				node.setId(Strings.getUUID());
				node.setParentNode(dCondition.getParentNode());
				node.setCurrentNode(dCondition.getCurrentNode());//当前节点id//必须参数
				node.setCurrentNodeLevel(nextLevel);//当前节点级别：渠道是0级别,一级代理商为1
				node.setOperator(dCondition.getOperator());//操作人
				insertList.add(node);
			}
			dataNodeDAO.batchInsert(insertList);
		}
		
	}
	
	*//**必要参数的校验**//*
	private void validateParam(DataNodeCondition dCondition){
		if(Strings.isEmpty(dCondition.getIdentityFlag())){
			throw new RuntimeException("节点类别IdentityFlag不能为空");
		}
		if(Strings.isEmpty(dCondition.getCurrentNode())){
			throw new RuntimeException("当前节点的值CurrentNode不能为空");
		}
		if(!dCondition.getIdentityFlag().equals(IdentityType.Identity_Channel.value())){
			if(Strings.isEmpty(dCondition.getParentNode())){
				throw new RuntimeException("当前节点父节点ParentNode不能为空");
			}
		}
		if(Strings.isEmpty(dCondition.getOperator())){
			throw new RuntimeException("操作人Operator不能为空");
		}
	}

	@Override
	public void deleteByCurrentNode(DataNodeCondition dataNodeCondition) {
		dataNodeDAO.deleteByCurrentNode(dataNodeCondition.getCurrentNode());
	}

	//根据商户编号找到代理商，在支付的时候获取代理商
	@Override
	public List<DataNode> findByCurrentNode(String currentNode) {
		return dataNodeDAO.findByCurrentNode(currentNode);
	}
	//下级找上级，在利润计算的时候用到
	@Override
	public List<DataNode> findParents(String agentNo) {
		// TODO Auto-generated method stub
		return dataNodeDAO.findParents(agentNo);
	}
	*/
}


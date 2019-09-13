/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.AgreementInfoCondition;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.ChannelWxParamsCondition;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.condition.OrganWalletCondition;
import com.hfepay.scancode.commons.condition.ParamsInfoCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.AgentBaseDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.AgreementInfo;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelWxParams;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.AgreementInfoService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.ChannelWxParamsService;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.operator.NodeRelationService;
import com.hfepay.scancode.service.operator.OrganWalletService;
import com.hfepay.scancode.service.operator.ParamsInfoService;

import net.sf.json.JSONSerializer;

@Service("agentBaseService")
public class AgentBaseServiceImpl implements AgentBaseService {
	public static final Logger log = LoggerFactory.getLogger(AgentBaseServiceImpl.class);

	@Autowired
	private AgentBaseDAO agentBaseDAO;

	@Autowired
	private NodeRelationService nodeRelationService;

	@Autowired
	private RedisSharedCache redisSharedCache;

	@Autowired
	private OrganWalletService organWalletService;
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private ChannelWxParamsService channelWxParamsService;
	@Autowired
	private ParamsInfoService paramsInfoService;
	@Autowired
	private IdCreateService idCreateService;
	@Autowired
	private AgreementInfoService agreementInfoService;

	/**
	 * 列表(分页)
	 * 
	 * @param agentBaseCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public PagingResult<AgentBase> findPagingResult(AgentBaseCondition agentBaseCondition) {
		CriteriaBuilder cb = Cnd.builder(AgentBase.class);
		if (!Strings.isEmpty(agentBaseCondition.getId())) {
			cb.andEQ("id", agentBaseCondition.getId());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentNo())) {
			cb.andEQ("agentNo", agentBaseCondition.getAgentNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentName())) {
			cb.andEQ("agentName", agentBaseCondition.getAgentName());
		}
		if (!Strings.isEmpty(agentBaseCondition.getChannelNo())) {
			cb.andEQ("channelNo", agentBaseCondition.getChannelNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentPreCode())) {
			cb.andEQ("agentPreCode", agentBaseCondition.getAgentPreCode());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentType())) {
			cb.andEQ("agentType", agentBaseCondition.getAgentType());
		}
		if (!Strings.isEmpty(agentBaseCondition.getName())) {
			cb.andEQ("name", agentBaseCondition.getName());
		}
		if (!Strings.isEmpty(agentBaseCondition.getMobile())) {
			cb.andEQ("mobile", agentBaseCondition.getMobile());
		}
		if (!Strings.isEmpty(agentBaseCondition.getParentNo())) {
			cb.andEQ("parentNo", agentBaseCondition.getParentNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentLevel())) {
			cb.andEQ("agentLevel", agentBaseCondition.getAgentLevel());
		}
		if (null != agentBaseCondition.getQrTotal()) {
			cb.andEQ("qrTotal", agentBaseCondition.getQrTotal());
		}
		if (null != agentBaseCondition.getUseTotal()) {
			cb.andEQ("useTotal", agentBaseCondition.getUseTotal());
		}
		if (null != agentBaseCondition.getLessTotal()) {
			cb.andEQ("lessTotal", agentBaseCondition.getLessTotal());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentFlag())) {
			cb.andEQ("agentFlag", agentBaseCondition.getAgentFlag());
		}
		if (!Strings.isEmpty(agentBaseCondition.getStatus())) {
			cb.andEQ("status", agentBaseCondition.getStatus());
		}
		if (!Strings.isEmpty(agentBaseCondition.getRecordStatus())) {
			cb.andEQ("recordStatus", agentBaseCondition.getRecordStatus());
		}
		if (null != agentBaseCondition.getCreateTime()) {
			cb.andEQ("createTime", agentBaseCondition.getCreateTime());
		}

		if (!Strings.isEmpty(agentBaseCondition.getOperator())) {
			cb.andEQ("operator", agentBaseCondition.getOperator());
		}

		if (!Strings.isEmpty(agentBaseCondition.getRemark())) {
			cb.andLike("remark", agentBaseCondition.getRemark());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp1())) {
			cb.andEQ("temp1", agentBaseCondition.getTemp1());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp2())) {
			cb.andEQ("temp2", agentBaseCondition.getTemp2());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp3())) {
			cb.andEQ("temp3", agentBaseCondition.getTemp3());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp4())) {
			cb.andEQ("temp4", agentBaseCondition.getTemp4());
		}
		cb.orderBy("createTime", Order.DESC);
		// 排序
		if (!Strings.isEmpty(agentBaseCondition.getOrderBy())) {
			if (agentBaseCondition.getOrderBy().indexOf(",") > 0) {
				String[] orderBys = agentBaseCondition.getOrderBy().split(",");
				String[] orders = agentBaseCondition.getOrder().split(",");
				for (int i = 0; i < orderBys.length && i < orders.length; i++) {
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			} else {
				cb.orderBy(agentBaseCondition.getOrderBy(), Order.valueOf(agentBaseCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(agentBaseCondition.getFirst()), Long.valueOf(agentBaseCondition.getLast()));

		PagingResult<AgentBase> page = agentBaseDAO.findPagingResult(buildCriteria);
		for (AgentBase agentBase : page.getResult()) {
			try {
				if ("1".equals(agentBase.getAgentLevel())) {
					ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(),RedisKeyEnum.CHANNEL_BASE.getKey() + agentBase.getChannelNo()));
					if (channelBase != null) {
						agentBase.setChannelName(channelBase.getChannelName());
					}
				} else {
					AgentBase agentBaseCache = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+agentBase.getParentNo()));
					if (agentBaseCache != null) {
						agentBase.setParentName(agentBaseCache.getAgentName());
					}
				}
			} catch (Exception e) {
				log.error("获取redis数据异常：" + e.getMessage());
			}
		}
		return page;
	}

	/**
	 * 列表
	 * 
	 * @param agentBase
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public List<AgentBase> findAll(AgentBaseCondition agentBaseCondition) {
		CriteriaBuilder cb = Cnd.builder(AgentBase.class);
		if (!Strings.isEmpty(agentBaseCondition.getId())) {
			cb.andEQ("id", agentBaseCondition.getId());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentNo())) {
			cb.andEQ("agentNo", agentBaseCondition.getAgentNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentName())) {
			cb.andLike("agentName", agentBaseCondition.getAgentName());
		}
		if (!Strings.isEmpty(agentBaseCondition.getChannelNo())) {
			cb.andEQ("channelNo", agentBaseCondition.getChannelNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentPreCode())) {
			cb.andEQ("agentPreCode", agentBaseCondition.getAgentPreCode());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentType())) {
			cb.andEQ("agentType", agentBaseCondition.getAgentType());
		}
		if (!Strings.isEmpty(agentBaseCondition.getName())) {
			cb.andEQ("name", agentBaseCondition.getName());
		}
		if (!Strings.isEmpty(agentBaseCondition.getMobile())) {
			cb.andEQ("mobile", agentBaseCondition.getMobile());
		}
		if (!Strings.isEmpty(agentBaseCondition.getParentNo())) {
			cb.andEQ("parentNo", agentBaseCondition.getParentNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentLevel())) {
			cb.andEQ("agentLevel", agentBaseCondition.getAgentLevel());
		}
		if (null != agentBaseCondition.getQrTotal()) {
			cb.andEQ("qrTotal", agentBaseCondition.getQrTotal());
		}
		if (null != agentBaseCondition.getUseTotal()) {
			cb.andEQ("useTotal", agentBaseCondition.getUseTotal());
		}
		if (null != agentBaseCondition.getLessTotal()) {
			cb.andEQ("lessTotal", agentBaseCondition.getLessTotal());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentFlag())) {
			cb.andEQ("agentFlag", agentBaseCondition.getAgentFlag());
		}
		if (!Strings.isEmpty(agentBaseCondition.getStatus())) {
			cb.andEQ("status", agentBaseCondition.getStatus());
		}
		if (!Strings.isEmpty(agentBaseCondition.getRecordStatus())) {
			cb.andEQ("recordStatus", agentBaseCondition.getRecordStatus());
		}
		if (null != agentBaseCondition.getCreateTime()) {
			cb.andEQ("createTime", agentBaseCondition.getCreateTime());
		}

		if (!Strings.isEmpty(agentBaseCondition.getOperator())) {
			cb.andEQ("operator", agentBaseCondition.getOperator());
		}

		if (!Strings.isEmpty(agentBaseCondition.getRemark())) {
			cb.andLike("remark", agentBaseCondition.getRemark());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp1())) {
			cb.andEQ("temp1", agentBaseCondition.getTemp1());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp2())) {
			cb.andEQ("temp2", agentBaseCondition.getTemp2());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp3())) {
			cb.andEQ("temp3", agentBaseCondition.getTemp3());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp4())) {
			cb.andEQ("temp4", agentBaseCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return agentBaseDAO.findByCriteria(buildCriteria);
	}

	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public AgentBase findById(String id) {
		return agentBaseDAO.get(id);
	}

	/**
	 * 新增
	 * 
	 * @param agentBaseCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public long insert(AgentBaseCondition agentBaseCondition) {
		AgentBase agentBase = new AgentBase();
		BeanUtils.copyProperties(agentBaseCondition, agentBase);
		return agentBaseDAO.insert(agentBase);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public long deleteById(String id) {
		return agentBaseDAO.deleteById(id);
	}

	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public long deleteByCriteria(Criteria criteria) {
		return agentBaseDAO.deleteByCriteria(criteria);
	}

	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public long countByCriteria(Criteria criteria) {
		return agentBaseDAO.countByCriteria(criteria);
	}

	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public long update(AgentBaseCondition agentBaseCondition) {
		AgentBase agentBase = new AgentBase();
		BeanUtils.copyProperties(agentBaseCondition, agentBase);
		try {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(),
					RedisKeyEnum.AGENT_BASE.getKey() + agentBaseCondition.getAgentNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(),
					RedisKeyEnum.AGENT_BASE.getKey() + agentBaseCondition.getAgentNo()), agentBase);
		} catch (Exception e) {
			log.error("#######保存渠道数据到redis失败######");
		}
		return agentBaseDAO.update(agentBase);
	}

	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public long updateByCriteria(AgentBaseCondition agentBaseCondition) {
		AgentBase agentBase = new AgentBase();
		BeanUtils.copyProperties(agentBaseCondition, agentBase);
		CriteriaBuilder cb = Cnd.builder(AgentBase.class);
		if (!Strings.isEmpty(agentBaseCondition.getId())) {
			cb.andEQ("id", agentBaseCondition.getId());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return agentBaseDAO.updateByCriteria(agentBase, buildCriteria);
	}

	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public long updateStatus(String id, String status) {
		return agentBaseDAO.updateStatus(id, status);
	}

	@Override
	public List<AgentBase> findAllByAgentNo(AgentBaseCondition agentBaseCondition, String nodeSeq) {
		CriteriaBuilder cb = Cnd.builder(AgentBase.class);
		if (!Strings.isEmpty(agentBaseCondition.getId())) {
			cb.andEQ("id", agentBaseCondition.getId());
		}
		// if(!Strings.isEmpty(agentBaseCondition.getAgentNo())){
		// cb.andEQ("agentNo", agentBaseCondition.getAgentNo());
		// }
		if(Strings.isNotEmpty(nodeSeq)){
			cb.addParam("identityFlag", IdentityType.Identity_Agent.getCode());
			cb.addParam("nodeSeq", nodeSeq);
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentName())) {
			cb.andEQ("agentName", agentBaseCondition.getAgentName());
		}
		if (!Strings.isEmpty(agentBaseCondition.getChannelNo())) {
			cb.andEQ("channelNo", agentBaseCondition.getChannelNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentPreCode())) {
			cb.andEQ("agentPreCode", agentBaseCondition.getAgentPreCode());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentType())) {
			cb.andEQ("agentType", agentBaseCondition.getAgentType());
		}
		if (!Strings.isEmpty(agentBaseCondition.getName())) {
			cb.andEQ("name", agentBaseCondition.getName());
		}
		if (!Strings.isEmpty(agentBaseCondition.getMobile())) {
			cb.andEQ("mobile", agentBaseCondition.getMobile());
		}
		if (!Strings.isEmpty(agentBaseCondition.getParentNo())) {
			cb.andEQ("parentNo", agentBaseCondition.getParentNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentLevel())) {
			cb.andEQ("agentLevel", agentBaseCondition.getAgentLevel());
		}
		if (null != agentBaseCondition.getQrTotal()) {
			cb.andEQ("qrTotal", agentBaseCondition.getQrTotal());
		}
		if (null != agentBaseCondition.getUseTotal()) {
			cb.andEQ("useTotal", agentBaseCondition.getUseTotal());
		}
		if (null != agentBaseCondition.getLessTotal()) {
			cb.andEQ("lessTotal", agentBaseCondition.getLessTotal());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentFlag())) {
			cb.andEQ("agentFlag", agentBaseCondition.getAgentFlag());
		}
		if (!Strings.isEmpty(agentBaseCondition.getStatus())) {
			cb.andEQ("status", agentBaseCondition.getStatus());
		}
		if (!Strings.isEmpty(agentBaseCondition.getRecordStatus())) {
			cb.andEQ("recordStatus", agentBaseCondition.getRecordStatus());
		}
		if (null != agentBaseCondition.getCreateTime()) {
			cb.andEQ("createTime", agentBaseCondition.getCreateTime());
		}

		if (!Strings.isEmpty(agentBaseCondition.getOperator())) {
			cb.andEQ("operator", agentBaseCondition.getOperator());
		}

		if (!Strings.isEmpty(agentBaseCondition.getRemark())) {
			cb.andLike("remark", agentBaseCondition.getRemark());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp1())) {
			cb.andEQ("temp1", agentBaseCondition.getTemp1());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp2())) {
			cb.andEQ("temp2", agentBaseCondition.getTemp2());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp3())) {
			cb.andEQ("temp3", agentBaseCondition.getTemp3());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp4())) {
			cb.andEQ("temp4", agentBaseCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return agentBaseDAO.findByCriteria(buildCriteria);
	}

	@Override
	public void saveAgentBaseAndNode(AgentBaseCondition agentBaseCondition) {

		AgentBase agentBase = new AgentBase();
		BeanUtils.copyProperties(agentBaseCondition, agentBase);
		agentBaseDAO.insert(agentBase);
		
		try {
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(),
					RedisKeyEnum.AGENT_BASE.getKey() + agentBaseCondition.getAgentNo()), agentBase);
		} catch (Exception e) {
			log.error("#######保存渠道数据到redis失败######");
		}
		
		NodeRelationCondition dataNodeCondition = new NodeRelationCondition();
		dataNodeCondition.setId(Strings.getUUID());
		dataNodeCondition.setCurrentNode(agentBaseCondition.getAgentNo());
		dataNodeCondition.setParentNode(agentBaseCondition.getParentNo());
		dataNodeCondition.setCurrentNodeLevel(agentBaseCondition.getAgentLevel());
		dataNodeCondition.setCreateTime(new Date());
		dataNodeCondition.setIdentityFlag("2");
		dataNodeCondition.setOperator(agentBaseCondition.getOperator());
		nodeRelationService.doSaveNodeRelations(dataNodeCondition);

		// 新增代理商钱包
		OrganWalletCondition organWalletCondition = new OrganWalletCondition();
		organWalletCondition.setId(Strings.getUUID());
		organWalletCondition.setOrganNo(agentBaseCondition.getAgentNo());// 机构编号
		organWalletCondition.setBalance(new BigDecimal(0));// 可用余额
		organWalletCondition.setFreezesAmt(new BigDecimal(0));// 冻结余额
		organWalletCondition.setStatus("1");// 是否有效：1：有效，2：无效
		organWalletCondition.setCreateTime(new Date());// 绑定时间
		organWalletCondition.setOperator(agentBaseCondition.getOperator());// 操作人账号
		organWalletCondition.setRemark("");// 备注
		organWalletCondition.setTemp1("");// 备用字段
		organWalletCondition.setTemp2("");// 备用字段
		organWalletService.insert(organWalletCondition);
	}

	/**
	 * 列表(分页)
	 * 
	 * @param agentBaseCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	@Override
	public PagingResult<AgentBase> findPagingResultByAgentNo(AgentBaseCondition agentBaseCondition,String nodeSeq) {
		CriteriaBuilder cb = Cnd.builder(AgentBase.class);
		if (!Strings.isEmpty(agentBaseCondition.getId())) {
			cb.andEQ("id", agentBaseCondition.getId());
		}
		if(Strings.isNotEmpty(nodeSeq)){
			cb.addParam("identityFlag", IdentityType.Identity_Agent.getCode());
			cb.addParam("nodeSeq", nodeSeq);
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentNo())) {
			cb.andEQ("agentNo", agentBaseCondition.getAgentNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentName())) {
			cb.andEQ("agentName", agentBaseCondition.getAgentName());
		}
		if (!Strings.isEmpty(agentBaseCondition.getChannelNo())) {
			cb.andEQ("channelNo", agentBaseCondition.getChannelNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentPreCode())) {
			cb.andEQ("agentPreCode", agentBaseCondition.getAgentPreCode());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentType())) {
			cb.andEQ("agentType", agentBaseCondition.getAgentType());
		}
		if (!Strings.isEmpty(agentBaseCondition.getName())) {
			cb.andEQ("name", agentBaseCondition.getName());
		}
		if (!Strings.isEmpty(agentBaseCondition.getMobile())) {
			cb.andEQ("mobile", agentBaseCondition.getMobile());
		}
		if (!Strings.isEmpty(agentBaseCondition.getParentNo())) {
			cb.andEQ("parentNo", agentBaseCondition.getParentNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentLevel())) {
			cb.andEQ("agentLevel", agentBaseCondition.getAgentLevel());
		}
		if (null != agentBaseCondition.getQrTotal()) {
			cb.andEQ("qrTotal", agentBaseCondition.getQrTotal());
		}
		if (null != agentBaseCondition.getUseTotal()) {
			cb.andEQ("useTotal", agentBaseCondition.getUseTotal());
		}
		if (null != agentBaseCondition.getLessTotal()) {
			cb.andEQ("lessTotal", agentBaseCondition.getLessTotal());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentFlag())) {
			cb.andEQ("agentFlag", agentBaseCondition.getAgentFlag());
		}
		if (!Strings.isEmpty(agentBaseCondition.getStatus())) {
			cb.andEQ("status", agentBaseCondition.getStatus());
		}
		if (!Strings.isEmpty(agentBaseCondition.getRecordStatus())) {
			cb.andEQ("recordStatus", agentBaseCondition.getRecordStatus());
		}
		if (null != agentBaseCondition.getCreateTime()) {
			cb.andEQ("createTime", agentBaseCondition.getCreateTime());
		}

		if (!Strings.isEmpty(agentBaseCondition.getOperator())) {
			cb.andEQ("operator", agentBaseCondition.getOperator());
		}

		if (!Strings.isEmpty(agentBaseCondition.getRemark())) {
			cb.andLike("remark", agentBaseCondition.getRemark());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp1())) {
			cb.andEQ("temp1", agentBaseCondition.getTemp1());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp2())) {
			cb.andEQ("temp2", agentBaseCondition.getTemp2());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp3())) {
			cb.andEQ("temp3", agentBaseCondition.getTemp3());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp4())) {
			cb.andEQ("temp4", agentBaseCondition.getTemp4());
		}
		cb.orderBy("createTime", Order.DESC);
		// 排序
		if (!Strings.isEmpty(agentBaseCondition.getOrderBy())) {
			if (agentBaseCondition.getOrderBy().indexOf(",") > 0) {
				String[] orderBys = agentBaseCondition.getOrderBy().split(",");
				String[] orders = agentBaseCondition.getOrder().split(",");
				for (int i = 0; i < orderBys.length && i < orders.length; i++) {
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			} else {
				cb.orderBy(agentBaseCondition.getOrderBy(), Order.valueOf(agentBaseCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(agentBaseCondition.getFirst()), Long.valueOf(agentBaseCondition.getLast()));

		PagingResult<AgentBase> page = agentBaseDAO.findPagingResult(buildCriteria);
		for (AgentBase agentBase : page.getResult()) {
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+agentBase.getParentNo()));
				if(channelBase != null){
					agentBase.setParentName(channelBase.getChannelName());
				}
				AgentBase agentBaseCache = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+agentBase.getParentNo()));
				if(agentBaseCache != null){
					agentBase.setParentName(agentBaseCache.getAgentName());
				}
			} catch (Exception e) {
				log.error("获取redis数据异常：" + e.getMessage());
			}
		}
		return page;
	}

	@Override
	public long updateUsedTimes(String agentNo) {
		return agentBaseDAO.updateUsedTimes(agentNo);
	}

	@Override
	public AgentBase findByAgentNo(String agentNo) {
		AgentBase agentBase = null;
    	if (Strings.isEmpty(agentNo)) {
			new RuntimeException("代理商编号不能为空");
		}
    	
    	try {
			//代理商信息从缓存中获取
//			agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+agentNo));
			if (agentBase == null) {				
				CriteriaBuilder cb = Cnd.builder(AgentBase.class);
				cb.andEQ("agentNo", agentNo);
				cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteria = cb.buildCriteria();
				agentBase = agentBaseDAO.findOneByCriteria(buildCriteria);
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+agentNo),agentBase);
				return agentBase;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		return agentBase;
	}

	@Override
	public void savePublicNo(ChannelExpandCondition channelExpandCondition) throws Exception {
		log.info("保存渠道信息：" + channelExpandCondition);
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", channelExpandCondition.getAppid());
		map.put("secret", channelExpandCondition.getSecret());
		map.put("registerTemplateId", channelExpandCondition.getRegisterTemplateId());
		map.put("payTemplateId", channelExpandCondition.getPayTemplateId());
		map.put("withDrawsTemplateId", channelExpandCondition.getWithDrawsTemplateId());
		map.put("unperfectTemplateId", channelExpandCondition.getUnperfectTemplateId());
		String wxParams = JSONSerializer.toJSON(map).toString();

		// 短信参数
		Map<String, String> smsMap = new HashMap<String, String>();
		smsMap.put("smsuser", channelExpandCondition.getSmsuser());
		smsMap.put("smspassword", channelExpandCondition.getSmspassword());
		smsMap.put("sendUrl", channelExpandCondition.getSendUrl());
		String smsParams = JSONSerializer.toJSON(smsMap).toString();

		if (Strings.isEmpty(channelExpandCondition.getId())) {
			channelExpandCondition.setIsActive(ScanCodeConstants.Y);
			channelExpandCondition.setCreateTime(new Date());
			channelExpandCondition.setStatus(ScanCodeConstants.STATUS_ACTIVE);

			AgentBase agentBase = this.findByAgentNo(channelExpandCondition.getChannelNo());
			if (agentBase != null) {
				channelExpandCondition.setTemp1(agentBase.getChannelNo());
			}

			// 新增
			channelExpandService.insert(channelExpandCondition);

			// 插入公众号信息
			ChannelWxParamsCondition channelWxParamsCondition = new ChannelWxParamsCondition();
			BeanUtils.copyProperties(channelExpandCondition, channelWxParamsCondition);
			channelWxParamsCondition.setOrganNo(channelExpandCondition.getChannelNo());// 添加标示符
			channelWxParamsCondition.setRecordStatus(ScanCodeConstants.Y);
			channelWxParamsCondition.setWxParams(wxParams);
			channelWxParamsService.insert(channelWxParamsCondition);
			ChannelWxParams params = channelWxParamsService.findByChannelNo(channelExpandCondition.getChannelNo());
			redisSharedCache.setObj(new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,
					HfepayConfig.CHANNEL_REDIS_WX_KEY + channelExpandCondition.getChannelNo()), params);

			// 保存短信信息
			saveSms(smsParams, channelExpandCondition);

			// 保存电子协议信息
			saveAgreementInfo(channelExpandCondition);

		} else {
			channelExpandService.update(channelExpandCondition);

			// 更新公众号信息
			ChannelWxParamsCondition channelWxParamsCondition = new ChannelWxParamsCondition();
			ChannelWxParams channelWxParams = channelWxParamsService
					.findByChannelNo(channelExpandCondition.getChannelNo());
			if (channelWxParams != null) {
				BeanUtils.copyProperties(channelExpandCondition, channelWxParamsCondition);
				channelWxParamsCondition.setId(channelWxParams.getId());
				channelWxParamsCondition.setWxParams(wxParams);
				channelWxParamsService.update(channelWxParamsCondition);
				redisSharedCache.del(new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,
						HfepayConfig.CHANNEL_REDIS_WX_KEY + channelExpandCondition.getChannelNo()));
				redisSharedCache.setObj(
						new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,
								HfepayConfig.CHANNEL_REDIS_WX_KEY + channelExpandCondition.getChannelNo()),
						channelWxParams);
			}

			// 更新短信信息
			ParamsInfo paramsInfo = paramsInfoService.findParamsKey(channelExpandCondition.getChannelNo(),
					ParamsType.PARAMS_SMS.getCode());
			if (null != paramsInfo) {
				ParamsInfoCondition paramsInfoCondition = new ParamsInfoCondition();
				paramsInfoCondition.setParamsValue(smsParams);
				paramsInfoCondition.setId(paramsInfo.getId());
				paramsInfoService.update(paramsInfoCondition);
			} else {
				saveSms(smsParams, channelExpandCondition);
			}

			AgreementInfoCondition agreementInfoCondition = new AgreementInfoCondition();
			agreementInfoCondition.setOrganNo(channelExpandCondition.getChannelNo());
			List<AgreementInfo> agreementList = agreementInfoService.findAll(agreementInfoCondition);
			if (agreementList != null && agreementList.size() > 0) {
				agreementInfoCondition.setAgreementcontent(channelExpandCondition.getAgreement());
				agreementInfoService.updateByCriteria(agreementInfoCondition);
			} else {
				agreementInfoCondition.setAgreementNo(idCreateService.createParamNo(""));
				agreementInfoCondition.setId(Strings.getUUID());
				agreementInfoCondition.setAgreementtype("0");
				agreementInfoCondition.setAgreementcontent(channelExpandCondition.getAgreement());
				agreementInfoCondition.setStatus("1");
				agreementInfoCondition.setCreateTime(new Date());
				agreementInfoCondition.setOperater(channelExpandCondition.getOperator());
				agreementInfoService.insert(agreementInfoCondition);
			}

		}
		log.info("保存渠道信息结束。");
	}

	/**
	 * 保存短信信息
	 * 
	 * @param smsParams
	 * @param channelBaseCondition
	 */
	private void saveSms(String smsParams, ChannelExpandCondition channelExpandCondition) {
		ParamsInfoCondition paramsInfoCondition = new ParamsInfoCondition();
		paramsInfoCondition.setParamsKey(channelExpandCondition.getChannelNo());
		paramsInfoCondition.setParamsType(ParamsType.PARAMS_SMS.getCode());
		paramsInfoCondition.setParamsValue(smsParams);
		paramsInfoService.insert(paramsInfoCondition);
	}

	/**
	 * 保存电子协议信息
	 * 
	 * @param channelExpandCondition
	 */
	private void saveAgreementInfo(ChannelExpandCondition channelExpandCondition) {
		AgreementInfoCondition agreementInfoCondition = new AgreementInfoCondition();
		agreementInfoCondition.setAgreementNo(idCreateService.createParamNo(""));
		agreementInfoCondition.setId(Strings.getUUID());
		agreementInfoCondition.setOrganNo(channelExpandCondition.getChannelNo());
		agreementInfoCondition.setAgreementtype("0");
		agreementInfoCondition.setAgreementcontent(channelExpandCondition.getAgreement());
		agreementInfoCondition.setStatus("1");
		agreementInfoCondition.setCreateTime(new Date());
		agreementInfoCondition.setOperater(channelExpandCondition.getOperator());
		agreementInfoService.insert(agreementInfoCondition);
	}

	/**
	 * 设置代理商redis
	 * 
	 * @throws Exception
	 */
	@Override
	public void setAgentRedis() throws Exception {
		AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
		List<AgentBase> list = this.findAll(agentBaseCondition);
		for (AgentBase agentBase : list) {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(),
					RedisKeyEnum.AGENT_BASE.getKey() + agentBase.getAgentNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(),
					RedisKeyEnum.AGENT_BASE.getKey() + agentBase.getAgentNo()), agentBase);
		}
	}
	
	@Override
	public AgentBase findByCondition(AgentBaseCondition agentBaseCondition) {
		CriteriaBuilder cb = Cnd.builder(AgentBase.class);
		if (!Strings.isEmpty(agentBaseCondition.getId())) {
			cb.andEQ("id", agentBaseCondition.getId());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentNo())) {
			cb.andEQ("agentNo", agentBaseCondition.getAgentNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentName())) {
			cb.andLike("agentName", agentBaseCondition.getAgentName());
		}
		if (!Strings.isEmpty(agentBaseCondition.getChannelNo())) {
			cb.andEQ("channelNo", agentBaseCondition.getChannelNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentPreCode())) {
			cb.andEQ("agentPreCode", agentBaseCondition.getAgentPreCode());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentType())) {
			cb.andEQ("agentType", agentBaseCondition.getAgentType());
		}
		if (!Strings.isEmpty(agentBaseCondition.getName())) {
			cb.andEQ("name", agentBaseCondition.getName());
		}
		if (!Strings.isEmpty(agentBaseCondition.getMobile())) {
			cb.andEQ("mobile", agentBaseCondition.getMobile());
		}
		if (!Strings.isEmpty(agentBaseCondition.getParentNo())) {
			cb.andEQ("parentNo", agentBaseCondition.getParentNo());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentLevel())) {
			cb.andEQ("agentLevel", agentBaseCondition.getAgentLevel());
		}
		if (null != agentBaseCondition.getQrTotal()) {
			cb.andEQ("qrTotal", agentBaseCondition.getQrTotal());
		}
		if (null != agentBaseCondition.getUseTotal()) {
			cb.andEQ("useTotal", agentBaseCondition.getUseTotal());
		}
		if (null != agentBaseCondition.getLessTotal()) {
			cb.andEQ("lessTotal", agentBaseCondition.getLessTotal());
		}
		if (!Strings.isEmpty(agentBaseCondition.getAgentFlag())) {
			cb.andEQ("agentFlag", agentBaseCondition.getAgentFlag());
		}
		if (!Strings.isEmpty(agentBaseCondition.getStatus())) {
			cb.andEQ("status", agentBaseCondition.getStatus());
		}
		if (!Strings.isEmpty(agentBaseCondition.getRecordStatus())) {
			cb.andEQ("recordStatus", agentBaseCondition.getRecordStatus());
		}
		if (null != agentBaseCondition.getCreateTime()) {
			cb.andEQ("createTime", agentBaseCondition.getCreateTime());
		}

		if (!Strings.isEmpty(agentBaseCondition.getOperator())) {
			cb.andEQ("operator", agentBaseCondition.getOperator());
		}

		if (!Strings.isEmpty(agentBaseCondition.getRemark())) {
			cb.andLike("remark", agentBaseCondition.getRemark());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp1())) {
			cb.andEQ("temp1", agentBaseCondition.getTemp1());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp2())) {
			cb.andEQ("temp2", agentBaseCondition.getTemp2());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp3())) {
			cb.andEQ("temp3", agentBaseCondition.getTemp3());
		}
		if (!Strings.isEmpty(agentBaseCondition.getTemp4())) {
			cb.andEQ("temp4", agentBaseCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return agentBaseDAO.findOneByCriteria(buildCriteria);
	}

}

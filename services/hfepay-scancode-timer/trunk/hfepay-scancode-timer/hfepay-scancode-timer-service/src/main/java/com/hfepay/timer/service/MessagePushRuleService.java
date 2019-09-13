/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service;

import java.util.List;

import com.hfepay.scancode.commons.condition.MessagePushRuleCondition;
import com.hfepay.scancode.commons.entity.MessagePushRule;

public interface MessagePushRuleService {
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param messagePushRuleCondition
	 * @return: List<MessagePushRule>
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	public List<MessagePushRule> findAll(MessagePushRuleCondition messagePushRuleCondition);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param messagePushRuleCondition
	 * @return: long
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	long update(MessagePushRuleCondition messagePushRuleCondition);

	/** 推送消息 */
	void messagePush();	
	
}


package com.hfepay.commons.criteria.impl;

import java.util.List;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.criteria.Condition;
import com.hfepay.commons.criteria.CriterionGroup;
import com.hfepay.commons.criteria.Logic;
 
/**
 * 默认的组合条件实现。暂时不支持嵌套的组合条件，如：
 * <pre>
 * AND ( ( column = value or column2=value2) )
 * </pre>
 * 
 * @author Sam
 *
 */
public class DefaultCriterionGroup extends AbstractCondition implements CriterionGroup {
		
	List<Condition> conditions = Lists.newList();
	
	{
		logic = Logic.AND;
	}
	
	public boolean isGroup() {
		return true;
	}

	public CriterionGroup add(Condition... conditions) {
		List<Condition> cnds = Lists.of(conditions);
		add(cnds);
		return this;
	}


	public List<Condition> getAll() {
		return conditions;
	}

	public CriterionGroup add(List<Condition> conditions) {
		if (conditions !=null && !conditions.isEmpty()) {
			int cnt = 1;
			for (Condition c : conditions) {
//				if (!c.isGroup()) {
//					if (cnt == 1) {
//						((DefaultCriterion)c).setLogic(Logic.NONE);
//					}
//					this.conditions.add(c);
//					cnt++;
//				}
				if (cnt == 1) {
					c.setLogic(Logic.NONE);
				}
				this.conditions.add(c);
				cnt++;
			}
		} 
		return this;
	}

	 

}

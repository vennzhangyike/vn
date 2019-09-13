package com.hfepay.scancode.service.operator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.SysResourceCondition;
import com.hfepay.scancode.commons.dao.SysResourceDAO;
import com.hfepay.scancode.commons.entity.SysResource;
import com.hfepay.scancode.service.operator.PageService;

@Service
public class PageServiceImpl implements PageService{
	@Autowired
	private SysResourceDAO sysResourceDAO;

	@Override
	public PagingResult<SysResource> findAllMenu(SysResourceCondition condition) {
		CriteriaBuilder cb = Cnd.builder(SysResource.class);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(condition.getFirst()), Long.valueOf(condition.getLast()));
		PagingResult<SysResource> findPagingResult = sysResourceDAO.findPagingResult(buildCriteria);
		return findPagingResult;
	}
}

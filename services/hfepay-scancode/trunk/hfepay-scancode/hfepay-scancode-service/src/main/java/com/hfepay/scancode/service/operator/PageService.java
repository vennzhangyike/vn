package com.hfepay.scancode.service.operator;

import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.SysResourceCondition;
import com.hfepay.scancode.commons.entity.SysResource;

public interface PageService {

	public PagingResult<SysResource> findAllMenu(SysResourceCondition condition);//(int pageNo,int pageSize);
}

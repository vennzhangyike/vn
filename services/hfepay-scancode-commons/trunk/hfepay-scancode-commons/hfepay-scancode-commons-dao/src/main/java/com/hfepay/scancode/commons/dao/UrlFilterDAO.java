package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.scancode.commons.entity.UrlFilter;

public interface UrlFilterDAO {
	List<UrlFilter> findRoles(String name);

	List<UrlFilter> findAllUrl();
}

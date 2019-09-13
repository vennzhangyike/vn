package com.hfepay.commons.utils;

import java.io.Serializable;
import java.util.List;
/**
 * 默认的分页结果实现
 * @author Sam
 *
 * @param <T>
 */
public class DefaultPagingResult<T> implements PagingResult<T>,Serializable{
	private static final long serialVersionUID = 1914724112835697247L;

	protected Long totalCount;
	
	protected List<T> result;

	public DefaultPagingResult(Long totalCount, List<T> result) {
		super();
		this.totalCount = totalCount;
		this.result = result;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalRows(Long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
}

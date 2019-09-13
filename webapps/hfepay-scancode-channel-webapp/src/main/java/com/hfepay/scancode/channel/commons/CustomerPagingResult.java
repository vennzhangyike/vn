package com.hfepay.scancode.channel.commons;

import java.io.Serializable;
import java.util.List;

import com.hfepay.commons.utils.PagingCondition;
import com.hfepay.commons.utils.PagingResult;
/**
 * 默认的分页结果实现
 * @author Sam
 *
 * @param <T>
 */
public class CustomerPagingResult<T> implements Serializable{
	private static final long serialVersionUID = 1914724112835697257L;

	protected Long totalCount;
	
	protected List<T> result;
	
	protected Integer pageSize;
	
	protected Long totalPages;
	
	protected Integer pageNo;

	public CustomerPagingResult(PagingResult<T> pageResult,PagingCondition condition) {
		this.totalCount = pageResult.getTotalCount();
		this.result = pageResult.getResult();
		this.pageSize = condition.getPageSize();
		this.pageNo = condition.getPageNo();
		this.totalPages = pageResult.getTotalCount()%pageSize==0?pageResult.getTotalCount()/pageSize:pageResult.getTotalCount()/pageSize+1;
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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	
	
	
}

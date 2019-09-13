package com.hfepay.commons.utils;

import java.io.Serializable;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.base.lang.Throwables;

public abstract class AbstractPagination implements Pagination,Ordering ,Serializable {


	private static final int DEFAULT_PAGE_SIZE = 10;

	private static final int DEFAULT_PAGE_NO = 1;

	private static final long serialVersionUID = -3408899809645520650L;
	
	
	/**
	 * 当前页
	 */
	protected int pageNo = DEFAULT_PAGE_NO;
	
	/**
	 * 每页显示记录数
	 */
	protected int pageSize = DEFAULT_PAGE_SIZE;
	
	/**
	 * asc-升序 desc-降序
	 */
	protected String order ;
	
	/**
	 * 排序字段
	 */
	protected String orderBy ;

	public AbstractPagination() {
		super();
	}

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		
		return pageNo;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 * 注意：相应的分页SQL应该是>=getFirst()的。
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize) + 1;
	}
	
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	
		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	/**
	 * 取得分页查询的索引下标值。
	 * 注意 ： 相应的分页SQL应该是<getLast();
	 */
	public int getLast() {
		return getFirst() + pageSize;
	}
	

	/**
	 * 获得排序方向.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(String order) {
		//检查order字符串的合法值
		String temp = "";
		if(!Strings.isEmpty(order)){
			temp = order.toUpperCase();
		}
		String[] orders = Strings.splitIgnoreBlank(temp, ",");
		for (String orderStr : orders) {
			if (!Strings.equals(DESC, orderStr) && !Strings.equals(ASC, orderStr)) {
				throw Throwables.makeThrow("The order '%s' is not valid!",orderStr);
			}
		}
	
		this.order = temp;
	}
	
	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (!Strings.isEmpty(orderBy) && !Strings.isEmpty(order));
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
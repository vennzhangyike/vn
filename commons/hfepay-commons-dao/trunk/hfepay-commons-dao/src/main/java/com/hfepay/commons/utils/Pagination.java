package com.hfepay.commons.utils;


/**
 * 分页信息抽象接口
 * 
 * @author Sam
 *
 */
public interface Pagination {
	
	public static String ASC = "ASC";
	public static String DESC = "DESC";
	
	/**
	 * 获得分页时开始索引
	 * @return
	 */
	int getFirst();
	
	/**
	 * 获得分页时结束索引
	 * @return
	 */
	int getLast();
	
	/**
	 * 获取当前页号
	 * @return
	 */
	int getPageNo();
	
	/**
	 * 获取分页记录条数
	 * @return
	 */
	int getPageSize();

	
	/**
	 * 设置当前页号
	 * @param pageNo
	 * @return
	 */
	void setPageNo(int pageNo);
	
	/**
	 * 设置页显示记录条数
	 * @param pageSize
	 * @return
	 */
	void setPageSize(int pageSize);	
	
	/**
	 * 设置排序值
	 * @param order
	 */
	void setOrder(String order) ;
	
	/**
	 * 设置排序字段
	 * @param orderBy
	 */
	void setOrderBy(String orderBy);
	
	/**
	 * 返回整排序的字段
	 * @return
	 */
	String getOrderBy();
	

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted();
	
	
	/**
	 * 获取排序条件
	 * @return
	 */
	String getOrder(); 
	
	
}

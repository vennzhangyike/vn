package com.hfepay.commons.jdbc;

/**
 * Mysql分页方言处理器
 * @author badqiu
 * @author Sam
 */
public class MySQLDialect extends Dialect{

	public boolean supportsLimitOffset(){
		return true;
	}
	
    public boolean supportsLimit() {   
        return true;   
    }  
    
	public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
		//mysql 的limit start,end 中的end是指要取的记录条数，页非索引值
		int rowcount = limit - offset;
		
		//limit索引值是从0开始的
		offset = offset % 10 > 0?offset-1:offset;
		
		return sql + " limit "+offset+","+rowcount;  
	}   
  
	 
}

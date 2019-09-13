package com.hfepay.commons.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.utils.Springs;

/**
 * 数据库工具类，用于获取一些元数据等等
 * @author Sam
 *
 */
public class Databases {
	
	private final static Logger logger = LoggerFactory.getLogger(Databases.class);
	
	private static DBType dbtype;
	
	private static void init(){
		DataSource ds = Springs.getBean(DataSource.class);
		Connection conn = null;
		try {
			conn = ds.getConnection();
			DatabaseMetaData dbMetaData = conn.getMetaData();
			String productName = dbMetaData.getDatabaseProductName();
			if (productName.toLowerCase().indexOf("mysql") != -1) {
				dbtype = DBType.MYSQL;
			}
			if (productName.toLowerCase().indexOf("sqlserver") != -1) {
				dbtype = DBType.SQLSERVER;
			}
			if (productName.toLowerCase().indexOf("sql server") != -1) {
				dbtype = DBType.SQLSERVER;
			}
			if (productName.toLowerCase().indexOf("oracle") != -1) {
				dbtype = DBType.ORACLE;
			}
			if (productName.toLowerCase().indexOf("db2") != -1) {
				dbtype = DBType.DB2;
			}
			if (productName.toLowerCase().indexOf("h2") != -1) {
				dbtype = DBType.H2;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
			} finally {
				conn = null;
			}
		}
	}
	
	public static DBType getDbType(){
		if(null == dbtype){
			init();
		}
		return dbtype;
	}
	
	public static boolean isOracle(){
		if(null == dbtype){
			init();
		}
		return DBType.ORACLE.equals(dbtype);
	}
	
	public static boolean isMySQL(){
		if(null == dbtype){
			init();
		}
		return DBType.MYSQL.equals(dbtype);
	}
	
	public static boolean isSqlServer(){
		if(null == dbtype){
			init();
		}
		return DBType.SQLSERVER.equals(dbtype);
	}
	
	public static boolean isDB2(){
		if(null == dbtype){
			init();
		}
		return DBType.DB2.equals(dbtype);
	}

	public enum DBType{
		ORACLE, MYSQL, SQLSERVER, DB2,H2
	}
}


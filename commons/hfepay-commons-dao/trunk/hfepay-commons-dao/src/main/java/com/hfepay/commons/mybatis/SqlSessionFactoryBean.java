package com.hfepay.commons.mybatis;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;

import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.reflect.Reflector;
import com.hfepay.commons.utils.Databases;
import com.hfepay.commons.utils.Databases.DBType;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * 扩展Mybatis的SqlSessionFactoryBean，解决资源重复加载的问题。
 * 
 * @author Sam
 *
 */
public class SqlSessionFactoryBean extends org.mybatis.spring.SqlSessionFactoryBean {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 设置一个主的mapper文件
	 */
	protected DBType dbtype =DBType.MYSQL;
	
	public void setMapperLocations(Resource[] mapperLocations) {
		if (ArrayUtils.isNotEmpty(mapperLocations)) {
			Map<String,Resource> resources = Maps.newMap();
			for (Resource r : mapperLocations) { 
				Resource resExists = resources.get(r.getFilename());
				if ( (resExists == null) || isFromClasspath(r) ) {
					resources.put(r.getFilename(), r);
				}				
			} 
			Resource [] mappers = new Resource[resources.size()];
			resources.values().toArray(mappers);
			super.setMapperLocations(mappers);
		}        
    }
	
	public void setDbtype(String dbtype) {
		this.dbtype = DBType.valueOf(dbtype);
	}
	
	/**
	 * 检测要加载的是否是WEB-INF/classes目录下面的资源
	 * @param r 资源
	 * @return true为是，反之不是
	 */
	private boolean isFromClasspath(Resource r) {
		try {
			if (r == null || r.getFile() == null) 
				return false;
			File f = r.getFile();
			if (f.getAbsolutePath().matches(".*[WEB-INF].?[classes].*"))
				return true;
		} catch (IOException e) {  }
		return false;
		
	}
	
	/**
	 * 重写此方法以实现支持多种数据库， 以oracle为基准， 其它数据库只需在mapper中重新定义那些有特性的sql。
	 */
	@Override
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		Configuration configuration;

		// 添加的代码行, 下面代码是通过特殊方式拿到父类私有变量的值
		Resource configLocation = (Resource) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "configLocation");
		Properties configurationProperties = (Properties) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "configurationProperties");
		Class<?>[] typeAliases = (Class<?>[]) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "typeAliases");
		String typeAliasesPackage = (String) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "typeAliasesPackage");
		Interceptor[] plugins = (Interceptor[]) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "plugins");
		TypeHandler<?>[] typeHandlers = (TypeHandler<?>[]) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "typeHandlers");
		String typeHandlersPackage = (String) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "typeHandlersPackage");
		DataSource dataSource = (DataSource) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "dataSource");
		TransactionFactory transactionFactory = (TransactionFactory) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "transactionFactory");
		String environmentstr = (String) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "environment");
		Resource[] mapperLocations = (Resource[]) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "mapperLocations");
		SqlSessionFactoryBuilder sqlSessionFactoryBuilder = (SqlSessionFactoryBuilder) Reflector.of(SqlSessionFactoryBean.class).getValue(this, "sqlSessionFactoryBuilder");
		// End

		XMLConfigBuilder xmlConfigBuilder = null;
		if (configLocation != null) {
			xmlConfigBuilder = new XMLConfigBuilder(configLocation.getInputStream(), null, configurationProperties);
			configuration = xmlConfigBuilder.getConfiguration();
		} else {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("Property 'configLocation' not specified, using default MyBatis Configuration");
			}
			configuration = new Configuration();
			configuration.setVariables(configurationProperties);
		}

		if (StringUtils.hasLength(typeAliasesPackage)) {
			String[] typeAliasPackageArray = StringUtils.tokenizeToStringArray(typeAliasesPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String packageToScan : typeAliasPackageArray) {
				configuration.getTypeAliasRegistry().registerAliases(packageToScan);
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Scanned package: '" + packageToScan + "' for aliases");
				}
			}
		}

		if (!ObjectUtils.isEmpty(typeAliases)) {
			for (Class<?> typeAlias : typeAliases) {
				configuration.getTypeAliasRegistry().registerAlias(typeAlias);
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Registered type alias: '" + typeAlias + "'");
				}
			}
		}

		if (!ObjectUtils.isEmpty(plugins)) {
			for (Interceptor plugin : plugins) {
				configuration.addInterceptor(plugin);
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Registered plugin: '" + plugin + "'");
				}
			}
		}

		if (StringUtils.hasLength(typeHandlersPackage)) {
			String[] typeHandlersPackageArray = StringUtils.tokenizeToStringArray(typeHandlersPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String packageToScan : typeHandlersPackageArray) {
				configuration.getTypeHandlerRegistry().register(packageToScan);
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Scanned package: '" + packageToScan + "' for type handlers");
				}
			}
		}

		if (!ObjectUtils.isEmpty(typeHandlers)) {
			for (TypeHandler<?> typeHandler : typeHandlers) {
				configuration.getTypeHandlerRegistry().register(typeHandler);
				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Registered type handler: '" + typeHandler + "'");
				}
			}
		}

		if (xmlConfigBuilder != null) {
			try {
				xmlConfigBuilder.parse();

				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Parsed configuration file: '" + configLocation + "'");
				}
			} catch (Exception ex) {
				throw new NestedIOException("Failed to parse config resource: " + configLocation, ex);
			} finally {
				ErrorContext.instance().reset();
			}
		}

		if (transactionFactory == null) {
			transactionFactory = new SpringManagedTransactionFactory();
		}

		Environment environment = new Environment(environmentstr, transactionFactory, dataSource);
		configuration.setEnvironment(environment);

		if (!ObjectUtils.isEmpty(mapperLocations)) {
			for (Resource mapperLocation : mapperLocations) {
				if (mapperLocation == null) {
					continue;
				}

				// this block is a workaround for issue
				// http://code.google.com/p/mybatis/issues/detail?id=235
				// when running MyBatis 3.0.4. But not always works.
				// Not needed in 3.0.5 and above.
				String path;
				if (mapperLocation instanceof ClassPathResource) {
					path = ((ClassPathResource) mapperLocation).getPath();
				} else {
					path = mapperLocation.toString();
				}

				InputStream mapperInputStream = null;
				try {
					// 通过合并获取最终包含其它数据库特性sql的mapper.xml
					mapperInputStream = this.mergerMapper(mapperLocation, configuration);

					XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperInputStream, configuration, path, configuration.getSqlFragments());
					xmlMapperBuilder.parse();
				} catch (Exception e) {
					throw new NestedIOException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
				} finally {
					ErrorContext.instance().reset();
	                if (mapperInputStream != null)
	                	mapperInputStream.close();
				}

				if (this.logger.isDebugEnabled()) {
					this.logger.debug("Parsed mapper file: '" + mapperLocation + "'");
				}
			}
		} else {
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("Property 'mapperLocations' was not specified or no matching resources found");
			}
		}

		return sqlSessionFactoryBuilder.build(configuration);
	}

	/**
	 * 以oracle基本， 合并其它数据库的特殊sql， 生成新的mapper.xml
	 * @param mapperLocation
	 * @param configuration
	 * @return
	 * @throws IOException 
	 */
	private InputStream mergerMapper(Resource mapperLocation, Configuration configuration) throws IOException {
		InputStream inputStream = null;//返回合并后的mapper.xml
		//InputStream otherDbMapperIs = null;//其它数据库类型的mapper.xml
		StringWriter strResponse = null;
		try {
			inputStream = mapperLocation.getInputStream();
			//假如是当前dbtype的类型的话则无须加载其他的,另外H2数据库因为可以设置各种数据库方言，也可以忽略不加载其他的
			if (Databases.getDbType() == dbtype || Databases.getDbType() == DBType.H2) {
				return inputStream;
			}

			Resource otherDbMapperResource = mapperLocation.createRelative("../" + Databases.getDbType().name().toLowerCase()+"/"+mapperLocation.getFilename());
			if (!otherDbMapperResource.exists()) {//不存在其它数据库对应的mapper.xml直接返回oracle的mapper.xml
				return inputStream;
			}

			//oracleXpathParser 解析oracle mapper.xml
			XPathParser oracleXpathParser = new XPathParser(mapperLocation.getInputStream(), true, configuration.getVariables(), new XMLMapperEntityResolver());
			Document doc = oracleXpathParser.getDocument();//Document oracle mapper.xml
			XNode orclMapperXNode = oracleXpathParser.evalNode("/mapper");//orclMapperXNode 获取mapper下面所有的Element

			//otherDbMapperIs = new FileInputStream(otherDbMapperPath);
			XPathParser newXpathParser = new XPathParser(otherDbMapperResource.getInputStream(), true, configuration.getVariables(), new XMLMapperEntityResolver());
			XNode otherDbMapperXNode = newXpathParser.evalNode("/mapper");//otherDbMapperXNode 获取其它数据库mapper下面所有的Element
			if(!StringUtils.hasLength(otherDbMapperXNode.toString())){
				logger.debug("");
			}

			//将其它数据库mapper.xml中的element合并到oracle mapper.xml
			for (XNode otherDbXNode : otherDbMapperXNode.evalNodes("select|insert|update|delete|parameterMap|resultMap|sql")) {
				for (XNode oracleXNode : orclMapperXNode.evalNodes("select|insert|update|delete|parameterMap|resultMap|sql")) {
					if (otherDbXNode.getNode().getAttributes().getNamedItem("id").getNodeValue().equals(oracleXNode.getNode().getAttributes().getNamedItem("id").getNodeValue())) {
						//假如id相同则合并到oracle mapper.xml
						orclMapperXNode.getNode().replaceChild(doc.adoptNode(otherDbXNode.getNode()), oracleXNode.getNode());
						break;
					}
				}
			}
			
			OutputFormat format = new OutputFormat(doc);
			format.setEncoding("UTF-8");
			format.setStandalone(true);
			format.setIndenting(true);
			format.setIndent(2);
			format.setLineSeparator(LineSeparator.Windows);
			strResponse = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(strResponse, format);
			try {
				serializer.asDOMSerializer();
				serializer.serialize(doc);
			} catch (IOException ex1) {
			}
			StringBuffer sb = strResponse.getBuffer();
			// 再由String转成InputStream
			return new ByteArrayInputStream(sb.toString().getBytes());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
            if (strResponse != null)
            	strResponse.close();
		}
	}
}

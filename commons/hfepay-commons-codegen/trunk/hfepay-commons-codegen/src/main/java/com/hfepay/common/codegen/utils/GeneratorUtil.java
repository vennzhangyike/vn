package com.hfepay.common.codegen.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;

import com.hfepay.common.codegen.manager.ProjectManager;

public final class GeneratorUtil {

	public static Map<String, String> properties = new TreeMap<String, String>();
	private static ArrayList<TableConfiguration> tableConfigurations = new ArrayList<TableConfiguration>();
	private static Map<String, FullyQualifiedJavaType> entityFullNameMap = new HashMap<String, FullyQualifiedJavaType>();
	private static IntrospectedTable introspectedTable;
	private static Interface daoInterfaze;
	private static TopLevelClass daoClass;
	private static Interface serviceInterfaze;
	private static TopLevelClass serviceClass;
	private static TopLevelClass actionClass;
	private static TopLevelClass entityClass;
	
	private static Map<Context, ProjectManager> cpMapper = new HashMap<Context, ProjectManager>();

	public static String getGeneratedString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return "\n    @com.hfepay.commons.base.annotation.Generated(\""
				+ sdf.format(new Date(System.currentTimeMillis())) + "\")";
	}

	public static void addTableConfiguration(TableConfiguration tblConfig) {
		tableConfigurations.add(tblConfig);
	}

	public static List<TableConfiguration> getTableConfigurations() {
		return tableConfigurations;
	}

	public static void addEntityFullName(String shortName,
			FullyQualifiedJavaType javaType) {
		entityFullNameMap.put(shortName, javaType);
	}

	public static FullyQualifiedJavaType getEntityJavaType(String shortName) {
		return entityFullNameMap.get(shortName);
	}

	/**
	 * 驼峰命名转 下划线命名
	 * 
	 * @param param
	 * @return
	 */
	public static String camel2Underline(String param) {
		Pattern p = Pattern.compile("[A-Z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		int i = 0;
		while (mc.find()) {
			builder.replace(mc.start() + i, mc.end() + i, "_"
					+ mc.group().toLowerCase());
			i++;
		}

		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}
	
	/**
	 * 驼峰命名转中间划线命名
	 * @param param
	 * @return
	 */
	public static String camel2Bar(String param) {
		Pattern p = Pattern.compile("[A-Z]");
		if (param == null || param.equals("")) {
			return "";
		}
		StringBuilder builder = new StringBuilder(param);
		Matcher mc = p.matcher(param);
		int i = 0;
		while (mc.find()) {
			builder.replace(mc.start() + i, mc.end() + i, "-"
					+ mc.group().toLowerCase());
			i++;
		}

		if ('_' == builder.charAt(0)) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}

	public static <T> List<T> list(T... objs) {

		List<T> l = new ArrayList<T>();
		for (T o : objs) {
			l.add(o);
		}
		return l;
	}

	public static IntrospectedTable getIntrospectedTable() {
		return introspectedTable;
	}

	public static void setIntrospectedTable(IntrospectedTable introspectedTable) {
		GeneratorUtil.introspectedTable = introspectedTable;
	}
	
	public static Map<String, FullyQualifiedJavaType> getEntityFullNameMap() {
		return entityFullNameMap;
	}

	public static Interface getDaoInterfaze() {
		return daoInterfaze;
	}

	public static TopLevelClass getDaoClass() {
		return daoClass;
	}

	public static Interface getServiceInterfaze() {
		return serviceInterfaze;
	}

	public static TopLevelClass getActionClass() {
		return actionClass;
	}

	public static TopLevelClass getEntityClass() {
		return entityClass;
	}

	public static void setEntityFullNameMap(
			Map<String, FullyQualifiedJavaType> entityFullNameMap) {
		GeneratorUtil.entityFullNameMap = entityFullNameMap;
	}

	public static void setDaoInterfaze(Interface daoInterfaze) {
		GeneratorUtil.daoInterfaze = daoInterfaze;
	}

	public static void setDaoClass(TopLevelClass daoClass) {
		GeneratorUtil.daoClass = daoClass;
	}

	public static void setServiceInterfaze(Interface serviceInterfaze) {
		GeneratorUtil.serviceInterfaze = serviceInterfaze;
	}

	public static void setActionClass(TopLevelClass actionClass) {
		GeneratorUtil.actionClass = actionClass;
	}

	public static void setEntityClass(TopLevelClass entityClass) {
		GeneratorUtil.entityClass = entityClass;
	}
	
	public static TopLevelClass getServiceClass() {
		return serviceClass;
	}

	public static void setServiceClass(TopLevelClass serviceClass) {
		GeneratorUtil.serviceClass = serviceClass;
	}
	
	/**
	 * cpMapper.
	 *
	 * @return the cpMapper
	 * @since JDK 1.6
	 */
	public static Map<Context, ProjectManager> getCpMapper() {
		return cpMapper;
	}
	
	public static ProjectManager getDefaultProjectManager() {
		Set<Entry<Context, ProjectManager>> entrySet = cpMapper.entrySet();
		if(entrySet.size()>0){
			Iterator<Entry<Context, ProjectManager>> iterator = entrySet.iterator();
			if(iterator.hasNext()){
				return iterator.next().getValue();
			}
		}
		return null;
	}
	
	/**
	 * cpMapper.
	 *
	 * @param cpMapper	the cpMapper to set
	 * @since JDK 1.6
	 */
	public static void setCpMapper(Map<Context, ProjectManager> cpMapper) {
		GeneratorUtil.cpMapper = cpMapper;
	}

	/**
	 * 
	 * isMultiModule <br/>
	 *
	 * @author hfepay
	 * @param context
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isMultiModule(Context context) {
		if (GeneratorUtil.cpMapper.get(context) != null) {
			if (GeneratorUtil.cpMapper.get(context).isMultiModule()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}

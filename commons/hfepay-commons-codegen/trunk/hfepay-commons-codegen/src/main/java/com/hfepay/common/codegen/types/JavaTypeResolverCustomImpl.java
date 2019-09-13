package com.hfepay.common.codegen.types;

import java.sql.Timestamp;
import java.sql.Types;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class JavaTypeResolverCustomImpl extends JavaTypeResolverDefaultImpl {
	
	public JavaTypeResolverCustomImpl() {
		super();
	}
	
	@Override
	public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
		if(introspectedColumn.getJdbcType() == Types.DATE){
			return new FullyQualifiedJavaType(Timestamp.class.getName());
		}
		return super.calculateJavaType(introspectedColumn);
	}
	
	@Override
	public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
		if(introspectedColumn.getJdbcType() == Types.DATE){
			return "TIMESTAMP";
		}
		return super.calculateJdbcTypeName(introspectedColumn);
	}

}

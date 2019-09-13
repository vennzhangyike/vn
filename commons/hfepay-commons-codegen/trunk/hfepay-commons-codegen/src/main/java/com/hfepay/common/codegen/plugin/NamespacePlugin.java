package com.hfepay.common.codegen.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import com.hfepay.common.codegen.constants.Constants;
import com.hfepay.common.codegen.utils.GeneratorUtil;

public class NamespacePlugin implements Plugin {

  private Context context;
  private Properties properties;
  private JavaTypeResolver javaTypeResolver;
  private static Map<String, List<IntrospectedColumn>> otherColumnsMap = new HashMap<String, List<IntrospectedColumn>>();
  private static Map<String, List<Map<String, String>>> linkedProperties = new HashMap<String, List<Map<String, String>>>();

  public void setContext(Context context) {
    this.context = context;
  }

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  private IntrospectedColumn getColumn(String javaProperty, Map<String, String> details) {
    String localTableName = details.get("refTable");
    String tableAlias = details.get("tableAlias");
    String refColumn = details.get("refColumn");
    String columnAlias = details.get("columnAlias");
    String comment = details.get("comment");
    List<IntrospectedColumn> result = new ArrayList<IntrospectedColumn>();

    Connection connection = null;
    ResultSet rs = null;
    try {
      java.lang.reflect.Method method = Context.class.getDeclaredMethod("getConnection", new Class[] {});
      method.setAccessible(true);
      connection = (Connection) method.invoke(this.context, new Object[] {});
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    try{
      DatabaseMetaData databaseMetaData = connection.getMetaData();

     rs = databaseMetaData.getColumns(null, null, localTableName, null);

    while (rs.next()) {
      if(rs.getString("COLUMN_NAME").equals(refColumn)){
        IntrospectedColumn introspectedColumn = ObjectFactory.createIntrospectedColumn(context);

        introspectedColumn.setTableAlias(tableAlias);
        introspectedColumn.setJdbcType(rs.getInt("DATA_TYPE")); //$NON-NLS-1$
        introspectedColumn.setLength(rs.getInt("COLUMN_SIZE")); //$NON-NLS-1$
        introspectedColumn.setActualColumnName(rs.getString("COLUMN_NAME")); //$NON-NLS-1$
        introspectedColumn.setNullable(rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable); //$NON-NLS-1$
        introspectedColumn.setScale(rs.getInt("DECIMAL_DIGITS")); //$NON-NLS-1$
        introspectedColumn.setRemarks(comment); //$NON-NLS-1$
        introspectedColumn.setDefaultValue(rs.getString("COLUMN_DEF")); //$NON-NLS-1$
        introspectedColumn.setJavaProperty(javaProperty);
        Properties properties = new Properties();
        properties.setProperty("tableName", localTableName);
        properties.setProperty("columnAlias", columnAlias);
        introspectedColumn.setProperties(properties);
        FullyQualifiedJavaType fullyQualifiedJavaType = javaTypeResolver.calculateJavaType(introspectedColumn);

        if (fullyQualifiedJavaType != null) {
          introspectedColumn.setFullyQualifiedJavaType(fullyQualifiedJavaType);
          introspectedColumn.setJdbcTypeName(javaTypeResolver.calculateJdbcTypeName(introspectedColumn));
        }
        return introspectedColumn;
      }
    }

    }catch(Exception e){
      e.printStackTrace();
    }finally{
      if(null != rs){try {
        rs.close();
      } catch (SQLException e) {
      }}
      if(null != connection){
        try {
          connection.close();
        } catch (SQLException e) {
        }
      }
    }
    return null;
  }

  public void initialized(IntrospectedTable introspectedTable) {
	GeneratorUtil.setIntrospectedTable(introspectedTable);
    String modelNamespace = this.context.getProperty("modelNamespace");
    FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    String entityName = entityType.getShortName().substring(0, 1).toLowerCase() + entityType.getShortName().substring(1, entityType.getShortName().length());
    
    javaTypeResolver = ObjectFactory.createJavaTypeResolver(this.context, new ArrayList<String>());
    java.util.Properties linkColumnProps = introspectedTable.getTableConfiguration().getProperties();
    Enumeration enu = linkColumnProps.keys();
    while (enu.hasMoreElements()) {
      String javaProperty = (String) enu.nextElement();
      String value = (String) linkColumnProps.get(javaProperty);
      Map<String, String> details = decodeObject(value);
      details.put("javaProperty", javaProperty);
      if ("primitive".equals(details.get("type"))) {
        String localTableName = introspectedTable.getTableConfiguration().getTableName();
        IntrospectedColumn introspectedColumn = getColumn(javaProperty, details);
        if (null != introspectedColumn) {
          if (!otherColumnsMap.containsKey(localTableName)) {
            otherColumnsMap.put(localTableName, new ArrayList<IntrospectedColumn>());
          }
          otherColumnsMap.get(localTableName).add(introspectedColumn);
          String i18nKey = modelNamespace + "." + entityName + "_" + introspectedColumn.getJavaProperty();
          if(!GeneratorUtil.properties.containsKey(i18nKey)){
            GeneratorUtil.properties.put(i18nKey, introspectedColumn.getRemarks());
          }
        }
      }else if ("association".equals(details.get("type")) || "collection".equals(details.get("type"))){
        if(!this.linkedProperties.containsKey(introspectedTable.getTableConfiguration().getTableName())){
          this.linkedProperties.put(introspectedTable.getTableConfiguration().getTableName(), new ArrayList<Map<String, String>>());
        }
        this.linkedProperties.get(introspectedTable.getTableConfiguration().getTableName()).add(details);
        String i18nKey = modelNamespace + "." + entityName + "_" + javaProperty;
        if(!GeneratorUtil.properties.containsKey(i18nKey)){
          GeneratorUtil.properties.put(modelNamespace + "." + entityName + "_" + javaProperty, details.get("comment"));
        }
      }
    }
    System.out.println();
  }

  public boolean validate(List<String> warnings) {
    // TODO Auto-generated method stub
    return true;
  }

  public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return null;
  }

  public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientInsertMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
      ModelClassType modelClassType) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
      IntrospectedTable introspectedTable, ModelClassType modelClassType) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
      IntrospectedTable introspectedTable, ModelClassType modelClassType) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }
  
  private void generateBaseRecordClassProperty(String name, Map<String, String> properties, TopLevelClass topLevelClass){
    if(!"association".equals(properties.get("type")) && !"collection".equals(properties.get("type"))){
      return;
    }
    String select = properties.get("select");
    for(TableConfiguration tblConfig:GeneratorUtil.getTableConfigurations()){
      if(select.startsWith(tblConfig.getDomainObjectName() + ".")){
        //String entityType = this.context.getJavaModelGeneratorConfiguration().getTargetPackage() + "." + tblConfig.getDomainObjectName();
        FullyQualifiedJavaType entityList = new FullyQualifiedJavaType("java.util.List");
        entityList.addTypeArgument(GeneratorUtil.getEntityJavaType(tblConfig.getDomainObjectName()));
        
        Field field = null;
        if("association".equals(properties.get("type"))){
          field = new Field(name, GeneratorUtil.getEntityJavaType(tblConfig.getDomainObjectName()));
        }else if("collection".equals(properties.get("type"))){
          field = new Field(name, entityList);
          topLevelClass.addImportedType(entityList);
        }
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        topLevelClass.addMethod(JavaGenerator.getGetter(field));
        topLevelClass.addMethod(JavaGenerator.getSetter(field));
        topLevelClass.addImportedType(GeneratorUtil.getEntityJavaType(tblConfig.getDomainObjectName()));
      }
    }
  }

  public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    if(null != introspectedTable.getColumn("RECORD_STATUS")){
    	 FullyQualifiedJavaType recordStatus = null;
    	 FullyQualifiedJavaType idEntity = null;
	  if(GeneratorUtil.isMultiModule(context)){
		  recordStatus = new FullyQualifiedJavaType(Constants.RECORD_STATUS_MM);
		  idEntity = new FullyQualifiedJavaType(Constants.ID_ENTITY_MM);
	  }else{
		  recordStatus = new FullyQualifiedJavaType(Constants.RECORD_STATUS_SM);
		  idEntity = new FullyQualifiedJavaType(Constants.ID_ENTITY_SM);
	  }
      
      FullyQualifiedJavaType serializable = new FullyQualifiedJavaType("java.io.Serializable");
      idEntity.addTypeArgument(new FullyQualifiedJavaType("Long"));
      recordStatus.addTypeArgument(new FullyQualifiedJavaType("Long"));
      topLevelClass.addSuperInterface(idEntity);
      topLevelClass.addSuperInterface(recordStatus);
      topLevelClass.addSuperInterface(serializable);
      topLevelClass.addImportedType(recordStatus);
      topLevelClass.addImportedType(idEntity);
      topLevelClass.addImportedType(serializable);
    }
    List<IntrospectedColumn> columns = this.otherColumnsMap.get(introspectedTable.getTableConfiguration().getTableName());
    if(null != columns){
      for (IntrospectedColumn column:columns) {
        JavaGenerator generator = new JavaGenerator();
        generator.setContext(this.context);
        generator.setIntrospectedTable(introspectedTable);
        Field field = generator.getJavaBeansField(column);
        Method getMethod = generator.getJavaBeansGetter(column);
        Method setMethod = generator.getJavaBeansSetter(column);
        topLevelClass.addField(field);
        topLevelClass.addMethod(getMethod);
        topLevelClass.addMethod(setMethod);
      }
    }
    
    if(this.linkedProperties.containsKey(introspectedTable.getTableConfiguration().getTableName())){
      for(Map<String, String> map:this.linkedProperties.get(introspectedTable.getTableConfiguration().getTableName())){
        generateBaseRecordClassProperty(map.get("javaProperty"), map, topLevelClass);
      }
    }
    
    generateEntityAnnotation(topLevelClass, introspectedTable);
    
    for(Field field:topLevelClass.getFields()){
      field.addAnnotation(GeneratorUtil.getGeneratedString());
    }
    for(Method method:topLevelClass.getMethods()){
      method.addAnnotation(GeneratorUtil.getGeneratedString());
    }
    topLevelClass.addAnnotation(GeneratorUtil.getGeneratedString());
    
    GeneratorUtil.setEntityClass(topLevelClass);
    return true;
  }
  
  private void generateEntityAnnotation(TopLevelClass topLevelClass, IntrospectedTable introspectedTable){
    for (InnerClass answer : topLevelClass.getInnerClasses()) {
      answer.getMethods().removeAll(answer.getMethods());
      answer.getFields().removeAll(answer.getFields());
      answer.getInnerClasses().removeAll(answer.getInnerClasses());
    }
    //topLevelClass.getMethods().removeAll(topLevelClass.getMethods());
    //topLevelClass.getFields().removeAll(topLevelClass.getFields());
    //topLevelClass.getInnerClasses().removeAll(topLevelClass.getInnerClasses());
    StringBuffer sb = new StringBuffer();
    sb.append("@SelectColumnMappings({\n");
    for(IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()){
      sb.append("  @SelectColumnMapping(");
      sb.append("property = \"" + column.getJavaProperty() + "\", ");
      sb.append("type = " + column.getFullyQualifiedJavaType().getFullyQualifiedName() + ".class, ");
      sb.append("table = \"" + introspectedTable.getTableConfiguration().getTableName() + "\", ");
      sb.append("tableAlias = \"" + introspectedTable.getTableConfiguration().getAlias() + "\", ");
      sb.append("column = \"" + column.getActualColumnName() + "\"");
      sb.append("), \n");
      /*Field field = new Field(camel2Underline(column.getJavaProperty()).toUpperCase(), new FullyQualifiedJavaType("java.lang.String"));
      field.setFinal(true);
      field.setStatic(true);
      field.setVisibility(JavaVisibility.PUBLIC);
      field.setInitializationString("\"" + column.getJavaProperty() + "\"");
      topLevelClass.addField(field);*/
    }
    for (IntrospectedColumn column : introspectedTable.getBaseColumns()) {
      sb.append("  @SelectColumnMapping(");
      sb.append("property = \"" + column.getJavaProperty() + "\", ");
      sb.append("type = " + column.getFullyQualifiedJavaType().getFullyQualifiedName() + ".class, ");
      sb.append("table = \"" + introspectedTable.getTableConfiguration().getTableName() + "\", ");
      sb.append("tableAlias = \"" + introspectedTable.getTableConfiguration().getAlias() + "\", ");
      sb.append("column = \"" + column.getActualColumnName() + "\"");
      sb.append("), \n");
      /*Field field = new Field(camel2Underline(column.getJavaProperty()).toUpperCase(), new FullyQualifiedJavaType("java.lang.String"));
      field.setFinal(true);
      field.setStatic(true);
      field.setVisibility(JavaVisibility.PUBLIC);
      field.setInitializationString("\"" + column.getJavaProperty() + "\"");
      topLevelClass.addField(field);*/
    }

    List<IntrospectedColumn> columns = this.otherColumnsMap.get(introspectedTable.getTableConfiguration().getTableName());
    if(null != columns){
      for (IntrospectedColumn column:columns) {
        //String javaProperty = (String) enu.nextElement();
        //String value = (String) linkColumnProps.get(javaProperty);
        //Map<String, String> details = decodeObject(value);
        //String refTable = details.get("refTable");
        //String tableAlias = details.get("tableAlias");
        //String refColumn = details.get("refColumn");
        //String columnAlias = details.get("columnAlias");
        //String clazz = details.get("clazz");
        sb.append("  @SelectColumnMapping(");
        sb.append("property = \"" + column.getJavaProperty() + "\", ");
        sb.append("type = " + column.getFullyQualifiedJavaType().getFullyQualifiedName() + ".class, ");
        sb.append("table = \"" + column.getProperties().getProperty("tableName") + "\", ");
        sb.append("tableAlias = \"" + column.getTableAlias() + "\", ");
        sb.append("column = \"" + column.getActualColumnName() + "\"");
        sb.append("), \n");
        //Field field = new Field(camel2Underline(column.getJavaProperty()).toUpperCase(), new FullyQualifiedJavaType("java.lang.String"));
        //field.setFinal(true);
        //field.setStatic(true);
        //field.setVisibility(JavaVisibility.PUBLIC);
        //field.setInitializationString("\"" + column.getJavaProperty() + "\"");
        //topLevelClass.addField(field);
      }
    }

    if (sb.toString().endsWith(", \n")) {
      sb.delete(sb.toString().lastIndexOf(", \n"), sb.length());
      sb.append("\n");
    }

    sb.append("})");
    topLevelClass.addAnnotation(sb.toString());
    //FullyQualifiedJavaType supperClass = new FullyQualifiedJavaType("com.kingnod.core.ssm.entity.BaseExample");
    //topLevelClass.setSuperClass(supperClass);
    //topLevelClass.addImportedType(new FullyQualifiedJavaType("com.kingnod.core.ssm.entity.BaseExample"));
    
    if(GeneratorUtil.isMultiModule(context)){
    	topLevelClass.addImportedType(new FullyQualifiedJavaType(Constants.SELECT_COLUMN_MAPPINGS_MM));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(Constants.SELECT_COLUMN_MAPPING_MM));
    }else{
    	topLevelClass.addImportedType(new FullyQualifiedJavaType(Constants.SELECT_COLUMN_MAPPINGS_SM));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(Constants.SELECT_COLUMN_MAPPING_SM));
    }

  }

  public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    return false;
  }

  public static String camel2Underline(String param) {
    Pattern p = Pattern.compile("[A-Z]");
    if (param == null || param.equals("")) {
      return "";
    }
    StringBuilder builder = new StringBuilder(param);
    Matcher mc = p.matcher(param);
    int i = 0;
    while (mc.find()) {
      builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
      i++;
    }

    if ('_' == builder.charAt(0)) {
      builder.deleteCharAt(0);
    }
    return builder.toString();
  }

  private static HashMap decodeObject(String string) {
    HashMap properties = new HashMap();
    try {
      StringTokenizer tokenizer = new StringTokenizer(string, ";");
      while (tokenizer.hasMoreTokens()) {
        String tokenString = tokenizer.nextToken();
        if (null != tokenString && tokenString.length() > 0 && tokenString.indexOf('=') > 0) {
          String name = tokenString.substring(0, tokenString.indexOf('='));
          String value = tokenString.substring(tokenString.indexOf('=') + 1);
          if (null != tokenString && tokenString.length() > 0) {
            name = name.trim();
            if (null != tokenString && tokenString.length() > 0) {
              value = value.trim();
            }
            properties.put(name, value);
          }
        }
      }
    } catch (Exception e) {
      // log.error(e.getMessage(), e);
    }
    return properties;
  }

  public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
    return true;
  }

  public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
    document.getRootElement().getAttributes().remove(0);
    document.getRootElement().addAttribute(new Attribute("namespace", introspectedTable.getTableConfiguration().getDomainObjectName()));
    return true;
  }
  
  private XmlElement generateLinkedPropertyXmlElement(String name, Map<String, String> properties){
    XmlElement xmlElement = null;
    if(!"association".equals(properties.get("type")) && !"collection".equals(properties.get("type"))){
      return xmlElement;
    }
    String select = properties.get("select");
    for(TableConfiguration tblConfig:GeneratorUtil.getTableConfigurations()){
      if(select.startsWith(tblConfig.getDomainObjectName() + ".")){
        String entityType = this.context.getJavaModelGeneratorConfiguration().getTargetPackage() + "." + tblConfig.getDomainObjectName();
        xmlElement = new XmlElement(properties.get("type"));
        xmlElement.addAttribute(new Attribute("property", name));
        xmlElement.addAttribute(new Attribute("column", properties.get("column")));
        xmlElement.addAttribute(new Attribute("select", select));
        return xmlElement;
      }
    }
    return null;
  }

  public boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    List<IntrospectedColumn> columns = this.otherColumnsMap.get(introspectedTable.getTableConfiguration().getTableName());
    if (null != columns) {
      for (IntrospectedColumn column : columns) {
        XmlElement resultElement = new XmlElement("result"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        //sb.append(column.getTableAlias());
        //sb.append('_');
        sb.append(column.getProperties().get("columnAlias"));
        resultElement.addAttribute(new Attribute("column", sb.toString())); //$NON-NLS-1$
        resultElement.addAttribute(new Attribute("property", column.getJavaProperty())); //$NON-NLS-1$
        resultElement.addAttribute(new Attribute("jdbcType", column.getJdbcTypeName()));

        if (stringHasValue(column.getTypeHandler())) {
          resultElement.addAttribute(new Attribute("typeHandler", column.getTypeHandler())); //$NON-NLS-1$
        }

        element.addElement(resultElement);
      }
    }
    
    if(this.linkedProperties.containsKey(introspectedTable.getTableConfiguration().getTableName())){
      for(Map<String, String> map:this.linkedProperties.get(introspectedTable.getTableConfiguration().getTableName())){
        if("association".equals(map.get("type"))){
          XmlElement xmlElement = generateLinkedPropertyXmlElement(map.get("javaProperty"), map);
          if(null != xmlElement){
            element.addElement(xmlElement);
          }
        }
      }
      for(Map<String, String> map:this.linkedProperties.get(introspectedTable.getTableConfiguration().getTableName())){
        if("collection".equals(map.get("type"))){
          XmlElement xmlElement = generateLinkedPropertyXmlElement(map.get("javaProperty"), map);
          if(null != xmlElement){
            element.addElement(xmlElement);
          }
        }
      }
    }
    
    
    return true;
  }

  public boolean sqlMapCountByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    element.getAttributes().clear();
    element.getElements().clear();
    element.addAttribute(new Attribute("id", "countByCriteria"));
    
    String criteria = null;
    if(GeneratorUtil.isMultiModule(context)){
    	criteria = Constants.CRITERIA_MM;
    }else{
    	criteria = Constants.CRITERIA_SM;
    }

    element.addAttribute(new Attribute("parameterType", criteria));
    element.addAttribute(new Attribute("resultType", "java.lang.Integer"));
    context.getCommentGenerator().addComment(element);
    GeneratedKey key = introspectedTable.getTableConfiguration().getGeneratedKey();
    String primaryKey = null!=key?key.getColumn():"*";
    element.addElement(new TextElement("select count("+primaryKey+") from "+introspectedTable.getTableConfiguration().getTableName()+" " + introspectedTable.getTableConfiguration().getAlias()));
    XmlElement ifelement = new XmlElement("if");
    ifelement.addAttribute(new Attribute("test", "_parameter != null"));
    element.addElement(ifelement);
    
    XmlElement where = new XmlElement("where");
    XmlElement include = new XmlElement("include");
    include.addAttribute(new Attribute("refid", "Global.Where_Clause"));
    where.addElement(include);
    ifelement.addElement(where);
    return true;
  }

  public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    element.getAttributes().clear();
    element.addAttribute(new Attribute("id", "deleteByCriteria"));
    
    String criteria = null;
    if(GeneratorUtil.isMultiModule(context)){
    	criteria = Constants.CRITERIA_MM;
    }else{
    	criteria = Constants.CRITERIA_SM;
    }
    
    element.addAttribute(new Attribute("parameterType", criteria));
    context.getCommentGenerator().addComment(element);
    
    for(Object ele:element.getElements()){
      if(ele instanceof XmlElement && ((XmlElement)ele).getName().equals("if")){
        XmlElement ifelement = (XmlElement)ele;
        XmlElement where = new XmlElement("where");
        XmlElement include = (XmlElement)ifelement.getElements().get(0);
        ifelement.getElements().remove(include);
        include.getAttributes().clear();
        include.addAttribute(new Attribute("refid", "Global.Where_Clause"));
        where.addElement(include);
        ifelement.addElement(where);
      }
    }
    return true;
  }

  public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    Attribute idAttr = null;
    for(Attribute attr:element.getAttributes()){
      if("id".equals(attr.getName())){
        idAttr = attr;
        break;
      }
    }
    element.getAttributes().remove(idAttr);
    element.addAttribute(new Attribute("id", "deleteById"));
    return true;
  }

  public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
	if(true)return false;
    if(introspectedTable.getMyBatis3UpdateByExampleWhereClauseId().equals(element.getAttributes().get(0).getValue())){
      element.getAttributes().clear();
      element.getElements().clear();
      return false;
    }
    element.getAttributes().clear();
    element.addAttribute(new Attribute("id", "Global.Where_Clause"));//设置where条件的sql id
    org.mybatis.generator.api.dom.xml.XmlElement xmlElement = null;
    for(Object ele:element.getElements()){
      if(ele instanceof org.mybatis.generator.api.dom.xml.XmlElement){
        xmlElement = (org.mybatis.generator.api.dom.xml.XmlElement)ele;
      }
    }
    element.getElements().remove(xmlElement);
    XmlElement whereElement = new XmlElement("where"); //$NON-NLS-1$
    element.addElement(whereElement);
    XmlElement outerForEachElement = new XmlElement("foreach"); //$NON-NLS-1$
    outerForEachElement.addAttribute(new Attribute("collection", "conditions")); //$NON-NLS-1$ //$NON-NLS-2$
    outerForEachElement.addAttribute(new Attribute("item", "condition")); //$NON-NLS-1$ //$NON-NLS-2$
    whereElement.addElement(outerForEachElement);
    
    if(true){
      XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
      ifElement.addAttribute(new Attribute("test", "condition.group")); //$NON-NLS-1$ //$NON-NLS-2$
      outerForEachElement.addElement(ifElement);
      
      ifElement.addElement(new org.mybatis.generator.api.dom.xml.TextElement(" ${condition.logic} "));
      
      XmlElement forEachIfElement1 = new XmlElement("foreach"); //$NON-NLS-1$
      forEachIfElement1.addAttribute(new Attribute("collection", "condition.all")); //$NON-NLS-1$ //$NON-NLS-2$
      forEachIfElement1.addAttribute(new Attribute("item", "criterion")); //$NON-NLS-1$ //$NON-NLS-2$
      forEachIfElement1.addAttribute(new Attribute("open", "(")); //$NON-NLS-1$ //$NON-NLS-2$
      forEachIfElement1.addAttribute(new Attribute("close", ")")); //$NON-NLS-1$ //$NON-NLS-2$
      ifElement.addElement(forEachIfElement1);
      
      XmlElement chosseIfElement1 = new XmlElement("choose"); //$NON-NLS-1$
      forEachIfElement1.addElement(chosseIfElement1);
      
      if(true){
        XmlElement when = new XmlElement("when"); //$NON-NLS-1$
        when.addAttribute(new Attribute("test", "criterion.between")); //$NON-NLS-1$ //$NON-NLS-2$
        when.addElement(new org.mybatis.generator.api.dom.xml.TextElement(" ${criterion.expression} #{criterion.value.start} and #{criterion.value.end} "));
        chosseIfElement1.addElement(when);
      }
      if(true){
        XmlElement when = new XmlElement("when"); //$NON-NLS-1$
        when.addAttribute(new Attribute("test", "criterion.list")); //$NON-NLS-1$ //$NON-NLS-2$
        when.addElement(new org.mybatis.generator.api.dom.xml.TextElement(" ${criterion.expression} "));
        XmlElement foreach = new XmlElement("foreach"); //$NON-NLS-1$
        foreach.addAttribute(new Attribute("collection", "criterion.value"));
        foreach.addAttribute(new Attribute("open", "("));
        foreach.addAttribute(new Attribute("close", ")"));
        foreach.addAttribute(new Attribute("item", "inItem"));
        foreach.addAttribute(new Attribute("separator", ","));
        foreach.addElement(new TextElement("#{inItem}"));
        when.addElement(foreach);
        chosseIfElement1.addElement(when);
      }
      if(true){
    	  XmlElement when = new XmlElement("when"); //$NON-NLS-1$
          when.addAttribute(new Attribute("test", "criterion.none")); //$NON-NLS-1$ //$NON-NLS-2$
          when.addElement(new org.mybatis.generator.api.dom.xml.TextElement(" ${criterion.expression} "));
          chosseIfElement1.addElement(when);  
      }
      if(true){
          XmlElement when = new XmlElement("otherwise"); //$NON-NLS-1$
          when.addElement(new org.mybatis.generator.api.dom.xml.TextElement(" ${criterion.expression} #{criterion.value} "));
          chosseIfElement1.addElement(when);
      }
    }
    
    if(true){
      XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
      ifElement.addAttribute(new Attribute("test", "condition.group == false")); //$NON-NLS-1$ //$NON-NLS-2$
      outerForEachElement.addElement(ifElement);
      
      XmlElement choose = new XmlElement("choose"); //$NON-NLS-1$
      ifElement.addElement(choose);
      
      if(true){
        XmlElement when = new XmlElement("when"); //$NON-NLS-1$
        when.addAttribute(new Attribute("test", "condition.between")); //$NON-NLS-1$ //$NON-NLS-2$
        when.addElement(new org.mybatis.generator.api.dom.xml.TextElement("${condition.expression} #{condition.value.start} and #{condition.value.end}"));
        choose.addElement(when);
      }
      if(true){
        XmlElement when = new XmlElement("when"); //$NON-NLS-1$
        when.addAttribute(new Attribute("test", "condition.list")); //$NON-NLS-1$ //$NON-NLS-2$
        when.addElement(new org.mybatis.generator.api.dom.xml.TextElement("${condition.expression}"));
        XmlElement foreach = new XmlElement("foreach"); //$NON-NLS-1$
        foreach.addAttribute(new Attribute("collection", "condition.value"));
        foreach.addAttribute(new Attribute("open", "("));
        foreach.addAttribute(new Attribute("close", ")"));
        foreach.addAttribute(new Attribute("item", "inItem"));
        foreach.addAttribute(new Attribute("separator", ","));
        foreach.addElement(new TextElement("#{inItem}"));
        when.addElement(foreach);
        choose.addElement(when);
      }
      if(true){
        XmlElement when = new XmlElement("when"); //$NON-NLS-1$
        when.addAttribute(new Attribute("test", "condition.none")); //$NON-NLS-1$ //$NON-NLS-2$
        when.addElement(new org.mybatis.generator.api.dom.xml.TextElement("${condition.expression} "));
        choose.addElement(when);
      }
      if(true){
          XmlElement when = new XmlElement("otherwise"); //$NON-NLS-1$
          when.addElement(new org.mybatis.generator.api.dom.xml.TextElement(" ${condition.expression} #{condition.value} "));
          choose.addElement(when);
      }
    }
    
    
    return true;
  }

  public boolean sqlMapBaseColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean sqlMapBlobColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    return false;
  }

  public boolean sqlMapResultMapWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    Attribute idAttr = null;
    for(Attribute attr:element.getAttributes()){
      if("id".equals(attr.getName())){
        idAttr = attr;
        break;
      }
    }
    element.getAttributes().remove(idAttr);
    element.addAttribute(new Attribute("id", "findById"));
    return true;
  }

  public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    element.getAttributes().clear();
    element.getElements().clear();
    element.addAttribute(new Attribute("id", "findByCriteria"));
    
    String criteria = null;
    if(GeneratorUtil.isMultiModule(context)){
    	criteria = Constants.CRITERIA_MM;
    }else{
    	criteria = Constants.CRITERIA_SM;
    }
    element.addAttribute(new Attribute("parameterType", criteria));
    element.addAttribute(new Attribute("resultMap", "BaseResultMap"));
    context.getCommentGenerator().addComment(element);
    
    element.addElement(new TextElement("select"));
    /*if(true){
      XmlElement ifelement = new XmlElement("if");
      ifelement.addAttribute(new Attribute("test", "distinct"));
      ifelement.addElement(new TextElement("distinct"));
      element.addElement(ifelement);
    }*/
    
    if(true){
      XmlElement include = new XmlElement("include");
      include.addAttribute(new Attribute("refid", "Base_Column_List"));
      element.addElement(include);
    }
    element.addElement(new TextElement(" from " + introspectedTable.getTableConfiguration().getTableName() + " " + introspectedTable.getTableConfiguration().getAlias()));
    if(true){
      XmlElement ifelement = new XmlElement("if");
      ifelement.addAttribute(new Attribute("test", "_parameter != null"));
      element.addElement(ifelement);
      XmlElement where = new XmlElement("where");
      XmlElement include = new XmlElement("include");
      include.addAttribute(new Attribute("refid", "Global.Where_Clause"));
      where.addElement(include);
      ifelement.addElement(where);
    }
    
    if(true){
      XmlElement ifelement = new XmlElement("if");
      ifelement.addAttribute(new Attribute("test", "hasOrderBy"));
      ifelement.addElement(new TextElement("order by"));
      element.addElement(ifelement);
      XmlElement foreach = new XmlElement("foreach");
      foreach.addAttribute(new Attribute("collection", "orderBys"));
      foreach.addAttribute(new Attribute("item", "orderBy"));
      foreach.addAttribute(new Attribute("separator", ","));
      foreach.addElement(new TextElement("${orderBy}"));
      ifelement.addElement(foreach);
    }
    return true;
  }

  public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    return true;
  }

  public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    element.getAttributes().clear();
    element.addAttribute(new Attribute("id", "updateByCriteria"));
    element.addAttribute(new Attribute("parameterType", "map"));
    
    XmlElement ifelement = (XmlElement)element.getElements().get(element.getElements().size()-1);
	
    XmlElement where = new XmlElement("where");
    XmlElement include = (XmlElement)ifelement.getElements().get(0);
    ifelement.getElements().remove(include);
    include.getAttributes().clear();
    include.addAttribute(new Attribute("refid", "Global.Where_Clause"));
    where.addElement(include);
    ifelement.addElement(where);
    
    /*for(Object ele:element.getElements()){
      if(ele instanceof XmlElement){
        for(Object innerele:((XmlElement)ele).getElements()){
          if(innerele instanceof XmlElement){
            if(((XmlElement) innerele).getName().equals("if")){
            	XmlElement ifelement = (XmlElement)innerele;
            	
            	boolean iswhereif = false;
            	for(Attribute arri:ifelement.getAttributes()){
            		if("test".equals(arri.getName()) && "_parameter != null".equals(arri.getValue())){
            			iswhereif = true;
            			break;
            		}
            	}
            	if(!iswhereif){
            		continue;
            	}
            	
                XmlElement where = new XmlElement("where");
                XmlElement include = (XmlElement)ifelement.getElements().get(0);
                ifelement.getElements().remove(include);
                include.getAttributes().clear();
                include.addAttribute(new Attribute("refid", "Global.Where_Clause"));
                where.addElement(include);
                ifelement.addElement(where);
            	
            	
              //((XmlElement) innerele).getAttributes().clear();
              //((XmlElement) innerele).getAttributes().add(new Attribute("refid", "Global.Where_Clause"));
            }
          }
        }
      }
    }*/
    return true;
  }

  public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    return false;
  }

  public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    return false;
  }

	public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		return false;
	}

  public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    return false;
  }

  public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
    element.getAttributes().clear();
	element.getElements().clear();
	XmlElement answer = element; //$NON-NLS-1$

	answer.addAttribute(new Attribute(
			"id", "update")); //$NON-NLS-1$

	answer.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType())); //$NON-NLS-1$ //$NON-NLS-2$

	context.getCommentGenerator().addComment(answer);

	StringBuilder sb = new StringBuilder();
	sb.append("update "); //$NON-NLS-1$
	sb.append(introspectedTable
			.getAliasedFullyQualifiedTableNameAtRuntime());
	answer.addElement(new TextElement(sb.toString()));

	XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
	answer.addElement(dynamicElement);

	for (IntrospectedColumn introspectedColumn : introspectedTable
			.getAllColumns()) {
		XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
		sb.setLength(0);
		sb.append(introspectedColumn.getJavaProperty("")); //$NON-NLS-1$
		sb.append(" != null"); //$NON-NLS-1$
		isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
		dynamicElement.addElement(isNotNullElement);

		sb.setLength(0);
		sb.append(MyBatis3FormattingUtilities
				.getAliasedEscapedColumnName(introspectedColumn));
		sb.append(" = "); //$NON-NLS-1$
		sb.append(MyBatis3FormattingUtilities.getParameterClause(
				introspectedColumn, "")); //$NON-NLS-1$
		sb.append(',');

		isNotNullElement.addElement(new TextElement(sb.toString()));
	}
	for(IntrospectedColumn primaryKey:introspectedTable.getPrimaryKeyColumns()){
		answer.addElement(new TextElement("where "+MyBatis3FormattingUtilities.getAliasedEscapedColumnName(primaryKey)+" = #{id,jdbcType=DECIMAL}"));
	}
	return true;
  }

  public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    return false;
  }

  public boolean providerApplyWhereMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean providerCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean providerDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean providerInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean providerSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean providerSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean providerUpdateByExampleSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean providerUpdateByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean providerUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    // TODO Auto-generated method stub
    return true;
  }

}

class JavaGenerator extends AbstractJavaGenerator{

  public List<CompilationUnit> getCompilationUnits() {
    return null;
  }
  
  public static Method getSetter(Field field) {
    Method method = new Method();
    method.setName(JavaBeansUtil.getSetterMethodName(field.getName()));
    method.addParameter(new Parameter(field.getType(), field.getName()));
    method.setVisibility(JavaVisibility.PUBLIC);
    StringBuilder sb = new StringBuilder();
    sb.append("this."); //$NON-NLS-1$
    sb.append(field.getName());
    sb.append(" = "); //$NON-NLS-1$
    sb.append(field.getName());
    sb.append(';');
    method.addBodyLine(sb.toString());
    return method;
}
  
}
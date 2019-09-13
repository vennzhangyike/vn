package com.hfepay.common.codegen.utils;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import com.hfepay.common.codegen.constants.Constants;
import com.hfepay.common.codegen.manager.ProjectManager;

public class DaoJavaClientGenerator extends AbstractJavaClientGenerator {

  public DaoJavaClientGenerator() {
    super(true);
  }

  public DaoJavaClientGenerator(boolean requiresMatchedXMLGenerator) {
    super(requiresMatchedXMLGenerator);
  }

  public AbstractXmlGenerator getMatchedXMLGenerator() {
    return new XMLMapperGenerator();
  }

  @Override
  public List<CompilationUnit> getCompilationUnits() {
    progressCallback.startTask(getString("Progress.17", introspectedTable.getFullyQualifiedTable().toString()));
    List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
    Interface daoInterfaze = getDAOInterface();
    GeneratorUtil.setDaoInterfaze(daoInterfaze);
    if (context.getPlugins().clientGenerated(daoInterfaze, null, introspectedTable)) {
      answer.add(daoInterfaze);
    }
    TopLevelClass daoClass = getDAOClass();
    GeneratorUtil.setDaoClass(daoClass);
    if (context.getPlugins().clientGenerated(daoInterfaze, daoClass, introspectedTable)) {
      answer.add(daoClass);
    }
    Interface serviceInterfaze = getServiceInterface();
    GeneratorUtil.setServiceInterfaze(serviceInterfaze);
    if (context.getPlugins().clientGenerated(serviceInterfaze, null, introspectedTable)) {
      answer.add(serviceInterfaze);
    }
    TopLevelClass serviceClass = getServiceClass();
    GeneratorUtil.setServiceClass(serviceClass);
    /*if (context.getPlugins().clientGenerated(null, serviceClass, introspectedTable)) {
      answer.add(serviceClass);
    }*/
    

    TopLevelClass actionClass = getActionClass(serviceInterfaze);
    GeneratorUtil.setActionClass(actionClass);
    if (context.getPlugins().clientGenerated(null, actionClass, introspectedTable)) {
      answer.add(actionClass);
    }

    addProperties();

    return answer;
  }

  protected void addProperties() {
    
    List<String> recordStatus = new ArrayList();
    recordStatus.add("id");
    recordStatus.add("recordStatus");
    recordStatus.add("updateCount");
    recordStatus.add("creatorId");
    recordStatus.add("createDate");
    recordStatus.add("updaterId");
    recordStatus.add("updateDate");
    String modelNamespace = this.context.getProperty("modelNamespace");
    FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    String entityName = entityType.getShortName().substring(0, 1).toLowerCase() + entityType.getShortName().substring(1, entityType.getShortName().length());
    for(IntrospectedColumn column : introspectedTable.getAllColumns()){
      if(!recordStatus.contains(column.getJavaProperty())){
    	  String i18nKey = modelNamespace + "." + entityName + "_" + column.getJavaProperty();
    	  if(!GeneratorUtil.properties.containsKey(i18nKey)){
    		  GeneratorUtil.properties.put(modelNamespace + "." + entityName + "_" + column.getJavaProperty(), column.getRemarks());
    	  }
      }
    }
    
  }

  protected TopLevelClass getActionClass(Interface serviceInterfaze) {
    String namespace = this.context.getProperty("modelNamespace");
    FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getDAOInterfaceType());
    FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    String entityName = entityType.getShortName().substring(0, 1).toLowerCase() + entityType.getShortName().substring(1, entityType.getShortName().length());
    type = new FullyQualifiedJavaType(type.getPackageName().substring(0, type.getPackageName().lastIndexOf(".dao")) + ".action." + entityType.getShortName()
        + "Action");
    String actionurl = GeneratorUtil.camel2Underline(entityType.getShortName()).replace('_', '-').toLowerCase();
    FullyQualifiedJavaType baseAction = new FullyQualifiedJavaType("com.kingnod.core.extensions.struts2.action.FilterableEntityAction");
    baseAction.addTypeArgument(entityType);
    for (IntrospectedColumn pkColumn : introspectedTable.getPrimaryKeyColumns()) {
      baseAction.addTypeArgument(pkColumn.getFullyQualifiedJavaType());
    }

    TopLevelClass innerClass = new TopLevelClass(type);
    innerClass.setVisibility(JavaVisibility.PUBLIC);
    innerClass.addAnnotation("@org.springframework.stereotype.Controller");
    innerClass.addAnnotation("@Namespace(\"/"+namespace+"\")");
    innerClass.addAnnotation("@Results( ");
    innerClass.addAnnotation("{");
    innerClass.addAnnotation("@Result(name = EntityAction.RELOAD, type = \"redirectAction\", params = { \"actionName\", \""+actionurl+"\", \"namespace\", \"/"+namespace + "\"})");
    innerClass.addAnnotation("}");
    innerClass.addAnnotation(")");
    innerClass.addAnnotation(GeneratorUtil.getGeneratedString());
    innerClass.setSuperClass(baseAction);

    Field serviceField = new Field();
    serviceField.setName(entityName + "Service");
    serviceField.setType(serviceInterfaze.getType());
    serviceField.setVisibility(JavaVisibility.PRIVATE);
    serviceField.addAnnotation("@Autowired");
    serviceField.addAnnotation(GeneratorUtil.getGeneratedString());
    innerClass.addField(serviceField);
    
    Method filterDefinesMethod = new Method();
    filterDefinesMethod.setName("filterDefines");
    filterDefinesMethod.setVisibility(JavaVisibility.PUBLIC);
    filterDefinesMethod.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
    filterDefinesMethod.addBodyLine("return \"" + namespace + "." + entityName + "FilterItems\";");
    filterDefinesMethod.addAnnotation(GeneratorUtil.getGeneratedString());
    innerClass.addMethod(filterDefinesMethod);

    Method serviceMethod = new Method();
    serviceMethod.setName("getEntityService");
    serviceMethod.setVisibility(JavaVisibility.PROTECTED);
    serviceMethod.setReturnType(new FullyQualifiedJavaType("java.lang.Object"));
    serviceMethod.addBodyLine("return this." + entityName + "Service" + ";");
    serviceMethod.addAnnotation(GeneratorUtil.getGeneratedString());
    innerClass.addMethod(serviceMethod);

    innerClass.addImportedType(new FullyQualifiedJavaType("org.apache.struts2.convention.annotation.Namespace"));
    innerClass.addImportedType(new FullyQualifiedJavaType("org.apache.struts2.convention.annotation.Result"));
    innerClass.addImportedType(new FullyQualifiedJavaType("org.apache.struts2.convention.annotation.Results"));
    innerClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
    innerClass.addImportedType(new FullyQualifiedJavaType("com.kingnod.core.extensions.struts2.action.FilterableEntityAction"));
    innerClass.addImportedType(new FullyQualifiedJavaType("com.kingnod.core.extensions.struts2.action.EntityAction"));
    innerClass.addImportedType(entityType);
    innerClass.addImportedType(serviceInterfaze.getType());
    return innerClass;
  }

  protected Interface getServiceInterface() {
    FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getDAOInterfaceType());
    FullyQualifiedJavaType criteriaType = new FullyQualifiedJavaType("com.kingnod.core.criteria.Criteria");
    type = new FullyQualifiedJavaType(type.getPackageName().substring(0, type.getPackageName().lastIndexOf(".dao")) + ".service." + entityType.getShortName()
        + "Service");
    Interface interfaze = new Interface(type);
    interfaze.setVisibility(JavaVisibility.PUBLIC);
    interfaze.addAnnotation(GeneratorUtil.getGeneratedString());
    CommentGenerator commentGenerator = context.getCommentGenerator();
    commentGenerator.addJavaFileComment(interfaze);

    FullyQualifiedJavaType pageType = new FullyQualifiedJavaType("com.kingnod.core.pager.PagingResult");
    pageType.addTypeArgument(entityType);
    FullyQualifiedJavaType entityListType = new FullyQualifiedJavaType("java.util.List");
    entityListType.addTypeArgument(entityType);
    String entityName = entityType.getShortName().substring(0, 1).toLowerCase() + entityType.getShortName().substring(1, entityType.getShortName().length());
    // 生成get方法
    Method getMethod = new Method("get" + entityType.getShortName());
    getMethod.setReturnType(entityType);
    getMethod.setVisibility(JavaVisibility.PUBLIC);
    getMethod.addAnnotation(GeneratorUtil.getGeneratedString());
    StringBuffer getMethodDoc = new StringBuffer();
    getMethodDoc.append("/**\n");
    getMethodDoc.append("     * 根据指定的主键获取" + entityType.getShortName() + "对象。\n");
    for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
      getMethod.addParameter(new Parameter(column.getFullyQualifiedJavaType(), column.getJavaProperty()));
      getMethodDoc.append("     * @param " + column.getJavaProperty() + "\n");
    }
    getMethodDoc.append("     * @return\n");
    getMethodDoc.append("     */");
    // getMethod.addJavaDocLine(getMethodDoc.toString());
    interfaze.addMethod(getMethod);
    // 生成saveEntity方法
    Method saveMethod = new Method("save" + entityType.getShortName());
    saveMethod.setReturnType(new FullyQualifiedJavaType("int"));
    saveMethod.addParameter(new Parameter(entityType, entityName));
    saveMethod.setVisibility(JavaVisibility.PUBLIC);
    saveMethod.addAnnotation(GeneratorUtil.getGeneratedString());
    StringBuffer saveMethodDoc = new StringBuffer();
    saveMethodDoc.append("/**\n");
    saveMethodDoc.append("     * 新增" + entityType.getShortName() + "对象。\n");
    saveMethodDoc.append("     * @param " + entityType.getShortName() + "\n");
    saveMethodDoc.append("     */");
    // saveMethod.addJavaDocLine(saveMethodDoc.toString());
    interfaze.addMethod(saveMethod);

    // 生成updateEntity方法
    Method updateMethod = new Method("update" + entityType.getShortName());
    updateMethod.setReturnType(entityType);
    updateMethod.addParameter(new Parameter(entityType, entityName));
    updateMethod.addAnnotation(GeneratorUtil.getGeneratedString());
    updateMethod.setVisibility(JavaVisibility.PUBLIC);
    StringBuffer updateMethodDoc = new StringBuffer();
    updateMethodDoc.append("/**\n");
    updateMethodDoc.append("     * 新增" + entityType.getShortName() + "对象。\n");
    updateMethodDoc.append("     * @param " + entityType.getShortName() + "\n");
    updateMethodDoc.append("     */");
    // updateMethod.addJavaDocLine(updateMethodDoc.toString());
    // interfaze.addMethod(updateMethod);

    // 生成delete方法
    Method deleteMethod = new Method("delete" + entityType.getShortName());
    deleteMethod.addAnnotation(GeneratorUtil.getGeneratedString());
    deleteMethod.setReturnType(new FullyQualifiedJavaType("int"));
    deleteMethod.setVisibility(JavaVisibility.PUBLIC);
    StringBuffer deleteMethodDoc = new StringBuffer();
    deleteMethod.addParameter(new Parameter(entityType, entityName));
    deleteMethodDoc.append("/**\n");
    deleteMethodDoc.append("     * 更新" + entityType.getShortName() + "对象。\n");
    deleteMethodDoc.append("     * @param " + entityType.getShortName() + "\n");
    deleteMethodDoc.append("     */");
    // deleteMethod.addJavaDocLine(deleteMethodDoc.toString());
    interfaze.addMethod(deleteMethod);

    // 生成deleteByIds方法
    Method deleteByIdsMethod = new Method("delete" + entityType.getShortName() + "ByIds");
    deleteByIdsMethod.addAnnotation(GeneratorUtil.getGeneratedString());
    deleteByIdsMethod.setReturnType(new FullyQualifiedJavaType("int"));
    deleteByIdsMethod.setVisibility(JavaVisibility.PUBLIC);
    StringBuffer deleteByIdsMethodDoc = new StringBuffer();
    for (IntrospectedColumn pkColumn : introspectedTable.getPrimaryKeyColumns()) {
      FullyQualifiedJavaType idsType = new FullyQualifiedJavaType(pkColumn.getFullyQualifiedJavaType() + "[]");
      // idsType.addTypeArgument(pkColumn.getFullyQualifiedJavaType());
      deleteByIdsMethod.addParameter(new Parameter(idsType, "ids"));
    }
    deleteByIdsMethodDoc.append("/**\n");
    deleteByIdsMethodDoc.append("     * 更新" + entityType.getShortName() + "对象。\n");
    deleteByIdsMethodDoc.append("     * @param " + entityType.getShortName() + "\n");
    deleteByIdsMethodDoc.append("     */");
    // deleteByIdsMethod.addJavaDocLine(deleteByIdsMethodDoc.toString());
    interfaze.addMethod(deleteByIdsMethod);

    // 生成findUser并分页的方法
    Method searchEntityMethod = new Method("find" + entityType.getShortName());
    searchEntityMethod.addAnnotation(GeneratorUtil.getGeneratedString());
    searchEntityMethod.setVisibility(JavaVisibility.PUBLIC);
    searchEntityMethod.setReturnType(pageType);
    StringBuffer searchEntityMethodDoc = new StringBuffer();
    // searchEntityMethod.addParameter(new Parameter(exampleType, exampleName));
    searchEntityMethod.addParameter(new Parameter(criteriaType, "criteria"));
    searchEntityMethodDoc.append("/**\n");
    searchEntityMethodDoc.append("     * 更新" + entityType.getShortName() + "对象。\n");
    searchEntityMethodDoc.append("     * @param " + entityType.getShortName() + "\n");
    searchEntityMethodDoc.append("     */");
    // searchEntityMethod.addJavaDocLine(searchEntityMethodDoc.toString());
    interfaze.addMethod(searchEntityMethod);

    interfaze.addImportedType(pageType);
    interfaze.addImportedType(entityType);
    interfaze.addImportedType(criteriaType);

    return interfaze;

  }
  
  protected TopLevelClass getServiceClass() {
	    FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
	    FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getDAOInterfaceType());
	    FullyQualifiedJavaType criteriaType = new FullyQualifiedJavaType("com.kingnod.core.criteria.Criteria");
	    type = new FullyQualifiedJavaType(type.getPackageName().substring(0, type.getPackageName().lastIndexOf(".dao")) + ".service.impl." + entityType.getShortName()
	        + "ServiceImpl");
	    TopLevelClass serviceClass = new TopLevelClass(type);
	    serviceClass.setVisibility(JavaVisibility.PUBLIC);
	    serviceClass.addSuperInterface(GeneratorUtil.getServiceInterfaze().getType());
	    serviceClass.addAnnotation("@org.springframework.stereotype.Service");
	    CommentGenerator commentGenerator = context.getCommentGenerator();
	    commentGenerator.addJavaFileComment(serviceClass);

	    FullyQualifiedJavaType pageType = new FullyQualifiedJavaType("com.kingnod.core.pager.PagingResult");
	    pageType.addTypeArgument(entityType);
	    FullyQualifiedJavaType entityListType = new FullyQualifiedJavaType("java.util.List");
	    entityListType.addTypeArgument(entityType);
	    String entityName = entityType.getShortName().substring(0, 1).toLowerCase() + entityType.getShortName().substring(1, entityType.getShortName().length());
	    //添加DAO属性
	    Field daoField = new Field(entityName + "DAO", GeneratorUtil.getDaoInterfaze().getType());
	    daoField.setVisibility(JavaVisibility.PROTECTED);
	    daoField.addAnnotation("@Autowired");
	    serviceClass.addField(daoField);
	    // 生成get方法
	    Method getMethod = new Method("get" + entityType.getShortName());
	    getMethod.setReturnType(entityType);
	    getMethod.setVisibility(JavaVisibility.PUBLIC);
	    StringBuffer getMethodDoc = new StringBuffer();
	    getMethodDoc.append("/**\n");
	    getMethodDoc.append("     * 根据指定的主键获取" + entityType.getShortName() + "对象。\n");
	    for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
	      getMethod.addParameter(new Parameter(column.getFullyQualifiedJavaType(), column.getJavaProperty()));
	      getMethodDoc.append("     * @param " + column.getJavaProperty() + "\n");
	    }
	    getMethodDoc.append("     * @return\n");
	    getMethodDoc.append("     */");
	    // getMethod.addJavaDocLine(getMethodDoc.toString());
	    getMethod.addBodyLine("return "+daoField.getName()+".get(id);");
	    serviceClass.addMethod(getMethod);
	    // 生成saveEntity方法
	    Method saveMethod = new Method("save" + entityType.getShortName());
	    saveMethod.setReturnType(new FullyQualifiedJavaType("int"));
	    saveMethod.addParameter(new Parameter(entityType, entityName));
	    saveMethod.setVisibility(JavaVisibility.PUBLIC);
	    StringBuffer saveMethodDoc = new StringBuffer();
	    saveMethodDoc.append("/**\n");
	    saveMethodDoc.append("     * 新增" + entityType.getShortName() + "对象。\n");
	    saveMethodDoc.append("     * @param " + entityType.getShortName() + "\n");
	    saveMethodDoc.append("     */");
	    saveMethod.addBodyLine("int count = 0;");
	    saveMethod.addBodyLine("IdEntity<Long> idEntity = (IdEntity)" + entityName + ";");
	    saveMethod.addBodyLine("if(null != idEntity.getId() && null != "+daoField.getName()+".get(idEntity.getId())){");
	    saveMethod.addBodyLine("count = "+daoField.getName()+".update(" + entityName + ");");
	    saveMethod.addBodyLine("}else{");
	    saveMethod.addBodyLine("count = "+daoField.getName()+".insert(" + entityName + ");");
	    saveMethod.addBodyLine("}");
	    saveMethod.addBodyLine("return count;");
	    // saveMethod.addJavaDocLine(saveMethodDoc.toString());
	    serviceClass.addMethod(saveMethod);

	    // 生成updateEntity方法
	    Method updateMethod = new Method("update" + entityType.getShortName());
	    updateMethod.setReturnType(entityType);
	    updateMethod.addParameter(new Parameter(entityType, entityName));
	    updateMethod.setVisibility(JavaVisibility.PUBLIC);
	    StringBuffer updateMethodDoc = new StringBuffer();
	    updateMethodDoc.append("/**\n");
	    updateMethodDoc.append("     * 新增" + entityType.getShortName() + "对象。\n");
	    updateMethodDoc.append("     * @param " + entityType.getShortName() + "\n");
	    updateMethodDoc.append("     */");
	    // updateMethod.addJavaDocLine(updateMethodDoc.toString());
	    // interfaze.addMethod(updateMethod);

	    // 生成delete方法
	    Method deleteMethod = new Method("delete" + entityType.getShortName());
	    deleteMethod.setReturnType(new FullyQualifiedJavaType("int"));
	    deleteMethod.setVisibility(JavaVisibility.PUBLIC);
	    StringBuffer deleteMethodDoc = new StringBuffer();
	    deleteMethod.addParameter(new Parameter(entityType, entityName));
	    deleteMethodDoc.append("/**\n");
	    deleteMethodDoc.append("     * 更新" + entityType.getShortName() + "对象。\n");
	    deleteMethodDoc.append("     * @param " + entityType.getShortName() + "\n");
	    deleteMethodDoc.append("     */");
	    // deleteMethod.addJavaDocLine(deleteMethodDoc.toString());
	    deleteMethod.addBodyLine("return "+daoField.getName()+".delete(" + entityName + ");");
	    serviceClass.addMethod(deleteMethod);

	    // 生成deleteByIds方法
	    Method deleteByIdsMethod = new Method("delete" + entityType.getShortName() + "ByIds");
	    deleteByIdsMethod.setReturnType(new FullyQualifiedJavaType("int"));
	    deleteByIdsMethod.setVisibility(JavaVisibility.PUBLIC);
	    StringBuffer deleteByIdsMethodDoc = new StringBuffer();
	    for (IntrospectedColumn pkColumn : introspectedTable.getPrimaryKeyColumns()) {
	      FullyQualifiedJavaType idsType = new FullyQualifiedJavaType(pkColumn.getFullyQualifiedJavaType() + "[]");
	      // idsType.addTypeArgument(pkColumn.getFullyQualifiedJavaType());
	      deleteByIdsMethod.addParameter(new Parameter(idsType, "ids"));
	    }
	    deleteByIdsMethodDoc.append("/**\n");
	    deleteByIdsMethodDoc.append("     * 更新" + entityType.getShortName() + "对象。\n");
	    deleteByIdsMethodDoc.append("     * @param " + entityType.getShortName() + "\n");
	    deleteByIdsMethodDoc.append("     */");
	    deleteByIdsMethod.addBodyLine("return "+daoField.getName()+".deleteById(ids);");
	    // deleteByIdsMethod.addJavaDocLine(deleteByIdsMethodDoc.toString());
	    serviceClass.addMethod(deleteByIdsMethod);

	    // 生成findUser并分页的方法
	    Method searchEntityMethod = new Method("find" + entityType.getShortName());
	    searchEntityMethod.setVisibility(JavaVisibility.PUBLIC);
	    searchEntityMethod.setReturnType(pageType);
	    StringBuffer searchEntityMethodDoc = new StringBuffer();
	    // searchEntityMethod.addParameter(new Parameter(exampleType, exampleName));
	    searchEntityMethod.addParameter(new Parameter(criteriaType, "criteria"));
	    searchEntityMethodDoc.append("/**\n");
	    searchEntityMethodDoc.append("     * 更新" + entityType.getShortName() + "对象。\n");
	    searchEntityMethodDoc.append("     * @param " + entityType.getShortName() + "\n");
	    searchEntityMethodDoc.append("     */");
	    searchEntityMethod.addBodyLine("return "+daoField.getName()+".findPagingResult(criteria);");
	    // searchEntityMethod.addJavaDocLine(searchEntityMethodDoc.toString());
	    serviceClass.addMethod(searchEntityMethod);

	    serviceClass.addImportedType(pageType);
	    serviceClass.addImportedType(entityType);
	    serviceClass.addImportedType(criteriaType);
	    serviceClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
	    serviceClass.addImportedType(GeneratorUtil.getDaoInterfaze().getType());
	    serviceClass.addImportedType(new FullyQualifiedJavaType("com.kingnod.core.entity.IdEntity"));
	    return serviceClass;

	  }

  protected TopLevelClass getDAOClass() {
	ProjectManager projectManager = GeneratorUtil.getCpMapper().get(context);
	boolean multiModule = projectManager.isMultiModule();
	String daoFullTypeSpecification = introspectedTable.getDAOImplementationType();
	if(multiModule){
		String daoImplementationClass = daoFullTypeSpecification.substring(daoFullTypeSpecification.lastIndexOf(".")+1);
		daoFullTypeSpecification = projectManager.getProperty(Constants.PROPERTIES_DAO_IMPL_PACKAGE)+"."+daoImplementationClass;
	}
	  
    FullyQualifiedJavaType type = new FullyQualifiedJavaType(daoFullTypeSpecification);
    FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    // FullyQualifiedJavaType exampleType = new
    // FullyQualifiedJavaType(introspectedTable.getExampleType());
    FullyQualifiedJavaType interfaze = new FullyQualifiedJavaType(introspectedTable.getDAOInterfaceType());
    
    FullyQualifiedJavaType baseDaoImpl = null;
    if(GeneratorUtil.isMultiModule(context)){
    	baseDaoImpl = new FullyQualifiedJavaType(Constants.MYBATIS_ENTITY_DAO_MM);
    }else{
    	baseDaoImpl = new FullyQualifiedJavaType(Constants.MYBATIS_ENTITY_DAO_SM);
    }
    
    TopLevelClass innerClass = new TopLevelClass(type);
    innerClass.setVisibility(JavaVisibility.PUBLIC);
    innerClass.addAnnotation("@org.springframework.stereotype.Repository");
    innerClass.addAnnotation(GeneratorUtil.getGeneratedString());
    baseDaoImpl.addTypeArgument(entityType);
    // baseDaoImpl.addTypeArgument(exampleType);
    for (IntrospectedColumn pkColumn : introspectedTable.getPrimaryKeyColumns()) {
      baseDaoImpl.addTypeArgument(pkColumn.getFullyQualifiedJavaType());
    }
    innerClass.setSuperClass(baseDaoImpl);
    innerClass.addSuperInterface(interfaze);
    innerClass.addImportedType(baseDaoImpl);
    innerClass.addImportedType(interfaze);
    innerClass.addImportedType(entityType);
    // innerClass.addImportedType(exampleType);
    return innerClass;
  }

  protected Interface getDAOInterface() {
    FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getDAOInterfaceType());
    FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
    // FullyQualifiedJavaType exampleType = new
    // FullyQualifiedJavaType(introspectedTable.getExampleType());
    FullyQualifiedJavaType pageType = new FullyQualifiedJavaType("com.kingnod.core.ssm.entity.Page");
    pageType.addTypeArgument(entityType);
    FullyQualifiedJavaType entityListType = new FullyQualifiedJavaType("java.util.List");
    entityListType.addTypeArgument(entityType);
    String entityName = entityType.getShortName().substring(0, 1).toLowerCase() + entityType.getShortName().substring(1, entityType.getShortName().length());
    // String exampleName = exampleType.getShortName().substring(0,
    // 1).toLowerCase() + exampleType.getShortName().substring(1,
    // exampleType.getShortName().length());
    
    FullyQualifiedJavaType baseDaoInterfaze = null;
    if(GeneratorUtil.isMultiModule(context)){
    	baseDaoInterfaze = new FullyQualifiedJavaType(Constants.ENTITY_DAO_MM);
    }else{
    	baseDaoInterfaze = new FullyQualifiedJavaType(Constants.ENTITY_DAO_SM);
    }
    
    baseDaoInterfaze.addTypeArgument(entityType);
    // baseDaoInterfaze.addTypeArgument(new
    // FullyQualifiedJavaType("java.lang.Long"));
    // baseDaoInterfaze.addTypeArgument(exampleType);
    for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
      baseDaoInterfaze.addTypeArgument(column.getFullyQualifiedJavaType());
    }
    Interface interfaze = new Interface(type);
    interfaze.addAnnotation(GeneratorUtil.getGeneratedString());
    interfaze.setVisibility(JavaVisibility.PUBLIC);
    interfaze.addSuperInterface(baseDaoInterfaze);
    CommentGenerator commentGenerator = context.getCommentGenerator();
    commentGenerator.addJavaFileComment(interfaze);

    String rootInterface = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
    if (!stringHasValue(rootInterface)) {
      rootInterface = context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
    }

    if (stringHasValue(rootInterface)) {
      FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
      interfaze.addSuperInterface(fqjt);
      interfaze.addImportedType(fqjt);
    }

    // //生成get方法
    // Method getMethod = new Method("get");
    // getMethod.setReturnType(entityType);
    // getMethod.setVisibility(JavaVisibility.PUBLIC);
    // StringBuffer getMethodDoc = new StringBuffer();
    // getMethodDoc.append("/**\n");
    // getMethodDoc.append("     * 根据指定的主键获取"+entityType.getShortName()+"对象。\n");
    // for(IntrospectedColumn column:introspectedTable.getPrimaryKeyColumns()){
    // getMethod.addParameter(new Parameter(column.getFullyQualifiedJavaType(),
    // column.getJavaProperty()));
    // getMethodDoc.append("     * @param " + column.getJavaProperty() + "\n");
    // }
    // getMethodDoc.append("     * @return\n");
    // getMethodDoc.append("     */");
    // getMethod.addJavaDocLine(getMethodDoc.toString());
    // interfaze.addMethod(getMethod);
    // //生成insert方法
    // Method insertMethod = new Method("insert");
    // insertMethod.setVisibility(JavaVisibility.PUBLIC);
    // StringBuffer insertMethodDoc = new StringBuffer();
    // insertMethod.addParameter(new Parameter(entityType, entityName));
    // insertMethodDoc.append("/**\n");
    // insertMethodDoc.append("     * 新增"+entityType.getShortName()+"对象。\n");
    // insertMethodDoc.append("     * @param " + entityType.getShortName() +
    // "\n");
    // insertMethodDoc.append("     */");
    // insertMethod.addJavaDocLine(insertMethodDoc.toString());
    // interfaze.addMethod(insertMethod);
    //
    // //生成update方法
    // Method updateMethod = new Method("update");
    // updateMethod.setVisibility(JavaVisibility.PUBLIC);
    // StringBuffer updateMethodDoc = new StringBuffer();
    // updateMethod.addParameter(new Parameter(entityType, entityName));
    // updateMethodDoc.append("/**\n");
    // updateMethodDoc.append("     * 更新"+entityType.getShortName()+"对象。\n");
    // updateMethodDoc.append("     * @param " + entityType.getShortName() +
    // "\n");
    // updateMethodDoc.append("     */");
    // updateMethod.addJavaDocLine(updateMethodDoc.toString());
    // interfaze.addMethod(updateMethod);
    //
    // //生成delete方法
    // Method deleteMethod = new Method("delete");
    // deleteMethod.setVisibility(JavaVisibility.PUBLIC);
    // StringBuffer deleteMethodDoc = new StringBuffer();
    // deleteMethod.addParameter(new Parameter(entityType, entityName));
    // deleteMethodDoc.append("/**\n");
    // deleteMethodDoc.append("     * 更新"+entityType.getShortName()+"对象。\n");
    // deleteMethodDoc.append("     * @param " + entityType.getShortName() +
    // "\n");
    // deleteMethodDoc.append("     */");
    // deleteMethod.addJavaDocLine(deleteMethodDoc.toString());
    // interfaze.addMethod(deleteMethod);
    //
    // //生成select方法
    // Method selectMethod = new Method("select");
    // selectMethod.setVisibility(JavaVisibility.PUBLIC);
    // selectMethod.setReturnType(entityListType);
    // StringBuffer selectMethodDoc = new StringBuffer();
    // selectMethod.addParameter(new Parameter(exampleType, exampleName));
    // selectMethodDoc.append("/**\n");
    // selectMethodDoc.append("     * 更新"+entityType.getShortName()+"对象。\n");
    // selectMethodDoc.append("     * @param " + entityType.getShortName() +
    // "\n");
    // selectMethodDoc.append("     */");
    // selectMethod.addJavaDocLine(selectMethodDoc.toString());
    // interfaze.addMethod(selectMethod);
    //
    // //生成select并分页的方法
    // Method selectByPageMethod = new Method("select");
    // selectByPageMethod.setVisibility(JavaVisibility.PUBLIC);
    // selectByPageMethod.setReturnType(entityListType);
    // StringBuffer selectByPageMethodDoc = new StringBuffer();
    // selectByPageMethod.addParameter(new Parameter(exampleType, exampleName));
    // selectByPageMethod.addParameter(new Parameter( new
    // FullyQualifiedJavaType("int"), "offset"));
    // selectByPageMethod.addParameter(new Parameter( new
    // FullyQualifiedJavaType("int"), "pageSize"));
    // selectByPageMethodDoc.append("/**\n");
    // selectByPageMethodDoc.append("     * 更新"+entityType.getShortName()+"对象。\n");
    // selectByPageMethodDoc.append("     * @param " + entityType.getShortName()
    // + "\n");
    // selectByPageMethodDoc.append("     */");
    // selectByPageMethod.addJavaDocLine(selectByPageMethodDoc.toString());
    // interfaze.addMethod(selectByPageMethod);
    //
    // //生成count方法
    // Method countMethod = new Method("count");
    // countMethod.setVisibility(JavaVisibility.PUBLIC);
    // countMethod.setReturnType(new FullyQualifiedJavaType("int"));
    // StringBuffer countMethodDoc = new StringBuffer();
    // countMethod.addParameter(new Parameter(exampleType, exampleName));
    // countMethodDoc.append("/**\n");
    // countMethodDoc.append("     * 更新"+entityType.getShortName()+"对象。\n");
    // countMethodDoc.append("     * @param " + entityType.getShortName() +
    // "\n");
    // countMethodDoc.append("     */");
    // countMethod.addJavaDocLine(countMethodDoc.toString());
    // interfaze.addMethod(countMethod);

    interfaze.addImportedType(baseDaoInterfaze);
    interfaze.addImportedType(entityType);
    // interfaze.addImportedType(exampleType);
    // interfaze.addImportedType(entityListType);

    return interfaze;

  }

}

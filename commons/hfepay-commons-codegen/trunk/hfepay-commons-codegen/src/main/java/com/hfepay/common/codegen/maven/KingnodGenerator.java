package com.hfepay.common.codegen.maven;

import static org.mybatis.generator.internal.util.ClassloaderUtility.getCustomClassloader;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.NullProgressCallback;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.XmlFileMergerJaxp;

import com.hfepay.common.codegen.constants.Constants;
import com.hfepay.common.codegen.manager.ProjectManager;
import com.hfepay.common.codegen.utils.GeneratorUtil;
import com.hfepay.common.codegen.utils.HuafuePayGeneratedJavaFile;
import com.hfepay.common.codegen.utils.ReflectionUtils;

public class KingnodGenerator extends MyBatisGenerator {

	File i18nfolder;

	public KingnodGenerator(Configuration configuration, ShellCallback shellCallback, List<String> warnings) throws InvalidConfigurationException {
		super(configuration, shellCallback, warnings);
	}

	@Override
	public void generate(ProgressCallback callback, Set<String> contextIds, Set<String> fullyQualifiedTableNames) throws SQLException, IOException, InterruptedException {
		if (callback == null) {
			callback = new NullProgressCallback();
		}

		Configuration configuration = (Configuration) ReflectionUtils.getFieldValue(this, "configuration");
		ShellCallback shellCallback = (ShellCallback) ReflectionUtils.getFieldValue(this, "shellCallback");
		List<GeneratedJavaFile> generatedJavaFiles = (List<GeneratedJavaFile>) ReflectionUtils.getFieldValue(this, "generatedJavaFiles");
		List<GeneratedXmlFile> generatedXmlFiles = (List<GeneratedXmlFile>) ReflectionUtils.getFieldValue(this, "generatedXmlFiles");
		List<String> warnings = (List<String>) ReflectionUtils.getFieldValue(this, "warnings");
		Set<String> projects = (Set<String>) ReflectionUtils.getFieldValue(this, "projects");

		generatedJavaFiles.clear();
		generatedXmlFiles.clear();

		// calculate the contexts to run
		List<Context> contextsToRun;
		if (contextIds == null || contextIds.size() == 0) {
			contextsToRun = configuration.getContexts();
		} else {
			contextsToRun = new ArrayList<Context>();
			for (Context context : configuration.getContexts()) {
				if (contextIds.contains(context.getId())) {
					contextsToRun.add(context);
				}
			}
		}

		// 读取项目模块配置信息
		GeneratorUtil.setCpMapper(constructCPMapper(contextsToRun, warnings));

		// 从原有的文件读取消息资源
		getOldProperties(contextsToRun, shellCallback, warnings, GeneratorUtil.getCpMapper());

		// setup custom classloader if required
		if (configuration.getClassPathEntries().size() > 0) {
			ClassLoader classLoader = getCustomClassloader(configuration.getClassPathEntries());
			ObjectFactory.setExternalClassLoader(classLoader);
		}

		// now run the introspections...
		int totalSteps = 0;
		for (Context context : contextsToRun) {
			totalSteps += context.getIntrospectionSteps();
		}
		callback.introspectionStarted(totalSteps);

		for (Context context : contextsToRun) {
			context.getJavaModelGeneratorConfiguration().addProperty("enableSubPackages", "true");
			context.getSqlMapGeneratorConfiguration().addProperty("enableSubPackages", "true");
			context.getJavaClientGeneratorConfiguration().addProperty("enableSubPackages", "true");
			for (TableConfiguration table : context.getTableConfigurations()) {
				table.addProperty("ignoreQualifiersAtRuntime", "true");
			}
			context.introspectTables(callback, warnings, fullyQualifiedTableNames);
			for (TableConfiguration table : context.getTableConfigurations()) {
				/*
				 * table.setCatalog(null); table.setSchema(null);
				 */
				table.addProperty("ignoreQualifiersAtRuntime", "true");
			}
		}

		// now run the generates
		totalSteps = 0;
		for (Context context : contextsToRun) {
			totalSteps += context.getGenerationSteps();
		}
		callback.generationStarted(totalSteps);

		for (Context context : contextsToRun) {
			context.generateFiles(callback, generatedJavaFiles, generatedXmlFiles, warnings);
		}

		// now save the files
		callback.saveStarted(generatedXmlFiles.size() + generatedJavaFiles.size());
		List<String> generatedItems = GeneratorUtil.list(null != System.getProperty("generated") ? System.getProperty("generated").split(",") : new String[] { "action", "service", "dao", "mapper","impl",
				"entity" });
		if (generatedItems.contains("mapper")) {
			for (GeneratedXmlFile gxf : generatedXmlFiles) {
				projects.add(gxf.getTargetProject());

				File targetFile;
				String source;
				try {
					File directory = shellCallback.getDirectory(gxf.getTargetProject(), gxf.getTargetPackage());
					targetFile = new File(directory, gxf.getFileName());
					if (targetFile.exists()) {
						if (gxf.isMergeable()) {
							source = XmlFileMergerJaxp.getMergedSource(gxf, targetFile);
						} else if (shellCallback.isOverwriteEnabled()) {
							source = gxf.getFormattedContent();
							warnings.add(getString("Warning.11", //$NON-NLS-1$
									targetFile.getAbsolutePath()));
						} else {
							source = gxf.getFormattedContent();
							targetFile = getUniqueFileName(directory, gxf.getFileName());
							warnings.add(getString("Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$
						}
					} else {
						source = gxf.getFormattedContent();
					}
				} catch (ShellException e) {
					warnings.add(e.getMessage());
					continue;
				}

				callback.checkCancel();
				callback.startTask(getString("Progress.15", targetFile.getName())); //$NON-NLS-1$
				writeFile(targetFile, source);
			}
		}

		ProjectManager projectManager = GeneratorUtil.getDefaultProjectManager();
		
		for (GeneratedJavaFile gjf : generatedJavaFiles) {
			String packageName = gjf.getCompilationUnit().getType().getPackageName();
			packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
			if (!generatedItems.contains(packageName)) {
				continue;
			}
			
			String targetProject = gjf.getTargetProject();
			if(projectManager!=null && projectManager.isMultiModule()){
				String pkg = projectManager.getProperty(Constants.PROPERTIES_DAO_IMPL_PACKAGE);
				if(gjf.getCompilationUnit().getType().getPackageName().equals(pkg)){
					if(projectManager.getProperty(Constants.PROPERTIES_DAO_IMPL_PROJECT)!=null){
						targetProject = projectManager.getProperty(Constants.PROPERTIES_DAO_IMPL_PROJECT);
					}
				}
			}
			
			gjf = new HuafuePayGeneratedJavaFile(gjf.getCompilationUnit(), targetProject);
			projects.add(targetProject);

			File targetFile;
			String source;
			try {
				File directory = shellCallback.getDirectory(targetProject, gjf.getTargetPackage());
				targetFile = new File(directory, gjf.getFileName());
				if (targetFile.exists()) {
					if (shellCallback.isMergeSupported()) {
						if (shellCallback instanceof MavenShellCallback) {
							source = ((MavenShellCallback) shellCallback).mergeJavaFile(gjf, targetFile.getAbsolutePath());
						} else {
							source = shellCallback.mergeJavaFile(gjf.getFormattedContent(), targetFile.getAbsolutePath(), MergeConstants.OLD_ELEMENT_TAGS);
						}
					} else if (shellCallback.isOverwriteEnabled()) {
						source = gjf.getFormattedContent();
						warnings.add(getString("Warning.11", //$NON-NLS-1$
								targetFile.getAbsolutePath()));
					} else {
						source = gjf.getFormattedContent();
						targetFile = getUniqueFileName(directory, gjf.getFileName());
						warnings.add(getString("Warning.2", targetFile.getAbsolutePath())); //$NON-NLS-1$
					}
				} else {
					source = gjf.getFormattedContent();
				}

				callback.checkCancel();
				callback.startTask(getString("Progress.15", targetFile.getName())); //$NON-NLS-1$
				writeFile(targetFile, source);
			} catch (ShellException e) {
				warnings.add(e.getMessage());
			}
		}

		for (String project : projects) {
			shellCallback.refreshProject(project);
		}

		for (Context context : contextsToRun) {
			try {
				File directory = shellCallback.getDirectory(context.getSqlMapGeneratorConfiguration().getTargetProject(), "i18n");
				File targetPropertiesFile = new File(directory, context.getProperty("messageFileName"));
				StringBuilder sb = new StringBuilder();
				Set<String> keyset = GeneratorUtil.properties.keySet();
				for (String key : keyset) {
					sb.append(key + "=" + GeneratorUtil.properties.get(key));
					OutputUtilities.newLine(sb);
				}
				writeFile(targetPropertiesFile, sb.toString());
			} catch (ShellException e) {
				warnings.add(e.getMessage());
			}
		}

		System.out.println("======================Service Class===========================");
		System.out.println(GeneratorUtil.getServiceClass().getFormattedContent());
		System.out.println("======================Service Class===========================");

		generateWebPage(contextsToRun, shellCallback, warnings);

		callback.done();
	}

	private Map<Context, ProjectManager> constructCPMapper(List<Context> contextsToRun, List<String> warnings) {
		Map<Context, ProjectManager> map = new HashMap<Context, ProjectManager>();
		for (Context context : contextsToRun) {
			map.put(context, new ProjectManager(context.getProperties(), context.getSqlMapGeneratorConfiguration().getTargetProject()));
		}
		return map;
	}

	private void generateWebPage(List<Context> contextsToRun, ShellCallback shellCallback, List<String> warnings) {
		String contentPath = System.getProperty("content_path");
		if (null == contextsToRun && contextsToRun.size() == 0) {
			return;
		}
		Context context = contextsToRun.get(0);
		String namespace = context.getProperty("modelNamespace");
		TableConfiguration tblConfig = context.getTableConfigurations().get(0);
		IntrospectedTable introTable = GeneratorUtil.getIntrospectedTable();
		FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introTable.getBaseRecordType());
		String entityName = entityType.getShortName().substring(0, 1).toLowerCase() + entityType.getShortName().substring(1, entityType.getShortName().length());
		String actionName = GeneratorUtil.camel2Bar(entityName);
		TopLevelClass entityClass = GeneratorUtil.getEntityClass();
		StringBuffer sb = new StringBuffer();
		sb.append("<%@ include file=\"/WEB-INF/content/cmn/include.jsp\" %>\r\n");
		sb.append("<html>\r\n");
		sb.append("<head>\r\n");
		sb.append("</head>\r\n");
		sb.append("<body>\r\n");
		sb.append("<k:form id=\"mainForm\" action=\"");
		sb.append(actionName);
		sb.append(".action\">\r\n");
		sb.append("<table align=\"left\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
		sb.append("	<tr>\r\n		<td>\r\n");
		sb.append("			<k:fitlerSimplePanel dictName=\"" + namespace + "." + entityName + "FilterItems\"" + " entityType=\"" + entityType + "\"/>\r\n");
		sb.append("		</td>\r\n	</tr>\r\n");
		sb.append("	<tr>\r\n		<td>\r\n");
		sb.append("			<k:grid titleKey=\"common.method_list\" cellSpacing=\"0\" data=\"page\" var=\"obj\" " + "actionUrl=\"" + actionName + ".action\" width=\"100%\">\r\n");
		sb.append("				<k:toolbar>\r\n");
		sb.append("					<k:button action=\"" + actionName + "\" method=\"input\" textKey=\"common.method_new\"/>\r\n");
		sb.append("					<k:button action=\"" + actionName + "\" method=\"delete\" textKey=\"common.method_delete\" "
				+ "openMode=\"submit\" interceptorScript=\"confirm('@{common.confirm.delete}')\"/>\r\n");
		sb.append("				</k:toolbar>\r\n				<k:row>\r\n");
		sb.append("					<k:seialNumberColumn title=\"\"/>\r\n");
		sb.append("					<k:selectorColumn name=\"ids\" titleKey=\"common.method_selectall\" type=\"checkbox\" value=\"${obj.id}\"/>\r\n");
		for (Field field : entityClass.getFields()) {
			String propertyName = field.getName();
			if ("id".equals(propertyName)) {
				System.out.println("ID不生成");
			} else if ("updateCount".equals(propertyName)) {
				System.out.println("updateCount不生成");
			} else if ("recordStatus".equals(propertyName)) {
				System.out.println("recordStatus不生成");
			} else {
				if ("creatorId".equals(propertyName)) {
					/*
					 * sb.append("		  <k:column name=\"");
					 * sb.append(propertyName); sb.append(
					 * "\" width=\"100\" titleKey=\"common.entity_creatorId\" sortable=\"true\"></k:column>\r\n"
					 * );
					 */
				} else if ("updaterId".equals(propertyName)) {
					sb.append("					<k:column name=\"");
					sb.append(propertyName);
					sb.append("\" width=\"100\" titleKey=\"common.entity_updaterId\" dictName=\"org.userFullName\" sortable=\"true\"></k:column>\r\n");
				} else if ("createDate".equals(propertyName)) {
					/*
					 * sb.append("		  <k:column name=\"");
					 * sb.append(propertyName); sb.append(
					 * "\" width=\"100\" titleKey=\"common.entity_createDate\" type=\"date\" format=\"yyyy-MM-dd\"></k:column>\r\n"
					 * );
					 */
				} else if ("updateDate".equals(propertyName)) {
					sb.append("					<k:column name=\"");
					sb.append(propertyName);
					sb.append("\" width=\"100\" titleKey=\"common.entity_updateDate\" type=\"date\"></k:column>\r\n");
				} else {
					if ("java.util.Date".equals(field.getType().toString())) {
						sb.append("					<k:column name=\"");
						sb.append(propertyName);
						sb.append("\" width=\"100\" titleKey=\"" + namespace + "." + entityName + "_" + propertyName + "\" type=\"date\"></k:column>\r\n");
					} else {
						sb.append("					<k:column name=\"");
						sb.append(propertyName);
						sb.append("\" width=\"100\" titleKey=\"" + namespace + "." + entityName + "_" + propertyName + "\" sortable=\"true\"></k:column>\r\n");
					}
				}
			}
		}
		sb.append("					<k:column titleKey=\"common.label_operate\">\r\n");
		sb.append("					</k:column>\r\n");
		sb.append("				</k:row>\r\n");
		sb.append("			</k:grid>\r\n");
		sb.append("		</td>\r\n");
		sb.append("	</tr>\r\n");
		sb.append("</table>\r\n");
		sb.append("</k:form>\r\n");
		sb.append("</body>\r\n");
		sb.append("</html>\r\n");
		System.out.println("############################################查询页面################################");
		System.out.println(sb);

		System.out.println("############################################表单页面################################");
		StringBuffer sb2 = new StringBuffer();
		sb2.append("<%@ include file=\"/WEB-INF/content/cmn/include.jsp\" %>\r\n");
		sb2.append("<html>\r\n");
		sb2.append("<head>\r\n");
		sb2.append("</head>\r\n");
		sb2.append("<body>\r\n");
		sb2.append("	<k:form action=\"" + actionName + "!save.action\">\r\n");
		sb2.append("		<k:hidden name=\"id\"/>\r\n");
		sb2.append("		<k:hidden name=\"updateCount\"/>\r\n");
		sb2.append("		<k:layout layoutCols=\"1\">\r\n");
		for (Field field : entityClass.getFields()) {
			String propertyName = field.getName();
			if ("id".equals(propertyName)) {
				System.out.println("ID不生成");
			} else if ("updateCount".equals(propertyName)) {
				System.out.println("updateCount不生成");
			} else if ("recordStatus".equals(propertyName)) {
				System.out.println("recordStatus不生成");
			} else if ("creatorId".equals(propertyName)) {
				System.out.println("recordStatus不生成");
			} else if ("createDate".equals(propertyName)) {
				System.out.println("recordStatus不生成");
			} else if ("updaterId".equals(propertyName)) {
				System.out.println("recordStatus不生成");
			} else if ("updateDate".equals(propertyName)) {
				System.out.println("recordStatus不生成");
			} else if ("recordStatus".equals(propertyName)) {
				System.out.println("recordStatus不生成");
			} else {
				sb2.append("			<k:text name=\"" + propertyName + "\" labelKey=\"" + namespace + "." + entityName + "_" + propertyName + "\"/>\r\n");
			}
		}
		sb2.append("		</k:layout>\r\n");
		sb2.append("		<k:button textKey=\"common.method_save\" action=\"" + actionName + "\" method=\"save\" openMode=\"submit\"/>\r\n");
		sb2.append("	</k:form>\r\n");
		sb2.append("</body>\r\n");
		sb2.append("</html>\r\n");
		System.out.println(sb2);

		// 生成JSP
		try {
			File f = new File(contentPath + "\\" + namespace + "\\" + actionName + ".jsp");
			File f2 = new File(contentPath + "\\" + namespace + "\\" + actionName + "-input.jsp");
			if (f.exists()) {
				warnings.add(f.getName() + " 已经存在， 没有覆盖！");
			} else {
				if (f.createNewFile()) {
					warnings.add(f.getName() + " 已经生成。");
					FileOutputStream out = new FileOutputStream(f, true);
					out.write(sb.toString().getBytes("utf-8"));
					out.close();
				} else {
					System.out.println("查询JSP创建失败");
				}
			}
			if (f2.exists()) {
				warnings.add(f2.getName() + " 已经存在， 没有覆盖！");
			} else {
				if (f2.createNewFile()) {
					warnings.add(f2.getName() + " 已经生成。");
					FileOutputStream out2 = new FileOutputStream(f2, true);
					out2.write(sb2.toString().getBytes("utf-8"));
					out2.close();
				} else {
					System.out.println("新增修改JSP创建失败");
				}
			}

		} catch (Exception e) {
			System.out.println("生成失败!!!");
		}

	}

	private void getOldProperties(List<Context> contextsToRun, ShellCallback shellCallback, List<String> warnings, Map<Context, ProjectManager> cpMapper) {
		for (Context context : contextsToRun) {
			try {
				ProjectManager projectManager = cpMapper.get(context);
				if(projectManager.isMultiModule()){
					continue;
				}
				File directory = shellCallback.getDirectory(context.getSqlMapGeneratorConfiguration().getTargetProject(), "i18n");
				File targetPropertiesFile = new File(directory, context.getProperty("messageFileName"));
				java.io.BufferedReader reader = null;
				try {
					reader = new java.io.BufferedReader(new java.io.FileReader(targetPropertiesFile));
					String line = "";
					Map<String, String> properties = new HashMap<String, String>();
					while ((line = reader.readLine()) != null) {
						if (line.length() == 0 || line.indexOf("=") == -1) {
							continue;
						}
						String[] array = line.split("=");
						String key = "";
						String value = "";
						if (array.length > 0) {
							key = array[0];
						}
						if (array.length > 1) {
							value = array[1];
						}
						GeneratorUtil.properties.put(key, value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (ShellException e) {
				warnings.add(e.getMessage());
			}
		}
	}

	private File getUniqueFileName(File directory, String fileName) {
		return (File) ReflectionUtils.invokeMethod(this, "getUniqueFileName", new Class[] { File.class, String.class }, new Object[] { directory, fileName });
	}

	private void writeFile(File file, String content) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
		bw.write(content);
		bw.close();
	}

}

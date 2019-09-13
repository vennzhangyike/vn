/*
 * Copyright 2009 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hfepay.common.codegen.maven;

import static org.mybatis.generator.internal.util.ClassloaderUtility.getCustomClassloader;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

import com.hfepay.common.codegen.utils.GeneratorUtil;

/**
 * Goal which generates MyBatis/iBATIS artifacts.
 * 
 * @goal generate
 * @phase generate-sources
 */
public class MyBatisGeneratorMojo extends AbstractMojo {
    
    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     * 
     */
    private MavenProject project;
    
    /**
     * @parameter expression="${mybatis.generator.outputDirectory}" 
     *             default-value="${project.build.directory}/generated-sources/mybatis-generator"
     * @required
     */
    private File outputDirectory;
    
    /**
     * Location of the configuration file.
     * 
     * @parameter expression="${mybatis.generator.configurationFile}"
     *             default-value="${basedir}/src/main/resources/generatorConfig.xml"
     * @required
     */
    private File configurationFile;
    
    /**
     * Specifies whether the mojo writes progress messages to the log
     * 
     * @parameter expression="${mybatis.generator.verbose}" default-value=false
     */
    private boolean verbose;

    /**
     * Specifies whether the mojo overwrites existing files.  Default is false.
     * 
     * @parameter expression="${mybatis.generator.overwrite}" default-value=false
     */
    private boolean overwrite;
    
    /**
     * Location of a SQL script file to run before generating code.
     * If null, then no script will be run.  If not null,
     * then jdbcDriver, jdbcURL must be supplied also,
     * and jdbcUserId and jdbcPassword may be supplied.
     * 
     * @parameter expression="${mybatis.generator.sqlScript}"
     */
    private String sqlScript;
    
    /**
     * JDBC Driver to use if a sql.script.file is specified
     * 
     * @parameter expression="${mybatis.generator.jdbcDriver}"
     */
    private String jdbcDriver;
    
    /**
     * JDBC URL to use if a sql.script.file is specified
     * 
     * @parameter expression="${mybatis.generator.jdbcURL}"
     */
    private String jdbcURL;
    
    /**
     * JDBC user ID to use if a sql.script.file is specified
     * 
     * @parameter expression="${mybatis.generator.jdbcUserId}"
     */
    private String jdbcUserId;
    
    /**
     * JDBC password to use if a sql.script.file is specified
     * 
     * @parameter expression="${mybatis.generator.jdbcPassword}"
     */
    private String jdbcPassword;
    
    /**
     * Comma delimited list of table names to generate
     * 
     * @parameter expression="${mybatis.generator.tableNames}"
     */
    private String tableNames;
    
    /**
     * Comma delimited list of contexts to generate
     * 
     * @parameter expression="${mybatis.generator.contexts}"
     */
    private String contexts;
    
    public void execute() throws MojoExecutionException {
        if (configurationFile == null) {
            throw new MojoExecutionException(Messages.getString("RuntimeError.0")); //$NON-NLS-1$
        }
        String generateTable = System.getProperty("table");
        if(null == generateTable || generateTable.length() == 0){
          getLog().error("请指定table参数");
          return;
        }
        tableNames = generateTable.trim();

        List<String> warnings = new ArrayList<String>();

        //The configuration file is under the execution root project.
        if(project.isExecutionRoot()){
        	 if (!configurationFile.exists()) {
                 throw new MojoExecutionException(Messages.getString(
                         "RuntimeError.1", configurationFile.toString())); //$NON-NLS-1$
             }
        }else{
        	return;
        }
        
        runScriptIfNecessary();

        Set<String> fullyqualifiedTables = new HashSet<String>();
        if (StringUtility.stringHasValue(tableNames)) {
            StringTokenizer st = new StringTokenizer(tableNames,
                    ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    fullyqualifiedTables.add(s);
                }
            }
        }

        Set<String> contextsToRun = new HashSet<String>();
        if (StringUtility.stringHasValue(contexts)) {
            StringTokenizer st = new StringTokenizer(contexts, ","); //$NON-NLS-1$
            while (st.hasMoreTokens()) {
                String s = st.nextToken().trim();
                if (s.length() > 0) {
                    contextsToRun.add(s);
                }
            }
        }

        try {
          ConfigurationParser cp = new ConfigurationParser(project.getProperties(), warnings);
            Configuration config = cp.parseConfiguration(configurationFile);
            
            TableConfiguration checkedTblConfig = checkTableParameter(config, generateTable);
            if(null == checkedTblConfig){
            	getLog().error("请指定table的generatedKey.");
              return;
            }
            if(null == checkedTblConfig.getGeneratedKey()){
            	//return;
            }
            fullyqualifiedTables.clear();
            fullyqualifiedTables.add(org.mybatis.generator.internal.util.StringUtility.composeFullyQualifiedTableName(checkedTblConfig.getCatalog(), checkedTblConfig.getSchema(), checkedTblConfig.getTableName(), '.'));
            
            
            
            if (config.getClassPathEntries().size() > 0) {
              ClassLoader classLoader = getCustomClassloader(config.getClassPathEntries());
              ObjectFactory.setExternalClassLoader(classLoader);
          }

            ShellCallback callback = new MavenShellCallback(this, overwrite);

            KingnodGenerator myBatisGenerator = new KingnodGenerator(config, callback, warnings);

            myBatisGenerator.generate(new MavenProgressCallback(getLog(), verbose), contextsToRun,
                    fullyqualifiedTables);

        } catch (XMLParserException e) {
            for (String error : e.getErrors()) {
                getLog().error(error);
            }

            throw new MojoExecutionException(e.getMessage());
        } catch (SQLException e) {
            throw new MojoExecutionException(e.getMessage());
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage());
        } catch (InvalidConfigurationException e) {
            for (String error : e.getErrors()) {
                getLog().error(error);
            }

            throw new MojoExecutionException(e.getMessage());
        } catch (InterruptedException e) {
            // ignore (will never happen with the DefaultShellCallback)
            ;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MojoExecutionException(e.getMessage());
        }

        for (String error : warnings) {
            getLog().warn(error);
        }
        
        if (project != null && outputDirectory != null && outputDirectory.exists()) {
            project.addCompileSourceRoot(outputDirectory.getAbsolutePath());
            
            Resource resource = new Resource();
            resource.setDirectory(outputDirectory.getAbsolutePath());
            resource.addInclude("**/*.xml");
            project.addResource(resource);
        }
    }
    
    private void runScriptIfNecessary() throws MojoExecutionException {
        if (sqlScript == null) {
            return;
        }
        
        SqlScriptRunner scriptRunner = new SqlScriptRunner(sqlScript, jdbcDriver, jdbcURL, jdbcUserId, jdbcPassword);
        scriptRunner.setLog(getLog());
        scriptRunner.executeScript();
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }
    
    private TableConfiguration checkTableParameter(Configuration config, String generateTable) {
      if(true){//确保每次只生成一个table的代码
        Context context = null;
        TableConfiguration tblConfig = null;
        for(Context tmpcontext:config.getContexts()){
          String dbscheme = "";
          if("oracle.jdbc.driver.OracleDriver".equals(tmpcontext.getJdbcConnectionConfiguration().getDriverClass())){
            dbscheme = tmpcontext.getJdbcConnectionConfiguration().getUserId();
          }
          
          for(TableConfiguration tmptblConfig:tmpcontext.getTableConfigurations()){
            tmptblConfig.setSchema(dbscheme);
            String entityType = tmpcontext.getJavaModelGeneratorConfiguration().getTargetPackage() + "." + tmptblConfig.getDomainObjectName();
            GeneratorUtil.addEntityFullName(tmptblConfig.getDomainObjectName(), new FullyQualifiedJavaType(entityType));
            GeneratorUtil.addTableConfiguration(tmptblConfig);
            if(tmptblConfig.getTableName().equals(generateTable)){
              context = tmpcontext;
              tblConfig = tmptblConfig;
            }
          }
        }
        
        if(null == tblConfig){
          getLog().error("请检查table参数指定的表名或" + configurationFile.getName() + "设置的表名是否正确");
          return null;
        }
        
        config.getContexts().clear();
        context.getTableConfigurations().clear();
        context.addTableConfiguration(tblConfig);
        config.getContexts().add(context);
        return tblConfig;
      }
      return null;
    }
}

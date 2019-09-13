/*
 *  Copyright 2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.hfepay.common.codegen.maven;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.messages.Messages;

import com.hfepay.common.codegen.utils.ReflectionUtils;

/**
 * @author Jeff Butler
 */
public class MavenShellCallback extends DefaultShellCallback {
    private MyBatisGeneratorMojo mybatisGeneratorMojo;

    /**
     * @param overwrite
     */
    public MavenShellCallback(MyBatisGeneratorMojo mybatisGeneratorMojo, boolean overwrite) {
        super(overwrite);
        this.mybatisGeneratorMojo = mybatisGeneratorMojo;
    }

    @Override
    public File getDirectory(String targetProject, String targetPackage)
            throws ShellException {
        if (!"MAVEN".equals(targetProject)) {
            return super.getDirectory(targetProject, targetPackage);
        }
        
        // targetProject is the output directory from the MyBatis generator
        // Mojo. It will be created if necessary
        //
        // targetPackage is interpreted as a sub directory, but in package
        // format (with dots instead of slashes).  The sub directory will be created
        // if it does not already exist
        
        File project = mybatisGeneratorMojo.getOutputDirectory();
        if (!project.exists()) {
            project.mkdirs();
        }
        
        if (!project.isDirectory()) {
            throw new ShellException(Messages.getString("Warning.9", //$NON-NLS-1$
                    project.getAbsolutePath()));
        }
        
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(targetPackage, "."); //$NON-NLS-1$
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append(File.separatorChar);
        }
        
        File directory = new File(project, sb.toString());
        if (!directory.isDirectory()) {
            boolean rc = directory.mkdirs();
            if (!rc) {
                throw new ShellException(Messages.getString("Warning.10", //$NON-NLS-1$
                        directory.getAbsolutePath()));
            }
        }
        
        return directory;
    }
    
    @Override
    public boolean isMergeSupported() {
        return true;
    }
    
    @Override
    public String mergeJavaFile(String newFileSource, String existingFileFullPath, String[] javadocTags)
      throws ShellException {
        return super.mergeJavaFile(newFileSource, existingFileFullPath, javadocTags);
    }
    
    private ASTParser astParser = ASTParser.newParser(AST.JLS3);// 非常慢
    
  public String mergeJavaFile(GeneratedJavaFile gjf, String existingFileFullPath) throws ShellException {
    BufferedInputStream bufferedInputStream = null;
    StringBuffer fieldString = new StringBuffer();
    StringBuffer methodString = new StringBuffer();
    try {
      JavaElement topLevelClass = (JavaElement)gjf.getCompilationUnit();
      bufferedInputStream = new BufferedInputStream(new FileInputStream(existingFileFullPath));
      byte[] input = new byte[bufferedInputStream.available()];
      bufferedInputStream.read(input);
      bufferedInputStream.close();
      this.astParser.setSource(new String(input).toCharArray());
      /**/
      CompilationUnit result = (CompilationUnit) (this.astParser.createAST(null));// 很慢
      TypeDeclaration type = (TypeDeclaration) result.types().get(0);
      FieldDeclaration[] fieldList = type.getFields();
      for (FieldDeclaration field : fieldList) {
        List modifiers = (List)ReflectionUtils.getFieldValue(field, "modifiers");
        if(isGenerated(modifiers)){
          continue;
        }
        fieldString.append(field.toString());
      }
      topLevelClass.setOldFieldString(fieldString.toString());
      MethodDeclaration[] methodList = type.getMethods();
      for(MethodDeclaration methodDec:methodList){
        List modifiers = (List)ReflectionUtils.getFieldValue(methodDec, "modifiers");
        if(isGenerated(modifiers)){
          continue;
        }
        methodString.append(methodDec.toString());
      }
      topLevelClass.setOldMethodString(methodString.toString());
      List<org.eclipse.jdt.core.dom.ImportDeclaration> importList = result.imports();
      for(org.eclipse.jdt.core.dom.ImportDeclaration importDec:importList){
        String typename = importDec.getName().getFullyQualifiedName();
          if(topLevelClass instanceof TopLevelClass){
            ((TopLevelClass)topLevelClass).addImportedType(new FullyQualifiedJavaType(typename));
          }else if(topLevelClass instanceof org.mybatis.generator.api.dom.java.Interface){
            ((org.mybatis.generator.api.dom.java.Interface)topLevelClass).addImportedType(new FullyQualifiedJavaType(typename));
          }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally{
      try {
        bufferedInputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return gjf.getFormattedContent();
  }
  
  private boolean isGenerated(List modifiers){
    boolean result = false;
    for(Object item:modifiers){
      if(item instanceof SingleMemberAnnotation){
        if(((SingleMemberAnnotation)item).getTypeName().toString().equals("com.hfepay.commons.annotation.Generated")){
          result = true;
          break;
        }
      }
    }
    return result;
  }
  /*private Method getOldMethod(MethodDeclaration methodDec){
    List modifiers = (List)ReflectionUtils.getFieldValue(methodDec, "modifiers");
    Method newMethod = new Method();
    for(Object item:modifiers){
      if(item instanceof Modifier){
        Modifier modifier = (Modifier)item;
        if("public".equals(modifier.getKeyword())){
          newMethod.setVisibility(JavaVisibility.PUBLIC);
        }else if("private".equals(modifier.getKeyword())){
          newMethod.setVisibility(JavaVisibility.PRIVATE);
        }if("protected".equals(modifier.getKeyword())){
          newMethod.setVisibility(JavaVisibility.PROTECTED);
        }
      }
    }
    String name = methodDec.getName().getFullyQualifiedName();
    SimpleType returnType = (SimpleType)ReflectionUtils.getFieldValue(methodDec, "returnType");
    Block method_block=methodDec.getBody();
    newMethod.setName(name);
    newMethod.setReturnType(new FullyQualifiedJavaType(returnType.toString()));
    newMethod.addBodyLine(method_block.toString());
    return newMethod;
  }
  private Field getOldField(FieldDeclaration field){
    String modifiers = ReflectionUtils.getFieldValue(field, "modifiers").toString();
    Object fieldType = ReflectionUtils.getFieldValue(field, "baseType");
    String name = field.fragments().get(0).toString();
    Field newField = new Field(name, new FullyQualifiedJavaType(fieldType.toString()));
    if(modifiers.indexOf("private") != -1){
      newField.setVisibility(JavaVisibility.PRIVATE);
    }else if(modifiers.indexOf("public") != -1){
      newField.setVisibility(JavaVisibility.PUBLIC);
    }else if(modifiers.indexOf("protected") != -1){
      newField.setVisibility(JavaVisibility.PROTECTED);
    }
    return newField;
  }*/
}

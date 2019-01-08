package com.github.raphael008.plugins;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.lang.reflect.Field;
import java.util.List;

public class GenerateMapperPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType interfaceType = new FullyQualifiedJavaType(
                String.format("BaseMapper<%s, %s, java.lang.Long>",
                        introspectedTable.getBaseRecordType(),
                        introspectedTable.getExampleType()));

        FullyQualifiedJavaType importType = new FullyQualifiedJavaType("com.github.raphael008.mapper.BaseMapper");

        interfaze.addSuperInterface(interfaceType);
        interfaze.addImportedType(importType);

        interfaze.getMethods().clear();
        interfaze.getAnnotations().clear();

        interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Repository"));
        interfaze.addAnnotation("@Repository");

        return super.clientGenerated(interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}

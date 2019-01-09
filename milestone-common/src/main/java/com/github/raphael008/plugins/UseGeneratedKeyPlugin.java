package com.github.raphael008.plugins;


import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;

public class UseGeneratedKeyPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        IntrospectedColumn primaryKeyColumn = primaryKeyColumns.stream()
                .filter(IntrospectedColumn::isAutoIncrement)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("生成文件时发生错误, 表: %s, 无自增主键。", introspectedTable.getFullyQualifiedTableNameAtRuntime())));

        element.addAttribute(new Attribute("useGeneratedKeys", "true"));
        element.addAttribute(new Attribute("keyColumn", primaryKeyColumn.getActualColumnName()));
        element.addAttribute(new Attribute("keyProperty", JavaBeansUtil.getCamelCaseString(primaryKeyColumn.getActualColumnName(), false)));

        return super.sqlMapInsertElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapInsertSelectiveElementGenerated(element, introspectedTable);
    }
}

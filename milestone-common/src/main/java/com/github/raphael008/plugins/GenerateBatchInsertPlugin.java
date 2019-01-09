package com.github.raphael008.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;
import java.util.stream.Collectors;

public class GenerateBatchInsertPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        IntrospectedColumn primaryKeyColumn = primaryKeyColumns.stream()
                .filter(IntrospectedColumn::isAutoIncrement)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("生成文件时发生错误, 表: %s, 无自增主键。", introspectedTable.getFullyQualifiedTableNameAtRuntime())));

        XmlElement insertRange = new XmlElement("insert");
        insertRange.addAttribute(new Attribute("id", "insertRange"));
        insertRange.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        insertRange.addAttribute(new Attribute("useGeneratedKeys", "true"));
        insertRange.addAttribute(new Attribute("keyColumn", primaryKeyColumn.getActualColumnName()));
        insertRange.addAttribute(new Attribute("keyProperty", JavaBeansUtil.getCamelCaseString(primaryKeyColumn.getActualColumnName(), false)));

        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        String columns = allColumns.stream()
                .map(IntrospectedColumn::getActualColumnName)
                .collect(Collectors.joining(", "));

        insertRange.addElement(new TextElement(String.format("insert into %s (%s) values", introspectedTable.getFullyQualifiedTableNameAtRuntime(), columns)));

        String valueColumns = allColumns.stream()
                .map(column -> String.format("#{item.%s, jdbcType=%s}",
                        JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), false),
                        column.getJdbcTypeName()))
                .collect(Collectors.joining(", "));

        XmlElement foreach = new XmlElement("foreach");
        foreach.addAttribute(new Attribute("item", "item"));
        foreach.addAttribute(new Attribute("index", "index"));
        foreach.addAttribute(new Attribute("collection", "list"));
        foreach.addAttribute(new Attribute("separator", ","));
        foreach.addElement(new TextElement(String.format("(%s)", valueColumns)));

        insertRange.addElement(foreach);

        System.out.println(insertRange.toString());

        XmlElement rootElement = document.getRootElement();
        rootElement.addElement(insertRange);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}

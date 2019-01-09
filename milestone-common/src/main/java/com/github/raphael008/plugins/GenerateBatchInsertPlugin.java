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

        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();

        String aliasedFullyQualifiedTableNameAtRuntime = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
        String fullyQualifiedTableNameAtRuntime = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        XmlElement insertRange = new XmlElement("insert");
        insertRange.addAttribute(new Attribute("id", "insertRange"));
        insertRange.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));

        String columns = allColumns.stream()
                .map(column -> JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), false))
                .collect(Collectors.joining(", "));

        String valueColumns = allColumns.stream()
                .map(column -> String.format("#{%s, jdbcType=%s}",
                        JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), false),
                        column.getJdbcTypeName()))
                .collect(Collectors.joining(", "));

        StringBuilder sql = new StringBuilder();
        sql.append(String.format("insert into %s%n", introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        sql.append(String.format("(%s)%n", columns));
        sql.append(String.format("values(%s)%n", valueColumns));

        insertRange.addElement(new TextElement(sql.toString()));

        XmlElement rootElement = document.getRootElement();
        rootElement.addElement(insertRange);
//        return false;
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}

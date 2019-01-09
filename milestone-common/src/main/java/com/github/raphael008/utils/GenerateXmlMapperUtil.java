package com.github.raphael008.utils;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;
import java.util.stream.Collectors;

public class GenerateXmlMapperUtil {
    public static XmlElement checkNullForColumn(IntrospectedColumn column) {
        String actualColumnName = column.getActualColumnName();
        String camelCaseColumnName = JavaBeansUtil.getCamelCaseString(actualColumnName, false);

        XmlElement element = new XmlElement("if");
        element.addAttribute(new Attribute("test", String.format("%s != null", camelCaseColumnName)));
        element.addElement(new TextElement(String.format("%s,", actualColumnName)));
        return element;
    }

    public static XmlElement checkNullForValue(IntrospectedColumn column) {
        String actualColumnName = column.getActualColumnName();
        String camelCaseColumnName = JavaBeansUtil.getCamelCaseString(actualColumnName, false);

        XmlElement element = new XmlElement("if");
        element.addAttribute(new Attribute("test", String.format("%s != null", camelCaseColumnName)));
        element.addElement(new TextElement(String.format("#{%s, jdbcType=%s},", camelCaseColumnName, column.getJdbcTypeName())));
        return element;
    }

    public static String generateSqlForUpdateRangeByPrimaryKey(IntrospectedTable table) {
        List<IntrospectedColumn> primaryKeyColumns = table.getPrimaryKeyColumns();
        IntrospectedColumn primaryKeyColumn = primaryKeyColumns.get(0);
        List<IntrospectedColumn> nonPrimaryKeyColumns = table.getNonPrimaryKeyColumns();

        String values = nonPrimaryKeyColumns.stream()
                .map(GenerateXmlMapperUtil::setValueToField)
                .collect(Collectors.joining(", "));

        StringBuilder sql = new StringBuilder();
        sql.append(String.format("update %s ", table.getFullyQualifiedTableNameAtRuntime()));
        sql.append(String.format("set %s ", values));
        sql.append(String.format("where %s = #{%s, jdbcType=%s}",
                primaryKeyColumn.getActualColumnName(),
                JavaBeansUtil.getCamelCaseString(primaryKeyColumn.getActualColumnName(), false),
                primaryKeyColumn.getJdbcTypeName()));

        return sql.toString();
    }

    public static String setValueToField(IntrospectedColumn column) {
        return String.format("%s = #{%s, jdbcType=%s}",
                column.getActualColumnName(),
                JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), false),
                column.getJdbcTypeName());
    }
}

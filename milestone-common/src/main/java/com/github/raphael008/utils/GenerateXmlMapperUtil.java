package com.github.raphael008.utils;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.JavaBeansUtil;

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

    public static XmlElement checkNullForSetValue(IntrospectedColumn column) {
        String actualColumnName = column.getActualColumnName();
        String camelCaseColumnName = JavaBeansUtil.getCamelCaseString(actualColumnName, false);

        XmlElement element = new XmlElement("if");
        element.addAttribute(new Attribute("test", String.format("%s != null", camelCaseColumnName)));
        element.addElement(new TextElement(String.format("%s = #{%s, jdbcType=%s},", column.getActualColumnName(), camelCaseColumnName, column.getJdbcTypeName())));
        return element;
    }

    public static String setValueToField(IntrospectedColumn column) {
        return String.format("%s = #{%s, jdbcType=%s}",
                column.getActualColumnName(),
                JavaBeansUtil.getCamelCaseString(column.getActualColumnName(), false),
                column.getJdbcTypeName());
    }
}

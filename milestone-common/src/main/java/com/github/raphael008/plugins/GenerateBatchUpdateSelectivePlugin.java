package com.github.raphael008.plugins;

import com.github.raphael008.utils.GenerateXmlMapperUtil;
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

public class GenerateBatchUpdateSelectivePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement element = new XmlElement("update");
        element.addAttribute(new Attribute("id", "updateRangeByPrimaryKeySelective"));
        element.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));

        element.addElement(new TextElement(String.format("update %s", introspectedTable.getFullyQualifiedTableNameAtRuntime())));

        XmlElement set = new XmlElement("set");
        List<IntrospectedColumn> nonPrimaryKeyColumns = introspectedTable.getNonPrimaryKeyColumns();
        List<XmlElement> values = nonPrimaryKeyColumns.stream()
                .map(GenerateXmlMapperUtil::checkNullForSetValue)
                .collect(Collectors.toList());
        values.forEach(set::addElement);
        element.addElement(set);

        IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);
        element.addElement(new TextElement(String.format("where %s = #{%s, jdbcType=%s}",
                primaryKeyColumn.getActualColumnName(),
                JavaBeansUtil.getCamelCaseString(primaryKeyColumn.getActualColumnName(), false),
                primaryKeyColumn.getJdbcTypeName())));

        XmlElement rootElement = document.getRootElement();
        rootElement.addElement(element);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}

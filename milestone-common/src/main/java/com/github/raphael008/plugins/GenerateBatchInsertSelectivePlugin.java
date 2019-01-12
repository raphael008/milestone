package com.github.raphael008.plugins;


import com.github.raphael008.utils.GenerateXmlMapperUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;
import java.util.stream.Collectors;

public class GenerateBatchInsertSelectivePlugin extends PluginAdapter {
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


        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        XmlElement insertRange = new XmlElement("insert");
        insertRange.addAttribute(new Attribute("id", "insertRangeSelective"));
        insertRange.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        insertRange.addAttribute(new Attribute("useGeneratedKeys", "true"));
        insertRange.addAttribute(new Attribute("keyColumn", primaryKeyColumn.getActualColumnName()));
        insertRange.addAttribute(new Attribute("keyProperty", JavaBeansUtil.getCamelCaseString(primaryKeyColumn.getActualColumnName(), false)));


        List<XmlElement> columns = allColumns.stream()
                .map(GenerateXmlMapperUtil::checkNullForColumn)
                .collect(Collectors.toList());
        XmlElement trimForColumns = new XmlElement("trim");
        trimForColumns.addAttribute(new Attribute("prefix", "("));
        trimForColumns.addAttribute(new Attribute("suffix", ")"));
        trimForColumns.addAttribute(new Attribute("suffixOverrides", ","));
        columns.forEach(trimForColumns::addElement);

        List<XmlElement> values = allColumns.stream()
                .map(GenerateXmlMapperUtil::checkNullForValue)
                .collect(Collectors.toList());
        XmlElement trimForValues = new XmlElement("trim");
        trimForValues.addAttribute(new Attribute("prefix", "values ("));
        trimForValues.addAttribute(new Attribute("suffix", ")"));
        trimForValues.addAttribute(new Attribute("suffixOverrides", ","));
        values.forEach(trimForValues::addElement);

        insertRange.addElement(trimForColumns);
        insertRange.addElement(trimForValues);
        XmlElement rootElement = document.getRootElement();
        rootElement.addElement(insertRange);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}

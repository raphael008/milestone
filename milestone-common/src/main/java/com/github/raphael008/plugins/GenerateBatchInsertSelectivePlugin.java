package com.github.raphael008.plugins;


import com.github.raphael008.utils.GenerateXmlMapperUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;
import java.util.stream.Collectors;

public class GenerateBatchInsertSelectivePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        XmlElement insertRange = new XmlElement("insert");
        insertRange.addAttribute(new Attribute("id", "insertRangeSelective"));
        insertRange.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));

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

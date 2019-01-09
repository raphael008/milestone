package com.github.raphael008.plugins;

import com.github.raphael008.utils.GenerateXmlMapperUtil;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class GenerateBatchUpdatePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement insertRange = new XmlElement("update");
        insertRange.addAttribute(new Attribute("id", "updateRangeByPrimaryKey"));
        insertRange.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));

        String sql = GenerateXmlMapperUtil.generateSqlForUpdateRangeByPrimaryKey(introspectedTable);

        insertRange.addElement(new TextElement(sql));

        XmlElement rootElement = document.getRootElement();
        rootElement.addElement(insertRange);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
}

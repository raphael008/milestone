package com.github.raphael008.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;

import java.util.Arrays;
import java.util.List;

public class GenerateServicePlugin extends PluginAdapter {

    public static final String baseMapper = "com.github.raphael008.mapper.BaseMapper";
    public static final String baseService = "com.github.raphael008.service.BaseService";
    public static final String baseServiceImpl = "com.github.raphael008.service.impl.BaseServiceImpl";
    public static final String targetProject = "milestone-service/src/main/java";
    public static final String targetPackage = "com.github.raphael008.service";

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        GeneratedJavaFile service = generateService(introspectedTable);
        GeneratedJavaFile serviceImpl = generateServiceImpl(introspectedTable);
        return Arrays.asList(service, serviceImpl);
    }

    private GeneratedJavaFile generateService(IntrospectedTable table) {
        // 获取实体类型
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        // 获取示例类型
        FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(table.getExampleType());
        // 获取主键类型
        FullyQualifiedJavaType primaryType = table.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();
        // 生成 Service 名称
        String service = targetPackage + "." + table.getFullyQualifiedTable().getDomainObjectName() + "Service";
        // 构造 Service 文件
        Interface interfaze = new Interface(new FullyQualifiedJavaType(service));
        // 设置作用域
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        // import
        interfaze.addImportedType(new FullyQualifiedJavaType(baseService));
        interfaze.addImportedType(entityType);
        interfaze.addImportedType(exampleType);
        interfaze.addSuperInterface(new FullyQualifiedJavaType(String.format("%s<%s, %s, %s>",
                baseService,
                entityType.getShortName(),
                exampleType.getShortName(),
                primaryType.getShortName())));
        return new GeneratedJavaFile(interfaze, targetProject, new DefaultJavaFormatter());
    }

    private GeneratedJavaFile generateServiceImpl(IntrospectedTable table) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(table.getExampleType());
        FullyQualifiedJavaType primaryType = table.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();

        String domainObjectName = table.getFullyQualifiedTable().getDomainObjectName();
        String service = targetPackage + "." + domainObjectName + "Service";
        String serviceImpl = targetPackage + ".impl." + domainObjectName + "ServiceImpl";

        TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(serviceImpl));
        clazz.addImportedType(new FullyQualifiedJavaType(baseServiceImpl));
        clazz.addImportedType(entityType);
        clazz.addImportedType(exampleType);
        clazz.addImportedType(new FullyQualifiedJavaType(service));
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
//        clazz.addAnnotation(String.format("@Service(\"%s\")", firstLetterLowerCase(domainObjectName + "Service")));
        clazz.addAnnotation(String.format("@Service(\"%s\")", firstLetterLowerCase(domainObjectName + "Service")));
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.setSuperClass(new FullyQualifiedJavaType(String.format("%s<%s, %s, %s>",
                baseServiceImpl,
                entityType.getShortName(),
                exampleType.getShortName(),
                primaryType.getShortName())));
        clazz.addSuperInterface(new FullyQualifiedJavaType(service));

        clazz.addImportedType(baseMapper);
        clazz.addImportedType(new FullyQualifiedJavaType(String.format("%s.%sMapper", new FullyQualifiedJavaType(baseMapper).getPackageName(), entityType.getShortName())));
        clazz.addImportedType("org.springframework.beans.factory.annotation.Autowired");

        Field field = new Field(entityType.getShortName(), entityType);
        field.addAnnotation("@Autowired");
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(new FullyQualifiedJavaType(String.format("%sMapper", entityType.getShortName())));
        field.setName(String.format("%sMapper", firstLetterLowerCase(entityType.getShortName())));
        clazz.addField(field);

        Method method = new Method("getMapper");
        method.setName("getMapper");
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setReturnType(new FullyQualifiedJavaType(baseMapper));
        method.addBodyLine(String.format("return %sMapper;", firstLetterLowerCase(entityType.getShortName())));
        clazz.addMethod(method);

        return new GeneratedJavaFile(clazz, targetProject, new DefaultJavaFormatter());
    }

    private String firstLetterLowerCase(String name) {
        char c = name.charAt(0);
        if (c >= 'A' && c <= 'Z') {
            String temp = String.valueOf(c);
            return name.replaceFirst(temp, temp.toLowerCase());
        }
        return name;
    }
}

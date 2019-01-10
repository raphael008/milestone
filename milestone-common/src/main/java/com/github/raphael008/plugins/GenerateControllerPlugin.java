package com.github.raphael008.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;

import java.util.Arrays;
import java.util.List;

public class GenerateControllerPlugin extends PluginAdapter {
    public static final String baseService = "com.github.raphael008.service.BaseService";
    public static final String baseController = "com.github.raphael008.controller.BaseController";
    public static final String baseControllerImpl = "com.github.raphael008.controller.impl.BaseControllerImpl";
    public static final String targetProject = "milestone-web/src/main/java";
    public static final String targetPackage = "com.github.raphael008.controller";

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        GeneratedJavaFile serviceImpl = generateController(introspectedTable);
        return Arrays.asList(serviceImpl);
    }

    private GeneratedJavaFile generateController(IntrospectedTable table) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(table.getBaseRecordType());
        FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(table.getExampleType());
        FullyQualifiedJavaType primaryType = table.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();

        String domainObjectName = table.getFullyQualifiedTable().getDomainObjectName();
        String service = targetPackage + "." + domainObjectName + "Controller";
        String serviceImpl = targetPackage + "." + domainObjectName + "Controller";

        String typeName = entityType.getShortName();
        char upperCase = typeName.charAt(0);
        char lowerCase =  upperCase;
        if (upperCase >= 'A' && upperCase <= 'Z') {
            lowerCase = (char)(upperCase + 32);
        }
        typeName = typeName.replaceFirst(Character.toString(upperCase), Character.toString(lowerCase));

        TopLevelClass clazz = new TopLevelClass(new FullyQualifiedJavaType(serviceImpl));
        clazz.addImportedType(new FullyQualifiedJavaType(baseControllerImpl));
        clazz.addImportedType(entityType);
        clazz.addImportedType(exampleType);
        clazz.addImportedType(new FullyQualifiedJavaType(service));
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
        clazz.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));
//        clazz.addAnnotation(String.format("@Service(\"%s\")", firstLetterLowerCase(domainObjectName + "Service")));
        clazz.addAnnotation("@RestController");
        clazz.addAnnotation(String.format("@RequestMapping(value=\"%s\", produces = \"application/json\")", typeName));
        clazz.setVisibility(JavaVisibility.PUBLIC);
        clazz.setSuperClass(new FullyQualifiedJavaType(String.format("%s<%s, %s, %s>",
                baseControllerImpl,
                entityType.getShortName(),
                exampleType.getShortName(),
                primaryType.getShortName())));

        clazz.addImportedType(baseService);
        clazz.addImportedType(new FullyQualifiedJavaType(String.format("%s.%sService", new FullyQualifiedJavaType(baseService).getPackageName(), entityType.getShortName())));
        clazz.addImportedType("org.springframework.beans.factory.annotation.Autowired");

        Field field = new Field(entityType.getShortName(), entityType);
        field.addAnnotation("@Autowired");
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(new FullyQualifiedJavaType(String.format("%sService", entityType.getShortName())));
        field.setName(String.format("%sService", firstLetterLowerCase(entityType.getShortName())));
        clazz.addField(field);

        Method method = new Method("getService");
        method.setName("getService");
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setReturnType(new FullyQualifiedJavaType(baseService));
        method.addBodyLine(String.format("return %sService;", firstLetterLowerCase(entityType.getShortName())));
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

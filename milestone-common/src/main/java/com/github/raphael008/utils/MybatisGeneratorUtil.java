package com.github.raphael008.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

public class MybatisGeneratorUtil {
    public static final String driverClass = "org.mariadb.jdbc.Driver";
    public static final String connectionUrl = "jdbc:mariadb://ip:port/database";
    public static final String username = "username";
    public static final String password = "password";
    public static final Boolean overwrite = true;

    public static void generate(String... tables) {
        Context context = initContext();
        initJdbcConfig(context);
        initJavaModelConfig(context);
        initJavaClientConfig(context);
        initSqlMapConfig(context);
        initCommentConfig(context);
        initPlugins(context);

        for (String table : tables) {
            initTableConfig(context, table);

            List<String> warnings = new ArrayList<>();

            Configuration config = new Configuration();
            config.addContext(context);

            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            try {
                MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
                generator.generate(null);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            warnings.forEach(System.out::println);
        }
    }

    private static Context initContext() {
        Context context = new Context(ModelType.FLAT);
        context.setId("context");
        context.setTargetRuntime("MyBatis3");
        context.addProperty("javaFileEncoding", "UTF-8");

        return context;
    }

    private static void initJdbcConfig(Context context) {
        JDBCConnectionConfiguration jdbcConfig = new JDBCConnectionConfiguration();
        jdbcConfig.setDriverClass(driverClass);
        jdbcConfig.setConnectionURL(connectionUrl);
        jdbcConfig.setUserId(username);
        jdbcConfig.setPassword(password);
        context.setJdbcConnectionConfiguration(jdbcConfig);
    }

    private static void initJavaModelConfig(Context context) {
        JavaModelGeneratorConfiguration modelConfig = new JavaModelGeneratorConfiguration();
        modelConfig.setTargetProject("milestone-model/src/main/java");
        modelConfig.setTargetPackage("com.github.raphael008.model");
        context.setJavaModelGeneratorConfiguration(modelConfig);
    }

    private static void initJavaClientConfig(Context context) {
        JavaClientGeneratorConfiguration javaMapperConfig = new JavaClientGeneratorConfiguration();
        javaMapperConfig.setConfigurationType("XMLMAPPER");
        javaMapperConfig.setTargetProject("milestone-mapper/src/main/java");
        javaMapperConfig.setTargetPackage("com.github.raphael008.mapper");
        context.setJavaClientGeneratorConfiguration(javaMapperConfig);
    }

    private static void initSqlMapConfig(Context context) {
        SqlMapGeneratorConfiguration xmlMapperConfig = new SqlMapGeneratorConfiguration();
        xmlMapperConfig.setTargetProject("milestone-mapper/src/main/resources");
        xmlMapperConfig.setTargetPackage("com.github.raphael008.mapper");
        context.setSqlMapGeneratorConfiguration(xmlMapperConfig);
    }

    private static void initCommentConfig(Context context) {
        CommentGeneratorConfiguration commentConfig = new CommentGeneratorConfiguration();
        commentConfig.addProperty("suppressAllComments", "true");
        context.setCommentGeneratorConfiguration(commentConfig);
    }

    private static void initPlugins(Context context) {
        PluginConfiguration generateLombokPlugin = new PluginConfiguration();
        generateLombokPlugin.setConfigurationType("com.github.raphael008.plugins.GenerateLombokPlugin");

        PluginConfiguration generateMapperPlugin = new PluginConfiguration();
        generateMapperPlugin.setConfigurationType("com.github.raphael008.plugins.GenerateMapperPlugin");

        PluginConfiguration generateServicePlugin = new PluginConfiguration();
        generateServicePlugin.setConfigurationType("com.github.raphael008.plugins.GenerateServicePlugin");

        PluginConfiguration generateControllerPlugin = new PluginConfiguration();
        generateControllerPlugin.setConfigurationType("com.github.raphael008.plugins.GenerateControllerPlugin");

        PluginConfiguration generateBatchInsertPlugin = new PluginConfiguration();
        generateBatchInsertPlugin.setConfigurationType("com.github.raphael008.plugins.GenerateBatchInsertPlugin");

        context.addPluginConfiguration(generateLombokPlugin);
        context.addPluginConfiguration(generateMapperPlugin);
        context.addPluginConfiguration(generateServicePlugin);
        context.addPluginConfiguration(generateControllerPlugin);
        context.addPluginConfiguration(generateBatchInsertPlugin);
    }

    private static void initTableConfig(Context context, String table) {
        DomainObjectRenamingRule renamingRule = new DomainObjectRenamingRule();
        renamingRule.setSearchString("^(Bd)|(Dd)");
        renamingRule.setReplaceString(null);

        IgnoredColumn deletedColumn = new IgnoredColumn("deleted");
        IgnoredColumn ignoredColumn = new IgnoredColumn("ts");

        TableConfiguration tableConfig = new TableConfiguration(context);
        tableConfig.setTableName(table);
        tableConfig.setDomainObjectRenamingRule(renamingRule);
        tableConfig.addIgnoredColumn(deletedColumn);
        tableConfig.addIgnoredColumn(ignoredColumn);
        context.addTableConfiguration(tableConfig);
    }

    public static void main(String[] args) throws Exception {

        MybatisGeneratorUtil.generate("dd_bill_detail");
    }
}

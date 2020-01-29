package com.augurit.tool.codeBuilder;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 使用说明在main函数中设置好表名,然后运行项目,即可以生成一系列代码文件，然后启动项目，
 * 本模块的增删查改基本功能已经可用
 * 项目架构为springMVC+mybatis+EasyUI
 * Created by BranGao on 2016-11-01.
 */
public class CodeCommon extends CodeSuper {
    public String beanName;                   //类名
    private String tableDesc;                               //数据表名的业务描述

    /*请不要随意提交对此代码所做改动,请在自己本地使用右键忽略文件夹选项！！
        如果确认有对此文件编码功能上的改动,请知会我（高康）后提交,
        请各位不要提交无意义的改变表名或者注释某个生成语句的svn！！*/
    public static void main(String args[]) throws Exception {
        //String tableName="aea_log_apply_state_hist";

        //表名内部必须使用下划线分隔！！
        /* my.genarate(tableName);*/
        //支持批量表生成代码
        String tableNames[] = {"aea_log_apply_state_hist"};
        for (String tableNamePer : tableNames) {
            CodeCommon my = new CodeCommon(tableNamePer);
            my.genarate(tableNamePer);
            Thread.sleep(1000);
        }
        //CodeCommon my=new CodeCommon(tableName);
        //my.genarate(tableName);
    }

    static boolean getShowResult() {
        return Boolean.valueOf(CodeSuper.getPropertiesValue("showResult"));
    }

    @Override
    public String getOutputPath() {
        //String result=System.getProperty("user.dir");
//       String result="d:\\code\\";
        String result = System.getProperty("user.dir");
        //result=result.substring(0,result.lastIndexOf("\\"))+"\\"+getStaticProjectTarget();//上一层目录
        result = result + "\\" + getStaticProjectTarget();//上一层目录
        return result;
    }

    private String src;                                       //JAVA代码路径--包名以外的绝对路径
    private String resource_src;                              //资源配置文件路径--包名以外的绝对路径
    private String jsp_path;                                 //jsp文件路径--报名以外的绝对路径
    private String js_path;                                 //js文件路径--报名以外的绝对路径
    private String baseSrc;                                 //包名
    private String domainbaseSrc;                          //domain文件的包名
    private String sqlmapperbaseSrc;                       //sqlmapper文件的包名

    public void genarate(String tableName) throws Exception {
        /*CodeCommon my;
        //表名必须使用下划线分隔！！
                *//*String tableName="erp_report_sev_contract_lw";
        String tableName="crm_contract_info_value_item";*//*
        my=new CodeCommon(tableName);*/
        init(tableName);
//        my=new CodeCommon("erp_mgmt_okr_target");
//        my.onUsingTableNames="crm_base_client_info,crm1_base_linkman_info,crm1_proj_base_info,spl1_base_supplier_info,erp_mgmt_okr_task,";
//        if(my.hasTableOnUsing(my.getTableName())){
//            System.out.println("该表目前正在使用，重新覆盖代码或许会对系统造成较大影响！\n 请尽量换个tablename来进行代码生成....");
//            return;
//        }
        System.out.println(getTableName());
        //setTableNameDesc(createTableNameDesc().substring(0,createTableNameDesc().length()-1));
//        setTableNameDesc("项目计划模板节点"); //如果数据库中表名没有描述,则自己手动赋值给tablenamedesc
        //setTableNameDesc("项目管理信息"); //如果数据库中表名没有描述,则自己手动赋值给tablenamedesc
        Map root = builTemplateForOneTable();
       /* if(false==(validCodeConflict(root,tableName))){
            System.out.println("该表目前正在使用，重新覆盖代码或许会对系统造成较大影响！\n 请尽量换个tablename来进行代码生成....");
            return;
        }*/
        /**
         * log.debug for show test  string
         */
        log.debug("JspFileName: \t" + fetchJspFileName());
        log.debug("MappingBaseUrl: \t" + fetchMappingBaseUrl());
        log.debug("JspFilePath: \t" + fetchJspFilePath());
        log.debug("jspfilename: \t" + jsp_file_name);
        /**
         * 生成代码文件
         */
        createCodeForSqlMapperXml(root);
        createCodeForVo(root);        //居然有人把这句话注释了然后提交，害我编辑到一半发现不对然后又要备份文件重新生成！
        createCodeForMapper(root);
        createCodeForService(root);
        createCodeForServiceImpl(root);
        createCodeForController(root);
//        createCodeForJspFile(root);
//        createCodeForJsFile(root);
    }

    public void mutiGenarate(String tableNames[]) throws Exception {
        for (String tableName : tableNames) {
            genarate(tableName);
        }
    }

    public String domain_clazz;
    public String sqlmapper_clazz;
    public String mapper_clazz;
    public String service_inteface_clazz;
    public String service_clazz;
    public String controller_clazz;
    public String httpRequestBase_url;
    public String jsp_file_name;
    public String onUsingTableNames;

    public CodeCommon(String tablename) {
        init(tablename);
    }

    public CodeCommon() {
    }

    public void init(String tablename) {
        setTableName(tablename);
        setSrc(getOutputPath() + "\\src\\main\\java");
        setResource_src(getOutputPath() + "\\src\\main\\resources");
        setBaseSrc(AllBase_Pkg_Src);     //包名
        setDomainbaseSrc(fetchModuleBase(getTableName()) + ".domain");          //domain文件的包名
        setSqlmapperbaseSrc(fetchModuleBase(getTableName()) + ".mapper");       //sqlmapper文件的包名
        setJsp_path(fetchJspFilePath());
        beanName = filedTransformer(getTableName());                   //类名
        domain_clazz = domainbaseSrc + "." + beanName;
        sqlmapper_clazz = sqlmapperbaseSrc + "." + beanName + "Mapper";
        mapper_clazz = sqlmapperbaseSrc + "." + beanName + "Mapper";
        service_inteface_clazz = baseSrc + "." + fetchServicePath(getTableName()) + ".service." + beanName + "Service";
        service_clazz = baseSrc + "." + fetchServicePath(getTableName()) + ".service.impl." + beanName + "ServiceImpl";
        controller_clazz = baseSrc + "." + fetchServicePath(getTableName()) + ".controller." + beanName + "Controller";
        httpRequestBase_url = fetchMappingBaseUrl();
        jsp_file_name = fetchJspFileName() + "_index";
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) { this.src = src; }

    public String getResource_src() {
        return resource_src;
    }

    public void setResource_src(String resource_src) { this.resource_src = resource_src; }

    public String getBaseSrc() {
        return baseSrc;
    }

    public void setBaseSrc(String baseSrc) {
        this.baseSrc = baseSrc;
    }

    public String getDomainbaseSrc() {
        return domainbaseSrc;
    }

    public void setDomainbaseSrc(String domainbaseSrc) {
        this.domainbaseSrc = domainbaseSrc;
    }

    public String getSqlmapperbaseSrc() {
        return sqlmapperbaseSrc;
    }

    public void setSqlmapperbaseSrc(String sqlmapperbaseSrc) {
        this.sqlmapperbaseSrc = sqlmapperbaseSrc;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getJsp_path() { return jsp_path; }

    public void setJsp_path(String jsp_path) { this.jsp_path = jsp_path; }

    public String getJs_path() { return js_path; }

    public void setJs_path(String js_path) { this.js_path = js_path; }


    @Override
    public String createTableNameDesc() {
        String sql_desc = "";
        BasicDataSource dataSource;
        Connection connection;
        PreparedStatement prestmt;
        String tabledesc = "";
        if (getTableNameDesc() != null && !"".equals(getTableNameDesc().trim())) {
            return getTableNameDesc();
        }

        try {
            dataSource = getBasicDataSource();
            sql_desc = getDiarectMetaSql().getTableNameDesc(getDataBaseName());
            connection = dataSource.getConnection();
            prestmt = connection.prepareStatement(sql_desc);
            prestmt.setString(1, this.getTableName());

            ResultSet rs = prestmt.executeQuery();
            while (rs.next()) {
                tabledesc = rs.getString(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tabledesc;
    }


    /***
     * 这样一个方法,主要输出模板参数map,以及模板类,作为预处理过程,每个表只需要初始化一次就行,接下来,就是创建
     * 各个文件了，所以本方法需要把各个单独文件的共同参数map 整理出来
     * @return
     * @throws SQLException
     */
    public Map builTemplateForOneTable() throws Exception {
        try {
            BasicDataSource dataSource = getBasicDataSource();
            String sql_rmark = getDiarectMetaSql().getTableColumnsBaseSql(getDataBaseName(), getTableName());
            System.out.println(sql_rmark);
            List listTableRowMateData = new ArrayList();
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultset_stmt_rmark = stmt.executeQuery(sql_rmark);
            int i = 0;
            List<TableColumnBean> columns = new ArrayList<TableColumnBean>();
            List<String> pk_columns = new ArrayList<String>();

            while (resultset_stmt_rmark.next()) {
                TableColumnBean o = new TableColumnBean();
                o.setDb_fields(resultset_stmt_rmark.getString("COLUMN_NAME"));
                if ("IS_DELETED".equals(resultset_stmt_rmark.getString("COLUMN_NAME"))) {
                    setTrueDelete(true);
                }

                o.setFields(filedTransformer(resultset_stmt_rmark.getString("COLUMN_NAME")));
                o.setTableName(getTableName());
                o.setType(resultset_stmt_rmark.getString("DATA_TYPE").toUpperCase());//   stmt_meta.getColumnTypeName(i));
                o.setLength(resultset_stmt_rmark.getInt("CHARACTER_MAXIMUM_LENGTH") == 0 ? (resultset_stmt_rmark.getInt("NUMERIC_PRECISION") == 0 ? resultset_stmt_rmark.getInt("DATETIME_PRECISION") : resultset_stmt_rmark.getInt("NUMERIC_PRECISION")) : resultset_stmt_rmark.getInt("CHARACTER_MAXIMUM_LENGTH"));
                o.setRemark(resultset_stmt_rmark.getString("COLUMN_COMMENT"));
                o.setScale(resultset_stmt_rmark.getInt("NUMERIC_SCALE"));
                o.setTableDesc(getTableNameDesc());
                o.setColumnKey(resultset_stmt_rmark.getString("COLUMN_KEY"));

                if ("YES".equals(resultset_stmt_rmark.getString("IS_NULLABLE"))) {
                    o.enable();
                } else {
                    o.disable();
                }

                if (resultset_stmt_rmark.getString("COLUMN_KEY") != null && !"".equals(resultset_stmt_rmark.getString("COLUMN_KEY").trim())) {
                    if ("PRI".equals(resultset_stmt_rmark.getString("COLUMN_KEY").toUpperCase()))
                        pk_columns.add(resultset_stmt_rmark.getString("COLUMN_NAME"));
                }
                listTableRowMateData.add(o);
                columns.add(o);
                i++;
            }

            if (getShowResult()) System.out.println(listTableRowMateData);
            stmt.close();
            connection.close();

            Map root = new HashMap();

            root.put("bean_created", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            root.put("bean_author", System.getProperty("user.name"));
            root.put("bean_property", columns);
            //root.put("bean_parent", parent == null ? null : parent.getName());
            root.put("isreallydelete", getTrueDelete());  //是否删除物理字段
            root.put("bean_parent", null);

            root.put("bean_pk_field", filedTransformer(pk_columns.isEmpty() ? null : pk_columns.get(0)));
            try {
                root.put("bean_pk", pk_columns.isEmpty() ? null : pk_columns.get(0));
                root.put("bean_desc", filedTransformer(getTableNameDesc()));
            } catch (Exception e) {
                throw e;
            }
            root.put("bean_table", filedTransformer(getTableName()));
            root.put("bean_db_table", getTableName().toUpperCase());

            root.put("vo_class", domain_clazz);
            root.put("vo_class_name", beanName);
            root.put("vo_obj_name", fetchObjName(getTableName()));
            root.put("service_class", service_inteface_clazz);
            root.put("service_class_name", beanName + "Service");
            root.put("mappingBaseUrl", httpRequestBase_url);
            root.put("jspFolder", fetchJspFolder());
            root.put("jspFileName", jsp_file_name);
            root.put("mapper_class", mapper_clazz);
            root.put("mapper_class_name", beanName + "Mapper");

            return root;
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean hasTableOnUsing(String tableName) {
        if (onUsingTableNames.toLowerCase().indexOf(tableName.trim().toLowerCase()) >= 0) return true;
        return false;
    }

    /*
     * 通过domain去检查是否冲突，代替手动维护
     * 验证通过返回true,否则false
     * */
    protected boolean validCodeConflict(Map root, String tableName) {
        boolean result = false;
        String javaFilePath = src + "\\" + domain_clazz.replace('.', '\\') + ".java";
        File file = new File(javaFilePath);
        if (file.exists()) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    public void createCodeForVo(Map root) throws Exception {
        buildCodeForTemplater(src, domain_clazz, "UJavaBean.ftl", ".java", root);
    }

    public void createCodeForSqlMapperXml(Map root) throws Exception {
        //buildCodeForTemplater(resource_src,sqlmapper_clazz,"USqlMapper.ftl",".xml",root);
        buildCodeForTemplater(resource_src, sqlmapper_clazz, "USqlMapperAddColumnMap.ftl", ".xml", root);
    }

    public void createCodeForMapper(Map root) throws Exception {
        buildCodeForTemplater(src, mapper_clazz, "UMapper.ftl", ".java", root);               //mapper dao接口生成
    }

    public void createCodeForService(Map root) throws Exception {
        buildCodeForTemplater(src, service_inteface_clazz, "UService.ftl", ".java", root);
    }

    public void createCodeForServiceImpl(Map root) throws Exception {
        buildCodeForTemplater(src, service_clazz, "UServiceImpl.ftl", ".java", root);
    }

    public void createCodeForController(Map root) throws Exception {
        buildCodeForTemplater(src, controller_clazz, "UController.ftl", ".java", root);
    }

    public void createCodeForJspFile(Map root) throws Exception {
        buildCodeForTemplater("", "", "UIndexJsp.ftl", ".jsp", root);
    }

    public void createCodeForJsFile(Map root) throws Exception {
        buildCodeForTemplater("", "", "UIndexJs.ftl", ".js", root);
    }
}

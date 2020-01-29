package com.augurit.tool.codeBuilder;

import com.augurit.agcloud.helper.utils.PropertiesManager;
import com.augurit.tool.utils.AbstractDiarectMetaSql;
import com.augurit.tool.utils.DiarectMetaSql;
import com.augurit.tool.utils.DiarectStrEnum;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BranGao on 2016-11-01.
 */
public abstract class CodeSuper {

    public static Log log = LogFactory.getLog(CodeSuper.class);
    //项目路径
    public static String Generator_Project_Path = System.getProperty("user.dir");
    public static Map<String, String> FIELD_DESC = new HashMap<String, String>();
    //public static String TABLE_DB_SCHEMA = "agcloud";//数据库名
    //public static String TABLE_DB_SCHEMA = "newagcloud193";//数据库名

    public static String AllBase_Pkg_Src = "com.augurit.agcloud";   //最上层包名
    //public static  String Base_Project_Path = "D:\\code\\bsc";
    //public static String ProjDirectName_Child="cms";  // 子项目包路径名
    //public static String templater_project=CodeSuper.getPropertiesValue("templater_project");
    public static String property_FileName = Generator_Project_Path + "\\src\\main\\resources\\application.properties";  //数据库链接与配置文件路径
    //        "\\src\\main\\java\\com\\augurit\\agcloud\\helper\\utils\\mysql.properties"
    //public static String Templater_Path = ;
    public static String Web_UI_JSP_PATH = "\\src\\main\\webapp\\WEB-INF\\ui-jsp";
    public static String Web_UI_JS_PATH = "\\src\\main\\webapp";
    // "\\crm\\base\\client\\client1_info_index_bak.jsp";
    public static PropertiesManager pm;
    private BasicDataSource dataSource;
    private String fileName;//不带全路径的文件名，不带后缀
    private String filePath;//获取将要创建的文件路径
    private String javaClassName;//类名
    private String javaClassPath;//类路径-包路径
    private DiarectMetaSql diarectMetaSql;
    private String javaCommonPackagePath;//"com.augurit"
    private String tablePk;//主键
    private String tableName;//数据库表名
    private String tableNameDesc;//数据库表名描述
    private Boolean trueDelete = false;//是否真实删除数据,0否-逻辑删除,1是-物理删除   是否含有IS_DELETED字段
    private String schameName;//数据库名

    public static PropertiesManager getPropertiesManager() {
        try {
            if (pm == null) {
                File file = new File(property_FileName);
                pm = new PropertiesManager(new FileInputStream(file));
            }
            pm.setProperty("file", property_FileName);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return pm;
    }

    public static String getPropertiesValue(String propertiesKey) {
        PropertiesManager pm = CodeSuper.getPropertiesManager();
        return pm.getString(propertiesKey) == null ? "" : pm.getString(propertiesKey);
    }

    public static String fetchBeanName(String tableFileds) {
        tableFileds = tableFileds.toLowerCase();
        StringBuilder sb = new StringBuilder();
        //解开数据库中的下划线分隔
        String[] filed_chars = tableFileds.split("_");
        if (filed_chars.length > 1) {

            for (int i = 0; i < filed_chars.length; i++) {
                //去掉头尾的字符串
                if (i != 0 & i != (filed_chars.length - 1)) {
                    sb.append(filed_chars[i].substring(0, 1).toUpperCase() + filed_chars[i].substring(1));
                }
            }
        } else {
            sb.append(tableFileds);
        }
        String objNameBefore = sb.toString();
        return objNameBefore.substring(0, 1).toLowerCase() + objNameBefore.substring(1);
    }

    public abstract String getOutputPath();

    public static String getStaticTemplaterPath() {
        return Generator_Project_Path + "\\" + CodeSuper.getPropertiesValue("templater_project") + CodeSuper.getPropertiesValue("template_path");
    }

    public static String getStaticProjectTarget() {
        return CodeSuper.getPropertiesValue("target_genarate_project_name");
    }

    public static String getDataBaseName() {
        return CodeSuper.getPropertiesValue("table_db_schema");
    }

    /***
     * 根据表名获得sc的额外附加包名
     * @param tablename  表名   比如 crm_base_client_info  拆分为crm.sc.base.service等
     * @return
     */
    public String fetchServicePath(String tablename) {
        //1.全部小写
        //2.根据下划线分隔替换为点号分隔
        //3.去掉头尾,头部和中间之间加入sc
        tablename = tablename.toLowerCase();
        tablename = tablename.replaceAll("_", ".");
        return tablename.substring(0, tablename.indexOf(".") + 1) + "sc" + tablename.substring(tablename.indexOf("."), tablename.lastIndexOf("."));
    }

    /***
     *返回VO对象javabean的名称,命令规则是 数据库表名去掉"_"分割之后 最开始的字串以及结尾的字串
     * @param tableFileds
     * @return
     */
    public String fetchObjName(String tableFileds) {
        tableFileds = tableFileds.toLowerCase();
        StringBuilder sb = new StringBuilder();
        //解开数据库中的下划线分隔
        String[] filed_chars = tableFileds.split("_");
        if (filed_chars.length > 1) {

            for (int i = 0; i < filed_chars.length; i++) {
                //去掉头尾的字符串
//                if(i!=0 & i!=(filed_chars.length-1)) {
//                    sb.append(filed_chars[i].substring(0, 1).toUpperCase() + filed_chars[i].substring(1));
//                }
                sb.append(filed_chars[i].substring(0, 1).toUpperCase() + filed_chars[i].substring(1));
            }
        } else {
            sb.append(tableFileds);
        }
        String objNameBefore = sb.toString();
        return objNameBefore.substring(0, 1).toLowerCase() + objNameBefore.substring(1);
    }

    /***
     *根据表名获取包名源路径
     * @param tablename
     * @return
     */
    public String fetchModuleBase(String tablename) {
        tablename = tablename.toLowerCase();
        return AllBase_Pkg_Src + "." + tablename.substring(0, tablename.indexOf("_"));
    }

    /***
     * 根据表名第一个下划线前面的字串生成jsp、js的UI文件夹
     * @return
     */
    public String fetchPageDirect() {
        String tablename = getTableName().toLowerCase();
        return "/" + tablename.toLowerCase().substring(0, tablename.lastIndexOf("_")).replace("_", "/");
    }

    public String fetchJspFolder() {
        String tablename = getTableName().toLowerCase();
        String result = "/" + tablename.toLowerCase().substring(0, tablename.lastIndexOf("_")).replace("_", "/");
        return result;
    }

    public String fetchMappingBaseUrl() {
        String tablename = getTableName().toLowerCase();
        //"/"+tablename.toLowerCase().substring(0,tablename.lastIndexOf("_")).replace("_","/");
        String result = "/" + tablename.toLowerCase().replace("_", "/");
        return result;
    }

    public String fetchJspFileName() {
        String tablename = getTableName().toLowerCase();
        int i = tablename.indexOf("_", tablename.indexOf("_") + 1);
        String filename = tablename.substring(i + 1, tablename.length());
        return filename;
    }

    public String fetchJspFilePath() {
        //String requestMappingBaseUrl =
        String tablename = getTableName().toLowerCase();
        return getOutputPath() + Web_UI_JSP_PATH + "\\" + tablename.toLowerCase().substring(0, tablename.lastIndexOf("_")).replace("_", "\\") + "\\" + fetchJspFileName() + "_index";
    }

    public String fetchJsFilePath() {
        //String requestMappingBaseUrl =
        String tablename = getTableName().toLowerCase();
        return getOutputPath() + Web_UI_JS_PATH + "\\" + tablename.toLowerCase().substring(0, tablename.indexOf("_")).replace("_", "\\") + "\\ui\\" + fetchJspFileName() + "_index";
    }

    /* public abstract  String createFileName();//不带全路径的文件名，不带后缀
     public abstract  String createFilePath();//获取将要创建的文件路径
     public abstract  String createJavaClassName();//类名
     public abstract  String createJavaClassPath();//类路径-包路径
     public abstract  String createJavaCommonPackagePath();//"com.augurit"

     public abstract String createTablePk();//主键*/
    //public abstract String getTableName();//数据库表名
    public abstract String createTableNameDesc();//数据库表名描述
    //public abstract Boolean isTrueDelete();//是否真实删除数据,0否-逻辑删除,1是-物理删除   是否含有IS_DELETED字段
    /*public abstract String createSchameName();//数据库名*/

    public BasicDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getJavaClassName() {
        return javaClassName;
    }

    public void setJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
    }

    public String getJavaClassPath() {
        return javaClassPath;
    }

    public void setJavaClassPath(String javaClassPath) {
        this.javaClassPath = javaClassPath;
    }

    public String getJavaCommonPackagePath() {
        return javaCommonPackagePath;
    }

    public void setJavaCommonPackagePath(String javaCommonPackagePath) {
        this.javaCommonPackagePath = javaCommonPackagePath;
    }

    public String getTablePk() {
        return tablePk;
    }

    public void setTablePk(String tablePk) {
        this.tablePk = tablePk;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public BasicDataSource getBasicDataSource() throws Exception {
        if (dataSource != null) return dataSource;
        File file = new File(property_FileName);
        PropertiesManager pm = CodeSuper.getPropertiesManager();
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(pm.getString("spring.datasource.driver-class-name"));
        dataSource.setUrl(pm.getString("spring.datasource.url"));
        dataSource.setUsername(pm.getString("spring.datasource.username"));
        dataSource.setPassword(pm.getString("spring.datasource.password"));
        diarectMetaSql = AbstractDiarectMetaSql.createInstance(DiarectStrEnum.fromJdbcUrl(pm.getString("spring.datasource.url")));
        return dataSource;
    }

    /***
     * 创建代码文件method
     * @param src  package的外部绝对路径
     * @param packageAndClassPath   包以及类名路径
     * @param ftl_file_name        模板文件   xxx.ftl
     * @param file_type            文件类型-后缀名  如:.xml,.java
     * @param root                 从前面方法传过来的模板上下文 参数map
     * @throws Exception
     */
    public void buildCodeForTemplater(String src, String packageAndClassPath, String ftl_file_name, String file_type, Map root) throws Exception {
        String javaFilePath = "";
        // 处理文件地址
        boolean isPagePath = false;
        isPagePath = ".jsp".equals(file_type) || ".js".equals(file_type);
        if (isPagePath) {
            if (".jsp".equals(file_type)) {
                javaFilePath = fetchJspFilePath() + file_type;
            } else {
                javaFilePath = fetchJsFilePath() + file_type;
            }

        } else {
            javaFilePath = src + "\\" + packageAndClassPath.replace('.', '\\') + file_type;
        }
        File javaFilePath_Direct = new File(javaFilePath);
        // 级联建立好文件存在的目录
        if (!javaFilePath_Direct.getParentFile().exists()) {
            javaFilePath_Direct.getParentFile().mkdirs();
        }

        // 模板上下文
        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(CodeSuper.getStaticTemplaterPath()));
        cfg.setDefaultEncoding("GBK");
        // 建立模板
        Template t = cfg.getTemplate(ftl_file_name, "UTF-8");
        Writer out = null;
        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(javaFilePath), "UTF-8"));
        //拷贝一份map对象
        Map newFileParamsMap = new HashMap();
        newFileParamsMap.putAll(root);
        if (!isPagePath) {
            newFileParamsMap.put("bean_pkg", packageAndClassPath.substring(0, packageAndClassPath.lastIndexOf(".")));
            newFileParamsMap.put("bean_type", packageAndClassPath.substring(packageAndClassPath.lastIndexOf(".") + 1));
            newFileParamsMap.put("bean_class_package", packageAndClassPath);
        }
        // 根据模板来自动编写代码文件
        t.process(newFileParamsMap, out);
        //log.info("成功创建文件"+javaFilePath);
        System.out.println("成功创建文件" + javaFilePath);
        out.flush();
        out.close();
    }

    public void setTableNameDesc(String tableNameDesc) {
        this.tableNameDesc = tableNameDesc;
    }

    public Boolean getTrueDelete() {
        return trueDelete;
    }

    public void setTrueDelete(Boolean trueDelete) {
        this.trueDelete = trueDelete;
    }

    public String getSchameName() {
        return schameName;
    }

    public void setSchameName(String schameName) {
        this.schameName = schameName;
    }

    public DiarectMetaSql getDiarectMetaSql() {
        return diarectMetaSql;
    }

    /***
     * 数据库字段名转换为java字段名
     * 规则为将类似 CLIENT_INFO_ID 转换为 clientInfoId这样的名称
     * 1.全部小写2 下划线之后第一个字母大写3 去掉下划线4 在 ftl文件中 将首字母小写
     * */
    public String filedTransformer(String tableFileds) {
        if (tableFileds == null) return "";
        tableFileds = tableFileds.toLowerCase();
        StringBuilder sb = new StringBuilder();
        //解开数据库中的下划线分隔
        String[] filed_chars = tableFileds.split("_");
        if (filed_chars.length > 1) {
            for (int i = 0; i < filed_chars.length; i++) {
                sb.append(filed_chars[i].substring(0, 1).toUpperCase() + filed_chars[i].substring(1));
            }
        } else {
            sb.append(tableFileds);
        }
        return sb.toString();
    }

    public Map getTableInfo() {
        Map result = new HashMap<String, Object>();
        try {
            BasicDataSource dataSource = getBasicDataSource();
            String sql_rmark = diarectMetaSql.getTableNameBaseSql(getDataBaseName(), getTableName());
            //System.out.println(sql_rmark);
            List listTableRowMateData = new ArrayList();
            Connection connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultset_stmt_rmark = stmt.executeQuery(sql_rmark);
            String tableComment = "";
            while (resultset_stmt_rmark.next()) {
                tableComment = resultset_stmt_rmark.getString("TABLE_COMMENT");
            }
            stmt.close();
            connection.close();
            result.put("tableComment", tableComment);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return result;
    }

    public String getTableNameDesc() {

        Map map = getTableInfo();
        if (map.get("tableComment") != null || map.get("TABLECOMMENT") != null) {
            tableNameDesc = map.get("tableComment").toString();
        }

        return tableNameDesc;
    }
}

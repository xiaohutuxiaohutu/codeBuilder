package com.augurit.tool.utils;

public interface DiarectMetaSql {
    public String tableNameBaseSql="";
    public String tableColumnsBaseSql="";
    public String tableColumnWithPriKeyBaseSql="";
    public String tableColumnWithAllConstransBaseSql="";

    public String getTableNameBaseSql(String dbName,String tableName);
    public String getTableColumnsBaseSql(String dbName,String tableName);
    public String getTableNameDesc(String dbName);

}

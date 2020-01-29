package com.augurit.tool.utils;

public interface DiarectMetaSql {
    String tableNameBaseSql = "";
    String tableColumnsBaseSql = "";
    String tableColumnWithPriKeyBaseSql = "";
    String tableColumnWithAllConstransBaseSql = "";

    String getTableNameBaseSql(String dbName, String tableName);

    String getTableColumnsBaseSql(String dbName, String tableName);

    String getTableNameDesc(String dbName);

}

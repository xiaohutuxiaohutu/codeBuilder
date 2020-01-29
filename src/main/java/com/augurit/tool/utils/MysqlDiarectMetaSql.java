package com.augurit.tool.utils;

public class MysqlDiarectMetaSql extends AbstractDiarectMetaSql {
    @Override
    public String getTableNameBaseSql(String dbName, String tableName) {
        String sql_rmark = "select TABLE_NAME,TABLE_COMMENT from information_schema.tables " +
                "WHERE table_name = '" + tableName
                + "' and TABLE_SCHEMA='" + dbName + "' ";
        return sql_rmark;
    }

    @Override
    public String getTableColumnsBaseSql(String dbName, String tableName) {
        String sql = "SELECT 	c.COLUMN_NAME,	c.TABLE_NAME,	c.COLUMN_COMMENT,	c.IS_NULLABLE,	c.COLUMN_TYPE," +
                "	c.NUMERIC_PRECISION,	c.NUMERIC_SCALE,	c.DATETIME_PRECISION," +
                "c.DATA_TYPE,	c.CHARACTER_MAXIMUM_LENGTH,	c.COLUMN_KEY,	c.ORDINAL_POSITION" +
                " FROM 	information_schema. COLUMNS c 	WHERE c.table_name = '" + tableName
                + "' and c.TABLE_SCHEMA='" + dbName + "' ORDER  BY  c.ORDINAL_POSITION";
        ;
        return sql;
    }


    @Override
    public String getTableNameDesc(String dbName) {
        String sql = "SELECT T.TABLE_NAME,T.TABLE_SCHEMA,T.TABLE_COMMENT FROM information_schema.`TABLES` T  WHERE T.TABLE_SCHEMA='" + dbName + "' and T.TABLE_NAME='?'  ";
        return sql;
    }
}

package com.augurit.tool.utils;

public class OracleDiarectMetaSql extends AbstractDiarectMetaSql {
    @Override
    public String getTableNameBaseSql(String dbName, String tableName) {
        String sql="select TC.TABLE_NAME, CASE WHEN TC.TABLE_TYPE='TABLE' THEN 'BASE TABLE' ELSE 'VIEW' END AS TABLE_TYPE,\n" +
                "TC.COMMENTS  AS  TABLE_COMMENT,T.TABLESPACE_NAME from user_tab_comments  TC INNER JOIN\n" +
                "USER_TABLES T ON TC.TABLE_NAME=T.TABLE_NAME AND T.TABLESPACE_NAME='USERS' AND TC.TABLE_NAME='"+tableName+"'";
        return sql;
    }

    @Override
    public String getTableColumnsBaseSql(String dbName, String tableName) {
        String sql="select sub1.*,sub2.column_name,case when sub1.column_name=sub2.column_name then 'PRI' END AS COLUMN_KEY  from (\n" +
                "SELECT   A.TABLE_NAME,A.COLUMN_NAME,B.comments COLUMN_COMMENT,A.NULLABLE IS_NULLABLE,\n" +
                "A.DATA_DEFAULT COLUMN_DEFAULT,A.DATA_TYPE ,A.DATA_LENGTH CHARACTER_MAXIMUM_LENGTH,\n" +
                "A.DATA_PRECISION AS NUMERIC_PRECISION,A.DATA_SCALE NUMERIC_SCALE,A.COLUMN_ID ORDINAL_POSITION\n" +
                " FROM USER_TAB_COLUMNS A ,USER_COL_COMMENTS B \n" +
                "WHERE   A.TABLE_NAME=B.TABLE_NAME AND A.COLUMN_NAME=B.column_name \n" +
                ") sub1, \n" +
                "(select\n" +
                " col.table_name,col.column_name,col.owner,con.constraint_name\n" +
                "from\n" +
                " user_constraints con,user_cons_columns col\n" +
                "where   \n" +
                " con.constraint_name=col.constraint_name and con.constraint_type='P'\n" +
                " and col.owner='"+dbName.toUpperCase()+"'\n" +
                "  )   sub2\n" +
                " where  sub1.table_name=sub2.table_name  and sub1.table_name =  '"+tableName.toUpperCase()+"'\n" +
                "  order by  sub1.TABLE_NAME,sub1.ORDINAL_POSITION ASC\n" +
                "  ";
        return sql;
    }
    @Override
    public String getTableNameDesc(String dbName) {
        String sql="";
        if(dbName==null){
            sql= "select TC.TABLE_NAME, CASE WHEN TC.TABLE_TYPE='TABLE' THEN 'BASE TABLE' ELSE 'VIEW' END AS TABLE_TYPE,\n" +
                    "TC.COMMENTS  AS  TABLE_COMMENT,T.TABLESPACE_NAME from user_tab_comments  TC INNER JOIN\n" +
                    "USER_TABLES T ON TC.TABLE_NAME=T.TABLE_NAME AND T.TABLESPACE_NAME='USERS' AND TC.TABLE_NAME='?'";
        }
        return sql;
    }
}

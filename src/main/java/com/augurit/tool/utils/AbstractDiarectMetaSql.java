package com.augurit.tool.utils;

public abstract class AbstractDiarectMetaSql implements DiarectMetaSql  {

    private final static OracleDiarectMetaSql oracleDiarectMetaSql=new OracleDiarectMetaSql();
    private final static MysqlDiarectMetaSql mysqlDiarectMetaSql=new MysqlDiarectMetaSql();

    public static   DiarectMetaSql createInstance(String dbTyp){
        switch (dbTyp){
            case "oracle":
                return oracleDiarectMetaSql;
            case "mysql":
                return  mysqlDiarectMetaSql;
            default:
                return  mysqlDiarectMetaSql;
        }
    }
}

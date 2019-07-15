package com.augurit.tool.utils;

/**
 * Created by ASUS on 2018/1/10.
 */
public enum DiarectStrEnum {
    mysql, mariadb, sqlite, oracle, hsqldb, postgresql, sqlserver, db2, informix, h2, sqlserver2012;

    public static DiarectStrEnum of(String dialect) {
        try {
            DiarectStrEnum d = DiarectStrEnum.valueOf(dialect.toLowerCase());
            return d;
        } catch (IllegalArgumentException e) {
            String dialects = null;
            for (DiarectStrEnum d : DiarectStrEnum.values()) {
                if (dialects == null) {
                    dialects = d.toString();
                } else {
                    dialects += "," + d;
                }
            }
            throw new IllegalArgumentException("Mybatis分页插件dialect参数值错误，可选值为[" + dialects + "]");
        }
    }

    public static String[] dialects() {
        DiarectStrEnum[] dialects = DiarectStrEnum.values();
        String[] ds = new String[dialects.length];
        for (int i = 0; i < dialects.length; i++) {
            ds[i] = dialects[i].toString();
        }
        return ds;
    }

    public static String fromJdbcUrl(String jdbcUrl) {
        String[] dialects = dialects();
        for (String dialect : dialects) {
            if (jdbcUrl.indexOf(":" + dialect + ":") != -1) {
                return dialect;
            }
        }
        return null;
    }
}

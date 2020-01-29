package com.augurit.tool.codeBuilder;

import java.util.Date;

/**
 * TableModelDesc
 * <p>
 * TableColumnBean:表格模型描述
 * </P>
 *
 * @author gaokang 2014-5-27
 * @version 0.0.1
 */
public class TableColumnBean {
    // ----------------------------------------------------- Properties
    /**
     * 描述
     */
    private String db_fields;

    private String fields;
    /**
     * 数据库字段类型
     */
    private String type;
    /**
     * 长度
     */
    private Integer length;
    /**
     * 精度
     */
    private Integer scale;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 备注
     */
    private String remark;
    /**
     * 描述
     */
    private String tableDesc;
    /**
     * 是否不为空
     */
    private boolean enableNull = true;
    /**
     * 表名
     */
    private String tableName;

    /**
     * 键   主键 : PRI
     */
    private String columnKey;

    // ----------------------------------------------------- Constructors

    // ----------------------------------------------------- Methods


    /**
     * 获取数据库字段类型对应的java类型
     *
     * @return String java类型
     */
    public String getJavaTypeDesc() {
        return getJavaType().getName();
    }

    @SuppressWarnings("unchecked")
    public Class getJavaType() {
        if (type.equals("VARCHAR") || type.equals("CHAR")) {
            return String.class;
        } else if (type.equals("DATE") || type.equals("DATETIME") || type.equals("TIMESTAMP")) {
            return Date.class;
        } else if (type.equals("TINYTEXT") || type.equals("LONGTEXT") || type.equals("TEXT")) {
            return String.class;
        } else if (type.equals("NUMBER") || type.equals("INT") || type.equals("DOUBLE") || type.equals("DECIMAL")) {
            if (scale != null && scale != 0) {
                return Double.class;
            }

            if (length >= 10) {
                return Long.class;
            }
            return Integer.class;
        }

        return String.class;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public void enable() {
        this.enableNull = true;
    }

    public void disable() {
        this.enableNull = false;
    }

    public boolean isEnableNull() {
        return enableNull;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDb_fields() {
        return db_fields;
    }

    public void setDb_fields(String db_fields) {
        this.db_fields = db_fields;
    }

    public String getColumnKey() { return columnKey; }

    public void setColumnKey(String columnKey) { this.columnKey = columnKey; }

    @Override
    public String toString() {
        return "\n TableColumnBean{" +
                "db_fields='" + db_fields + '\'' +
                ", fields='" + fields + '\'' +
                ", type='" + type + '\'' +
                ", length=" + length +
                ", scale=" + scale +
                ", defaultValue='" + defaultValue + '\'' +
                ", remark='" + remark + '\'' +
                ", tableDesc='" + tableDesc + '\'' +
                ", enableNull=" + enableNull +
                ", tableName='" + tableName + '\'' +
                ", columnKey='" + columnKey + '\'' +
                '}';
    }
}

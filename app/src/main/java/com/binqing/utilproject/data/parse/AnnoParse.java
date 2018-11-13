package com.binqing.utilproject.data.parse;

import android.text.TextUtils;

import com.binqing.utilproject.data.annotation.Column;
import com.binqing.utilproject.data.annotation.Table;
import com.binqing.utilproject.data.model.ColumnInfo;
import com.binqing.utilproject.data.model.DataType;
import com.binqing.utilproject.data.model.TableInfo;

import java.lang.reflect.Field;
import java.util.HashMap;


/**
 * 解析注解
 */
public class AnnoParse {
    private static final String TAG = "AnnoParse";

    /**
     * 初始化table 的信息
     *
     * @param clazz
     */
    public static TableInfo initTableInfo(Class<?> clazz) {
        Table table = (Table) clazz.getAnnotation(Table.class);
        if (table == null) {
            return null;
        }
        TableInfo tableInfo = new TableInfo();
        tableInfo.clazzName = clazz.getName();
        if (TextUtils.isEmpty(table.name())) {
            tableInfo.tableName = clazz.getName();
        } else {
            tableInfo.tableName = table.name();
        }

        HashMap<String, ColumnInfo> columnMaps = null;
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                // 列信息
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.fieldName = field.getName();
                columnInfo.fieldtype = field.getType().getName();

                columnInfo.columLength = column.length();

                //数据库 对应的列类型
                columnInfo.dbtype = getType(field.getType(), columnInfo.fieldtype);

                if (columnMaps == null) {
                    columnMaps = new HashMap<String, ColumnInfo>();
                }
                columnMaps.put(columnInfo.fieldName, columnInfo);
            }
        }
        tableInfo.colunmMap = columnMaps;
        return tableInfo;
    }

    public static <T> String getTableName(Class<?> clazz) {
        Table table = (Table) clazz.getAnnotation(Table.class);
        if (table == null) {
            return null;
        }
        String tableName = null;
        if (TextUtils.isEmpty(table.name())) {
            tableName = clazz.getName();
        } else {
            tableName = table.name();
        }
        return tableName;
    }

    /**
     * 通过field的type类型名字，得到存到数据库里对应的数据类型
     *
     * @param typeName
     * @return
     */
    private static DataType getType(Class<?> clazz, String typeName) {
        if (clazz.isEnum()) {
            return DataType.ENUM;
        } else if (long.class.getName().equals(typeName) || Long.class.getName().equals(typeName)) {
            return DataType.LONG;
        } else if (Integer.class.getName().equals(typeName) || int.class.getName().equals(typeName)) {
            return DataType.INTEGER;
        } else if (String.class.getName() == typeName) {
            return DataType.STRING;
        } else if (float.class.getName().equals(typeName) || Float.class.getName().equals(typeName)) {
            return DataType.FLOAT;
        } else if (boolean.class.getName().equals(typeName) || Boolean.class.getName().equals(typeName)) {
            return DataType.BOOLEAN;
        } else if (short.class.getName().equals(typeName) || Short.class.getName().equals(typeName)) {
            return DataType.SHORT;
        } else {
            return DataType.UNKOWN;
        }
    }
}

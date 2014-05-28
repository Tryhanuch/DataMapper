package sql;

import anntt.Column;
import anntt.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by tish on 11.05.2014.
 */
public class QueryBuilder {

    public static String buildInsertSQL(Object o) {
        String tableName = o.getClass().getAnnotation(Entity.class).name();
        ArrayList<String> conf = new ArrayList<>();

        for (Field field : o.getClass().getDeclaredFields()) {
            String columnName = field.getAnnotation(Column.class).name();
            conf.add(columnName);
        }

        String params = "";
        String values = "";
        for (int i = 0; i < conf.size(); i++) {
            if (i != (conf.size() - 1)) params = params + conf.get(i) + ", ";
            else params = params + conf.get(i);

            if (i != (conf.size() - 1)) values = values + "?, ";
            else values = values + "?";
        }

        String sql = "INSERT INTO " + tableName + " (" + params + ") VALUES(" + values + ")";
        return sql;
    }

    public static String buildSelectByIdSQL(Object o, long id) {
        String tableName = o.getClass().getAnnotation(Entity.class).name();
        String sql = "SELECT * FROM " + tableName + " WHERE ID = " + id;
        return sql;
    }

    public static String buildSelectAllSQL(Object o) {
        String tableName = o.getClass().getAnnotation(Entity.class).name();
        String sql = "SELECT * FROM " + tableName;
        return sql;
    }

    public static String buildUpdateSQL(Object o, long id) {
        String tableName = o.getClass().getAnnotation(Entity.class).name();
        ArrayList<String> conf = new ArrayList<>();

        for (Field field : o.getClass().getDeclaredFields()) {
            String columnName = field.getAnnotation(Column.class).name();
            conf.add(columnName);
        }

        String params = "";
        for (int i = 0; i < conf.size(); i++) {
            if (i != (conf.size() - 1)) params = params + conf.get(i) + "=?, ";
            else params = params + conf.get(i) + "=?";
        }

        String sql = "UPDATE " + tableName + " SET " + params + " WHERE ID = " + id;
        return sql;
    }

    public static String buildDeleteSQL(Object o, long id) {
        String tableName = o.getClass().getAnnotation(Entity.class).name();
        String sql = "DELETE FROM " + tableName + " WHERE ID = " + id;
        return sql;
    }

}

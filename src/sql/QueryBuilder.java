package sql;

import anntt.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by tish on 11.05.2014.
 */
public class QueryBuilder {
    public static final String CONF_EXT = ".conf";

    public static String buildInsertSQL(Object o){
        String tableName = o.getClass().getAnnotation(Entity.class).name();
        String confFileName = tableName + CONF_EXT;

        List<String> conf = loadConfigFile(confFileName);

        String params = "";
        String values = "";
        for (int i = 0; i < conf.size(); i++) {
            if (i != (conf.size() - 1))params = params + conf.get(i) + ", ";
            else params = params + conf.get(i);

            if (i != (conf.size() - 1))values = values + "?, ";
            else values = values + "?";
        }

        String sql = "INSERT INTO " + tableName + " (" + params + ") VALUES(" + values +")";
        return sql;
    }

    public static String buildSelectByIdSQL(Object o, long id){
        String tableName = o.getClass().getAnnotation(Entity.class).name();
        String sql = "SELECT * FROM " + tableName + " WHERE ID = " + id;
        return sql;
    }

    public static String buildSelectAllSQL(Object o){
        String tableName = o.getClass().getAnnotation(Entity.class).name();
        String sql = "SELECT * FROM " + tableName;
        return sql;
    }

    public static String buildUpdateSQL(Object o, long id){
        String tableName = o.getClass().getAnnotation(Entity.class).name();
        String confFileName = tableName + CONF_EXT;

        List<String> conf = loadConfigFile(confFileName);

        String params = "";
        for (int i = 0; i < conf.size(); i++) {
            if (!conf.get(i).equals("ID")){
                if (i != (conf.size() - 1))params = params + conf.get(i) + "=?, ";
                else params = params + conf.get(i) + "=?";
            }
        }

        String sql = "UPDATE " + tableName + " SET " + params + " WHERE ID = " + id;
        return sql;
    }

    public static String buildDeleteSQL(Object o, long id){
        String tableName = o.getClass().getAnnotation(Entity.class).name();
        String sql = "DELETE FROM " + tableName + " WHERE ID = " + id;
        return sql;
    }

    private static List<String> loadConfigFile(String fName) {
        List<String> result = new ArrayList<>();
        File confFile = new File(getPath() + fName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(confFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String s;
            assert br != null;
            while ((s = br.readLine()) != null) {
                String[] st = s.split(":");
                result.add(st[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String getPath() {
        String path = null;
        Properties property = new Properties();

        try {
            property.load(new FileInputStream("config.properties"));
            path = property.getProperty("dir.path");
        } catch (IOException e) {
            System.err.println("ERROR! Property file is not found!");
        }

        return path;
    }

}

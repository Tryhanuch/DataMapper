package mpp;

import sql.QueryBuilder;

import java.io.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by tish on 11.05.2014.
 */
public class DataMapper implements IDataMapper {
    private static Connection connection;

    //Constructor!
    public DataMapper() {

    }

    //Connection setting!
    static {
        try {
            Properties property = new Properties();
            property.load(new FileInputStream("config.properties"));
            String driver = property.getProperty("db.driver");
            String con = property.getProperty("db.url");
            String username = property.getProperty("db.username");
            String password = property.getProperty("db.password");
            Class.forName(driver);
            connection = DriverManager.getConnection(con, username, password);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    //Basic interface methods!
    public void save(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String sql = QueryBuilder.buildInsertSQL(o);

        // PreparedStatement method!
        addToTable(o, fields, sql);
    }

    public Object load(long id, Class clazz) {

        try {
            Object result = clazz.newInstance();
            String sql = QueryBuilder.buildSelectByIdSQL(result, id);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            List<String[]> line = parseLine(resultSet, result);

            for (int i = 0; i < line.size(); i++) {
                String[] strFields = line.get(i);
                result = getObject(strFields, result);
            }

            return result;

        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Object> loadAll(Class clazz) {
        List<Object> result = new ArrayList<>();

        try {
            Object o = clazz.newInstance();
            String sql = QueryBuilder.buildSelectAllSQL(o);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            List<String[]> line = parseLine(resultSet, o);

            for (int i = 0; i < line.size(); i++) {
                Object object = clazz.newInstance();
                String[] strFields = line.get(i);
                object = getObject(strFields, object);
                result.add(object);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void update(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        int id = 0;

        try {
            Field f = o.getClass().getDeclaredField("id");
            f.setAccessible(true);
            id = Integer.parseInt(String.valueOf(f.get(o)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        String sql = QueryBuilder.buildUpdateSQL(o, id);

        // PreparedStatement method!
        addToTable(o, fields, sql);
    }

    public void delete(long id, Class clazz) {
        try {
            String sql = QueryBuilder.buildDeleteSQL(clazz.newInstance(), id);
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Other compatible methods!
    private void addToTable(Object o, Field[] fields, String sql) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            int count = 1;
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (fields[i].getType().equals(String.class)) {
                    ps.setString(count, String.valueOf(fields[i].get(o)));
                } else if (fields[i].getType().equals(int.class)) {
                    ps.setInt(count, Integer.parseInt(String.valueOf(fields[i].get(o))));
                } else if (fields[i].getType().equals(long.class)) {
                    ps.setLong(count, Long.parseLong(String.valueOf(fields[i].get(o))));
                } else if (fields[i].getType().equals(byte.class)) {
                    ps.setByte(count, Byte.parseByte(String.valueOf(fields[i].get(o))));
                } else if (fields[i].getType().equals(double.class)) {
                    ps.setDouble(count, Double.parseDouble(String.valueOf(fields[i].get(o))));
                } else if (fields[i].getType().equals(float.class)) {
                    ps.setFloat(count, Float.parseFloat(String.valueOf(fields[i].get(o))));
                } else if (fields[i].getType().equals(short.class)) {
                    ps.setShort(count, Short.parseShort(String.valueOf(fields[i].get(o))));
                } else if (fields[i].getType().equals(boolean.class)) {
                    ps.setBoolean(count, Boolean.parseBoolean(String.valueOf(fields[i].get(o))));
                }
                count++;
            }
            ps.executeUpdate();

        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private List<String[]> parseLine(ResultSet rs, Object o) {
        List<String[]> result = new ArrayList<>();
        Field[] f = o.getClass().getDeclaredFields();
        int size = f.length;

        try {
            while (rs.next()) {
                String[] line = new String[size];
                for (int i = 0; i < line.length; i++) {
                    line[i] = rs.getString(i + 1);
                }

                result.add(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private Object getObject(String[] strFields, Object o) throws IllegalAccessException {
        Field[] f = o.getClass().getDeclaredFields();

        for (int i = 0; i < f.length; i++) {
            f[i].setAccessible(true);
            if (f[i].getType().equals(String.class)) {
            f[i].set(o, strFields[i]);
            } else if (f[i].getType().equals(int.class)) {
            f[i].set(o, Integer.parseInt(strFields[i]));
            } else if (f[i].getType().equals(long.class)) {
            f[i].set(o, Long.parseLong(strFields[i]));
            } else if (f[i].getType().equals(byte.class)) {
            f[i].set(o, Byte.parseByte(strFields[i]));
            } else if (f[i].getType().equals(double.class)) {
            f[i].set(o, Double.parseDouble(strFields[i]));
            } else if (f[i].getType().equals(float.class)) {
            f[i].set(o, Float.parseFloat(strFields[i]));
            } else if (f[i].getType().equals(short.class)) {
            f[i].set(o, Short.parseShort(strFields[i]));
            } else if (f[i].getType().equals(boolean.class)) {
            f[i].set(o, Boolean.parseBoolean(strFields[i]));
            }
        }

        return o;
    }

}

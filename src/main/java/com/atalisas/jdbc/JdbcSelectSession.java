package com.atalisas.jdbc;

import com.atalisas.crud.SelectSession;
import com.atalisas.util.StringsUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class JdbcSelectSession extends JdbcCommonSession<SelectSession> implements SelectSession {

    private LinkedHashMap<String, String> selectsMap = new LinkedHashMap<>();

    private StringBuilder fromState = new StringBuilder(" from ");
    private boolean hasOrderState = false;
    private StringBuilder orderState = new StringBuilder(" order by ");
    private boolean hasGroupState = false;
    private StringBuilder groupState = new StringBuilder(" group by ");

    private Connection conn;

    public JdbcSelectSession(Connection connection){
        this.conn = connection;
    }

    @Override
    public <T> T buildObject(Class cls) {
        String sql = parseSQL();
        List<T> list = makeList(sql, cls);
        return list.get(0);
    }

    @Override
    public <T> List<T> buildList(Class cls) {
        String sql = parseSQL();
        return makeList(sql, cls);
    }

    private <T> List<T> makeList(String sql, Class clz) {
        List<T> list = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Object instance = clz.newInstance();
                int index = 1;
                for (Map.Entry<String, String> m : selectsMap.entrySet()) {
                    //TODO: through reflect
                    Field f = clz.getDeclaredField(m.getValue());
                    Class methodPara = f.getType();
                    Method setter = clz.getMethod("set" + StringsUtil.captureName(m.getValue()), methodPara);
                    setter.invoke(instance, //OMG.老子的脑细胞呀，希望下次看到可以记得
                            dealSetterMethod(methodPara, resultSet, index));
                    index++;
                }
                list.add((T)instance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
                statement.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("what?");
                e.printStackTrace();
            }finally {
                resultSet = null;
                statement = null;
                conn = null;
            }
        }
        return list;
    }

    private Object dealSetterMethod(Class clz, ResultSet resultSet, int columnIndex) {
        Object result = null;
        try {
            switch (clz.getSimpleName()) {
                case "Integer":
                    result = resultSet.getInt(columnIndex);
                    break;
                case "Long":
                    result = resultSet.getLong(columnIndex);
                    break;
                case "String":
                    result = resultSet.getString(columnIndex);
                    break;
                case "Short":
                    result = resultSet.getShort(columnIndex);
                    break;
                case "Boolean":
                    result = resultSet.getBoolean(columnIndex);
                    break;
                case "Float":
                    result = resultSet.getFloat(columnIndex);
                    break;
                case "Double":
                    result = resultSet.getDouble(columnIndex);
                    break;
                case "Time":
                    result = resultSet.getTime(columnIndex);
                    break;
                case "Date":
                    result = resultSet.getDate(columnIndex);
                    break;
                case "Timestamp":
                    result = resultSet.getTimestamp(columnIndex);
                    break;
                default:
                    throw new NoSuchElementException("The Type is invalid!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public SelectSession groupBy(String column) {
        hasGroupState = true;
        groupState.append(column);
        return this;
    }

    @Override
    public SelectSession having(String state) {
        groupState.append(" having ");
        groupState.append(state);
        return this;
    }

    @Override
    public SelectSession from(String table) {
        this.fromState.append(table);
        return this;
    }

    @Override
    public ToState select(String column) {
        return new JdbcSelectState(this)
                .select(column);
    }

    @Override
    public SelectSession orderBy(String state){
        return orderBy(state, true);
    }

    @Override
    public SelectSession orderBy(String state, boolean isASC) {
        hasOrderState = true;
        orderState.append(state);
        if (isASC){
            orderState.append(" ASC");
        }
        else {
            orderState.append(" DESC");
        }
        return this;
    }

    @Override
    protected String parseSQL() {
        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        for (String s : selectsMap.keySet()) {
            builder.append(s);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);

        builder.append(fromState);
        if (hasWhereState){
            builder.append(whereState);
        }
        if (hasGroupState){
            builder.append(groupState);
        }
        if (hasOrderState){
            builder.append(orderState);
        }
        return builder.toString();
    }

    public static class JdbcSelectState implements SelectState{
        private JdbcSelectSession session;

        public JdbcSelectState(JdbcSelectSession sess) {
            this.session = sess;
        }

        public ToState select(String column) {
            return new JdbcToState(session, column);
        }
    }

    public static class JdbcToState implements ToState {
        private JdbcSelectSession session;
        private String column;

        public JdbcToState(JdbcSelectSession sess, String column) {
            if (sess == null) {
                throw new IllegalArgumentException("SelectSession object can not be null");
            }
            if (column == null || column.equals("")) {
                throw new IllegalArgumentException("column can not be empty or null");
            }
            this.session = sess;
            this.column = column;
        }

        public SelectSession to(String field) {
            session.selectsMap.put(column, field);
            return session;
        }

        //use same column with field
        public SelectSession to() {
            session.selectsMap.put(column, column);
            return session;
        }
    }

}

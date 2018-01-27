package com.atalisas.jdbc;

import com.atalisas.crud.InsertSession;
import com.atalisas.util.StringsUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class JdbcInsertSession implements InsertSession {

    private Connection conn;
    private LinkedHashMap<String, String> insertsMap = new LinkedHashMap<>();
    private List objList = new ArrayList();
    private StringBuilder insertState = new StringBuilder();

    public JdbcInsertSession(Connection conn) {
        this.conn = conn;
    }

    @Override
    public PropState column(String column) {
        return new JdbcPropState(this, column);
    }

    @Override
    public InsertSession object(Object obj) {
        objList.add(obj);
        return this;
    }

    @Override
    public InsertSession list(List list) {
        objList.addAll(list);
        return this;
    }

    @Override
    public int execute() {
        String sql = parseSQL();
        Statement statement = null;
        int result = 0;
        try {
            statement = conn.createStatement();
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                statement = null;
                conn = null;
            }
        }
        return result;
    }

    @Override
    public IntoState insert() {
        return new JdbcIntoState(this);
    }

    private String parseSQL() {
        for (String col : insertsMap.keySet()){
            insertState.append(col);
            insertState.append(",");
        }
        insertState.deleteCharAt(insertState.length()-1);
        insertState.append(")");
        insertState.append(" values ");
        for (Object o : objList){
            String value = parseObject(o);
            insertState.append(value);
            insertState.append(",");
        }
        insertState.deleteCharAt(insertState.length()-1);
        return insertState.toString();
    }

    private String parseObject(Object obj){
        Class cls = obj.getClass();
        StringBuilder builder = new StringBuilder("(");
        for (String f : insertsMap.values()){
            try {
                String getter = "get"+ StringsUtil.captureName(f);
                Method getterMethod = cls.getMethod(getter);
                Object results = getterMethod.invoke(obj);
                builder.append(results);
                builder.append(",");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append(")");
        return builder.toString();
    }

    public static class JdbcPropState implements PropState{
        private JdbcInsertSession session;
        private String column;
        public JdbcPropState(JdbcInsertSession session, String column) {
            this.session = session;
            this.column = column;
        }

        //TODO: 主键生成
        //public InsertSession.PropState primaryKey(){
        //    return this;
        //}

        public InsertSession prop(String property){
            session.insertsMap.put(column, property);
            return session;
        }
    }

    public static class JdbcIntoState implements IntoState{
        private JdbcInsertSession session;
        public  JdbcIntoState(JdbcInsertSession session){
            this.session = session;
        }

        public InsertSession into(String table){
            session.insertState.append("insert into ");
            session.insertState.append(table);
            session.insertState.append(" (");
            return session;
        }
    }
}

package com.atalisas.jdbc;

import com.atalisas.crud.UpdateSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class JdbcUpdateSession extends JdbcCommonSession<UpdateSession>
        implements UpdateSession {

    private Connection conn;
    private StringBuilder updateState = new StringBuilder("update ");

    public JdbcUpdateSession(Connection connection) {
        this.conn = connection;
    }


    @Override
    public UpdateSession update(String table) {
        updateState.append(table);
        updateState.append(" set ");
        return this;
    }

    @Override
    public EqualState set(String column) {
        return new JbdcEqualState(this, column);
    }

    @Override
    public int execute() {
        String sql = parseSQL();
        Statement statement = null;
        int i = -1;
        try {
            statement = conn.createStatement();
            i = statement.executeUpdate(sql);
        }catch (SQLException e) {
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
        return i;
    }

    @Override
    protected String parseSQL() {
        updateState.deleteCharAt(updateState.length() - 1);
        if (hasWhereState){
            updateState.append(whereState);
        }
        return updateState.toString();
    }

    public static class JbdcEqualState implements EqualState{
        private JdbcUpdateSession session;
        private String column;
        public JbdcEqualState(JdbcUpdateSession session, String column) {
            this.session = session;
            this.column = column;
        }
        public <V> JdbcUpdateSession eq(V value){
            session.updateState.append(column);
            session.updateState.append("=");
            session.updateState.append(value);
            session.updateState.append(",");
            return session;
        }
    }
}



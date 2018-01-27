package com.atalisas.jdbc;

import com.atalisas.crud.DeleteSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class JdbcDeleteSession extends JdbcCommonSession<DeleteSession> implements DeleteSession {

    Connection conn;
    public JdbcDeleteSession(Connection conn) {
        this.conn = conn;
    }

    private StringBuilder deleteState = new StringBuilder();

    @Override
    public int execute() {
        Statement statement = null;
        String sql = parseSQL();
        int result = -1;
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
    public DeleteSession from(String table) {
        return null;
    }

    @Override
    public DeleteState delete() {
        deleteState.append("delete from ");
        return new JdbcDeleteState(this);
    }

    @Override
    protected String parseSQL() {
        if (hasWhereState){
            deleteState.append(whereState);
        }
        return deleteState.toString();
    }

    public static class JdbcDeleteState implements DeleteState{
        JdbcDeleteSession session;
        public JdbcDeleteState(JdbcDeleteSession session) {
            this.session = session;
        }

        public DeleteSession from(String table){
            session.deleteState.append(table);
            return session;
        }
    }
}

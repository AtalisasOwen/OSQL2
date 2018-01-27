package com.atalisas.jdbc;

import com.atalisas.crud.*;
import com.atalisas.util.functional.ConnectionFunction;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class JdbcOSQL extends OSQL {

    public JdbcOSQL(DataSource dataSource) {
        super(dataSource);
    }

    public JdbcOSQL(ConnectionFunction generateFunc) {
        super(generateFunc);
    }

    @Override
    public SelectSession startSelect() {
        Connection conn = getStrategyConnection();
        return new JdbcSelectSession(conn);
    }

    @Override
    public DeleteSession startDelete() {
        Connection conn = getStrategyConnection();
        return new JdbcDeleteSession(conn);
    }

    @Override
    public InsertSession startInsert() {
        Connection conn = getStrategyConnection();
        return new JdbcInsertSession(conn);
    }

    @Override
    public UpdateSession startUpdate() {
        Connection conn = getStrategyConnection();
        return new JdbcUpdateSession(conn);
    }
}

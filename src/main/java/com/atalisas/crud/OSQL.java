package com.atalisas.crud;

import com.atalisas.util.functional.ConnectionFunction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public abstract class OSQL {
    protected DataSource dataSource = null;
    protected ConnectionFunction func = null;
    public OSQL(DataSource dataSource){
        this.dataSource = dataSource;
    }
    public OSQL(ConnectionFunction generateFunc){
        func = generateFunc;
    }

    protected Connection getStrategyConnection(){
        Connection connection = null;
        if (dataSource != null){
            try {
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (func != null){
            connection = func.getConnection();
        }else{
            throw new IllegalArgumentException("can not get any connection");
        }
        return connection;
    }

    public abstract SelectSession startSelect();
    public abstract DeleteSession startDelete();
    public abstract InsertSession startInsert();
    public abstract UpdateSession startUpdate();
}

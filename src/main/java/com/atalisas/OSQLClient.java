package com.atalisas;

import com.atalisas.crud.OSQL;
import com.atalisas.jdbc.JdbcOSQL;
import com.atalisas.util.functional.ConnectionFunction;

import javax.sql.DataSource;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class OSQLClient {

    public static OSQL jdbc(DataSource dataSource){
        return new JdbcOSQL(dataSource);
    }

    public static OSQL jdbc(ConnectionFunction func){
        return new JdbcOSQL(func);
    }



}

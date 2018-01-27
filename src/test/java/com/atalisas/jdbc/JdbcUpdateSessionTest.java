package com.atalisas.jdbc;

import org.junit.Test;
import com.atalisas.util.ConnectionUtil;

import java.sql.Connection;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class JdbcUpdateSessionTest {
    @Test
    public void testUpdateSession(){
        Connection connection = ConnectionUtil.getConnection();
        int results = new JdbcUpdateSession(connection)
                .update("temps")
                .set("str").eq(12)
                .set("hi_temp").eq(12)
                .where("id = 1")
                .execute();
        System.out.println(results);
    }

    @Test
    public void testUpdateAnother(){
        Connection cn = ConnectionUtil.getConnection();
        int results = new JdbcUpdateSession(cn)
                .update("temp")
                .set("str").eq(12)
                .where("id = 1")
                .execute();
    }

}

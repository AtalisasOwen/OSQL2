package com.atalisas.jdbc;

import org.junit.Test;
import com.atalisas.util.ConnectionUtil;

import java.sql.Connection;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class JdbcDeleteSessionTest {
    @Test
    public void testDeleteSession(){
        Connection connection = ConnectionUtil.getConnection();
        int results = new JdbcDeleteSession(connection)
                .delete().from("temps")
                .where("id > 100")
                .execute();
        System.out.println(results);
    }
}

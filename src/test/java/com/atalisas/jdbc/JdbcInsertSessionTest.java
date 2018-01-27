package com.atalisas.jdbc;

import com.atalisas.OSQLClient;
import domain.Temp;
import org.junit.Test;
import com.atalisas.util.ConnectionUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class JdbcInsertSessionTest {
    @Test
    public void testInsertSession(){
        Temp t1 = new Temp(127, 100L);
        Temp t2 = new Temp(119, 200L);

        List<Temp> l = new ArrayList<>();
        l.add(new Temp(130, 20L));
        l.add(new Temp(140, 49L));

        Connection connection = ConnectionUtil.getConnection();
        int results = OSQLClient.jdbc(() -> connection)
                .startInsert()
                .insert().into("temps")
                .column("id").prop("id")
                .column("hi_temp").prop("hiTemp")
                .object(t2)
                .object(t1)
                .list(l)
                .execute();
        System.out.println(results);
    }
}

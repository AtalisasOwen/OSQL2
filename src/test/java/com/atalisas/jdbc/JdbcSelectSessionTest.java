package com.atalisas.jdbc;

import com.atalisas.OSQLClient;
import domain.AuthorOrders;
import domain.Temp;
import org.junit.Test;
import com.atalisas.util.ConnectionUtil;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public class JdbcSelectSessionTest {

    @Test
    public void testSelectSession(){
        Connection connection = ConnectionUtil.getConnection();
        Temp tempList = new JdbcSelectSession(connection)
                        .select("id").to("id")
                        .select("hi_temp as HI").to("hiTemp")
                        .from("temps")
                        .groupBy("HI")
                        .having("HI = 50")
                        .orderBy("HI", true)
                        .buildObject(Temp.class);

        System.out.println(tempList);
    }

    @Test
    public void testOSQLClient(){
        List<Temp> tempList = OSQLClient.jdbc(ConnectionUtil::getConnection)
                .startSelect()
                .select("id").to()
                .select("hi_temp").to("hiTemp")
                .from("temps")
                .buildList(Temp.class);
        tempList.stream().forEach(System.out::println);
    }

    @Test
    public void testAnotherSelect(){
        Connection connection = ConnectionUtil.getConnection();
        List<AuthorOrders> list = OSQLClient.jdbc(() -> connection)
                .startSelect()
                .select("a.title_id").to("titleId")
                .select("b.au_fname").to("authorName")
                .select("b.phone").to("phone")
                .select("b.address").to("address")
                .from("au_orders a inner join authors b on a.author2 = b.au_id")
                .buildList(AuthorOrders.class);
        list.stream().forEach(System.out::println);
    }

    @Test
    public void testAsyncSelect() throws ExecutionException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();
        Future<List<AuthorOrders>> list = OSQLClient.jdbc(() -> connection)
                .startSelect()
                .select("a.title_id").to("titleId")
                .select("b.au_fname").to("authorName")
                .select("b.phone").to("phone")
                .select("b.address").to("address")
                .from("au_orders a inner join authors b on a.author2 = b.au_id")
                .buildListAsync(AuthorOrders.class);

        try {
            System.out.println(list.get(2, TimeUnit.MICROSECONDS));
        } catch (TimeoutException e) {
            System.out.println("Timeout!!");
        }
    }

}

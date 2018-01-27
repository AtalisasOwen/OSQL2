# OSQL2

#### 一个简单的ORM框架，目前只是对JDBC的简单封装，单元测试写得还不够多，肯定还有bug，发现再改吧。
#### 此外，还有添加对MyBatis的封装，因为眼红它的缓存设计，不过也有可能自己重新写，待我先去看看MyBatis源码。
---
### select操作
```
List<AuthorOrders> list = OSQLClient.jdbc(ConnectionUtil::getConnection) //也支持DataSource对象
                .startSelect()
                .select("a.title_id").to("titleId")                      //select("表列名").to("对象的字段名字")
                .select("b.au_fname").to("authorName")
                .select("b.phone").to("phone")
                .select("b.address").to("address")
                .from("au_orders a inner join authors b on a.author2 = b.au_id")
                .buildList(AuthorOrders.class);

Connection connection = ConnectionUtil.getConnection();        
Temp temp = OSQLClient.jdbc(() -> connection)
                .startSelect()
                .select("id").to("id")
                .select("hi_temp as HI").to("hiTemp")
                .from("temps")
                .groupBy("HI")
                .having("HI = 50")
                .orderBy("HI", true)
                .buildObject(Temp.class);
System.out.println(temp);

//其中Domain对象如下
public class AuthorOrders {
    private String titleId;
    private String authorName;
    private String phone;
    private String address;

    //最好全都加上这个，用于反射的newInstance();
    public AuthorOrders(){};
    
    //...省略各种setter/getter
}

```

### Insert操作
```
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

public class Temp {
    private Integer id;
    private Long hiTemp;
    public Temp(){}
    public Temp(Integer id, Long hiTemp) {
        this.id = id;
        this.hiTemp = hiTemp;
    }

    @Override
    public String toString() {
        return "Temp{" +
                "id=" + id +
                ", hiTemp=" + hiTemp +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getHiTemp() {
        return hiTemp;
    }

    public void setHiTemp(Long hiTemp) {
        this.hiTemp = hiTemp;
    }
}

```

### update操作
```
Connection connection = ConnectionUtil.getConnection();
int results = new JdbcUpdateSession(connection)
            .update("temps")
            .set("str").eq(12)
            .set("hi_temp").eq(12)
            .where("id = 1")
            .execute();
System.out.println(results);
```

### delete操作
```
Connection connection = ConnectionUtil.getConnection();
int results = new JdbcDeleteSession(connection)
        .delete().from("temps")
        .where("id > 100")
        .execute();
System.out.println(results);
```

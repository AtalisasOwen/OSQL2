package com.atalisas.crud;

import com.atalisas.crud.basic.Insert;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public interface InsertSession extends Insert<Insert.IntoState>{

    PropState column(String column);
    InsertSession object(Object obj);
    InsertSession list(List list);
    int execute();
    Future<Integer> executeAsync();

}

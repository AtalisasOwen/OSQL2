package com.atalisas.crud.basic;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public interface Join<V> {
    V leftJoin(String table);
    V rightJoin(String table);
    V fullJoin(String table);
    V crossJoin(String table);
    V innerJoin(String table);
}

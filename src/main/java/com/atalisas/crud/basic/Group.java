package com.atalisas.crud.basic;

/**
 * Created by 顾文涛 on 2017/12/30.
 */
public interface Group<V> {
    V groupBy(String column);
    V having(String state);
}

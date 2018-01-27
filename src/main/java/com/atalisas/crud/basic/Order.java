package com.atalisas.crud.basic;

/**
 * Created by 顾文涛 on 2017/12/30.
 */
public interface Order<V> {
    V orderBy(String state, boolean isASC);
    V orderBy(String state);
}

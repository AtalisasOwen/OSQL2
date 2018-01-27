package com.atalisas.crud;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public interface CommonSession<V> {
    public V where(String state);
    public V and();
    public V or();
}

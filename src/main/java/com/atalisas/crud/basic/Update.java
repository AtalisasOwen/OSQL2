package com.atalisas.crud.basic;

import com.atalisas.crud.UpdateSession;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public interface Update<V> {
    V update(String table);
    interface EqualState{
        <V> UpdateSession eq(V value);
    }
}

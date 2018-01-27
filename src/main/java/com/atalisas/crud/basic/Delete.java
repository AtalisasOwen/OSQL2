package com.atalisas.crud.basic;

import com.atalisas.crud.DeleteSession;

/**
 * Created by 顾文涛 on 2017/12/30.
 */
public interface Delete<V> {
    V delete();
    interface DeleteState{
        DeleteSession from(String table);
    }
}

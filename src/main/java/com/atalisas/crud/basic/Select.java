package com.atalisas.crud.basic;

import com.atalisas.crud.SelectSession;

/**
 * Created by 顾文涛 on 2017/12/30.
 */
public interface Select<V> {
    V select(String column);

    interface ToState {
        SelectSession to(String field);
        //use same column with field
        SelectSession to();
    }

    interface SelectState {
        ToState select(String column);
    }
}

package com.atalisas.crud.basic;

import com.atalisas.crud.InsertSession;

/**
 * Created by 顾文涛 on 2017/12/30.
 */
public interface Insert<V> {
    V insert();
    interface IntoState{
        InsertSession into(String table);
    }

    interface PropState{
        /*
        //TODO: 主键生成
        public PropState primaryKey(){
            return this;
        }
        */
        InsertSession prop(String property);
    }
}

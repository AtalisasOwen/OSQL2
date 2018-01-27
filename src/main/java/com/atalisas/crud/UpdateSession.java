package com.atalisas.crud;

import com.atalisas.crud.basic.Update;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public interface UpdateSession  extends CommonSession<UpdateSession>, Update<UpdateSession> {

    EqualState set(String column);
    int execute();



}

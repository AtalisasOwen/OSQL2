package com.atalisas.crud;

import com.atalisas.crud.basic.Delete;
import com.atalisas.crud.basic.From;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public interface DeleteSession extends Delete<Delete.DeleteState>, From<DeleteSession>,CommonSession<DeleteSession> {

    DeleteState delete();
    int execute();
}

package com.atalisas.crud;

import com.atalisas.crud.basic.From;
import com.atalisas.crud.basic.Group;
import com.atalisas.crud.basic.Order;
import com.atalisas.crud.basic.Select;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public interface SelectSession extends Select<Select.ToState>, Order<SelectSession>,
        Group<SelectSession>, From<SelectSession>,CommonSession<SelectSession> {
    <T> T buildObject(Class<T> cls);
    <T> List<T> buildList(Class<T> cls);
    <T> Future<List<T>> buildListAsync(Class<T> cls);
    <T> Future<T> buildObjectAsync(Class<T> cls);

}

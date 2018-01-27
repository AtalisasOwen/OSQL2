package com.atalisas.crud;

import com.atalisas.crud.basic.From;
import com.atalisas.crud.basic.Group;
import com.atalisas.crud.basic.Order;
import com.atalisas.crud.basic.Select;

import java.util.List;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public interface SelectSession extends Select<Select.ToState>, Order<SelectSession>,
        Group<SelectSession>, From<SelectSession>,CommonSession<SelectSession> {
    <T> T buildObject(Class cls);
    <T> List<T> buildList(Class cls);
}

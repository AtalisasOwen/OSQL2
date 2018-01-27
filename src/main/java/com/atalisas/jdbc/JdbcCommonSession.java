package com.atalisas.jdbc;

import com.atalisas.crud.CommonSession;

/**
 * Created by 顾文涛 on 2018/1/27.
 */
public abstract class JdbcCommonSession<V> implements CommonSession<V> {
    protected boolean hasWhereState = false;
    protected StringBuilder whereState = new StringBuilder(" where ");

    protected abstract String parseSQL();

    @Override
    public V where(String state){
        hasWhereState = true;
        V v = (V)this;
        this.whereState.append(state);
        return v;
    }

    @Override
    public V and(){
        V v = (V)this;
        this.whereState.append(" and ");
        return v;
    }

    @Override
    public V or(){
        V v = (V)this;
        this.whereState.append(" or ");
        return v;
    }
}

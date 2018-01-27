package com.atalisas.util.functional;

import java.sql.Connection;

/**
 * Created by 顾文涛 on 2017/12/14.
 */
@FunctionalInterface
public interface ConnectionFunction {
    Connection getConnection();
}

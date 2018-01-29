package com.atalisas.util.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by 顾文涛 on 2018/1/29.
 */
public class ThreadPoolUtil {

    public static final ExecutorService executor = Executors.newCachedThreadPool();

    public static <T> Future<T> execute(Callable<T> callable){
        return executor.submit(callable);
    }

}

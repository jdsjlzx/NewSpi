package com.baidu.lib;

import java.util.concurrent.Callable;

/**
 * 此类并没有用，只是为了读取他的字节码,构建CallableDump类
 */
public class MyCallable implements Callable<Apple> {

    @Override
    public Apple call() throws Exception {
        return new Apple();
    }
}

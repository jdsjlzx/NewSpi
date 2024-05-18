package com.baidu.spi;


@ServiceProviderInterface(Banana.class)
public class Banana implements Foo {

    @Override
    public void eat() {
    }
}

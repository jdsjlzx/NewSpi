package com.baidu.spi;


@ServiceProviderInterface(Apple.class)
public class Apple implements Foo {

    @Override
    public void eat() {
    }
}

package com.baidu.lib;

import android.util.Log;

import com.baidu.spi.ServiceProviderInterface;

@ServiceProviderInterface(Apple.class)
public class Apple implements Food {
    @Override
    public void order() {
        Log.e("lzx","Apple order");
    }
}

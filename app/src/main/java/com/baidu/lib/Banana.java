package com.baidu.lib;

import android.util.Log;

import com.baidu.spi.ServiceProviderInterface;

@ServiceProviderInterface(Banana.class)
public class Banana implements Food {
    @Override
    public void order() {
        Log.e("lzx","Banana order");
    }
}

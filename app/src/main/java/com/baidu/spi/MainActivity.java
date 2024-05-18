package com.baidu.spi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.baidu.lib.Apple;
import com.baidu.lib.Banana;
import com.baidu.lib.Food;

public class MainActivity extends Activity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food food = ServiceLoader.load(Apple.class).get();
                food.order();
            }
        });
    }

}
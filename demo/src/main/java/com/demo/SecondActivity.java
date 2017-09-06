package com.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.transfar.router.link.UrlRouter;

/**
 * Created by Administrator on 2017/6/13.
 */

public class SecondActivity extends Activity {
    private TextView txt_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_app = (TextView) findViewById(R.id.txt_app);
        txt_app.setText(getIntent().getStringExtra("key1"));
        txt_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UrlRouter.from(SecondActivity.this).jumpToMain("lujing://main/main");
            }
        });
    }
}

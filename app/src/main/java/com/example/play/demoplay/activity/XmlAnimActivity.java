package com.example.play.demoplay.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.play.demoplay.R;

/**
 * Created by LJDY490 on 2017/3/7.
 */

public class XmlAnimActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_activity);
        mll = (LinearLayout) findViewById(R.id.ll);
        mll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        initAnim();
        TextView tv = new TextView(this);
        tv.setText("测试文本");
        mll.addView(tv);
    }

    private void initAnim() {
        ScaleAnimation sa =  new ScaleAnimation(0,1,0,1);
        sa.setDuration(1000);
        LayoutAnimationController lac = new LayoutAnimationController(sa,1f);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mll.setLayoutAnimation(lac);
    }
}

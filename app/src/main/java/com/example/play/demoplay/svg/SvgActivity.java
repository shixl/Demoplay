package com.example.play.demoplay.svg;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.play.demoplay.R;

/**
 * Created by LJDY490 on 2017/3/8.
 */

public class SvgActivity extends AppCompatActivity {

    private ImageView mIv;
    private ImageView mIv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        mIv = (ImageView) findViewById(R.id.iv);
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateTest();
            }
        });

        mIv_search = (ImageView) findViewById(R.id.iv_search);
        mIv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateSearch();
            }
        });
    }

    private void animateSearch() {
        Drawable drawable = mIv_search.getDrawable();

        if(drawable instanceof  Animatable){
            ((Animatable) drawable).start();
        }
    }

    private void animateTest() {
        Drawable drawable = mIv.getDrawable();

        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }

    }
}

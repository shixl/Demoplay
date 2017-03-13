package com.example.play.demoplay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.play.demoplay.R;
import com.example.play.demoplay.anim.AnimDownActivity;
import com.example.play.demoplay.openGl.OpenGlActivity;
import com.example.play.demoplay.svg.SvgActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void renderView(View view){
        Intent intent  = new Intent();
        switch (view.getId()){
            case R.id.bt_one:
                intent.setClass(this,OneActivity.class);
                break;
            case R.id.bt_two:
                intent.setClass(this,AnimatorActivity.class);
                break;
            case R.id.bt_three:
                intent.setClass(this,VectorActivity.class);
                break;
            case R.id.bt_four:
                intent.setClass(this, OpenGlActivity.class);
                break;
            case R.id.bt_five:
                intent.setClass(this,XmlAnimActivity.class);
                break;
            case R.id.bt_six:
                intent.setClass(this, SvgActivity.class);
                break;
            case R.id.bt_anim:
                intent.setClass(this, AnimDownActivity.class);
                break;
        }
        startActivity(intent);
    }
}

package com.example.play.demoplay.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.play.demoplay.R;

/**
 * Created by LJDY490 on 2017/2/24.
 */

public class VectorActivity extends Activity implements View.OnClickListener {

    private ImageView mIv;
    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        mIv = (ImageView) findViewById(R.id.anim_path);
        mIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.anim_path:
                startAnim(mIv);
                break;
        }
    }

    /**
     * 启动动画
     *
     * @param imageView
     */
    private void startAnim(ImageView imageView) {
        drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}

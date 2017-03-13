package com.example.play.demoplay.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.play.demoplay.R;

/**
 * Created by LJDY490 on 2017/2/24.
 */

public class AnimatorActivity extends Activity {

    private ImageView mIv;
    private TextView mTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        mIv = (ImageView) findViewById(R.id.iv);
        mTv = (TextView) findViewById(R.id.tv);
        //ObjectAnimator.ofFloat(mIv,"translationX",0F,200F).setDuration(1000).start();

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startAnimator();
                //startCodeAnimator();
                //timeLoader();
                proAnim();
            }
        });
    }

    /**
     * 读取xml
     */
    private void startAnimator(){
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim);
        animator.setTarget(mIv);
        animator.start();
    }

    /**
     * 代码
     */
    private void startCodeAnimator(){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mIv, "translationX", 0F, 200F).setDuration(1000);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mIv, "translationY", 0F, 200F).setDuration(1000);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mIv, "rotation", 0F, 360F).setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        //同时进行
        //animatorSet.playTogether(animator1,animator2);
        //依次进行
        //animatorSet.playSequentially(animator1,animator2);
        animatorSet.play(animator1).with(animator2);
        animatorSet.play(animator3).after(animator1);
        animatorSet.start();
    }

    /**
     * ValueAnimator的使用
     * ValueAnimator不会作用于任何一个属性，简单来说，它就是“数值发生器”，实际上在属性动画中，产生每一步的具体动画实现效果都是通过ValueAnimator计算出来的。
     * ObjectAnimator是继承自ValueAnimator的，ValueAnimator并没有ObjectAnimator使用的广泛。
        ValueAnimator通过动画已经继续的时间和总时间的比值产生一个0～1点时间因子，有了这样的时间因子，经过相应的变换，就可以根据startValue和endValue来生成中间相应的值。
     */
    private void timeLoader(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer animatedValue = (Integer) animation.getAnimatedValue();
                mTv.setText("" + animatedValue);
            }
        });

        valueAnimator.start();
    }

    /**
     *PropertyValuesHolder练习
     */
    private void proAnim(){
        PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX", 300f);
        PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(mIv,pvh1,pvh2,pvh3).setDuration(3000).start();
    }
}

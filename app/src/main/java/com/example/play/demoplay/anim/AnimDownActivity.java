package com.example.play.demoplay.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.play.demoplay.R;

/**
 * Created by LJDY490 on 2017/3/9.
 */

public class AnimDownActivity extends AppCompatActivity {

    private LinearLayout mHiddenView;
    private float mDensity;
    private int mHiddenViewMeasureHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_down);
        mHiddenView = (LinearLayout) findViewById(R.id.hidden_view);
        mDensity = getResources().getDisplayMetrics().density;

        mHiddenViewMeasureHeight = (int) (mDensity * 40 + 0.5);
    }

    public void onClick(View view){
        if(mHiddenView.getVisibility() == View.GONE){
            //打开动画
            animateOpen(mHiddenView);
        }else {
            //关闭动画
            animateClose(mHiddenView);
        }
    }

    private void animateClose(final View view) {
        int height = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, height, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });

        animator.start();
    }

    private void animateOpen(View view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view, 0, mHiddenViewMeasureHeight);
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View view, int start , int end){
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = value;
                view.setLayoutParams(params);
            }
        });
        return animator;
    }
}

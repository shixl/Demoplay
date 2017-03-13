package com.example.sxl.puzzle.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.sxl.puzzle.R;

/**
 * Created by LJDY490 on 2017/3/10.
 */

public class PuzzleActivity extends AppCompatActivity {

    // 设置为N*N显示
    public static int TYPE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_details_pic);

    }
}

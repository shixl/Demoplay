package com.example.sxl.puzzle.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.sxl.puzzle.R;
import com.example.sxl.puzzle.utils.ImagesUtils;
import com.example.sxl.puzzle.utils.ScreenUtils;

/**
 * Created by LJDY490 on 2017/3/10.
 */

public class PuzzleActivity extends AppCompatActivity {
    //拼图完成时显示最后一张图片
    public static Bitmap mLastBitMap;
    // 设置为N*N显示
    public static int TYPE = 2;
    private String mPicPath;
    private int mResId;
    //选择的图片
    private Bitmap mPicSelected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_details_pic);
        //获取选择的图片
        Bitmap picSelectedTemp;
        // 选择默认图片还是自定义图片
        mResId = getIntent().getExtras().getInt("picSelectedID");
        mPicPath = getIntent().getExtras().getString("picPath");
        if(mResId != 0){
            picSelectedTemp = BitmapFactory.decodeResource(getResources(), mResId);
        }else {
            picSelectedTemp = BitmapFactory.decodeFile(mPicPath);
        }
        TYPE = getIntent().getExtras().getInt("type");
        //对图片的处理
        handlerImages(picSelectedTemp);
        //初始化views
        initViews();
        //生成游戏数据
        generateGame();

    }

    private void initViews() {

    }

    private void generateGame() {
    }

    /**
     * 对图片处理  自适应大小
     * @param picSelectedTemp
     */
    private void handlerImages(Bitmap picSelectedTemp) {
        //将图片放大到固定尺寸
        int screenWidth = ScreenUtils.getScreenSize(this).widthPixels;
        int screenHeight = ScreenUtils.getScreenSize(this).heightPixels;
        mPicSelected = new ImagesUtils().resizeBitMap(screenWidth*0.8f,screenHeight*0.6f,picSelectedTemp);
    }
}

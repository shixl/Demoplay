package com.example.sxl.puzzle.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sxl.puzzle.R;
import com.example.sxl.puzzle.adapter.GridItemAdapter;
import com.example.sxl.puzzle.bean.ItemBean;
import com.example.sxl.puzzle.utils.GameUtils;
import com.example.sxl.puzzle.utils.ImagesUtils;
import com.example.sxl.puzzle.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


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
    private Button mBtnBack;
    private Button mBtnImg;
    private Button mBtnRestart;
    // 切图后的图片
    private List<Bitmap> mBitmapItemLists = new ArrayList<Bitmap>();
    // Flag 是否已显示原图
    private boolean mIsShowImg;
    private GridView mGvPuzzle;
    private TextView mTvPuzzleMainCounts;

    // 步数显示
    public static int COUNT_INDEX = 0;
    // 计时显示
    public static int TIMER_INDEX = 0;
    private TextView mTvPuzzleMainTimes;
    private ImageView mImageView;
    private GridItemAdapter mAdapter;
    // 计时器类
    private Timer mTimer;
    /**
     * 计时器线程
     */
    private TimerTask mTimerTask;

    //更新ui
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //更新设计器
                    TIMER_INDEX++;
                    mTvPuzzleMainTimes.setText("" +TIMER_INDEX);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        TYPE = getIntent().getExtras().getInt("mType",2);
        //对图片的处理
        handlerImages(picSelectedTemp);
        //初始化views
        initViews();
        //生成游戏数据
        generateGame();

    }

    /**
     * 初始化参数
     */
    private void initViews() {
        mBtnBack = (Button) findViewById(R.id.btn_puzzle_main_back);
        mBtnImg = (Button) findViewById(R.id.btn_puzzle_main_img);
        mBtnRestart = (Button) findViewById(R.id.btn_puzzle_main_restart);
        //flag 是否已经显示原图
        mIsShowImg = false;
        mGvPuzzle = (GridView) findViewById(R.id.gv_puzzle_main_detail);
        //设置gv的行列数
        mGvPuzzle.setNumColumns(TYPE);
        RelativeLayout.LayoutParams gridParams = new RelativeLayout.LayoutParams(mPicSelected.getWidth(), mPicSelected.getHeight());
        gridParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        gridParams.addRule(RelativeLayout.BELOW,R.id.ll_puzzle_main_spinner);
        mGvPuzzle.setLayoutParams(gridParams);
        mGvPuzzle.setHorizontalSpacing(0);
        mGvPuzzle.setVerticalSpacing(0);
        //tv步数
        mTvPuzzleMainCounts = (TextView) findViewById(R.id.tv_puzzle_main_counts);
        mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
        //tv计时
        mTvPuzzleMainTimes = (TextView) findViewById(R.id.tv_puzzle_main_time);
        mTvPuzzleMainTimes.setText("0秒");
        //显示原图
        addImages();
    }

    /**
     * 添加显示原图的view
     */
    private void addImages() {
        RelativeLayout rlPuzzleMain = (RelativeLayout) findViewById(R.id.rl_puzzle_main_layout);
        mImageView = new ImageView(PuzzleActivity.this);
        mImageView.setImageBitmap(mPicSelected);
        int x = (int) (mPicSelected.getWidth() * 0.9F);
        int y = (int) (mPicSelected.getHeight() * 0.9F);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(x, y);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mImageView.setLayoutParams(params);
        rlPuzzleMain.addView(mImageView);
        mImageView.setVisibility(View.GONE);
    }

    /**
     * 生成游戏数据
     */
    private void generateGame() {
        new ImagesUtils().createInitBitmaps(TYPE,mPicSelected,PuzzleActivity.this);
        GameUtils.getPuzzleGenerator();
        for(ItemBean temp : GameUtils.mItemBeans){
            mBitmapItemLists.add(temp.getBitmap());
        }
        mAdapter = new GridItemAdapter(this, mBitmapItemLists);
        mGvPuzzle.setAdapter(mAdapter);
        //启用计时器
        mTimer = new Timer(true);
        //计时器线程
        mTimerTask = new TimerTask(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        };
        //每延迟1000ms 执行0s
        mTimer.schedule(mTimerTask,0,1000);
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

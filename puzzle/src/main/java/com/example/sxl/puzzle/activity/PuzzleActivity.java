package com.example.sxl.puzzle.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sxl.puzzle.R;
import com.example.sxl.puzzle.adapter.GridItemAdapter;
import com.example.sxl.puzzle.bean.ItemBean;
import com.example.sxl.puzzle.utils.GameUtil;
import com.example.sxl.puzzle.utils.ImagesUtil;
import com.example.sxl.puzzle.utils.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by LJDY490 on 2017/3/10.
 */

public class PuzzleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
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
        setContentView(R.layout.xpuzzle_puzzle_detail_main);
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
        mGvPuzzle.setOnItemClickListener(this);

        // 返回按钮点击事件
        mBtnBack.setOnClickListener(this);
        // 显示原图按钮点击事件
        mBtnRestart.setOnClickListener(this);
        // 重置按钮点击事件
        mBtnRestart.setOnClickListener(this);
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
        RelativeLayout rlPuzzleMain = (RelativeLayout) findViewById(R.id.rl_puzzle_main_main_layout);
        mImageView = new ImageView(PuzzleActivity.this);
        mImageView.setImageBitmap(mPicSelected);
        int x = (int) (mPicSelected.getWidth() * 0.9F);
        int y = (int) (mPicSelected.getHeight() * 0.9F);
        LayoutParams params = new LayoutParams(x, y);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        //params.addRule(RelativeLayout.BELOW,R.id.ll_puzzle_layout);
        mImageView.setLayoutParams(params);
        rlPuzzleMain.addView(mImageView);
        mImageView.setVisibility(View.GONE);
    }

    /**
     * 生成游戏数据
     */
    private void generateGame() {
        new ImagesUtil().createInitBitmaps(TYPE,mPicSelected,PuzzleActivity.this);
        GameUtil.getPuzzleGenerator();
        for(ItemBean temp : GameUtil.mItemBeans){
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
        mPicSelected = new ImagesUtil().resizeBitmap(screenWidth*0.8f,screenHeight*0.6f,picSelectedTemp);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //判断是否可以移动
        if(GameUtil.isMoveable(i)){
            //点击交互空格与item的位置
            GameUtil.swapItems(GameUtil.mItemBeans.get(i),GameUtil.mBlankItemBean);
            //重新获取数据
            recreateData();
            //通知gv刷新数据
            mAdapter.notifyDataSetChanged();
            //更新步数
            COUNT_INDEX++;
            mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
            //判断是否成功
            if(GameUtil.isSuccess()){
                // 将最后一张图显示完整
                recreateData();
                mBitmapItemLists.remove(TYPE*TYPE -1);
                mBitmapItemLists.add(mLastBitMap);
                // 通知GridView更改UI
                mAdapter.notifyDataSetChanged();
                Toast.makeText(PuzzleActivity.this, "拼图成功!", Toast.LENGTH_LONG).show();
                mGvPuzzle.setEnabled(false);
                mTimer.cancel();
                mTimerTask.cancel();
            }
        }
    }

    /**
     * 重新获取图片
     */
    private void recreateData() {
        mBitmapItemLists.clear();
        for(ItemBean bean: GameUtil.mItemBeans){
            mBitmapItemLists.add(bean.getBitmap());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //返回按钮
            case R.id.btn_puzzle_main_back:
                PuzzleActivity.this.finish();
                break;
            //显示原图按钮
            case R.id.btn_puzzle_main_img:
                Animation animShow = AnimationUtils.loadAnimation(this, R.anim.image_show_anim);
                Animation animHidden = AnimationUtils.loadAnimation(this, R.anim.image_hide_anim);
                if(mIsShowImg){
                    mImageView.setAnimation(animHidden);
                    mImageView.setVisibility(View.GONE);
                    mIsShowImg = false;
                }else {
                    mImageView.setAnimation(animShow);
                    mImageView.setVisibility(View.VISIBLE);
                    mIsShowImg = true;
                }
                break;
            //重置
            case R.id.btn_puzzle_main_restart:
                clearConfig();
                generateGame();
                recreateData();
                // 通知GridView更改UI
                mTvPuzzleMainCounts.setText("" + COUNT_INDEX);
                mAdapter.notifyDataSetChanged();
                mGvPuzzle.setEnabled(true);
                break;
        }
    }

    /**
     * 清空配置参数
     */
    private void clearConfig(){
        GameUtil.mItemBeans.clear();
        // 停止计时器
        mTimer.cancel();
        mTimerTask.cancel();
        COUNT_INDEX = 0;
        TIMER_INDEX = 0;
        // 清除拍摄的照片
        if (mPicPath != null) {
            // 删除照片
            File file = new File(MainActivity.TEMP_IMAGE_PATH);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 返回时调用
     */
    @Override
    protected void onStop() {
        super.onStop();
        clearConfig();
        this.finish();
    }
}

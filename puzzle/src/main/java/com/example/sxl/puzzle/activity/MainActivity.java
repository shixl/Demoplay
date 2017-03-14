package com.example.sxl.puzzle.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sxl.puzzle.R;
import com.example.sxl.puzzle.adapter.PicAdapter;
import com.example.sxl.puzzle.utils.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    //返回码:系统相册
    private static final int RESULT_IMAGE = 100;
    //返回码:相机
    private static final int RESULT_CAMERA = 200;
    // IMAGE TYPE
    private static final String IMAGE_TYPE = "image/*";
    // Temp照片路径
    public static String TEMP_IMAGE_PATH;

    private TextView mTvPoPup;
    private PopupWindow mPopupWindow;
    private GridView mGvPic;
    private int[] mResPid;
    private List<Bitmap> mPicList;
    private PicAdapter mAdapter;
    //难度选择N*N
    private int mType = 2;
    // 本地图册、相机选择
    private String[] mCustomItems =new String[]{"本地图册","相机拍照"};
    private LayoutInflater mLayoutInflater;
    private View mPopupView;
    private TextView mTvType2;
    private TextView mTvType3;
    private TextView mTvType4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TEMP_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath()+"/temp.png";
        mTvPoPup = (TextView) findViewById(R.id.tv_puzzle_main_type_selected);
        mPicList = new ArrayList<>();
        initData();
        mAdapter = new PicAdapter(this, mPicList);
        mGvPic.setAdapter(mAdapter);
        mGvPic.setOnItemClickListener(this);
    }

    /**
     * 初始化图片集合
     */
    private void initData() {
        mGvPic = (GridView) findViewById(R.id.gv_puzzle);
        mResPid = new int[]{
            R.mipmap.pic1,R.mipmap.pic2,
                R.mipmap.pic3,R.mipmap.pic4,
                R.mipmap.pic5,R.mipmap.pic6,
                R.mipmap.pic7,R.mipmap.pic8,
                R.mipmap.pic9,R.mipmap.pic10,
                R.mipmap.pic11,R.mipmap.pic12,
                R.mipmap.pic13,R.mipmap.pic14,
                R.mipmap.pic15,R.mipmap.plus};

        Bitmap[] bitmaps = new Bitmap[mResPid.length];

        for(int i = 0;i < bitmaps.length;i ++){
            bitmaps[i] = BitmapFactory.decodeResource(getResources(),mResPid[i]);
            mPicList.add(bitmaps[i]);
        }

        mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mPopupView = mLayoutInflater.inflate(R.layout.puzzle_type_selected, null);

        mTvPoPup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow(view);
            }
        });

        mTvType2 = (TextView) mPopupView.findViewById(R.id.tv_selected_type2);
        mTvType3 = (TextView) mPopupView.findViewById(R.id.tv_selected_type3);
        mTvType4 = (TextView) mPopupView.findViewById(R.id.tv_selected_type4);
        // 监听事件
        mTvType2.setOnClickListener(this);
        mTvType3.setOnClickListener(this);
        mTvType4.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(position == mResPid.length -1){
            showChooseDialog();
        }else{
            // 选择默认图片
            Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
            intent.putExtra("picSelectedID", mResPid[position]);
            intent.putExtra("mType", mType);
            startActivity(intent);
        }
    }

    /**
     * 难度选择的pop
     * @param view
     */
    private void popupWindow(View view){
        int density = (int) ScreenUtils.getDeviceDensity(this);
        mPopupWindow = new PopupWindow(mPopupView, 180 * density, 120 * density);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(colorDrawable);
        //获取位置
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY,location[0],location[1]+30*density);
    }

    /**
     *显示选择系统图库 相机对话框
     */
    private void showChooseDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("选择:");
        alertDialog.setItems(mCustomItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    //本地相册
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
                    startActivityForResult(intent, RESULT_IMAGE);
                }else if(i == 1){
                    //照相
                    // 系统相机
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri photoUri = Uri.fromFile(new File(TEMP_IMAGE_PATH));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, RESULT_CAMERA);
                }
            }
        });
        alertDialog.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == RESULT_IMAGE && data != null){
                //相册
                Cursor cursor = this.getContentResolver().query(data.getData(), null, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
                intent.putExtra("picPath",imagePath);
                intent.putExtra("type",mType);
                cursor.close();
                startActivity(intent);
            }else if(requestCode == RESULT_CAMERA){
                Intent intent = new Intent(MainActivity.this, PuzzleActivity.class);
                intent.putExtra("picPath",TEMP_IMAGE_PATH);
                intent.putExtra("type",mType);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_selected_type2:
                mType = 2;
                mTvPoPup.setText("2 x 2");
                break;
            case R.id.tv_selected_type3:
                mType = 3;
                mTvPoPup.setText("3 x 3");
                break;
            case R.id.tv_selected_type4:
                mType = 4;
                mTvPoPup.setText("4 x 4");
                break;
        }
        mPopupWindow.dismiss();
    }
}

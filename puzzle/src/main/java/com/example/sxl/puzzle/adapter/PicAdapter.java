package com.example.sxl.puzzle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.sxl.puzzle.utils.ScreenUtils;

import java.util.List;

/**
 * Created by LJDY490 on 2017/3/10.
 */

public class PicAdapter extends BaseAdapter {

    private List<Bitmap> mList;
    private Context mContext;

    public PicAdapter(Context context,List<Bitmap> list){
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = null;

        int density = (int) ScreenUtils.getDeviceDensity(mContext);

        if(view == null){
            imageView = new ImageView(mContext);
            //设置布局图片
            imageView.setLayoutParams(new GridView.LayoutParams(80*density,100*density));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }else {
            imageView = (ImageView) view;
        }

        imageView.setBackgroundColor(Color.BLACK);
        imageView.setImageBitmap(mList.get(i));
        return imageView;
    }
}

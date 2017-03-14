package com.example.sxl.puzzle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by LJDY490 on 2017/3/13.
 */

public class GridItemAdapter extends BaseAdapter {

    private Context mContext;
    private List<Bitmap> mBitmapList;

    public GridItemAdapter(Context context , List<Bitmap> list){
        this.mContext = context;
        this.mBitmapList = list;
    }

    @Override
    public int getCount() {
        return mBitmapList.size();
    }

    @Override
    public Object getItem(int i) {
        return mBitmapList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageView iv_pic_item = null;
        if (convertView == null) {
            iv_pic_item = new ImageView(mContext);
            // 设置布局 图片
            iv_pic_item.setLayoutParams(new GridView.LayoutParams(
                    mBitmapList.get(position).getWidth(),
                    mBitmapList.get(position).getHeight()));
            // 设置显示比例类型
            iv_pic_item.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            iv_pic_item = (ImageView) convertView;
        }
        iv_pic_item.setImageBitmap(mBitmapList.get(position));
        return iv_pic_item;
    }
}

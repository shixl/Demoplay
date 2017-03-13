package com.example.sxl.puzzle.bean;

import android.graphics.Bitmap;

/**
 * Created by LJDY490 on 2017/3/10.
 */

public class ItemBean {

    private int itemId;
    private int bitmapId;
    private Bitmap bitmap;

    public ItemBean() {
    }

    public ItemBean(int mItemId, int mBitmapId, Bitmap mBitmap) {
        this.itemId = mItemId;
        this.bitmapId = mBitmapId;
        this.bitmap = mBitmap;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getBitmapId() {
        return bitmapId;
    }

    public void setBitmapId(int bitmapId) {
        this.bitmapId = bitmapId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

package com.example.sxl.puzzle.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.sxl.puzzle.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJDY490 on 2017/3/10.
 */

public class ImagesUtils {


    private ItemBean itemBean;

    /**
     *
     * @param type 游戏种类
     * @param picSelected 选择的图片
     * @param context context
     */
    public void createInitBitmaps(int type, Bitmap picSelected, Context context){

        Bitmap bitmap = null;
        List<Bitmap> bitmapList = new ArrayList<>();

        int itemWidth = picSelected.getWidth() / type;
        int itemHeight = picSelected.getHeight() / type;

        for(int i = 1; i <= type; i++){
            for (int j = 1;j <= type; j++){
                bitmap = Bitmap.createBitmap(picSelected,
                        (j - 1) * itemWidth,
                        (i - 1) * itemHeight,
                        itemWidth, itemHeight);
                bitmapList.add(bitmap);
                itemBean = new ItemBean((i - 1) * type + j, (i - 1) * type + j, bitmap);
                GameUtils.mItemBeans.add(itemBean);
            }

        }
    }
}

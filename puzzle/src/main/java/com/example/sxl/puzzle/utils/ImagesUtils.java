package com.example.sxl.puzzle.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.sxl.puzzle.R;
import com.example.sxl.puzzle.activity.PuzzleActivity;
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
            //保存最后一张图片在完成时填充
            PuzzleActivity.mLastBitMap = bitmapList.get(type * type - 1);
            //设置最后一个为空item
            bitmapList.remove(type*type -1);
            GameUtils.mItemBeans.remove(type*type -1);
            Bitmap blankBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.blank);
            blankBitmap = Bitmap.createBitmap(blankBitmap,0,0,itemWidth,itemHeight);

            bitmapList.add(blankBitmap);
            GameUtils.mItemBeans.add(new ItemBean(type*type,0,blankBitmap));
            GameUtils.mBlankItemBean = GameUtils.mItemBeans.get(type*type-1);
        }
    }

    /**
     * 处理图片 放大、缩小到合适位置
     *
     * @param newWidth  缩放后Width
     * @param newHeight 缩放后Height
     * @param bitmap    bitmap
     * @return bitmap
     */
    public Bitmap resizeBitMap(float newWidth ,float newHeight,Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth/bitmap.getWidth(),newHeight/bitmap.getHeight());
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return newBitmap;
    }
}

package com.example.sxl.puzzle.utils;

import com.example.sxl.puzzle.activity.PuzzleActivity;
import com.example.sxl.puzzle.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJDY490 on 2017/3/10.
 * 拼图工具类：实现拼图的交换与生成算法
 */
public class GameUtils {

    // 游戏信息单元格Bean
    public static List<ItemBean> mItemBeans = new ArrayList<ItemBean>();
    // 空格单元格
    public static ItemBean mBlankItemBean = new ItemBean();

    /**
     *  判断点击item是否可以移动
     * @param position
     * @return 能否移动
     */
    public static boolean isMove(int position){
        int type = PuzzleActivity.TYPE;
        //获取空格item
        int blankId = GameUtils.mBlankItemBean.getItemId() - 1;
        // 不同行 相差为type
        if(Math.abs(blankId - position) == type){
            return true;
        }
        // 相同行 相差为1
        if ((blankId / type == position / type) && Math.abs(blankId - position) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 交换空格与点击Item的位置
     * @param from  交换图
     * @param blank 空白图
     */
    public  static void swapItems(ItemBean from , ItemBean blank){
        ItemBean tempItemBean = new ItemBean();
        //交换BitmapID
        tempItemBean.setBitmapId(from.getItemId());
        from.setBitmapId(blank.getItemId());
        blank.setBitmapId(tempItemBean.getBitmapId());

        //交换Bitmap
        tempItemBean.setBitmap(from.getBitmap());
        from.setBitmap(blank.getBitmap());
        blank.setBitmap(tempItemBean.getBitmap());

        //设置新的blank
        GameUtils.mBlankItemBean = from;
    }

    /**
     * 是否拼图成功
     *
     * @return 是否拼图成功
     */
    public static boolean isSuccess() {
        for (ItemBean tempBean : GameUtils.mItemBeans) {
            if (tempBean.getBitmapId() != 0 && (tempBean.getItemId()) == tempBean.getBitmapId()) {
                continue;
            } else if (tempBean.getBitmapId() == 0 && tempBean.getItemId() == PuzzleActivity.TYPE * PuzzleActivity.TYPE) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 生成随机的item
     */
    public static void getPuzzleGenerator(){
        int index = 0;
        //随即打乱顺序
        for(int i = 0; i < mItemBeans.size() ;i++ ){
            index = (int) (Math.random()* PuzzleActivity.TYPE * PuzzleActivity.TYPE);
            swapItems(mItemBeans.get(index),GameUtils.mBlankItemBean);
        }
        List<Integer> data = new ArrayList<>();
        for (int i = 0 ;i < mItemBeans.size(); i++){
            data.add(mItemBeans.get(i).getBitmapId());
        }

        //判断生成是否有解
        if(canSolve(data)){
            return;
        }else {
            getPuzzleGenerator();
        }
    }

    /**
     * 该数据是否有解
     *
     * @param data 拼图数组数据
     * @return 该数据是否有解
     */
    private static boolean canSolve(List<Integer> data) {
        //获取空格Id
        int blankId = GameUtils.mBlankItemBean.getItemId();
        //可行性原则
        if(data.size() % 2 == 1){
            return getInversions(data) % 2 == 0;
        }else {
            if(((blankId-1)/PuzzleActivity.TYPE) % 2 == 1 ){
                return getInversions(data) % 2 == 0;
            }else {
                // 从底往上数,空位位于偶数行
                return getInversions(data) % 2 == 1;
            }
        }
    }

    /**
     * 计算倒置和算法
     *
     * @param data 拼图数组数据
     * @return 该序列的倒置和
     */
    private static int getInversions(List<Integer> data) {
        int inversions = 0;
        int inversionCount = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                int index = data.get(i);
                if (data.get(j) != 0 && data.get(j) < index) {
                    inversionCount++;
                }
            }
            inversions += inversionCount;
            inversionCount = 0;
        }
        return inversions;
    }
}

package com.example.play.demoplay.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.play.demoplay.R;
import com.example.play.demoplay.adapter.DividerItemDecoration;
import com.example.play.demoplay.adapter.NormalAdapter;
import com.example.play.demoplay.adapter.NormalAdapterWrapper;
import com.example.play.demoplay.model.ObjectModel;

import java.util.ArrayList;
import java.util.List;


public class OneActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private List<ObjectModel> mData;
    private NormalAdapterWrapper mAdapter;
    private NormalAdapter mNoHeaderAdapter;
    private DividerItemDecoration mDecoration;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        mRecyclerView = (RecyclerView) findViewById(R.id.lv);
        mDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mDecoration);

        mNoHeaderAdapter = new NormalAdapter(mData = initData());
        mAdapter = new NormalAdapterWrapper(mNoHeaderAdapter);

        //加载头和脚
        View headerView = LayoutInflater.from(this).inflate(R.layout.item_head, mRecyclerView, false);
        View footView = LayoutInflater.from(this).inflate(R.layout.item_foot, mRecyclerView, false);

        mAdapter.addHeaderView(headerView);
        mAdapter.addFooterView(footView);

        mRecyclerView.setAdapter(mAdapter);

    }

    /**
     * 数据加载
     */
    public List<ObjectModel> initData(){
        List<ObjectModel> models = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.title_array);
        for(int i=0;i<titles.length;i++){
            ObjectModel model = new ObjectModel();
            model.number = i + 1;
            model.title = titles[i];
            models.add(model);
        }
        return models;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //添加
            case R.id.item_add:
                ObjectModel obj = new ObjectModel();
                obj.number = 0;
                obj.title = "Insert";
                mData.add(0,obj);
                mAdapter.notifyItemInserted(1);
                break;
            //删除
            case R.id.item_delete:
                mData.remove(0);
                mAdapter.notifyItemRemoved(1);
                break;
            //切换divider
            case R.id.item_change_divider:
                mDecoration.setDividerDrawable(getResources().getDrawable(R.drawable.divider));
                mAdapter.notifyDataSetChanged();
                break;
            //横向ListView
            case R.id.item_hlistview:
                mRecyclerView.removeItemDecoration(mDecoration);
                mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mDecoration = new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST);
                mRecyclerView.addItemDecoration(mDecoration);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

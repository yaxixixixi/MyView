package com.yaxi.photopicker.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yaxi.photopicker.R;
import com.yaxi.photopicker.test.TestRecyclerAdapter;
import com.yaxi.photopicker.test.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaxi on 2017/1/3.
 */

public class TestActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<TestBean> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recycler = (RecyclerView) findViewById(R.id.recycler);
        initData();
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycler.setAdapter(new TestRecyclerAdapter(this,datas));
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i%3 == 0){
                datas.add(new TestBean(R.mipmap.triangle,1));
            }else{
                datas.add(new TestBean(i+"条数据",0));
            }
        }
    }
}

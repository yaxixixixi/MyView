package com.yaxi.photopicker.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaxi.photopicker.R;
import com.yaxi.photopicker.test.TestBean;

import java.util.List;

/**
 * Created by yaxi on 2017/1/3.
 */

public class TestRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int ITEM_TYPE_DEFAULT = 0;
    private static final int ITEM_TYPE_NORMAL = 1;
    private Context context;
    private List<TestBean> datas;


    public TestRecyclerAdapter(Context context, List<TestBean> datas) {
        this.context = context;

        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case ITEM_TYPE_DEFAULT:
                view = inflater.inflate(R.layout.item_test_one,parent,false);
                holder = new ViewHolder1(view);
                break;
            case ITEM_TYPE_NORMAL:
                view = inflater.inflate(R.layout.item_test_two,parent,false);
                holder = new ViewHolder2(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1){
            ((ViewHolder1) holder).tv.setText(datas.get(position).getDes());
        }else if (holder instanceof ViewHolder2){
            ((ViewHolder2) holder).img.setImageResource(datas.get(position).getImgid());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder{
    private TextView tv;
        public ViewHolder1(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder{
        private ImageView img;
        public ViewHolder2(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }


}

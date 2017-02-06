package com.yaxi.photopicker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yaxi.photopicker.R;

import java.io.File;
import java.util.List;

/**
 * Created by yaxi on 2017/1/3.
 */

public class PhotoPickAdapter extends RecyclerView.Adapter<PhotoPickAdapter.ViewHolder>{

    private static final String TAG = PhotoPickAdapter.class.getSimpleName();
    private List<String> datas;
    private Context context;


    public PhotoPickAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public PhotoPickAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PhotoPickAdapter.ViewHolder holder;
                holder = new ViewHolder(LayoutInflater.from(context).inflate(
                        R.layout.item_photo_pivk,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(PhotoPickAdapter.ViewHolder holder, int position) {
        loadImage(datas.get(position),holder.photoItemImg);
        holder.photoItemCheckBox.setChecked(false);
    }



    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

         ImageView photoItemImg;
         CheckBox photoItemCheckBox;
        public ViewHolder(View itemView) {
            super(itemView);
            photoItemImg = (ImageView) itemView.findViewById(R.id.photo_item_image);
            photoItemCheckBox = (CheckBox) itemView.findViewById(R.id.photo_item_checkbox);

        }
    }


    private Bitmap fileName2Bitmap(String fileName){
        Log.i(TAG, "fileName2Bitmap: "+fileName);
        return BitmapFactory.decodeFile(fileName);
    }

    private void loadImage(String fileName,ImageView view){
        Glide.with(context)
                .load(fileName)
                .into(view);
    }
}

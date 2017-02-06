package com.yaxi.photopicker;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yaxi.photopicker.adapter.PhotoPickAdapter;
import com.yaxi.photopicker.bean.ImageFolder;
import com.yaxi.photopicker.utils.PermissionUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView gridPhoto;
    private TextView showAllPhoto,showPreView;
    int totalCount = 0;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    /**
     * 维护所有包含图片的文件夹的集合
     */
    private List<ImageFolder> imageForderList = new ArrayList<>();

    /**
     * 保存一个值，用来找到包含图片数量最多的文件夹
     */
    private int maxSize;

    /**
     * which file has the largest number of images.
     */
    private File maxSizeDir;

    /**
     *
     */
    private List<String> currentImgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!PermissionUtils.checkIsPermissionAllowed(this,PermissionUtils.READ_EXTERNAL_STORAGE)){
            PermissionUtils.addPermissionNeeded(this,PermissionUtils.MY_REQUEST_READ_EXTERNAL_STORAGE_CODE,
                    PermissionUtils.READ_EXTERNAL_STORAGE);
        }

        gridPhoto = (RecyclerView) findViewById(R.id.grid_photo);
        getAllImages();

        showAllPhoto = (TextView) findViewById(R.id.show_all_photo);
        showAllPhoto.setOnClickListener(this);
        showPreView = (TextView) findViewById(R.id.show_preview);
        showPreView.setOnClickListener(this);
    }

    private void initPhotoView(List<String> datas) {

        gridPhoto.setLayoutManager(new GridLayoutManager(this,3));
        gridPhoto.setAdapter(new PhotoPickAdapter(datas,this));
        gridPhoto.addItemDecoration(new );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_all_photo:
                showPopupWindow();
                break;
            case R.id.show_preview:

                break;
        }
    }

    private void showPopupWindow() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        ContentResolver resolver = getContentResolver();
        if (requestCode == 1000){
            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver,uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showListOfPhotos() {
        Intent intent = new Intent(Intent.ACTION_PICK,null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent,1000);
    }

    /**
     *  Image query just only happened in sub thread.
     */
    public void getAllImages(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.i(TAG, "getAllImages: No ExternalStorage");
            return;
        }
//        ProgressDialog.show(this,"搜索图片中",null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //用来作为文件夹的背景显示
                String firstImagePath = null;
                Uri imgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver resolver = MainActivity.this.getContentResolver();
                //MediaStore.Images.Media.MIME_TYPE + "=? or"+MediaStore.Images.Media.MIME_TYPE + "=?",这里用来表示占位符
                Cursor cursor = resolver.query(imgUri,null,MediaStore.Images.Media.MIME_TYPE + "=? or "
                        +MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg","image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                Log.i(TAG, "run: "+cursor.moveToNext());
                while (cursor.moveToNext()){
                    //获取当前游标所指图片的路径
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    Log.i(TAG, "run: path:"+path);
                    if (firstImagePath == null)
                        firstImagePath = path;

                    //获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;

                    String dirPath = parentFile.getAbsolutePath();
                    Log.i(TAG, "run: 目录："+ dirPath);
                    ImageFolder imageFolder = null;
                    if (mDirPaths.contains(dirPath)){
                        continue;
                    }else{
                        mDirPaths.add(dirPath);
                        imageFolder = new ImageFolder();
                        imageFolder.setDir(dirPath);
                        imageFolder.setFitstImagePath(firstImagePath);
                    }

                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            if (name.endsWith(".jpg")||name.endsWith(".jpeg")||name.endsWith(".png"))
                                return true;
                            return false;
                        }
                    }).length;
                    totalCount += picSize;
                    Log.i(TAG, "run: total pics = "+totalCount);

                    imageFolder.setCount(picSize);
                    imageForderList.add(imageFolder);

                    if (picSize > maxSize){
                        maxSize = picSize;
                        maxSizeDir = parentFile;
                    }
                }
                cursor.close();
                mDirPaths = null;
                overScanHandler.sendEmptyMessage(0);
            }
        }).start();
    }



    private Handler overScanHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (maxSizeDir == null){
                Log.i(TAG, "handleMessage: 不存在最大数量的文件夹，就是说还是初始化的文件夹，也就是说没有搜索到任何图片");
            }

            currentImgs = Arrays.asList(maxSizeDir.list());
            for (int i = 0; i < currentImgs.size(); i++) {
                currentImgs.set(i,maxSizeDir.getAbsolutePath()+"/"+currentImgs.get(i));
            }
            initPhotoView(currentImgs);
        }
    };


}

package com.yaxi.photopicker.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * Created by Administrator on 2016/8/12.
 */
public class PermissionUtils {

    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    public static final String SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW";
    public static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";


    public static final int MY_REQUEST_EXTERNAL_STORAGE_CDDE = 1;
    public static final int MY_REQUEST_INTERNET_CDDE = 2;
    public static final int MY_REQUEST_ACCESS_COARSE_LOCATION_CODE = 3;
    public static final int MY_REQUEST_SYSTEM_ALERT_WINDOW_CODE = 4;
    public static final int MY_REQUEST_READ_PHONE_STATE_CODE = 5;
    public static final int MY_REQUEST_READ_EXTERNAL_STORAGE_CODE = 6;

    //检查是否拥有所需要的权限
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkIsPermissionAllowed(Context ctx, String permission){
        if(ContextCompat.checkSelfPermission(ctx, permission)!=
                PackageManager.PERMISSION_GRANTED)
            return false;
        else
            return true;
    }



    //自定义添加所需要的权限
    public static void addPermissionNeeded(Activity maActivity, int requestCode, String permission){

        ActivityCompat.requestPermissions(
                maActivity, new String[]{permission}, requestCode);
    }



    //一步处理权限的检查和添加

    public static void onestepPermission(Context ctx, String permission, Activity maActivity, int requestCode){
        if(ContextCompat.checkSelfPermission(ctx, permission)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    maActivity, new String[]{permission}, requestCode);
        }
    }


    //悬浮窗权限 必须由用户到设置去打开，开发者在manifest和Java code中动态获取的都无效
    @TargetApi(Build.VERSION_CODES.M)
    public static void requestAlertDialogPermission(Context context){
        if (!Settings.canDrawOverlays(context)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:"+context.getPackageName()));
            ((Activity)context).startActivityForResult(intent,5);
        }
    }
}

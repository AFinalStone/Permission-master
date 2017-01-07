package com.shi.androidstudio.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CameraActivity extends AppCompatActivity {

    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private Button btn_openCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        btn_openCamera = (Button) findViewById(R.id.btn_openCamera);
        btn_openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CameraActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //正常申请权限
                    ActivityCompat.requestPermissions(CameraActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }else{
                    ToastUtil.show(CameraActivity.this, "已经授予权限");
                    openCamera();
                }

            }
        });
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CameraActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        }
        if(requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS){
            if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }else{
                //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                //默认的，第一次打开APP，这个方法也返回false
                boolean flag = ActivityCompat.shouldShowRequestPermissionRationale(CameraActivity.this,
                        Manifest.permission.CAMERA);
                if (!flag) {
                    showMessageOKCancel("请授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 用户拒绝授权并选择了不再提示
                            ToastUtil.show(CameraActivity.this,"请进行手动授权");
                            // 帮跳转到该应用的设置界面，让用户手动授权
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });

                }else{
                    SnackBarUtil.show(btn_openCamera, "授权失败");
                }
            }
        }
    }


    /**
     * 打开照相机
     */
    void openCamera() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
    }
}

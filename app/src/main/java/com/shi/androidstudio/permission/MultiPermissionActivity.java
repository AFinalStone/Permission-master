package com.shi.androidstudio.permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiPermissionActivity extends AppCompatActivity {

    int MY_PERMISSIONS_REQUEST_MULTI_CODE = 0;

    Button btn_callPhone;
    //被用户拒绝并勾选了不再提示选项
    final List<String> permissionsNeedRequest = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_permission);
        btn_callPhone = (Button) findViewById(R.id.btn_callPhone);
        btn_callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermissions();
            }
        });


    }

    private void getPermissions(){

        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.READ_CONTACTS);
        list.add(Manifest.permission.WRITE_CONTACTS);
        list.add(Manifest.permission.CALL_PHONE);
        list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionRequest(list);
    }

    /**
     *  检测用户是否勾选了不再提示选项,拒绝了返回false，没有拒绝返回true
     **/
    private void permissionRequest(List<String> permissionsList) {
        permissionsNeedRequest.clear();
        for (int i=0; i<permissionsList.size(); i++){
            String permission = permissionsList.get(i);
            //检测用户是否示已经授权该权限
            if (ActivityCompat.checkSelfPermission( MultiPermissionActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

                permissionsNeedRequest.add(permission);
            }
        }
        if(permissionsNeedRequest.size() > 0){
            ActivityCompat.requestPermissions( MultiPermissionActivity.this, permissionsNeedRequest.toArray(new String[permissionsNeedRequest.size()]),
                    MY_PERMISSIONS_REQUEST_MULTI_CODE);
        }else{
            Toast.makeText(MultiPermissionActivity.this, "权限全部申请成功了", Toast.LENGTH_SHORT)
                    .show();
        }

    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MultiPermissionActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

//    String message = "";
//    for (int i = 0; i < permissionsNeedRequest.size(); i++){
//        if(i == 0){
//            message += "请授权" + permissionsNeedRequest.get(i);
//        }else{
//            message += ", " + permissionsNeedRequest.get(i);
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            if(MY_PERMISSIONS_REQUEST_MULTI_CODE == requestCode)
            {
                //初始化,防止NullPointerException
                Map<String, Integer> perms = new HashMap<String, Integer>();
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                && perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED
                && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    // All Permissions Granted
                    Toast.makeText(MultiPermissionActivity.this, "权限全部申请成功了", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Permission Denied
                    Toast.makeText(MultiPermissionActivity.this, "部分权限被拒绝掉了", Toast.LENGTH_SHORT)
                            .show();
                }
            }
    }

}

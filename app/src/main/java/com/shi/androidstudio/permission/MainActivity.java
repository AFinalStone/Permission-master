package com.shi.androidstudio.permission;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_toCameraActivity;
    private Button btn_toPhoneActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_toCameraActivity = (Button) findViewById(R.id.btn_toCameraActivity);
        btn_toPhoneActivity = (Button) findViewById(R.id.btn_toPhoneActivity);
        btn_toCameraActivity.setOnClickListener(this);
        btn_toPhoneActivity.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_toPhoneActivity:
                startActivity(new Intent(this, MultiPermissionActivity.class));
                break;
            case R.id.btn_toCameraActivity:
                startActivity(new Intent(this, CameraActivity.class));
                break;
        }
    }
}

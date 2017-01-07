代码主要实现了截取屏幕图片或者某个特定控件所在区域的图片，并保存到本地SD中，代码很简单，就不再进行具体的描述了。

- **效果图**
![效果图](http://img.blog.csdn.net/20161227105536021?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYWJjNjM2ODc2NQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
- **SaveScreenActivity.java代码**
```java
package com.shi.androidstudio.savescreen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.InvalidParameterException;

/**
 *  截取图片并保存
 * @author SHI
 * @time 2016/12/27 10:37
 */
public class SaveScreenActivity extends AppCompatActivity {

    Button btn_saveScreen;
    Button btn_saveViewScreen;
    ImageView imageView;
    View rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = View.inflate(this, R.layout.activity_save_screen, null);
        setContentView(rootView);
        btn_saveScreen = (Button) findViewById(R.id.btn_saveScreen);
        btn_saveViewScreen = (Button) findViewById(R.id.btn_saveViewScreen);
        imageView = (ImageView) findViewById(R.id.imageView);
        btn_saveScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScreenImage(getWindow().getDecorView(), "/sdcard/saveScreen.png");
            }
        });
        btn_saveViewScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScreenImage(imageView, "/sdcard/saveViewScreen.png");
            }
        });
    }

    private void saveScreenImage(View v, String filePath) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas();
            canvas.setBitmap(bitmap);
            v.draw(canvas);

            try {
                FileOutputStream fos = new FileOutputStream(filePath);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                show("截图保存成功");
            } catch (FileNotFoundException e) {
                throw new InvalidParameterException();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show(String msg) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT).show();
    }
}

```
- **activity_save_screen.xml**
```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center_horizontal"
    android:orientation="vertical"
    android:padding="10dp"
    tools:context=".SaveScreenActivity">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:src="@mipmap/icon" />

    <Button
        android:id="@+id/btn_saveViewScreen"
        style="?android:textAppearanceSmall"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="@string/btn_saveViewScreen"
        android:textStyle="bold" />

    <Button
        android:id="@+id/btn_saveScreen"
        style="?android:textAppearanceSmall"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="@string/btn_saveScreen"
        android:textStyle="bold" />
</LinearLayout>

```

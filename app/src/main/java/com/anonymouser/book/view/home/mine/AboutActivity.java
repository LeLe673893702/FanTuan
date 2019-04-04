package com.anonymouser.book.view.home.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.aitangba.swipeback.SwipeBackActivity;
import com.anonymouser.book.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于页面
 * Created by YandZD on 2017/8/10.
 */

public class AboutActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.ivReturn)
    public void onRetrun(View view) {
        finish();
    }
}

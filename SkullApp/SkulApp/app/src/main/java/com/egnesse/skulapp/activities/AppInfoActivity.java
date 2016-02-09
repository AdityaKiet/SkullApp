package com.egnesse.skulapp.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.egnesse.skulapp.R;
import com.egnesse.skulapp.adapter.AppInfoSlidingImageAdapter;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adityaagrawal on 09/02/16.
 */
public class AppInfoActivity extends AppCompatActivity{
    @Bind(R.id.btnStart)
    Button btnStart;
    @Bind(R.id.indicator)
    FlycoPageIndicaor indicaor;
    @Bind(R.id.pager)
    ViewPager pager;

    @OnClick(R.id.btnStart) void submit(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_app_info);

        populate();
    }

    private void populate(){
        ButterKnife.bind(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnStart.setBackgroundResource(R.drawable.abc_alpha_btn_background_ripple);
        }

        AppInfoSlidingImageAdapter appInfoSlidingImageAdapter = new AppInfoSlidingImageAdapter(this);
        pager.setAdapter(appInfoSlidingImageAdapter);
        indicaor.setViewPager(pager);

    }
}

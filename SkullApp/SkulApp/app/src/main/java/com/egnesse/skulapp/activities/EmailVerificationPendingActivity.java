package com.egnesse.skulapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.egnesse.skulapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by adityaagrawal on 11/02/16.
 */
public class EmailVerificationPendingActivity extends AppCompatActivity{
    @Bind(R.id.gifSuccess)
    GifImageView gifSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification_pending);
        ButterKnife.bind(this);
        try {
            GifDrawable gifFromAssets = new GifDrawable(getAssets(), "success.gif");
            gifSuccess.setImageDrawable(gifFromAssets);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

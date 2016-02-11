package com.egnesse.skulapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.egnesse.skulapp.GCMIntentService;
import com.egnesse.skulapp.R;
import com.egnesse.skulapp.dto.SessionDTO;
import com.egnesse.skulapp.util.SckulApp;
import com.google.android.gcm.GCMRegistrar;

/**
 * Created by adityaagrawal on 09/02/16.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SessionDTO sessionDTO = SckulApp.getSession(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (sessionDTO.getGcmKey() == null) {
            try {
                GCMRegistrar.checkDevice(this);
                GCMRegistrar.checkManifest(this);
                GCMRegistrar.register(this, GCMIntentService.SENDER_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, AppInfoActivity.class);
                    startActivity(intent);
                  /*  Splash splash = new Splash();
                    if (splash.isLogin(SplashActivity.this)) {
                        Intent intent = new Intent("com.o9pathshala.student.slidingmenu.fragments.MainActivity");
                        startActivity(intent);
                        SplashActivity.this.overridePendingTransition(R.drawable.zooom_in,0);
                    } else {
                        Intent intent = new Intent("com.o9pathshala.student.MainActivity");
                        startActivity(intent);
                        SplashActivity.this.overridePendingTransition(R.drawable.zooom_in,0);
                    }*/
                }

            }
        };

        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

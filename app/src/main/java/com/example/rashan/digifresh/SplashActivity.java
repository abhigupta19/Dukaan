package com.example.rashan.digifresh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


public class SplashActivity extends AppCompatActivity {


    ImageView scrollingImageView;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        scrollingImageView = (ImageView) findViewById(R.id.splash_image1);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String SP_LOGIN_STATUS = getResources().getString(R.string.SP_LOGIN_STATUS);
        final String SP_FIRST_LOGIN = getResources().getString(R.string.SP_FIRST_LOGIN);


        new SabziInitTask(SplashActivity.this).execute();
        if (preferences.getBoolean(SP_LOGIN_STATUS, false)) {
            Intent intent = new Intent(getApplicationContext(), ZLogin.class);
             startActivity(intent);
              finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), ZLogin.class);
            startActivity(intent);
            finish();
        }

    }



 /*       new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {


                                              if (isNetworkAvailable()) {
                                                  if (preferences.getBoolean(SP_LOGIN_STATUS, false)) {
                                                      //Intent intent = new Intent(getApplicationContext(), ZSabziActivity.class);
                                                     // startActivity(intent);
                                                    //  finish();
                                                  } else {
                                                      Intent intent = new Intent(getApplicationContext(), ZLogin.class);
                                                      startActivity(intent);
                                                      finish();
                                                  }
                                              } else {
                                                 // Intent intent = new Intent(getApplicationContext(),  NoInternetActivity.class);
                                                 // startActivity(intent);
                                                 // finish();
                                              }

                                      }
                                  }

                , 2000);
    }*/

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}

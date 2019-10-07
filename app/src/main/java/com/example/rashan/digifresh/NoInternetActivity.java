package com.example.rashan.digifresh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class NoInternetActivity extends AppCompatActivity {

    ImageView refresh;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        refresh = (ImageView) findViewById(R.id.refresh);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {

                    if(preferences.getBoolean(getResources().getString(R.string.SP_SABZI_INIT),true))
                    {
                        new SabziInitTask(NoInternetActivity.this).execute();
                    }

                    if (preferences.getBoolean(getResources().getString(R.string.SP_LOGIN_STATUS), false)) {
                        Intent intent = new Intent(getApplicationContext(), ZSabziActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ZLogin.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(refresh, "No Internet Found", Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                }
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

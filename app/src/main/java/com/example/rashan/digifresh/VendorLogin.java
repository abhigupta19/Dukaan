package com.example.rashan.digifresh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VendorLogin extends AppCompatActivity {

    EditText vlMail, vlPassword;
    Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);

        setTitle("Vendor Login");

        vlMail = (EditText) findViewById(R.id.vl_mail);
        vlPassword = (EditText) findViewById(R.id.vl_password);

        register = (Button) findViewById(R.id.vl_register_btn);
        login = (Button) findViewById(R.id.vl_login_btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VendorLogin.this, VendorRegistration.class);
                startActivity(in);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add
            }
        });
    }
}

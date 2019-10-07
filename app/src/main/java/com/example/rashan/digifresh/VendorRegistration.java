package com.example.rashan.digifresh;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VendorRegistration extends AppCompatActivity {

    EditText vrName, vrPhone, vrMail, vrLocality, vrPassword;
    TextView vrAadhar;
    ContentValues vrDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        vrName = (EditText) findViewById(R.id.vr_name);
        vrPhone = (EditText) findViewById(R.id.vr_phone);
        vrMail = (EditText) findViewById(R.id.vr_mail);
        vrLocality = (EditText) findViewById(R.id.vr_locality);
        vrPassword = (EditText) findViewById(R.id.vr_password);

        vrAadhar = (TextView) findViewById(R.id.vr_adhar_filepath);
        vrAadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add photo
            }
        });
    }

    private ContentValues getData(){
        ContentValues values = new ContentValues();

        try{

            values.put("name", vrName.getText().toString());
            values.put("mail", vrMail.getText().toString());
            values.put("phone", vrPhone.getText().toString());
            values.put("locality", vrLocality.getText().toString());
            values.put("password", vrLocality.getText().toString());

            return values;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public void fabClicked(View view)
    {
       Dialog dialog = new Dialog(VendorRegistration.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.show();
        //dialog.setCancelable(false);

    }

    public void yesClicked(View view)
    {
        Toast.makeText(VendorRegistration.this, "Your Request Has Been Registered along with your ESIC Benefit plan", Toast.LENGTH_SHORT).show();
        Intent ir = new Intent(VendorRegistration.this, ZLogin.class);
        startActivity(ir);
        finish();
    }

    public void noClicked(View view )
    {
        Toast.makeText(VendorRegistration.this, "Your Request Has Been Registered", Toast.LENGTH_SHORT).show();
        Intent ir = new Intent(VendorRegistration.this, ZLogin.class);
        startActivity(ir);
        finish();
    }
}

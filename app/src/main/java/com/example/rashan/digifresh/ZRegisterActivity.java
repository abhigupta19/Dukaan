package com.example.rashan.digifresh;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ZRegisterActivity extends AppCompatActivity {


    final String MAIL_KEY = "email";
    final String PASSWORD_KEY = "password";
    final String RESULT_STATUS_KEY = "status";
    final String NAME_KEY = "name";
    final String PIC_KEY = "pic";
    final String CONTACT_KEY = "contact";
    final String ADDRESS_KEY = "address";
    final String GENDER_KEY = "gender";
    final String GENDER_PREFERENCE_KEY = "gender_preference";
    final String FAVOURITE_KEY = "favourite";
    final String ID_KEY = "id";

    String SP_VENDOR_CATEGORY_4;
    String SP_VENDOR_CATEGORY_3;
    String SP_VENDOR_CATEGORY_2;
    String SP_VENDOR_CATEGORY_1;
    String SP_SUBAREA_ID;
    String SP_SUBAREA;
    String SP_AREA;
    String SP_LOGIN_STATUS;
    String SP_GENDER_PREFERENCE;
    String SP_USER_GENDER;
    String SP_USER_ADDRESS;
    String SP_USER_CONTACT;
    String SP_USER_PIC;
    String SP_USER_EMAIL;
    String SP_USER_NAME;
    String SP_USER_ID;
    String SELECT_AREA_TASK_URL;

    String fb_id, email;


    TextInputEditText rname, rmail, rpwd, rcpwd;
    TextView loginLink, orR;
    AppCompatButton register;
    Dialog dialog;
    ProgressBar rpro;

    private Typeface ssp;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private JSONObject json;
    private JSONArray jsonArray;

    private ImageButton GRegister;
    private TextView termsR;
    private ImageView progressImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("Rashan","In ZRegisterActivity");
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);

        setContentView(R.layout.activity_register);


        SP_USER_ID = getResources().getString(R.string.SP_USER_ID);
        SP_USER_NAME = getResources().getString(R.string.SP_USER_NAME);
        SP_USER_EMAIL = getResources().getString(R.string.SP_USER_EMAIL);
        SP_USER_PIC = getResources().getString(R.string.SP_USER_PIC);
        SP_USER_CONTACT = getResources().getString(R.string.SP_USER_CONTACT);
        SP_USER_ADDRESS = getResources().getString(R.string.SP_USER_ADDRESS);
        SP_USER_GENDER = getResources().getString(R.string.SP_USER_GENDER);
        SP_GENDER_PREFERENCE = getResources().getString(R.string.SP_GENDER_PREFERENCE);
        SP_LOGIN_STATUS = getResources().getString(R.string.SP_LOGIN_STATUS);
        SP_AREA = getResources().getString(R.string.SP_AREA);
        SP_SUBAREA = getResources().getString(R.string.SP_SUBAREA);
        SP_SUBAREA_ID = getResources().getString(R.string.SP_SUBAREA_ID);
        SP_VENDOR_CATEGORY_1 = getResources().getString(R.string.SP_VENDOR_CATEGORY_1);
        SP_VENDOR_CATEGORY_2 = getResources().getString(R.string.SP_VENDOR_CATEGORY_2);
        SP_VENDOR_CATEGORY_3 = getResources().getString(R.string.SP_VENDOR_CATEGORY_3);
        SP_VENDOR_CATEGORY_4 = getResources().getString(R.string.SP_VENDOR_CATEGORY_4);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();


        ssp = Typeface.createFromAsset(getAssets(),
                "ssp.ttf");
        rname = (TextInputEditText) findViewById(R.id.rname);
        rmail = (TextInputEditText) findViewById(R.id.rmail);
        rpwd = (TextInputEditText) findViewById(R.id.rpwd);
        rcpwd = (TextInputEditText) findViewById(R.id.rcpwd);
        register = (AppCompatButton) findViewById(R.id.register);
        rpro = (ProgressBar) findViewById(R.id.rpro);
        loginLink = (TextView) findViewById(R.id.loginLink);
        termsR=(TextView)findViewById(R.id.termsR);
        orR = (TextView) findViewById(R.id.orR);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressImage=(ImageView)dialog.findViewById(R.id.load);
        ((AnimationDrawable)progressImage.getBackground()).start();

        GRegister = (ImageButton) findViewById(R.id.GRegister);




        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ZLogin.class);
                startActivity(intent);
                finish();
            }
        });



        termsR.setText(Html.fromHtml("<p>By signing up you accept our " +
                "<a href=\"http://vegvendors.in/pages/privacy-policy\" style=\"color: #4caf50\">" +
                "Privacy Policies</a> and <a href=\"http://vegvendors.in/pages/tncs\" style=\"color: #4caf50\">" +
                "Terms &amp; Conditions</a></p>"));

        termsR.setMovementMethod(LinkMovementMethod.getInstance());

     /*   termsR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(getResources().getString(R.string.privacy_policy_url));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });*/




        register.setTypeface(ssp);
        loginLink.setTypeface(ssp);
        orR.setTypeface(ssp);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    rname.setText(rname.getText().toString().trim());
                    rmail.setText(rmail.getText().toString().trim());
                    if (rname.getText().toString().equals("")) {
                        Toast.makeText(ZRegisterActivity.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                    } else if (rmail.getText().toString().equals("")) {
                        Toast.makeText(ZRegisterActivity.this, "Please enter Email ID", Toast.LENGTH_SHORT).show();
                    } else if (rpwd.getText().toString().equals("")) {
                        Toast.makeText(ZRegisterActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    } else if (rcpwd.getText().toString().equals("")) {
                        Toast.makeText(ZRegisterActivity.this, "Please confirm Password", Toast.LENGTH_SHORT).show();
                    }
                 else if (!rpwd.getText().toString().equals(rcpwd.getText().toString())) {
                    Toast.makeText(ZRegisterActivity.this, "Passwords Do not Match", Toast.LENGTH_SHORT).show();
                        rpwd.setText("");
                        rcpwd.setText("");
                } else if (!isValidEmail(rmail.getText().toString())) {
                        Toast.makeText(ZRegisterActivity.this, "Enter Valid E-Mail Address", Toast.LENGTH_SHORT).show();
                        rpwd.setText("");
                        rcpwd.setText("");
                    } else if (rpwd.getText().toString().length()<8){
                        Toast.makeText(ZRegisterActivity.this,"Password must be at least 8 characters long",Toast.LENGTH_SHORT).show();
                        rpwd.setText("");
                        rcpwd.setText("");
                        }
                 else {
                    //Task to be executed

                        register.setVisibility(View.GONE);
                        rpro.setVisibility(View.VISIBLE);

                        Object objects[] = SerializeData.read(ZRegisterActivity.this, "users");
                        List<UserPojo> userPojoList;
                        if (objects != null) {
                            userPojoList = (List<UserPojo>) objects[0];
                        } else {
                            userPojoList = new ArrayList<UserPojo>();
                        }

                        boolean flg=true;
                        for (int i = 0; i < userPojoList.size(); i++)
                        {
                            if(userPojoList.get(i).getEmail().equals(rmail.getText().toString()))
                            {
                                flg=false;
                                break;
                            }
                        }

                        if(flg==true)
                        {
                            userPojoList.add(new UserPojo(rmail.getText().toString(), rpwd.getText().toString(), rname.getText().toString()));
                            Object object[]={userPojoList};

                            SerializeData.save(ZRegisterActivity.this,object,"users");

                            Intent intent = new Intent(ZRegisterActivity.this, ZLogin.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(ZRegisterActivity.this, "Registered! Please Login", Toast.LENGTH_SHORT).show();
                            Object o[]=SerializeData.read(ZRegisterActivity.this,"users");

                        }
                        else
                        {
                            Toast.makeText(ZRegisterActivity.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                        }


                   // new RegisterTask().execute(rmail.getText().toString(), rpwd.getText().toString(), rname.getText().toString());

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






    public boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), ZLogin.class);
        startActivity(intent);
        finish();
    }



    public class RegisterTask extends AsyncTask<String, Void, Void> {
        final String MAIL_KEY = "user_email";
        final String PASSWORD_KEY = "user_password";
        final String NAME_KEY = "full_name";
        String mail, pwd, name;
        String base_url = getResources().getString(R.string.register_script);
        String result;


        @Override
        protected Void doInBackground(String... strings) {

            mail = strings[0];
            pwd = strings[1];
            name = strings[2];


            try {
                URL url = new URL(base_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                String data = URLEncoder.encode(MAIL_KEY, "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8") +
                        "&" + URLEncoder.encode(PASSWORD_KEY, "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8") +
                        "&" + URLEncoder.encode(NAME_KEY, "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                result = bufferedReader.readLine();
                result = result.trim();
                bufferedReader.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result.equals("registered")) {


                Intent intent = new Intent(ZRegisterActivity.this, ZLogin.class);
                startActivity(intent);
                finish();
                Toast.makeText(ZRegisterActivity.this, "Registered! Please Login", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ZRegisterActivity.this, "Registration Failed! Please Try Again", Toast.LENGTH_SHORT).show();
                register.setVisibility(View.VISIBLE);
                rpro.setVisibility(View.GONE);
            }
        }
    }
}

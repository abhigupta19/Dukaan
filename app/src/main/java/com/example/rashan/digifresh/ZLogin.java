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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZLogin extends AppCompatActivity {

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
    String SP_USER_REFERRAL;
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


    TextInputEditText lmail, lpwd;
    AppCompatButton login;
    Dialog dialog;
    TextView registerLink, forgotLink, orL;
    ProgressBar lpro;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Typeface ssp;
    ImageButton GLogin;
    String fb_id, email;
    private JSONArray jsonArray;
    private JSONObject json;

    private TextView terms;
    private ImageView progressImage;
    int flag;


    Button fbLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);


        setContentView(R.layout.activity_login2);
        ssp = Typeface.createFromAsset(getAssets(),
                "ssp.ttf");


        try {
            File file = new File(getApplicationContext().getFilesDir().toString().concat("/crash.txt"));
            FileOutputStream fout = new FileOutputStream(file);
            // Log.v("Rashan",jsonArray.toString());
            fout.write("HELLO I AM RASHAN".getBytes());
            fout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }




        // A.uploadFile(getApplicationContext().getFilesDir().toString().concat("/crash.txt"));


        SP_USER_ID = getResources().getString(R.string.SP_USER_ID);
        SP_USER_NAME = getResources().getString(R.string.SP_USER_NAME);
        SP_USER_EMAIL = getResources().getString(R.string.SP_USER_EMAIL);
        SP_USER_PIC = getResources().getString(R.string.SP_USER_PIC);
        SP_USER_CONTACT = getResources().getString(R.string.SP_USER_CONTACT);
        SP_USER_ADDRESS = getResources().getString(R.string.SP_USER_ADDRESS);
        SP_USER_REFERRAL = getResources().getString(R.string.SP_USER_REFERRAL);
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

        lmail = (TextInputEditText) findViewById(R.id.lmail);
        lpwd = (TextInputEditText) findViewById(R.id.lpwd);
        login = (AppCompatButton) findViewById(R.id.login);
        lpro = (ProgressBar) findViewById(R.id.lpro);
        registerLink = (TextView) findViewById(R.id.registerLink);
        forgotLink = (TextView) findViewById(R.id.forgotLink);
        orL = (TextView) findViewById(R.id.orL);

        GLogin = (ImageButton) findViewById(R.id.GLogin);
        terms = (TextView) findViewById(R.id.terms);


        login.setTypeface(ssp);
        registerLink.setTypeface(ssp);
        orL.setTypeface(ssp);
        forgotLink.setTypeface(ssp);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressImage = (ImageView) dialog.findViewById(R.id.load);
        ((AnimationDrawable) progressImage.getBackground()).start();
       /* dialog.setIndeterminate(true);
        dialog.setMessage("Loading.......");*/
        dialog.setCancelable(false);



        new SabziInitTask(ZLogin.this).execute();



        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ZRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        terms.setText(Html.fromHtml("<p>By signing up you accept our " +
                "<a href=\"http://vegvendors.in/pages/privacy-policy\" style=\"color: #4caf50\">" +
                "Privacy Policies</a> and <a href=\"http://vegvendors.in/pages/tncs\" style=\"color: #4caf50\">" +
                "Terms &amp; Conditions</a></p>"));

        terms.setMovementMethod(LinkMovementMethod.getInstance());

        /*terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse(getResources().getString(R.string.privacy_policy_url));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });*/




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



/*
                PrintWriter pw = null;

                try {
                    Log.v("Rashan",getFilesDir().listFiles().toString());
                    File Fileright = new File(getApplicationContext().getFilesDir().toString().concat("/data.txt"));
                    //throwable.printStackTrace();
                    pw = new PrintWriter(Fileright);




                 *//*   pw.write("User Id: "+preferences.getString("USER_ID","Not Logged in yet")+ "\n");
                    pw.write("User Email: "+preferences.getString("USER_EMAIL","")+ "\n");
                    pw.write("Android Version:"+ String.valueOf(Build.VERSION.SDK_INT)+ "\n");*//*
                    pw.write("Hello Mukul File Bna Li?? ");
                   // pw.write("User Email: "+preferences.getString("USER_EMAIL","")+"\n");


                    //throwable.printStackTrace(pw);
                    pw.write("****");
                    pw.flush();
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //    new SelectAreaTask(ZLogin.this,1,"",null).execute();
                new A(ZLogin.this).execute(getApplicationContext().getFilesDir().toString().concat("/data.txt"));*/

                lmail.setText(lmail.getText().toString().trim());

                flag = -1;

                if (isValidEmail(lmail.getText().toString()))
                    flag = 1;
                else if (lmail.getText().toString().length() == 10)             //only 10 digit mobile number
                {
                    flag = 0;
                    for (int i = 0; i < lmail.getText().toString().length(); i++) {
                        if (!Character.isDigit(lmail.getText().toString().charAt(i))) {
                            flag = -1;
                            break;
                        }
                    }
                }


                if (lmail.getText().toString().equals("")) {
                    Toast.makeText(ZLogin.this, "Please enter Email ID", Toast.LENGTH_SHORT).show();
                } else if (lpwd.getText().toString().equals("")) {
                    Toast.makeText(ZLogin.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                } else if (flag == -1) {
                    Toast.makeText(ZLogin.this, "Enter Valid E-Mail Address Or Mobile Number", Toast.LENGTH_SHORT).show();
                } else {
                    //Task to be executed

                    login.setVisibility(View.GONE);
                    lpro.setVisibility(View.VISIBLE);

                    Object objects[] = SerializeData.read(ZLogin.this, "users");

                    if (objects==null) {
                        lpro.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Incorrect Username Or Password", Toast.LENGTH_SHORT).show();
                    } else {
                        List<UserPojo> userPojoList;
                        if (objects != null) {
                            userPojoList = (List<UserPojo>) objects[0];
                        } else {
                            userPojoList = new ArrayList<UserPojo>();
                        }


                        boolean flg = true;
                        for (int i = 0; i < userPojoList.size(); i++) {
                            if (userPojoList.get(i).getEmail().equals(lmail.getText().toString()) && userPojoList.get(i).getPassword().equals(lpwd.getText().toString())) {

                                editor.putString(SP_USER_NAME, userPojoList.get(i).getEmail());
                                editor.putString(SP_USER_EMAIL, userPojoList.get(i).getName());
                                editor.putBoolean(SP_LOGIN_STATUS, true);
                                flg=false;
                                editor.commit();
                                Intent intent = new Intent(ZLogin.this, ZSabziActivity.class);
                                startActivity(intent);
                                finish();



                            }
                        }
                        if(flg)
                        Toast.makeText(getApplicationContext(), "Incorrect Username Or Password", Toast.LENGTH_SHORT).show();


                        // new LoginTask().execute(lmail.getText().toString(), lpwd.getText().toString(), String.valueOf(flag));

                    }

                }
            }
        }
        );


    }




    public boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public class LoginTask extends AsyncTask<String, Void, Void> {


        String mail, pwd, flg;
        String base_url = "http://www.vegvendors.in/android/login.php";
        String result;
        SharedPreferences preferences;

        String myCookie;

        @Override
        protected Void doInBackground(String... strings) {

            mail = strings[0];
            pwd = strings[1];
            flg = strings[2];


            try {
                URL url = new URL(base_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));


                String data = URLEncoder.encode("flag", "UTF-8") + "=" + URLEncoder.encode(flg, "UTF-8") +
                        "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8") +
                        "&" + URLEncoder.encode(PASSWORD_KEY, "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                final String COOKIES_HEADER = "Set-Cookie";
                final String COOKIE = "Cookie";
                CookieManager msCookieManager = new CookieManager();
                Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                //Uri uri=Uri.parse("rashanjyot");
                //URI uri=new URI("rashanjyot");
                URI uri = URI.create("myappcookie");
                if (cookiesHeader != null) {
                    for (String cookie : cookiesHeader) {
                        msCookieManager.getCookieStore().add(uri, HttpCookie.parse(cookie).get(0));
                    }
                }


                List<HttpCookie> cookieList = msCookieManager.getCookieStore().get(uri);
                HttpCookie httpCookie = cookieList.get(0);
                String httpCookieName = httpCookie.getName();
                String httpCookiePath = httpCookie.getPath();
                String httpCookieValue = httpCookie.getValue();


                myCookie = TextUtils.join(";", msCookieManager.getCookieStore().getCookies());


                result = bufferedReader.readLine();

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
            try {

                if (result != null) {


                    Log.v("Rashan", result);

                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString(RESULT_STATUS_KEY).equals("ok")) {

                        editor.putInt(SP_USER_ID, jsonObject.getInt(ID_KEY));

                        if (jsonObject.getString(NAME_KEY).equals("null")) {
                            editor.putString(SP_USER_NAME, "");
                        } else {
                            editor.putString(SP_USER_NAME, jsonObject.getString(NAME_KEY));
                        }
                        if (jsonObject.getString(MAIL_KEY).equals("null")) {
                            editor.putString(SP_USER_EMAIL, "");
                        } else {
                            editor.putString(SP_USER_EMAIL, jsonObject.getString(MAIL_KEY));
                        }
                        if (jsonObject.getString(CONTACT_KEY).equals("null")) {
                            editor.putString(SP_USER_CONTACT, "");
                        } else {
                            editor.putString(SP_USER_CONTACT, jsonObject.getString(CONTACT_KEY));
                        }
                        if (jsonObject.getString(ADDRESS_KEY).equals("null")) {
                            editor.putString(SP_USER_ADDRESS, "");
                        } else {
                            editor.putString(SP_USER_ADDRESS, jsonObject.getString(ADDRESS_KEY));
                        }
                        if (jsonObject.getString("referral_code").equals("null")) {
                            editor.putString(SP_USER_REFERRAL, "");
                        } else {
                            editor.putString(SP_USER_REFERRAL, jsonObject.getString("referral_code"));
                        }

                        editor.putString(SP_USER_PIC, jsonObject.getString(PIC_KEY));
                        editor.putString(SP_USER_GENDER, jsonObject.getString(GENDER_KEY));
                        editor.putString(SP_GENDER_PREFERENCE, jsonObject.getString(GENDER_PREFERENCE_KEY));
                        editor.putBoolean(SP_LOGIN_STATUS, true);
                        editor.putString("cookiedetails", myCookie);
                        editor.commit();
//////////////////////////////////////////////////////////////////////////
                        //new ReferralTask(ZLogin.this).execute(String.valueOf(jsonObject.getInt(ID_KEY)));

                        Intent intent = new Intent(ZLogin.this, ZSabziActivity.class);
                        //Intent intent = new Intent(ZLogin.this, ZRegisterActivity.class);
                        intent.putExtra("cookiedetails", myCookie);
                        //   editor.putString("cookiedetails",myCookie);
                        // intent.putExtra("cookiedetails","PHPSESSID=pr26ooeahqe69421lml9hvpou7");
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(ZLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        login.setVisibility(View.VISIBLE);
                        lpro.setVisibility(View.GONE);

                    }
                } else {

                    Toast.makeText(ZLogin.this, "Network issue", Toast.LENGTH_SHORT).show();
                    login.setVisibility(View.VISIBLE);
                    lpro.setVisibility(View.GONE);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void vendorLink(View view)
    {
        Intent ir = new Intent(ZLogin.this, VendorRegistration.class);
        startActivity(ir);
        finish();
    }



}


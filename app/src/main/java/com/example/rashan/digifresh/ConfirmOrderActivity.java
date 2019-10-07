package com.example.rashan.digifresh;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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

public class ConfirmOrderActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private static final String PLACE_CODE_KEY = "place_code";
    final String SABZI_ID_KEY = "name";
    final String USER_ID_KEY = "uid";
    final String ADDRESS_KEY = "newAdd";
    final String WEIGHT_KEY = "count";
    final String PRICE_KEY = "total";
    final String VENDOR_ID_KEY = "vendorId";
    final String ORDER_ARRAY_KEY = "basket";
    final String COUPON_STATUS_KEY = "coupon_status";
    final String COUPON_KEY = "coupon_code";


    List<AddressPojo> addressPojoList;
    ProgressBar otpProgress;
    AppCompatButton sendOTP, checkOTP;
    AppCompatEditText mobilechange, OTP;
    DbHelper db;
    String vendorIDCat1, vendorIDCat2, vendorIDCat3, vendorIDCat4;
    List<SabziDetailsPojo> list = new ArrayList<>();

    AppCompatButton confirmButton;
    EditText address, mobile;
    TextView email, name, addHead, nameHead, conHead, mailHead;
    ImageView editContact, editAddress;
    boolean editContactFlag = false, editAddressFlag = false;
    SharedPreferences preferences;
    TextView resendOTP, mobileShow;
    ProgressBar confirmProgress;
    AppCompatTextView amount;
    String USERNAME, EMAIL, CONTACT, ADDRESS, PREF_USER_ID;
    Dialog dialog;
    private String add;
    private Typeface ssp, ubu;
    boolean orderConfirmed;
    boolean paymentType; ///if true then cah else paytm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        setContentView(R.layout.activity_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
orderConfirmed=false;
        paymentType=false;
        ssp = Typeface.createFromAsset(getAssets(),
                "ssp.ttf");
        ubu = Typeface.createFromAsset(getAssets(),
                "ubu.ttf");
        db = new DbHelper(this);
        USERNAME = getResources().getString(R.string.SP_USER_NAME);
        EMAIL = getResources().getString(R.string.SP_USER_EMAIL);
        ADDRESS = getResources().getString(R.string.SP_USER_ADDRESS);
        CONTACT = getResources().getString(R.string.SP_USER_CONTACT);
        PREF_USER_ID = getResources().getString(R.string.SP_USER_ID);

        addressPojoList=db.fetchALLAddressData(db);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        confirmButton = (AppCompatButton) findViewById(R.id.confirmbutton);
        editContact = (ImageView) findViewById(R.id.editContact);
        editAddress = (ImageView) findViewById(R.id.editAddress);
        address = (EditText) findViewById(R.id.address);
        name = (TextView) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (TextView) findViewById(R.id.email);
        amount = (AppCompatTextView) findViewById(R.id.amount);
        confirmProgress = (ProgressBar) findViewById(R.id.confirmProgress);

        conHead = (TextView) findViewById(R.id.conHead);
        mailHead = (TextView) findViewById(R.id.mailHead);
        nameHead = (TextView) findViewById(R.id.nameHead);
        addHead = (TextView) findViewById(R.id.addHead);

        //conHead.setTypeface(ssp);
        //mailHead.setTypeface(ssp);
        name.setTypeface(ssp);
        //addHead.setTypeface(ssp);
        confirmButton.setTypeface(ssp);
        address.setTypeface(ubu);
        name.setTypeface(ubu);
        mobile.setTypeface(ubu);
        email.setTypeface(ubu);
        amount.setTypeface(ubu);

        setTotalPayableAmount();

        name.setText(preferences.getString(USERNAME, ""));
        email.setText(preferences.getString(EMAIL, ""));
        address.setText(preferences.getString(ADDRESS, ""));
        mobile.setText(preferences.getString(CONTACT, ""));

        vendorIDCat1 = String.valueOf(preferences.getInt(getResources().getString(R.string.SP_VENDOR_CATEGORY_1), 0));
        vendorIDCat2 = String.valueOf(preferences.getInt(getResources().getString(R.string.SP_VENDOR_CATEGORY_2), 0));
        vendorIDCat3 = String.valueOf(preferences.getInt(getResources().getString(R.string.SP_VENDOR_CATEGORY_3), 0));
        vendorIDCat4 = String.valueOf(preferences.getInt(getResources().getString(R.string.SP_VENDOR_CATEGORY_4), 0));

        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Rashan",preferences.getInt(getResources().getString(R.string.SP_SUBAREA_ID),0)+"");
              //  Log.v("Rashan",db.fetchAddressData(db,preferences.getInt("USER_SUBAREA_ID",0)));
                for (AddressPojo i: db.fetchAddressData(db,preferences.getInt("USER_SUBAREA_ID",0))) {
                  Log.v("Rashan", i.getName() + i.getSubarea());

              }
                  if (!editAddressFlag) {
                    address.setEnabled(true);
                    editAddressFlag = true;
                } else {
                    address.setEnabled(false);
                    editAddressFlag = false;
                }
            }
        });

        editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(ConfirmOrderActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.otp_dialog);


                sendOTP = (AppCompatButton) dialog.findViewById(R.id.sendOTP);
                checkOTP = (AppCompatButton) dialog.findViewById(R.id.checkOTP);
                mobilechange = (AppCompatEditText) dialog.findViewById(R.id.mobile);
                OTP = (AppCompatEditText) dialog.findViewById(R.id.OTP);
                otpProgress = (ProgressBar) dialog.findViewById(R.id.otpProgress);
                mobileShow = (TextView) dialog.findViewById(R.id.mobileShow);
                resendOTP = (TextView) dialog.findViewById(R.id.resendOTP);

                sendOTP.setTypeface(ssp);
                mobilechange.setTypeface(ubu);
                OTP.setTypeface(ubu);
                checkOTP.setTypeface(ssp);
                resendOTP.setTypeface(ssp);
                resendOTP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isNetworkAvailable()) {
                            new SendOTPTask().execute(mobileShow.getText().toString(), String.valueOf(preferences.getInt(getResources().getString(R.string.SP_USER_ID), 0)));
                            Toast.makeText(ConfirmOrderActivity.this, "OTP sent again", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ConfirmOrderActivity.this, "No Network Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                sendOTP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mobilechange.getText().toString().equals("")) {
                            Toast.makeText(ConfirmOrderActivity.this, "Please Enter A Mobile No. First", Toast.LENGTH_SHORT).show();
                        } else {
                            if (mobilechange.getText().length() < 10 || mobilechange.getText().length() > 10) {
                                Toast.makeText(ConfirmOrderActivity.this, "Enter Valid Mobile No.", Toast.LENGTH_SHORT).show();
                            } else {
                                if (isNetworkAvailable()) {
                                    SendOTPTask sendOTPTask = new SendOTPTask();
                                    sendOTPTask.execute(mobilechange.getText().toString(), String.valueOf(preferences.getInt(getResources().getString(R.string.SP_USER_ID), 0)));
                                    sendOTP.setVisibility(View.GONE);
                                    mobileShow.setText(mobilechange.getText());
                                    mobilechange.setVisibility(View.GONE);
                                    mobileShow.setVisibility(View.VISIBLE);
                                    OTP.setVisibility(View.VISIBLE);
                                    checkOTP.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(ConfirmOrderActivity.this, "No Network found", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }
                });

                checkOTP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (OTP.getText().toString().equals("")) {
                            Toast.makeText(ConfirmOrderActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                        } else {
                            if (isNetworkAvailable()) {
                                CheckOTPTask checkOTPTask = new CheckOTPTask();
                                checkOTPTask.execute(OTP.getText().toString(), String.valueOf(preferences.getInt(getResources().getString(R.string.SP_USER_ID), 0)));
                                checkOTP.setVisibility(View.GONE);
                                otpProgress.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(ConfirmOrderActivity.this, "No Network Found", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });

                dialog.setCancelable(true);
                dialog.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (mobile.getText().toString().equals("")) {
                    Toast.makeText(ConfirmOrderActivity.this, "Please Enter A Mobile No.", Toast.LENGTH_SHORT).show();
                } else*/ if (address.getText().toString().equals("")) {
                    Toast.makeText(ConfirmOrderActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                } else if (isNetworkAvailable() || true) {
                    confirmProgress.setVisibility(View.VISIBLE);
                    confirmButton.setVisibility(View.GONE);
                    list = db.fetchALLCartData(db);

                    final JSONArray orderArray = new JSONArray();
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject sabziObject = new JSONObject();
                        try {
                            float price = list.get(i).getWeight() * list.get(i).getCost();
                            sabziObject.put(SABZI_ID_KEY, list.get(i).getName());
                            sabziObject.put(WEIGHT_KEY, list.get(i).getWeight());
                            sabziObject.put(PRICE_KEY, price);
                            if (list.get(i).getCategory() == 1) {
                                sabziObject.put(VENDOR_ID_KEY, vendorIDCat1);
                            } else if (list.get(i).getCategory() == 2) {
                                sabziObject.put(VENDOR_ID_KEY, vendorIDCat2);
                            } else if (list.get(i).getCategory() == 3) {
                                sabziObject.put(VENDOR_ID_KEY, vendorIDCat3);
                            } else if (list.get(i).getCategory() == 4) {
                                sabziObject.put(VENDOR_ID_KEY, vendorIDCat4);
                            }
                            orderArray.put(sabziObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    final JSONObject fullOrderObject = new JSONObject();
                    add = address.getText().toString();
                    try {
                        fullOrderObject.put(USER_ID_KEY, preferences.getInt(PREF_USER_ID, 0));
                        fullOrderObject.put(ADDRESS_KEY, address.getText().toString());
                        if (getIntent().getBooleanExtra("status", false)) {
                            fullOrderObject.put(COUPON_STATUS_KEY, "yes");
                        } else {
                            fullOrderObject.put(COUPON_STATUS_KEY, "no");
                        }
                        fullOrderObject.put(COUPON_KEY, getIntent().getStringExtra("coupon"));
                        fullOrderObject.put(PLACE_CODE_KEY, preferences.getInt(getResources().getString(R.string.SP_SUBAREA_ID), 0));
                        fullOrderObject.put("timeslot",getIntent().getIntExtra("timeslot",-2));
                        fullOrderObject.put(ORDER_ARRAY_KEY, orderArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    new AsyncTask<Void, Void, Void>() {

                        String result, base_url = getResources().getString(R.string.confirm_orders_script);

                        @Override
                        protected Void doInBackground(Void... voids) {
                            try {
                                URL url = new URL(base_url);
                        throw new IOException();

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
                            if (result == null && false) {
                                Toast.makeText(ConfirmOrderActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
                                confirmProgress.setVisibility(View.GONE);
                                confirmButton.setVisibility(View.VISIBLE);
                            } else if (true ||result.equals("Ok") || (result.contains("Ok")) || (result.contains("ok"))) {
                              /*  LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.order_confirm_toast,
                                        (ViewGroup) findViewById(R.id.toastLayout));
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();
                                Toast.makeText(ConfirmOrderActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();*/
                                orderConfirmed();

                            } else {
                                Toast.makeText(ConfirmOrderActivity.this, "Error Occurred! Please Try Again", Toast.LENGTH_LONG).show();
                                confirmProgress.setVisibility(View.GONE);
                                confirmButton.setVisibility(View.VISIBLE);
                            }
                        }
                    }.execute();
                } else {
                    Toast.makeText(ConfirmOrderActivity.this, "Network Issue! Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void orderConfirmed() {
        orderConfirmed=true;


        db.deleteAllCartData(db);
        setContentView(R.layout.order_confirm);
        TextView confiirmOrder, confirmText, amountConfirm;
        Button close;
        confiirmOrder = (TextView) findViewById(R.id.confirmOrder);
        confirmText = (TextView) findViewById(R.id.confirmText);
        close = (Button) findViewById(R.id.close);
        amountConfirm = (TextView) findViewById(R.id.amountConfirm);

        for (int i = 0; i < list.size(); i++) {
            String s = (i + 1) + ") " + list.get(i).getName() + "  " + list.get(i).getWeight() + " Kgs" + System.getProperty("line.separator");
            confiirmOrder.append(s);
        }
        amountConfirm.setTypeface(ssp);
        confiirmOrder.setTypeface(ssp);
        confirmText.setTypeface(ssp);
        close.setTypeface(ssp);

        amountConfirm.append(String.valueOf(getIntent().getFloatExtra("value", 0)));
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  db.deleteAllCartData(db);*/

                Intent intent=new Intent(getApplicationContext(),ZSabziActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);


                finish();
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void setTotalPayableAmount() {
        DbHelper dbHelper = new DbHelper(this);
        if (dbHelper.checkCartAllData(dbHelper)) {
            List<SabziDetailsPojo> list = dbHelper.fetchALLCartData(dbHelper);
            float cartAmount = 0.0f;
            for (int i = 0; i < list.size(); i++) {
                float value = list.get(i).getCost() * list.get(i).getWeight();
                cartAmount = cartAmount + value;
            }
            amount.append(String.valueOf(getIntent().getFloatExtra("value", cartAmount)));
        } else {
            amount.append(String.valueOf(0.0f));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(orderConfirmed)
        {
            Intent intent=new Intent(getApplicationContext(),ZSabziActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        }
        return true;
    }



    public class SendOTPTask extends AsyncTask<String, Void, Void> {

        final String MOBILE_NO_KEY = "newPhone";
        String mobile, id;
        String base_url = getResources().getString(R.string.send_otp_script);

        @Override
        protected Void doInBackground(String... params) {

            mobile = params[0];
            id = params[1];
            try {
                URL url = new URL(base_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                String data = URLEncoder.encode(MOBILE_NO_KEY, "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8") + "&" +
                        URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = bufferedReader.readLine();
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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    resendOTP.setVisibility(View.VISIBLE);
                }
            }, 8000);

        }
    }


    public class CheckOTPTask extends AsyncTask<String, Void, String> {

        final String OTP_KEY = "otp";
        String OTP, result, id;
        String base_url = getResources().getString(R.string.check_otp_script);

        @Override
        protected String doInBackground(String... params) {

            OTP = params[0];
            id = params[1];
            try {
                URL url = new URL(base_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                String data = URLEncoder.encode(OTP_KEY, "UTF-8") + "=" + URLEncoder.encode(OTP, "UTF-8") + "&" +
                        URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = bufferedReader.readLine();
                bufferedReader.close();

                result = line.trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String param) {
            super.onPostExecute(param);

            if (result.equals("OK")) {
                mobile.setText(mobilechange.getText());
                dialog.dismiss();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong OTP", Toast.LENGTH_SHORT).show();
                otpProgress.setVisibility(View.GONE);
                checkOTP.setVisibility(View.VISIBLE);

            }


        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        int id=item.getItemId();
        if(id==-1)
        {
            name.setText(preferences.getString(USERNAME, ""));
           address.setText(preferences.getString(ADDRESS, ""));
            return true;
        }
        AddressPojo addressPojo=addressPojoList.get(id);
        name.setText(addressPojo.getName());
        address.setText(addressPojo.getAddress1()+", "+addressPojo.getAddress2()+", "+addressPojo.getSubarea()+", "+addressPojo.getArea());


        return true;
    }

    public  void showAddressPopup(View view)
    {
        PopupMenu popupMenu=new PopupMenu(ConfirmOrderActivity.this,view);
        /*MenuInflater menuInflater=popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.address_popup,popupMenu.getMenu());*/

popupMenu.setOnMenuItemClickListener(ConfirmOrderActivity.this);

        popupMenu.getMenu().add(Menu.NONE, -1, Menu.NONE,"Default");

        for(int i=0;i<addressPojoList.size();i++)
        {
            AddressPojo addressPojo=addressPojoList.get(i);

            if (addressPojoList.get(i).getPlaceCode()==preferences.getInt(getResources().getString(R.string.SP_SUBAREA_ID),-1))
            {
                popupMenu.getMenu().add(Menu.NONE, i, Menu.NONE,addressPojo.getName()+"- "+addressPojo.getAddress1()+", "+ addressPojo.getAddress2()+", "+ addressPojo.getSubarea()+", "+ addressPojo.getArea());
            }
        }



        popupMenu.show();



    }

    public void paymentType(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        Log.v("Rashan",checked+"");

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.cod:
                paymentType=true;
                break;
            case R.id.pod:
                paymentType=false;
                break;

        }
    }
}

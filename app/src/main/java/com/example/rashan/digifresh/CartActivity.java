package com.example.rashan.digifresh;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CartActivity extends AppCompatActivity {

    List<SabziDetailsPojo> cartList = new ArrayList<>();
    List<SabziDetailsPojo> sabziList = new ArrayList<>();
    RecyclerView cartRecycler;
    Button proceedCart, applyCoupon;
    AppCompatTextView totalValue;
    TextView noCartItem;
   // EditText couponText;
    float totalAmount;
    CartAdapter cartAdapter;
    DbHelper db;
    Typeface ssp;
    Intent intent;
    Dialog progressDialog;
    ImageView progressImage;
    int min = 0, max = 0, discount = 0;
    boolean deliverType; // used to check delivery type
    Handler mHandler;
    int timeslot;

Dialog dialog;
    RadioGroup radioGroup;
   // RadioGroup todayGroup,tomGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
deliverType=false;
        cartRecycler = (RecyclerView) findViewById(R.id.cartRecycler);
        totalValue = (AppCompatTextView) findViewById(R.id.totalValue);
        proceedCart = (Button) findViewById(R.id.proceedCart);
        noCartItem = (TextView) findViewById(R.id.noCartItem);
        db = new DbHelper(getApplicationContext());
        cartList = db.fetchALLCartData(db);
        sabziList = db.fetchALLSabziData(db);
      //  couponText = (EditText) findViewById(R.id.couponText);
     //   applyCoupon = (Button) findViewById(R.id.applyCoupon);
        progressDialog = new Dialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.loading);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressImage = (ImageView) progressDialog.findViewById(R.id.load);

        intent = new Intent(getApplicationContext(), ConfirmOrderActivity.class);
        intent.putExtra("status", false);
     //   intent.putExtra("coupon", couponText.getText());
        ssp = Typeface.createFromAsset(getAssets(),
                "ssp.ttf");

        totalValue.setTypeface(ssp);
        proceedCart.setTypeface(ssp);
        noCartItem.setTypeface(ssp);
       // applyCoupon.setTypeface(ssp);
timeslot=-2;
     //   applyCoupon.setEnabled(false);
      //  couponText.setEnabled(false);


        if (cartList.size() > 0) {
            cartAdapter = new CartAdapter(this, cartList, sabziList);
            cartRecycler.setAdapter(cartAdapter);
            cartRecycler.setLayoutManager(new LinearLayoutManager(this));
            noCartItem.setVisibility(View.GONE);
            cartRecycler.setVisibility(View.VISIBLE);
            proceedCart.setEnabled(true);
            //    applyCoupon.setEnabled(false);
       //     couponText.setEnabled(true);
        }
        dialog=new Dialog(CartActivity.this);
        //dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_time_slot);
        radioGroup = (RadioGroup)dialog.findViewById(R.id.radioGroup);
        // todayGroup=(RadioGroup)dialog.findViewById(R.id.todayGroup);
      //  tomGroup=(RadioGroup)dialog.findViewById(R.id.tomGroup);

      /*  RadioGroup.OnCheckedChangeListener radioListener=new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {



                Log.v("Rashan","!");


                if(i==R.id.todaym || i==R.id.todaya || i==R.id.todaye || i==R.id.tomm || i==R.id.toma || i==R.id.tome)
                {
                    Log.v("Rashan","!!!");

                   // ((RadioButton)dialog.findViewById(i)).setChecked(true);
                }



            }
        };

        todayGroup.setOnCheckedChangeListener(radioListener);
        tomGroup.setOnCheckedChangeListener(radioListener);*/



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.v("Rashan","HEE");
              //  radioGroup.clearCheck();
                switch (checkedId)
                {
                    case R.id.todaym: timeslot=1; break;
                    case R.id.todaya: timeslot=2;break;
                    case R.id.todaye: timeslot=3;break;
                    case R.id.tomm: timeslot=4; break;
                    case R.id.toma: timeslot=5; break;
                    case R.id.tome: timeslot=6; break;
                    default: timeslot=-2;
                }

             //   ((RadioButton)radioGroup.findViewById(checkedId)).setChecked(true);
            }
        });



        proceedCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("value", totalAmount);
                intent.putExtra("timeslot",1);
                startActivity(intent);
               // cartProceed();


            }
        });




    /*    couponText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                applyCoupon.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().equals("")) {
                    applyCoupon.setEnabled(false);
                } else {
                    applyCoupon.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        applyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                couponText.setEnabled(false);
                final String couponCode = couponText.getText().toString();
                if (isNetworkAvailable()) {
                    new AsyncTask<String, Void, Void>() {

                        String base_url = getResources().getString(R.string.coupon_script);
                        String result;

                        @Override
                        protected Void doInBackground(String... strings) {
                            try {
                                URL url = new URL(base_url);
                                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                httpURLConnection.setRequestMethod("POST");
                                httpURLConnection.setDoOutput(true);

                                OutputStream outputStream = httpURLConnection.getOutputStream();
                                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                                String data = URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(couponCode, "UTF-8");

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
                            if (result == null) {
                                Toast.makeText(CartActivity.this, "Error Occured! Try Again", Toast.LENGTH_SHORT).show();
                                couponText.setEnabled(true);
                                intent.putExtra("status", false);
                                intent.putExtra("coupon", couponText.getText());
                            } else if (result.equals("not")) {
                                min = 0;
                                max = 0;
                                discount = 0;
                                Toast.makeText(CartActivity.this, "Invalid Coupon Code", Toast.LENGTH_SHORT).show();
                                couponText.setEnabled(true);
                                intent.putExtra("status", false);
                                intent.putExtra("coupon", couponText.getText());
                            } else {
                                try {
                                    JSONArray jsonArray = new JSONArray(result);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    if (jsonObject.getString("status").equals("ok")) {

                                        min = jsonObject.getInt("min");
                                        max = jsonObject.getInt("max");
                                        discount = jsonObject.getInt("discount");

                                        if (totalAmount >= jsonObject.getInt("min")) {
                                            updateTotalValue(totalAmount);
                                            intent.putExtra("status", true);
                                            intent.putExtra("coupon", couponText.getText());
                                            cartRecycler.setEnabled(false);
                                            Toast.makeText(CartActivity.this, "Coupon Applied", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(CartActivity.this, "Code not Valid on this amount", Toast.LENGTH_SHORT).show();
                                            couponText.setEnabled(true);
                                            intent.putExtra("status", false);
                                            intent.putExtra("coupon", couponText.getText());
                                        }
                                    } else {
                                        min = 0;
                                        max = 0;
                                        discount = 0;
                                        Toast.makeText(CartActivity.this, "Invalid Coupon Code", Toast.LENGTH_SHORT).show();
                                        couponText.setEnabled(true);
                                        intent.putExtra("status", false);
                                        intent.putExtra("coupon", couponText.getText());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }.execute(couponCode);
                } else {
                    Toast.makeText(CartActivity.this, "Network Issue! Try Again", Toast.LENGTH_SHORT).show();
                    couponText.setEnabled(true);
                    intent.putExtra("status", false);
                    intent.putExtra("coupon", couponText.getText());
                }
            }
        });*/



        dialog.findViewById(R.id.proceed).setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                intent.putExtra("value", totalAmount);
                intent.putExtra("timeslot",timeslot);
                startActivity(intent);

                dialog.dismiss();
            }





        });
    }

    public void cartProceed()
    {




        try {

            if (isNetworkAvailable()) {

                progressDialog.show();
                ((AnimationDrawable) progressImage.getBackground()).start();
              // new TimeTask(CartActivity.this,dialog,progressDialog).execute();




            } else {
                throw new NullPointerException();
            }
        }
        catch (Exception e)
        {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }



    public void deliveryType(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        Log.v("Rashan",checked+"");

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.normaldeliv:
                deliverType=false;
                break;
            case R.id.expressdeliv:
                deliverType=true;
                break;

        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(dialog.isShowing()==true)
        {
            Log.v("Rashan","DONE");

            mHandler.removeCallbacksAndMessages(null);
        }
        finish();
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
    }

    public void updateList() {
        cartList = db.fetchALLCartData(db);
        cartAdapter.notifyDataSetChanged();
    }

    public void updateTotalValue(float cost) {
        totalAmount = cost;
        float dis;
        String txt;

        if (totalAmount >= min && discount != 0) {

            dis = (totalAmount * discount) / 100;
            if (dis > max) {
                totalAmount = totalAmount - max;
                txt = "Total Cart Value : " + String.valueOf(totalAmount);
            } else {
                totalAmount = totalAmount - dis;
                txt = "Total Cart Value : " + String.valueOf(totalAmount);
            }
        } else {
            txt = "Total Cart Value : " + String.valueOf(cost);

        }
        totalValue.setText(txt);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        }
        return true;
    }






}
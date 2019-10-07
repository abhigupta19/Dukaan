package com.example.rashan.digifresh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

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

public class EditProfileActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String USERNAME, EMAIL, CONTACT, ADDRESS, GENDER, GP;

    TextInputEditText update_username, update_email, update_contact, update_address;
    MaterialBetterSpinner update_gender, update_gp;
    ProgressBar updateProgress;
    String[] gender = {"Male", "Female", "Other"}, gp = {"Male", "Female", "NP"};
    String selectedGender, selectedGP;
    AppCompatButton updateProfile;
    private Typeface ssp, ubu;
    RecyclerView addressRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile Details");
        ssp = Typeface.createFromAsset(getAssets(),
                "ssp.ttf");
        ubu = Typeface.createFromAsset(getAssets(),
                "ubu.ttf");







       /* addressRecyclerView=(RecyclerView)findViewById(R.id.addressRecyclerView);
        List<AddressPojo> list=new ArrayList<>();
        list.add(new AddressPojo("PAS","K","K",1));
        AddressAdapter addressAdapter = new AddressAdapter(this,list);
        addressRecyclerView.setAdapter(addressAdapter);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressRecyclerView.setVisibility(View.VISIBLE);*/


        USERNAME = getResources().getString(R.string.SP_USER_NAME);
        EMAIL = getResources().getString(R.string.SP_USER_EMAIL);
        ADDRESS = getResources().getString(R.string.SP_USER_ADDRESS);
        CONTACT = getResources().getString(R.string.SP_USER_CONTACT);
        GENDER = getResources().getString(R.string.SP_USER_GENDER);
        GP = getResources().getString(R.string.SP_GENDER_PREFERENCE);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        updateProgress = (ProgressBar) findViewById(R.id.updateProgress);
        update_username = (TextInputEditText) findViewById(R.id.update_username);
        update_email = (TextInputEditText) findViewById(R.id.update_email);
        update_address = (TextInputEditText) findViewById(R.id.update_address);
        update_contact = (TextInputEditText) findViewById(R.id.update_contact);
        update_gender = (MaterialBetterSpinner) findViewById(R.id.update_gender);
        update_gp = (MaterialBetterSpinner) findViewById(R.id.update_gp);
        updateProfile = (AppCompatButton) findViewById(R.id.updateProfile);


       // update_contact.setEnabled(false);

        updateProfile.setTypeface(ssp);
        update_username.setTypeface(ssp);
        update_address.setTypeface(ssp);
        update_contact.setTypeface(ubu);/*
        update_gender.setTypeface(ssp);
        update_gp.setTypeface(ssp);*/

        update_username.setText(preferences.getString(USERNAME, ""));
        update_email.setText(preferences.getString(EMAIL, ""));
        update_address.setText(preferences.getString(ADDRESS, ""));
        update_contact.setText(preferences.getString(CONTACT, ""));
        update_gender.setAdapter(new ArrayAdapter<String>(EditProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, gender));
        update_gp.setAdapter(new ArrayAdapter<String>(EditProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, gp));
        selectedGender = gender[0];
        selectedGP = gp[2];
        String name = preferences.getString(getResources().getString(R.string.SP_USER_NAME), "");
        name = name.trim().toUpperCase();

        update_gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGender = gender[i];
            }
        });

        update_gp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGP = gp[i];
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  throw new RuntimeException("HEX");
                updateProfile.setVisibility(View.GONE);
                updateProgress.setVisibility(View.VISIBLE);
                new EditTask().execute(update_username.getText().toString().trim(),
                        update_address.getText().toString().trim(),
                        update_contact.getText().toString().trim(),
                        selectedGender,
                        selectedGP,
                        String.valueOf(preferences.getInt(getResources().getString(R.string.SP_USER_ID), 0)));
            }
        });

    }



    public void addAddress(View view)
    {

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
    }

    public class EditTask extends AsyncTask<String, Void, Void> {

        String base_url ="http://www.vegvendors.in/android/profileUpdateDetails.php";
        String result;

        @Override
        protected Void doInBackground(String... params) {

            try {
                URL url = new URL(base_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                String str=preferences.getString("cookiedetails","-1");
                httpURLConnection.setRequestProperty("Cookie",str);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") +
                        "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") +
                        "&" + URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") +
                        "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") +
                        "&" + URLEncoder.encode("gp", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") +
                        "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8");

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
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                if (jsonObject.getString("status").equals("ok")) {
                    editor.putString(USERNAME, jsonObject.getString("name"));
                    editor.putString(CONTACT, jsonObject.getString("contact"));
                    editor.putString(ADDRESS, jsonObject.getString("address"));
                    editor.putString(GENDER, jsonObject.getString("gender"));
                    editor.putString(GP, jsonObject.getString("gp"));
                    editor.commit();
                    Toast.makeText(EditProfileActivity.this, "Profile Successfully Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileActivity.this, ZSabziActivity.class);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(EditProfileActivity.this, "Oops!Some error occurred Please Try Again", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            updateProgress.setVisibility(View.GONE);
            updateProfile.setVisibility(View.VISIBLE);
        }
    }
}

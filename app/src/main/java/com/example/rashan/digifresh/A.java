package com.example.rashan.digifresh;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class A extends AsyncTask<String,Void,Void>

{
    SharedPreferences preferences;
    Activity activity;
    A(Activity activity)
    {
        this.activity=activity;
        preferences= PreferenceManager.getDefaultSharedPreferences(activity);

    }

    @Override
    protected Void doInBackground(String... voids) {
        uploadFile(voids[0]);
        return null;
    }

    public byte[] read(File file) throws Exception{


        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            if (ios.read(buffer) == -1) {
                throw new IOException(
                        "EOF reached while trying to read the whole file");
            }
        } finally {
            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return buffer;
    }
    public void uploadFile(String pathToOurFile) {


        String urlServer = "http://androidphphit.000webhostapp.com/upload1.php";

        try {
            URL url = new URL(urlServer);

            String data= Base64.encodeToString(read(new File(pathToOurFile)),Base64.DEFAULT);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);


            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));


            JSONObject jsonObject=new JSONObject();
//            jsonObject.put("f1",data);
//            jsonObject.put("name","AEYYYYY MUKUL");
//            jsonObject.put("filename","myFile");
            JSONArray jsonArray=new JSONArray();
            jsonArray.put(jsonObject);
            JsonObject jsonObject1;
           /* JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("f1",data);
            jsonObject.addProperty("name","AEYYYYY MUKUL");
            jsonObject.addProperty("filename","myFile");*/
            data= URLEncoder.encode("myjson", "UTF-8") + "=" + URLEncoder.encode(jsonObject.toString());
            //data= URLEncoder.encode("myjson", "UTF-8") + "=" + URLEncoder.encode(jsonArray.toString(), "UTF-8");
            /*data = URLEncoder.encode("f1", "UTF-8") + "=" + URLEncoder.encode(data, "UTF-8")+
                    "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode("AE MUKUL", "UTF-8")+
                    "&" + URLEncoder.encode("filename", "UTF-8") + "=" + URLEncoder.encode("myfile", "UTF-8");*/
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String result = bufferedReader.readLine();
            bufferedReader.close();
          //  Toast.makeText(activity,"DONE",Toast.LENGTH_LONG).show();


        } catch (Exception ex) {
            //Exception handling
            ex.printStackTrace();
         //   Toast.makeText(activity," Not DONE",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        Log.v("Rashan","HELLOOOOOO");
    }
}
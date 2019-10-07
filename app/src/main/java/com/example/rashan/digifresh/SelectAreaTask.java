package com.example.rashan.digifresh;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

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

/**
 * Created by Rashan on 31-12-2016.
 */
public class SelectAreaTask extends AsyncTask<Void, Void, Void> {

Activity activity;
    int id;
    SharedPreferences preferences;
    Object object;
    String subArea;
    SelectAreaTask(Activity activity, int id, String subArea, Object object)
    {
        this.subArea="";
        this.object=null;
        this.subArea=subArea;
        this.activity=activity;
        this.id=id;
        this.object=object;
        preferences= PreferenceManager.getDefaultSharedPreferences(activity);
    }


    String result;

    @Override
    protected Void doInBackground(Void... strings) {

        //selectedArea = preferences.getString(SP_AREA, "");

        try {
           // URL url = new URL(preferences.getString(activity.getResources().getString(R.string.area_selector_script),""));
           URL url = new URL("http://www.vegvendors.in/android/mainAreaJson.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            //String data = URLEncoder.encode(SELECT_AREA_TASK_POST_AREA_KEY, "UTF-8") + "=" + URLEncoder.encode(selectedArea, "UTF-8");

            bufferedWriter.write("");
            bufferedWriter.flush();
            bufferedWriter.close();

            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

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
//        Log.v("Rashan",result);









if (result==null)
{
    result="";
}






        if(id==1)
        {
            ((ZSabziActivity)activity).postSelectAreaTask(result);
        }
        else if(id==2)
        {
          //  ((AddressDialog)object).postAreaTask(result,subArea,false,null);
        }


    }
}
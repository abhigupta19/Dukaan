package com.example.rashan.digifresh;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rashan on 10-01-2017.
 */
public class AreaSpinnerAdapter extends ArrayAdapter<String> {

    Activity activity;
    List<AreaPojo> pojoList;



    AreaSpinnerAdapter(Activity activity, List<AreaPojo> pojoList)
    {
        super(activity,-1);
        this.activity=activity;
        this.pojoList=pojoList;
    }

    @Override
    public int getCount() {
        return pojoList.size();
    }



    @Override
    public String getItem(int i) {


        return pojoList.get(i).getName();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View a;
        if(convertView == null) {
            a =  LayoutInflater.from(activity).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup, false);

        }
        else {
            a = convertView;
        }
        Log.v("Rashan",((TextView)a.findViewById(android.R.id.text1)).getText().toString());
        ((TextView)a.findViewById(android.R.id.text1)).setText(pojoList.get(i).getName());
      //  View a = LayoutInflater.from(activity).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup, false);
        //((TextView)a.findViewById(android.R.id.text1)).setText(pojoList.get(i).getName());
        return a;
    }

    public AreaPojo getPojo(int i)
    {
        return pojoList.get(i);
    }

    public View getDropDownView(int i, View convertView, ViewGroup viewGroup) {
        View a;
        if(convertView == null) {
            a =  LayoutInflater.from(activity).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup, false);

        }
        else {
            a = convertView;
        }
        Log.v("Rashan",((TextView)a.findViewById(android.R.id.text1)).getText().toString());
        ((TextView)a.findViewById(android.R.id.text1)).setText(pojoList.get(i).getName());
        //  View a = LayoutInflater.from(activity).inflate(R.layout.support_simple_spinner_dropdown_item, viewGroup, false);
        //((TextView)a.findViewById(android.R.id.text1)).setText(pojoList.get(i).getName());
        return a;
    }
}

package com.example.rashan.digifresh;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rashan on 04-01-2017.
 */
public class PrevOrderAdapter extends BaseAdapter {

    Activity activity;
    List<PreviousSubOrderPojo> subList;
    List<PreviousOrderPojo> list;

    private final Typeface ssp, ubu;
    DbHelper db;
SharedPreferences preferences;
    ImageView status, confirm_pic, details;
    TextView order_id, cost;
    ImageView cancel,cancelled;


    RecyclerView recyclerView;

    public PrevOrderAdapter(Activity activity, List<PreviousOrderPojo> list, List<PreviousSubOrderPojo> subList)
    {

        this.activity=activity;
        this.list=list;
        this.subList=subList;
        preferences= PreferenceManager.getDefaultSharedPreferences(activity);
        db = new DbHelper(activity);
        ssp = Typeface.createFromAsset(activity.getAssets(),
                "ssp.ttf");
        ubu = Typeface.createFromAsset(activity.getAssets(),
                "ubu.ttf");

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PreviousOrderPojo getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        View itemView = LayoutInflater.from(activity).inflate(R.layout.previous_ordercard_layout1, viewGroup, false);
        cancelled=(ImageView)itemView.findViewById(R.id.cancelled);
        cancel=(ImageView)itemView.findViewById(R.id.cancel);
        status = (ImageView) itemView.findViewById(R.id.status);
        order_id = (TextView) itemView.findViewById(R.id.order_id);
        cost = (TextView) itemView.findViewById(R.id.cost);
        confirm_pic = (ImageView) itemView.findViewById(R.id.confirm_pic);
        details = (ImageView) itemView.findViewById(R.id.details);
        recyclerView=(RecyclerView)itemView.findViewById(R.id.subOrderRecycler);

        order_id.setTypeface(ubu);
        cost.setTypeface(ubu);


        String orderString = "Order ID : " + String.valueOf(list.get(position).getId());
        String costString = "Price : ₹ " + String.valueOf(list.get(position).getPrice());

//        Picasso.with(activity).load(list.get(position).getPic()).into(confirm_pic);
        order_id.setText(orderString);
        cost.setText(costString);
       /* if (list.get(position).getStatus()) {
            status.setImageResource(R.drawable.delivered);
        } else {
            status.setImageResource(R.drawable.pending);
        }*/
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).getStatus()==1){
                    Toast.makeText(activity, "  Delivered  ", Toast.LENGTH_SHORT).show();
                }else if(list.get(position).getStatus()==0){
                    Toast.makeText(activity, "  Pending  ", Toast.LENGTH_SHORT).show();
                }
                else if (list.get(position).getStatus()==-1)
                {
                    Toast.makeText(activity, "  Cancelled  ", Toast.LENGTH_SHORT).show();
                }
            }
        });



        final long idee = list.get(position).getId();
        List<PreviousSubOrderPojo> tempList = new ArrayList<PreviousSubOrderPojo>();

        for (int i = 0; i < subList.size(); i++) {
            if (subList.get(i).getId() == idee) {
                tempList.add(subList.get(i));
            }
        }
        recyclerView.setAdapter(new SubOrderAdapter(activity, tempList));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

       // recyclerView.bringToFront();


        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long id = list.get(position).getId();
                String s_id = "Order ID : " + id;
                Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.suborder_dialog);

                RecyclerView subOrderRecycler = (RecyclerView) dialog.findViewById(R.id.subOrderRecycler);
                TextView orderID = (TextView) dialog.findViewById(R.id.orderID);
                orderID.setTypeface(ssp);
                orderID.setText(s_id);
                final RatingBar ratingVendor = (RatingBar) dialog.findViewById(R.id.ratingVendor);
                List<PreviousSubOrderPojo> tempList = new ArrayList<PreviousSubOrderPojo>();

                for (int i = 0; i < subList.size(); i++) {
                    if (subList.get(i).getId() == id) {
                        tempList.add(subList.get(i));
                    }
                }
                ratingVendor.setRating(list.get(position).getRating());
                ratingVendor.setStepSize(1.0f);
                ratingVendor.setIsIndicator(false);
                subOrderRecycler.setAdapter(new SubOrderAdapter(activity, tempList));
                subOrderRecycler.setLayoutManager(new LinearLayoutManager(activity));

                ratingVendor.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        final int count = (int) ratingVendor.getRating();
                        final long orderId = id;
                        if (isNetworkAvailable()) {
                            new AsyncTask<Void, Void, Void>() {

                                String result, base_url = activity.getResources().getString(R.string.rating_script);

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    try {
                                        URL url = new URL(base_url);
                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoOutput(true);

                                        OutputStream outputStream = httpURLConnection.getOutputStream();
                                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                                        String data = URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(preferences.getInt("USER_ID", 0)), "UTF-8") +
                                                "&" + URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(count), "UTF-8") +
                                                "&" + URLEncoder.encode("order_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(orderId), "UTF-8");

                                        bufferedWriter.write(data);
                                        bufferedWriter.flush();
                                        bufferedWriter.close();

                                        InputStream inputStream = httpURLConnection.getInputStream();
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                                        result = bufferedReader.readLine();
                                        if (result != null) {
                                            result = result.trim();
                                        }
                                        bufferedReader.close();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);
                                }
                            }.execute();

                        } else {
                            Toast.makeText(activity, "Rating Not Submitted! No Internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });


        return itemView;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public List<PreviousSubOrderPojo> getSubOrderPojoList(long id)
    {

        List<PreviousSubOrderPojo> tempList = new ArrayList<PreviousSubOrderPojo>();
        for (int i = 0; i < subList.size(); i++) {
            if (subList.get(i).getId() == id) {
                tempList.add(subList.get(i));
            }
        }
        return  tempList;
    }

}

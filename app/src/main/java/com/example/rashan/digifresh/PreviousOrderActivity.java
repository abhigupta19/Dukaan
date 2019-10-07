package com.example.rashan.digifresh;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

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


public class PreviousOrderActivity extends AppCompatActivity {

    SharedPreferences preferences;
    int uid;
    String result;
    List<PreviousOrderPojo> list = new ArrayList<>();
    RecyclerView ordersRecycler;
    TextView noOrder;
    private ProgressBar subOrderProgress;
    private Typeface ssp;
    FloatingActionMenu fab_menu;
    boolean selectionStatus;
    int fab_id;
    ListView orderListView;
    Dialog dialog;
    Button yes,no;
    DbHelper db;

    Snackbar snackbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        setContentView(R.layout.activity_previous_order);
        db = new DbHelper(getApplicationContext());
snackbar= Snackbar.make(findViewById(R.id.prevOrderCoordinator),"Please Choose an Order for the selected task", Snackbar.LENGTH_INDEFINITE);

        selectionStatus=false;
        fab_id=-1;


        dialog = new Dialog(PreviousOrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.cancel_confirm_dialog);
        yes=(Button)dialog.findViewById(R.id.yes);
        no=(Button)dialog.findViewById(R.id.no);


        fab_menu=(FloatingActionMenu)findViewById(R.id.fab_menu);
        fab_menu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if(opened)
                {

                }
                else
                {
                    rest_all_fab(true);
                }

            }
        });




      /*  fab_menu.toggle();*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ssp = Typeface.createFromAsset(getAssets(), "ssp.ttf");

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        uid = preferences.getInt(getResources().getString(R.string.SP_USER_ID), 0);
        noOrder = (TextView) findViewById(R.id.noOrder);
        subOrderProgress = (ProgressBar) findViewById(R.id.subOrderProgress);
        ordersRecycler = (RecyclerView) findViewById(R.id.ordersRecycler);
        orderListView=(ListView)findViewById(R.id.orderListView);

        noOrder.setTypeface(ssp);


       orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onListItemClick(view,i);
           }
       });
        new PreviousOrderTask().execute();
    }


    public void fab_click(View view)
    {
        rest_all_fab(false);
        selectionStatus=true;
        ((FloatingActionButton)view).setColorNormalResId(R.color.colorPrimary);
        fab_id=view.getId();
        snackbar.show();
    }


    public void onListItemClick(View view,int position)
    {

        if(selectionStatus)
        {
            selectionStatus=false;
            fab_menu.toggle(true);
            snackbar.dismiss();
            if(fab_id==R.id.fabe1)
            {
                reorder(view,position);
            }
            else if(fab_id==R.id.fabe2)
            {

            }
            else if(fab_id==R.id.fabe3)
            {

            }
            else if(fab_id==R.id.fabe4)
            {
                cancelOrder(view,position);
            }


        }
    }
    public void rest_all_fab(boolean flag)
    {
        selectionStatus=false;
        if (flag)
        snackbar.dismiss();
        ((FloatingActionButton)findViewById(R.id.fabe1)).setColorNormalResId(R.color.Theme_elements);
        ((FloatingActionButton)findViewById(R.id.fabe2)).setColorNormalResId(R.color.Theme_elements);
        ((FloatingActionButton)findViewById(R.id.fabe3)).setColorNormalResId(R.color.Theme_elements);
        ((FloatingActionButton)findViewById(R.id.fabe4)).setColorNormalResId(R.color.Theme_elements);

    }

    public void reorder(View view,int position)
    {
        db.deleteAllCartData(db);//if cart data is to be removed
        long l=((PreviousOrderPojo)orderListView.getAdapter().getItem(position)).getId();
        List<PreviousSubOrderPojo> list=((PrevOrderAdapter)orderListView.getAdapter()).getSubOrderPojoList(l);
        List<SabziDetailsPojo> sabziDetailsPojoList=db.fetchALLSabziData(db);


        for(SabziDetailsPojo sabziDetailsPojo:sabziDetailsPojoList)
        {
            for(PreviousSubOrderPojo pojo:list)
            {
                if(sabziDetailsPojo.getId()==pojo.getSid())
                {
                    sabziDetailsPojo.setWeight(pojo.getQty());
                    db.insertCartData(db,sabziDetailsPojo);
                }
            }
        }
        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
    }

    public void cancelOrder(View view,final int position)
    {
        dialog.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  final long id = list.get(holder.gegetId();tAdapterPosition()).


                dialog.dismiss();
               // new CancelTask(PreviousOrderActivity.this).execute(String.valueOf(((PreviousOrderPojo)orderListView.getAdapter().getItem(position)).getId()));

                //     new CancelTask().execute(String.valueOf(list.get(position).getId()));


            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

       // new CancelTask(PreviousOrderActivity.this).execute(String.valueOf(((PreviousOrderPojo)orderListView.getAdapter().getItem(position)).getId()));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        }
        return super.onOptionsItemSelected(item);
    }

    public class PreviousOrderTask extends AsyncTask<Void, Void, Void> {

        List<PreviousOrderPojo> list = new ArrayList<>();
        List<PreviousSubOrderPojo> subList = new ArrayList<>();
        String base_url = getResources().getString(R.string.orders_script);

        @Override
        protected Void doInBackground(Void... strings) {


            try {
                URL url = new URL(base_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

                String data = URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(uid), "UTF-8");

                bufferedWriter.write(data);
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
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(result);
                if (jsonArray.getString(0).equals("ok")) {
                    for (int i = 1; i < jsonArray.length(); i++) {
                        float cost = 0.0f;
                        boolean statusFlag = true;
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        PreviousOrderPojo previousOrderPojo = new PreviousOrderPojo();
                        previousOrderPojo.setId(jsonObject.getInt("id"));

                        previousOrderPojo.setStatus(jsonObject.getInt("status"));

                        JSONArray subOrderJsonArray = jsonObject.getJSONArray("order");
                        for (int j = 0; j < subOrderJsonArray.length(); j++) {
                            PreviousSubOrderPojo previousSubOrderPojo = new PreviousSubOrderPojo();
                            JSONObject subOrderJsonObjects = subOrderJsonArray.getJSONObject(j);
                            previousSubOrderPojo.setId(jsonObject.getInt("id"));
                            String strings[]=db.fetchSabziInitData(db,subOrderJsonObjects.getInt("vegetableId"));
                            previousSubOrderPojo.setName(strings[1]);

                            previousSubOrderPojo.setSid(subOrderJsonObjects.getInt("vegetableId"));
                            previousSubOrderPojo.setQty(Float.valueOf(String.valueOf(subOrderJsonObjects.getDouble("qty"))));
                            previousSubOrderPojo.setPrice(Float.valueOf(String.valueOf(subOrderJsonObjects.getDouble("price"))));
                            cost = cost + Float.valueOf(String.valueOf(subOrderJsonObjects.getDouble("price")));


                           /* if (subOrderJsonObjects.getInt("status") == 0) {
                                statusFlag = false;
                            }*/
                            statusFlag=false;
                            ///



                            subList.add(previousSubOrderPojo);
                        }

                        previousOrderPojo.setPrice(cost);
                      /*  if (statusFlag) {
                            previousOrderPojo.setStatus(statusFlag);
                        } else {
                            previousOrderPojo.setStatus(statusFlag);
                        }*/

                        list.add(previousOrderPojo);
                    }

                    //ordersRecycler.setAdapter(new PreviousOrderAdapter(PreviousOrderActivity.this, list, subList));


                    ordersRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    ordersRecycler.setHasFixedSize(true);
                    subOrderProgress.setVisibility(View.GONE);
                    noOrder.setVisibility(View.GONE);
                    //ordersRecycler.setVisibility(View.VISIBLE);
                   // customFab.bringToFront();
                    orderListView.setAdapter(new PrevOrderAdapter(PreviousOrderActivity.this,list,subList));
                    orderListView.setVisibility(View.VISIBLE);
                } else {
                    subOrderProgress.setVisibility(View.GONE);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}

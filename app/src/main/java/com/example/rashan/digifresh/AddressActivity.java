package com.example.rashan.digifresh;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;


public class AddressActivity extends AppCompatActivity {

    RecyclerView addressRecyclerView;
    AppCompatButton save,cancel;
    EditText name,a1,a2;
    Spinner areaSpinner,subAreaSpinner;

    Dialog dialog;
    AddressAdapter addressAdapter;
    FloatingActionButton fab;
    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        setContentView(R.layout.activity_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
     /*   getSupportActionBar().setTitle("My Addresses");*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    fab=(FloatingActionButton)findViewById(R.id.fab);

        db=new DbHelper(getApplicationContext());

        /*db.insertAddressData(db,new AddressPojo(list.size(),"Rashan","170/6","PASCHIM VIHAR","DELHI"));*/
        addressRecyclerView=(RecyclerView)findViewById(R.id.addressRecyclerView);
         addressAdapter=new AddressAdapter(AddressActivity.this,db.fetchALLAddressData(db));
        addressRecyclerView.setAdapter(addressAdapter);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));










       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AddDialog addDialog=new AddDialog(AddressActivity.this,addressAdapter);
                addDialog.setDialog(false,null);
/*

                AddressDialog addressDialog=new AddressDialog(AddressActivity.this,addressAdapter);
                addressDialog.setDialog(false);
*/



            }
        });







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
        return true;
    }

}

package com.example.rashan.digifresh;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rashan on 19-10-2016.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyHolder> {


        List<AddressPojo> addressPojos = new ArrayList<>();
       // Context context;
    Activity activity;
        //float finalWeight, weightFactor, currentWeight;
        DbHelper db;
        Typeface ssp, ubu;
        SharedPreferences preferences;

    String[] arealist={"Pitampura"};String[] subarealist={"BB","DD","FF"};

        SharedPreferences.Editor editor;

        public AddressAdapter(Activity activity, List<AddressPojo> list) {
          //  this.context = context;
            this.activity=activity;
            this.addressPojos = list;
            db = new DbHelper(activity.getApplicationContext());
            ssp = Typeface.createFromAsset(activity.getAssets(),
                    "ssp.ttf");
            ubu = Typeface.createFromAsset(activity.getAssets(),
                    "ubu.ttf");
            preferences= PreferenceManager.getDefaultSharedPreferences(activity);


        }

        @Override
        public AddressAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_card_layout, parent, false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public int getItemCount() {
            return addressPojos.size();
        }

        @Override
        public void onBindViewHolder(final AddressAdapter.MyHolder holder,final int position) {



            holder.name.setText(addressPojos.get(position).getName());
            holder.id.setText(String.valueOf(addressPojos.get(position).getId()));
            holder.address1.setText(addressPojos.get(position).getAddress1());
            holder.address2.setText(addressPojos.get(position).getAddress2());
            holder.subArea.setText(addressPojos.get(position).getSubarea());
            holder.area.setText(addressPojos.get(position).getArea());
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AddDialog addDialog=new AddDialog(activity,AddressAdapter.this);
                    addDialog.setDialog(true,addressPojos.get(position));
                   // addDialog.putDetails(addressPojos.get(position));
                  /*  AddressDialog addressDialog=new AddressDialog(activity,AddressAdapter.this);
                    addressDialog.setDialog(true);
                    //addressDialog.putDetails(addressPojos.get(position));
                    addressDialog.postAreaTask("","",true,addressPojos.get(position));*/

                }
            });

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  AddressDialog addressDialog=new AddressDialog(activity,AddressAdapter.this);
                    addressDialog.delete(addressPojos.get(position).getId());*/
                    AddDialog addDialog=new AddDialog(activity,AddressAdapter.this);
                    addDialog.delete(addressPojos.get(position).getId());


                }
            });




//        String name = sabziDetailsPojos.get(position).getName().substring(0, sabziDetailsPojos.get(position).getName().indexOf("("));
           /* String cost = "₹ " + String.valueOf(sabziDetailsPojos.get(position).getCost()) + " /Kg";
            String name = sabziDetailsPojos.get(position).getName();

            holder.sabziname.setText(name);
            holder.sabzicost.setText(cost);
            holder.weight.setText(String.valueOf(sabziDetailsPojos.get(position).getWeight()));

*/


           // AnimationClass.recyclerAnimationSabzi(holder, true);

        }

        public class MyHolder extends RecyclerView.ViewHolder {


           TextView  name,address1,address2,subArea,area,id;
            ImageButton imageButton,deleteButton;

            public MyHolder(View itemView) {
                super(itemView);
             imageButton=(ImageButton)itemView.findViewById(R.id.imageButton);
                deleteButton=(ImageButton)itemView.findViewById(R.id.deleteButton);
                 id=(TextView)itemView.findViewById(R.id.idd);
                 address1=(TextView)itemView.findViewById(R.id.address1);
                address2=(TextView)itemView.findViewById(R.id.address2);
                name=(TextView) itemView.findViewById(R.id.name);
                subArea=(TextView)itemView.findViewById(R.id.subArea);
                area=(TextView)itemView.findViewById(R.id.area);
              //  buildingno.setTypeface(ssp);







            }
        }
    }



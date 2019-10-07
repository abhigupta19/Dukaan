package com.example.rashan.digifresh;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahendroo on 09-07-2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    List<SabziDetailsPojo> sabziDetailsPojos = new ArrayList<>();
    Activity context;
    float finalWeight, weightFactor, currentWeight;
    DbHelper db;
    Typeface ssp, ubu;
    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    public MyAdapter(Activity context, List<SabziDetailsPojo> list) {
        this.context = context;
        this.sabziDetailsPojos = list;
        db = new DbHelper(context.getApplicationContext());
        ssp = Typeface.createFromAsset(context.getAssets(),
                "ssp.ttf");
        ubu = Typeface.createFromAsset(context.getAssets(),
                "ubu.ttf");
       preferences=PreferenceManager.getDefaultSharedPreferences(context);


    }

    @Override
    public MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sabzilist_card_layout, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return sabziDetailsPojos.size();
    }

    @Override
    public void onBindViewHolder(final MyAdapter.MyHolder holder, int position) {

//        String name = sabziDetailsPojos.get(position).getName().substring(0, sabziDetailsPojos.get(position).getName().indexOf("("));
        String cost = "₹ " + String.valueOf(sabziDetailsPojos.get(position).getCost()) + " /Kg";
        String name = sabziDetailsPojos.get(position).getName();

        holder.sabziname.setText(name);
        holder.sabzicost.setText(cost);
        holder.weight.setText(String.valueOf(sabziDetailsPojos.get(position).getWeight()));

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightFactor = db.fetchSabziData(db, sabziDetailsPojos.get(holder.getAdapterPosition()).getId()).getWeight();
                weightFactor = Math.round(100 * weightFactor) / 100.0f;
                currentWeight = Float.valueOf(holder.weight.getText().toString());
                currentWeight = Math.round(100 * currentWeight) / 100.0f;
                finalWeight = weightFactor + currentWeight;
                finalWeight = Math.round(100 * finalWeight) / 100.0f;
                holder.weight.setText(String.valueOf(finalWeight));
            }
        });


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightFactor = db.fetchSabziData(db, sabziDetailsPojos.get(holder.getAdapterPosition()).getId()).getWeight();
                weightFactor = Math.round(100 * weightFactor) / 100.0f;
                currentWeight = Float.valueOf(holder.weight.getText().toString());
                currentWeight = Math.round(100 * currentWeight) / 100.0f;
                if (currentWeight > weightFactor) {
                    finalWeight = currentWeight - weightFactor;
                    finalWeight = Math.round(100 * finalWeight) / 100.0f;
                    holder.weight.setText(String.valueOf(finalWeight));
                }
            }
        });
        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    SabziDetailsPojo sabziDetailsPojo = new SabziDetailsPojo();

                    sabziDetailsPojo = sabziDetailsPojos.get(holder.getAdapterPosition());
                    sabziDetailsPojo.setWeight(Float.valueOf(holder.weight.getText().toString()));

                    if (!db.checkCartData(db, sabziDetailsPojo.getId())) {
                        db.insertCartData(db, sabziDetailsPojo);
                    } else {
                        db.updateCartData(db, sabziDetailsPojo, sabziDetailsPojo.getId());
                    }

                    String fullName = sabziDetailsPojos.get(holder.getAdapterPosition()).getName();
                    String trimmedName = fullName.substring(0, sabziDetailsPojos.get(holder.getAdapterPosition()).getName().indexOf("("));
                    String toastMessageAddToCart = "   " + String.valueOf(sabziDetailsPojo.getWeight()) + " Kgs of " + trimmedName + " added to Cart   ";
                    Toast toast = Toast.makeText(context, toastMessageAddToCart, Toast.LENGTH_SHORT);
                    toast.getView().setBackgroundColor(context.getResources().getColor(R.color.Toast_Custom));
                    toast.show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        //holder.sabzipic.setImageDrawable(Drawable.createFromPath(context.getFilesDir().toString().concat("/android/sabzi-pics/"+sabziDetailsPojos.get(position).getId()+".gif")));
       // holder.sabzipic.setImageDrawable(Drawable.createFromPath(context.getFilesDir().toString().concat("/"+String.valueOf(sabziDetailsPojos.get(position).getId()).trim()+".gif")));
       // holder.sabzipic.setImageBitmap(BitmapFactory.decodeFile(context.getFilesDir().toString().concat("/android/sabzi-pics/"+sabziDetailsPojos.get(position).getId()+".gif")));
        /*if (preferences.getBoolean(SP_SABZI_CHECK, true))
        {
            Log.v("Rashan","Loading from server");
            Picasso.with(context).load("http://vegvendors.in/" + sabziDetailsPojos.get(position).getPic()).into(holder.sabzipic);
            editor=preferences.edit();
            editor.putBoolean(SP_SABZI_CHECK,false);
            editor.commit();
        }
        else
        {Log.v("Rashan","Loading from internal storage");
            *//*Picasso.with(context).load(new File(context.getFilesDir().toString().concat("/a.gif"))).into(holder.sabzipic);*//*
            holder.sabzipic.setImageDrawable(Drawable.createFromPath(context.getFilesDir().toString().concat("/a.gif")));
        }*/

        switch(sabziDetailsPojos.get(position).getId()){
            case 1: holder.sabzipic.setImageResource(R.drawable.a01); break;
            case 2: holder.sabzipic.setImageResource(R.drawable.a02); break;
            case 3: holder.sabzipic.setImageResource(R.drawable.a03); break;
            case 4: holder.sabzipic.setImageResource(R.drawable.a04); break;
            case 5: holder.sabzipic.setImageResource(R.drawable.a05); break;
            case 6: holder.sabzipic.setImageResource(R.drawable.a06); break;
            case 7: holder.sabzipic.setImageResource(R.drawable.a07); break;
            case 8: holder.sabzipic.setImageResource(R.drawable.a08); break;
            case 9: holder.sabzipic.setImageResource(R.drawable.a09); break;
            case 10: holder.sabzipic.setImageResource(R.drawable.a10); break;
            case 11: holder.sabzipic.setImageResource(R.drawable.a11); break;
            case 12: holder.sabzipic.setImageResource(R.drawable.a12); break;
            case 13: holder.sabzipic.setImageResource(R.drawable.a13); break;
            case 14: holder.sabzipic.setImageResource(R.drawable.a14); break;
            case 15: holder.sabzipic.setImageResource(R.drawable.a15); break;
            case 16: holder.sabzipic.setImageResource(R.drawable.a16); break;
            case 17: holder.sabzipic.setImageResource(R.drawable.a17); break;
            case 18: holder.sabzipic.setImageResource(R.drawable.a18); break;
            case 19: holder.sabzipic.setImageResource(R.drawable.a19); break;
            case 20: holder.sabzipic.setImageResource(R.drawable.a20); break;
            case 21: holder.sabzipic.setImageResource(R.drawable.a21); break;
            case 22: holder.sabzipic.setImageResource(R.drawable.a22); break;
            case 23: holder.sabzipic.setImageResource(R.drawable.a23); break;
            case 24: holder.sabzipic.setImageResource(R.drawable.a24); break;
            case 25: holder.sabzipic.setImageResource(R.drawable.a25); break;
            case 26: holder.sabzipic.setImageResource(R.drawable.a26); break;
            case 27: holder.sabzipic.setImageResource(R.drawable.a27); break;
            case 28: holder.sabzipic.setImageResource(R.drawable.a28); break;
            case 29: holder.sabzipic.setImageResource(R.drawable.a29); break;
            case 30: holder.sabzipic.setImageResource(R.drawable.a30); break;
            case 31: holder.sabzipic.setImageResource(R.drawable.a31); break;
            case 32: holder.sabzipic.setImageResource(R.drawable.a32); break;
            case 33: holder.sabzipic.setImageResource(R.drawable.a33); break;
            case 34: holder.sabzipic.setImageResource(R.drawable.a34); break;
            case 35: holder.sabzipic.setImageResource(R.drawable.a35); break;
            case 36: holder.sabzipic.setImageResource(R.drawable.a36); break;
            case 37: holder.sabzipic.setImageResource(R.drawable.a37); break;
            case 38: holder.sabzipic.setImageResource(R.drawable.a38); break;
            case 39: holder.sabzipic.setImageResource(R.drawable.a39); break;
            case 40: holder.sabzipic.setImageResource(R.drawable.a40); break;
            case 41: holder.sabzipic.setImageResource(R.drawable.a41); break;
        }







        AnimationClass.recyclerAnimationSabzi(holder, true);

    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView sabziname, sabzicost, weight;
        ImageView minus, plus, sabzipic;
        Button addtocart;

        public MyHolder(View itemView) {
            super(itemView);
            sabzipic = (ImageView) itemView.findViewById(R.id.sabzipic);
            sabziname = (TextView) itemView.findViewById(R.id.sabziname);
            sabzicost = (TextView) itemView.findViewById(R.id.sabzicost);
            plus = (ImageView) itemView.findViewById(R.id.plus);
            minus = (ImageView) itemView.findViewById(R.id.minus);
            addtocart = (Button) itemView.findViewById(R.id.addtocart);
            weight = (TextView) itemView.findViewById(R.id.weight);


            sabziname.setTypeface(ssp);
            sabzicost.setTypeface(ubu);
            addtocart.setTypeface(ssp);
            weight.setTypeface(ubu);
        }
    }
}

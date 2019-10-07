package com.example.rashan.digifresh;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahendroo on 24-07-2016.
 */
public class SubOrderAdapter extends RecyclerView.Adapter<SubOrderAdapter.MyHolder> {

    private final Typeface ssp, ubu;
    Activity activity;
    List<PreviousSubOrderPojo> list = new ArrayList<>();

    public SubOrderAdapter(Activity activity, List<PreviousSubOrderPojo> list) {
        this.activity = activity;
        this.list = list;
        ssp = Typeface.createFromAsset(activity.getAssets(),
                "ssp.ttf");
        ubu = Typeface.createFromAsset(activity.getAssets(),
                "ubu.ttf");
    }


    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_subordercard_layout, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String name = "Name : " + list.get(position).getName();
        String weight = "Weight : " + list.get(position).getQty() + " Kgs";
        String price = "Price : ₹ " + list.get(position).getPrice();
        holder.suborder_name.setText(name);
        holder.suborder_weight.setText(weight);
        holder.suborder_price.setText(price);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView suborder_weight, suborder_price, suborder_name;

        public MyHolder(View itemView) {
            super(itemView);

            suborder_name = (TextView) itemView.findViewById(R.id.suborder_name);
            suborder_price = (TextView) itemView.findViewById(R.id.suborder_price);
            suborder_weight = (TextView) itemView.findViewById(R.id.suborder_weight);
            suborder_name.setTypeface(ssp);
            suborder_weight.setTypeface(ubu);
            suborder_price.setTypeface(ubu);

        }
    }

}

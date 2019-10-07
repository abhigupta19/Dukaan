package com.example.rashan.digifresh;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahendroo on 15-07-2016.
 */
public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.MyHolder> {

    private final Typeface ssp, ubu;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Activity activity;
    int category;
    List<VendorPojo> vendorPojos = new ArrayList<>();
    int vendorPosition = -1;
    String SP_VENDOR_CATEGORY_1;
    String SP_VENDOR_CATEGORY_2;
    String SP_VENDOR_CATEGORY_3;
    String SP_VENDOR_CATEGORY_4;
    String SP_VENDOR_CATEGORY_1_NAME;
    String SP_VENDOR_CATEGORY_2_NAME;
    String SP_VENDOR_CATEGORY_3_NAME;
    String SP_VENDOR_CATEGORY_4_NAME;

    public VendorAdapter(Activity ZSabziActivity, List<VendorPojo> list, int category) {
        activity = ZSabziActivity;
        vendorPojos = list;
        this.category = category;
        notifyDataSetChanged();
        SP_VENDOR_CATEGORY_1 = activity.getResources().getString(R.string.SP_VENDOR_CATEGORY_1);
        SP_VENDOR_CATEGORY_2 = activity.getResources().getString(R.string.SP_VENDOR_CATEGORY_2);
        SP_VENDOR_CATEGORY_3 = activity.getResources().getString(R.string.SP_VENDOR_CATEGORY_3);
        SP_VENDOR_CATEGORY_4 = activity.getResources().getString(R.string.SP_VENDOR_CATEGORY_4);
        SP_VENDOR_CATEGORY_1_NAME = activity.getResources().getString(R.string.SP_VENDOR_CATEGORY_1_NAME);
        SP_VENDOR_CATEGORY_2_NAME = activity.getResources().getString(R.string.SP_VENDOR_CATEGORY_2_NAME);
        SP_VENDOR_CATEGORY_3_NAME = activity.getResources().getString(R.string.SP_VENDOR_CATEGORY_3_NAME);
        SP_VENDOR_CATEGORY_4_NAME = activity.getResources().getString(R.string.SP_VENDOR_CATEGORY_4_NAME);

        ssp = Typeface.createFromAsset(activity.getAssets(),
                "ssp.ttf");
        ubu = Typeface.createFromAsset(activity.getAssets(),
                "ubu.ttf");

    }

    @Override
    public VendorAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendorlist_card_layout, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final VendorAdapter.MyHolder holder, final int position) {
        holder.vendorName.setText(vendorPojos.get(position).getName());
        holder.vendorPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (vendorPosition == -1) {
                    holder.vendorCard.setSelected(true);
                    holder.vendorCard.setBackground(activity.getResources().getDrawable(R.drawable.rect_boundary));
                    vendorPosition = holder.getLayoutPosition();
                } else {
                    holder.vendorCard.setBackground(activity.getResources().getDrawable(R.drawable.rect_boundary));
                    holder.vendorCard.setSelected(true);
                   *//* MyHolder myHolder = (MyHolder) activity.vendorRecyclerView.findViewHolderForLayoutPosition(vendorPosition);
                    myHolder.vendorCard.setSelected(false);*//*
                    vendorPosition = holder.getLayoutPosition();
                }*/

                int cat= ((ZSabziActivity)activity).viewPager.getCurrentItem()+1;
                editor.putInt("VENDOR_CAT_"+cat, vendorPojos.get(holder.getAdapterPosition()).getId());
                editor.putString("VENDOR_CAT_"+cat+"_NAME", vendorPojos.get(holder.getAdapterPosition()).getName());

                Toast.makeText(activity.getApplicationContext(), vendorPojos.get(holder.getAdapterPosition()).getName() + " Selected", Toast.LENGTH_SHORT).show();

            /*    if (category == 1) {
                    editor.putInt(SP_VENDOR_CATEGORY_1, vendorPojos.get(holder.getAdapterPosition()).getId());
                    editor.putString(SP_VENDOR_CATEGORY_1_NAME, vendorPojos.get(holder.getAdapterPosition()).getName());
                }
                if (category == 2) {
                    editor.putInt(SP_VENDOR_CATEGORY_2, vendorPojos.get(holder.getAdapterPosition()).getId());
                    editor.putString(SP_VENDOR_CATEGORY_2_NAME, vendorPojos.get(holder.getAdapterPosition()).getName());
                }
                if (category == 3) {
                    editor.putInt(SP_VENDOR_CATEGORY_3, vendorPojos.get(holder.getAdapterPosition()).getId());
                    editor.putString(SP_VENDOR_CATEGORY_3_NAME, vendorPojos.get(holder.getAdapterPosition()).getName());
                }
                if (category == 4) {
                    editor.putInt(SP_VENDOR_CATEGORY_4, vendorPojos.get(holder.getAdapterPosition()).getId());
                    editor.putString(SP_VENDOR_CATEGORY_4_NAME, vendorPojos.get(holder.getAdapterPosition()).getName());
                }*/

                editor.apply();

                ((ZSabziActivity)activity).selectedVendor();
            }
        });

        Picasso.with(activity).load("http://vegvendors.in/" + vendorPojos.get(position).getPic()).into(holder.vendorPic);

        AnimationClass.recyclerAnimationVendor(holder, true);
    }

    @Override
    public int getItemCount() {
        return vendorPojos.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView vendorPic;
        TextView vendorName;
        CardView vendorCard;

        public MyHolder(View itemView) {
            super(itemView);
            vendorCard = (CardView) itemView.findViewById(R.id.vendorCard);
            vendorName = (TextView) itemView.findViewById(R.id.vendorName);
            vendorPic = (ImageView) itemView.findViewById(R.id.vendorPic);
            preferences = PreferenceManager.getDefaultSharedPreferences(activity);
            editor = preferences.edit();

            vendorName.setTypeface(ssp);
        }
    }
}

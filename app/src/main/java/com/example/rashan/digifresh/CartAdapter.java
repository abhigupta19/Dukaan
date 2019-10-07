package com.example.rashan.digifresh;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahendroo on 09-07-2016.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {

    private final Typeface ssp, ubu;
    List<SabziDetailsPojo> cartListPojo = new ArrayList<>();
    List<SabziDetailsPojo> sabziListPojo = new ArrayList<>();
    List<VendorPojo> vendorListPojo = new ArrayList<>();
    CartActivity context;
    DbHelper dbHelper;
    String vendorIDCat1, vendorIDCat2, vendorIDCat3, vendorIDCat4, vendorIDCat1Name, vendorIDCat2Name, vendorIDCat3Name, vendorIDCat4Name;
    SharedPreferences preferences;

    public CartAdapter(CartActivity context, List<SabziDetailsPojo> cart, List<SabziDetailsPojo> sabzi) {
        this.context = context;
        this.cartListPojo = cart;
        this.sabziListPojo = sabzi;
        dbHelper = new DbHelper(context);
//        vendorListPojo = dbHelper.fetchALLVendorData(dbHelper);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        vendorIDCat1 = String.valueOf(preferences.getInt(context.getResources().getString(R.string.SP_VENDOR_CATEGORY_1), 0));
        vendorIDCat2 = String.valueOf(preferences.getInt(context.getResources().getString(R.string.SP_VENDOR_CATEGORY_2), 0));
        vendorIDCat3 = String.valueOf(preferences.getInt(context.getResources().getString(R.string.SP_VENDOR_CATEGORY_3), 0));
        vendorIDCat4 = String.valueOf(preferences.getInt(context.getResources().getString(R.string.SP_VENDOR_CATEGORY_4), 0));
        vendorIDCat1Name = preferences.getString(context.getResources().getString(R.string.SP_VENDOR_CATEGORY_1_NAME), "");
        vendorIDCat2Name = preferences.getString(context.getResources().getString(R.string.SP_VENDOR_CATEGORY_2_NAME), "");
        vendorIDCat3Name = preferences.getString(context.getResources().getString(R.string.SP_VENDOR_CATEGORY_3_NAME), "");
        vendorIDCat4Name = preferences.getString(context.getResources().getString(R.string.SP_VENDOR_CATEGORY_4_NAME), "");

        ssp = Typeface.createFromAsset(context.getAssets(),
                "ssp.ttf");
        ubu = Typeface.createFromAsset(context.getAssets(),
                "ubu.ttf");
    }

    @Override
    public CartAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlist_card_layout, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return cartListPojo.size();
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {

        final DbHelper db = new DbHelper(context.getApplicationContext());
        float cost = cartListPojo.get(position).getCost() * cartListPojo.get(position).getWeight();
        String costString = "₹ " + String.valueOf(cost);

        String vendorName;

        if (cartListPojo.get(position).getCategory() == 1) {
            vendorName = "Vendor : " + vendorIDCat1Name;
            holder.selectedVendorName.setText(vendorName);
        } else if (cartListPojo.get(position).getCategory() == 2) {
            vendorName = "Vendor : " + vendorIDCat2Name;
            holder.selectedVendorName.setText(vendorName);
        } else if (cartListPojo.get(position).getCategory() == 3) {
            vendorName = "Vendor : " + vendorIDCat3Name;
            holder.selectedVendorName.setText(vendorName);
        } else if (cartListPojo.get(position).getCategory() == 4) {
            vendorName = "Vendor : " + vendorIDCat4Name;
            holder.selectedVendorName.setText(vendorName);
        }

        String name = cartListPojo.get(position).getName();
        holder.sabziname.setText(name);
        holder.sabzicost.setText(costString);
        holder.weight.setText(String.valueOf(cartListPojo.get(position).getWeight()));
        setTotalPayableAmount();

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            try {
                float weightFactor = db.checkSabziData(db, cartListPojo.get(holder.getAdapterPosition()).getId());
                weightFactor = Math.round(100 * weightFactor) / 100.0f;
                float currentWeight = Float.valueOf(holder.weight.getText().toString());
                currentWeight = Math.round(100 * currentWeight) / 100.0f;
                float finalWeight = weightFactor + currentWeight;
                finalWeight = Math.round(100 * finalWeight) / 100.0f;
                holder.weight.setText(String.valueOf(finalWeight));

                SabziDetailsPojo sabziDetailsPojo = new SabziDetailsPojo();

                sabziDetailsPojo = cartListPojo.get(holder.getAdapterPosition());
                sabziDetailsPojo.setWeight(finalWeight);

                if (!db.checkCartData(db, sabziDetailsPojo.getId())) {
                    db.insertCartData(db, sabziDetailsPojo);
                } else {
                    db.updateCartData(db, sabziDetailsPojo, sabziDetailsPojo.getId());
                }
                float cost = sabziDetailsPojo.getCost() * sabziDetailsPojo.getWeight();
                String costString = "₹ " + String.valueOf(cost);

                holder.sabzicost.setText(costString);
                setTotalPayableAmount();
            }
            catch (Exception e)
            {

            }
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   float weightFactor = db.checkSabziData(db, cartListPojo.get(holder.getAdapterPosition()).getId());
                   weightFactor = Math.round(100 * weightFactor) / 100.0f;
                   float currentWeight = Float.valueOf(holder.weight.getText().toString());
                   currentWeight = Math.round(100 * currentWeight) / 100.0f;
                   float finalWeight = currentWeight - weightFactor;
                   finalWeight = Math.round(100 * finalWeight) / 100.0f;
                   if (currentWeight > weightFactor) {
                       holder.weight.setText(String.valueOf(finalWeight));
                       SabziDetailsPojo sabziDetailsPojo = new SabziDetailsPojo();

                       sabziDetailsPojo = cartListPojo.get(holder.getAdapterPosition());
                       sabziDetailsPojo.setWeight(finalWeight);

                       if (!db.checkCartData(db, sabziDetailsPojo.getId())) {
                           db.insertCartData(db, sabziDetailsPojo);
                       } else {
                           db.updateCartData(db, sabziDetailsPojo, sabziDetailsPojo.getId());
                       }
                       float cost = sabziDetailsPojo.getCost() * sabziDetailsPojo.getWeight();
                       String costString = "₹ " + String.valueOf(cost);

                       holder.sabzicost.setText(costString);
                   } else {

                   }
                   setTotalPayableAmount();
               }
               catch (Exception e)
               {

               }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              try {
                  if (db.checkCartAllData(db)) {

                      db.deleteCartData(db, cartListPojo.get(holder.getAdapterPosition()).getId());

                      cartListPojo.remove(holder.getAdapterPosition());
                      notifyItemRemoved(holder.getAdapterPosition());
                      notifyItemRangeChanged(holder.getAdapterPosition(), cartListPojo.size());
                      setTotalPayableAmount();
                      if (!(cartListPojo.size() > 0)) {
                          context.proceedCart.setEnabled(false);
                          context.cartRecycler.setVisibility(View.GONE);
                          context.noCartItem.setVisibility(View.VISIBLE);
                          //context.couponText.setText("");
                          //  context.couponText.setEnabled(false);
                          // context.applyCoupon.setEnabled(false);
                      }
                  }
              }
              catch (Exception e)
              {
                  Log.v("Rashan","TRIED DELETIOn TOO FAst");
              }
            }
        });

       // Picasso.with(context).load("http://vegvendors.in/" + cartListPojo.get(position).getPic()).into(holder.sabzipic);
        holder.sabzipic.setImageDrawable(Drawable.createFromPath(context.getFilesDir().toString().concat("/"+String.valueOf(cartListPojo.get(position).getId()).trim()+".gif")));

        switch(cartListPojo.get(position).getId()){
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
    }

    public void setTotalPayableAmount() {
        DbHelper dbHelper = new DbHelper(context);
        if (dbHelper.checkCartAllData(dbHelper)) {
            List<SabziDetailsPojo> list = dbHelper.fetchALLCartData(dbHelper);
            float amount = 0.0f;
            for (int i = 0; i < list.size(); i++) {
                float value = list.get(i).getCost() * list.get(i).getWeight();
                amount = amount + value;
            }
            context.updateTotalValue(amount);

        } else {
            context.updateTotalValue(0.0f);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView sabziname, sabzicost, selectedVendorName;
        ImageView sabzipic, delete, minus, plus;
        AppCompatTextView weight;

        public MyHolder(View itemView) {
            super(itemView);
            sabzipic = (ImageView) itemView.findViewById(R.id.cart_sabzipic);
            sabziname = (TextView) itemView.findViewById(R.id.cart_sabziname);
            sabzicost = (TextView) itemView.findViewById(R.id.cart_sabzicost);
            plus = (ImageView) itemView.findViewById(R.id.cart_plus);
            minus = (ImageView) itemView.findViewById(R.id.cart_minus);
            weight = (AppCompatTextView) itemView.findViewById(R.id.cart_weight);
            delete = (ImageView) itemView.findViewById(R.id.cart_delete);
            selectedVendorName = (TextView) itemView.findViewById(R.id.selectedVendorName);

            sabziname.setTypeface(ssp);
            selectedVendorName.setTypeface(ssp);
            sabzicost.setTypeface(ubu);
            weight.setTypeface(ubu);

        }
    }
}


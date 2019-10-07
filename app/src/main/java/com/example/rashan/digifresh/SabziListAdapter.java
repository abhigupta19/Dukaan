package com.example.rashan.digifresh;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Rashan on 15-11-2016.
 */
public class SabziListAdapter extends BaseAdapter {

    List<SabziDetailsPojo> sabziDetailsPojos;
    Activity activity;
    DbHelper db;
    float finalWeight, weightFactor, currentWeight;
    TextView sabziname, sabzicost, weight;
    ImageView minus, plus, sabzipic;
    Button addtocart;
    ListView listView;
    public SabziListAdapter(Activity activity, List<SabziDetailsPojo> list, ListView listView)
    {
        this.listView=listView;
        this.activity=activity;
        this.sabziDetailsPojos=list;
        db=new DbHelper(activity.getApplicationContext());
    }


    @Override
    public int getCount() {
        return sabziDetailsPojos.size();
    }

    @Override
    public Object getItem(int i) {
        return sabziDetailsPojos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, final View view, ViewGroup viewGroup) {
        View a;
        if(view==null)
        {
             a = LayoutInflater.from(activity).inflate(R.layout.sabzilist_card_layout, viewGroup, false);
        }
        else
        {
            a=view;
        }
   //  View a = LayoutInflater.from(activity).inflate(R.layout.sabzilist_card_layout, viewGroup, false);

        sabzipic = (ImageView) a.findViewById(R.id.sabzipic);
        sabziname = (TextView) a.findViewById(R.id.sabziname);
        sabzicost = (TextView) a.findViewById(R.id.sabzicost);
        plus = (ImageView) a.findViewById(R.id.plus);
        minus = (ImageView) a.findViewById(R.id.minus);
        addtocart = (Button) a.findViewById(R.id.addtocart);
        weight = (TextView) a.findViewById(R.id.weight);

        String cost = "₹ " + String.valueOf(sabziDetailsPojos.get(position).getCost()) + " /Kg";
        String name = sabziDetailsPojos.get(position).getName();

        sabziname.setText(name);
       sabzicost.setText(cost);
        weight.setText(String.valueOf(sabziDetailsPojos.get(position).getWeight()));

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        View d=getViewByPosition(position,listView);
                //  View d=(View) listView.getChildAt(listView.getPositionForView(v));
                // View d=view;
                //    int pos=((ListView)v.getParent()).getPositionForView(v);
                weightFactor = db.fetchSabziData(db, sabziDetailsPojos.get(listView.getPositionForView(v)).getId()).getWeight();
                weightFactor  = Math.round(100 * weightFactor) / 100.0f;
                //currentWeight = Float.valueOf(weight.getText().toString());
                currentWeight = Float.valueOf(((TextView)d.findViewById(R.id.weight)).getText().toString());
                currentWeight = Math.round(100 * currentWeight) / 100.0f;
                finalWeight = weightFactor + currentWeight;
                finalWeight = Math.round(100 * finalWeight) / 100.0f;
               ((TextView)d.findViewById(R.id.weight)).setText(String.valueOf(finalWeight));

            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  View d=(View) listView.getChildAt(listView.getPositionForView(v));
                View d=getViewByPosition(position,listView);
                weightFactor = db.fetchSabziData(db, sabziDetailsPojos.get(listView.getPositionForView(v)).getId()).getWeight();
                weightFactor = Math.round(100 * weightFactor) / 100.0f;
                currentWeight = Float.valueOf(((TextView)d.findViewById(R.id.weight)).getText().toString());
                currentWeight = Math.round(100 * currentWeight) / 100.0f;
                if (currentWeight > weightFactor) {
                    finalWeight = currentWeight - weightFactor;
                    finalWeight = Math.round(100 * finalWeight) / 100.0f;
                    ((TextView)d.findViewById(R.id.weight)).setText(String.valueOf(finalWeight));
                }
            }
        });
       addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View d=(View) listView.getChildAt(listView.getPositionForView(v));
                View d=getViewByPosition(position,listView);

                SabziDetailsPojo sabziDetailsPojo = new SabziDetailsPojo();

               sabziDetailsPojo = sabziDetailsPojos.get(listView.getPositionForView(v));
                sabziDetailsPojo.setWeight(Float.valueOf(((TextView)d.findViewById(R.id.weight)).getText().toString()));

                if (!db.checkCartData(db, sabziDetailsPojo.getId())) {
                    db.insertCartData(db, sabziDetailsPojo);
                } else {
                    db.updateCartData(db, sabziDetailsPojo, sabziDetailsPojo.getId());
                }

              String fullName = sabziDetailsPojos.get(listView.getPositionForView(v)).getName();
              String trimmedName = fullName.substring(0, sabziDetailsPojos.get(listView.getPositionForView(v)).getName().indexOf("("));
          String toastMessageAddToCart = "   " + String.valueOf(sabziDetailsPojo.getWeight()) + " Kgs of " + trimmedName + " added to Cart   ";
               Toast toast = Toast.makeText(activity, toastMessageAddToCart, Toast.LENGTH_SHORT);
             toast.getView().setBackgroundColor(activity.getResources().getColor(R.color.Toast_Custom));
             toast.show();
            }
        });
       //  sabzipic.setImageDrawable(Drawable.createFromPath(activity.getApplicationContext().getFilesDir().toString().concat("/"+String.valueOf(sabziDetailsPojos.get(position).getId()).trim()+".gif")));


        switch(sabziDetailsPojos.get(position).getId()){
            case 1: sabzipic.setImageResource(R.drawable.a01); break;
            case 2: sabzipic.setImageResource(R.drawable.a02); break;
            case 3: sabzipic.setImageResource(R.drawable.a03); break;
            case 4: sabzipic.setImageResource(R.drawable.a04); break;
            case 5: sabzipic.setImageResource(R.drawable.a05); break;
            case 6: sabzipic.setImageResource(R.drawable.a06); break;
            case 7: sabzipic.setImageResource(R.drawable.a07); break;
            case 8: sabzipic.setImageResource(R.drawable.a08); break;
            case 9: sabzipic.setImageResource(R.drawable.a09); break;
            case 10: sabzipic.setImageResource(R.drawable.a10); break;
            case 11: sabzipic.setImageResource(R.drawable.a11); break;
            case 12: sabzipic.setImageResource(R.drawable.a12); break;
            case 13: sabzipic.setImageResource(R.drawable.a13); break;
            case 14: sabzipic.setImageResource(R.drawable.a14); break;
            case 15: sabzipic.setImageResource(R.drawable.a15); break;
            case 16: sabzipic.setImageResource(R.drawable.a16); break;
            case 17: sabzipic.setImageResource(R.drawable.a17); break;
            case 18: sabzipic.setImageResource(R.drawable.a18); break;
            case 19: sabzipic.setImageResource(R.drawable.a19); break;
            case 20: sabzipic.setImageResource(R.drawable.a20); break;
            case 21: sabzipic.setImageResource(R.drawable.a21); break;
            case 22: sabzipic.setImageResource(R.drawable.a22); break;
            case 23: sabzipic.setImageResource(R.drawable.a23); break;
            case 24: sabzipic.setImageResource(R.drawable.a24); break;
            case 25: sabzipic.setImageResource(R.drawable.a25); break;
            case 26: sabzipic.setImageResource(R.drawable.a26); break;
            case 27: sabzipic.setImageResource(R.drawable.a27); break;
            case 28: sabzipic.setImageResource(R.drawable.a28); break;
            case 29: sabzipic.setImageResource(R.drawable.a29); break;
            case 30: sabzipic.setImageResource(R.drawable.a30); break;
            case 31: sabzipic.setImageResource(R.drawable.a31); break;
            case 32: sabzipic.setImageResource(R.drawable.a32); break;
            case 33: sabzipic.setImageResource(R.drawable.a33); break;
            case 34: sabzipic.setImageResource(R.drawable.a34); break;
            case 35: sabzipic.setImageResource(R.drawable.a35); break;
            case 36: sabzipic.setImageResource(R.drawable.a36); break;
            case 37: sabzipic.setImageResource(R.drawable.a37); break;
            case 38: sabzipic.setImageResource(R.drawable.a38); break;
            case 39: sabzipic.setImageResource(R.drawable.a39); break;
            case 40: sabzipic.setImageResource(R.drawable.a40); break;
            case 41: sabzipic.setImageResource(R.drawable.a41); break;
        }


        return a;
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}

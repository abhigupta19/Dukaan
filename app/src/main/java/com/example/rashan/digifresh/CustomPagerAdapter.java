package com.example.rashan.digifresh;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

/**
 * Created by Rashan on 13-11-2016.
 */
public class CustomPagerAdapter extends PagerAdapter {

    int size;
    Map<Integer, List<SabziDetailsPojo>> catlistSabzi;
  Activity activity;
    List<String> sabziCategories;
    public CustomPagerAdapter(Activity activity, Map<Integer, List<SabziDetailsPojo>> catlistSabzi, List<String> sabziCategories) {
        this.sabziCategories=sabziCategories;
        this.activity=activity;
        this.catlistSabzi=catlistSabzi;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==(View) object;
    }

    @Override
    public int getCount() {
        return catlistSabzi.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


            ListView listView = new ListView(activity);
 /*       Resources resources = activity.getResources();
        XmlPullParser parser = resources.getXml(R.layout.sabzilist_card_layout);
        AttributeSet attributes = Xml.asAttributeSet(parser);*/

            List<SabziDetailsPojo> xx = catlistSabzi.get(position+1);
            listView.setAdapter(new SabziListAdapter(activity, xx,listView));
            //    listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            container.addView(listView);


            return listView;
        }
   // }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      // super.destroyItem(container, position, object);
           container.removeView((View)object);
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return sabziCategories.get(position);

    }
}

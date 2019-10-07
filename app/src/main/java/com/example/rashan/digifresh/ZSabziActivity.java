package com.example.rashan.digifresh;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class ZSabziActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ViewPager viewPager;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    SearchFragment fragment;
    List<PlacePojo> areas, subAreas;


    final String URL_EXTRA = "url";
    final String PLACE_KEY = "place";
    final String CODE_KEY = "code";
    final String SELECT_AREA_TASK_POST_AREA_KEY = "area";
    final String PLACE_CODE_KEY = "code";
    final String COST_KEY = "price";
    final String WEIGHT_KEY = "weight";
    final String CATEGORY_KEY = "category";
    final String IMAGE_URL_KEY = "pic";
    final String HINDI_NAME_KEY = "hing_name";
    final String STATUS_KEY = "status";
    final String ID_KEY = "id";
    final String NAME_KEY = "name";
    final String GENDER_KEY = "gender";
    final String PLACE_CODE = "place_code";
    final String PIC_KEY = "pic";
    final String CAT1_KEY = "cat1";
    final String CAT2_KEY = "cat2";
    final String CAT3_KEY = "cat3";
    final String CAT4_KEY = "cat4";
    String SP_VENDOR_CATEGORY_4;
    String SP_VENDOR_CATEGORY_3;
    String SP_VENDOR_CATEGORY_2;
    String SP_VENDOR_CATEGORY_1;
    String SP_SUBAREA_ID;
    String SP_SUBAREA;
    String SP_AREA;
    String SP_LOGIN_STATUS;
    String SP_GENDER_PREFERENCE;
    String SP_USER_GENDER;
    String SP_USER_ADDRESS;
    String SP_USER_CONTACT;
    String SP_USER_PIC;
    String SP_USER_EMAIL;
    String SP_USER_NAME;
    String SP_USER_ID;
    String SP_VENDOR_CATEGORY_1_NAME;
    String SP_VENDOR_CATEGORY_2_NAME;
    String SP_VENDOR_CATEGORY_3_NAME;
    String SP_VENDOR_CATEGORY_4_NAME;
    String SELECT_AREA_TASK_URL;
    String SP_SABZI_CHECK;

    /*Data Lists*/
    //List<SubAreaPojo> subAreaPojoList;
    List<String> subAreaList;
    //   List<String> areaList;
    List<SabziDetailsPojo> sabziDetailsPojos;
    List<SabziDetailsPojo> currentList;

 /*   Map<Integer, String> areaMap;
    Map<Integer, List<Integer>> placeCodeMap;
    Map<Integer, List<String>> subAreaMap;*/

    Map<Integer, List<Integer>> placeCodeMap;
    Map<Integer, List<Integer>> areaMap;
    Map<Integer, String> subAreaNameMap;
    Map<Integer, String> areaNameMap;

    Map<Integer, List<SabziDetailsPojo>> catlistSabzi;
    Menu menu;
    SubMenu subMenu;

    boolean vendorLoad = false, sabziLoad = false;
    String[] areaList = {"Pitampura"};
    String selectedArea, selectedSubarea;
    int selectedSubareaCode;
    Bitmap bitmap;
    File file2;

    Spinner areaSpinner, localitySpinner;
    ProgressBar progress, sabziProgress, vendorProgress;
    ////////
    List<String> sabziCategories;
    // List<List<VendorPojo>> vendorDetails;
    Map<Integer, List<VendorPojo>> vendorDetails;

    ////////
    /*Views*/
    AppCompatButton update;
    RecyclerView sabziRecyclerView, vendorRecyclerView, vegvendorsRecyclerView;
    MenuItem location, searchItem;
    SearchView searchView;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Dialog dialog;
    SearchManager searchManager;
    ImageView v1, v2, v3;
    CircleImageView userPic;
    TextView username;
    View headerView;
    DbHelper db;
    SharedPreferences preferences;
    TextView selectedVendor;
    int categoryFlag = 0;

    /*Database*/
    SharedPreferences.Editor editor;
    Dialog progressDialog;
    ImageView progressImage;


    private Button vendorText;
    private ViewFlipper randomPics;
    private Typeface ssp, ubu;

    private View share;
    private Dialog vendorDialog;




    ExpandableListView expandableListView;
    int previousGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        setContentView(R.layout.activity_sabzi);
        //     setContentView(R.layout.newexample2);

        // new NewTask().execute(4);

        //subAreaPojoList = new ArrayList<>();
        currentList = new ArrayList<>();
        sabziDetailsPojos = new ArrayList<>();

        placeCodeMap = new HashMap<>();
        areaMap = new HashMap<>();
        //subAreaNameMap=new HashMap<>();
        // areaNameMap=new HashMap<>();
        areas = new ArrayList<>();
        subAreas = new ArrayList<>();


        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new SearchFragment();
        fragmentTransaction.add(R.id.Zsabzilayout, fragment);
        fragmentTransaction.commit();






        /////////////////////









    /*Constants*/
        SP_SABZI_CHECK = getResources().getString(R.string.SP_SABZI_CHECK);
        SP_USER_ID = getResources().getString(R.string.SP_USER_ID);
        SP_USER_NAME = getResources().getString(R.string.SP_USER_NAME);
        SP_USER_EMAIL = getResources().getString(R.string.SP_USER_EMAIL);
        SP_USER_PIC = getResources().getString(R.string.SP_USER_PIC);
        SP_USER_CONTACT = getResources().getString(R.string.SP_USER_CONTACT);
        SP_USER_ADDRESS = getResources().getString(R.string.SP_USER_ADDRESS);
        SP_USER_GENDER = getResources().getString(R.string.SP_USER_GENDER);
        SP_GENDER_PREFERENCE = getResources().getString(R.string.SP_GENDER_PREFERENCE);
        SP_LOGIN_STATUS = getResources().getString(R.string.SP_LOGIN_STATUS);
        SP_AREA = getResources().getString(R.string.SP_AREA);
        SP_SUBAREA = getResources().getString(R.string.SP_SUBAREA);
        SP_SUBAREA_ID = getResources().getString(R.string.SP_SUBAREA_ID);
        SP_VENDOR_CATEGORY_1 = getResources().getString(R.string.SP_VENDOR_CATEGORY_1);
        SP_VENDOR_CATEGORY_2 = getResources().getString(R.string.SP_VENDOR_CATEGORY_2);
        SP_VENDOR_CATEGORY_3 = getResources().getString(R.string.SP_VENDOR_CATEGORY_3);
        SP_VENDOR_CATEGORY_4 = getResources().getString(R.string.SP_VENDOR_CATEGORY_4);
        SP_VENDOR_CATEGORY_1_NAME = getResources().getString(R.string.SP_VENDOR_CATEGORY_1_NAME);
        SP_VENDOR_CATEGORY_2_NAME = getResources().getString(R.string.SP_VENDOR_CATEGORY_2_NAME);
        SP_VENDOR_CATEGORY_3_NAME = getResources().getString(R.string.SP_VENDOR_CATEGORY_3_NAME);
        SP_VENDOR_CATEGORY_4_NAME = getResources().getString(R.string.SP_VENDOR_CATEGORY_4_NAME);


        SELECT_AREA_TASK_URL = getResources().getString(R.string.area_selector_script);





        /*Views Referencing*/
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /*vegvendorsRecyclerView=(RecyclerView)findViewById(R.id.vegvendorsRecyclerView);*/
        sabziRecyclerView = (RecyclerView) findViewById(R.id.sabziRecyclerView);
        vendorRecyclerView = (RecyclerView) findViewById(R.id.vendorRecyclerView);
        randomPics = (ViewFlipper) findViewById(R.id.randomPics);
        sabziProgress = (ProgressBar) findViewById(R.id.sabziProgress);
        vendorProgress = (ProgressBar) findViewById(R.id.vendorProgress);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        vendorText = (Button) findViewById(R.id.vendorText);
        selectedVendor = (TextView) findViewById(R.id.selectedVendor);
        v1 = (ImageView) findViewById(R.id.v1);
        v2 = (ImageView) findViewById(R.id.v2);
        v3 = (ImageView) findViewById(R.id.v3);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        //////////////////////////////////////////////////

        sabziCategories = new ArrayList<>();

        vendorDetails = new HashMap<>();
        catlistSabzi = new HashMap<>();
//////////////////////////////
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        expandableListView=(ExpandableListView) findViewById(R.id.expandableListView);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition))
                {
                    parent.collapseGroup(groupPosition);
                } else
                {
                    if (groupPosition != previousGroup)
                    {
                        parent.collapseGroup(previousGroup);
                    }
                    previousGroup = groupPosition;
                    parent.expandGroup(groupPosition);
                }

                parent.smoothScrollToPosition(groupPosition);
                return true;
            }
        });


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                //calling CatWiseSearchResults with parameters of subcat code.
                //CatWiseSearchResults will fetch items based on subcatcode.

            /*    Intent intent=new Intent(HomeScreen.this,CatWiseSearchResults.class);

                ArrayList<SubCategory> tempList = new ArrayList<SubCategory>();
                tempList =  subcategory_name.get(groupPosition);

                intent.putExtra("subcategory", tempList.get(childPosition).getSubCatCode());
                startActivity(intent);
                mDrawerLayout.closeDrawer(expandableListView);

                return true;*/
                return true;
            }
        });



        menu = navigationView.getMenu();
        getMenuInflater().inflate(R.menu.activity_sabzi_drawer_location, menu);

       // getMenuInflater().inflate(R.menu.my_menu, menu);
        subMenu = menu.addSubMenu("Category");
        getMenuInflater().inflate(R.menu.activity_sabzi_drawer, menu);
////////////////////////////////


        headerView = navigationView.getHeaderView(0);

        username = (TextView) headerView.findViewById(R.id.username);
        userPic = (CircleImageView) headerView.findViewById(R.id.userPic);

        location = navigationView.getMenu().findItem(R.id.location);
        searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        ssp = Typeface.createFromAsset(getAssets(),
                "ssp.ttf");
        ubu = Typeface.createFromAsset(getAssets(),
                "ubu.ttf");




        vendorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vendorRecyclerView.getVisibility() == View.VISIBLE) {
                    vendorRecyclerView.setVisibility(View.GONE);
                } else {
                    vendorRecyclerView.setVisibility(View.VISIBLE);
                }

            }
        });

        selectedVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vendorDialog = new Dialog(ZSabziActivity.this);
                vendorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                vendorDialog.setContentView(R.layout.vendor_info);
                vendorDialog.setCancelable(true);
                TextView vendorNameD = (TextView) vendorDialog.findViewById(R.id.vendorNameD);
                TextView vendorIDD = (TextView) vendorDialog.findViewById(R.id.vendorIDD);
                TextView vendorCategoryD = (TextView) vendorDialog.findViewById(R.id.vendorCategoryD);
                ImageView vendorPicD = (ImageView) vendorDialog.findViewById(R.id.vendorPicD);
                vendorIDD.setTypeface(ssp);
                vendorNameD.setTypeface(ssp);
                vendorCategoryD.setTypeface(ssp);
                String name, vid, cat;
                name = "<pre><strong>Name : </strong>";
                vid = "<pre><strong>ID : </strong>";
                cat = "<pre><strong>Deals In Category</strong>";

                if (categoryFlag == 1) {
                    VendorPojo vendorPojo = db.fetchVendorData(db, preferences.getInt(getResources().getString(R.string.SP_VENDOR_CATEGORY_1), 0));

                    Picasso.with(ZSabziActivity.this).load("http://vegvendors.in/" + vendorPojo.getPic()).into(vendorPicD);
                    name = name + vendorPojo.getName() + "</pre>";
                    vid = vid + "VVDI" + String.valueOf(vendorPojo.getId()) + "</pre>";

                    if (vendorPojo.getCategory1() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_1);
                    }
                    if (vendorPojo.getCategory2() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_2);
                    }

                    if (vendorPojo.getCategory3() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_3);
                    }
                    if (vendorPojo.getCategory4() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_4);
                    }

                } else if (categoryFlag == 2) {
                    VendorPojo vendorPojo = db.fetchVendorData(db, preferences.getInt(getResources().getString(R.string.SP_VENDOR_CATEGORY_2), 0));

                    Picasso.with(ZSabziActivity.this).load("http://vegvendors.in/" + vendorPojo.getPic()).into(vendorPicD);
                    name = name + vendorPojo.getName() + "</pre>";
                    vid = vid + "VVDI" + String.valueOf(vendorPojo.getId()) + "</pre>";

                    if (vendorPojo.getCategory1() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_1);
                    }
                    if (vendorPojo.getCategory2() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_2);
                    }
                    if (vendorPojo.getCategory3() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_3);
                    }
                    if (vendorPojo.getCategory4() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_4);
                    }

                } else if (categoryFlag == 3) {
                    VendorPojo vendorPojo = db.fetchVendorData(db, preferences.getInt(getResources().getString(R.string.SP_VENDOR_CATEGORY_3), 0));

                    Picasso.with(ZSabziActivity.this).load("http://vegvendors.in/" + vendorPojo.getPic()).into(vendorPicD);
                    name = name + vendorPojo.getName() + "</pre>";
                    vid = vid + "VVDI" + String.valueOf(vendorPojo.getId()) + "</pre>";

                    if (vendorPojo.getCategory1() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_1);
                    }
                    if (vendorPojo.getCategory2() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_2);
                    }
                    if (vendorPojo.getCategory3() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_3);
                    }
                    if (vendorPojo.getCategory4() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_4);
                    }

                } else if (categoryFlag == 4) {
                    VendorPojo vendorPojo = db.fetchVendorData(db, preferences.getInt(getResources().getString(R.string.SP_VENDOR_CATEGORY_4), 0));

                    Picasso.with(ZSabziActivity.this).load("http://vegvendors.in/" + vendorPojo.getPic()).into(vendorPicD);
                    name = name + vendorPojo.getName() + "</pre>";
                    vid = vid + "VVDI" + String.valueOf(vendorPojo.getId()) + "</pre>";

                    if (vendorPojo.getCategory1() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_1);
                    }
                    if (vendorPojo.getCategory2() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_2);
                    }
                    if (vendorPojo.getCategory3() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_3);
                    }
                    if (vendorPojo.getCategory4() == 1) {
                        cat = cat + "</pre><br/><pre>" + getResources().getString(R.string.category_4);
                    }

                }

                cat = cat + "</pre>";
                vendorNameD.setText(Html.fromHtml(name));
                vendorIDD.setText(Html.fromHtml(vid));
                vendorCategoryD.setText(Html.fromHtml(cat));

                vendorDialog.show();
            }
        });


        //////////////////////////////////////////////////////


        ///////////////////////////////////////////////////









        /*Initialisation*/
        db = new DbHelper(getApplicationContext());
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        progressDialog = new Dialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressImage = (ImageView) progressDialog.findViewById(R.id.load);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setTitle("Dukaan");
        }
        catch (Exception e)
        {}
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundColor(getResources().getColor(R.color.GREY_BACKGROUND));
        username.setText(preferences.getString(SP_USER_NAME, "USER"));
        headerView.setBackground(getResources().getDrawable(R.drawable.header));

        overrideFonts(navigationView);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
       /* if (position == 0) {
           // randomPics.setVisibility(View.VISIBLE);
            vendorText.setVisibility(View.GONE);
            selectedVendor.setVisibility(View.GONE);
        } else {*/
                // randomPics.setVisibility(View.GONE);
                //////////////////////!!!!!!!!
                vendorText.setVisibility(View.VISIBLE);
                selectedVendor.setVisibility(View.VISIBLE);

                //  Log.v("Rashan", vendorDetails.get(position+1).toString() + "");

                selectedVendor();
                if (vendorDetails.get(position + 1) != null) {
                    VendorAdapter vendorAdapter = new VendorAdapter(ZSabziActivity.this, vendorDetails.get(position + 1), position + 1);
                    vendorRecyclerView.setAdapter(vendorAdapter);
                    vendorRecyclerView.setLayoutManager(new LinearLayoutManager(ZSabziActivity.this, LinearLayoutManager.HORIZONTAL, false));

                    vendorProgress.setVisibility(View.GONE);
                } else {
                    vendorRecyclerView.getAdapter().notifyItemRangeRemoved(0, vendorRecyclerView.getAdapter().getItemCount());
                }
            }


            //}


            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //  new NewTask().execute(preferences.getInt(getResources().getString(R.string.SP_SUBAREA_ID),0));


        /*Setting Location if previously selected else prompting Dialog to select new*/


        if (preferences.getString(SP_AREA, "").equals("") || preferences.getInt(SP_SUBAREA_ID, -1) == -1) {
            ///
            //new NewTask().execute(4);
            setDialog(true);
        } else {
            String slocation = preferences.getString(SP_AREA, "") + ", " + preferences.getString(SP_SUBAREA, "");
            location.setTitle(slocation);

            progressDialog.show();
            ((AnimationDrawable) progressImage.getBackground()).start();
            if (isNetworkAvailable()) {
                new NewTask().execute(preferences.getInt(getResources().getString(R.string.SP_SUBAREA_ID), 0));
                // new NewTask().execute(4);
                // new SabziDetailsTask().execute(preferences.getInt(getResources().getString(R.string.SP_SUBAREA_ID), 0));


            } else {
                startActivity(new Intent(ZSabziActivity.this, NoInternetActivity.class));
                finish();
            }


        }



        /*Floating Action Button Listener*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });


//        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Please wait a moment!!");

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(ZSabziActivity.this, EditProfileActivity.class);
               // startActivity(intent);
            }
        });

     //   Picasso.with(this).load(preferences.getString(getResources().getString(R.string.SP_USER_PIC), "http://vegvendors.in/android/user.jpg")).into(userPic);

      //  Picasso.with(this).load("http://vegvendors.in/android/v1.jpg").into(v1);
     //   Picasso.with(this).load("http://vegvendors.in/android/v2.jpg").into(v2);
      //  Picasso.with(this).load("http://vegvendors.in/android/v3.jpg").into(v3);

    }


    public void selectedVendor() {
        String x = "";
        /*if (categoryFlag == 1) {
            x = "Selected : " + preferences.getString(SP_VENDOR_CATEGORY_1_NAME, "");
        } else if (categoryFlag == 2) {
            x = "Selected : " + preferences.getString(SP_VENDOR_CATEGORY_2_NAME, "");
        } else if (categoryFlag == 3) {
            x = "Selected : " + preferences.getString(SP_VENDOR_CATEGORY_3_NAME, "");
        } else if (categoryFlag == 4) {
            x = "Selected : " + preferences.getString(SP_VENDOR_CATEGORY_4_NAME, "");
        }*/
        x = "Selected : " + preferences.getString("VENDOR_CAT_" + (viewPager.getCurrentItem() + 1) + "_NAME", "");
        selectedVendor.setText(x);
        vendorRecyclerView.setVisibility(View.GONE);
    }

    public void overrideFonts(final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(ssp);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNetworkAvailable()) {
            Intent intent = new Intent(getApplicationContext(), NoInternetActivity.class);
            startActivity(intent);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {

        /*Checking whether NavigationDrawer is open or not*/

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            List<SabziDetailsPojo> list = new ArrayList<>();
            if (!(categoryFlag == 0)) {
                getSupportActionBar().setTitle("Veg Vendors");
                categoryFlag = 0;
                List<Integer> randomID = new ArrayList<>();
                for (int i = 1; i <= sabziDetailsPojos.size(); i++) {
                    randomID.add(i);
                }
                Collections.shuffle(randomID);
                randomID = randomID.subList(0, 10);
                for (int j = 0; j < 10; j++) {
                    for (int k = 0; k < sabziDetailsPojos.size(); k++) {
                        if (sabziDetailsPojos.get(k).getId() == randomID.get(j)) {
                            list.add(sabziDetailsPojos.get(k));
                        }
                    }
                }
                currentList = list;
                MyAdapter myAdapter = new MyAdapter(ZSabziActivity.this, list);
                sabziRecyclerView.setAdapter(myAdapter);
                sabziRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                vendorProgress.setVisibility(View.GONE);
                sabziProgress.setVisibility(View.GONE);
                vendorText.setVisibility(View.GONE);
                selectedVendor.setVisibility(View.GONE);
                vendorRecyclerView.setVisibility(View.GONE);

                sabziRecyclerView.setVisibility(View.VISIBLE);

            } else {
                super.onBackPressed();
            }
        }
    }


    /*Dialog setup*/

    public void setDialog(boolean bb) {




        /*Dialog Views' Initialization */

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.location_dialog);
        if (bb)
            dialog.setCancelable(false);


        areaSpinner = (Spinner) dialog.findViewById(R.id.areaSpinner);
        localitySpinner = (Spinner) dialog.findViewById(R.id.localitySpinner);
        update = (AppCompatButton) dialog.findViewById(R.id.update);

       // areaSpinner.setTypeface(ssp);

        /*Area Spinner Adapter */

       /* if (isNetworkAvailable()) {
            progress = (ProgressBar) dialog.findViewById(R.id.progress);
            progress.setVisibility(View.VISIBLE);


            new SelectAreaTask(ZSabziActivity.this,1,"",null).execute();
        } else {
            dialog.dismiss();
            setDialog(true);
            Toast.makeText(ZSabziActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
        }*/


        List<SubAreaPojo> pojoList=new ArrayList<>();
        pojoList.add(new SubAreaPojo("Sector 1",1,1));
        pojoList.add(new SubAreaPojo("Sector 2",2,1));
        pojoList.add(new SubAreaPojo("Sector 3",3,2));
        pojoList.add(new SubAreaPojo("Sector 4",4,2));
        pojoList.add(new SubAreaPojo("Sector 5",5,2));
        pojoList.add(new SubAreaPojo("Sector 6",6,3));
        pojoList.add(new SubAreaPojo("Sector 7",7,3));
        pojoList.add(new SubAreaPojo("Sector 8",8,4));
        pojoList.add(new SubAreaPojo("Sector 9",9,4));


        List<AreaPojo> list=new ArrayList<>();





        AreaPojo rohini=new AreaPojo("Rohini",1,pojoList);

        list.add(rohini);
        Object o[]={list};
        SerializeData.save(ZSabziActivity.this,o,"areas");
        list.add(0,new AreaPojo("Select Area",-1,null));
        areaSpinner.setAdapter(new AreaSpinnerAdapter(ZSabziActivity.this,list));///
        areaSpinner.setVisibility(View.VISIBLE);




        SpinnerInteractionListener listener = new SpinnerInteractionListener();
        listener.setFlag(1);
        areaSpinner.setOnTouchListener(listener);
        areaSpinner.setOnItemSelectedListener(listener);



        SpinnerInteractionListener listener1 = new SpinnerInteractionListener();
        listener1.setFlag(2);
        localitySpinner.setOnTouchListener(listener1);
        localitySpinner.setOnItemSelectedListener(listener1);












        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //////!!!!!!!!!!!!!!



                    update.setVisibility(View.GONE);
                   // progress.setVisibility(View.VISIBLE);
                    editor.putInt(SP_SUBAREA_ID, selectedSubareaCode);
                    editor.putString(SP_SUBAREA, selectedSubarea);
                    editor.putString(SP_AREA, selectedArea);
                    editor.commit();
                    if (categoryFlag == 0) {

                    } else {
                        vendorRecyclerView.setVisibility(View.GONE);
                        vendorProgress.setVisibility(View.VISIBLE);
                    }
        /*Task to fetch SabziList and VendorList*/

                    progressDialog.show();
                    ((AnimationDrawable) progressImage.getBackground()).start();
                    new NewTask().execute(preferences.getInt(getResources().getString(R.string.SP_SUBAREA_ID), 0));
                    //  new NewTask().execute(4);
                    //  new SabziDetailsTask().execute(preferences.getInt(getResources().getString(R.string.SP_SUBAREA_ID), 0));

                    String slocation = preferences.getString(SP_AREA, "") + ", " + preferences.getString(SP_SUBAREA, "");
                    location.setTitle(slocation);
                    dialog.dismiss();

            }
        });

        /*Showing Dialog Finally after setting up*/

        dialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Log.v("Rashan", "In Options Item Selected");
        return toggle.onOptionsItemSelected(item);
    }

    /*Setting up of menu items*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.v("Rashan", "onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.sabzi, menu);
        searchItem = menu.findItem(R.id.search);
        searchItem.setVisible(false);


        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            EditText editText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            editText.setTextColor(Color.WHITE);
            ImageView searchCloseIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
            searchCloseIcon.setImageResource(R.drawable.cancel);

            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {

                        findViewById(R.id.Zsabzilayout).setVisibility(View.VISIBLE);

                    } else {
                        findViewById(R.id.Zsabzilayout).setVisibility(View.GONE);
                    }

                }
            });
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(ZSabziActivity.this.getComponentName()));


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String query) {
                  /*  FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    findViewById(R.id.Zsabzilayout).setVisibility(View.VISIBLE);
                    SearchFragment fragment = new SearchFragment();
                    fragmentTransaction.add(R.id.Zsabzilayout,fragment);
                    fragmentTransaction.commit();
*/
                    /*Filling up of List to filter out search list*/

                    List<SabziDetailsPojo> listPojo = new ArrayList<>();
                    if (sabziDetailsPojos.size() > 0) {
                        listPojo = sabziDetailsPojos;
                    } else {
                        listPojo = db.fetchALLSabziData(db);
                    }

                    /*Filtered List*/
                    List<SabziDetailsPojo> filteredList = new ArrayList<SabziDetailsPojo>();

                    if (query.equals("")) {

                    } else {
                        for (int i = 0; i < listPojo.size(); i++) {
                            if (listPojo.get(i).getName().toLowerCase().contains(query.toLowerCase()) || listPojo.get(i).getHinglish().toLowerCase().contains(query.toLowerCase())) {
                                filteredList.add(listPojo.get(i));
                            }
                        }
                    }

                    /*Setting up filtered list on recyclerView with adapter*/

                    //sabziRecyclerView.setAdapter(new MyAdapter(ZSabziActivity.this, filteredList));
                    fragment.setFragmentRecyclerView(ZSabziActivity.this, filteredList);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {

                    /*Filling up of List to filter out search list*/

                    List<SabziDetailsPojo> listPojo = new ArrayList<SabziDetailsPojo>();
                    if (sabziDetailsPojos.size() > 0) {
                        listPojo = sabziDetailsPojos;
                    } else {
                        listPojo = db.fetchALLSabziData(db);
                    }

                    /*Filtered List*/
                    List<SabziDetailsPojo> filteredList = new ArrayList<SabziDetailsPojo>();

                    if (query.equals("")) {
                        filteredList = currentList;
                    } else {
                        for (int i = 0; i < listPojo.size(); i++) {
                            if (listPojo.get(i).getName().toLowerCase().contains(query.toLowerCase()) || listPojo.get(i).getHinglish().toLowerCase().contains(query.toLowerCase())) {
                                filteredList.add(listPojo.get(i));
                            }
                        }
                    }
                    /*Setting up filtered list on recyclerView with adapter*/

                    // sabziRecyclerView.setAdapter(new MyAdapter(ZSabziActivity.this, filteredList));
                    fragment.setFragmentRecyclerView(ZSabziActivity.this, filteredList);
                    return true;
                }

            });

        }
        return true;
    }

    /*Navigation Item Selection Event*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Log.v("Rashan", "OnNAVIGATION");
        /*Declaring List to put on Main Screen i.e ZSabziActivity*/

        List<SabziDetailsPojo> list = new ArrayList<>();
        List<VendorPojo> vlist = new ArrayList<>();
        if (id == R.id.refer) {


  /*  Intent f=new Intent(ZSabziActivity.this,ExampleActivity.class);
           // f.putExtra("extra",catlistSabzi);
            startActivity(f);*/
/*
            CustomPagerAdapter customPagerAdapter=new CustomPagerAdapter(ZSabziActivity.this,catlistSabzi);
            viewPager.setAdapter(customPagerAdapter);*/

            // setGrids();
         /*   boolean b=(new File(getFilesDir().toString().concat("/android/sabzi-pics/1.gif"))).exists();
            Log.v("Rashan",b+"");*/
            Dialog dialog1;
            dialog1 = new Dialog(this);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.setContentView(R.layout.refer_dialog);
            if(preferences.getString("refer_string","hola").equals("hola"))
            {
                Double dd=Math.random()*10000;
                editor.putString("refer_string","DF"+dd.intValue());
                editor.commit();
            }


            ((TextView) dialog1.findViewById(R.id.refercode)).setText(preferences.getString("refer_string",""));
            dialog1.show();
        } else if (id == R.id.myaddresses) {
            Intent intent = new Intent(getApplicationContext(), AddressActivity.class);
            startActivity(intent);
        } else if (id == -1) {
            categoryFlag = 0;
            setGrids();
            ((LinearLayout) findViewById(R.id.sabziLinearLayout)).setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            ((LinearLayout) findViewById(R.id.vendorDetailLayout)).setVisibility(View.GONE);

        }else if (id == R.id.logout) {



            editor.putInt(SP_USER_ID, -1);      /*To be Changed Later*/
            editor.putString(SP_USER_ID + "fb", "");
            editor.putString(SP_USER_ID + "google", "");
            editor.putString(SP_USER_NAME, "");
            editor.putString(SP_USER_EMAIL, "");
            editor.putString(SP_USER_PIC, "");
            editor.putString(SP_USER_CONTACT, "");
            editor.putString(SP_USER_ADDRESS, "");
            editor.putString(SP_USER_GENDER, "");
            editor.putString(SP_GENDER_PREFERENCE, "");
            editor.putBoolean(SP_LOGIN_STATUS, false);
            editor.putString(SP_AREA, "");
            editor.putString(SP_SUBAREA, "");
            editor.putInt(SP_VENDOR_CATEGORY_1, 0);     /*To be Changed Later*/
            editor.putInt(SP_VENDOR_CATEGORY_2, 0);     /*To be Changed Later*/
            editor.putInt(SP_VENDOR_CATEGORY_3, 0);     /*To be Changed Later*/
            editor.putInt(SP_VENDOR_CATEGORY_4, 0);

            /*To be Changed Later*/
            editor.commit();



            db.deleteAllCartData(db);
            Intent intent = new Intent(getApplicationContext(), ZLogin.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.location) {

            setDialog(false);
            if (preferences.getString(SP_AREA, "").equals("") || preferences.getInt(SP_SUBAREA_ID, -1) == -1) {
                setDialog(false);
            } else {
                String slocation = preferences.getString(SP_AREA, "") + ", " + preferences.getString(SP_SUBAREA, "");
                item.setTitle(slocation);
            }
        }
//        else if (id == R.id.orders) {
//            Intent intent = new Intent(getApplicationContext(), PreviousOrderActivity.class);
//            startActivity(intent);
//        }
        else {
            //categoryFlag=id;
            //setGrids();
            ((LinearLayout) findViewById(R.id.sabziLinearLayout)).setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.vendorDetailLayout)).setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(id - 1);
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void shareReferCode(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "HELLO BRO");
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent, "Share via");
        startActivity(sendIntent);
    }


    /*-------------------------SelectArea Task Starts---------------------------------------*/



    /*-------------------------SelectArea Task Ends---------------------------------------*/


    /*-----------------------------Vendor Task Ends----------------------------*/
    class MyTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {

        }


        @Override
        protected Void doInBackground(String... params) {


            try {

             /*   File n=new File(getApplicationContext().getFilesDir().toString().concat("/"+params[0]));
                if(!n.exists())
                    n.mkdir();*/
                // URL url=new URL("http://vegvendors.in/" + params[0]+"/"+params[1]+".gif");
                URL url = new URL("http://www.vegvendors.in/android/sabzi-pics/".concat(params[0]));
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                byte[] data = new byte[1024];
                int total = 0;
                int count = 0;
                //OutputStream outputStream=new FileOutputStream(getApplicationContext().getFilesDir().toString().concat("/"+params[0]+"/"+params[1]+".gif"));
                OutputStream outputStream = new FileOutputStream(getApplicationContext().getFilesDir().toString().concat("/".concat(params[0])));
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    outputStream.write(data, 0, count);
                }
                inputStream.close();
                outputStream.close();


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }


    public class NewTask extends AsyncTask<Integer, Void, Void> {


      //  List<SubAreaPojo> subArea = new ArrayList<>();
        String result;

        @Override
        protected Void doInBackground(Integer... strings) {

            //selectedArea = preferences.getString(SP_AREA, "");


            try {
              //  String str=getIntent().getStringExtra("cookiedetails");
                String link="http://www.vegvendors.in/android/mainJson.php";
                throw new IOException();


            } catch (MalformedURLException e) {
                Log.v("Rashan", e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
          //  new SocialLoginDetails(ZSabziActivity.this).execute();

            if (result == null && false) {
              //  Toast.makeText(getApplicationContext(), "Network Issue", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), NoInternetActivity.class));
               // finish();
            } else {
//                Log.v("Rashan", result);
                try {
                     result = "{\"ProductList\":[{\"product\":\"Vegetable\",\"values\":[{\"categoryName\":\"Lifeline Veggies\",\"categoryId\":\"1\",\"subcat\":[{\"sid\":\"1\",\"IncrimentRate\":\"0.5\",\"price\":\"30\"},{\"sid\":\"3\",\"IncrimentRate\":\"0.5\",\"price\":\"70\"},{\"sid\":\"2\",\"IncrimentRate\":\"0.5\",\"price\":\"20\"}]},{\"categoryName\":\"Green Veggies\",\"categoryId\":\"2\",\"subcat\":[{\"sid\":\"4\",\"IncrimentRate\":\"0.5\",\"price\":\"30\"},{\"sid\":\"5\",\"IncrimentRate\":\"0.25\",\"price\":\"40\"},{\"sid\":\"6\",\"IncrimentRate\":\"0.5\",\"price\":\"20\"},{\"sid\":\"7\",\"IncrimentRate\":\"0.25\",\"price\":\"60\"},{\"sid\":\"8\",\"IncrimentRate\":\"0.25\",\"price\":\"75\"},{\"sid\":\"9\",\"IncrimentRate\":\"0.5\",\"price\":\"40\"},{\"sid\":\"10\",\"IncrimentRate\":\"0.5\",\"price\":\"40\"},{\"sid\":\"11\",\"IncrimentRate\":\"0.25\",\"price\":\"75\"},{\"sid\":\"12\",\"IncrimentRate\":\"0.25\",\"price\":\"100\"},{\"sid\":\"13\",\"IncrimentRate\":\"0.5\",\"price\":\"60\"},{\"sid\":\"15\",\"IncrimentRate\":\"0.25\",\"price\":\"70\"},{\"sid\":\"41\",\"IncrimentRate\":\"0.25\",\"price\":\"80\"}]},{\"categoryName\":\"Green Veggies 2\",\"categoryId\":\"3\",\"subcat\":[{\"sid\":\"16\",\"IncrimentRate\":\"0.5\",\"price\":\"70\"},{\"sid\":\"18\",\"IncrimentRate\":\"0.25\",\"price\":\"30\"},{\"sid\":\"19\",\"IncrimentRate\":\"0.25\",\"price\":\"40\"},{\"sid\":\"21\",\"IncrimentRate\":\"0.5\",\"price\":\"70\"},{\"sid\":\"22\",\"IncrimentRate\":\"0.5\",\"price\":\"60\"},{\"sid\":\"23\",\"IncrimentRate\":\"0.5\",\"price\":\"20\"},{\"sid\":\"24\",\"IncrimentRate\":\"0.5\",\"price\":\"40\"},{\"sid\":\"26\",\"IncrimentRate\":\"0.1\",\"price\":\"250\"}]},{\"categoryName\":\"Chutney Items\",\"categoryId\":\"4\",\"subcat\":[{\"sid\":\"32\",\"IncrimentRate\":\"0.25\",\"price\":\"120\"},{\"sid\":\"28\",\"IncrimentRate\":\"0.25\",\"price\":\"100\"},{\"sid\":\"29\",\"IncrimentRate\":\"0.1\",\"price\":\"80\"},{\"sid\":\"30\",\"IncrimentRate\":\"0.1\",\"price\":\"100\"},{\"sid\":\"31\",\"IncrimentRate\":\"0.1\",\"price\":\"80\"},{\"sid\":\"35\",\"IncrimentRate\":\"0.1\",\"price\":\"200\"},{\"sid\":\"36\",\"IncrimentRate\":\"0.25\",\"price\":\"40\"},{\"sid\":\"37\",\"IncrimentRate\":\"0.1\",\"price\":\"80\"},{\"sid\":\"42\",\"IncrimentRate\":\"0.1\",\"price\":\"60\"},{\"sid\":\"40\",\"IncrimentRate\":\"0.1\",\"price\":\"140\"},{\"sid\":\"34\",\"IncrimentRate\":\"0.5\",\"price\":\"100\"},{\"sid\":\"33\",\"IncrimentRate\":\"0.5\",\"price\":\"70\"}]}],\"vendorList\":[{\"category\":\"Lifeline Veggies\",\"categoryId\":\"1\",\"value\":[{\"vid\":\"35\",\"name\":\"Ajay\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Ajay\\/BlueLogo.png\"},{\"vid\":\"43\",\"name\":\"Kalu\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Kalu\\/GreenLogo.png\"},{\"vid\":\"44\",\"name\":\"Ramu\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Ramu\\/PinkLogo.png\"}]},{\"category\":\"Green Veggies\",\"categoryId\":\"2\",\"value\":[{\"vid\":\"35\",\"name\":\"Ajay\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Ajay\\/BlueLogo.png\"},{\"vid\":\"43\",\"name\":\"Kalu\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Kalu\\/GreenLogo.png\"},{\"vid\":\"44\",\"name\":\"Ramu\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Ramu\\/PinkLogo.png\"}]},{\"category\":\"Green Veggies 2\",\"categoryId\":\"3\",\"value\":[{\"vid\":\"35\",\"name\":\"Ajay\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Ajay\\/BlueLogo.png\"},{\"vid\":\"43\",\"name\":\"Kalu\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Kalu\\/GreenLogo.png\"},{\"vid\":\"44\",\"name\":\"Ramu\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Ramu\\/PinkLogo.png\"}]},{\"category\":\"Chutney Items\",\"categoryId\":\"4\",\"value\":[{\"vid\":\"35\",\"name\":\"Ajay\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Ajay\\/BlueLogo.png\"},{\"vid\":\"43\",\"name\":\"Kalu\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Kalu\\/GreenLogo.png\"},{\"vid\":\"44\",\"name\":\"Ramu\",\"gender\":null,\"available\":\"1\",\"pic\":\"vender-pics\\/4\\/Ramu\\/PinkLogo.png\"}]}]}],\"adv\":[\"adv details unavailable\"],\"vendorImgurl\":\"not defined\",\"sabziImgurl\":\"android\\/sabzi-pics\",\"express_delivery\":[{\"DurationTime\":\"2\",\"Px\":\"30\"}]}";




                    List<String> productList=new ArrayList<>();
                    Map <Integer,List<String>> categoriesList=new HashMap<>();

                 //   Map<String,List<String>> productMap=new HashMap<>();


                    Map<Integer, String> categoryMap = new HashMap<>();
                    List<SabziDetailsPojo> sabziDetailsPojosList = new ArrayList<>();
                    List<VendorPojo> vendorPojoList = new ArrayList<>();


                    JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                    JsonArray jsonArray = jsonObject.get("ProductList").getAsJsonArray();

                    String sabziImgurl = jsonObject.get("sabziImgurl").getAsString();
                    sabziCategories.clear();
                    vendorDetails.clear();


                    JsonObject jObject;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        jObject = jsonArray.get(i).getAsJsonObject();
                        String product_type = jObject.get("product").getAsString();
                        //productMap.put(product_type,new ArrayList<String>());
                        productList.add(product_type);
                        categoriesList.put(productList.size()-1,new ArrayList<String>());

                        JsonArray jArray = jObject.get("values").getAsJsonArray();
                        JsonObject jObj;
                        for (int j = 0; j < jArray.size(); j++) {
                            jObj = jArray.get(j).getAsJsonObject();
                            String category_name = jObj.get("categoryName").getAsString();
                            int category_id = jObj.get("categoryId").getAsInt();

                            categoriesList.get(productList.size()-1).add(category_name);

                            categoryMap.put(category_id, category_name);

                            sabziCategories.add(category_name);

                            JsonArray jArr = jObj.get("subcat").getAsJsonArray();
                            JsonObject jsonObj;
                            String s[] = new String[3];
                            for (int k = 0; k < jArr.size(); k++) {
                                jsonObj = jArr.get(k).getAsJsonObject();
                                SabziDetailsPojo sabziDetailsPojo = new SabziDetailsPojo();
                                int id = jsonObj.get("sid").getAsInt();
                                s = db.fetchSabziInitData(db, id);


                                sabziDetailsPojo.setName(s[1]);
                                sabziDetailsPojo.setId(id);
                                sabziDetailsPojo.setCost(BigDecimal.valueOf(jsonObj.get("price").getAsDouble()).floatValue());
                                sabziDetailsPojo.setWeight((float) jsonObj.get("IncrimentRate").getAsDouble());
                                sabziDetailsPojo.setCategory(category_id);

                                sabziDetailsPojo.setHinglish(s[2]);
                                if (!(new File(getApplicationContext().getFilesDir().toString().concat("/" + String.valueOf(sabziDetailsPojo.getId()).trim() + ".gif"))).exists()) {

                                    Log.v("Rashan", sabziImgurl + sabziDetailsPojo.getId());

                                   // new MyTask().execute(String.valueOf(sabziDetailsPojo.getId()).trim() + ".gif").get();
                                }
                                sabziDetailsPojosList.add(sabziDetailsPojo);

                            }

                        }
                        JsonArray jsonArr = jObject.get("vendorList").getAsJsonArray();
                        JsonObject jsonObject1;
                        for (int l = 0; l < jsonArr.size(); l++) {
                            jsonObject1 = jsonArr.get(l).getAsJsonObject();
                            int category_id = jsonObject1.get("categoryId").getAsInt();
                            JsonArray jsonArray1 = jsonObject1.get("value").getAsJsonArray();
                            JsonObject jsonObject2;
                            List<VendorPojo> catVendor = new ArrayList<>();
                            for (int m = 0; m < jsonArray1.size(); m++) {
                                jsonObject2 = jsonArray1.get(m).getAsJsonObject();


                                if (jsonObject2.get("available").getAsInt() != 1)
                                    continue;
                                VendorPojo vendorPojo = new VendorPojo();
                                vendorPojo.setId(jsonObject2.get("vid").getAsInt());
                                vendorPojo.setName(jsonObject2.get("name").getAsString());
                                vendorPojo.setGender("male");
                                vendorPojo.setPic(jsonObject2.get("pic").getAsString());
                                catVendor.add(vendorPojo);
                                //  Log.v("Rashan",vendorPojo.getName()+" "+ category);

                            }
                            vendorDetails.put(category_id, catVendor);


                        }

                    }



                    sabziDetailsPojos = sabziDetailsPojosList;
                    db.deleteAllSabziData(db);
                    db.insertSabziDataList(db, sabziDetailsPojosList);


                    Map<Integer, List<SabziDetailsPojo>> catlists = new HashMap<>();
                    subMenu.clear();
                    subMenu.add(1, -1, Menu.NONE, "Best Buy").setIcon(R.drawable.random);


                    MenuItem menuItem=subMenu.findItem(-1);




                   /* subMenu.addSubMenu(2,1,Menu.NONE,"RJ1");
                    subMenu.addSubMenu(2,2,Menu.NONE,"RJ2");
                    subMenu.addSubMenu(2,3,Menu.NONE,"RJ3");*/



//                    expandableListView.setAdapter(new ExpandableListAdapter(ZSabziActivity.this,productList,categoriesList));


                    Set<Integer> set = new HashSet<>();
                    for (int i = 0; i < sabziDetailsPojos.size(); i++) {
                        if (set.add(sabziDetailsPojos.get(i).getCategory())) {
                            catlists.put(sabziDetailsPojos.get(i).getCategory(), new ArrayList<SabziDetailsPojo>());
                            subMenu.add(1, set.size(), Menu.NONE, categoryMap.get(sabziDetailsPojos.get(i).getCategory())).setIcon(R.drawable.cat1);
                            //subMenu.add(1, set.size(), Menu.NONE, sabziCategories.get(sabziDetailsPojos.get(i).getCategory()-1)).setIcon(R.drawable.cat1);

                        }
                        catlists.get(sabziDetailsPojos.get(i).getCategory()).add(sabziDetailsPojos.get(i));

                    }

                    Log.v("Rashan", set.size() + "");
                    Log.v("Rashan", catlists.size() + "");
                    catlistSabzi = catlists;


/////////////////////////////////////////////////////////VENDOR









                    CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(ZSabziActivity.this, catlistSabzi, sabziCategories);
                    viewPager.setAdapter(customPagerAdapter);
                    setGrids();
                    progressDialog.dismiss();

                } catch (Exception e) {
                    Log.v("Rashan", "catch");
                    e.printStackTrace();
                }
            }

        }
    }


    public void setGrids() {
        if (categoryFlag == 0) {
            getSupportActionBar().setTitle("Dukaan");

           // randomPics.setVisibility(View.VISIBLE);
           /* vendorText.setVisibility(View.GONE);
            selectedVendor.setVisibility(View.GONE);*/

            List<Integer> randomID = new ArrayList<>();
            for (int i = 1; i <= sabziDetailsPojos.size(); i++) {
                randomID.add(i);
            }
            Collections.shuffle(randomID);
            randomID = randomID.subList(0, 10);
            List<SabziDetailsPojo> list = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < sabziDetailsPojos.size(); k++) {
                    if (sabziDetailsPojos.get(k).getId() == randomID.get(j)) {
                        list.add(sabziDetailsPojos.get(k));
                    }
                }
            }
            currentList = list;
            MyAdapter myAdapter = new MyAdapter(ZSabziActivity.this, list);
            sabziRecyclerView.setAdapter(myAdapter);
            sabziRecyclerView.setLayoutManager(new LinearLayoutManager(ZSabziActivity.this));
            sabziProgress.setVisibility(View.GONE);
            sabziRecyclerView.setVisibility(View.VISIBLE);


        } else {

            getSupportActionBar().setTitle(sabziCategories.get(categoryFlag - 1));
            vendorText.setVisibility(View.VISIBLE);


            selectedVendor.setVisibility(View.VISIBLE);
            //   selectedVendor();

            if ((!vendorLoad || !sabziLoad) && false) {
                if (!vendorLoad) {
                }
                if (!sabziLoad) {
                    sabziProgress.setVisibility(View.VISIBLE);
                }
            } else {
                Log.v("Rashan", "Reached");


                MyAdapter myAdapter = new MyAdapter(ZSabziActivity.this, catlistSabzi.get(categoryFlag));
                sabziRecyclerView.setAdapter(myAdapter);
                sabziRecyclerView.setLayoutManager(new LinearLayoutManager(ZSabziActivity.this));
                sabziProgress.setVisibility(View.GONE);
                sabziRecyclerView.setVisibility(View.VISIBLE);
            }


            VendorAdapter vendorAdapter = new VendorAdapter(ZSabziActivity.this, vendorDetails.get(categoryFlag), categoryFlag);
            vendorRecyclerView.setAdapter(vendorAdapter);
            vendorRecyclerView.setLayoutManager(new LinearLayoutManager(ZSabziActivity.this, LinearLayoutManager.HORIZONTAL, false));
            vendorProgress.setVisibility(View.GONE);
        }

        progressDialog.dismiss();


    }

    public void postSelectAreaTask(String result) {
     //   progress.setVisibility(View.GONE);
        if (result == null) {

            dialog.dismiss();
            setDialog(true);
            Toast.makeText(ZSabziActivity.this, "Network Issue", Toast.LENGTH_SHORT).show();
        } else {

            try {

                JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
                JsonArray jsonArray = jsonObject.get("Area").getAsJsonArray();
                placeCodeMap = new HashMap<>();
                areaMap = new HashMap<>();
                //subAreaNameMap=new HashMap<>();
                // areaNameMap=new HashMap<>();

                areas = new ArrayList<>();
                subAreas = new ArrayList<>();

                ///////////////
                List<AreaPojo>  areaPojoList=new ArrayList<>();



                JsonObject jObject;
                JsonArray jArray;
                for (int i = 0; i < jsonArray.size(); i++) {
                    jObject = jsonArray.get(i).getAsJsonObject();
                    String area = jObject.get("AreaName").getAsString();
                    int areaCode = jObject.get("AreaCode").getAsInt();
                    jArray = jObject.get("places").getAsJsonArray();

                    areaMap.put(areaCode, new ArrayList<Integer>());
                    //   areaNameMap.put(areaCode,area);
                    areas.add(new PlacePojo(area, areaCode, -1, -1));


                    //////////
                   // areaPojoList.add(new AreaPojo(area,areaCode,new ArrayList<SubAreaPojo>()));
                    AreaPojo areaPojo=new AreaPojo(area,areaCode,new ArrayList<SubAreaPojo>());



                    JsonObject jObj;
                    JsonArray jArr;
                    for (int j = 0; j < jArray.size(); j++) {
                        jObj = jArray.get(j).getAsJsonObject();
                        int placeCode = jObj.get("PlaceCode").getAsInt();
                        jArr = jObj.get("SubAreas").getAsJsonArray();
                        for (int l = 0; l < jArr.size(); l++) {
                            String subArea = jArr.get(l).getAsJsonObject().get("Subareas").getAsString();

                            int subAreaCode = jArr.get(l).getAsJsonObject().get("SubareaCode").getAsInt();

                            //areaMap.get(areaCode).add(placeCode);
                            if (!placeCodeMap.containsKey(placeCode)) {
                                placeCodeMap.put(placeCode, new ArrayList<Integer>());
                            }
                            placeCodeMap.get(placeCode).add(subAreaCode);
                            //subAreaNameMap.put(subAreaCode,subArea);
                            subAreas.add(new PlacePojo(subArea, subAreaCode, placeCode, areaCode));


                            ////////////
                            areaPojo.getSubAreaPojoList().add(new SubAreaPojo(subArea,subAreaCode,placeCode));


                        }
                        areaMap.get(areaCode).add(placeCode);




                    }

                    ///////////////
                    areaPojoList.add(areaPojo);


                }

                Object[] objects={areaPojoList};
              SerializedData.save(ZSabziActivity.this,objects);
//                SerializedData.save(ZSabziActivity.this,areaPojoList);
                //areaSpinner.setAdapter(new AreaAdapter(ZSabziActivity.this, areas));

                List<AreaPojo> pojoList=new ArrayList<>();
                pojoList.addAll(areaPojoList);
                pojoList.add(0,new AreaPojo("Select Area",-1,null));
                areaSpinner.setAdapter(new AreaSpinnerAdapter(ZSabziActivity.this,pojoList));///
                areaSpinner.setVisibility(View.VISIBLE);

  //              SerializedData.read(ZSabziActivity.this,1);
           SerializedData.read(ZSabziActivity.this);





            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

        boolean userSelect = false;
        int flag=-1;


        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            userSelect = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (userSelect) {




                if(flag==1)
                {
                    if(pos!=0) {
                        localitySpinner.setVisibility(View.VISIBLE);
                        onAreaChange(pos);

                    }
                    else {
                        localitySpinner.setSelection(0);
                        localitySpinner.setVisibility(View.GONE);
                        update.setVisibility(View.GONE);

                    }
                }
                else if(flag==2)
                {
                    if (pos!=0) {
                        onSubAreaChange(pos);
                    }
                    else
                    {
                        update.setVisibility(View.GONE);
                    }
                }




                userSelect = false;
            }
        }


        public int getFlag() {
            return flag;
        }

        public void setFlag(int f) {
            flag = f;
        }
        public void onAreaChange(int i)
        {

            AreaPojo aPojo=((AreaSpinnerAdapter) areaSpinner.getAdapter()).getPojo(i);
            selectedArea =aPojo.getName();
            int x = aPojo.getAreaCode();

////////
            /////
            List<SubAreaPojo> list = new ArrayList<>();
            list.addAll(aPojo.getSubAreaPojoList());
            list.add(0,new SubAreaPojo("Select Subarea",-1,-1));
            localitySpinner.setAdapter(new SubAreaSpinnerAdapter(ZSabziActivity.this,list));


            //subAreaSpinner.setAdapter(new SubAreaSpinnerAdapter(activity,aPojo.getSubAreaPojoList()));
            localitySpinner.setVisibility(View.VISIBLE);

        }

        public void onSubAreaChange(int i)
        {
            SubAreaPojo sPojo=((SubAreaSpinnerAdapter) localitySpinner.getAdapter()).getPojo(i);
            selectedSubarea = sPojo.getName();
            selectedSubareaCode =sPojo.getPlaceCode();
            update.setVisibility(View.VISIBLE);

        }
    }


}
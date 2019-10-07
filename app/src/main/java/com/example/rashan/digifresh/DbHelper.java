package com.example.rashan.digifresh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahendroo on 09-07-2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    final static String DATABASE_NAME = "sabzi.db";
    final static int DATABASE_VERSION = 1;
    final static String CART_TABLE = "cart_data";
    final static String[] cartList = {"id", "name", "cost", "weight", "category", "pic"};
    final static String SABZI_TABLE = "sabzi_data";
    final static String[] sabziList = {"id", "name", "cost", "weight", "category", "pic", "hinglish"};
    final static String VENDOR_TABLE = "vendor_data";
    final static String[] vendorList = {"id", "name", "gender", "pic", "category1", "category2", "category3", "category4"};

    final static String SABZI_INIT_TABLE="sabzi_init_data";
    final static String[] sabziInitList={"id","name","hinglish"};

    final static  String ADDRESS_TABLE="address_data";
    final static  String[] addressList={"id","name","address1","address2","subarea","area","subareacode","placecode","areacode"};


    final static int SIT_id=0;
    final static int SIT_name=1;
    final static int SIT_hinglish=2;

 /*   final static int AREA_id=0;
    final static int AREA_name=1;
    final static int AREA_placecode=1;
    final static int AREA_areacode=1;*/

    final static int CI_id = 0;
    final static int CI_name = 1;
    final static int CI_cost = 2;
    final static int CI_weight = 3;
    final static int CI_category = 4;
    final static int CI_pic = 5;
    final static int CI_hinglish = 6;


    final static  int AT_id=0;
    final static  int AT_name=1;
    final static int AT_address1=2;
    final static int AT_address2=3;
    final static int AT_subArea=4;
    final static int AT_area=5;
    final static int AT_subAreaCode=6;
    final static int AT_placeCode=7;
    final static int AT_areaCode=8;



    final static int VT_id = 0;
    final static int VT_name = 1;
    final static int VT_gender = 2;
    final static int VT_pic = 3;
    final static int VT_category1 = 4;
    final static int VT_category2 = 5;
    final static int VT_category3 = 6;
    final static int VT_category4 = 7;

    List<SabziDetailsPojo> list = new ArrayList<>();

    String createCartTable = "create table " + CART_TABLE + "(" +
            "id integer PRIMARY KEY," +
            "name nvarchar ," +
            "cost float(4)," +
            "weight float(2)," +
            "category varchar," +
            "pic varchar);";

    String createSabziInitTable="create table "+ SABZI_INIT_TABLE+ "(" +
            "id integer PRIMARY KEY,"+
            "name TEXT," +
            "hinglish TEXT);";

    String createSabziTable = "create table " + SABZI_TABLE + "(" +
            "id integer PRIMARY KEY," +
            "name varchar ," +
            "cost float(4)," +
            "weight float(2)," +
            "category varchar," +
            "pic varchar," +
            "hinglish varchar);";

    String createVendorTable = "create table " + VENDOR_TABLE + "(" +
            "id integer PRIMARY KEY," +
            "name varchar ," +
            "gender varchar," +
            "pic varchar," +
            "category1 integer," +
            "category2 integer," +
            "category3 integer," +
            "category4 integer);";

   /* String createAreaTable="create table "+ AREA_TABLE+ "(" +
            "id integer PRIMARY KEY," +
            "name varchar,"+
            "placecode integer,"+
            "areacode integer);";*/

    String createAddressTable= "create table "+ ADDRESS_TABLE+ "(" +
            "id integer PRIMARY KEY,"+
            "name TEXT,"+
            "address1 TEXT,"+
            "address2 TEXT,"+
            "subarea TEXT,"+
            "area TEXT,"+
            "subareacode integer,"+
            "placecode integer,"+
            "areacode integer);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createSabziTable);
        db.execSQL(createAddressTable);
        db.execSQL(createCartTable);
        db.execSQL(createVendorTable);
        db.execSQL(createSabziInitTable);
        /*db.execSQL(createAreaTable);*/


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertSabziInitData(DbHelper db,int id, String name,String hinglish)
    {
        SQLiteDatabase sqLiteDatabase=db.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(sabziInitList[0],id);
        cv.put(sabziInitList[1],name);
        cv.put(sabziInitList[2],hinglish);
        sqLiteDatabase.insert(SABZI_INIT_TABLE,null,cv);
    }

    public String[] fetchSabziInitData(DbHelper db,int id) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        String[] strings=new String[3];
        Cursor cursor = sqLiteDatabase.query(SABZI_INIT_TABLE, sabziInitList, sabziInitList[0] + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {




            strings[0]=(cursor.getInt(SIT_id))+"";
            strings[1]=(cursor.getString(SIT_name));
            strings[2]=(cursor.getString(SIT_hinglish));




        }

        return strings;
    }



    public void insertCartData(DbHelper db, SabziDetailsPojo sp) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(cartList[0], sp.getId());
        cv.put(cartList[1], sp.getName());
        cv.put(cartList[2], sp.getCost());
        cv.put(cartList[3], sp.getWeight());
        cv.put(cartList[4], sp.getCategory());
        cv.put(cartList[5], sp.getPic());

        sqLiteDatabase.insert(CART_TABLE, null, cv);
    }

    public void updateCartData(DbHelper db, SabziDetailsPojo sp, int id) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(cartList[0], sp.getId());
        cv.put(cartList[1], sp.getName());
        cv.put(cartList[2], sp.getCost());
        cv.put(cartList[3], sp.getWeight());
        cv.put(cartList[4], sp.getCategory());
        cv.put(cartList[5], sp.getPic());

        sqLiteDatabase.update(CART_TABLE, cv, cartList[0] + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteCartData(DbHelper db, int id) {
        try {
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

            sqLiteDatabase.delete(CART_TABLE, cartList[0] + "=?", new String[]{String.valueOf(id)});
        }catch (Exception e)
        {

        }

    }

    public void deleteAllCartData(DbHelper db) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        sqLiteDatabase.delete(CART_TABLE, null, null);
    }

    public boolean checkCartData(DbHelper db, int id) {
        boolean flag = false;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(CART_TABLE, cartList, cartList[0] + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            flag = true;
        }

        return flag;
    }

    public boolean checkCartAllData(DbHelper db) {
        boolean flag = false;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(CART_TABLE, cartList, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            flag = true;
        }

        return flag;
    }

    public List<SabziDetailsPojo> fetchALLCartData(DbHelper db) {
    try {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        List<SabziDetailsPojo> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(CART_TABLE, cartList, null, null, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                SabziDetailsPojo sabziDetailsPojo = new SabziDetailsPojo();

                sabziDetailsPojo.setId(cursor.getInt(CI_id));
                sabziDetailsPojo.setName(cursor.getString(CI_name));
                sabziDetailsPojo.setCost(cursor.getFloat(CI_cost));
                sabziDetailsPojo.setWeight(cursor.getFloat(CI_weight));
                sabziDetailsPojo.setCategory(cursor.getInt(CI_category));
                sabziDetailsPojo.setPic(cursor.getString(CI_pic));

                list.add(sabziDetailsPojo);

            }
            while (cursor.moveToNext());
        }

        return list;
    }
    catch (Exception e)
    {
        return null;
    }
    }





/*


    public void insertAddressData(DbHelper db, AddressPojo ap) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(addressList[0],ap.getId());
        cv.put(addressList[1], ap.getName());
        cv.put(addressList[2],ap.getAddress1());
        cv.put(addressList[3],ap.getAddress2());
        cv.put(addressList[4],ap.getSubarea());
        cv.put(addressList[5],ap.getArea());
        cv.put(addressList[6],ap.getCode());


        sqLiteDatabase.insert(ADDRESS_TABLE, null, cv);
    }

    public void updateAddressData(DbHelper db, AddressPojo ap, String prevaddress) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //cv.put(addressList[1], ap.getAddress());
        cv.put(addressList[2],ap.getSubarea());
        cv.put(addressList[3],ap.getArea());

        sqLiteDatabase.update(ADDRESS_TABLE, cv, addressList[0] + "=?", new String[]{prevaddress});
    }


    public void deleteAddressData(DbHelper db, int id) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        sqLiteDatabase.delete(ADDRESS_TABLE, addressList[0] + "=?", new String[]{String.valueOf(id)});
    }


    public List<AddressPojo> fetchALLAddressData(DbHelper db) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        List<AddressPojo> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(ADDRESS_TABLE, addressList, null, null, null, null, null);
        if (cursor.moveToFirst()) {

            do {


                AddressPojo addressPojo=new AddressPojo();
                addressPojo.setId(cursor.getInt(AT_id));
                addressPojo.setName(cursor.getString(AT_name));
                addressPojo.setAddress1(cursor.getString(AT_address1));
                addressPojo.setAddress2(cursor.getString(AT_address2));
                addressPojo.setSubarea(cursor.getString(AT_subArea));
                addressPojo.setArea(cursor.getString(AT_Area));
                addressPojo.setCode(cursor.getInt(AT_code));
                list.add(addressPojo);




            }
            while (cursor.moveToNext());
        }
        Log.v("Rashan","Returning list");
        return list;
    }

    public List<AddressPojo> fetchAddressData(DbHelper db, int id) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();

        List<AddressPojo> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(ADDRESS_TABLE, addressList, addressList[6] + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                AddressPojo addressPojo = new AddressPojo();
                addressPojo.setId(cursor.getInt(AT_id));
                addressPojo.setName(cursor.getString(AT_name));
                addressPojo.setAddress1(cursor.getString(AT_address1));
                addressPojo.setAddress2(cursor.getString(AT_address2));
                addressPojo.setSubarea(cursor.getString(AT_subArea));
                addressPojo.setArea(cursor.getString(AT_Area));
                addressPojo.setCode(cursor.getInt(AT_code));
                list.add(addressPojo);
            }while (cursor.moveToNext());
        }

        return list;
    }
*/





    public void insertAddressData(DbHelper db, AddressPojo ap) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(addressList[0],ap.getId());
        cv.put(addressList[1], ap.getName());
        cv.put(addressList[2],ap.getAddress1());
        cv.put(addressList[3],ap.getAddress2());
        cv.put(addressList[4],ap.getSubarea());
        cv.put(addressList[5],ap.getArea());
        cv.put(addressList[6],ap.getSubAreaCode());
        cv.put(addressList[7],ap.getPlaceCode());
        cv.put(addressList[8],ap.getAreaCode());



        sqLiteDatabase.insert(ADDRESS_TABLE, null, cv);
    }

    public void updateAddressData(DbHelper db, AddressPojo ap, String prevaddress) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //cv.put(addressList[1], ap.getAddress());
        cv.put(addressList[2],ap.getSubarea());
        cv.put(addressList[3],ap.getArea());

        sqLiteDatabase.update(ADDRESS_TABLE, cv, addressList[0] + "=?", new String[]{prevaddress});
    }


    public void deleteAddressData(DbHelper db, int id) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        sqLiteDatabase.delete(ADDRESS_TABLE, addressList[0] + "=?", new String[]{String.valueOf(id)});
    }


    public List<AddressPojo> fetchALLAddressData(DbHelper db) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        List<AddressPojo> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(ADDRESS_TABLE, addressList, null, null, null, null, null);
        if (cursor.moveToFirst()) {

            do {


                AddressPojo addressPojo=new AddressPojo();
                addressPojo.setId(cursor.getInt(AT_id));
                addressPojo.setName(cursor.getString(AT_name));
                addressPojo.setAddress1(cursor.getString(AT_address1));
                addressPojo.setAddress2(cursor.getString(AT_address2));
                addressPojo.setSubarea(cursor.getString(AT_subArea));
                addressPojo.setArea(cursor.getString(AT_area));
                addressPojo.setSubAreaCode(cursor.getInt(AT_subAreaCode));
                addressPojo.setPlaceCode(cursor.getInt(AT_placeCode));
                addressPojo.setAreaCode(cursor.getInt(AT_areaCode));
                list.add(addressPojo);




            }
            while (cursor.moveToNext());
        }
        Log.v("Rashan","Returning list");
        return list;
    }

    public List<AddressPojo> fetchAddressData(DbHelper db, int id) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();

        List<AddressPojo> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(ADDRESS_TABLE, addressList, addressList[6] + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                AddressPojo addressPojo = new AddressPojo();
                addressPojo.setId(cursor.getInt(AT_id));
                addressPojo.setName(cursor.getString(AT_name));
                addressPojo.setAddress1(cursor.getString(AT_address1));
                addressPojo.setAddress2(cursor.getString(AT_address2));
                addressPojo.setSubarea(cursor.getString(AT_subArea));
                addressPojo.setArea(cursor.getString(AT_area));
                addressPojo.setSubAreaCode(cursor.getInt(AT_subAreaCode));
                addressPojo.setPlaceCode(cursor.getInt(AT_placeCode));
                addressPojo.setAreaCode(cursor.getInt(AT_areaCode));
                list.add(addressPojo);
            }while (cursor.moveToNext());
        }

        return list;
    }






















    public void insertVendorDataList(DbHelper db, List<VendorPojo> list) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < list.size(); i++) {
            cv.put(vendorList[0], list.get(i).getId());
            cv.put(vendorList[1], list.get(i).getName());
            cv.put(vendorList[2], list.get(i).getGender());
            cv.put(vendorList[3], list.get(i).getPic());
            cv.put(vendorList[4], list.get(i).getCategory1());
            cv.put(vendorList[5], list.get(i).getCategory2());
            cv.put(vendorList[6], list.get(i).getCategory3());
            cv.put(vendorList[7], list.get(i).getCategory4());

            sqLiteDatabase.insert(VENDOR_TABLE, null, cv);
        }
    }


    public List<VendorPojo> fetchALLVendorData(DbHelper db) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        List<VendorPojo> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(VENDOR_TABLE, vendorList, null, null, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                VendorPojo vendorPojo = new VendorPojo();

                vendorPojo.setId(cursor.getInt(VT_id));
                vendorPojo.setName(cursor.getString(VT_name));
                vendorPojo.setGender(cursor.getString(VT_gender));
                vendorPojo.setPic(cursor.getString(VT_pic));
                vendorPojo.setCategory1(cursor.getInt(VT_category1));
                vendorPojo.setCategory2(cursor.getInt(VT_category2));
                vendorPojo.setCategory3(cursor.getInt(VT_category3));
                vendorPojo.setCategory4(cursor.getInt(VT_category4));

                list.add(vendorPojo);

            }
            while (cursor.moveToNext());
        }

        return list;
    }

    public void deleteAllVendorData(DbHelper db) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        sqLiteDatabase.delete(VENDOR_TABLE, null, null);
    }

    public VendorPojo fetchVendorData(DbHelper db, int id) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        VendorPojo vendorPojo = new VendorPojo();

        Cursor cursor = sqLiteDatabase.query(VENDOR_TABLE, vendorList, vendorList[0] + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            vendorPojo.setId(cursor.getInt(VT_id));
            vendorPojo.setName(cursor.getString(VT_name));
            vendorPojo.setPic(cursor.getString(VT_pic));
            vendorPojo.setCategory1(cursor.getInt(VT_category1));
            vendorPojo.setCategory2(cursor.getInt(VT_category2));
            vendorPojo.setCategory3(cursor.getInt(VT_category3));
            vendorPojo.setCategory4(cursor.getInt(VT_category4));
        }

        return vendorPojo;
    }


    public void insertSabziData(DbHelper db, SabziDetailsPojo sp) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(sabziList[0], sp.getId());
        cv.put(sabziList[1], sp.getName());
        cv.put(sabziList[2], sp.getCost());
        cv.put(sabziList[3], sp.getWeight());
        cv.put(sabziList[4], sp.getCategory());
        cv.put(sabziList[5], sp.getPic());
        cv.put(sabziList[6], sp.getHinglish());

        sqLiteDatabase.insert(SABZI_TABLE, null, cv);
    }

    public void insertSabziDataList(DbHelper db, List<SabziDetailsPojo> list) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < list.size(); i++) {
            cv.put(sabziList[0], list.get(i).getId());
            cv.put(sabziList[1], list.get(i).getName());
            cv.put(sabziList[2], list.get(i).getCost());
            cv.put(sabziList[3], list.get(i).getWeight());
            cv.put(sabziList[4], list.get(i).getCategory());
            cv.put(sabziList[5], list.get(i).getPic());
            cv.put(sabziList[6], list.get(i).getHinglish());

            sqLiteDatabase.insert(SABZI_TABLE, null, cv);
        }
    }


    public void updateSabziData(DbHelper db, SabziDetailsPojo sp, int id) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(sabziList[0], sp.getId());
        cv.put(sabziList[1], sp.getName());
        cv.put(sabziList[2], sp.getCost());
        cv.put(sabziList[3], sp.getWeight());
        cv.put(sabziList[4], sp.getCategory());
        cv.put(sabziList[5], sp.getPic());
        cv.put(sabziList[5], sp.getHinglish());

        sqLiteDatabase.update(SABZI_TABLE, cv, sabziList[0] + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteSabziData(DbHelper db, int id) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        sqLiteDatabase.delete(SABZI_TABLE, sabziList[0] + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteAllSabziData(DbHelper db) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        sqLiteDatabase.delete(SABZI_TABLE, null, null);
    }

    public float checkSabziData(DbHelper db, int id) {
        float weight = 0.0f;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(SABZI_TABLE, sabziList, sabziList[0] + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            weight = cursor.getFloat(CI_weight);
        }

        return weight;
    }

    public String fetchSabziDataPic(DbHelper db, String s) {
        String pic = "";
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(SABZI_TABLE, sabziList, sabziList[1] + "=?", new String[]{s}, null, null, null);
        if (cursor.moveToFirst()) {
            pic = cursor.getString(CI_pic);
        }

        return pic;
    }

    public List<SabziDetailsPojo> fetchALLSabziData(DbHelper db) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        List<SabziDetailsPojo> list = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(SABZI_TABLE, sabziList, null, null, null, null, null);
        if (cursor.moveToFirst()) {

            do {
                SabziDetailsPojo sabziDetailsPojo = new SabziDetailsPojo();

                sabziDetailsPojo.setId(cursor.getInt(CI_id));
                sabziDetailsPojo.setName(cursor.getString(CI_name));
                sabziDetailsPojo.setCost(cursor.getFloat(CI_cost));
                sabziDetailsPojo.setWeight(cursor.getFloat(CI_weight));
                sabziDetailsPojo.setCategory(cursor.getInt(CI_category));
                sabziDetailsPojo.setPic(cursor.getString(CI_pic));
                sabziDetailsPojo.setHinglish(cursor.getString(CI_hinglish));

                list.add(sabziDetailsPojo);

            }
            while (cursor.moveToNext());
        }
        return list;
    }


    public SabziDetailsPojo fetchSabziData(DbHelper db, int id) {
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        SabziDetailsPojo sabziDetailsPojo = new SabziDetailsPojo();

        Cursor cursor = sqLiteDatabase.query(SABZI_TABLE, sabziList, cartList[0] + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {

            sabziDetailsPojo.setId(cursor.getInt(CI_id));
            sabziDetailsPojo.setName(cursor.getString(CI_name));
            sabziDetailsPojo.setCost(cursor.getFloat(CI_cost));
            sabziDetailsPojo.setWeight(cursor.getFloat(CI_weight));
            sabziDetailsPojo.setCategory(cursor.getInt(CI_category));
            sabziDetailsPojo.setPic(cursor.getString(CI_pic));
            sabziDetailsPojo.setHinglish(cursor.getString(CI_hinglish));

        }

        return sabziDetailsPojo;
    }


}

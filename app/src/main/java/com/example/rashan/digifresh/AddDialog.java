package com.example.rashan.digifresh;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rashan on 11-01-2017.
 */
public class AddDialog {


    boolean flag;
    final String b[]={"Select Subarea"};
    AppCompatButton save,cancel;
    EditText name,a1,a2;
    Spinner areaSpinner,subAreaSpinner;
    RecyclerView addressRecyclerView;
    Dialog dialog;
    AddressAdapter addressAdapter;
    int id;
    // List<SubAreaPojo> subAreaPojoList;
    DbHelper db;
    Activity activity;

    List<PlacePojo> areas, subAreas;
    Map<Integer, List<Integer>> placeCodeMap;
    Map<Integer, List<Integer>> areaMap;
    List<AreaPojo> areaPojoList;
    int selectedSubareaCode;



    int areaSpinnerPostition,subAreaSpinnerPosition;
    String selectedArea,selectedSubArea;

    public AddDialog(Activity activity, AddressAdapter addressAdapter)
    {

        db=new DbHelper(activity.getApplicationContext());
        this.addressAdapter=addressAdapter;
        this.addressRecyclerView=(RecyclerView)activity.findViewById(R.id.addressRecyclerView);
        this.activity=activity;
        //subAreaPojoList=new ArrayList<>();
        Object[] objects=SerializeData.read(activity,"areas");
        areaPojoList= (List<AreaPojo>) objects[0];



    }

    public  void setDialog(boolean f,AddressPojo addressPojo)
    {

        flag=f;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.content_add_address);
        dialog.setCancelable(false);
        areaSpinnerPostition=-1;
        subAreaSpinnerPosition=-1;
        id=-1;
        selectedArea="";
        selectedSubArea="";
        selectedSubareaCode=-1;

        save = (AppCompatButton) dialog.findViewById(R.id.save);
        cancel=(AppCompatButton)dialog.findViewById(R.id.cancel);


        name=(EditText)dialog.findViewById(R.id.name);
        a1=(EditText)dialog.findViewById(R.id.a1);
        a2=(EditText)dialog.findViewById(R.id.a2);
        areaSpinner=(Spinner) dialog.findViewById(R.id.areaSpinner);
        subAreaSpinner=(Spinner) dialog.findViewById(R.id.subAreaSpinner);


        /////////
        areaPojoList.add(0,new AreaPojo("Select Area",-1,null));


        areaSpinner.setAdapter(new AreaSpinnerAdapter(activity,areaPojoList));
        areaSpinner.setVisibility(View.VISIBLE);
       subAreaSpinner.setVisibility(View.VISIBLE);




        SpinnerInteractionListener listener = new SpinnerInteractionListener();
        listener.setFlag(1);
        areaSpinner.setOnTouchListener(listener);
        areaSpinner.setOnItemSelectedListener(listener);



        SpinnerInteractionListener listener1 = new SpinnerInteractionListener();
        listener1.setFlag(2);
        subAreaSpinner.setOnTouchListener(listener1);
        subAreaSpinner.setOnItemSelectedListener(listener1);



     /*   areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    AreaPojo aPojo = ((AreaSpinnerAdapter) areaSpinner.getAdapter()).getPojo(i);
                    selectedArea = aPojo.getName();
                    int x = aPojo.getAreaCode();


                    subAreaSpinner.setAdapter(new SubAreaSpinnerAdapter(activity, aPojo.getSubAreaPojoList()));
                    subAreaSpinner.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        subAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                SubAreaPojo sPojo=((SubAreaSpinnerAdapter) subAreaSpinner.getAdapter()).getPojo(i);
                selectedSubArea = sPojo.getName();
                selectedSubareaCode =sPojo.getPlaceCode();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("Rashan","save button listener");
                if(name.getText().toString().trim().isEmpty())
                    Toast.makeText(activity,"Please Enter Name",Toast.LENGTH_SHORT).show();
                else if(a1.getText().toString().trim().isEmpty())
                    Toast.makeText(activity,"Please Enter ".concat(a1.getHint().toString()),Toast.LENGTH_SHORT).show();
                else if(a2.getText().toString().trim().isEmpty())
                    Toast.makeText(activity,"Please Enter ".concat(a2.getHint().toString()),Toast.LENGTH_SHORT).show();
                else if( areaSpinner.getSelectedItemPosition()==0)
                    Toast.makeText(activity,"Please Choose Area",Toast.LENGTH_SHORT).show();
                    // else if(subAreaSpinner.getSelectedItemPosition()==0)
                else if( subAreaSpinner.getSelectedItemPosition()==0)
                    Toast.makeText(activity,"Please Choose Subarea",Toast.LENGTH_SHORT).show();
                else
                    save(flag);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // putDetails(new AddressPojo(0,"Rashan","A","B","GP Block","Pitampua",));
             //   subAreaSpinner.setSelection(3,false);
                dialog.dismiss();
            }
        });


        dialog.show();


        if(addressPojo!=null)
        putDetails(addressPojo);
    }


    public void save(boolean flag)
    {

        AreaPojo areaPojo= ((AreaSpinnerAdapter)areaSpinner.getAdapter()).getPojo(areaSpinner.getSelectedItemPosition());
        SubAreaPojo subAreaPojo= ((SubAreaSpinnerAdapter)subAreaSpinner.getAdapter()).getPojo(subAreaSpinner.getSelectedItemPosition());



        if(flag)
        {

            db.deleteAddressData(db,id );
            db.insertAddressData(db, new AddressPojo(id, name.getText().toString(), a1.getText().toString(), a2.getText().toString(),subAreaPojo.getName(),areaPojo.getName(),subAreaPojo.getSubAreaCode(),subAreaPojo.getPlaceCode(),areaPojo.getAreaCode()));
        }
        else
        {
            db.insertAddressData(db, new AddressPojo(db.fetchALLAddressData(db).size(), name.getText().toString(), a1.getText().toString(), a2.getText().toString(),subAreaPojo.getName(),areaPojo.getName(),subAreaPojo.getSubAreaCode(),subAreaPojo.getPlaceCode(),areaPojo.getAreaCode()));
        }


        addressAdapter=new AddressAdapter(activity,db.fetchALLAddressData(db));

        addressRecyclerView.setAdapter(addressAdapter);



        dialog.dismiss();


    }



    public void putDetails(AddressPojo pojo) {
        name.setText(pojo.getName());
        a1.setText(pojo.getAddress1());
        a2.setText(pojo.getAddress2());
        id = pojo.getId();

        try
        {

            synchronized (this) {
                for (int i = 0; i < areaPojoList.size(); i++) {
                    if (areaPojoList.get(i).getAreaCode() == pojo.getAreaCode()) {
                        areaSpinner.setSelection(i, false);

                        //List<SubAreaPojo> list = areaPojoList.get(i).getSubAreaPojoList();


                        /////
                        List<SubAreaPojo> list = new ArrayList<>();
                        list.addAll(areaPojoList.get(i).getSubAreaPojoList());
                        list.add(0,new SubAreaPojo("Select Subarea",-1,-1));



                        subAreaSpinner.setAdapter(new SubAreaSpinnerAdapter(activity, list));

                        for (int j = 0; j < list.size(); j++) {
                            if (list.get(j).getSubAreaCode() == pojo.getSubAreaCode()) {
                                //subAreaSpinner.setSelection(j);
                                //subAreaSpinner.setSelection(j);


                                subAreaSpinner.setSelection(j, false);

                                break;
                            }

                        }


                        break;

                    }
                }
            }



        } catch (Exception e)
        {
            e.printStackTrace();
        }



    }


    public void delete(int id)
    {
        db.deleteAddressData(db,id);
        addressAdapter=new AddressAdapter(activity,db.fetchALLAddressData(db));

        addressRecyclerView.setAdapter(addressAdapter);

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
                        subAreaSpinner.setVisibility(View.VISIBLE);
                        onAreaChange(pos);

                    }
                    else {
                        subAreaSpinner.setSelection(0);
                        subAreaSpinner.setVisibility(View.GONE);



                    }
                }
                else if(flag==2)
                {
                    if (pos!=0) {
                        onSubAreaChange(pos);
                    }
                    else
                    {

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
        subAreaSpinner.setAdapter(new SubAreaSpinnerAdapter(activity,list));


        //subAreaSpinner.setAdapter(new SubAreaSpinnerAdapter(activity,aPojo.getSubAreaPojoList()));
        subAreaSpinner.setVisibility(View.VISIBLE);

    }

    public void onSubAreaChange(int i)
    {
        SubAreaPojo sPojo=((SubAreaSpinnerAdapter) subAreaSpinner.getAdapter()).getPojo(i);
        selectedSubArea = sPojo.getName();
        selectedSubareaCode =sPojo.getPlaceCode();

    }





}

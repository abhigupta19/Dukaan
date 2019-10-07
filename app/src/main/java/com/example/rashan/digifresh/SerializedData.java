package com.example.rashan.digifresh;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Rashan on 03-01-2017.
 */
public class SerializedData  {





    public static void save(Activity activity,Object[] objects) {

        try {
            File file = new File(activity.getFilesDir().toString().concat("/area.ser"));
            FileOutputStream f_out = new FileOutputStream(file);

            ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

           obj_out.writeObject(objects);
            obj_out.flush();
            obj_out.close();
            f_out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static Object[] read(Activity activity) {

        try {
            File file = new File(activity.getFilesDir().toString().concat("/area.ser"));
            FileInputStream f_in = new FileInputStream(file);


            ObjectInputStream obj_in =new ObjectInputStream(f_in);
            Object[] objects = (Object[]) obj_in.readObject();
          /*  for (int i=0;i<objects.length;i++)
            {
                Object object=objects[i];
                List<PlacePojo> list=(List<PlacePojo>)object;
                Log.v("Rashan",list.size()+"");
                for (int j=0;j<list.size();j++)
                {
                    Log.v("Rashan",list.get(j).getName());
                }
                break;


            }*/


            return objects;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }




}

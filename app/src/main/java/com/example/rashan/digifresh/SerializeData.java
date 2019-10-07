package com.example.rashan.digifresh;

import android.app.Activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Rashan on 03-01-2017.
 */
public class SerializeData {





    public static void save(Activity activity,Object[] objects,String name) {

        String string="/"+name+".ser";
        try {
            File file = new File(activity.getFilesDir().toString().concat(string));
            FileOutputStream f_out = new FileOutputStream(file);

            ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

           obj_out.writeObject(objects);
            obj_out.flush();
            obj_out.close();
            f_out.close();


        }
       catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static Object[] read(Activity activity,String name) {
        String string="/"+name+".ser";

        try {
            File file = new File(activity.getFilesDir().toString().concat(string));
            FileInputStream f_in = new FileInputStream(file);


            ObjectInputStream obj_in =new ObjectInputStream(f_in);
            Object[] objects = (Object[]) obj_in.readObject();



            return objects;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }




}

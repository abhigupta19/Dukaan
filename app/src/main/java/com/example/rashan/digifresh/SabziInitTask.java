package com.example.rashan.digifresh;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URL;

/**
 * Created by Rashan on 27-10-2016.
 */
public class SabziInitTask extends AsyncTask<Void,Void,Void> {
    Activity activity;
    String result;
DbHelper db;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public SabziInitTask(Activity activity) {
        this.activity = activity;
        db = new DbHelper(activity.getApplicationContext());
        preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        editor = preferences.edit();
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://www.vegvendors.in/android/vegnmlist.php");
            throw new RuntimeException();



        } catch (Exception e) {
            Log.v("Rashan","Here");
           /// activity.startActivity(new Intent(activity, NoInternetActivity.class));
           // activity.finish();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


    if(result==null && false)
    {
      //  editor.putBoolean(activity.getResources().getString(R.string.SP_FIRST_LOGIN), true);
       // activity.startActivity(new Intent(activity,NoInternetActivity.class));
      //  activity.finish();
    }
        else
    {

result="{\"vegnmlist\":[{\"1\":[{\"name\":\"Potato (\\u0906\\u0932\\u0942)\",\"hinglish\":\"Aalu\",\"hindi_name\":\"allu allo alu alloo aalu\"}]},{\"2\":[{\"name\":\"Onion (\\u092a\\u094d\\u092f\\u093e\\u095b)\",\"hinglish\":\"Pyaaz\",\"hindi_name\":\"pyaaz pyaz pyaaj pyaj\"}]},{\"3\":[{\"name\":\"Tomato (\\u091f\\u092e\\u093e\\u091f\\u0930)\",\"hinglish\":\"Tamatar\",\"hindi_name\":\"tamatar\"}]},{\"4\":[{\"name\":\"Cabbage (\\u092a\\u0924\\u094d\\u0924\\u093e \\u0917\\u094b\\u092d\\u0940)\",\"hinglish\":\"Patta gobhi\",\"hindi_name\":\"bandh gobhi patta gobhi band gobi\"}]},{\"5\":[{\"name\":\"Carrot (\\u0917\\u093e\\u091c\\u0930)\",\"hinglish\":\"Gajar\",\"hindi_name\":\"gajjar gaajar gajar gazar gaazar\"}]},{\"6\":[{\"name\":\"Radish (\\u092e\\u0942\\u0932\\u0940)\",\"hinglish\":\"Mooli\",\"hindi_name\":\"mooli\"}]},{\"7\":[{\"name\":\"Peas (\\u0939\\u0930\\u0940 \\u092e\\u091f\\u0930)\",\"hinglish\":\"Matar\",\"hindi_name\":\"matar\"}]},{\"8\":[{\"name\":\"French Beans (\\u092b\\u094d\\u0930\\u093e\\u0902\\u0938 \\u092c\\u0940\\u0928\\u094d\",\"hinglish\":\"French beans\",\"hindi_name\":\"fali\"}]},{\"9\":[{\"name\":\"Jackfruit (\\u0915\\u091f\\u0939\\u0932)\",\"hinglish\":\"Kathal\",\"hindi_name\":\"kathal\"}]},{\"10\":[{\"name\":\"Ridge Gourd (\\u0924\\u094b\\u0930\\u0940\\/ \\u0924\\u0941\\u0930\\u0908)\",\"hinglish\":\"Tori\",\"hindi_name\":\"tori\"}]},{\"11\":[{\"name\":\"Sweet Corn (\\u0938\\u094d\\u0935\\u0940\\u091f \\u0915\\u0949\\u0930\\u094d\\u0928)\",\"hinglish\":\"Sweet corn\",\"hindi_name\":\"Sweet Corn Baby Corn\"}]},{\"12\":[{\"name\":\"Broccoli (\\u092c\\u094d\\u0930\\u094b\\u0915\\u094d\\u0915\\u094b\\u0932\\u0940)\",\"hinglish\":\"Broccoli\",\"hindi_name\":\"Broccoli\"}]},{\"13\":[{\"name\":\"Cucumber (\\u0916\\u0940\\u0930\\u093e)\",\"hinglish\":\"Kheera\",\"hindi_name\":\"kheera\"}]},{\"14\":[{\"name\":\"Sem (??? ?? ???)\",\"hinglish\":\"Sem\",\"hindi_name\":\"sem\"}]},{\"15\":[{\"name\":\"Capsicum (\\u0936\\u093f\\u092e\\u0932\\u093e \\u092e\\u093f\\u0930\\u094d\\u091a)\",\"hinglish\":\"Hari shimla mirch\",\"hindi_name\":\"shimla mirch\"}]},{\"16\":[{\"name\":\"Cauliflower (\\u092b\\u0942\\u0932 \\u0917\\u094b\\u092d\\u0940)\",\"hinglish\":\"Phool gobhi\",\"hindi_name\":\"fool gobi Phool gobhi\"}]},{\"17\":[{\"name\":\"Sarso Saag ( ????? ?? ???)\",\"hinglish\":\"Sarso saag\",\"hindi_name\":\"Sarso Saag\"}]},{\"18\":[{\"name\":\"Spinach (\\u092a\\u093e\\u0932\\u0915)\",\"hinglish\":\"Palak\",\"hindi_name\":\"palak paalak\"}]},{\"19\":[{\"name\":\"Ladyfinger (\\u092d\\u093f\\u0928\\u094d\\u0921\\u0940\\/ \\u0913\\u0915\\u0930\\u093e)\",\"hinglish\":\"Bhindi\",\"hindi_name\":\"bhindi\"}]},{\"20\":[{\"name\":\"Cluster Beans (????? ?? ???))\",\"hinglish\":\"Gwar Phali\",\"hindi_name\":\"gwar phali fali guar\"}]},{\"21\":[{\"name\":\"Brinjal (\\u092c\\u0948\\u0902\\u0917\\u0928)\",\"hinglish\":\"Baingan\",\"hindi_name\":\"baingan\"}]},{\"22\":[{\"name\":\"Round Gourd (\\u091f\\u093f\\u0902\\u0921\\u093e)\",\"hinglish\":\"Tinda\",\"hindi_name\":\"tinda\"}]},{\"23\":[{\"name\":\"Pumpkin (\\u0938\\u0940\\u0924\\u093e\\u092b\\u0932\\/ \\u0915\\u0926\\u094d\\u0926\\u0942)\",\"hinglish\":\"Sitaphal\",\"hindi_name\":\"kaddu\"}]},{\"24\":[{\"name\":\"Bottle Gourd (\\u0918\\u093f\\u092f\\u093e \\/ \\u0932\\u094c\\u0915\\u0940)\",\"hinglish\":\"Gheeya\",\"hindi_name\":\"loki ghiya lauki\"}]},{\"25\":[{\"name\":\"Lettuce (???? ?????)\",\"hinglish\":\"Lettuce\",\"hindi_name\":\"salaad patta\"}]},{\"26\":[{\"name\":\"Mushroom (\\u092e\\u0936\\u0930\\u0941\\u092e)\",\"hinglish\":\"Mushroom\",\"hindi_name\":\"Mushroom\"}]},{\"27\":[{\"name\":\"Olives (?????)\",\"hinglish\":\"Olives\",\"hindi_name\":\"Olives Jaitun\"}]},{\"28\":[{\"name\":\"Lemon (\\u0928\\u0940\\u0902\\u092c\\u0942)\",\"hinglish\":\"Nimbu\",\"hindi_name\":\"nimbo nimboo neemboo\"}]},{\"29\":[{\"name\":\"Ginger (\\u0905\\u0926\\u0930\\u0915)\",\"hinglish\":\"Adarak\",\"hindi_name\":\"adrak\"}]},{\"30\":[{\"name\":\"Peppermint (\\u092a\\u0941\\u0926\\u0940\\u0928\\u093e)\",\"hinglish\":\"Pudina\",\"hindi_name\":\"pudeena\"}]},{\"31\":[{\"name\":\"Coriander Leaves (\\u0939\\u0930\\u093e \\u0927\\u0928\\u093f\\u092f\\u093e)\",\"hinglish\":\"Hara dhaniya\",\"hindi_name\":\"dhaniya\"}]},{\"32\":[{\"name\":\"Gooseberry (\\u0906\\u0901\\u0935\\u0932\\u093e)\",\"hinglish\":\"Aanwla\",\"hindi_name\":\"amla \"}]},{\"33\":[{\"name\":\"Sweet Potato (\\u0936\\u0915\\u0930\\u0915\\u0902\\u0926)\",\"hinglish\":\"Shakarkand\",\"hindi_name\":\"sakarkand shakarkand\"}]},{\"34\":[{\"name\":\"Turnip (\\u0936\\u0932\\u0917\\u092e)\",\"hinglish\":\"Shalgam\",\"hindi_name\":\"shalgam\"}]},{\"35\":[{\"name\":\"Garlic (\\u0932\\u0939\\u0938\\u0941\\u0928)\",\"hinglish\":\"Lahsun\",\"hindi_name\":\"lehsoon lason\"}]},{\"36\":[{\"name\":\"Bitter Gourd (\\u0915\\u0930\\u0947\\u0932\\u093e)\",\"hinglish\":\"Karela\",\"hindi_name\":\"karela\"}]},{\"37\":[{\"name\":\"Chillies (\\u092e\\u093f\\u0930\\u094d\\u091a)\",\"hinglish\":\"Mirch\",\"hindi_name\":\"mirch\"}]},{\"38\":[{\"name\":\"Bell Pepper (??? ?? ???? ????? ????? )\",\"hinglish\":\"Lal-peeli shimla mirch\",\"hindi_name\":\"shimla mirch\"}]},{\"39\":[{\"name\":\"Methi (\\u092e\\u0947\\u0925\\u0940)\",\"hinglish\":\"Methi\",\"hindi_name\":\"Methi\"}]},{\"40\":[{\"name\":\"Arbi (\\u0905\\u0930\\u092c\\u0940)\",\"hinglish\":\"Arbi\",\"hindi_name\":\"Arbi\"}]},{\"41\":[{\"name\":\"Kachi Ambi (\\u0915\\u091a\\u094d\\u091a\\u0940 \\u0905\\u092e\\u094d\\u092c\\u0940)\",\"hinglish\":\"Kacchi ambi\",\"hindi_name\":\"kaccha aam\"}]}]}";
        try
        {
            Log.v("Rashan",result);
            // String category="";
            String name,hinglish;
            //JsonParser jsonParser=new JsonParser();
            //JsonObject jsonObjectt=jsonParser.parse(result).getAsJsonObject();
            //String timestamp=jsonObjectt.get("timestamp").getAsString();
           // JsonObject jsonObject=jsonObjectt.get("Json").getAsJsonObject();
            JsonObject jsonObject=new JsonParser().parse(result).getAsJsonObject();
            JsonArray jsonArray=jsonObject.get("vegnmlist").getAsJsonArray();
            for(int i=0;i<jsonArray.size();i++)
            {

                jsonObject=jsonArray.get(i).getAsJsonObject();
            /*Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();

            for (Map.Entry<String, JsonElement> entryChild : set) {
                category=entryChild.getKey();
            }


            jsonObject=jsonObject.get(category).getAsJsonArray().get(0).getAsJsonObject();  //remove .getAsJsonArray().get(0) no use change script

          //  Log.v("Rashan",category+" "+name+ " "+hinglish);
            db.insertSabziInitData(db,Integer.valueOf(category),name,hinglish);
*/


                try
                {
                    jsonObject=jsonObject.get(String.valueOf(i+1)).getAsJsonArray().get(0).getAsJsonObject();
                    //jsonObject=jsonObject.get("sabziNames").getAsJsonArray().get(0).getAsJsonObject();
                    name=jsonObject.get("name").getAsString();
                    hinglish=jsonObject.get("hindi_name").getAsString();
                    db.insertSabziInitData(db,i+1,name,hinglish);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



            }
            editor.putBoolean(activity.getResources().getString(R.string.SP_SABZI_INIT),false);
         //   editor.putString("timestamp_vegnmlist",timestamp);

            editor.commit();
        }
        catch (Exception e)
        {

        }

    }

    }
}
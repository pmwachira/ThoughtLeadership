package mushirih.thoughtleadership2;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by p-tah on 30/03/2016.
 */
public class Profs {


    public static final int NO_NAVIGATION = -1;
    private static final String TAG=ListCred.class.getSimpleName();
    private static final String  URL_FEED = "www.profiles I Will Make.com";
    private ArrayList<ProfItem> profSource;
    private DrawableProvider mProvider;
    private Context contextHere;
    Cache cachee;
    public Profs(Context context) {
        contextHere=context;
        mProvider = new DrawableProvider(context);
        cachee= AppController.getInstance().getRequestQueue().getCache();


        loadCache(URL_FEED);

        profSource = new ArrayList<ProfItem>();
         /*STATIC INIT
                profSource.add(itemwithname("lol","lol","lol"));
                */

    }

    public void loadCache(String URL_FEED) {
        Cache.Entry entry=cachee.get(URL_FEED);//THROWING A NULL

        if(entry!=null){
            try {
                String data=new String(entry.data,"UTF-8");
                try {

                    parseJsonFeed(new JSONObject(data));

                } catch (JSONException e) {
                    //loader_show=true;
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                // loader_show=true;
                e.printStackTrace();
            }
        }else {
            // loader_show=true;
            Log.d("NULL ENTRY", "WORKING TILL HERE****************************************************");
            //SET FLAG FOR LOADER N TOAST

            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL_FEED, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {


                    VolleyLog.d(TAG, "Response: " + jsonObject.toString());
                    if (jsonObject != null) {
                        parseJsonFeed(jsonObject);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d(TAG, "Error: " +volleyError.getMessage());
                }
            });

            AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        }
    }

    private void parseJsonFeed(JSONObject jsonObject) {
        try {
            JSONArray feedArray=jsonObject.getJSONArray("profiles");

            for(int i=0;i<feedArray.length();i++) {
                JSONObject jsonObj = (JSONObject) feedArray.get(i);
                String name="";

                String workTitle="";

                String displaypic = "";
                String strip="";

                try {
                    name= jsonObj.getString("name");
                    workTitle= jsonObj.getString("workTitle");
                    displaypic= jsonObj.getString("displayPic");
                }catch (Exception e){

                }
                try {
                    strip = jsonObj.getString("displaypic");
                    displaypic = strip.replace("\\", "");


                    if (strip.isEmpty()) {
                        displaypic = "DEFAULT";
                    }
                }catch (Exception e){
                    displaypic = "DEFAULT";
                }



                profSource.add(itemwithname(name,workTitle,displaypic));
            }

        } catch (JSONException e) {
            Log.d("PARSEJSONFEED", "i ENCOUNTERED AN ERROR");

            e.printStackTrace();
        }
        //*************************************************
    }


    public int getCount() {
        return profSource.size();
    }

    public ProfItem getItem(int position) {
        return profSource.get(position);
    }
    public  ProfItem itemwithname(String nam, String titl, String pro) {
        String name = null;

        String title=null;
        String prof=null;


        name = nam;
        title=titl;
        prof=pro;

 return new ProfItem(name,title,prof);
    }



}

package mushirih.thoughtleadership2;

import android.app.ProgressDialog;
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
 * Created by p-tah on 12/10/2015.
 */
public class DataSource {
    public static boolean loader_show= false;
    public static final int NO_NAVIGATION = -1;
    private static final String TAG=MainActivity.class.getSimpleName();
    private ArrayList<DataItem> mDataSource;
    private DrawableProvider mProvider;
    public static ProgressDialog pDialog;
    private Context contextHere;
    Cache cache;

    public DataSource(Context context,String URL_FEED) {
        contextHere=context;
        mProvider = new DrawableProvider(context);
        mDataSource = new ArrayList<DataItem>();
     
//*********************************************************

         cache= AppController.getInstance().getRequestQueue().getCache();


        loadCache(URL_FEED);

    }

    public void refresh(String URL_FEED){
        cache.clear();
        loadCache(URL_FEED);
    }
    public void loadCache(String URL_FEED) {
        Cache.Entry entry=cache.get(URL_FEED);//THROWING A NULL

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
            JSONArray feedArray=jsonObject.getJSONArray("thoughtleadership");

            for(int i=0;i<feedArray.length();i++) {

                JSONObject jsonObj = (JSONObject) feedArray.get(i);
                String strip = null;
                String displaypic = null;
                String name="";
                String email="";
                String workTitle="";
                try {
                   name= jsonObj.getString("name");
                   email= jsonObj.getString("email");
                   workTitle= jsonObj.getString("workTitle");
                }catch (Exception e){

                }
                String reg = jsonObj.getString("region");
                if (reg.isEmpty()) {
                    reg = "KPMG";
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



                mDataSource.add(itemwithname(jsonObj.getString("title"),jsonObj.getString("content"),reg,jsonObj.getString("owner"),displaypic,jsonObj.getString("link"),name,email,workTitle));
    Log.d("CHECK",jsonObj.getString("link"));
            }

        } catch (JSONException e) {
            Log.d("PARSEJSONFEED", "i ENCOUNTERED AN ERROR");

            e.printStackTrace();
        }
        //*************************************************
    }



    public int getCount() {
        return mDataSource.size();
    }

    public DataItem getItem(int position) {
        return mDataSource.get(position);
    }
/*
    private DataItem itemFromType(int type) {
        String label = null;
        String content="";
        String region="";
        String owner="";
        String downloadUrl="";
        Drawable drawable = null;
        switch (type) {
            case DrawableProvider.SAMPLE_RECT:
                label = "Rectangle with Text";
                drawable = mProvider.getRect(label.substring(0,1));
                break;
            case DrawableProvider.SAMPLE_ROUND_RECT:
                label = "Round Corner with Text";
                drawable = mProvider.getRoundRect("B");
                break;
            case DrawableProvider.SAMPLE_ROUND:
                label = "Round with Text";
                drawable = mProvider.getRound("C");
                break;
            case DrawableProvider.SAMPLE_RECT_BORDER:
                label = "Rectangle with Border";
                drawable = mProvider.getRectWithBorder("D");
                break;
            //USING THIS FOR THE WORK PROJECT
            case DrawableProvider.SAMPLE_ROUND_RECT_BORDER:
                label = "Round Corner with Border";
                drawable = mProvider.getRoundRectWithBorder(label.substring(0,1));
                break;
            case DrawableProvider.SAMPLE_ROUND_BORDER:
                label = "Round with Border";
                drawable = mProvider.getRoundWithBorder("F");
                break;
            case DrawableProvider.SAMPLE_MULTIPLE_LETTERS:
                label = "Support multiple letters";
                drawable = mProvider.getRectWithMultiLetter("ABC");
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_FONT:
                label = "Support variable font styles";
                drawable = mProvider.getRoundWithCustomFont();
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_SIZE:
                label = "Support for custom size";
                drawable = mProvider.getRectWithCustomSize();
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_ANIMATION:
                label = "Support for animations";
                drawable = mProvider.getRectWithAnimation();
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_MISC:
                label = "Miscellaneous";
                drawable =  mProvider.getRect("\u03c0");
                type = NO_NAVIGATION;
                break;
        }
        return new DataItem(label,content,region,owner,downloadUrl, drawable);
    }
    */
    private DataItem itemwithname(String title,String content, String region, String owner, String displaypic, String download,String name, String email, String workTitle) {
        String label = null;

        String myContent=null;
        String myRegion=null;
        String owneR=null;
        String downloadUrl=null;
        String displayPic=null;

                label = title;
                myRegion=region;
                owneR=owner;
               // drawable = mProvider.getRoundRectWithBorder(label.substring(0,1));

                 myContent=content;
                displayPic=displaypic;
                 downloadUrl=download;





        return new DataItem(label,myContent,myRegion,owneR,downloadUrl, displayPic,name,email,workTitle);
    }



}

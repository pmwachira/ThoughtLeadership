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
 * Created by p-tah on 12/10/2015.
 */
public class EventsSource {

    public static final int NO_NAVIGATION = -1;
    private static final String TAG=MainActivity.class.getSimpleName();
    private ArrayList<EventItem> mEventsSource;
    String date,month,year,owner,title,desc,bookurl;
    private Context contextHere;
    public EventsSource(Context context,String URL_FEED) {
        contextHere=context;

        mEventsSource = new ArrayList<EventItem>();

//*********************************************************
        Cache cache= AppController.getInstance().getRequestQueue().getCache();


        Cache.Entry entry=cache.get(URL_FEED);//THROWING A NULL


        if(entry!=null){
            try {
                String data=new String(entry.data,"UTF-8");
                try {

                    parseJsonFeed(new JSONObject(data));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {

            Log.d("NULL ENTRY", "WORKING TILL HERE**********Existing list len " + mEventsSource.size());




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
            JSONArray feedArray=jsonObject.getJSONArray("events");

            for(int i=0;i<feedArray.length();i++) {

                JSONObject jsonObj = (JSONObject) feedArray.get(i);
                String date = jsonObj.getString("date");
                String month = jsonObj.getString("month");
                String year = jsonObj.getString("year");
                String title =jsonObj.getString("title");
                String desc = jsonObj.getString("desc");
                String owner = "mushirih";
                String bookurl = jsonObj.getString("bookurl");
                Log.d("LOG RECIOEVED DATA","I have : "+date +month+title);
                
                    String url = bookurl.replace("\\", "");
                 if(date.isEmpty()||date.equals("")){
                      date = "null";
                      month = "null";
                     year = "null";
                     title ="null";
                     desc = "null";
                      owner = "null";
                      bookurl = "null";
                 }


               



                mEventsSource.add(itemwithname(date, month, year, title, desc, owner,bookurl));
            }

        } catch (JSONException e) {
            Log.d("PARSEJSONFEED EVENTS", "i ENCOUNTERED AN ERROR");

            e.printStackTrace();
        }
        //*************************************************No network

    }



    public int getCount() {
        return mEventsSource.size();
    }

    public EventItem getItem(int position) {
        return mEventsSource.get(position);
    }
    /*
        private EventItem itemFromType(int type) {
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
            return new EventItem(label,content,region,owner,downloadUrl, drawable);
        }
        */
    private EventItem itemwithname(String date, String month, String year, String title, String desc, String owner, String bookurl) {
        String dat = null;
        String mont = null;
        String yea = null;
        String titl =null;
        String des = null;
        String owne = null;
        String bookur = null;

       dat=date;
        mont=month;
        yea=year;
        titl=title;
        des=desc;
        owne=owner;
        bookur=bookurl;




        return new EventItem(dat,mont,yea,titl,des,owne,bookur);
    }



}

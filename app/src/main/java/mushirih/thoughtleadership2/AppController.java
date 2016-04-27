package mushirih.thoughtleadership2;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by p-tah on 01/11/2015.
 */
public class AppController extends Application {
    public static final String TAG=AppController.class.getSimpleName();

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    LruBitmapCache lruBitmapCache;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public static synchronized AppController getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader(){
        getRequestQueue();
        if(imageLoader==null){
            getLruBitmapCache();
            imageLoader=new ImageLoader(this.requestQueue,lruBitmapCache);
        }
        return this.imageLoader;
    }

    private LruBitmapCache getLruBitmapCache() {
        if(lruBitmapCache==null){
            lruBitmapCache=new LruBitmapCache();
        }
        return this.lruBitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> req,String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}

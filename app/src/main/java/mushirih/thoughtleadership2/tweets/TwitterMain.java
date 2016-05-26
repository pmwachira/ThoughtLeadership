package mushirih.thoughtleadership2.tweets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import mushirih.thoughtleadership2.Connect;
import mushirih.thoughtleadership2.R;

public class TwitterMain extends AppCompatActivity {
    int flag=0;
   LinearLayout test;
    Handler handler;
    Runnable r;
    ListView goog,goo;
    ProgressDialog pDialog;
    final static String TWITTER_API_KEY = "96XLyNA3JYzDGgjqjX1O2lo3j";
    final static String TWITTER_API_SECRET = "wvEL3nh0kGgKFFXXNczfOmOOmyT7iBNnf2UVEzaNPOMQtAe7aC";
    final static String twitterScreenName = "KPMGEastAfrica";
    final static String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twittermain);
        test= (LinearLayout) findViewById(R.id.test);
        final SwipeRefreshLayout swipe=(SwipeRefreshLayout)findViewById(R.id.sw);
         goog= (ListView) findViewById(R.id.listVV);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                new TwitterAsyncTask().execute(twitterScreenName, this, goog);
                swipe.setRefreshing(false);
            }
        });
        supportActionBar();
        goog.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                goog.setPadding(0, 4, 0, 0);
                swipe.setEnabled(firstVisibleItem == 0 ? true : false);
                //  swipe.setEnabled(listView.getFirstVisiblePosition()==0&&listView.getChildAt(0).getTop()==0);
            }
        });

        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
        if (androidNetworkUtility.isConnected(this)) {

            new TwitterAsyncTask().execute(twitterScreenName,this,goog);
        } else {


            flag=1;
            test.setBackgroundResource(R.drawable.error);
            Snackbar.make(test, "Please check your internet connection and try again", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            if (androidNetworkUtility.isConnected(this)) {
                swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipe.setRefreshing(true);
                        new TwitterAsyncTask().execute(twitterScreenName, this, goog);
                        swipe.setRefreshing(false);
                    }
                });
            }
             handler=new Handler();
             r=new Runnable() {
                 @Override
                 public void run() {
                     startActivity(new Intent(getBaseContext(), Connect.class));
                 }
             };
            handler.postDelayed(r,5000);
        }
    }

    private class TwitterAsyncTask  extends AsyncTask<Object, Void, ArrayList<TwitterTweet>> {


            @Override
            protected void onPreExecute() {

                pDialog = new ProgressDialog(TwitterMain.this);
                pDialog.setCancelable(false);

                pDialog.setMessage("Loading tweets.Please wait ...");
//            pDialog.setIndeterminate(true);
//              pDialog.setCancelable(false);
                pDialog.show();

            }

            @Override
            protected ArrayList<TwitterTweet> doInBackground(Object... params) {
                ArrayList<TwitterTweet> twitterTweets = new ArrayList<>();


                goo= (ListView) params[2];
                if (params.length > 0) {

                    TwitterAPI twitterAPI = new TwitterAPI(TWITTER_API_KEY,TWITTER_API_SECRET);
                    twitterTweets = twitterAPI.getTwitterTweets(params[0].toString());
                }
                return twitterTweets;
            }

            @Override
            protected void onPostExecute(ArrayList<TwitterTweet> twitterTweets) {
                ArrayAdapter<TwitterTweet> adapter =
                        new ArrayAdapter<TwitterTweet>(TwitterMain.this, R.layout.twitter_tweets_list,
                                R.id.listTextView, twitterTweets);
                goo.setAdapter(adapter);
                // callerActivity.setListAdapter(adapter);
                //ListView lv = callerActivity.getListView();
                goo.setDividerHeight(0);
                goo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try{
                            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("twitter://user?screen_name="+twitterScreenName)));
                        }catch (Exception E){
                            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/"+twitterScreenName)));
                        }
                    }
                });
                //lv.setDivider(this.getResources().getDrawable(android.R.color.transparent));
//                goo.setBackgroundColor(TwitterMain.this.getResources().getColor(R.color.colorAccent));//Change to twitter colors
                pDialog.dismiss();
            }
        }


    private void supportActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbk);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tweets from KPMG East Africa");
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(flag==1) {
            handler.removeCallbacks(r);
        }
    }

}
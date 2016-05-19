package mushirih.thoughtleadership2;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,SearchView.OnQueryTextListener {
 public static ListView listView;
    DataSource dataSource;
    SORTER sorter;
    SharedPreferences editor;
    SharedPreferences.Editor fav;
    public static String search="";
   TextView desc;
    public static ProgressDialog pDialog;
    String videoPath="";

    public static final int progress_bar_type=0;//set progress bar to horizontal
    NotificationManager nM;
    int downloadError=0;
    String file;
    final static int uniqueID=6790000;
    String URL_FEED = "http://192.185.77.246/~muchiri/thoughtleadership/scripts/thoughtleadership.php";
    private SearchView mSearchView;
    DrawableProvider mProvider;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        final LinearLayout test= (LinearLayout) findViewById(R.id.test);
        context=this;
        dataSource = new DataSource(MainActivity.this, URL_FEED);
        mProvider = new DrawableProvider(this);
        listView= (ListView) findViewById(R.id.listView);
        final SwipeRefreshLayout swipe=(SwipeRefreshLayout)findViewById(R.id.shhhhhwipe);
        swipe.setEnabled(false);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                listView.setPadding(0,4,0,0);
                swipe.setEnabled(firstVisibleItem==0? true:false);
              //  swipe.setEnabled(listView.getFirstVisiblePosition()==0&&listView.getChildAt(0).getTop()==0);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                       @Override
                                       public void onRefresh() {
                                           ConnectivityManager check = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                                           NetworkInfo info = check.getActiveNetworkInfo();
                                           TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                           if (info == null || !info.isConnected() || tm.getDataState() != tm.DATA_CONNECTED) {
                                             Toast.makeText(getApplicationContext(),"Please check yout internet connection and try again",Toast.LENGTH_LONG).show();
                                               swipe.setRefreshing(false);
                                           }else {
                                               dataSource.refresh(URL_FEED);
                                               dataSource = new DataSource(MainActivity.this, URL_FEED);
                                               loader(test);
                                               listView.setAdapter(new SampleAdapter());
                                               listView.setOnItemClickListener(MainActivity.this);
                                               // ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
                                               swipe.setRefreshing(false);
                                           }
                                       }
                                   });

        desc= (TextView) findViewById(R.id.desc);
        if(desc.getText().equals(null)){
            desc.setVisibility(View.GONE);

        }
        supportActionBar();

        nM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nM.cancel(uniqueID);
        dataSource=new DataSource(this,URL_FEED);

//         FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Snackbar.make(view, "SEARCH ACTION GOES HERE", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//SHOW PROGRESS BAR

//
//
//
        listView.setAdapter(new SampleAdapter());
        loader(test);

        listView.setOnItemClickListener(this);

        int popeye=dataSource.getCount();
        editor = PreferenceManager.getDefaultSharedPreferences(this);
        fav = editor.edit();
        fav.putInt("counting",popeye);
        fav.commit();
//        Toast.makeText(getApplicationContext(),
//                "WATCHING : "+editor.getInt("counting",1),
//                Toast.LENGTH_LONG).show();


    }

    private void loader(final LinearLayout test) {
        if(new SampleAdapter().getCount()<1){
            pDialog = new ProgressDialog(context);
            pDialog.setCancelable(false);

        pDialog.setMessage("Loading articles.Please wait ...");
//            pDialog.setIndeterminate(true);
//              pDialog.setCancelable(false);
        pDialog.show();
        android.os.Handler handler=new android.os.Handler();
            Runnable r =new Runnable() {
                @Override
                public void run() {
                   SampleAdapter adapter=new SampleAdapter();
                    //if(adapter.getCount()>0) {//-------------------------------TESTING THIS
                        pDialog.dismiss();

                        listView.setAdapter(adapter);
                        if(listView.getAdapter().getCount()==0){
                            test.setBackgroundResource(R.drawable.error);
                            Snackbar.make(test, "Please check your internet connection and try again", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // startActivity(new Intent(getBaseContext(),Home.class));

                        }
                   // }
                }
            };
        handler.postDelayed(r, 5000);
        }
    }

    private void supportActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Articles");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            mSearchView= (SearchView) searchItem.getActionView();
            setUpSearch(searchItem);
        }else{
           searchItem.setVisible(false);
           this.invalidateOptionsMenu();
        }
        //listView.setAdapter(new SampleSORTEDAdapter());
       // listView.getAdapter().notify();

       // listView.setOnItemClickListener(this);
       //String query=SearchManager.QUERY;
        //Toast.makeText(getApplicationContext(),"Query recieved = "+query,
        // Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setUpSearch(MenuItem searchItem) {
        if(isAlwaysExpanded()){
            mSearchView.setIconified(false);

        }else {
            searchItem.setShowAsActionFlags
                    (MenuItem.SHOW_AS_ACTION_IF_ROOM|MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }
        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if(searchManager!=null) {
            /*
            List <SearchableInfo>searchables=searchManager.getSearchablesInGlobalSearch();
            SearchableInfo searchableInfo=searchManager.getSearchableInfo(getComponentName());
            for(SearchableInfo inf:searchables){
                if(inf.getSuggestAuthority()!=null &&
                inf.getSuggestAuthority().startsWith("applications")){
                    searchableInfo=inf;
                }
            }
            mSearchView.setSearchableInfo(searchableInfo);

        */
        }
        mSearchView.setOnQueryTextListener(this);
    }

    private boolean isAlwaysExpanded() {

        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
       //Toast.makeText(getApplicationContext(),"Recieved "+query
       // +" Submitted",Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

       search=newText;
        sorter=new SORTER(context,URL_FEED,newText);

            listView.setAdapter(new SampleSORTEDAdapter());



        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DataItem item = (DataItem) listView.getItemAtPosition(position);
        if (item.getNavigationInfo() != DataSource.NO_NAVIGATION &&
                item.getLabel()!="No item matches your search") {
            Intent intent = new Intent(this, Report.class);
            intent.putExtra("title",item.getLabel());
            intent.putExtra("content",item.getContent());
            intent.putExtra("region",item.getRegion());
            intent.putExtra("download",item.getDownloadUrl());
            intent.putExtra("drawable",item.getDrawable());
            intent.putExtra("owner",item.getOwner());
            intent.putExtra("name",item.getName());
            intent.putExtra("email",item.getEmail());
            intent.putExtra("workTitle",item.getWorkTitle());
            startActivity(intent);
        }else{
            Intent restart=new Intent(this,MainActivity.class);
            restart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(restart);
        }
    }



    private class SampleSORTEDAdapter extends BaseAdapter{
        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int i) {
            return true;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public int getCount() {
            return sorter.getCount();
        }

        @Override
        public DataItem getItem(int i) {
            return sorter.getItem(i);
        }


        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                convertView=View.inflate(MainActivity.this,R.layout.list_item_layout,null);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            final DataItem item=getItem(position);

            String drawable=item.getDrawable();
            Drawable mydefault=null;
            if(drawable ==null||drawable.isEmpty()||drawable.equals("null")){
                holder.imageView.setVisibility(View.INVISIBLE);
            }
            if(drawable.equals("DEFAULT")) {
                String text=item.getRegion();
                mydefault=mProvider.getRectWithMultiLetter(text);
                holder.imageView.setImageDrawable(mydefault);
            }else{
                holder.imageView.setImageBitmap(null);
                Picasso.with(context).load(item.getDrawable()).into(holder.imageView);

            }
            holder.textview.setText(item.getLabel());
//if original code has children options....................................use to check downloadable

            if(item.getNavigationInfo()!=DataSource.NO_NAVIGATION &&
                    item.getLabel()!="No item matches your search"){

//                holder.filesize.setText("88 KB");
//                holder.download.setImageResource(R.drawable.download);
            }else{
                holder.textview.setCompoundDrawablesWithIntrinsicBounds(null,null,
                        null,null);
            }/*

            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   downloadPDF(item);
                }
            });
            */
            /*
            if(drawable instanceof AnimationDrawable){
                holder.imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        ((AnimationDrawable)drawable).stop();
                        ((AnimationDrawable)drawable).start();
                    }
                });
            }
            */
            notifyDataSetChanged();
            return convertView;
        }



        private class ViewHolder {
            public ImageView imageView;
            public TextView textview;
            //public ImageView download;
            //public TextView filesize;
            private ViewHolder(View v){
                imageView = (ImageView) v.findViewById(R.id.imageView);
                textview = (TextView) v.findViewById(R.id.textView);
                //download= (ImageView) v.findViewById(R.id.downloadable);
               // filesize= (TextView) v.findViewById(R.id.filesize);
            }

        }
    }
    public class SampleAdapter extends BaseAdapter{
        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEnabled(int i) {
            return true;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public int getCount() {
            return dataSource.getCount();
        }

        @Override
        public DataItem getItem(int i) {
            return dataSource.getItem(i);
        }


        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                convertView=View.inflate(MainActivity.this,R.layout.list_item_layout,null);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }
            final DataItem item=getItem(position);

             String drawable=item.getDrawable();
            Drawable mydefault=null;
            if(drawable.isEmpty()){
                holder.imageView.setVisibility(View.INVISIBLE);
            }
            if(drawable.equals("DEFAULT")) {
                //mydefault = mProvider.getRoundRectWithBorder(item.getLabel().substring(0, 1));
                mydefault=mProvider.getRectWithMultiLetter(item.getRegion());
                holder.imageView.setImageDrawable(mydefault);
            }else{
                holder.imageView.setImageBitmap(null);
                Picasso.with(context).load(item.getDrawable()).into(holder.imageView);
            //Toast.makeText(context,"DISPLAY THIS"+drawable, Toast.LENGTH_LONG).show();
            }
            holder.textview.setText(item.getLabel());
//if original code has children options.................................use to check downloadable
            if(item.getNavigationInfo()!=DataSource.NO_NAVIGATION){

               // holder.filesize.setText("88 KB");
               // holder.download.setImageResource(R.drawable.download);
            }else{
                holder.textview.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        null, null);
            }
            /*
            holder.download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadPDF(item);
                }
            });
            */
            /*
            if(drawable instanceof AnimationDrawable){
                holder.imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        ((AnimationDrawable)drawable).stop();
                        ((AnimationDrawable)drawable).start();
                    }
                });

            }*/
            notifyDataSetChanged();
            return convertView;
        }



        private class ViewHolder {
            public ImageView imageView;
            public TextView textview;
            public ImageView download;
            //public TextView filesize;
            private ViewHolder(View v){
                imageView = (ImageView) v.findViewById(R.id.imageView);
                textview = (TextView) v.findViewById(R.id.textView);
               // download= (ImageView) v.findViewById(R.id.downloadable);
                //filesize= (TextView) v.findViewById(R.id.filesize);
            }

        }
    }

    private void downloadPDF(DataItem toDownload) {
        Date now = new Date();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);
        String url = toDownload.getDownloadUrl().replace("\\", "");
        String filename = (toDownload.getLabel().trim().replace(":", "-").replace(";", "-")
                + timestamp).substring(0, 35) + "...";
        if (url.equals("")) {
            Toast.makeText(getApplicationContext(), "File not available for download",
                    Toast.LENGTH_SHORT).show();
        } else {
            new DownloadFileFromURL().execute(filename, url);
        }
    }
//********************************************************************************DOWNLD
  /*to view
  public void openFolder(){
  Intent intent=new Intent(Intent.Action_GET_CONTENT);
  Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/KPMG KENYA");
  intent.setDataAndType(uri,"äpplication,pdf");
  startActivity(Intent.createChooser(intent,"Open Downloads Folder"));
  }

                        if(!videoPath.isEmpty()) {
                            File pdffile = new File(videoPath);
                            Uri path = Uri.fromFile(pdffile);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(path, "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            try {
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(getBaseContext(), "No application found to view PDF",
                                 Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getBaseContext(),"PDF file has not yet been downloaded",
                            Toast.LENGTH_SHORT).show();
                        }
        */


            public class DownloadFileFromURL extends AsyncTask<String, String, String> {


                /**
                 * Before starting background thread
                 * Show Progress Bar Dialog
                 */
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    showDialog(progress_bar_type);
                }

                /**
                 * Downloading file in background thread
                 */
                @Override
                protected String doInBackground(String... f_url) {
                    int count;
                    OutputStream output;

                    pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            Toast.makeText(getApplicationContext(),"PDF download cancelled.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                    try {
                        URL url = new URL(f_url[1]);
                        URLConnection conection = url.openConnection();

                        conection.connect();
                        // this will be useful so that you can show a tipical 0-100% progress bar
                        int lenghtOfFile = conection.getContentLength();

                        // download the file
                        InputStream input = new BufferedInputStream(url.openStream(), 8192);
                        //-------------------------------------NEW DIRECTORY
                        File folder = new File(Environment.
                                getExternalStorageDirectory() + "/KPMG KENYA");
                        boolean success = true;
                        if (!folder.exists()) {
                            success = folder.mkdir();
                        }
                        if (success) {
                            Log.d("DONE", "FOLDER SUCCESSFULLY CREATED");
                        } else {
                            Log.d("ERROR", "FOLDER WAS NOT SUCCESSFULLY CREATED");
                        }
                        // Output stream
                        file=f_url[0];

                            output = new FileOutputStream(Environment.getExternalStorageDirectory()
                                    .toString() + "/KPMG KENYA/" + file + ".pdf");


                        byte data[] = new byte[1024];

                        long total = 0;

                        while ((count = input.read(data)) != -1) {
                            total += count;
                            // publishing the progress....
                            // After this onProgressUpdate will be called
                            publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                            // writing data to file
                            output.write(data, 0, count);
                        }

                        // flushing output
                        output.flush();

                        // closing streams
                        output.close();
                        input.close();

                    } catch (Exception e) {
                        Log.e("Error: ", e.getMessage());
                        downloadError = downloadError + 1;
                    }

                    return null;
                }

                /**
                 * Updating progress bar
                 */
                protected void onProgressUpdate(String... progress) {
                    // setting progress percentage
                    pDialog.setProgress(Integer.parseInt(progress[0]));
                }





                /**
                 * After completing background task
                 * Dismiss the progress dialog
                 * *
                 */
                @Override
                protected void onPostExecute(String file_url) {
                    // dismiss the dialog after the file was downloaded
                    //dismissDialog(progress_bar_type);
                    pDialog.dismiss();
                    if(downloadError!=0){
                        Toast.makeText(getApplicationContext(),
                                "Failed to download.Check your network and try again",
                                Toast.LENGTH_LONG).show();
                    }

                    videoPath = Environment.getExternalStorageDirectory().toString()
                            + "/KPMG KENYA/"+file+".pdf";
                    Log.d("LINK URI!!!!!!!!!!!!!!",videoPath);

                    if (downloadError == 0) {

                        displayNotification();
                    }

                }


            }

            private void displayNotification() {

                NotificationCompat.Builder mBuilder= (NotificationCompat.Builder)
                        new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.smallicon)
                        .setContentTitle("KPMG download complete")
                        .setContentText("PDF has been downloaded to your collection")
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL);
                Intent resultIntent = new Intent();
                resultIntent.setAction(Intent.ACTION_VIEW);
                resultIntent.setDataAndType(Uri.fromFile(new File(videoPath)), "application/pdf");
                PendingIntent resultPendingIntent=PendingIntent.getActivity(this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
               NotificationManager notificationManager= (NotificationManager)
                       getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(uniqueID, mBuilder.build());

            }
            // Displaying downloaded image into image view
            // Reading image path from sdcard

            // setting downloaded into image view
            // my_image.setImageDrawable(Drawable.createFromPath(imagePath));

            protected Dialog onCreateDialog(int id) {
                switch (id) {
                    case progress_bar_type: // we set this to 0
                        pDialog = new ProgressDialog(this);
                        pDialog.setMessage("Downloading file. Please wait...");
                        pDialog.setIndeterminate(false);
                        pDialog.setMax(100);
                        pDialog.setProgress(10);

                        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pDialog.setCancelable(true);
                        pDialog.show();
                        return pDialog;
                    default:
                        return null;
                }

            }
        // /***********************************************************************DOWNLD
    }




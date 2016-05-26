package mushirih.thoughtleadership2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by p-tah on 30/03/2016.
 */
public class ListCred extends AppCompatActivity
        implements AdapterView.OnItemClickListener {
    private ListView listView;
    Profs profs;
    TextView desc;
    DrawableProvider mProvider;
    Context context;
    SampleAdapter sampleAdapter;
    ProgressDialog pDialog;
    LinearLayout linearLayout;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.shhhhhwipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        context = this;
        linearLayout= (LinearLayout) findViewById(R.id.test);
        mProvider = new DrawableProvider(this);
        listView = (ListView) findViewById(R.id.listView);
         profs=new Profs(context);
        sampleAdapter=new SampleAdapter();

        desc = (TextView) findViewById(R.id.desc);
        if (desc.getText().equals(null)) {
            desc.setVisibility(View.GONE);

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Lead Profiles");

       listView.setAdapter(sampleAdapter);
        loader(linearLayout);
        listView.setOnItemClickListener(this);


    }
    private void loader(final LinearLayout test) {
        if(new SampleAdapter().getCount()<1){
            pDialog = new ProgressDialog(context);
            pDialog.setCancelable(false);

            pDialog.setMessage("Loading profiles.Please wait ...");
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        return true;
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

        /*ProfItem item = (ProfItem) listView.getItemAtPosition(position);

            Intent intent = new Intent(this, Report.class);
            intent.putExtra("title", item.getName());
            intent.putExtra("content", item.getTitle());
            intent.putExtra("region", item.getDownloadUrl());

            startActivity(intent);
            */
        startActivity(new Intent(getBaseContext(),Cred.class));

    }

    private class SampleAdapter extends BaseAdapter {
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
            return profs.getCount();
        }

        @Override
        public ProfItem getItem(int i) {
            return profs.getItem(i);
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
            if (convertView == null) {
                convertView = View.inflate(ListCred.this, R.layout.cred_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ProfItem item = getItem(position);

            String drawable = item.getDownloadUrl();
            Drawable mydefault = null;
            if (drawable.isEmpty()) {
                holder.imageView.setVisibility(View.INVISIBLE);
           /*
            }
           if (drawable.equals("DEFAULT")) {
                //mydefault = mProvider.getRoundRectWithBorder(item.getLabel().substring(0, 1));
                mydefault = mProvider.getRectWithMultiLetter(item.getRegion());
                holder.imageView.setImageDrawable(mydefault);
                */
            } else {
                holder.imageView.setImageBitmap(null);
                Picasso.with(context).load(item.getDownloadUrl()).into(holder.imageView);

                // }
                holder.name.setText(item.getName());


                holder.pos.setText(item.getTitle());



            }

            return convertView;
        }

        private class ViewHolder {
            public ImageView imageView;
            public TextView name;
            public TextView pos;

            private ViewHolder(View v) {
                imageView = (ImageView) v.findViewById(R.id.imageView);
                name = (TextView) v.findViewById(R.id.name);
                pos = (TextView) v.findViewById(R.id.pos);

            }

        }


    }
}




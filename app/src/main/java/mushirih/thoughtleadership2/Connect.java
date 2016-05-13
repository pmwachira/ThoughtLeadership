package mushirih.thoughtleadership2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeIntents;

import java.lang.reflect.Field;

//import com.google.android.youtube.player.YouTubeIntents;

/**
 * Created by p-tah on 18/09/2015.
 */
public class Connect extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // themeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_connect);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolconn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            getSupportActionBar().setTitle("Contact Us");
        } catch (Exception e) {

        }
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        String TITLES[] = {"Visit website", "Facebook", "Twitter", "Youtube", "Linked In", "Call us", "Come to us","Write to us"};
        int ICONS[] = {R.drawable.web, R.drawable.facebook, R.drawable.tweet, R.drawable.youtube, R.drawable.linked, R.drawable.call, R.drawable.location,R.drawable.email};
        adapter = new MyAdapterConnect(TITLES, ICONS);
        recyclerView.setAdapter(adapter);
        final GestureDetector gestureDetector = new GestureDetector(Connect.this, new GestureDetector.SimpleOnGestureListener() {


            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }


        });
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {

                    int pos = recyclerView.getChildPosition(child);

                    switch (pos) {
                        case 0:
                            Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kpmg.com/eastafrica/en/Pages/default.aspx"));
                            startActivity(web);
                            break;
                        case 1:
                            try {
                                Connect.this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                                Intent fb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/kpmgglobal"));
                                startActivity(fb);
                            } catch (Exception f) {
                                Intent fb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/kpmgglobal"));
                                startActivity(fb);
                            }
                            break;
                        case 2:
                            try{
                                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("twitter://user?screen_name="+"mushirih")));
                            }catch (Exception E){
                                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/#!/"+"mushirih")));
                            }

                            break;
                        case 3:
                            Toast.makeText(getBaseContext(), "Opening KPMG Kenya on YouTube", Toast.LENGTH_SHORT).show();


                            try {
                                Intent intent = YouTubeIntents.createSearchIntent(getBaseContext(), "kpmg kenya");
                                startActivity(intent);
                            } catch (Exception e2) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCzxpyR-Oi5PUsoQEdFPcf4Q")));
                            }
                            break;
                        case 4:
                            Uri uri = Uri.parse("http://www.linkedin.com/company/kpmg-kenya");
                            Intent linkd = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(linkd);

                            break;
                        case 5:
                            //call intent
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(Connect.this);
                            builder2.setItems(R.array.call, mDialogListenerCall);

                            AlertDialog alertDialog = builder2.create();
                            alertDialog.setTitle("Choose number to call");

                            alertDialog.show();

                            break;
                        case 6:
                            Uri loc=Uri.parse("http://maps.google.com/maps?&daddr=-1.259728,36.777888 (ABC Place, Waiyaki Way, Nairobi, Kenya)");
                            Intent i=new Intent(Intent.ACTION_VIEW,loc);
                            i.setPackage("com.google.android.apps.maps");
                            try {
                                startActivity(i);
                            }catch (Exception eo){
                                Toast.makeText(getApplicationContext(),"Google Maps not installed on your device",Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 7:
                            Intent write=new Intent(getBaseContext(),Email.class);
                            startActivity(write);
                            break;
                    }
                    return true;
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

        });

    }
    protected DialogInterface.OnClickListener mDialogListenerCall = new DialogInterface.OnClickListener() {
        String number="";
        @Override
        public void onClick(DialogInterface dialog, int num) {

            switch (num){
                case 0:
                    number="0712613052";
                    break;
                case 1:
                    // Toast.makeText(getBaseContext(),"TWO SELECTED",Toast.LENGTH_SHORT).show();
                    number="0712613052";
                    break;

                default:
                    number="0712613052";
                    break;
            }

            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + number)));

        }



    };



}



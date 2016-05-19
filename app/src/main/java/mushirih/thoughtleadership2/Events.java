package mushirih.thoughtleadership2;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by p-tah on 02/03/2016.
 */
public class Events extends AppCompatActivity
        implements  RecyclerViewAdapterEvents.OnItemClickListener {
    RecyclerView recyclerView;
    EventsSource eventsSource;
    String EVENT_FEED = "http://192.185.77.246/~muchiri/thoughtleadership/scripts/events.php";
    Context context;
    List<EventItem> items=new ArrayList<EventItem>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarevents);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Events");
        context=this;



        recyclerView= (RecyclerView) findViewById(R.id.recyclerevents);
        eventsSource=new EventsSource(context,EVENT_FEED);

        for(int i=0;i<eventsSource.getCount();i++) {
           items.add(eventsSource.getItem(i));
         }
        if(items.size()==0){
            items.add(new EventItem("null","null","null","null","null","null","null"));

        }
        final SwipeRefreshLayout swipe=(SwipeRefreshLayout)findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
         @Override
         public void onRefresh() {
             setRecyclerAdapter(recyclerView);

             swipe.setRefreshing(false);
         }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(Events.this, 1));




        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setRecyclerAdapter(recyclerView);
        }


    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        setRecyclerAdapter(recyclerView);
        recyclerView.scheduleLayoutAnimation();
    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {
        RecyclerViewAdapterEvents adapter=new RecyclerViewAdapterEvents(items,Events.this);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onItemClick(View view, EventItem item) {

      //  saveEve(item);
        //OR
        if(item.getTitle().equals("null")){
            startActivity(new Intent(getBaseContext(),Home.class));
        }else {
            showEventNow(item);
        }
    }

    private void saveEve(EventItem item) {
        Calendar beginTime = Calendar.getInstance();

        beginTime.set(Integer.parseInt(item.getYear()), Integer.parseInt(item.getMonth())-1, Integer.parseInt(item.getDate()));
        Intent calendar=new Intent(Intent.ACTION_INSERT);
        //calendar.setData(CalendarContract.Events.CONTENT_URI);
        calendar.setType("vnd.android.cursor.item/event");
        calendar.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        // calendar.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        calendar.putExtra(CalendarContract.Events.TITLE,item.getTitle());
        //calendar.putExtra(CalendarContract.Events.DESCRIPTION,item.getDesc()+"\n\nFor Locations Ask : "+item.getOwner());
        calendar.putExtra(CalendarContract.Events.EVENT_LOCATION,"Ask "+item.getOwner());
        //calendar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        calendar.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(calendar);
    }

    private void showEventNow(final EventItem item) {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setIcon(R.drawable.icon)
                .setTitle("Event name: "+item.getTitle())
                .setMessage(item.getDesc()+" on "+item.getDate()+"/"+item.getMonth()+"/"+item.getYear())
                .setPositiveButton("Save to Calendar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               saveEve(item);
            }
        }).setNegativeButton("Don't save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

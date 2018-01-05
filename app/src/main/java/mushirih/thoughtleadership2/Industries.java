package mushirih.thoughtleadership2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p-tah on 08/02/2016.
 */
public class Industries extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {
 private static List<ViewModel> items=new ArrayList<>();
    private static int icons[]={
            R.drawable.chem,
            R.drawable.capi,
            R.drawable.ener,
            R.drawable.finan,
            R.drawable.food,
            R.drawable.publico,
            R.drawable.health,
            R.drawable.indust,
           R.drawable.infra,
            R.drawable.insura,
            R.drawable.invest,
            R.drawable.life,
            R.drawable.media,
           R.drawable.mining,
            R.drawable.retail,
            R.drawable.tech0,
            R.drawable.telle,
            R.drawable.transpo,
           R.drawable.real,
            R.drawable.auto,
            R.drawable.pic
    };
    private static String industries[]={
            "Premium",
            "Real Estate",
            "Vehicles",
            "Holidays",
            "Services",
            "Jobs",
           "Mobiles & Tablets",
            "Electronics & Appliances",
            "Home & Lifestyle",
            "Fashion & Wellbeing",
            "Kids",
            "Industrial Manufacturing",
            "Infrastructure",
            "Insurance",
            "Investment Management",
            "Life Sciences",
            "Pets",
            "Personals",
            "Miscellaneous",
            "CAT 0",
            "CAT 1"
    };
    static {
        for (int i=0;i<industries.length;i++){
            items.add(new ViewModel(industries[i],icons[i]));
            Log.e("Model Size: ","::"+items.size());
        }
        Log.e("Model Size: ","::"+items.size());
    }

    private View content;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.industries);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbk);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Categories");


        recyclerView= (RecyclerView) findViewById(R.id.recycler);
       recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        content=findViewById(R.id.content);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setRecyclerAdapter(RecyclerView recyclerView) {
    RecyclerViewAdapter adapter=new RecyclerViewAdapter(items);
        adapter.setOnItemClickListener(this);
       recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, ViewModel viewModel) {
        String industry=viewModel.getText();
        Intent clicked=new Intent(this,SingleIndustry.class);
        clicked.putExtra("industries",industry);
         startActivity(clicked);


    }
}

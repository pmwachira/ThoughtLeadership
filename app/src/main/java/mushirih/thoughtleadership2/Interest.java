package mushirih.thoughtleadership2;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by p-tah on 21/03/2016.
 */
public class Interest extends AppCompatActivity {
    int count=0;
    Button button;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    SharedPreferences editor;
    private ProgressDialog pDialog;
    Set<String> myset = new HashSet<String>();
    SharedPreferences.Editor fav;
    String[] send;
    ArrayList<String> selecteditems;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarint);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Choose your Topics of Interest");
        listView = (ListView) findViewById(R.id.interes);
        button = (Button) findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                justSave();
            }
        });

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, getResources().getStringArray(R.array.interest_options));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                count = parent.getCount()-1;
                if (position != 0 && !listView.isItemChecked(position)) {
                    listView.setItemChecked(0, false);
                }
                if (position == 0 && listView.isItemChecked(0)) {

                    for (int i = 0; i < parent.getCount(); i++) {
                        listView.setItemChecked(i, true);

                    }
                }
                if (position == 0 && !listView.isItemChecked(0)) {

                    for (int i = 0; i < parent.getCount(); i++) {
                        listView.setItemChecked(i, false);
                    }
                }


            }
        });
        listView.setAdapter(arrayAdapter);

        editor = PreferenceManager.getDefaultSharedPreferences(this);
        fav = editor.edit();

        Set<String> interest = new HashSet<String>();
        interest = editor.getStringSet("fav", null);
        if (interest != null) {
            List<String> lol = new ArrayList<>(interest);
            if (lol != null) {
                for (int i = 0; i < interest.size(); i++) {
                    int s=Integer.parseInt(lol.get(i));
                            listView.setItemChecked(s, true);
                }
            }
        }

    }




    @Override
    protected void onPause() {
        super.onPause();
        justSave();
      Intent thisOne = new Intent(getBaseContext(), Home.class);
        thisOne.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(thisOne);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)

    private void justSave() {
        if(listView.isItemChecked(0)){

            send = new String[count];
            int p=0;
            for(int i=0;i<count;i++){
                p=i+1;
                send[i]=""+p+"";
                myset.add(send[i]);
            }

        }else {
            SparseBooleanArray checked = listView.getCheckedItemPositions();
             selecteditems = new ArrayList<String>();
            for (int i = 0; i < checked.size(); i++) {
                int position = checked.keyAt(i);
                if (checked.valueAt(i))
                    selecteditems.add(arrayAdapter.getItem(position));

            }


            String[] output = new String[selecteditems.size()];
            send = new String[selecteditems.size()];
            for (int i = 0; i < selecteditems.size(); i++) {
                output[i] = selecteditems.get(i);

                switch (output[i]) {
                    case "Insurance":
                        send[i] = "0";
                        break;
                    case "Mining":
                        send[i] = "1";
                        break;
                    case "Automotive":
                        send[i] = "2";
                        break;
                    case "Technology":
                        send[i] = "3";
                        break;

                }
                myset.add(send[i]);
            }

        }
        fav.putStringSet("fav", myset);
        fav.commit();
        if (selecteditems.size()!=0) {
            pDialog = new ProgressDialog(this);

            pDialog.setMessage("Saving topics.Please wait ...");
            pDialog.setMax(10000);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        if (selecteditems.size()!=0) {
            Toast.makeText(getApplicationContext(), "Topics Saved", Toast.LENGTH_SHORT).show();
        }
    }

}

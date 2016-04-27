package mushirih.thoughtleadership2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p-tah on 30/03/2016.
 */
public class DIR extends AppCompatActivity {
    private List<String> dList=new ArrayList<String>();//START THIS D LIST
    private List<String> fileList=new ArrayList<String>();
    ListView XP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardir);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        XP= (ListView) findViewById(R.id.xp);


        try {
            getSupportActionBar().setTitle("Downloads");
        } catch (Exception e) {

        }
        try {
            File root=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/KPMG KENYA");
            ListDir(root);
        }catch (Exception e){
            fileList.add("You have not downloaded any article");
            ArrayAdapter<String> directoryList=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,fileList);

            XP.setAdapter(directoryList);
        }
    }

    private void ListDir(File root) {
        File[] files=root.listFiles();
        fileList.clear();
        for(File file:files){
            fileList.add(file.getPath()/*.substring(31)*/);
            dList.add(file.getPath().substring(31));
        }
        final ArrayAdapter<String> directoryList=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,fileList);
        final ArrayAdapter<String> dList2=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,dList);

        XP.setAdapter(dList2);
        XP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent pdf = new Intent();
                pdf.setAction(Intent.ACTION_VIEW);
                pdf.setDataAndType(Uri.fromFile(new File(directoryList.getItem(position))), "application/pdf");
                pdf.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pdf);
            }
        });
    }
}

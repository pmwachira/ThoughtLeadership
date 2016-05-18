package mushirih.thoughtleadership2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by p-tah on 08/02/2016.
 */
public class Services extends AppCompatActivity{
    TextView txt_help,content;
    TextView txt_help2,content2;
    TextView txt_help3,content3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolservices);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Our Services");

        txt_help = (TextView) findViewById(R.id.title_help);
        txt_help.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.a, 0);
        content = (TextView) findViewById(R.id.contents);
        content.setVisibility(View.GONE);
        txt_help2 = (TextView) findViewById(R.id.title_help2);
        txt_help2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.a, 0);
        content2 = (TextView) findViewById(R.id.contents2);
        content2.setVisibility(View.GONE);
        txt_help3 = (TextView) findViewById(R.id.title_help3);
        txt_help3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.a, 0);
        content3 = (TextView) findViewById(R.id.contents3);
        content3.setVisibility(View.GONE);
        txt_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_help.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.b,0);
                if(content2.isShown()){
                    content2.setVisibility(View.GONE);
                    txt_help2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.a, 0);
                }else if(content3.isShown()){
                    txt_help3.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.a,0);
                    content3.setVisibility(View.GONE);
                }else if(content.isShown()){
                    txt_help.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.a,0);
                }
                 content.setVisibility(content.isShown() ? View.GONE : View.VISIBLE);

            }
        });
        txt_help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_help2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.b, 0);
                if (content.isShown()) {
                    txt_help.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.a, 0);
                    content.setVisibility(View.GONE);
                } else if (content3.isShown()) {
                    txt_help3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.a, 0);
                    content3.setVisibility(View.GONE);
                }else if(content2.isShown()){
                    txt_help2.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.a,0);
                }
                content2.setVisibility(content2.isShown() ? View.GONE : View.VISIBLE);

            }
        });
        txt_help3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_help3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.b, 0);

                if(content.isShown()){
                    txt_help.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.a, 0);
                    content.setVisibility(View.GONE);
                }else if(content2.isShown()){
                    txt_help2.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.a,0);
                    content2.setVisibility(View.GONE);
                }else if(content3.isShown()){
                    txt_help3.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.a,0);
                }
                content3.setVisibility(content3.isShown() ? View.GONE : View.VISIBLE);

            }
        });


    }
}

package mushirih.thoughtleadership2;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by p-tah on 26/05/2016.
 */
public class Bout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            getSupportActionBar().setTitle("About App");
        } catch (Exception e) {

        }
        TextView textView= (TextView) findViewById(R.id.box);

           textView.setText("Developed by: pmwachira@gmail.com ");
           textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
           textView.setTextColor(Color.RED);


    }
}

package mushirih.thoughtleadership2;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


/**
 * Created by p-tah on 22/03/2016.
 */
public class Cred extends AppCompatActivity {
    TextView name,pos,email,statement,experience,clients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credentials);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcred);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            getSupportActionBar().setTitle("Credentials Log");
        } catch (Exception e) {

        }
        name= (TextView) findViewById(R.id.name);
        name.setText("Wachira,Peter");
        pos = (TextView) findViewById(R.id.pos);
        pos.setText("Data & Analytics");
        statement= (TextView) findViewById(R.id.statement);
        statement.setText("To fully utilize as well as gain more knowledge, skills and experience, by working through organizations, by innovating, visualizing ideas and working to see them effected.");
       experience= (TextView) findViewById(R.id.experience);
        experience.setText("4 years in Data & Analytics");

        email= (TextView) findViewById(R.id.email);
        email.setText("pmwachira@gmail.com");
        email.setPaintFlags(email.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent2.setType("text/plain");

                intent2.putExtra(Intent.EXTRA_TEXT, getString(R.string.download_link_footer));
                intent2.setData(Uri.parse("mailto:" + email)); // or just "mailto:" for blank
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                startActivity(intent2);
            }
        });
        clients= (TextView) findViewById(R.id.clients);
        clients.setText("1. Safaricom\n2.I&M Kenya\n3.Kenya Airways");
    }
}

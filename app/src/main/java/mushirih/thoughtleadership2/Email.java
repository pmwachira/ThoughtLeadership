package mushirih.thoughtleadership2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by p-tah on 22/02/2016.
 */
public class Email extends AppCompatActivity {
    String[] email_addresses = {"Classified Ads","Business Space Administrator", "Sales & Marketing"};
    Spinner spinnerDropdown;
    String sendTo="";
    String name="";
    String companyEmail="info@kpmg.co.ke";
    EditText opt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // themeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolem);
        opt= (EditText) findViewById(R.id.etop);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        try {
            getSupportActionBar().setTitle("Talk to us");
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
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,email_addresses);
        spinnerDropdown = (Spinner) findViewById(R.id.spinner);
        spinnerDropdown.setAdapter(adapter);
        spinnerDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        sendTo="ads@xpatlink.info";
                        break;
                    case 1:
                        sendTo="info@xpatlink.info";
                        break;
                    default:
                        sendTo="sales@xpatlink.info";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final Button btnSend = (Button) findViewById(R.id.submit);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // String sendTo=email_addresses[sid];
                TextView head = (TextView) findViewById(R.id.ettitle);
                TextView mail = (TextView) findViewById(R.id.etmail);
                EditText namei = (EditText) findViewById(R.id.etname);
                name = namei.getText().toString();
                if (name.isEmpty()) {
                    name = "a client";
                }
                String heading = head.getText().toString();
                if (heading.isEmpty()) {
                    heading = "Enquiries";
                }
                String mailing = mail.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:")); // it's not ACTION_SEND
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{sendTo}); // or just "mailto:" for blank
                intent.putExtra(Intent.EXTRA_SUBJECT, heading);
                intent.putExtra(Intent.EXTRA_TEXT, "Message from : " + name + "\n\n" + mailing + "\n\n Sent from Xpat Link Magazine Android App. Available on https://play.google.com/store/apps/details?id=mushirih.thoughtleadership2;hl=en");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                try {
                    intent.setPackage("com.google.android.gm");
                } catch (Exception e) {


                    Intent intent2 = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                    intent2.setType("text/plain");
                    intent2.putExtra(Intent.EXTRA_SUBJECT, heading);
                    intent2.putExtra(Intent.EXTRA_TEXT, "Message from : " + name + "\n\n" + mailing + "\n\n Sent from Xpat Link Magazine Android App. Available on https://play.google.com/store/apps/details?id=mushirih.thoughtleadership2;hl=en");
                    intent2.setData(Uri.parse("mailto:" + sendTo)); // or just "mailto:" for blank
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    startActivity(intent2);
                }
                startActivity(intent);
                // Toast.makeText(getApplicationContext(),"Opening your emailing application",Toast.LENGTH_LONG).show();
                // startActivity(new Intent(getBaseContext(), Connect.class));


            }
        });

    }
}
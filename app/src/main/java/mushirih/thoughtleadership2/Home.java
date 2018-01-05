package mushirih.thoughtleadership2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import mushirih.thoughtleadership2.eventsPictorial.*;
import mushirih.thoughtleadership2.testData.*;
import mushirih.thoughtleadership2.testData.MainActivity;
import mushirih.thoughtleadership2.trash.Cred;

/**
 * Created by p-tah on 09/02/2016.
 */
public class Home  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
  Typeface typeface;
    TextView cont,eve,abou,ind,art,ser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

       typeface=Typeface.createFromAsset(getBaseContext().getAssets(),"KPMGAppExtraLight.ttf");

        cont= (TextView) findViewById(R.id.cont);
        //cont.setTypeface(typeface);

        //abou.setTypeface(typeface);
        eve= (TextView) findViewById(R.id.eve);
        //eve.setTypeface(typeface);
        ind= (TextView) findViewById(R.id.ind);
        //ind.setTypeface(typeface);
        art= (TextView) findViewById(R.id.art);
        //art.setTypeface(typeface);
        ser= (TextView) findViewById(R.id.serv);
       //ser.setTypeface(typeface);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.xpat_link_logo);
        getSupportActionBar().setTitle("  ");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Date date=new Date(System.currentTimeMillis());
        if( date.getYear()>115 && date.getMonth()>3) {

            navigationView.getMenu().findItem(R.id.nav_bout).setVisible(true);
        }
        navigationView.setNavigationItemSelectedListener(this);




    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.home, menu);


        return true;
    }
    public void articles(View v){
//        startActivity(new Intent(this,MainActivity.class));
        startActivity(new Intent(this, mushirih.thoughtleadership2.testData.MainActivity.class));
    }
    public void industries(View v){

//        startActivity(new Intent(this, Industries.class));
        startActivity(new Intent(this, WebView.class));
    }
    public void services(View v){
       // startActivity(new Intent(this,Services.class));
        startActivity(new Intent(getApplicationContext(), mushirih.thoughtleadership2.eventsPictorial.MainActivity.class));
    }

    public void about(View v){
        startActivity(new Intent(this,About.class));

    }

    public void contact(View v){
        startActivity(new Intent(this,Connect.class));
    }
    public void events(View v){startActivity(new Intent(this,About.class));}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();

        if(id==R.id.nav_industries){
            startActivity(new Intent(this, mushirih.thoughtleadership2.testData.MainActivity.class));
          //  startActivity(new Intent(this,Industries.class));
        }else if (id == R.id.nav_about) {
            startActivity(new Intent(this, About.class));
        }else if(id==R.id.nav_service){
            startActivity(new Intent(this, mushirih.thoughtleadership2.eventsPictorial.MainActivity.class));
        }else if(id==R.id.nav_contact){
            startActivity(new Intent(this,Connect.class));
        }else if(id==R.id.nav_interest){
            startActivity(new Intent(this, WebView.class));
          //work with this///  startActivity(new Intent(this,Interest.class));
            /*
            new android.app.AlertDialog.Builder(Home.this).setIcon(R.drawable.icon).setTitle("Choose topic of interest").
                    setMessage("jbbjjb").setMultiChoiceItems(R.array.interest_options,onNavigationItemSelected(),null).setPositiveButton("Got it", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
            */
        }else if(id==R.id.nav_insights){
           // startActivity(new Intent(this,MainActivity.class));
        }/*else if(id== R.id.nav_careers){
            new android.support.v7.app.AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Proceed to Careers on website?").setMessage("Do you wish to exit app and proceed to our website?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Uri career = Uri.parse("http://www.kpmg.com/eastafrica/en/careers/Pages/default.aspx");
                    Intent carre = new Intent(Intent.ACTION_VIEW, career);
                    startActivity(carre);
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   // Toast.makeText(getBaseContext(), "Add graduate recruitment Here", Toast.LENGTH_SHORT).show();
                }
            }).show();

        }
        */else if(id == R.id.nav_cred) {

                startActivity(new Intent(getBaseContext(), ArticlesActivity.class));


        }else if(id==R.id.nav_d){
            startActivity(new Intent(getBaseContext(),DIR.class));
        }else if(id==R.id.nav_disclaimer){
            new android.app.AlertDialog.Builder(Home.this).setIcon(R.drawable.logo_jpeg_xp).setTitle("Terms and Conditions").setMessage("All classified ads are paid at time of order. Although every effort will be made to meet Advertisers wishes, XpatLink does not accept responsibility or consequences arising for inaccuracies in advertisements.\n" +
                    "\n" +
                    "Please note that 48H ahead of the official deadline, XpatLink cannot guarantee the insertion of any advertisement on the coming issue.\n" +
                    "The onus is upon the customer to contact XpatLink within 3days of the 1st day of the publication for any error.\n" +
                    "No refunds, credits or free ads will be published in the case of typographical errors. No responsibility is accepted for error in the printing.\n" +
                    "It is the advertiser’s responsibility to ensure that the copy conforms to all government legislation.\n" +
                    "XpatLink makes no representation and gives no warranty as to the competence of any person, firm or company, nor as to the accuracy of any facts or materials stated in any advertisement.\n" +
                    "All material in this publication is the copyright of XpatLink and cannot be published without our express permission.\n" +
                    "Notice of cancellation from any advertiser or representative will not be accepted by telephone and must be in writing at least one week before the deadline for the scheduled publication. For ads run in series no refund or credit will be given in the case of cancellation in the course of the series. The placing of an order with XpatLink will signify acceptance of the above conditions.\n" +
                    "\n" +
                    "\n" + "Acceptance\n" +
                    "XpatLink provides a collection of online resources which include classified advertisements, forums, and certain e-mail services on the website. By using the Service, you agree to comply with these Terms. Additionally, when using XpatLink service, you agree to conform to any applicable posted guidelines for such Service, which may change from time to time. You understand and agree that you are solely responsible for reviewing these Terms from time to time.\n" +
                    "Policy\n" +
                    "You understand that XpatLink does not control, and is not responsible for ads, directory information, business listings/information, messages between users, whether through the Website or another Third Party Website or offerings, comments, user postings, files, images, photos, video, sounds, business listings/information and directory information or any other material made available through the Website, and that by using the Website, you may be exposed to Content that is offensive, indecent, inaccurate, misleading, or otherwise objectionable. You acknowledge and agree that you are responsible for and must evaluate, and bear all risks associated with, the use of any Content, and that under no circumstances will XpatLink be liable in any way for the Content or for any loss or damage of any kind incurred as a result of the use of any Content listed, e-mailed or otherwise made available.\n" +
                    "\n" +
                    "You acknowledge and agree that you are solely responsible for your own Content posted on, transmitted through, or linked from the Service and the consequences of posting, transmitting, linking or publishing it. More specifically, you are solely responsible for all Content that you upload, email or otherwise make available. In connection with such Content posted on, transmitted through, or linked from the Service by you, you affirm, acknowledge, represent, and warrant that: you own or have the necessary licenses, rights, consents, and permissions to use such Content on the Website (including without limitation all patent, trademark, trade secret, copyright or other proprietary rights in and to any and all such Content) and authorize XpatLink to use such Content.\n" +
                    "XpatLink does not endorse any Content or any opinion, statement, recommendation, or advice expressed therein, and XpatLink expressly disclaims any and all liability in connection with user Content. XpatLink does not permit copyright infringing activities. XpatLink reserves the right to remove any Content without prior notice.\n" +
                    "XpatLink does not accept responsibility or consequences arising for inaccuracies in advertisements.\n" +
                    "The onus is upon the customer to contact XpatLink within 3days of the 1st day of the publication for any error.\n" +
                    "Paid Postings\n" +
                    "XpatLink may charge a fee to post content in some areas of the Website. The fee permits certain content to be posted in a designated area of the Website. Each party posting content to the service is responsible for said content and compliance with the Terms and Conditions. Any such fees paid hereunder are non-refundable in the event any content is removed from the service for violating these Terms and Conditions.Web Options and Final Price (Incl VAT)\n" +
                    "Any post is FREE for a period of 10 days.\n").setPositiveButton("OK,Got it", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
        }else if(id==R.id.nav_bout){
            startActivity(new Intent(getBaseContext(),Bout.class));
        }
         /*
        else if(id==R.id.nav_downloads){
    THIS CODE WORKS ON MY XPERIA
            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/KPMG KENYA/")),"*?/pdf");
            startActivity(Intent.createChooser(intent,"Open Downloads Folder"));


            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
           // Intent intent=new Intent(Intent.ACTION_VIEW;
            Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/KPMG KENYA/");
            intent.setDataAndType(uri,"resource/folder");
            startActivity(Intent.createChooser(intent,"Open Downloads Folder"));
        }
         */
        item.setChecked(true);
        // drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawers();
        return true;
    }
    protected DialogInterface.OnClickListener mDialogListenerCall = new DialogInterface.OnClickListener() {
        String number="";
        @Override
        public void onClick(DialogInterface dialog, int num) {

            switch (num){
                case 0:
                    number="Partner";
                    break;
                case 1:
                    // Toast.makeText(getBaseContext(),"TWO SELECTED",Toast.LENGTH_SHORT).show();
                    number="Director";
                    break;

                default:
                    number="Managers";
                    break;
            }
            Intent intent=new Intent(getBaseContext(),Cred.class);
            intent.putExtra("extra",number);
            startActivity(intent);

        }



    };

}

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
        cont.setTypeface(typeface);
        abou= (TextView) findViewById(R.id.abou);
        abou.setTypeface(typeface);
        eve= (TextView) findViewById(R.id.eve);
        eve.setTypeface(typeface);
        ind= (TextView) findViewById(R.id.ind);
        ind.setTypeface(typeface);
        art= (TextView) findViewById(R.id.art);
        art.setTypeface(typeface);
        ser= (TextView) findViewById(R.id.serv);
       ser.setTypeface(typeface);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.smallicon);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.home, menu);


        return true;
    }

    public void industries(View v){

        startActivity(new Intent(this, Industries.class));
    }
    public void services(View v){
        startActivity(new Intent(this,Services.class));
    }
    public void articles(View v){
        startActivity(new Intent(this,MainActivity.class));
    }
    public void about(View v){
        startActivity(new Intent(this,About.class));
    }

    public void contact(View v){
        startActivity(new Intent(this,Connect.class));
    }
    public void events(View v){startActivity(new Intent(this,Events.class));}
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
            startActivity(new Intent(this,Industries.class));
        }else if (id == R.id.nav_about) {
            startActivity(new Intent(this, About.class));
        }else if(id==R.id.nav_service){
            startActivity(new Intent(this,Services.class));
        }else if(id==R.id.nav_contact){
            startActivity(new Intent(this,Connect.class));
        }else if(id==R.id.nav_interest){
            startActivity(new Intent(this,Interest.class));
            /*
            new android.app.AlertDialog.Builder(Home.this).setIcon(R.drawable.icon).setTitle("Choose topic of interest").
                    setMessage("jbbjjb").setMultiChoiceItems(R.array.interest_options,onNavigationItemSelected(),null).setPositiveButton("Got it", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
            */
        }else if(id==R.id.nav_insights){
            startActivity(new Intent(this,MainActivity.class));
        }else if(id== R.id.nav_careers){
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

        }else if(id == R.id.nav_cred) {
            Profs prof = new Profs(this);
           if (prof.getCount() == 0) {
                startActivity(new Intent(getBaseContext(), ListCred.class));
            } else{
                AlertDialog.Builder builder2 = new AlertDialog.Builder(Home.this);
            builder2.setItems(R.array.cred, mDialogListenerCall);
            AlertDialog alertDialog = builder2.create();

            alertDialog.show();
        }

        }else if(id==R.id.nav_d){
            startActivity(new Intent(getBaseContext(),DIR.class));
        }else if(id==R.id.nav_disclaimer){
            new android.app.AlertDialog.Builder(Home.this).setIcon(R.drawable.icon).setTitle("Dislaimer").setMessage("").setPositiveButton("OK,Got it", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
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

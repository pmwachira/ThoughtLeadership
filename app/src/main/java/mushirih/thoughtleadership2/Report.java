package mushirih.thoughtleadership2;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by p-tah on 28/01/2016.
 */
public class Report extends AppCompatActivity {
    TextView titlE;
    TextView contenT;
    TextView owneR;
    TextView Name,Email,Web;
    String name,email,workTitle;
    MainActivity mainActivity;
    String download,title;
    ProgressDialog pDialog;
    String videoPath="";
   LinearLayout dwn;
    LinearLayout cont;
    public static final int progress_bar_type=0;//set progress bar to horizontal
    NotificationManager nM;
    int downloadError=0;
    String file;
    final static int uniqueID=67900870;
    Typeface typeface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        typeface= Typeface.createFromAsset(getBaseContext().getAssets(), "KPMGAppExtraLight.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbb);
        dwn= (LinearLayout) findViewById(R.id.dwn);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Reports");
        title=getIntent().getStringExtra("title");
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        workTitle=getIntent().getStringExtra("workTitle");
        Name= (TextView) findViewById(R.id.name);
        Email= (TextView) findViewById(R.id.email);
        Web= (TextView) findViewById(R.id.website);
        cont= (LinearLayout) findViewById(R.id.contact);

        final String content=getIntent().getStringExtra("content");
        String owner=getIntent().getStringExtra("owner");
         download=getIntent().getStringExtra("download");
        if(!download.isEmpty()){
            dwn.setVisibility(View.VISIBLE);
        }
        titlE= (TextView) findViewById(R.id.title);
        titlE.setTypeface(typeface);

        titlE.setText(title);
        contenT= (TextView) findViewById(R.id.content);
        contenT.setText(content);

        owneR= (TextView) findViewById(R.id.owner);
        owneR.setText(owner);

        android.support.v7.widget.CardView cardView= (CardView) findViewById(R.id.card1);

        if(name!=null){
            Name.setText(name+", "+workTitle);
            Email.setText(email);
        }else{
            cont.setVisibility(View.INVISIBLE);
            cardView.setVisibility(View.GONE);

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Intent.ACTION_SEND);
                send.setType("text/plain");


                //CHECK IF TO SENT IS GREATER THAN 4200
                if(content.length()>3750){
                    send.putExtra(Intent.EXTRA_TEXT, title + "\n\n\n\n\n\n\n by KPMG Kenya \n\n" + content.subSequence(0,3800)+"...(Read more on our mobile app)"+ getString(R.string.download_link_footer));
                }else{
                    send.putExtra(Intent.EXTRA_TEXT, title + "\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "\n by KPMG Kenya \n\n" + content + getString(R.string.download_link_footer));
                }
                startActivity(Intent.createChooser(send, "Share article using.."));


            }
        });





    }

    public  void downloadPDF(View v){

        Date now=new Date();
        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);
        String url=download.replace("\\", "");
        String filename=(title.trim().replace(":","-").replace(";","-")+timestamp).substring(0, 20)+"...";
       new DownloadFileFromURL().execute(filename, url);
    }
    public class DownloadFileFromURL extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            OutputStream output;

            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Toast.makeText(getApplicationContext(), "PDF download cancelled.", Toast.LENGTH_LONG).show();
                }
            });

            try {
                URL url = new URL(f_url[1]);
                URLConnection conection = url.openConnection();

                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                //-------------------------------------NEW DIRECTORY
                File folder = new File(Environment.getExternalStorageDirectory() + "/KPMG KENYA");
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                }
                if (success) {
                    Log.d("DONE", "FOLDER SUCCESSFULLY CREATED");
                } else {
                    Log.d("ERROR", "FOLDER WAS NOT SUCCESSFULLY CREATED");
                }
                // Output stream
                file=f_url[0];

                output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/KPMG KENYA/" + file + ".pdf");


                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                downloadError = downloadError + 1;
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }





        /**
         * After completing background task
         * Dismiss the progress dialog
         * *
         */
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //dismissDialog(progress_bar_type);
            pDialog.dismiss();
            if(downloadError!=0){
                Toast.makeText(getApplicationContext(),"Failed to download.Check your network and try again",Toast.LENGTH_LONG).show();
            }

            videoPath = Environment.getExternalStorageDirectory().toString() + "/KPMG KENYA/"+file+".pdf";

            if (downloadError == 0) {

                displayNotification();
            }

        }


    }
    private void displayNotification() {

        NotificationCompat.Builder mBuilder= (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.smallicon)
                .setContentTitle("KPMG download complete")
                .setContentText("PDF has been downloaded to your collection")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        Intent resultIntent = new Intent();
        resultIntent.setAction(Intent.ACTION_VIEW);
        resultIntent.setDataAndType(Uri.fromFile(new File(videoPath)), "application/pdf");
        PendingIntent resultPendingIntent=PendingIntent.getActivity(this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID, mBuilder.build());

    }
    // Displaying downloaded image into image view
    // Reading image path from sdcard

    // setting downloaded into image view
    // my_image.setImageDrawable(Drawable.createFromPath(imagePath));

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgress(10);

                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }

    }

    public void openEmail(View v){
        String heading = "";
        if (heading.isEmpty()) {
            heading = "Reply on your \'"+title+"\' article";
        }

        Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:")); // it's not ACTION_SEND
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pmwachia@gmail.com"}); // or just "mailto:" for blank
        intent.putExtra(Intent.EXTRA_SUBJECT, heading);
        intent.putExtra(Intent.EXTRA_TEXT, "\n\n\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n Sent from KPMG EA Android App. Available on https://play.google.com/store/apps/details?id=mushirih.thoughtleadership2;hl=en");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        try {
            intent.setPackage("com.google.android.gm");
        } catch (Exception e) {


            Intent intent2 = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_SUBJECT, heading);
            intent.putExtra(Intent.EXTRA_TEXT, "\n\n\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n Sent from KPMG EA Android App. Available on https://play.google.com/store/apps/details?id=mushirih.thoughtleadership2;hl=en");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pmwachia@gmail.com"}); // or just "mailto:" for blank

            startActivity(intent2);
        }

        startActivity(intent);
    }
    public void openWebsite(View v){
        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kpmg.com/eastafrica/en/Pages/default.aspx"));
        startActivity(web);
    }
}

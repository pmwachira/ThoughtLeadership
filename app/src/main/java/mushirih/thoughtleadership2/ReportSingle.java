package mushirih.thoughtleadership2;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
 * Created by p-tah on 11/02/2016.
 */
public class ReportSingle  extends AppCompatActivity {
    TextView titlE;
    TextView contenT;
    TextView owneR;
    MainActivity mainActivity;
    ProgressDialog pDialog;
    String videoPath="";

    public static final int progress_bar_type=0;//set progress bar to horizontal
    NotificationManager nM;
    int downloadError=0;
    String file,download, title;
    final static int uniqueID=67949870;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Industry Report");
         title=getIntent().getStringExtra("title");
        String content=getIntent().getStringExtra("content");
        String owner=getIntent().getStringExtra("owner");
        download=getIntent().getStringExtra("download");

        titlE= (TextView) findViewById(R.id.title);
        titlE.setText(title);
        contenT= (TextView) findViewById(R.id.content);
        contenT.setText(content);
        owneR= (TextView) findViewById(R.id.owner);
        owneR.setText(owner);
  }
    public  void downloadPDF(View v){

        Date now=new Date();
        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);
        String url=download.replace("\\", "");

        String filename=(title.trim().replace(":","-").replace(";","-")+timestamp).substring(0, 20)+"...";
        if(url.equals("")){
            Toast.makeText(getApplicationContext(),"File not available for download",Toast.LENGTH_SHORT).show();
        }else {
            new DownloadFileFromURL().execute(filename, url);
        }
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
}

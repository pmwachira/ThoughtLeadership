package mushirih.thoughtleadership2.eventsPictorial;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mushirih.thoughtleadership2.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by mushirih on 22/12/2017.
 */

public class MagAdapter extends RecyclerView.Adapter<MagAdapter.MyViewHolder> {
    private Context context;
    private List<Mag> albumList;
    ProgressDialog pDialog;
    String file;
    int downloadError=0;
    String videoPath="";


    public MagAdapter(Context context, List<Mag> albumList) {
        this.context=context;
        this.albumList=albumList;
    }

    @Override
    public MagAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card_mag,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MagAdapter.MyViewHolder holder, int position) {
        final Mag album=albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(album.getNumOfSong());
        Picasso.with(context).load(album.getThumbnail()).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadPDF(album.getName(),album.getDownloadUrl());
             //   Log.d("Tset Url","=="+album.getDownloadUrl());
            }
        });
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpMenu(holder.overflow);
            }
        });

    }



    private void showPopUpMenu(ImageView overflow) {
        PopupMenu popupMenu=new PopupMenu(context,overflow);
        MenuInflater menuInflater=popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_mag,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_add_favourite:
                        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_play_next:
                        //Toast.makeText(context, "Booked", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                }
                return false;

            }

        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
public TextView title,count;
public ImageView thumbnail,overflow;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.album_title);
            count=itemView.findViewById(R.id.count);
            thumbnail=itemView.findViewById(R.id.thumbnail);
            overflow=itemView.findViewById(R.id.overflow);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MagAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MagAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    public  void downloadPDF(String title, String url){

        Date now=new Date();
        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);
        url=url.replace("\\", "");

        String filename=(title.trim().replace(":","-").replace(";","-")+timestamp).substring(0, 20);
        if(url.equals("")){
            Toast.makeText(context,"File not available for download",Toast.LENGTH_SHORT).show();
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
            showDialog(0);
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
                    Toast.makeText(context, "PDF download cancelled.", Toast.LENGTH_LONG).show();
                }
            });

            try {
                URL url = new URL(f_url[1]);
                URLConnection conection = url.openConnection();

                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());
                //-------------------------------------NEW DIRECTORY
                File folder = new File(Environment.getExternalStorageDirectory() + "/XpatLink");
                boolean success = true;
                String err="";
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

                output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/XpatLink/" + file + ".pdf");


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
                Log.d("Error","=="+e.getMessage());
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
                Toast.makeText(context,"Failed to download.Check your network and try again",Toast.LENGTH_LONG).show();

            }

            videoPath = Environment.getExternalStorageDirectory().toString() + "/XpatLink/"+file+".pdf";

            if (downloadError == 0) {
                //TODO REMOVE THIS HARDCODED RESOLVE
                displayNotification();
            }

        }


    }
    private void displayNotification() {

        NotificationCompat.Builder mBuilder= (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("XpatLink download complete")
                .setContentText("PDF has been downloaded to your collection")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        Intent resultIntent = new Intent();
        resultIntent.setAction(Intent.ACTION_VIEW);
        resultIntent.setDataAndType(Uri.parse("file:///storage/emulated/0/XpatLink/issue156.pdf"), "application/pdf");
        PendingIntent resultPendingIntent=PendingIntent.getActivity(context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(969669, mBuilder.build());

    }
    protected Dialog showDialog(int id) {
        switch (id) {
            case 0: // we set this to 0
                pDialog = new ProgressDialog(context);
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

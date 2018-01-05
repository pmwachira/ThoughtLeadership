package mushirih.thoughtleadership2.data;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import mushirih.thoughtleadership2.DataSource;
import mushirih.thoughtleadership2.DrawableProvider;
import mushirih.thoughtleadership2.MainActivity;
import mushirih.thoughtleadership2.R;

/**
 * Created by p-tah on 31/12/2015.
 */
public class updateDataBG extends Service {
    //***********************************************SCRIPT FOR ARTICLES

    SharedPreferences editor;
    DataSource dataSource;
    NotificationManager nM;
    final static int uniqueID=6798400;
    String URL_FEED = "http://192.185.77.246/~muchiri/thoughtleadership/scripts/thoughtleadership.php";
    DrawableProvider mProvider;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        nM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nM.cancel(uniqueID);
        mProvider = new DrawableProvider(getApplicationContext());
        dataSource = new DataSource(getApplicationContext(), URL_FEED);
        dataSource.refresh(URL_FEED);
       //dataSource = new DataSource(getApplicationContext(), URL_FEED);
     //   if(1!=1) {
        editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
         int dexter=editor.getInt("counting", 0);
        int mojojojo=dataSource.getCount();
    // Toast.makeText(getApplicationContext(), "we are at " + dataSource.getCount() + "AGAINST EXISTING: " + dexter, Toast.LENGTH_LONG).show();
            if(mojojojo > dexter) {
                displayNotification();
            }
       // }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void displayNotification() {
    Notification.Builder mBuilder=
                new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("KPMG East Africa")
                        //.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.service))
                        .setContentText("New KPMG East Africa article published")
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL);
        Intent resultIntent = new Intent(getBaseContext(),MainActivity.class);
        PendingIntent resultPendingIntent=PendingIntent.getActivity(this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager= (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(uniqueID, mBuilder.build());
 }

}

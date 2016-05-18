package mushirih.thoughtleadership2.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by p-tah on 31/12/2015.
 */
public class DataListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context,"DATA HAS CHANGED",Toast.LENGTH_LONG).show();//WORKING
        //CHECK DATA----------------------------------------------------------------------------------------------------------------------
        ConnectivityManager check = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = check.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (info == null || !info.isConnected() || tm.getDataState() != tm.DATA_CONNECTED) {

            //Toast.makeText(context, "DATA HAS CHANGED BUT NO NET", Toast.LENGTH_LONG).show();//WORKING
        }else{
            //Toast.makeText(context,"DATA WORKING",Toast.LENGTH_LONG).show();
            Intent dataonBG=new Intent(context,updateDataBG.class);
             context.startService(dataonBG);
        }
    }
}

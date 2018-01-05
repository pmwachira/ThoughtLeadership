package mushirih.thoughtleadership2.intro;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mushirih on 28/12/2017.
 */

public class PrefManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE=0;

    private static final String PREF_NAME="mushirih_welcome";
    private static final String IS_FIRST_LAUNCH= "is_first_time_launch";

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_LAUNCH,isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return sharedPreferences.getBoolean(IS_FIRST_LAUNCH,true);
    }
}

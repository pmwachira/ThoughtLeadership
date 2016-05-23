package mushirih.thoughtleadership2;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by p-tah on 30/03/2016.
 */
public class Profs {


    public static final int NO_NAVIGATION = -1;
    private static final String TAG=ListCred.class.getSimpleName();
    private ArrayList<ProfItem> profSource;
    private DrawableProvider mProvider;
    private Context contextHere;
    public Profs(Context context) {
        contextHere=context;
        mProvider = new DrawableProvider(context);
        profSource = new ArrayList<ProfItem>();
                profSource.add(itemwithname("lol","lol","lol"));

    }



    public int getCount() {
        return profSource.size();
    }

    public ProfItem getItem(int position) {
        return profSource.get(position);
    }
    public  ProfItem itemwithname(String nam, String titl, String pro) {
        String name = null;

        String title=null;
        String prof=null;


        name = nam;
        title=titl;
        prof=pro;

 return new ProfItem(name,title,prof);
    }



}

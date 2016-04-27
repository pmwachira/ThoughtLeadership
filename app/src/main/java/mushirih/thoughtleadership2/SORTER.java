
package mushirih.thoughtleadership2;

        import android.content.Context;

import java.util.ArrayList;

/**
 * Created by p-tah on 05/02/2016.
 */
public class SORTER {
    public static final int NO_NAVIGATION = -1;
    private static final String TAG=MainActivity.class.getSimpleName();
    ArrayList<DataItem> filteredDataSource;
    DrawableProvider mProvider;
    DataSource mDataSource;
    String search;
    public SORTER(Context context,String URL_FEED,String search) {
        mProvider = new DrawableProvider(context);
        mDataSource=new DataSource(context,URL_FEED);
        filteredDataSource = new ArrayList<DataItem>();
       // Log.d("RECIEVED SEARCH=",MainActivity.search);
        sorted(search);
    }
    private void sorted(String sorter)
    {
        for(int j=0;j<mDataSource.getCount();j++){
            if(mDataSource.getItem(j).getContent().toLowerCase().contains(sorter)) {
                filteredDataSource.add(mDataSource.getItem(j));
            }else
                if(mDataSource.getItem(j).getLabel().toLowerCase().contains(sorter)){
                    filteredDataSource.add(mDataSource.getItem(j));
                }

        }
        if(filteredDataSource.size()==0){
            filteredDataSource.add(nullable());
        }

    }
    public int getCount() {
        return filteredDataSource.size();
    }

    public DataItem getItem(int position) {
        return filteredDataSource.get(position);
    }
/*
    private DataItem itemFromType(int type) {
        String label = null;
        String content="";
        String region="";
        String owner="";
        String downloadUrl="";
        Drawable drawable = null;
        switch (type) {
            case DrawableProvider.SAMPLE_RECT:
                label = "Rectangle with Text";
                drawable = mProvider.getRect(label.substring(0,1));
                break;
            case DrawableProvider.SAMPLE_ROUND_RECT:
                label = "Round Corner with Text";
                drawable = mProvider.getRoundRect("B");
                break;
            case DrawableProvider.SAMPLE_ROUND:
                label = "Round with Text";
                drawable = mProvider.getRound("C");
                break;
            case DrawableProvider.SAMPLE_RECT_BORDER:
                label = "Rectangle with Border";
                drawable = mProvider.getRectWithBorder("D");
                break;
            //USING THIS FOR THE WORK PROJECT
            case DrawableProvider.SAMPLE_ROUND_RECT_BORDER:
                label = "Round Corner with Border";
                drawable = mProvider.getRoundRectWithBorder(label.substring(0,1));
                break;
            case DrawableProvider.SAMPLE_ROUND_BORDER:
                label = "Round with Border";
                drawable = mProvider.getRoundWithBorder("F");
                break;
            case DrawableProvider.SAMPLE_MULTIPLE_LETTERS:
                label = "Support multiple letters";
                drawable = mProvider.getRectWithMultiLetter("ABC");
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_FONT:
                label = "Support variable font styles";
                drawable = mProvider.getRoundWithCustomFont();
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_SIZE:
                label = "Support for custom size";
                drawable = mProvider.getRectWithCustomSize();
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_ANIMATION:
                label = "Support for animations";
                drawable = mProvider.getRectWithAnimation();
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_MISC:
                label = "Miscellaneous";
                drawable =  mProvider.getRect("\u03c0");
                type = NO_NAVIGATION;
                break;
        }
        return new DataItem(label,content,region,owner,downloadUrl, drawable);
    }
    */
private DataItem itemwithname(String name, String content, String region, String owner,String displaypic, String download) {
    String label = null;
    String drawable = null;
    String myContent=null;
    String myRegion=null;
    String owneR=null;
    String downloadUrl=null;
    String displayPic=null;

    label = name;
    myRegion=region;
    owneR=owner;
    // drawable = mProvider.getRoundRectWithBorder(label.substring(0,1));

    myContent=content;
    displayPic=displaypic;
    downloadUrl=download;



    return new DataItem(label,myContent,myRegion,owneR,downloadUrl, drawable);
}



    private DataItem nullable(){
        String label="No item matches your search";

        return new DataItem(label,null,null,null,null,"null");
    }

}

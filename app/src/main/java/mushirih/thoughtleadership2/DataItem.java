package mushirih.thoughtleadership2;

/**
 * Created by p-tah on 12/10/2015.
 */
public class DataItem {
    private String label;
    private String content;
    private String region;
    private String owner;
    private String downloadUrl;
    private String drawable;
    private int navigationInfo;

    public DataItem(String label,String content,String region,String owner,String downloadUrl, String drawable){
        this.label=label;
        this.content=content;
        this.drawable=drawable;
        this.region=region;
        this.owner=owner;
        this.downloadUrl=downloadUrl;
        this.navigationInfo=navigationInfo;

    }



    public int getNavigationInfo() {

        return navigationInfo;
    }

    public String getDrawable() {
        return drawable;
    }

    public String getLabel() {

       return label;
    }
    public String getContent(){
        return content;
    }
    public String getRegion(){
        return region;
    }
    public String getDownloadUrl(){
        return downloadUrl;
    }

    public String getOwner() {
        return owner;
    }

}

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
    private String name;
    private String email;
    private String workTitle;

    public DataItem(String label, String content, String region, String owner, String downloadUrl, String drawable,String name, String email, String workTitle ){
        this.label=label;
        this.content=content;
        this.drawable=drawable;
        this.region=region;
        this.owner=owner;
        this.downloadUrl=downloadUrl;
        this.name=name;
        this.email=email;
        this.workTitle=workTitle;

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
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getWorkTitle() {
        return workTitle;
    }

}

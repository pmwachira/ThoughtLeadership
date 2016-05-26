package mushirih.thoughtleadership2;

/**
 * Created by p-tah on 30/03/2016.
 */
public class ProfItem {
    private String name;
    private String title;
     private String desc;
    private String downloadUrl;
   

    public ProfItem(String name, String title, String downloadUrl, String descr){
       this.name=name;
        this.title=title;
        this.downloadUrl=downloadUrl;
        this.desc=descr;


    }

    public String getName(){
        return name;
    }
    public String getTitle() {
        return title;
    }
    public String getDownloadUrl(){
        return downloadUrl;
    }
    public String getDesc(){
        return desc;
    }



}

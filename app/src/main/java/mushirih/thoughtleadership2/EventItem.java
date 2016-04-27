package mushirih.thoughtleadership2;

/**
 * Created by p-tah on 12/10/2015.
 */
public class EventItem {
    private String dat;
    private String mont;
    private String yea;
    private String titl;
    private String des;
    private String owne;
    private String bookur;

    public EventItem(String date, String month, String year, String title,String desc, String owner, String bookurl){
     this.dat=date;
        this.mont=month;
        this.yea=year;
        this.titl=title;
        this.des=desc;
        this.owne=owner;
        this.bookur=bookurl;

    }



    public String getDate() {

        return dat;
    }
    public String getMonth() {

        return mont;
    }
    public String getYear() {

        return yea;
    }
    public String getTitle() {

        return titl;
    }
    public String getDesc() {

        return des;
    }
    public String getBookUrl() {

        return bookur;
    }


    public String getOwner() {
        return owne;
    }

}

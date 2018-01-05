package mushirih.thoughtleadership2.eventsPictorial;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

/**
 * Created by mushirih on 22/12/2017.
 */

public class Mag {
    private String name;
    private String numOfSong;
    private String thumbnail;
    private String downloadUrl;

    public Mag() {
    }

    public Mag(String name, String numOfSong, String thumbnail,String downloadUrl) {
        this.name = name;
        this.numOfSong = numOfSong;
        this.thumbnail = thumbnail;
        this.downloadUrl=downloadUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumOfSong() {
        return numOfSong;
    }

    public void setNumOfSong(String numOfSong) {
        this.numOfSong = numOfSong;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}

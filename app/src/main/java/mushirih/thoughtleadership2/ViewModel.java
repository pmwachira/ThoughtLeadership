package mushirih.thoughtleadership2;

/**
 * Created by p-tah on 08/02/2016.
 */
public class ViewModel {
    private String text;
    private int image;

    public ViewModel(String text,int image) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}

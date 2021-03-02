package abhishekti.appyhigh.customshare.models;

/**
 * Created by Abhishek Tiwari on 24-02-2021.
 */
public class CustomShareDataText extends CustomShareData {

    private String text;

    public CustomShareDataText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

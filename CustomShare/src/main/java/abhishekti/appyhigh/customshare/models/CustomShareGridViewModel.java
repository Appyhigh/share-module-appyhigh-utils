package abhishekti.appyhigh.customshare.models;

import androidx.annotation.IdRes;

/**
 * Created by Abhishek Tiwari on 24-02-2021.
 */
public class CustomShareGridViewModel {

    private int item_id;
    private String item_title;

    @IdRes
    private int item_icon;

    public CustomShareGridViewModel(int item_id, String item_title, int item_icon) {
        this.item_id= item_id;
        this.item_title = item_title;
        this.item_icon = item_icon;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public int getItem_icon() {
        return item_icon;
    }

    public void setItem_icon(int item_icon) {
        this.item_icon = item_icon;
    }
}

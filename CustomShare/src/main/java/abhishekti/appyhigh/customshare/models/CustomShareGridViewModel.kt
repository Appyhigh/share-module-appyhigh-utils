package abhishekti.appyhigh.customshare.models

import androidx.annotation.IdRes

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class CustomShareGridViewModel(item_id: Int, item_title: String, item_icon: Int){

    public val item_id: Int = item_id
    public val item_title: String = item_title

    @IdRes
    public val item_icon: Int = item_icon


}
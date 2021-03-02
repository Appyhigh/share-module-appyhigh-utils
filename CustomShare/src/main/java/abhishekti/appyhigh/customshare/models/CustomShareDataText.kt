package abhishekti.appyhigh.customshare.models

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class CustomShareDataText(text: String) : CustomShareData() {
    private val text: String = text
    fun getText(): String {
        return text
    }

}

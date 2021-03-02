package abhishekti.appyhigh.customshare.models

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class Config {
    private var customShareData: CustomShareData? = null
    fun getCustomShareData(): CustomShareData? {
        return customShareData
    }

    fun setCustomShareData(customShareData: CustomShareData?) {
        this.customShareData = customShareData
    }
}
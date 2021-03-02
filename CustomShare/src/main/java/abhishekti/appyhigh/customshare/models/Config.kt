package abhishekti.appyhigh.customshare.models

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class Config {
    private var customShareData: CustomShareData? = null
    private var isLocalFile: Boolean = false

    fun getIsLocalFile(): Boolean{
        return isLocalFile
    }

    fun setIsLocalFile(isLocal: Boolean){
        this.isLocalFile = isLocal
    }

    fun getCustomShareData(): CustomShareData? {
        return customShareData
    }

    fun setCustomShareData(customShareData: CustomShareData?) {
        this.customShareData = customShareData
    }
}
package abhishekti.appyhigh.customshare

import abhishekti.appyhigh.customshare.models.Config
import abhishekti.appyhigh.customshare.models.CustomShareData

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class Builder(private var customShare: CustomShare) {

    private var customShareData: CustomShareData? = null
    private var config: Config = Config()
    private var isLocalFile: Boolean = false

    /**
     * Specifies whether the file is local to the app or not.
     * If this is set to true, then the library uses the FileProvider to share files since this is
     * the new standard for sharing internal files. An exception (URI Exposed exception)
     * is thrown if File Provider is not set
     * for sharing internal files/
     */
    fun isLocalFile(isLocal: Boolean): Builder{
        this.isLocalFile = isLocal
        return this
    }

    /**
     * Sets the data class for share intent
     */
    fun setShareData(data: CustomShareData): Builder{
        this.customShareData = data
        return this
    }

    fun show(){
        this.config.setCustomShareData(customShareData)
        this.config.setIsLocalFile(isLocalFile)
        customShare.show(config)
    }

}
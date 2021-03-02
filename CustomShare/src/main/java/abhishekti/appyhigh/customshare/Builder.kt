package abhishekti.appyhigh.customshare

import abhishekti.appyhigh.customshare.models.Config
import abhishekti.appyhigh.customshare.models.CustomShareData

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class Builder(customShare: CustomShare) {

    private var customShare: CustomShare = customShare
    private var customShareData: CustomShareData? = null
    private var config: Config = Config()

    public fun setShareData(data: CustomShareData): Builder{
        this.customShareData = data
        return this
    }

    public fun show(){
        this.config.setCustomShareData(customShareData)
        customShare.show(config)
    }

}
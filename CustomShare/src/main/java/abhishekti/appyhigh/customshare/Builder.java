package abhishekti.appyhigh.customshare;


import abhishekti.appyhigh.customshare.models.Config;
import abhishekti.appyhigh.customshare.models.CustomShareData;

/**
 * Created by Abhishek Tiwari on 19-01-2021.
 */
public class Builder {

    private CustomShareData shareData;
    private CustomShare customShare;
    private Config config;

    public Builder(CustomShare customShare){
        this.customShare = customShare;
        this.config = new Config();
    }

    public Builder setShareData(CustomShareData data){
        this.shareData = data;
        return this;
    }

    public void show(){
        this.config.setCustomShareData(shareData);
        customShare.show(config);
    }
}

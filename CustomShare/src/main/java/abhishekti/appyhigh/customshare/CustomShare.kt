package abhishekti.appyhigh.customshare

import abhishekti.appyhigh.customshare.Constants.CUSTOM_SHARE_BOTTOM_SHEET_TAG
import abhishekti.appyhigh.customshare.models.Config
import abhishekti.appyhigh.customshare.models.CustomShareDataFile
import abhishekti.appyhigh.customshare.models.CustomShareDataText
import abhishekti.appyhigh.customshare.ui.CustomShareBottomSheet
import android.app.Activity
import android.util.Log
import androidx.fragment.app.FragmentManager
import java.lang.ref.WeakReference

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
public class CustomShare private constructor(activity: Activity, manager: FragmentManager) {
    private val mActivity: WeakReference<Activity> = WeakReference(activity)
    private val fragmentManager: FragmentManager = manager

    fun show(config: Config?) {
        if(config == null){
            throw IllegalConfigurationException("The configuration parameter is null")
        }

        if(config.getCustomShareData() is CustomShareDataFile){
            val fileData = config.getCustomShareData() as CustomShareDataFile
            if(fileData.getFilePath()==null && config.getIsLocalFile()){
                throw IllegalDataParametersException(
                    "You have specified that file is a local file but file path is null. " +
                            "This will throw an Exception while creating the File provider uri")
            }

            if(fileData.getFilePath()==null && BuildConfig.DEBUG){
                Log.e(TAG, "Warning: File Path is empty")
            }

        }
        val bottomSheet = CustomShareBottomSheet(config)
        bottomSheet.show(fragmentManager, CUSTOM_SHARE_BOTTOM_SHEET_TAG)
    }

    /**
     * Sets the builder object for this instance
     */
    fun Builder(): Builder {
        return Builder(this)
    }

    fun getActivity(): Activity? {
        return mActivity.get()
    }

    companion object {
        private const val TAG = "CustomShare"
        fun getInstance(activity: Activity, fragmentManager: FragmentManager): CustomShare {
            return CustomShare(activity, fragmentManager)
        }
    }

}

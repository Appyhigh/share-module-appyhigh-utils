package abhishekti.appyhigh.customshare

import abhishekti.appyhigh.customshare.models.Config
import abhishekti.appyhigh.customshare.ui.CustomShareBottomSheet
import android.app.Activity
import androidx.fragment.app.FragmentManager
import java.lang.ref.WeakReference

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
public class CustomShare private constructor(activity: Activity, manager: FragmentManager) {
    private val mActivity: WeakReference<Activity> = WeakReference(activity)
    private val fragmentManager: FragmentManager = manager

    fun show(config: Config?) {
        val bottomSheet = CustomShareBottomSheet(config)
        bottomSheet.show(fragmentManager, "custom_share")
    }

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

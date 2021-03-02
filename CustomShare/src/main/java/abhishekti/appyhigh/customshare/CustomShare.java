package abhishekti.appyhigh.customshare;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import java.lang.ref.WeakReference;
import java.util.List;

import abhishekti.appyhigh.customshare.models.Config;
import abhishekti.appyhigh.customshare.ui.CustomShareBottomSheet;

/**
 * Created by Abhishek Tiwari on 18-01-2021.
 */
public final class CustomShare {
    private static final String TAG = "CustomShare";
    private final WeakReference<Activity> mActivity;
    private final FragmentManager fragmentManager;

    private CustomShare(Activity activity, FragmentManager manager){
        this.mActivity = new WeakReference<>(activity);
        this.fragmentManager = manager;
    }

    public static CustomShare getInstance(Activity activity, FragmentManager fragmentManager){
        return new CustomShare(activity, fragmentManager);
    }

    public void show(Config config){
        CustomShareBottomSheet bottomSheet = new CustomShareBottomSheet(config);
        bottomSheet.show(fragmentManager, "custom_share");
    }

    public Builder Builder(){
        return new Builder(this);
    }

    @Nullable
    Activity getActivity(){
        return mActivity.get();
    }

}

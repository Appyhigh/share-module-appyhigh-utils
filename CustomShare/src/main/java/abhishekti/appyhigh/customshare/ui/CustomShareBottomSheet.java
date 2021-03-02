package abhishekti.appyhigh.customshare.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import abhishekti.appyhigh.customshare.BuildConfig;
import abhishekti.appyhigh.customshare.Builder;
import abhishekti.appyhigh.customshare.Constants;
import abhishekti.appyhigh.customshare.CustomShare;
import abhishekti.appyhigh.customshare.R;
import abhishekti.appyhigh.customshare.adapters.CustomShareGridItemAdapter;
import abhishekti.appyhigh.customshare.databinding.FragmentCustomShareBottomSheetBinding;
import abhishekti.appyhigh.customshare.models.Config;
import abhishekti.appyhigh.customshare.models.CustomShareData;
import abhishekti.appyhigh.customshare.models.CustomShareDataText;
import abhishekti.appyhigh.customshare.models.CustomShareFile;
import abhishekti.appyhigh.customshare.models.CustomShareGridViewModel;

public class CustomShareBottomSheet extends BottomSheetDialogFragment {

    private FragmentCustomShareBottomSheetBinding bottomSheetBinding;
    private Context context;
    private ArrayList<CustomShareGridViewModel> item_list;
    private CustomShareGridItemAdapter adapter;
    private Config mConfig;
    private Intent shareIntent;
    private List<String> blacklist;


    public CustomShareBottomSheet(){
        // Required empty public constructor
    }

    public CustomShareBottomSheet(Config config) {
        this.mConfig = config;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bottomSheetBinding = FragmentCustomShareBottomSheetBinding.inflate(inflater, container, false);
        View view = bottomSheetBinding.getRoot();
        context = view.getContext();

        initConfig();
        return view;
    }

    private void initConfig() {
        blacklist = new ArrayList<>(Arrays.asList("com.whatsapp", "com.facebook.katana", "com.instagram.android", "com.snapchat.android", "com.twitter.android"));

        shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);


        // populate the grid view items
        item_list = new ArrayList<>();
        item_list.add(new CustomShareGridViewModel(Constants.CUSTOM_SHARE_ID_WHATSAPP, context.getResources().getString(R.string.whatsapp), R.drawable.custom_share_ic_whatsapp));
        item_list.add(new CustomShareGridViewModel(Constants.CUSTOM_SHARE_ID_MESSENGER, context.getResources().getString(R.string.messenger), R.drawable.custom_share_ic_messenger));
        item_list.add(new CustomShareGridViewModel(Constants.CUSTOM_SHARE_ID_INSTAGRAM, context.getResources().getString(R.string.instagram), R.drawable.custom_share_ic_instagram));
        item_list.add(new CustomShareGridViewModel(Constants.CUSTOM_SHARE_ID_FACEBOOK, context.getResources().getString(R.string.facebook), R.drawable.custom_share_ic_facebook));
        item_list.add(new CustomShareGridViewModel(Constants.CUSTOM_SHARE_ID_SNAPCHAT, context.getResources().getString(R.string.snapchat), R.drawable.custom_share_ic_snapchat));
        item_list.add(new CustomShareGridViewModel(Constants.CUSTOM_SHARE_ID_TWITTER, context.getResources().getString(R.string.twitter), R.drawable.custom_share_ic_twitter));
        item_list.add(new CustomShareGridViewModel(Constants.CUSTOM_SHARE_ID_MORE, context.getResources().getString(R.string.more_apps), R.drawable.custom_share_ic_more_apps));


        if(mConfig.getCustomShareData() instanceof CustomShareDataText){
            CustomShareDataText textData = (CustomShareDataText) mConfig.getCustomShareData();

            bottomSheetBinding.customShareFileName.setText(textData.getText());
            bottomSheetBinding.customShareFileType.setText("Text");
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, textData.getText());
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.share_text));
            item_list.add(new CustomShareGridViewModel(Constants.CUSTOM_SHARE_ID_COPY, context.getResources().getString(R.string.copy_link), R.drawable.custom_share_ic_copy));
        }else{
            CustomShareFile fileData = (CustomShareFile) mConfig.getCustomShareData();

            shareIntent.setType(fileData.getFileType());
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileData.getFileUri());
            
            if(fileData.getFileType().toLowerCase().contains("image")){
                Glide.with(context)
                        .load(fileData.getFileUri())
                        .centerCrop()
                        .into(bottomSheetBinding.customShareIcShare);
            }

            bottomSheetBinding.customShareFileName.setText(fileData.getFileName());
            bottomSheetBinding.customShareFileType.setText(fileData.getDisplayFileType());
        }

        adapter = new CustomShareGridItemAdapter(context, item_list);
        bottomSheetBinding.customShareGridView.setAdapter(adapter);

        bottomSheetBinding.customShareGridView.setOnItemClickListener((parent, view, position, id) -> {
            if(position==Constants.CUSTOM_SHARE_ID_WHATSAPP){
                shareTargeted("com.whatsapp");
            }else if(position == Constants.CUSTOM_SHARE_ID_MESSENGER){
                shareTargeted("com.facebook.orca");
            }else if(position == Constants.CUSTOM_SHARE_ID_INSTAGRAM){
                shareTargeted("com.instagram.android");
            }else if(position == Constants.CUSTOM_SHARE_ID_FACEBOOK){
                shareTargeted("com.facebook.katana");
            }else if(position == Constants.CUSTOM_SHARE_ID_SNAPCHAT){
                shareTargeted("com.snapchat.android");
            }else if(position == Constants.CUSTOM_SHARE_ID_TWITTER){
                shareTargeted("com.twitter.android");
            }else if(position == Constants.CUSTOM_SHARE_ID_COPY){
                copyText();
            }else if(position == Constants.CUSTOM_SHARE_ID_MORE){
                startActivity(Intent.createChooser(shareIntent, context.getResources().getString(R.string.share_text)));
            }
            dismiss();
        });
    }

    private void copyText() {
        CustomShareDataText textData = (CustomShareDataText)mConfig.getCustomShareData();
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(
                context.getResources().getString(R.string.share_text),
                textData.getText()
        );
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(context, "Text copied", Toast.LENGTH_SHORT).show();
    }

    private void openPlayStore(String packageName){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+packageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+packageName)));
        }
    }

    private void shareTargeted(String packageName){
        shareIntent.setPackage(packageName);
        try{
            startActivity(shareIntent);
        }catch (Exception e){
            openPlayStore(packageName);
        }
    }

}
package abhishekti.appyhigh.customshareintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;

import java.io.File;

import abhishekti.appyhigh.customshare.Builder;
import abhishekti.appyhigh.customshare.Constants;
import abhishekti.appyhigh.customshare.CustomShare;
import abhishekti.appyhigh.customshare.models.CustomShareDataText;
import abhishekti.appyhigh.customshare.models.CustomShareFile;
import abhishekti.appyhigh.customshare.ui.CustomShareBottomSheet;
import abhishekti.appyhigh.customshareintent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding mainBinding;
    private SHARE_TYPE share_type;

    public static final int SHARE_REQ_CODE = 1001;

    public static enum SHARE_TYPE {
        SHARE_IMAGE, SHARE_TEXT, SHARE_PDF
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.btnShareImage.setOnClickListener((v) -> {
            share_type = SHARE_TYPE.SHARE_IMAGE;
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpeg");
            startActivityForResult(intent, SHARE_REQ_CODE);
        });

        mainBinding.btnShareText.setOnClickListener((v) -> {
            share_type = SHARE_TYPE.SHARE_PDF;

            CustomShare.getInstance(this, getSupportFragmentManager())
                    .Builder()
                    .setShareData(new CustomShareDataText(getString(R.string.dummy_text)))
                    .show();
        });

        mainBinding.btnSharePdf.setOnClickListener((v) -> {
            share_type = SHARE_TYPE.SHARE_PDF;
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, SHARE_REQ_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHARE_REQ_CODE && resultCode == RESULT_OK) {
            Log.e(TAG, data.getData().toString());
            FileMetaData fileMetaData = getFileMetaData(getApplicationContext(), data.getData());
            String type = "";
            if (share_type == SHARE_TYPE.SHARE_IMAGE) {
                type = "image/jpeg";
            } else if (share_type == SHARE_TYPE.SHARE_PDF) {
                type = "application/pdf";
            }
            CustomShare.getInstance(MainActivity.this, getSupportFragmentManager())
                    .Builder()
                    .setShareData(
                            new CustomShareFile(
                                    fileMetaData.path,
                                    fileMetaData.displayName,
                                    data.getData(),
                                    type
                            )
                    )
                    .show();
        }
    }

    public static class FileMetaData {
        public String displayName;
        public long size;
        public String mimeType;
        public String path;

        @Override
        public String toString() {
            return "name : " + displayName + " ; size : " + size + " ; path : " + path + " ; mime : " + mimeType;
        }
    }


    public static FileMetaData getFileMetaData(Context context, Uri uri) {
        FileMetaData fileMetaData = new FileMetaData();

        if ("file".equalsIgnoreCase(uri.getScheme())) {
            File file = new File(uri.getPath());
            fileMetaData.displayName = file.getName();
            fileMetaData.size = file.length();
            fileMetaData.path = file.getPath();

            return fileMetaData;
        } else {
            ContentResolver contentResolver = context.getContentResolver();

            try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
                fileMetaData.mimeType = contentResolver.getType(uri);
                if (cursor != null && cursor.moveToFirst()) {
                    int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                    fileMetaData.displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                    if (!cursor.isNull(sizeIndex))
                        fileMetaData.size = cursor.getLong(sizeIndex);
                    else
                        fileMetaData.size = -1;

                    try {
                        fileMetaData.path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                    } catch (Exception e) {
                        // DO NOTHING, _data does not exist
                    }

                    return fileMetaData;
                }
            } catch (Exception e) {
                Log.e(TAG, "exception thrown: ", e);
            }

            return null;
        }
    }

}
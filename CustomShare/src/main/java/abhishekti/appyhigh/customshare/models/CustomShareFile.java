package abhishekti.appyhigh.customshare.models;

import android.net.Uri;

import java.util.HashMap;

/**
 * Created by Abhishek Tiwari on 24-02-2021.
 */
public class CustomShareFile extends CustomShareData{

    private String filePath;
    private String fileName;
    private Uri fileUri;
    private String fileType;

    public CustomShareFile(String filePath, String fileName, Uri uri, String fileType) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileUri = uri;
        this.fileType = fileType;
    }

    public String getDisplayFileType(){
        int index = this.fileName.lastIndexOf(".");
        if(index==-1){
            return "File";
        }else{
            String extension = this.fileName.substring(index).toLowerCase();
            String result = "";
            if (extension.contains("jpeg")){
                result = "JPEG Image";
            }else if(extension.contains("png")){
                result = "PNG Image";
            }else if(extension.contains("jpg")){
                result = "JPG Image";
            }else if(extension.contains("pdf")){
                result = "PDF";
            }
            return result;
        }
    }

    public String getFileType() {
        return fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public Uri getFileUri() {
        return fileUri;
    }
}

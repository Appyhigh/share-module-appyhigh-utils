package abhishekti.appyhigh.customshare.models

import android.net.Uri
import java.util.*

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class CustomShareDataFile(filePath: String?, fileName: String, fileUri: Uri?, fileType: String) :
    CustomShareData() {
    private val filePath: String? = filePath
    private val fileName = fileName
    private val fileUri: Uri? = fileUri
    private val fileType = fileType

    /**
     * This function returns the file type to be displayed on the share intent
     * bottom sheet. It is based on checking of the file extension
     */
    fun getDisplayFileType(): String {
        val index: Int = fileName.lastIndexOf(".")
        if (index == -1) {
            return "File"
        } else {
            var result = ""
            val extension: String = fileName.substring(index).toLowerCase(Locale.ROOT)
            if (extension.contains("jpeg")) {
                result = "JPEG Image"
            } else if (extension.contains("png")) {
                result = "PNG Image"
            } else if (extension.contains("jpg")) {
                result = "JPG Image"
            } else if (extension.contains("pdf")) {
                result = "PDF"
            } else if(extension.contains("txt")){
                result = "Text File"
            } else if(extension.contains("pdf")){
                result = "PDF File"
            } else if(extension.contains("xls")){
                result = "Excel File"
            } else if(extension.contains("docx")){
                result = "Document File"
            } else {
                result = "File"
            }
            return result
        }
    }

    fun getFilePath(): String? {
        return filePath
    }

    fun getFileName(): String {
        return fileName
    }

    fun getFileUri(): Uri? {
        return fileUri
    }

    fun getFileType(): String {
        return fileType
    }
}
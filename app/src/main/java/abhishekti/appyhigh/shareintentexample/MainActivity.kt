package abhishekti.appyhigh.shareintentexample

import abhishekti.appyhigh.shareintentexample.databinding.ActivityMainBinding
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import java.io.File
import java.lang.Exception
import abhishekti.appyhigh.customshare.CustomShare
import abhishekti.appyhigh.customshare.models.CustomShareData
import abhishekti.appyhigh.customshare.models.CustomShareDataFile
import abhishekti.appyhigh.customshare.models.CustomShareDataText

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var shareType: SHARE_TYPE

    private val SHARE_REQ_CODE = 1001
    private enum class SHARE_TYPE{
        SHARE_IMAGE, SHARE_TEXT, SHARE_PDF
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


        mainBinding.btnShareText.setOnClickListener {
            shareType = SHARE_TYPE.SHARE_TEXT
            CustomShare.getInstance(activity = this, fragmentManager = supportFragmentManager)
                .Builder()
                .setShareData(CustomShareDataText(resources.getString(R.string.dummy_text)))
                .show()
        }

        mainBinding.btnShareImage.setOnClickListener {
            shareType = SHARE_TYPE.SHARE_IMAGE
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/jpeg"
            startActivityForResult(intent, SHARE_REQ_CODE)
        }

        mainBinding.btnSharePdf.setOnClickListener {
            shareType = SHARE_TYPE.SHARE_PDF
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "application/pdf"
            startActivityForResult(intent, SHARE_REQ_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SHARE_REQ_CODE && resultCode == RESULT_OK){
            Log.e(TAG, data?.data.toString() + " ")
            if(data!=null && data.data!=null){
                val fileMetaData: FileMetaData? = getFileMetaData(applicationContext, data.data!!)
                if(fileMetaData!=null){
                    var type = ""
                    if(shareType == SHARE_TYPE.SHARE_IMAGE){
                        type = "image/jpeg"
                    }else if(shareType == SHARE_TYPE.SHARE_PDF){
                        type = "application/pdf"
                    }
                    CustomShare.getInstance(this, supportFragmentManager)
                        .Builder()
                        .setShareData(CustomShareDataFile(
                            fileMetaData.path,
                            fileMetaData.displayName,
                            data.data!!,
                            type
                        ))
                        .show()
                }
            }
        }
    }

    public class FileMetaData  {
        public lateinit var displayName: String
        public var size: Long = 0
        public lateinit var mimeType: String
        public lateinit var path: String

        override fun toString(): String {
            return "name: ${displayName}, size: ${size}, mimeType: ${mimeType}, path: ${path}";
        }
    }

    public fun getFileMetaData(context: Context, uri: Uri): FileMetaData?{
        val fileMetaData = FileMetaData()
        if("file".equals(uri.scheme, ignoreCase = true)){
            val file = File(uri.path)
            fileMetaData.path = file.path
            fileMetaData.displayName = file.name
            fileMetaData.size = file.length()
            return fileMetaData
        }else{
            val contentResolver = context.contentResolver
            try{
                val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
                fileMetaData.mimeType = contentResolver.getType(uri)!!
                if(cursor!=null && cursor.moveToFirst()){
                    var sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    fileMetaData.displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    if(!cursor.isNull(sizeIndex)){
                        fileMetaData.size = cursor.getLong(sizeIndex)
                    }else{
                        fileMetaData.size = -1
                    }

                    try{
                        fileMetaData.path = cursor.getString(cursor.getColumnIndexOrThrow("_data"))
                    }catch (e: Exception){
                        e.printStackTrace()
                        fileMetaData.path = ""
                    }
                    return fileMetaData
                }
                cursor?.close()
            }catch (e: Exception){
                e.printStackTrace()
            }
            return null
        }
    }
}
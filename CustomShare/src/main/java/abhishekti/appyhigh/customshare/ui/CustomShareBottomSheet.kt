package abhishekti.appyhigh.customshare.ui

import abhishekti.appyhigh.customshare.Constants
import abhishekti.appyhigh.customshare.R
import abhishekti.appyhigh.customshare.adapters.CustomShareGridItemAdapter
import abhishekti.appyhigh.customshare.databinding.FragmentCustomShareBottomSheetBinding
import abhishekti.appyhigh.customshare.models.Config
import abhishekti.appyhigh.customshare.models.CustomShareDataFile
import abhishekti.appyhigh.customshare.models.CustomShareDataText
import abhishekti.appyhigh.customshare.models.CustomShareGridViewModel
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class CustomShareBottomSheet(private val config: Config?) : BottomSheetDialogFragment() {

    private lateinit var bottomSheetBinding: FragmentCustomShareBottomSheetBinding
    private lateinit var shareIntent: Intent


    /**
     * Overriding this function to catch exceptions in the cases when the button is clicked
     * twice and two or more successive add fragment calls are queued up.
     */
    override fun show(manager: FragmentManager, tag: String?) {
        try {
            manager.beginTransaction().remove(this).commit()
            super.show(manager, tag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomSheetBinding =
            FragmentCustomShareBottomSheetBinding.inflate(inflater, container, false)
        val view: View = bottomSheetBinding.root

        initConfig()
        return view
    }

    private fun initConfig() {
        shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND

        val item_list: ArrayList<CustomShareGridViewModel> = ArrayList()
        item_list.add(
            CustomShareGridViewModel(
                Constants.CUSTOM_SHARE_ID_WHATSAPP,
                context!!.resources.getString(R.string.whatsapp),
                R.drawable.custom_share_ic_whatsapp
            )
        )
        item_list.add(
            CustomShareGridViewModel(
                Constants.CUSTOM_SHARE_ID_MESSENGER,
                context!!.resources.getString(R.string.messenger),
                R.drawable.custom_share_ic_messenger
            )
        )
        item_list.add(
            CustomShareGridViewModel(
                Constants.CUSTOM_SHARE_ID_INSTAGRAM,
                context!!.resources.getString(R.string.instagram),
                R.drawable.custom_share_ic_instagram
            )
        )
        item_list.add(
            CustomShareGridViewModel(
                Constants.CUSTOM_SHARE_ID_FACEBOOK,
                context!!.resources.getString(R.string.facebook),
                R.drawable.custom_share_ic_facebook
            )
        )
        item_list.add(
            CustomShareGridViewModel(
                Constants.CUSTOM_SHARE_ID_SNAPCHAT,
                context!!.resources.getString(R.string.snapchat),
                R.drawable.custom_share_ic_snapchat
            )
        )
        item_list.add(
            CustomShareGridViewModel(
                Constants.CUSTOM_SHARE_ID_TWITTER,
                context!!.resources.getString(R.string.twitter),
                R.drawable.custom_share_ic_twitter
            )
        )
        item_list.add(
            CustomShareGridViewModel(
                Constants.CUSTOM_SHARE_ID_MORE,
                context!!.resources.getString(R.string.more_apps),
                R.drawable.custom_share_ic_more_apps
            )
        )


        if (config!!.getCustomShareData() is CustomShareDataText) {
            val textData: CustomShareDataText = config.getCustomShareData() as CustomShareDataText

            bottomSheetBinding.customShareFileName.text = textData.getText()
            bottomSheetBinding.customShareFileType.text = "Text"
            bottomSheetBinding.customShareIcShare.setImageResource(R.drawable.custom_share_text_placeholder)

            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, textData.getText())
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                context!!.resources.getString(R.string.share_text)
            )
            item_list.add(
                CustomShareGridViewModel(
                    Constants.CUSTOM_SHARE_ID_COPY,
                    context!!.resources.getString(R.string.copy_link),
                    R.drawable.custom_share_ic_copy
                )
            )
        } else {
            val fileData: CustomShareDataFile = config.getCustomShareData() as CustomShareDataFile
            val fileType: String = fileData.getFileType()

            shareIntent.type = fileType

            if (config.getIsLocalFile()) {
                val uri: Uri = FileProvider.getUriForFile(
                    context!!,
                    context!!.applicationContext.packageName + ".provider",
                    File(fileData.getFilePath()!!)
                )
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                shareIntent.putExtra(Intent.EXTRA_STREAM, fileData.getFileUri())
            }


//            if (fileType.toLowerCase(Locale.ROOT).contains("image")) {
//                Glide.with(context!!)
//                    .load(fileData.getFileUri())
//                    .centerCrop()
//                    .into(bottomSheetBinding.customShareIcShare)
//            } else if (fileType.toLowerCase(Locale.ROOT).contains("pdf")) {
//                bottomSheetBinding.customShareIcShare.setImageResource(R.drawable.custom_share_pdf_placeholder)
//            } else if (fileType.toLowerCase(Locale.ROOT).contains("text")) {
//                val textIcon: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.custom_share_text_placeholder)
//                val canvas: Canvas = Canvas(textIcon)
//                canvas.drawColor(Color.WHITE)
//                canvas.drawBitmap(textIcon, 0, null)
//
//                Glide.with(context!!)
//                    .load(textIcon)
//                    .into(bottomSheetBinding.customShareIcShare)
//            } else if (fileType.toLowerCase(Locale.ROOT).contains("excel")) {
//                bottomSheetBinding.customShareIcShare.setImageResource(R.drawable.custom_share_excel_placeholder)
//            } else if (fileType.toLowerCase(Locale.ROOT).contains("document")) {
//                bottomSheetBinding.customShareIcShare.setImageResource(R.drawable.custom_share_docx_placeholder)
//            } else {
//                bottomSheetBinding.customShareIcShare.setImageResource(R.drawable.custom_share_file_placeholder)
//            }

            bottomSheetBinding.customShareFileName.text = fileData.getFileName()
            bottomSheetBinding.customShareFileType.text = fileData.getDisplayFileType()
        }

        val adapter = CustomShareGridItemAdapter(context!!, item_list)
        bottomSheetBinding.customShareGridView.adapter = adapter
        bottomSheetBinding.customShareGridView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                if (position == Constants.CUSTOM_SHARE_ID_WHATSAPP) {
                    shareTargeted("com.whatsapp")
                } else if (position == Constants.CUSTOM_SHARE_ID_MESSENGER) {
                    shareTargeted("com.facebook.orca")
                } else if (position == Constants.CUSTOM_SHARE_ID_INSTAGRAM) {
                    shareTargeted("com.instagram.android")
                } else if (position == Constants.CUSTOM_SHARE_ID_FACEBOOK) {
                    shareTargeted("com.facebook.katana")
                } else if (position == Constants.CUSTOM_SHARE_ID_SNAPCHAT) {
                    shareTargeted("com.snapchat.android")
                } else if (position == Constants.CUSTOM_SHARE_ID_TWITTER) {
                    shareTargeted("com.twitter.android")
                } else if (position == Constants.CUSTOM_SHARE_ID_COPY) {
                    copyText()
                } else if (position == Constants.CUSTOM_SHARE_ID_MORE) {
                    startActivity(
                        Intent.createChooser(
                            shareIntent,
                            context!!.resources.getString(R.string.share_text)
                        )
                    )
                }
                dismiss()
            }
    }

    fun copyText() {
        val textData: CustomShareDataText = config!!.getCustomShareData() as CustomShareDataText
        val clipboardManager: ClipboardManager =
            context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(
            context!!.resources.getString(R.string.share_text),
            textData.getText()
        )
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context!!, "Text copied", Toast.LENGTH_SHORT).show()
    }

    fun openPlayStore(packageName: String) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=${packageName}")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=${packageName}")
                )
            )
        }
    }

    fun shareTargeted(packageName: String) {
        shareIntent.`package` = packageName;
        try {
            startActivity(shareIntent)
        } catch (e: Exception) {
            e.printStackTrace()
            openPlayStore(packageName)
        }
    }
}
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
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.Exception

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class CustomShareBottomSheet(config: Config?) : BottomSheetDialogFragment() {

    private lateinit var bottomSheetBinding: FragmentCustomShareBottomSheetBinding
    private val config = config
    private lateinit var shareIntent: Intent


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomSheetBinding =
            FragmentCustomShareBottomSheetBinding.inflate(inflater, container, false)
        val view: View = bottomSheetBinding.root
        val context = view.context

        initConfig()
        return view
    }

    private fun initConfig() {
        shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND

        var item_list: ArrayList<CustomShareGridViewModel> = ArrayList()
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

            shareIntent.type = fileData.getFileType()
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileData.getFileUri())

            if (fileData.getFileType().toLowerCase().equals("image")) {
                Glide.with(context!!)
                    .load(fileData.getFileUri())
                    .centerCrop()
                    .into(bottomSheetBinding.customShareIcShare)
            }
            bottomSheetBinding.customShareFileName.text = fileData.getFileName()
            bottomSheetBinding.customShareFileType.text = fileData.getDisplayFileType()
        }

        val adapter = CustomShareGridItemAdapter(context!!, item_list)
        bottomSheetBinding.customShareGridView.adapter = adapter
        bottomSheetBinding.customShareGridView.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            if(position == Constants.CUSTOM_SHARE_ID_WHATSAPP){
                shareTargeted("com.whatsapp")
            }else if(position == Constants.CUSTOM_SHARE_ID_MESSENGER){
                shareTargeted("com.facebook.orca")
            }else if(position == Constants.CUSTOM_SHARE_ID_INSTAGRAM){
                shareTargeted("com.instagram.android")
            }else if(position == Constants.CUSTOM_SHARE_ID_FACEBOOK){
                shareTargeted("com.facebook.katana")
            }else if(position == Constants.CUSTOM_SHARE_ID_SNAPCHAT){
                shareTargeted("com.snapchat.android")
            }else if(position == Constants.CUSTOM_SHARE_ID_TWITTER){
                shareTargeted("com.twitter.android")
            }else if(position == Constants.CUSTOM_SHARE_ID_COPY){
                copyText()
            }else if(position == Constants.CUSTOM_SHARE_ID_MORE){
                startActivity(Intent.createChooser(shareIntent, context!!.resources.getString(R.string.share_text)))
            }
            dismiss()
        }
    }

    fun copyText(){
        val textData: CustomShareDataText = config!!.getCustomShareData() as CustomShareDataText
        val clipboardManager:ClipboardManager = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(context!!.resources.getString(R.string.share_text), textData.getText())
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context!!, "Text copied", Toast.LENGTH_SHORT).show()
    }

    fun openPlayStore(packageName: String){
        try{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${packageName}")))
        }catch (e: ActivityNotFoundException){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${packageName}")))
        }
    }

    fun shareTargeted(packageName: String){
        shareIntent.`package` = packageName;
        try{
            startActivity(shareIntent)
        }catch (e: Exception){
            openPlayStore(packageName)
        }
    }
}
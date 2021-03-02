package abhishekti.appyhigh.customshare.adapters

import abhishekti.appyhigh.customshare.R
import abhishekti.appyhigh.customshare.models.CustomShareGridViewModel
import abhishekti.appyhigh.customshare.ui.CustomShareTextView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

/**
 * Created by Abhishek Tiwari on 02-03-2021.
 */
class CustomShareGridItemAdapter(context: Context, item_list: ArrayList<CustomShareGridViewModel>): BaseAdapter() {

    private val mContext: Context = context
    private val item_list: ArrayList<CustomShareGridViewModel> = item_list

    override fun getCount(): Int {
        return item_list.size
    }

    override fun getItemId(position: Int): Long {
        return item_list.get(position).item_id.toLong()
    }

    override fun getItem(position: Int): Any {
        return item_list.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val model: CustomShareGridViewModel = item_list.get(position)

        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_share_grid_view_item, null)
        }

        val icon: ImageView = convertView!!.findViewById(R.id.custom_share_item_icon)
        val textView: CustomShareTextView = convertView.findViewById(R.id.custom_share_item_title)

        icon.setImageResource(model.item_icon)
        textView.setText(model.item_title)

        return convertView!!
    }
}
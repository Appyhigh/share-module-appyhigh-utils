package abhishekti.appyhigh.customshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import abhishekti.appyhigh.customshare.R;
import abhishekti.appyhigh.customshare.models.CustomShareGridViewModel;
import abhishekti.appyhigh.customshare.ui.CustomShareTextView;

/**
 * Created by Abhishek Tiwari on 24-02-2021.
 */
public class CustomShareGridItemAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<CustomShareGridViewModel> item_list;

    public CustomShareGridItemAdapter(Context mContext, ArrayList<CustomShareGridViewModel> item_list) {
        this.mContext = mContext;
        this.item_list = item_list;
    }

    @Override
    public int getCount() {
        return item_list.size();
    }

    @Override
    public long getItemId(int position) {
        return item_list.get(position).getItem_id();
    }

    @Override
    public Object getItem(int position) {
        return item_list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomShareGridViewModel model = item_list.get(position);

        if(convertView==null){
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.custom_share_grid_view_item, null);
        }

        final ImageView icon = convertView.findViewById(R.id.custom_share_item_icon);
        final CustomShareTextView textView = convertView.findViewById(R.id.custom_share_item_title);

        icon.setImageResource(model.getItem_icon());
        textView.setText(model.getItem_title());

        return convertView;
    }
}

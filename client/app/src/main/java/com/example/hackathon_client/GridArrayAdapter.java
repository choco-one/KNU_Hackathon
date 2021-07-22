package com.example.hackathon_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.hackathon_client.MainActivity.ImageItemClickListener;

import java.util.List;

public class GridArrayAdapter extends ArrayAdapter<GridItem> {
    private static final int LAYOUT_RESOURCE_ID = R.layout.content_grid_item;

    private Context mContext;
    private List<GridItem> mItemList;
    private ImageItemClickListener mImageItemClickListener;

    public GridArrayAdapter(Context a_context, List<GridItem> a_itemList) {
        super(a_context, LAYOUT_RESOURCE_ID, a_itemList);

        mContext = a_context;
        mItemList = a_itemList;
    }

    @Override
    public View getView(int a_position, View a_convertView, ViewGroup a_parent) {
        GridItemViewHolder viewHolder;
        if (a_convertView == null) {
            a_convertView = LayoutInflater.from(mContext).inflate(LAYOUT_RESOURCE_ID, a_parent, false);

            viewHolder = new GridItemViewHolder(a_convertView);
            a_convertView.setTag(viewHolder);
        } else {
            viewHolder = (GridItemViewHolder) a_convertView.getTag();
        }

        final GridItem countryItem = mItemList.get(a_position);

        // Icon 설정
        viewHolder.ivIcon.setImageResource(countryItem.getImageResId());
        viewHolder.ivIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View a_view) {
                if (mImageItemClickListener != null) {
                    mImageItemClickListener.onImageItemClick(countryItem.getName(), a_position);
                }
            }
        });
        // Name 설정
        viewHolder.tvName.setText(countryItem.getName());

        return a_convertView;
    }
    public void setImageItemClickListener(ImageItemClickListener a_listener) {
        mImageItemClickListener = a_listener;
    }
}

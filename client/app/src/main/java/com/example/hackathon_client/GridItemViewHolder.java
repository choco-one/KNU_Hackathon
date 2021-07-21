package com.example.hackathon_client;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GridItemViewHolder {
    public ImageView ivIcon;

    public TextView tvName;

    public GridItemViewHolder(View a_view) {
        ivIcon = a_view.findViewById(R.id.iv_icon);
        tvName = a_view.findViewById(R.id.tv_name);
    }
}

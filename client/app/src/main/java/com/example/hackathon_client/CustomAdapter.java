package com.example.hackathon_client;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Chat> localDataSet;
    String stMyEmail = "";

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = view.findViewById(R.id.tvChat);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (localDataSet.get(position).email.equals(stMyEmail)){
            return 1;
        }
        else{
            return 2;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param MydataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(ArrayList<Chat> MydataSet, String stEmail) {
        localDataSet = MydataSet;
        this.stMyEmail = stEmail;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        if(viewType == 1){
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.right_text_view, viewGroup, false);
        }

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.getTextView().setText(localDataSet.get(position).getText());
        viewHolder.getTextView().setBackground(viewHolder.getTextView().getContext().getResources().getDrawable((R.drawable.theme_chatroom_bubble_me_02_image)));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

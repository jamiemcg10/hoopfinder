package com.example.hoopfinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SubscriberAdapter extends RecyclerView.Adapter<SubscriberAdapter.MyViewHolder> {
    private List<User> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView nameTextView;
        Button messageButton;

        public TextView textView;
        public MyViewHolder(View itemView) {

            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.courtName);
            messageButton = (Button) itemView.findViewById(R.id.subscribe);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SubscriberAdapter(List<User> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SubscriberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view


        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.court_recyclerview_row, parent, false);

        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        User testUser = mDataset.get(position);
        holder.nameTextView.setText(testUser.user_email);

        //Log.d("Court names", testCourt.getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mDataset == null){
            return 0;
        } else
            return mDataset.size();
    }
}
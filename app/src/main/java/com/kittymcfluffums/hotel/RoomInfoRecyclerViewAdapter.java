package com.kittymcfluffums.hotel;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class RoomInfoRecyclerViewAdapter extends
        RecyclerView.Adapter<RoomInfoRecyclerViewAdapter.ViewHolder> {

    private final List<HashMap<String, String>> mValues;

    public RoomInfoRecyclerViewAdapter(List<HashMap<String, String>> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_room_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.format("Room %s", mValues.get(position).get("room_number")));
        String availability = mValues.get(position).get("is_available");
        if (availability.equals("yes")) {
            holder.mContentView.setText("Available");
        } else {
            holder.mContentView.setText("Unavailable");
            holder.mContentView.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public HashMap<String, String> mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.room_number);
            mContentView = (TextView) view.findViewById(R.id.room_available);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

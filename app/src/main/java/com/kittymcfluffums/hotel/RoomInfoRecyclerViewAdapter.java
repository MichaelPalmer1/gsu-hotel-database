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

    private final List<HashMap<String, Object>> mValues;

    public RoomInfoRecyclerViewAdapter(List<HashMap<String, Object>> items) {
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
        Boolean availability = (Boolean) mValues.get(position).get("is_available");
        if (availability) {
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
        public HashMap<String, Object> mItem;

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

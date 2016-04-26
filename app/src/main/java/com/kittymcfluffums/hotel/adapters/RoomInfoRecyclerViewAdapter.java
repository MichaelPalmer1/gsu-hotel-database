package com.kittymcfluffums.hotel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kittymcfluffums.hotel.Listeners;
import com.kittymcfluffums.hotel.R;

import java.util.List;
import java.util.Locale;

public class RoomInfoRecyclerViewAdapter extends
        RecyclerView.Adapter<RoomInfoRecyclerViewAdapter.ViewHolder> {

    private final List<Integer> mValues;
    private final Listeners mListener;
    private String date_from, date_to;

    public RoomInfoRecyclerViewAdapter(List<Integer> items,
                                       Listeners listener,
                                       String date_from, String date_to) {
        mValues = items;
        mListener = listener;
        this.date_from = date_from;
        this.date_to = date_to;
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
        holder.mIdView.setText(String.format(Locale.US, "Room %d", mValues.get(position)));

        Button btn_select = (Button) holder.mView.findViewById(R.id.btn_select_room);
        btn_select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRoomSelected(
                            mValues.get(holder.getAdapterPosition()), date_from, date_to);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public int mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.room_number);
        }
    }
}

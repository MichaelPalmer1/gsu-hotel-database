package com.kittymcfluffums.hotel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kittymcfluffums.hotel.R;
import com.kittymcfluffums.hotel.Room;
import com.kittymcfluffums.hotel.dialogs.ReservationRoomTypeDialog;

import java.util.List;

public class ReservationRoomTypeRecyclerViewAdapter extends
        RecyclerView.Adapter<ReservationRoomTypeRecyclerViewAdapter.ViewHolder> {

    private final List<Room> mValues;
    private final ReservationRoomTypeDialog.OnRoomTypeSelectedListener mListener;
    private String date_from, date_to;

    public ReservationRoomTypeRecyclerViewAdapter(List<Room> items,
                              ReservationRoomTypeDialog.OnRoomTypeSelectedListener listener,
                                                  String date_from, String date_to) {
        mValues = items;
        mListener = listener;
        this.date_from = date_from;
        this.date_to = date_to;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_reservation_room_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getRoomType());
        holder.mImageView.setImageResource(mValues.get(position).getRoomPicResource());
        holder.mContentView.setText(mValues.get(position).getRoomDesc());

        Button btn_select = (Button) holder.mView.findViewById(R.id.btn_select);
        btn_select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRoomTypeSelected(holder.mItem, date_from, date_to);
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
        public final ImageView mImageView;
        public final TextView mContentView;
        public Room mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.room_type);
            mImageView = (ImageView) view.findViewById(R.id.room_picture);
            mContentView = (TextView) view.findViewById(R.id.room_desc);
        }
    }
}

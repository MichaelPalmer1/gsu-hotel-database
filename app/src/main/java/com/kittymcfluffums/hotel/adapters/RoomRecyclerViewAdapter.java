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
import com.kittymcfluffums.hotel.fragments.RoomFragment;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Room} and makes a call to the
 * specified {@link RoomFragment.OnBookClickedListener}.
 */
public class RoomRecyclerViewAdapter extends
        RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder> {

    private final List<Room> mValues;
    private final RoomFragment.OnBookClickedListener mListener;

    public RoomRecyclerViewAdapter(List<Room> items,
                                   RoomFragment.OnBookClickedListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(holder.mItem.getRoomType());
        holder.mImageView.setImageResource(holder.mItem.getRoomPicResource());
        holder.mContentView.setText(holder.mItem.getRoomDesc());
        Button btn_book = (Button) holder.mView.findViewById(R.id.btn_book);
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBookClicked();
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

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

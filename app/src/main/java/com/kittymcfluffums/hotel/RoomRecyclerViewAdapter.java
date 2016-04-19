package com.kittymcfluffums.hotel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kittymcfluffums.hotel.fragments.RoomFragment;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Room} and makes a call to the
 * specified {@link RoomFragment.OnListFragmentInteractionListener}.
 */
public class RoomRecyclerViewAdapter extends
        RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder> {

    private final List<Room> mValues;
    private final RoomFragment.OnListFragmentInteractionListener mListener;

    public RoomRecyclerViewAdapter(List<Room> items,
                                   RoomFragment.OnListFragmentInteractionListener listener) {
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
        holder.mIdView.setText(mValues.get(position).getRoomType());
        holder.mImageView.setImageResource(mValues.get(position).getRoomPicResource());
        holder.mContentView.setText(mValues.get(position).getRoomDesc());
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
        Button learn_more = (Button) holder.mView.findViewById(R.id.btn_learn_more);
        learn_more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onListFragmentInteraction(holder.mItem);
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

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

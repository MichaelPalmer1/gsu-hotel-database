package com.kittymcfluffums.hotel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kittymcfluffums.hotel.fragments.GuestFragment;

import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Guest} and makes a call to the
 * specified {@link GuestFragment.OnListFragmentInteractionListener}.
 */
public class GuestRecyclerViewAdapter extends
        RecyclerView.Adapter<GuestRecyclerViewAdapter.ViewHolder> {

    private final List<Guest> mValues;
    private final GuestFragment.OnListFragmentInteractionListener mListener;

    public GuestRecyclerViewAdapter(List<Guest> items,
                                    GuestFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_guest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.format(Locale.US, "%s %s %s",
                mValues.get(position).getFirstName(),
                mValues.get(position).getMiddleName(),
                mValues.get(position).getLastName()
        ));
        holder.mPhoneView.setText(mValues.get(position).getPhoneNumber());
        holder.mEmailView.setText(mValues.get(position).getEmail());
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
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView, mPhoneView, mEmailView;
        public Guest mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.guest_name);
            mPhoneView = (TextView) view.findViewById(R.id.guest_phone);
            mEmailView = (TextView) view.findViewById(R.id.guest_email);
        }
    }
}

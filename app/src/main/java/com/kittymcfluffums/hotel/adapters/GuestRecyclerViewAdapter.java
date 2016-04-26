package com.kittymcfluffums.hotel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kittymcfluffums.hotel.Guest;
import com.kittymcfluffums.hotel.R;

import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Guest}
 */
public class GuestRecyclerViewAdapter extends
        RecyclerView.Adapter<GuestRecyclerViewAdapter.ViewHolder> {

    private final List<Guest> mValues;

    public GuestRecyclerViewAdapter(List<Guest> items) {
        mValues = items;
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

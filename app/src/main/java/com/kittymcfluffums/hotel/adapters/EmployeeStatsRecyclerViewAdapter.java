package com.kittymcfluffums.hotel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kittymcfluffums.hotel.EmployeeStat;
import com.kittymcfluffums.hotel.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EmployeeStat}.
 */
public class EmployeeStatsRecyclerViewAdapter extends
        RecyclerView.Adapter<EmployeeStatsRecyclerViewAdapter.ViewHolder> {

    private final List<EmployeeStat> mValues;

    public EmployeeStatsRecyclerViewAdapter(List<EmployeeStat> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_employee_stats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.empStat = mValues.get(position);
        holder.mMetric.setText(holder.empStat.getMetric());
        holder.mMetricValue.setText(String.valueOf(mValues.get(position).getMetric_value()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMetric;
        public final TextView mMetricValue;
        public EmployeeStat empStat;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mMetric = (TextView) view.findViewById(R.id.metric);
            mMetricValue = (TextView) view.findViewById(R.id.metric_value);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMetric.getText() + "'";
        }
    }
}

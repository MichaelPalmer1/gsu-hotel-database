package com.kittymcfluffums.hotel.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kittymcfluffums.hotel.Employee;
import com.kittymcfluffums.hotel.R;

import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Employee}
 */
public class EmployeeRecyclerViewAdapter extends
        RecyclerView.Adapter<EmployeeRecyclerViewAdapter.ViewHolder> {

    private final List<Employee> mValues;

    public EmployeeRecyclerViewAdapter(List<Employee> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.format(Locale.US, "%s %s (%s)",
                mValues.get(position).getFirstName(),
                mValues.get(position).getLastName(),
                mValues.get(position).getGender()
        ));
        holder.mPositionView.setText(mValues.get(position).getPosition());
        holder.mDemographicView.setText(String.format(Locale.US, "Demographic: %s",
                mValues.get(position).getDemographic()));
        holder.mDateEmployedView.setText(String.format(Locale.US, "Employee since %s",
                mValues.get(position).getDateEmployed()));
        holder.mSalaryView.setText(String.format(Locale.US, "$%.2f",
                mValues.get(position).getSalary()));
        holder.mAgeView.setText(String.format(Locale.US, "Age: %d",
                mValues.get(position).getAge()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView, mPositionView, mDemographicView, mDateEmployedView,
                mSalaryView, mAgeView;
        public Employee mItem;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.employee_name);
            mPositionView = (TextView) view.findViewById(R.id.employee_position);
            mDemographicView = (TextView) view.findViewById(R.id.employee_demographic);
            mDateEmployedView = (TextView) view.findViewById(R.id.employee_date_employed);
            mSalaryView = (TextView) view.findViewById(R.id.employee_salary);
            mAgeView = (TextView) view.findViewById(R.id.employee_age);
        }
    }
}

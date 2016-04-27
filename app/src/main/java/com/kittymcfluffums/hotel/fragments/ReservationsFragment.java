package com.kittymcfluffums.hotel.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.kittymcfluffums.hotel.Listeners;
import com.kittymcfluffums.hotel.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * Fragment for the reservations screen.
 */
public class ReservationsFragment extends Fragment
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Listeners mListener;
    private EditText date_view, date_from, date_to;
    private Spinner guest_count;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);

        // Date pickers
        date_from = (EditText) view.findViewById(R.id.date_from);
        date_to = (EditText) view.findViewById(R.id.date_to);
        date_from.setOnClickListener(this);
        date_to.setOnClickListener(this);

        // Guest count
        guest_count = (Spinner) view.findViewById(R.id.guest_count);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.guest_count, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        guest_count.setAdapter(adapter);

        // Setup the on click listener for the search button
        Button btn_search = (Button) view.findViewById(R.id.btn_reservation_search);
        btn_search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    // Perform validation
                    boolean has_error = false;
                    if (date_from.getText().toString().equals("")) {
                        date_from.setError("Date From is required");
                        has_error = true;
                    } else {
                        date_from.setError(null);
                    }

                    if (date_to.getText().toString().equals("")) {
                        date_to.setError("Date To is required");
                        has_error = true;
                    } else {
                        date_to.setError(null);
                    }

                    if (has_error) {
                        return;
                    }

                    // Trigger the listener
                    mListener.onReservationSearch(
                            date_from.getText().toString(),date_to.getText().toString(),
                            Integer.parseInt(guest_count.getSelectedItem().toString()));
                }
            }
        });

        // Set the on click listener for the lookup button
        Button btn_lookup = (Button) view.findViewById(R.id.btn_reservation_lookup);
        final EditText reservation_id = (EditText) view.findViewById(R.id.reservation_id);
        btn_lookup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    // Perform validation
                    if (reservation_id.getText().toString().equals("")) {
                        reservation_id.setError("Reservation ID is required");
                        return;
                    } else {
                        reservation_id.setError(null);
                    }

                    // Trigger the listener
                    mListener.onReservationLookup(Integer.parseInt(
                            reservation_id.getText().toString()));
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listeners) {
            mListener = (Listeners) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Listeners");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Handle on click events
     * @param v View
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_from:
                // Open the date picker for the start date
                createDatePicker(v);
                break;
            case R.id.date_to:
                // Open the date picker for the end date
                createDatePicker(v);
                break;
        }
    }

    private void createDatePicker(View v) {
        // Save the date view that the date picker should use
        date_view = (EditText) v;

        // Create the date picker
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Set the minimum date as today
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Clear the dialog title
        datePickerDialog.setTitle("");

        // Show the dialog
        datePickerDialog.show();
    }

    /**
     * Save the date selected from the date picker
     * @param view DatePicker instance
     * @param year Year
     * @param monthOfYear Month
     * @param dayOfMonth Day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // Set the corresponding EditText view with the selected date
        Calendar date_selected = Calendar.getInstance();
        date_selected.set(year, monthOfYear, dayOfMonth);
        date_view.setText(dateFormat.format(date_selected.getTime()));
    }
}

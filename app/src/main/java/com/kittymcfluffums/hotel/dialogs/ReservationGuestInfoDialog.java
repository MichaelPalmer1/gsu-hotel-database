package com.kittymcfluffums.hotel.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kittymcfluffums.hotel.Listeners;
import com.kittymcfluffums.hotel.R;

/**
 * Fragment for the rooms screen.
 */
public class ReservationGuestInfoDialog extends DialogFragment {

    private EditText first_name, middle_name, last_name, email, phone;
    private int room_number;
    private String date_from, date_to;
    protected Listeners mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.dialog_reservation_guest, container, false);

        super.getDialog().setTitle("Enter your information");

        room_number = getArguments().getInt("room_number");
        date_from = getArguments().getString("date_from");
        date_to = getArguments().getString("date_to");

        first_name = (EditText) rootView.findViewById(R.id.reservation_guest_first_name);
        middle_name = (EditText) rootView.findViewById(R.id.reservation_guest_middle_name);
        last_name = (EditText) rootView.findViewById(R.id.reservation_guest_last_name);
        email = (EditText) rootView.findViewById(R.id.reservation_guest_email);
        phone = (EditText) rootView.findViewById(R.id.reservation_guest_phone);

        Button btn_submit = (Button) rootView.findViewById(R.id.btn_reservation_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldsInvalid()) {
                    return;
                }
                mListener.onGuestInfoSubmitted(room_number, date_from, date_to,
                        first_name.getText().toString(), middle_name.getText().toString(),
                        last_name.getText().toString(), email.getText().toString(),
                        phone.getText().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listeners) {
            mListener = (Listeners) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listeners");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Perform field validation
     * @return boolean
     */
    private boolean fieldsInvalid() {
        boolean has_error = false;
        if (first_name.getText().toString().equals("")) {
            first_name.setError("First Name is required");
            has_error = true;
        } else {
            first_name.setError(null);
        }

        if (middle_name.getText().toString().equals("")) {
            middle_name.setError("Middle Name is required");
            has_error = true;
        } else {
            middle_name.setError(null);
        }

        if (last_name.getText().toString().equals("")) {
            last_name.setError("Last Name is required");
            has_error = true;
        } else {
            last_name.setError(null);
        }

        if (email.getText().toString().equals("")) {
            email.setError("Email is required");
            has_error = true;
        } else {
            email.setError(null);
        }

        if (phone.getText().toString().equals("")) {
            phone.setError("Phone is required");
            has_error = true;
        } else {
            phone.setError(null);
        }

        return has_error;
    }
}

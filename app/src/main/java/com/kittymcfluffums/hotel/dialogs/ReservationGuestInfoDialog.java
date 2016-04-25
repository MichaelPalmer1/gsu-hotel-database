package com.kittymcfluffums.hotel.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.Listeners;
import com.kittymcfluffums.hotel.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

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
                mListener.onGuestInfoSubmitted(room_number, date_from, date_to, first_name,
                        middle_name, last_name, email, phone);
            }
        });

        return rootView;
    }
}

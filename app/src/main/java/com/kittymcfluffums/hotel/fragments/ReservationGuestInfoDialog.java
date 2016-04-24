package com.kittymcfluffums.hotel.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.R;

import java.util.Locale;

/**
 * Fragment for the rooms screen.
 */
public class ReservationGuestInfoDialog extends DialogFragment implements View.OnClickListener {

    private EditText first_name, middle_name, last_name, email, phone;
    private int room_number;
    private String date_from, date_to;
//    private APIReserve api;

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
        btn_submit.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_reservation_submit) {
            String sql;
            APIReserve1 apiReserve1 = new APIReserve1();

            // Insert into room usage
            sql = String.format(Locale.US, "{\"query\": \"" +
                    "INSERT INTO `Room_Usage` VALUES(%d, 'main', '%s', '%s');" +
                    "\"}", room_number, date_from, date_to);
            Log.d("QUERY", sql);
            apiReserve1.execute(Constants.API_QUERY_URL, sql);

            // Insert into reservation
            APIReserve2 apiReserve2 = new APIReserve2();
            sql = String.format(Locale.US, "{\"query\": \"" +
                    "INSERT INTO `Reservation`(`reservation_start_date`,`reservation_end_date`," +
                    "`date_from`,`room_number`,`hotel_id`) VALUES('%s', '%s', '%s', %d, 'main');" +
                    "\"}",
                    date_from, date_to, date_from, room_number);
            Log.d("QUERY", sql);
            apiReserve2.execute(Constants.API_QUERY_URL, sql);

            // Insert into guest
            APIReserve3 apiReserve3 = new APIReserve3();
            sql = String.format(Locale.US, "{\"query\": \"" +
                    "INSERT INTO `Guest`(`hotel_id`,`first_name`,`middle_name`,`last_name`," +
                    "`email`,`phone_number`) VALUES('main', '%s', '%s', '%s', '%s', '%s');" +
                    "\"}",
                    first_name.getText().toString(),
                    middle_name.getText().toString(),
                    last_name.getText().toString(),
                    email.getText().toString(),
                    phone.getText().toString());
            Log.d("QUERY", sql);
            apiReserve3.execute(Constants.API_QUERY_URL, sql);

            // Get last insert
            APIReserve4 apiReserve4 = new APIReserve4();
            sql = "{\"query\": \"" +
                    "INSERT INTO `Reservation_Guest` VALUES((SELECT MAX(reservation_id) " +
                    "FROM Reservation), (SELECT MAX(guest_id) FROM Guest), 'main');" +
                    "\"}";
            Log.d("QUERY", sql);
            apiReserve4.execute(Constants.API_QUERY_URL, sql);

            this.dismiss();
        }
    }

    class APIReserve1 extends API.Post {
        protected void processData(String json) {
            // Don't do anything
            Log.d("RESULT", json);
        }
    }
    class APIReserve2 extends APIReserve1 {}
    class APIReserve3 extends APIReserve1 {}
    class APIReserve4 extends APIReserve1 {
        @Override
        protected void processData(String json) {

        }
    }
}

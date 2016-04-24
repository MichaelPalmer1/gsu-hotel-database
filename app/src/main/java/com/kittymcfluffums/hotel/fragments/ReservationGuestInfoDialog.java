package com.kittymcfluffums.hotel.fragments;

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
import com.kittymcfluffums.hotel.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

/**
 * Fragment for the rooms screen.
 */
public class ReservationGuestInfoDialog extends DialogFragment implements View.OnClickListener {

    private EditText first_name, middle_name, last_name, email, phone;
    private int room_number;
    private String date_from, date_to;

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
            sql = API.buildQuery(String.format(Locale.US,
                    "INSERT INTO `Room_Usage` VALUES(%d, %d, '%s', '%s');",
                    Constants.HOTEL_ID, room_number, date_from, date_to));
            Log.d("QUERY", sql);
            apiReserve1.execute(Constants.API_QUERY_URL, sql);

            // Insert into reservation
            APIReserve2 apiReserve2 = new APIReserve2();
            sql = API.buildQuery(String.format(Locale.US,
                    "INSERT INTO `Reservation` VALUES(NULL,%d,%d,'%s','%s','%s',0);",
                    Constants.HOTEL_ID, room_number, date_from, date_from, date_to));
            Log.d("QUERY", sql);
            apiReserve2.execute(Constants.API_QUERY_URL, sql);

            // Insert into guest
            APIReserve3 apiReserve3 = new APIReserve3();
            sql = API.buildQuery(String.format(Locale.US,
                    "INSERT INTO `Guest` VALUES(%d,NULL,'%s','%s','%s','%s','%s');",
                    Constants.HOTEL_ID, first_name.getText().toString(),
                    middle_name.getText().toString(), last_name.getText().toString(),
                    email.getText().toString(), phone.getText().toString()));
            Log.d("QUERY", sql);
            apiReserve3.execute(Constants.API_QUERY_URL, sql);

            // Get last insert
            APIReserve4 apiReserve4 = new APIReserve4();
            sql = API.buildQuery(String.format(Locale.US,
                    "INSERT INTO `Reservation_Guest` " +
                    "VALUES(%d, (SELECT MAX(reservation_id) FROM Reservation), " +
                    "(SELECT MAX(guest_id) FROM Guest));", Constants.HOTEL_ID));
            Log.d("QUERY", sql);
            apiReserve4.execute(Constants.API_QUERY_URL, sql);

            // Close the dialog
            this.dismiss();

            // Output the reservation id
            APIReserve5 apiReserve5 = new APIReserve5();
            sql = API.buildQuery("SELECT MAX(reservation_id) AS id FROM Reservation;");
            Log.d("QUERY", sql);
            apiReserve5.execute(Constants.API_QUERY_URL, sql);
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
    class APIReserve4 extends APIReserve1 {}
    class APIReserve5 extends APIReserve1 {
        @Override
        protected void processData(String json) {
            try {
                int reservation_id = new JSONArray(json).getJSONObject(0).getInt("id");
//                Toast.makeText(
//                        getActivity(),
//                        String.format(Locale.US, "Reservation #%d successful", reservation_id),
//                        Toast.LENGTH_LONG
//                ).show();
                Log.d("RESERVATION", String.valueOf(reservation_id));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

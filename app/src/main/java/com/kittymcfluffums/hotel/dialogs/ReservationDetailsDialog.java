package com.kittymcfluffums.hotel.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Payment dialog
 */
public class ReservationDetailsDialog extends DialogFragment implements View.OnClickListener {

    private TextView room_number, start_date, end_date, room_type, guest_name;
    private EditText total_cost;
    private int reservation_id;
    private ReservationDetailsDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.dialog_reservation_details, container, false);

        reservation_id = getArguments().getInt("reservation_id");
        super.getDialog().setTitle(String.format(Locale.US, "Reservation #%d Details",
                reservation_id));

        room_number = (TextView) rootView.findViewById(R.id.reservation_details_room_number);
        room_type = (TextView) rootView.findViewById(R.id.reservation_details_room_type);
        guest_name = (TextView) rootView.findViewById(R.id.reservation_details_guest_name);
        start_date = (TextView) rootView.findViewById(R.id.reservation_details_start_date);
        end_date = (TextView) rootView.findViewById(R.id.reservation_details_end_date);
        total_cost = (EditText) rootView.findViewById(R.id.reservation_details_total_cost);
        dialog = this;

        APIReservationDetails reservationDetails = new APIReservationDetails();
        String sql = API.buildQuery(String.format(Locale.US,
                "SELECT Reservation.room_number, Reservation.reservation_start_date, " +
                        "Reservation.reservation_end_date, Reservation.total_charge, " +
                        "Room_Types.description, Guest.first_name, Guest.middle_name, " +
                        "Guest.last_name FROM Reservation " +
                        "JOIN Rooms ON Rooms.room_number = Reservation.room_number " +
                        "JOIN Room_Types ON Room_Types.room_type_id = Rooms.room_type_id " +
                        "JOIN Reservation_Guest " +
                        "ON Reservation_Guest.reservation_id = Reservation.reservation_id " +
                        "JOIN Guest on Reservation_Guest.guest_id = Guest.guest_id " +
                        "WHERE Reservation.reservation_id = %d", reservation_id));
        reservationDetails.execute(Constants.API_QUERY_URL, sql);

        Button btn_update = (Button) rootView.findViewById(R.id.btn_reservation_update);
        Button btn_delete = (Button) rootView.findViewById(R.id.btn_reservation_delete);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        String sql;
        switch (v.getId()) {
            case R.id.btn_reservation_update:
                APIReservationUpdate reservationUpdate = new APIReservationUpdate();
                sql = API.buildQuery(String.format(Locale.US,
                        "UPDATE `Reservation` SET `total_charge` = %s WHERE `hotel_id` = %d AND " +
                                "`reservation_id` = %d;",
                        total_cost.getText().toString(), Constants.HOTEL_ID, reservation_id));
                reservationUpdate.execute(Constants.API_QUERY_URL, sql);
                Toast.makeText(getContext(), "Reservation updated", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_reservation_delete:
                APIReservationDeletePayment deletePayment = new APIReservationDeletePayment();
                sql = API.buildQuery(String.format(Locale.US,
                        "DELETE FROM `Payment` WHERE `reservation_id` = %d", reservation_id));
                deletePayment.execute(Constants.API_QUERY_URL, sql);

                APIReservationDeleteRoomUsage deleteRoomUsage = new APIReservationDeleteRoomUsage();
                sql = API.buildQuery(String.format(Locale.US,
                        "DELETE FROM `Room_Usage` WHERE `hotel_id` = %d " +
                                "AND `room_number` = %s AND `date_from` = '%s';",
                        Constants.HOTEL_ID, room_number.getText(), start_date.getText()));
                deleteRoomUsage.execute(Constants.API_QUERY_URL, sql);

                APIReservationDeleteReservation deleteReservation =
                        new APIReservationDeleteReservation();
                sql = API.buildQuery(String.format(Locale.US,
                        "DELETE FROM `Reservation` WHERE `hotel_id` = %d AND `reservation_id` = %d",
                        Constants.HOTEL_ID, reservation_id));
                deleteReservation.execute(Constants.API_QUERY_URL, sql);

                this.dismiss();

                Toast.makeText(getContext(), "Reservation deleted", Toast.LENGTH_LONG).show();
                break;
        }
    }

    class APIReservationDetails extends API.Post {
        protected void processData(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                if (jsonArray.length() == 1) {
                    JSONObject data = jsonArray.getJSONObject(0);
                    room_number.setText(String.valueOf(data.getInt("room_number")));
                    room_type.setText(data.getString("description"));
                    guest_name.setText(String.format(Locale.US, "%s %s %s",
                            data.getString("first_name"), data.getString("middle_name"),
                            data.getString("last_name")));
                    start_date.setText(data.getString("reservation_start_date"));
                    end_date.setText(data.getString("reservation_end_date"));
                    total_cost.setText(String.format(Locale.US,
                            "%.2f", data.getDouble("total_charge")));
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Invalid reservation id",
                            Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class APILogResult extends API.Post {
        protected void processData(String json) {
            Log.d("RESULT", json);
        }
    }

    class APIReservationUpdate extends APILogResult {}

    class APIReservationDeletePayment extends APILogResult {}

    class APIReservationDeleteRoomUsage extends APILogResult {}

    class APIReservationDeleteReservation extends APILogResult {}

}

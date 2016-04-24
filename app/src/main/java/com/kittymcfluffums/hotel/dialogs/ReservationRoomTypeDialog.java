package com.kittymcfluffums.hotel.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.R;
import com.kittymcfluffums.hotel.adapters.ReservationRoomTypeRecyclerViewAdapter;
import com.kittymcfluffums.hotel.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Fragment for the rooms screen.
 */
public class ReservationRoomTypeDialog extends DialogFragment {
    private static ArrayList<Room> rooms = new ArrayList<>();
    private RecyclerView recyclerView;
    protected OnRoomTypeSelectedListener mListener;
    private String date_from, date_to;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.dialog_reservation_room_type_list, container, false);

        date_from = getArguments().getString("date_from");
        date_to = getArguments().getString("date_to");
        int guests = getArguments().getInt("guests");
        super.getDialog().setTitle("Select a room type");

        if (rootView instanceof RecyclerView) {
            Context context = rootView.getContext();
            recyclerView = (RecyclerView) rootView;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        String sql = API.buildQuery(String.format(Locale.US,
                "SELECT DISTINCT `Room_Types`.* FROM `Rooms` " +
                "JOIN `Room_Types` ON `Room_Types`.`room_type_id` = `Rooms`.`room_type_id` " +
                "WHERE room_number NOT IN (SELECT `room_number` FROM `Room_Usage` " +
                "WHERE `date_to` > '%s' AND `date_from` < '%s') " +
                "AND `max_guests` >= %d;", date_from, date_to, guests));

        Log.d("QUERY", sql);

        GetRoomTypes api = new GetRoomTypes();
        api.execute(Constants.API_QUERY_URL, sql);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRoomTypeSelectedListener) {
            mListener = (OnRoomTypeSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRoomTypeSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    class GetRoomTypes extends API.Post {
        protected void processData(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                rooms.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);
                    rooms.add(new Room(R.drawable.hotel,
                            row.getString("description"),
                            String.format(Locale.US, "$%.2f per night",
                                    row.getDouble("nightly_rate")),
                            row.getInt("room_type_id")
                    ));
                }
                recyclerView.setAdapter(
                        new ReservationRoomTypeRecyclerViewAdapter(rooms, mListener,
                                date_from, date_to));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnRoomTypeSelectedListener {
        void onRoomTypeSelected(Room room, String date_from, String date_to);
    }
}

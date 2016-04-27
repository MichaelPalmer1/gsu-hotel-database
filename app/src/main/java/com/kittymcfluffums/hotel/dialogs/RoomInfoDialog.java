package com.kittymcfluffums.hotel.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.Listeners;
import com.kittymcfluffums.hotel.R;
import com.kittymcfluffums.hotel.adapters.RoomInfoRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Fragment for the rooms screen.
 */
public class RoomInfoDialog extends DialogFragment {
    private static ArrayList<Integer> rooms = new ArrayList<>();
    private RecyclerView recyclerView;
    protected Listeners mListener;
    private String date_from, date_to;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_room_info_list, container, false);

        String room_type = getArguments().getString("room_type");
        date_from = getArguments().getString("date_from");
        date_to = getArguments().getString("date_to");
        int room_type_id = getArguments().getInt("room_type_id");
        super.getDialog().setTitle(room_type);

        if (rootView instanceof RecyclerView) {
            Context context = rootView.getContext();
            recyclerView = (RecyclerView) rootView;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        // Get all the available rooms for the specified room type during the specified date range
        String sql = API.buildQuery(String.format(Locale.US,
                "SELECT `room_number` FROM `Rooms` WHERE `room_type_id` = %d AND " +
                        "room_number NOT IN (SELECT `room_number` FROM `Room_Usage` " +
                        "WHERE `date_to` > '%s' AND `date_from` < '%s');",
                room_type_id, date_from, date_to));

        APIRoom api = new APIRoom();
        api.execute(Constants.API_QUERY_URL, sql);

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

    class APIRoom extends API.Post {
        protected void processData(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                rooms.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);
                    rooms.add(row.getInt("room_number"));
                }
                recyclerView.setAdapter(new RoomInfoRecyclerViewAdapter(rooms, mListener,
                        date_from, date_to));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

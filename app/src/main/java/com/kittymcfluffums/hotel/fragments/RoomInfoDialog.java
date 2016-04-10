package com.kittymcfluffums.hotel.fragments;

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
import com.kittymcfluffums.hotel.RoomInfoRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fragment for the rooms screen.
 */
public class RoomInfoDialog extends DialogFragment {
    private String room_type_id;
    private static ArrayList<HashMap<String, String>> rooms = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_room_info_list, container, false);

        String room_type = getArguments().getString("room_type");
        room_type_id = getArguments().getString("room_type_id");
        super.getDialog().setTitle(room_type);

        if (rootView instanceof RecyclerView) {
            Context context = rootView.getContext();
            recyclerView = (RecyclerView) rootView;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        APIRoom api = new APIRoom();
        api.execute(Constants.API_URL + "/Rooms/");

        return rootView;
    }

    class APIRoom extends API.Get {
        protected void processData(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                rooms.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);
                    if (row.getString("room_type_id").equals(room_type_id)) {
                        HashMap<String, String> hash = new HashMap<>();
                        hash.put("room_number", row.getString("room_number"));
                        hash.put("is_available", row.getString("is_available"));
                        rooms.add(hash);
                    }
                }
                recyclerView.setAdapter(new RoomInfoRecyclerViewAdapter(rooms));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

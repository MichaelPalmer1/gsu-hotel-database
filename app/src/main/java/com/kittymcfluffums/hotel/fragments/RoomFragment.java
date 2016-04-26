package com.kittymcfluffums.hotel.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.Listeners;
import com.kittymcfluffums.hotel.R;
import com.kittymcfluffums.hotel.Room;
import com.kittymcfluffums.hotel.adapters.RoomRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Fragment for the rooms screen.
 * Activities containing this fragment MUST implement the {@link Listeners}
 * interface.
 */
public class RoomFragment extends Fragment {

    public static final ArrayList<Room> ITEMS = new ArrayList<>();
    private RecyclerView recyclerView;

    protected Listeners mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        APIRoom api = new APIRoom();
        api.execute(Constants.API_URL + "/Room_Types/");

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

    class APIRoom extends API.Get {

        protected void processData(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                ITEMS.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    ITEMS.add(new Room(
                            R.drawable.hotel,
                            jsonArray.getJSONObject(i).getString("description"),
                            String.format(Locale.US, "$%.2f per night",
                                    jsonArray.getJSONObject(i).getDouble("nightly_rate")),
                            jsonArray.getJSONObject(i).getInt("room_type_id")
                    ));
                }

                recyclerView.setAdapter(new RoomRecyclerViewAdapter(ITEMS, mListener));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

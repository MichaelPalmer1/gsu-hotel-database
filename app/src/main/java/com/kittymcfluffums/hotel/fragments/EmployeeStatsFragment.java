package com.kittymcfluffums.hotel.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.EmployeeStat;
import com.kittymcfluffums.hotel.R;
import com.kittymcfluffums.hotel.Room;
import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.adapters.EmployeeStatsRecyclerViewAdapter;
import com.kittymcfluffums.hotel.adapters.RoomRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EmployeeStatsFragment extends Fragment {

    public static final ArrayList<EmployeeStat> ITEMS = new ArrayList<>();
    private RecyclerView recyclerView;

    public EmployeeStatsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_stats, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        HotelEmpStats api = new HotelEmpStats();
        api.execute(Constants.API_URL + "/Room_Types/");

        return view;
    }


    class HotelEmpStats extends API.Get {
        protected void processData(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    ITEMS.add(new EmployeeStat(
                            jsonArray.getJSONObject(i).getString("metric"),
                            jsonArray.getJSONObject(i).getInt("value")
                    ));
                }

                recyclerView.setAdapter(new EmployeeStatsRecyclerViewAdapter(ITEMS));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

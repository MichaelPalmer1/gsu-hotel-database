package com.kittymcfluffums.hotel.fragments;

import android.content.Context;
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
import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.adapters.EmployeeStatsRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeStatsFragment extends Fragment {

    public static final ArrayList<EmployeeStat> ITEMS = new ArrayList<>();
    public static final ArrayList<String> METRICS = new ArrayList<>(Arrays.asList(
            "Employee Count",
            "Max Salary"
    ));
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_stats_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        HotelEmpStats emp_count = new HotelEmpStats();
        String emp_count_query = API.buildQuery("SELECT COUNT(*) as 'value' from `Employee`;");
        emp_count.execute(Constants.API_QUERY_URL, emp_count_query);

        return view;
    }


    class HotelEmpStats extends API.Post {
        protected void processData(String json) {
            try {
                ITEMS.clear();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    ITEMS.add(new EmployeeStat(
                            METRICS.get(i),
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

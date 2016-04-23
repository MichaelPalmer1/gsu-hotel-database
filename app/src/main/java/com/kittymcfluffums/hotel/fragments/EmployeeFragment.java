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
import com.kittymcfluffums.hotel.Employee;
import com.kittymcfluffums.hotel.EmployeeRecyclerViewAdapter;
import com.kittymcfluffums.hotel.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Fragment for the employee screen.
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EmployeeFragment extends Fragment {

    public static final ArrayList<Employee> ITEMS = new ArrayList<>();
    private RecyclerView recyclerView;

    protected OnListFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        APIEmployee api = new APIEmployee();
        api.execute(Constants.API_URL + "/Employee/");

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * See the Android Training lesson Communicating with Other Fragments
     * http://developer.android.com/training/basics/fragments/communicating.html
     * for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Employee employee);
    }

    class APIEmployee extends API.Get {

        protected void processData(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                ITEMS.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    ITEMS.add(new Employee(
                            jsonArray.getJSONObject(i).getString("first_name"),
                            jsonArray.getJSONObject(i).getString("last_name"),
                            jsonArray.getJSONObject(i).getString("demographic"),
                            jsonArray.getJSONObject(i).getString("gender"),
                            jsonArray.getJSONObject(i).getString("position"),
                            jsonArray.getJSONObject(i).getString("date_employed"),
                            jsonArray.getJSONObject(i).getDouble("salary"),
                            jsonArray.getJSONObject(i).getInt("age")
                    ));
                }

                recyclerView.setAdapter(new EmployeeRecyclerViewAdapter(ITEMS, mListener));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

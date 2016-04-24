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
import com.kittymcfluffums.hotel.R;
import com.kittymcfluffums.hotel.Guest;
import com.kittymcfluffums.hotel.adapters.GuestRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Fragment for the guests screen.
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class GuestFragment extends Fragment {

    public static final ArrayList<Guest> ITEMS = new ArrayList<>();
    private RecyclerView recyclerView;

    protected OnListFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        APIGuest api = new APIGuest();
        api.execute(Constants.API_URL + "/Guest/");

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
        void onListFragmentInteraction(Guest guest);
    }

    class APIGuest extends API.Get {

        protected void processData(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                ITEMS.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    ITEMS.add(new Guest(
                            jsonArray.getJSONObject(i).getString("first_name"),
                            jsonArray.getJSONObject(i).getString("middle_name"),
                            jsonArray.getJSONObject(i).getString("last_name"),
                            jsonArray.getJSONObject(i).getString("phone_number"),
                            jsonArray.getJSONObject(i).getString("email")
                    ));
                }

                recyclerView.setAdapter(new GuestRecyclerViewAdapter(ITEMS, mListener));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.kittymcfluffums.hotel.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.R;


/**
 * Fragment for the destinations screen.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DestinationsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView destinations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destinations, container, false);
        destinations = (TextView) view.findViewById(R.id.destinations);
        HotelAPI api = new HotelAPI();
        api.execute(Constants.API_QUERY_URL, "{\"query\": \"show tables\"}");
        return view;
    }

    // TODO: Implement an action
    public void onSomeAction(Object object) {
        if (mListener != null) {
            mListener.onFragmentInteraction(object);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Object object);
    }

    class HotelAPI extends API.Post {
        protected void processData(String json) {
            Log.d("PostData", json);
            destinations.setText(json);
        }
    }
}

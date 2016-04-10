package com.kittymcfluffums.hotel.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Fragment for the home screen.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private TextView hotel_name, hotel_address, hotel_city_state_zip,
            hotel_email, hotel_website, hotel_phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        hotel_name = (TextView) view.findViewById(R.id.hotel_name);
        hotel_address = (TextView) view.findViewById(R.id.hotel_address);
        hotel_city_state_zip = (TextView) view.findViewById(R.id.hotel_city_state_zip);
        hotel_phone = (TextView) view.findViewById(R.id.hotel_phone);
        hotel_website = (TextView) view.findViewById(R.id.hotel_website);
        hotel_email = (TextView) view.findViewById(R.id.hotel_email);

        HotelAPI api = new HotelAPI();
        api.execute(Constants.API_URL + "/Hotel/");
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

    class HotelAPI extends API.Get {
        protected void processData(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (jsonArray.getJSONObject(i).getString("hotel_id").equals("main")) {
                        JSONObject hotel = jsonArray.getJSONObject(i);
                        hotel_name.setText(hotel.getString("name"));
                        hotel_address.setText(hotel.getString("address"));
                        hotel_city_state_zip.setText(getString(R.string.city_state_zip,
                                hotel.getString("city"),
                                hotel.getString("state"),
                                Integer.parseInt(hotel.getString("zipcode"))
                        ));
                        hotel_email.setText(hotel.getString("email"));
                        hotel_website.setText(hotel.getString("website"));
                        hotel_phone.setText(hotel.getString("phone"));
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

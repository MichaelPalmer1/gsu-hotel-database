package com.kittymcfluffums.hotel.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kittymcfluffums.hotel.R;
import com.kittymcfluffums.hotel.Room;
import com.kittymcfluffums.hotel.RoomRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Fragment for the rooms screen.
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RoomFragment extends Fragment {

    public static final ArrayList<Room> ITEMS = new ArrayList<>();

    static {
        // Create some sample data
        ITEMS.add(new Room(R.drawable.hotel, "Deluxe Room", "1 King Bed"));
        ITEMS.add(new Room(R.drawable.hotel, "Standard Room", "1 Double Bed"));
        ITEMS.add(new Room(R.drawable.hotel, "Executive Suite", "2 King Beds"));
        ITEMS.add(new Room(R.drawable.hotel, "Travel Suite", "1 futon"));
        ITEMS.add(new Room(R.drawable.hotel, "Honeymoon Suite", "A romantic getaway"));
        ITEMS.add(new Room(R.drawable.hotel, "Penthouse", "A beautiful view"));
    }

    private OnListFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new RoomRecyclerViewAdapter(ITEMS, mListener));
        }
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
        void onListFragmentInteraction(Object object);
    }
}

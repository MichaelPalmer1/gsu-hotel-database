package com.kittymcfluffums.hotel;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.kittymcfluffums.hotel.dialogs.*;
import com.kittymcfluffums.hotel.fragments.*;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, Listeners {

    private CollapsingToolbarLayout collapsingToolbar;
    private RoomInfoDialog roomInfoDialog;
    private ReservationRoomTypeDialog reservationRoomTypeDialog;
    private ReservationGuestInfoDialog reservationGuestInfoDialog;
    private int reservation_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize collapsing toolbar
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.app_name));

        // Create drawer layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        // Create navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        // Start home fragment
        setFragment(new HomeFragment(), R.string.app_name);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                setFragment(new HomeFragment(), R.string.app_name);
                break;
            case R.id.nav_rooms:
                setFragment(new RoomFragment(), R.string.nav_rooms);
                break;
            case R.id.nav_reservations:
                setFragment(new ReservationsFragment(), R.string.nav_reservations);
                break;
            case R.id.nav_guests:
                setFragment(new GuestFragment(), R.string.nav_guests);
                break;
            case R.id.nav_employees:
                setFragment(new EmployeeFragment(), R.string.nav_employees);
                break;
            case R.id.nav_employee_stats:
                setFragment(new EmployeeStatsFragment(), R.string.nav_employee_stats);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void setFragment(Fragment fragment, @StringRes int title) {
        // Set title
        collapsingToolbar.setTitle(getString(title));

        // Set fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .commit();
    }

    @Override
    public void onReservationSearch(String date_from, String date_to, int guests) {
        Bundle args = new Bundle();
        args.putString("date_from", date_from);
        args.putString("date_to", date_to);
        args.putInt("guests", guests);
        reservationRoomTypeDialog = new ReservationRoomTypeDialog();
        reservationRoomTypeDialog.setArguments(args);
        reservationRoomTypeDialog.show(getSupportFragmentManager(), "ReservationRoomTypeDialog");
    }

    @Override
    public void onGuestInfoSubmitted(int room_number, String date_from, String date_to,
                                     EditText first_name, EditText middle_name, EditText last_name,
                                     EditText email, EditText phone) {
        boolean has_error = false;
        if (first_name.getText().toString().equals("")) {
            first_name.setHint("First Name is required");
            first_name.setError("First Name is required");
            has_error = true;
        } else {
            first_name.setError(null);
        }

        if (middle_name.getText().toString().equals("")) {
            middle_name.setHint("Middle Name is required");
            middle_name.setError("Middle Name is required");
            has_error = true;
        } else {
            middle_name.setError(null);
        }

        if (last_name.getText().toString().equals("")) {
            last_name.setHint("Last Name is required");
            last_name.setError("Last Name is required");
            has_error = true;
        } else {
            last_name.setError(null);
        }

        if (email.getText().toString().equals("")) {
            email.setHint("Email is required");
            email.setError("Email is required");
            has_error = true;
        } else {
            email.setError(null);
        }

        if (phone.getText().toString().equals("")) {
            phone.setHint("Phone is required");
            phone.setError("Phone is required");
            has_error = true;
        } else {
            phone.setError(null);
        }

        if (has_error) {
            return;
        }

        String sql;
        APIReserve1 apiReserve1 = new APIReserve1();

        // Insert into room usage
        sql = API.buildQuery(String.format(Locale.US,
                "INSERT INTO `Room_Usage` VALUES(%d, %d, '%s', '%s');",
                Constants.HOTEL_ID, room_number, date_from, date_to));
        Log.d("QUERY", sql);
        apiReserve1.execute(Constants.API_QUERY_URL, sql);

        // Insert into reservation
        APIReserve2 apiReserve2 = new APIReserve2();
        sql = API.buildQuery(String.format(Locale.US,
                "INSERT INTO `Reservation` VALUES(NULL,%d,%d,'%s','%s','%s',0);",
                Constants.HOTEL_ID, room_number, date_from, date_from, date_to));
        Log.d("QUERY", sql);
        apiReserve2.execute(Constants.API_QUERY_URL, sql);

        // Insert into guest
        APIReserve3 apiReserve3 = new APIReserve3();
        sql = API.buildQuery(String.format(Locale.US,
                "INSERT INTO `Guest` VALUES(%d,NULL,'%s','%s','%s','%s','%s');",
                Constants.HOTEL_ID, first_name.getText().toString(),
                middle_name.getText().toString(), last_name.getText().toString(),
                email.getText().toString(), phone.getText().toString()));
        Log.d("QUERY", sql);
        apiReserve3.execute(Constants.API_QUERY_URL, sql);

        // Get last insert
        APIReserve4 apiReserve4 = new APIReserve4();
        sql = API.buildQuery(String.format(Locale.US,
                "INSERT INTO `Reservation_Guest` " +
                        "VALUES(%d, (SELECT MAX(reservation_id) FROM Reservation), " +
                        "(SELECT MAX(guest_id) FROM Guest));", Constants.HOTEL_ID));
        Log.d("QUERY", sql);
        apiReserve4.execute(Constants.API_QUERY_URL, sql);

        // Close the dialog
        reservationGuestInfoDialog.dismiss();

        // Output the reservation id
        APIReserve5 apiReserve5 = new APIReserve5();
        sql = API.buildQuery("SELECT MAX(reservation_id) AS id FROM Reservation;");
        Log.d("QUERY", sql);
        apiReserve5.execute(Constants.API_QUERY_URL, sql);
    }

    @Override
    public void onRoomTypeSelected(Room room, String date_from, String date_to) {
        reservationRoomTypeDialog.dismiss();
        Bundle args = new Bundle();
        args.putString("room_type", room.getRoomType());
        args.putInt("room_type_id", room.getRoomTypeId());
        args.putString("date_from", date_from);
        args.putString("date_to", date_to);
        roomInfoDialog = new RoomInfoDialog();
        roomInfoDialog.setArguments(args);
        roomInfoDialog.show(getSupportFragmentManager(), "RoomInfoDialog");
    }

    @Override
    public void onRoomSelected(int room_number, String date_from, String date_to) {
        roomInfoDialog.dismiss();
        Bundle args = new Bundle();
        args.putInt("room_number", room_number);
        args.putString("date_from", date_from);
        args.putString("date_to", date_to);
        reservationGuestInfoDialog = new ReservationGuestInfoDialog();
        reservationGuestInfoDialog.setArguments(args);
        reservationGuestInfoDialog.show(getSupportFragmentManager(), "ReservationGuestInfoDialog");
    }

    @Override
    public void onBookClicked() {
        setFragment(new ReservationsFragment(), R.string.nav_reservations);
    }

    class APIReserve1 extends API.Post {
        protected void processData(String json) {
            // Don't do anything
            Log.d("RESULT", json);
        }
    }
    class APIReserve2 extends APIReserve1 {}
    class APIReserve3 extends APIReserve1 {}
    class APIReserve4 extends APIReserve1 {}
    class APIReserve5 extends APIReserve1 {
        @Override
        protected void processData(String json) {
            try {
                reservation_id = new JSONArray(json).getJSONObject(0).getInt("id");
//                Toast.makeText(
//                        getActivity(),
//                        String.format(Locale.US, "Reservation #%d successful", reservation_id),
//                        Toast.LENGTH_LONG
//                ).show();
                Log.d("RESERVATION", String.valueOf(reservation_id));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

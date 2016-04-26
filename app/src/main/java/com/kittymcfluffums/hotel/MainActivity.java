package com.kittymcfluffums.hotel;

import android.content.Context;
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
import android.widget.Toast;

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
    private ReservationPaymentDialog paymentDialog;
    private int reservation_id;
    private Context context;

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

        context = this;

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
    public void onBookClicked() {
        setFragment(new ReservationsFragment(), R.string.nav_reservations);
    }

    @Override
    public void onReservationLookup(int reservation_id) {
        Bundle args = new Bundle();
        args.putInt("reservation_id", reservation_id);
        ReservationDetailsDialog detailsDialog = new ReservationDetailsDialog();
        detailsDialog.setArguments(args);
        detailsDialog.show(getSupportFragmentManager(), "ReservationDetailsDialog");
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
    public void onGuestInfoSubmitted(int room_number, String date_from, String date_to,
                                     String first_name, String middle_name, String last_name,
                                     String email, String phone) {
        String sql;
        APIRoomUsage apiRoomUsage = new APIRoomUsage();

        // Insert into room usage
        sql = API.buildQuery(String.format(Locale.US,
                "INSERT INTO `Room_Usage` VALUES(%d, %d, '%s', '%s');",
                Constants.HOTEL_ID, room_number, date_from, date_to));
        Log.d("QUERY", sql);
        apiRoomUsage.execute(Constants.API_QUERY_URL, sql);

        // Insert into reservation
        APIReservation apiReservation = new APIReservation();
        sql = API.buildQuery(String.format(Locale.US,
                "INSERT INTO `Reservation` VALUES(NULL,%d,%d,'%s','%s','%s',0);",
                Constants.HOTEL_ID, room_number, date_from, date_from, date_to));
        Log.d("QUERY", sql);
        apiReservation.execute(Constants.API_QUERY_URL, sql);

        // Insert into guest
        APIGuest apiGuest = new APIGuest();
        sql = API.buildQuery(String.format(Locale.US,
                "INSERT INTO `Guest` VALUES(%d,NULL,'%s','%s','%s','%s','%s');",
                Constants.HOTEL_ID, first_name, middle_name, last_name, email, phone));
        Log.d("QUERY", sql);
        apiGuest.execute(Constants.API_QUERY_URL, sql);

        // Get last insert
        APIReservationGuest apiReservationGuest = new APIReservationGuest();
        sql = API.buildQuery(String.format(Locale.US,
                "INSERT INTO `Reservation_Guest` " +
                        "VALUES(%d, (SELECT MAX(reservation_id) FROM Reservation), " +
                        "(SELECT MAX(guest_id) FROM Guest));", Constants.HOTEL_ID));
        Log.d("QUERY", sql);
        apiReservationGuest.execute(Constants.API_QUERY_URL, sql);

        // Output the reservation id
        APIReservationID apiReservationID = new APIReservationID();
        sql = API.buildQuery("SELECT MAX(reservation_id) AS id FROM Reservation;");
        Log.d("QUERY", sql);
        apiReservationID.execute(Constants.API_QUERY_URL, sql);
    }

    @Override
    public void onPaymentSubmitted(String card_type, String cardholder, String address, String city,
                                   String state, String zip, String card_number, String exp_date,
                                   String cvv) {
        String sql;
        APIPayment apiPayment = new APIPayment();

        // Insert into payment
        sql = API.buildQuery(String.format(Locale.US,
                "INSERT INTO `Payment` VALUES(NULL,%d,%s,'%s',%s,'%s','%s','%s',%s,'%s','%s');",
                reservation_id, card_number, exp_date, cvv, address,
                city, state, zip, card_type, cardholder));
        Log.d("QUERY", sql);
        apiPayment.execute(Constants.API_QUERY_URL, sql);
    }

    class APILogResult extends API.Post {
        protected void processData(String json) {
            Log.d("RESULT", json);
        }
    }
    class APIRoomUsage extends APILogResult {}
    class APIReservation extends APILogResult {}
    class APIGuest extends APILogResult {}
    class APIReservationGuest extends APILogResult {}
    class APIReservationID extends APILogResult {
        @Override
        protected void processData(String json) {
            try {
                reservation_id = new JSONArray(json).getJSONObject(0).getInt("id");
                reservationGuestInfoDialog.dismiss();
                Bundle args = new Bundle();
                args.putInt("reservation_id", reservation_id);
                paymentDialog = new ReservationPaymentDialog();
                paymentDialog.setArguments(args);
                paymentDialog.show(getSupportFragmentManager(), "PaymentDialog");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class APIPayment extends API.Post {
        protected void processData(String json) {
            Log.d("RESULT", json);
            paymentDialog.dismiss();
            onReservationLookup(reservation_id);
        }
    }
}

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

    /**
     * Create the activity
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view
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

        // Save the context
        context = this;

        // Start home fragment
        setFragment(new HomeFragment(), R.string.app_name);
    }

    /**
     * Handle back button presses
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Handle navigation item selections
     * @param item Menu item that was selected
     * @return boolean
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Switch based on the item selected
        switch (id) {
            case R.id.nav_home:
                // Home fragment
                setFragment(new HomeFragment(), R.string.app_name);
                break;
            case R.id.nav_rooms:
                // Room fragment
                setFragment(new RoomFragment(), R.string.nav_rooms);
                break;
            case R.id.nav_reservations:
                // Reservations fragment
                setFragment(new ReservationsFragment(), R.string.nav_reservations);
                break;
            case R.id.nav_guests:
                // Guest fragment
                setFragment(new GuestFragment(), R.string.nav_guests);
                break;
            case R.id.nav_employees:
                // Employee fragment
                setFragment(new EmployeeFragment(), R.string.nav_employees);
                break;
            case R.id.nav_employee_stats:
                // Employee stats fragment
                setFragment(new EmployeeStatsFragment(), R.string.nav_employee_stats);
                break;
        }

        // Close the drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    /**
     * Set the fragment
     * @param fragment Fragment to change to
     * @param title Title to use
     */
    private void setFragment(Fragment fragment, @StringRes int title) {
        // Set title
        collapsingToolbar.setTitle(getString(title));

        // Set fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .commit();
    }

    /**
     * Open reservations fragment when book button is clicked
     */
    @Override
    public void onBookClicked() {
        setFragment(new ReservationsFragment(), R.string.nav_reservations);
    }

    /**
     * Take action when reservation is looked up
     * @param reservation_id Reservation id
     */
    @Override
    public void onReservationLookup(int reservation_id) {
        this.reservation_id = reservation_id;

        // Query the database for the specified reservation id
        APILookupReservation lookupReservation = new APILookupReservation();
        String sql = API.buildQuery(String.format(Locale.US,
                "SELECT reservation_id FROM Reservation WHERE reservation_id = %d",
                reservation_id));
        lookupReservation.execute(Constants.API_QUERY_URL, sql);
    }

    /**
     * Take action when a reservation search is submitted
     * @param date_from Start date
     * @param date_to End date
     * @param guests Number of guests
     */
    @Override
    public void onReservationSearch(String date_from, String date_to, int guests) {
        // Set arguments
        Bundle args = new Bundle();
        args.putString("date_from", date_from);
        args.putString("date_to", date_to);
        args.putInt("guests", guests);

        // Open the room type dialog
        reservationRoomTypeDialog = new ReservationRoomTypeDialog();
        reservationRoomTypeDialog.setArguments(args);
        reservationRoomTypeDialog.show(getSupportFragmentManager(), "ReservationRoomTypeDialog");
    }

    /**
     * Open the room info dialog
     * @param room Room type
     * @param date_from Start date
     * @param date_to End date
     */
    @Override
    public void onRoomTypeSelected(Room room, String date_from, String date_to) {
        // Close the room type dialog
        reservationRoomTypeDialog.dismiss();

        // Set arguments
        Bundle args = new Bundle();
        args.putString("room_type", room.getRoomType());
        args.putInt("room_type_id", room.getRoomTypeId());
        args.putString("date_from", date_from);
        args.putString("date_to", date_to);

        // Open the room info dialog
        roomInfoDialog = new RoomInfoDialog();
        roomInfoDialog.setArguments(args);
        roomInfoDialog.show(getSupportFragmentManager(), "RoomInfoDialog");
    }

    /**
     * Triggered when room is selected
     * @param room_number Room number
     * @param date_from Start date
     * @param date_to End date
     */
    @Override
    public void onRoomSelected(int room_number, String date_from, String date_to) {
        // Close the room info dialog
        roomInfoDialog.dismiss();

        // Set arguments
        Bundle args = new Bundle();
        args.putInt("room_number", room_number);
        args.putString("date_from", date_from);
        args.putString("date_to", date_to);

        // Open the guest info dialog
        reservationGuestInfoDialog = new ReservationGuestInfoDialog();
        reservationGuestInfoDialog.setArguments(args);
        reservationGuestInfoDialog.show(getSupportFragmentManager(), "ReservationGuestInfoDialog");
    }

    /**
     * Triggered when guest info is submitted
     * @param room_number Room number
     * @param date_from Start date
     * @param date_to End date
     * @param first_name First name
     * @param middle_name Middle name
     * @param last_name Last name
     * @param email Email
     * @param phone Phone
     */
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

    /**
     * Triggered when payment dialog is submitted
     * @param card_type Card type
     * @param cardholder Cardholder name
     * @param address Billing address
     * @param city Billing city
     * @param state Billing state
     * @param zip Billing zipcode
     * @param card_number Card number
     * @param exp_date Expiration date
     * @param cvv CVV code
     */
    @Override
    public void onPaymentSubmitted(String card_type, String cardholder, String address, String city,
                                   String state, String zip, String card_number, String exp_date,
                                   String cvv) {
        String sql;
        APIPayment apiPayment = new APIPayment();

        // Insert into payment table
        sql = API.buildQuery(String.format(Locale.US,
                "INSERT INTO `Payment` VALUES(NULL,%d,%s,'%s',%s,'%s','%s','%s',%s,'%s','%s');",
                reservation_id, card_number, exp_date, cvv, address,
                city, state, zip, card_type, cardholder));
        Log.d("QUERY", sql);
        apiPayment.execute(Constants.API_QUERY_URL, sql);
    }

    /**
     * Log results of a query
     */
    class APILogResult extends API.Post {
        protected void processData(String json) {
            Log.d("RESULT", json);
        }
    }

    /**
     * Reservation lookup API object
     */
    class APILookupReservation extends API.Post {
        @Override
        protected void processData(String data) {
            try {
                JSONArray jsonArray = new JSONArray(data);
                if (jsonArray.length() == 0) {
                    Toast.makeText(context, "Invalid reservation id", Toast.LENGTH_LONG).show();
                } else {
                    Bundle args = new Bundle();
                    args.putInt("reservation_id", reservation_id);
                    ReservationDetailsDialog detailsDialog = new ReservationDetailsDialog();
                    detailsDialog.setArguments(args);
                    detailsDialog.show(getSupportFragmentManager(), "ReservationDetailsDialog");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Query API objects
     */
    class APIRoomUsage extends APILogResult {}
    class APIReservation extends APILogResult {}
    class APIGuest extends APILogResult {}
    class APIReservationGuest extends APILogResult {}

    /**
     * Reservation ID API object
     */
    class APIReservationID extends APILogResult {
        @Override
        protected void processData(String json) {
            try {
                reservation_id = new JSONArray(json).getJSONObject(0).getInt("id");

                // Close the guest info dialog
                reservationGuestInfoDialog.dismiss();

                // Open payment dialog
                paymentDialog = new ReservationPaymentDialog();
                paymentDialog.show(getSupportFragmentManager(), "PaymentDialog");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Payment completed, show confirmation dialog
     */
    class APIPayment extends API.Post {
        protected void processData(String json) {
            Log.d("RESULT", json);
            // Close payment dialog
            paymentDialog.dismiss();

            // Open confirmation dialog
            onReservationLookup(reservation_id);
        }
    }
}

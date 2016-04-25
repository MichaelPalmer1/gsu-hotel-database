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
import android.view.MenuItem;

import com.kittymcfluffums.hotel.dialogs.*;
import com.kittymcfluffums.hotel.fragments.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        RoomFragment.OnBookClickedListener,
        ReservationsFragment.OnReservationSearchListener,
        GuestFragment.OnListFragmentInteractionListener,
        EmployeeFragment.OnListFragmentInteractionListener,
        ReservationRoomTypeDialog.OnRoomTypeSelectedListener,
        RoomInfoDialog.OnRoomSelectedListener {

    private CollapsingToolbarLayout collapsingToolbar;
    private RoomInfoDialog roomInfoDialog;
    private ReservationRoomTypeDialog reservationRoomTypeDialog;

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
    public void onFragmentInteraction(Object object) {
        // TODO: Implement this
    }

//    @Override
//    public void onListFragmentInteraction(Room room) {
//        Bundle args = new Bundle();
//        args.putString("room_type", room.getRoomType());
//        args.putInt("room_type_id", room.getRoomTypeId());
//        RoomInfoDialog dialog = new RoomInfoDialog();
//        dialog.setArguments(args);
//        dialog.show(getSupportFragmentManager(), "RoomInfoDialog");
//    }

    @Override
    public void onListFragmentInteraction(Guest guest) {
        // TODO: Implement this
    }

    @Override
    public void onListFragmentInteraction(Employee employee) {
        // TODO: Implement this
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
        ReservationGuestInfoDialog reservationGuestInfoDialog = new ReservationGuestInfoDialog();
        reservationGuestInfoDialog.setArguments(args);
        reservationGuestInfoDialog.show(getSupportFragmentManager(), "ReservationGuestInfoDialog");
    }

    @Override
    public void onBookClicked() {
        setFragment(new ReservationsFragment(), R.string.nav_reservations);
    }
}

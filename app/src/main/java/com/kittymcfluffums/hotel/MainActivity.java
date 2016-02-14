package com.kittymcfluffums.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private ViewFlipper viewFlipper;
	private CollapsingToolbarLayout collapsingToolbar;
	final int NAV_HOME = 0, NAV_DEST = 1, NAV_ROOMS = 2, NAV_ROOM_INFO = 3, NAV_RESERVE = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		collapsingToolbar.setTitle(getString(R.string.app_name));

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		// @TODO Change these to fragments
		if (id == R.id.nav_home) {
			viewFlipper.setDisplayedChild(NAV_HOME);
			collapsingToolbar.setTitle(getString(R.string.app_name));
		} else if (id == R.id.nav_destinations) {
			viewFlipper.setDisplayedChild(NAV_DEST);
			collapsingToolbar.setTitle(getString(R.string.nav_destinations));
		} else if (id == R.id.nav_rooms) {
			viewFlipper.setDisplayedChild(NAV_ROOMS);
			collapsingToolbar.setTitle(getString(R.string.nav_rooms));
			setupRoomRecycler();
		} else if (id == R.id.nav_reservations) {
			viewFlipper.setDisplayedChild(NAV_RESERVE);
			collapsingToolbar.setTitle(getString(R.string.nav_reservations));
		} else if (id == R.id.nav_settings) {
			startActivity(new Intent(this, SettingsActivity.class));
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	private void setupRoomRecycler() {
		RecyclerView recyclerRooms = (RecyclerView) viewFlipper.findViewById(R.id.recyclerRooms);

		LinearLayoutManager llm = new LinearLayoutManager(viewFlipper.getContext());
		recyclerRooms.setLayoutManager(llm);

		ArrayList<Room> roomList = new ArrayList<>();

		roomList.add(new Room("Deluxe Room", "1 King Bed"));
		roomList.add(new Room("Standard Room", "1 Double Bed"));
		roomList.add(new Room("Executive Suite", "2 King Beds"));
		roomList.add(new Room("Best Available", "A futon"));
		roomList.add(new Room("Budget Room", "Chilled, concrete floor"));
		roomList.add(new Room("Outside", "Doghouse"));
		roomList.add(new Room("Penthouse", "Everything your heart desires"));

		RVAdapter adapter = new RVAdapter(roomList);
		recyclerRooms.setAdapter(adapter);
	}

	class RVAdapter extends RecyclerView.Adapter<RVAdapter.RoomViewHolder> {

		class RoomViewHolder extends RecyclerView.ViewHolder {
			CardView cardView;
			TextView roomType;
			TextView roomDesc;

			public RoomViewHolder(View view) {
				super(view);
				cardView = (CardView) view.findViewById(R.id.card_room);
				roomType = (TextView) view.findViewById(R.id.room_type);
				roomDesc = (TextView) view.findViewById(R.id.room_desc);
			}
		}

		List<Room> rooms;

		RVAdapter(List<Room> rooms) {
			this.rooms = rooms;
		}

		@Override
		public int getItemCount() {
			return rooms.size();
		}

		@Override
		public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext())
									  .inflate(R.layout.cardview_room, parent, false);
			return new RoomViewHolder(view);
		}

		@Override
		public void onBindViewHolder(RoomViewHolder holder, int position) {
			holder.roomType.setText(rooms.get(position).getRoomType());
			holder.roomDesc.setText(rooms.get(position).getRoomDesc());
		}

		@Override
		public void onAttachedToRecyclerView(RecyclerView recyclerView) {
			super.onAttachedToRecyclerView(recyclerView);
		}
	}
}

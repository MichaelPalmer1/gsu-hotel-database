package com.kittymcfluffums.hotel;

import android.widget.EditText;

public interface Listeners {
    void onGuestInfoSubmitted(int room_number, String date_from, String date_to,
                              EditText first_name, EditText middle_name, EditText last_name,
                              EditText email, EditText phone);
    void onRoomTypeSelected(Room room, String date_from, String date_to);
    void onRoomSelected(int room_number, String date_from, String date_to);
    void onBookClicked();
    void onReservationSearch(String date_from, String date_to, int guests);
}

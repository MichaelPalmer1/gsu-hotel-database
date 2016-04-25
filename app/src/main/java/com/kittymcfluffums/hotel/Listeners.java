package com.kittymcfluffums.hotel;

public interface Listeners {
    void onBookClicked();
    void onReservationLookup(int reservation_id);
    void onReservationSearch(String date_from, String date_to, int guests);
    void onRoomTypeSelected(Room room, String date_from, String date_to);
    void onRoomSelected(int room_number, String date_from, String date_to);
    void onGuestInfoSubmitted(int room_number, String date_from, String date_to,
                              String first_name, String middle_name, String last_name,
                              String email, String phone);
    void onPaymentSubmitted(String card_type, String cardholder, String address,
                            String city, String state, String zip, String card_number,
                            String exp_date, String cvv);
}

package com.kittymcfluffums.hotel;

/**
 * Listeners for the various dialogs
 */
public interface Listeners {
    /**
     * Booked button is clicked
     */
    void onBookClicked();

    /**
     * Reservation lookup
     * @param reservation_id Reservation id
     */
    void onReservationLookup(int reservation_id);

    /**
     * Reservation search
     * @param date_from Start date
     * @param date_to End date
     * @param guests Max guests
     */
    void onReservationSearch(String date_from, String date_to, int guests);

    /**
     * Room type selected
     * @param room Room type
     * @param date_from Start date
     * @param date_to End date
     */
    void onRoomTypeSelected(Room room, String date_from, String date_to);

    /**
     * Room selected
     * @param room_number Room number
     * @param date_from Start date
     * @param date_to End date
     */
    void onRoomSelected(int room_number, String date_from, String date_to);

    /**
     * Guest info submitted
     * @param room_number Room number
     * @param date_from Start date
     * @param date_to End date
     * @param first_name First name
     * @param middle_name Middle name
     * @param last_name Last name
     * @param email Email
     * @param phone Phone
     */
    void onGuestInfoSubmitted(int room_number, String date_from, String date_to,
                              String first_name, String middle_name, String last_name,
                              String email, String phone);

    /**
     * Payment submitted
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
    void onPaymentSubmitted(String card_type, String cardholder, String address,
                            String city, String state, String zip, String card_number,
                            String exp_date, String cvv);
}

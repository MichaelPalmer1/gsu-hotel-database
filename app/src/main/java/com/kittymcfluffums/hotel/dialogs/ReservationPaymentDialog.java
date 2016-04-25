package com.kittymcfluffums.hotel.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kittymcfluffums.hotel.API;
import com.kittymcfluffums.hotel.Constants;
import com.kittymcfluffums.hotel.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

/**
 * Payment dialog
 */
public class ReservationPaymentDialog extends DialogFragment implements View.OnClickListener {

    private EditText card_type, cardholder, address, city, state, zip, card_number, expiration, cvv;
    private int reservation_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.dialog_reservation_payment, container, false);

        super.getDialog().setTitle("Enter payment information");

        reservation_id = getArguments().getInt("reservation_id");

        card_type = (EditText) rootView.findViewById(R.id.reservation_payment_card_type);
        cardholder = (EditText) rootView.findViewById(R.id.reservation_payment_cardholder_name);
        address = (EditText) rootView.findViewById(R.id.reservation_payment_billing_address);
        city = (EditText) rootView.findViewById(R.id.reservation_payment_billing_city);
        state = (EditText) rootView.findViewById(R.id.reservation_payment_billing_state);
        zip = (EditText) rootView.findViewById(R.id.reservation_payment_billing_zipcode);
        card_number = (EditText) rootView.findViewById(R.id.reservation_payment_card_number);
        expiration = (EditText) rootView.findViewById(R.id.reservation_payment_card_expiration);
        cvv = (EditText) rootView.findViewById(R.id.reservation_payment_card_cvv);

        Button btn_submit = (Button) rootView.findViewById(R.id.btn_payment_submit);
        btn_submit.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_payment_submit) {
            boolean has_error = false;
            if (card_type.getText().toString().equals("")) {
                card_type.setHint("Card Type is required");
                card_type.setError("Card Type is required");
                has_error = true;
            } else {
                card_type.setError(null);
            }

            if (cardholder.getText().toString().equals("")) {
                cardholder.setHint("Cardholder Name is required");
                cardholder.setError("Cardholder Name is required");
                has_error = true;
            } else {
                cardholder.setError(null);
            }

            if (address.getText().toString().equals("")) {
                address.setHint("Billing Address is required");
                address.setError("Billing Address is required");
                has_error = true;
            } else {
                address.setError(null);
            }

            if (city.getText().toString().equals("")) {
                city.setHint("City is required");
                city.setError("City is required");
                has_error = true;
            } else {
                city.setError(null);
            }

            if (state.getText().toString().equals("")) {
                state.setError("Required");
                has_error = true;
            } else {
                state.setError(null);
            }

            if (zip.getText().toString().equals("")) {
                zip.setHint("Zipcode is required");
                zip.setError("Zipcode is required");
                has_error = true;
            } else {
                zip.setError(null);
            }

            if (card_number.getText().toString().equals("")) {
                card_number.setHint("Card Number is required");
                card_number.setError("Card Number is required");
                has_error = true;
            } else {
                card_number.setError(null);
            }

            if (expiration.getText().toString().equals("")) {
                expiration.setHint("Card Expiration is required");
                expiration.setError("Card Expiration is required");
                has_error = true;
            } else {
                expiration.setError(null);
            }

            if (cvv.getText().toString().equals("")) {
                cvv.setHint("CVV is required");
                cvv.setError("CVV is required");
                has_error = true;
            } else {
                cvv.setError(null);
            }

            if (has_error) {
                return;
            }

//            String sql;
//            APIPayment apiPayment = new APIPayment();

            // Insert into room usage
//            sql = API.buildQuery(String.format(Locale.US,
//                    "INSERT INTO `Payment` VALUES(NULL,%d,%s,'%s',%d,'%s','%s',%d,'%s','%s');",
//                    reservation_id, card_number.getText().toString(),
//                    cvv
//            ));
//            Log.d("QUERY", sql);
//            apiPayment.execute(Constants.API_QUERY_URL, sql);

            // Close the dialog
            this.dismiss();
        }
    }

    class APIPayment extends API.Post {
        protected void processData(String json) {
            // Don't do anything
            Log.d("RESULT", json);
        }
    }
}

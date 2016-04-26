package com.kittymcfluffums.hotel.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kittymcfluffums.hotel.Listeners;
import com.kittymcfluffums.hotel.R;

import java.util.Locale;

/**
 * Payment dialog
 */
public class ReservationPaymentDialog extends DialogFragment {

    private EditText card_type, cardholder, address, city, state, zip, card_number, expiration, cvv;
    private Listeners mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.dialog_reservation_payment, container, false);

        super.getDialog().setTitle("Payment");

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
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldsInvalid()) {
                    return;
                }

                String string_exp_date = expiration.getText().toString();
                String exp_date = String.format(Locale.US, "20%s-%s-01",
                        string_exp_date.substring(2), string_exp_date.substring(0, 2));

                mListener.onPaymentSubmitted(
                        card_type.getText().toString(), cardholder.getText().toString(),
                        address.getText().toString(), city.getText().toString(),
                        state.getText().toString(), zip.getText().toString(),
                        card_number.getText().toString(), exp_date,
                        cvv.getText().toString()
                );
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listeners) {
            mListener = (Listeners) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listeners");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private boolean fieldsInvalid() {
        boolean has_error = false;
        if (card_type.getText().toString().equals("")) {
            card_type.setError("Card Type is required");
            has_error = true;
        } else {
            card_type.setError(null);
        }

        if (cardholder.getText().toString().equals("")) {
            cardholder.setError("Cardholder Name is required");
            has_error = true;
        } else {
            cardholder.setError(null);
        }

        if (address.getText().toString().equals("")) {
            address.setError("Billing Address is required");
            has_error = true;
        } else {
            address.setError(null);
        }

        if (city.getText().toString().equals("")) {
            city.setError("City is required");
            has_error = true;
        } else {
            city.setError(null);
        }

        if (state.getText().toString().equals("")) {
            state.setError("State is required");
            has_error = true;
        } else {
            state.setError(null);
        }

        if (zip.getText().toString().equals("")) {
            zip.setError("Zipcode is required");
            has_error = true;
        } else {
            zip.setError(null);
        }

        if (card_number.getText().toString().equals("")) {
            card_number.setError("Card Number is required");
            has_error = true;
        } else {
            card_number.setError(null);
        }

        if (expiration.getText().toString().equals("")) {
            expiration.setError("Card Expiration is required");
            has_error = true;
        } else {
            expiration.setError(null);
        }

        if (cvv.getText().toString().equals("")) {
            cvv.setError("CVV is required");
            has_error = true;
        } else {
            cvv.setError(null);
        }

        return has_error;
    }

}

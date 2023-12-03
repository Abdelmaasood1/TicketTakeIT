package com.finalproject.model;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.finalproject.BR;
import com.finalproject.R;

import java.io.Serializable;
import java.util.List;

public class CreateCinemaModel extends BaseObservable implements Serializable {
    private String cinema_name;
    private String ticket_price;
    private String latitude;
    private String longitude;
    private String address;
    private String number_of_seats;

    public ObservableField<String> error_cinema_name = new ObservableField<>();
    public ObservableField<String> error_ticket_price = new ObservableField<>();
    public ObservableField<String> error_address = new ObservableField<>();
    public ObservableField<String> error_number_of_seats = new ObservableField<>();

    public boolean isDataValid(Context context) {
        if (!cinema_name.isEmpty() &&
                !ticket_price.isEmpty() &&
                !address.isEmpty() &&
                !number_of_seats.isEmpty()) {
            error_cinema_name.set(null);
            error_ticket_price.set(null);
            error_address.set(null);
            error_number_of_seats.set(null);
            return true;
        } else {
            if (cinema_name.isEmpty()) {
                error_cinema_name.set(context.getString(R.string.field_required));
            } else {
                error_cinema_name.set(null);
            }
            if (ticket_price.isEmpty()) {
                error_ticket_price.set(context.getString(R.string.field_required));
            } else {
                error_ticket_price.set(null);
            }
            if (address.isEmpty()) {
                error_address.set(context.getString(R.string.field_required));

            } else {
                error_address.set(null);

            }
            if (number_of_seats.isEmpty()) {
                error_number_of_seats.set(context.getString(R.string.field_required));
            } else {
                error_number_of_seats.set(null);
            }
            return false;
        }
    }

    public CreateCinemaModel() {
        this.cinema_name = "";
        this.ticket_price = "";
        this.address = "";
        this.latitude = "";
        this.longitude = "";
        this.number_of_seats = "";
    }

    @Bindable
    public String getCinema_name() {
        return cinema_name;
    }

    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
        notifyPropertyChanged(BR.cinema_name);
    }

    @Bindable
    public String getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(String ticket_price) {
        this.ticket_price = ticket_price;
        notifyPropertyChanged(BR.ticket_price);
    }

    @Bindable
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        notifyPropertyChanged(BR.latitude);
    }

    @Bindable
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        notifyPropertyChanged(BR.longitude);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getNumber_of_seats() {
        return number_of_seats;
    }

    public void setNumber_of_seats(String number_of_seats) {
        this.number_of_seats = number_of_seats;
        notifyPropertyChanged(BR.number_of_seats);
    }
}

package com.finalproject.model;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.finalproject.BR;

import java.io.Serializable;

public class ContactUsModel extends BaseObservable implements Serializable {
    private String name;
    private String mail;
    private String message;

    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_mail = new ObservableField<>();
    public ObservableField<String> error_message = new ObservableField<>();

    public ContactUsModel() {
        this.name = "";
        this.mail = "";
        this.message = "";
    }

    public Boolean isDataValid(Context context) {

        if (!name.trim().isEmpty()
                &&
                !mail.trim().isEmpty()
              && (!message.trim().isEmpty()))
        {
            error_name.set(null);
            error_mail.set(null);
            error_message.set(null);

            return true;
        } else {
            if (name.trim().isEmpty()) {
                error_name.set("Field is Required");
            } else {
                error_name.set(null);
            }
            if (mail.trim().isEmpty()) {
                error_mail.set("Field is Required");
            } else {
                error_mail.set(null);
            }
            if (message.trim().isEmpty()) {
                error_message.set("Field is Required");
            } else {
                error_message.set(null);

            }
            return false;
        }
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }


    @Bindable
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);
    }
}

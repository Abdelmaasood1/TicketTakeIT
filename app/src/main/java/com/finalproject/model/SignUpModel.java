package com.finalproject.model;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.finalproject.BR;
import com.finalproject.R;

import java.io.Serializable;

public class SignUpModel extends BaseObservable implements Serializable {
    private String name;
    private String user_name;
    private String password;
    private String national_id;
    private String email;
    private String gender;
    private String type;
    private String image;

    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_user_name = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_national_id = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();

    public boolean isDataValid(Context context) {
        if (!national_id.trim().isEmpty() &&
                !name.isEmpty() &&
                !email.trim().isEmpty() &&
                !user_name.trim().isEmpty() &&
                !password.trim().isEmpty()) {

            error_national_id.set(null);
            error_name.set(null);
            error_email.set(null);
            error_user_name.set(null);
            error_password.set(null);

            return true;
        } else {

            if (national_id.trim().isEmpty()) {
                error_national_id.set(context.getString(R.string.field_required));

            } else {
                error_national_id.set(null);

            }
            if (name.isEmpty()) {
                error_name.set(context.getString(R.string.field_required));

            } else {
                error_name.set(null);

            }
            if (email.trim().isEmpty()) {
                error_email.set(context.getString(R.string.field_required));

            } else {
                error_email.set(null);

            }
            if (user_name.trim().isEmpty()) {
                error_user_name.set(context.getString(R.string.field_required));

            } else {
                error_user_name.set(null);

            }
            if (password.trim().isEmpty()) {
                error_password.set(context.getString(R.string.field_required));

            } else {
                error_password.set(null);

            }
            if (gender.isEmpty()) {
                Toast.makeText(context, "gender required", Toast.LENGTH_SHORT).show();

            }
            if (type.isEmpty()){
                Toast.makeText(context, R.string.please_choose_the_user, Toast.LENGTH_SHORT).show();
            }

            return false;
        }
    }

    public boolean isDataValid2(Context context) {
        if (!national_id.trim().isEmpty() &&
                !name.isEmpty() &&
                !email.trim().isEmpty() &&
                !user_name.trim().isEmpty()) {

            error_national_id.set(null);
            error_name.set(null);
            error_email.set(null);
            error_user_name.set(null);

            return true;
        } else {

            if (national_id.trim().isEmpty()) {
                error_national_id.set(context.getString(R.string.field_required));

            } else {
                error_national_id.set(null);

            }
            if (name.isEmpty()) {
                error_name.set(context.getString(R.string.field_required));

            } else {
                error_name.set(null);

            }
            if (email.trim().isEmpty()) {
                error_email.set(context.getString(R.string.field_required));

            } else {
                error_email.set(null);

            }
            if (user_name.trim().isEmpty()) {
                error_user_name.set(context.getString(R.string.field_required));

            } else {
                error_user_name.set(null);

            }
            if (gender.isEmpty()) {
                Toast.makeText(context, "gender required", Toast.LENGTH_SHORT).show();

            }

            return false;
        }
    }

    public SignUpModel() {
        this.image = "";
        this.name = "";
        notifyPropertyChanged(BR.name);
        this.user_name = "";
        notifyPropertyChanged(BR.user_name);
        this.password = "";
        notifyPropertyChanged(BR.password);
        this.national_id = "";
        notifyPropertyChanged(BR.national_id);
        this.email = "";
        notifyPropertyChanged(BR.email);
        this.gender = "";
        notifyPropertyChanged(BR.gender);
        this.type = "";
        notifyPropertyChanged(BR.type);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
        notifyPropertyChanged(BR.user_name);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
        notifyPropertyChanged(BR.national_id);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

}

package io.blackbricks.todomanager.auth.registration.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 09/06/16.
 */
@ParcelablePlease
public class RegistrationPresentation extends BaseObservable implements Parcelable {

    String username;
    String email;
    String password;
    String passwordRepeat;

    @Bindable
    public String getUsername() {
        return username;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    @Bindable
    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        RegistrationPresentationParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<RegistrationPresentation> CREATOR = new Creator<RegistrationPresentation>() {
        public RegistrationPresentation createFromParcel(Parcel source) {
            RegistrationPresentation target = new RegistrationPresentation();
            RegistrationPresentationParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public RegistrationPresentation[] newArray(int size) {
            return new RegistrationPresentation[size];
        }
    };
}

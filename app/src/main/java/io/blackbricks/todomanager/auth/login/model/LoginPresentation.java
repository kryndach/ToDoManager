package io.blackbricks.todomanager.auth.login.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 09/06/16.
 */
@ParcelablePlease
public class LoginPresentation extends BaseObservable implements Parcelable {

    String username;
    String password;

    @Bindable
    public String getUsername() {
        return username;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        LoginPresentationParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<LoginPresentation> CREATOR = new Creator<LoginPresentation>() {
        public LoginPresentation createFromParcel(Parcel source) {
            LoginPresentation target = new LoginPresentation();
            LoginPresentationParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public LoginPresentation[] newArray(int size) {
            return new LoginPresentation[size];
        }
    };
}

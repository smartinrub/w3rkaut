package net.dynu.w3rkaut.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * POJO class for location
 */
public class Location implements Parcelable, Serializable {
    private String imageUrl;
    private String userFirstName;
    private String userLastName;
    private String timeRemaining;
    private Double distance;
    private String postedAt;
    private Double latitude;
    private Double longitude;

    public Location(){}

    public Location(String imageUrl, String userFirstName, String
            userLastName, String timeRemaining, Double distance, String
            postedAt, Double latitude, Double longitude) {
        this.imageUrl = imageUrl;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.timeRemaining = timeRemaining;
        this.distance = distance;
        this.postedAt = postedAt;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean isLessThan1000metres() {
        return this.distance < 1000;
    }

    public Double toKilometers() {
        return distance/1000;
    }

    public String displayMinutes() {
        int minutes = Integer.parseInt(timeRemaining.substring(3, 5));
        return String.valueOf(minutes);
    }

    public String displayHours() {
        int hours = Integer.parseInt(timeRemaining.substring(0, 2));
        return String.valueOf(hours);
    }

    public boolean isLessThanAnHour() {
        int hours = Integer.parseInt(timeRemaining.substring(0, 2));
        return hours == 0;
    }

    public boolean isLessThan10minutes() {
        if(Integer.parseInt((timeRemaining.substring(0,2))) == 0){
            return Integer.parseInt((timeRemaining.substring(3,5))) < 11;
        }
        return false;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private Location(Parcel in) {
        imageUrl = in.readString();
        userFirstName = in.readString();
        userLastName = in.readString();
        timeRemaining = in.readString();
        distance = in.readDouble();
        postedAt = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageUrl);
        parcel.writeString(userFirstName);
        parcel.writeString(userLastName);
        parcel.writeString(timeRemaining);
        parcel.writeDouble(distance);
        parcel.writeString(postedAt);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

}

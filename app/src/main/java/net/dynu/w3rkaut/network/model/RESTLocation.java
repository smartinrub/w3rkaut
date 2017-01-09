package net.dynu.w3rkaut.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RESTLocation {
    @SerializedName("user_id")
    @Expose
    private long userId;

    @SerializedName("user_first_name")
    @Expose
    private String userFirstName;

    @SerializedName("user_last_name")
    @Expose
    private String userLastName;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("posted_at")
    @Expose
    private String postedAt;

    @SerializedName("participants")
    @Expose
    private int participants;

    public RESTLocation(long userId, String userFirstName, String userLastName,
                        Double latitude, Double longitude, String postedAt,
                        int participants) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postedAt = postedAt;
        this.participants = participants;
    }

    public RESTLocation(long userId, Double latitude, Double longitude, String
            postedAt, int participants) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postedAt = postedAt;
        this.participants = participants;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public int getParticipants() {
        return participants;
    }
}

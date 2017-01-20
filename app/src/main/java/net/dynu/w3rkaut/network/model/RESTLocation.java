package net.dynu.w3rkaut.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RESTLocation {
    @SerializedName("user_id")
    @Expose
    private long userId;

    @SerializedName("first_name")
    @Expose
    private String userFirstName;

    @SerializedName("last_name")
    @Expose
    private String userLastName;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("time_remaining")
    @Expose
    private String timeRemaining;

    @SerializedName("participants")
    @Expose
    private int participants;

    public RESTLocation(long userId, String userFirstName, String userLastName,
                        Double latitude, Double longitude, String timeRemaining,
                        int participants) {
        this(userId, latitude, longitude, timeRemaining, participants);
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    public RESTLocation(long userId, Double latitude, Double longitude, String
            timeRemaining, int participants) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeRemaining = timeRemaining;
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

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public int getParticipants() {
        return participants;
    }
}

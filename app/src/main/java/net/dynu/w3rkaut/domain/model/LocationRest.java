package net.dynu.w3rkaut.domain.model;
/**
 * This is a POJO class of location for the REST service
 */
public class LocationRest {

    private long userId;
    private String userFirstName;
    private String userLastName;
    private Double latitude;
    private Double longitude;
    private String timeRemaining;
    private String postedAt;

    public LocationRest(long userId, String userFirstName, String userLastName,
                        Double latitude, Double longitude, String timeRemaining,
                        String postedAt) {
        this(userId, latitude, longitude, timeRemaining, postedAt);
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    public LocationRest(long userId, Double latitude, Double longitude, String
            timeRemaining, String postedAt) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeRemaining = timeRemaining;
        this.postedAt = postedAt;
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

    public String getPostedAt() {
        return postedAt;
    }
}

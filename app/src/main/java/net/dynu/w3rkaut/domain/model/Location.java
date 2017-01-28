package net.dynu.w3rkaut.domain.model;

/**
 * This is a POJO class of location for interactors
 */
public class Location {
    private long userId;
    private String userFirstName;
    private String userLastName;
    private String timeRemaining;
    private Double distance;
    private String postedAt;

    public Location(){}

    public Location(long userId, String userFirstName, String userLastName,
                    String timeRemaining, Double distance, String postedAt) {
        this(userId, postedAt);
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.timeRemaining = timeRemaining;
        this.distance = distance;
    }

    public Location(long userId, String postedAt) {
        this.userId = userId;
        this.postedAt = postedAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
}

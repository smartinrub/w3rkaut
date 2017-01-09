package net.dynu.w3rkaut.domain.model;

public class Location {
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String postedAt;
    private Double distance;
    private Integer participants;

    public Location(String userId, String userFirstName, String userLastName, String postedAt, Double distance, int participants) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.postedAt = postedAt;
        this.distance = distance;
        this.participants = participants;
    }

    public Location(String userId, Integer participants) {
        this.userId = userId;
        this.participants = participants;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }
}

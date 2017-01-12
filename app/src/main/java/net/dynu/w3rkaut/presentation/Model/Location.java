package net.dynu.w3rkaut.presentation.Model;

public class Location {
    private long userImageUrl;
    private String userFirstName;
    private String userLastName;
    private String postedAt;
    private Double distance;
    private Integer participants;

    public Location(){}

    public Location(long userImageUrl, String userFirstName, String userLastName, String postedAt, Double distance, int participants) {
        this.userImageUrl = userImageUrl;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.postedAt = postedAt;
        this.distance = distance;
        this.participants = participants;
    }

    public boolean isLessThan1000metres() {
        return this.distance < 1;
    }

    public Double toMetres() {
        return distance * 1000;
    }

    public long getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(long userImageUrl) {
        this.userImageUrl = userImageUrl;
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

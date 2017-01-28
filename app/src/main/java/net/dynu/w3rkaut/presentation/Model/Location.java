package net.dynu.w3rkaut.presentation.Model;

/**
 * POJO class for location
 */
public class Location {
    private String imageUrl;
    private String userFirstName;
    private String userLastName;
    private String timeRemaining;
    private Double distance;
    private String postedAt;

    public Location(){}

    public Location(String imageUrl, String userFirstName, String
            userLastName, String timeRemaining, Double distance, String postedAt) {
        this.imageUrl = imageUrl;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.timeRemaining = timeRemaining;
        this.distance = distance;
        this.postedAt = postedAt;
    }

    public boolean isLessThan1000metres() {
        return this.distance < 1;
    }

    public Double toMetres() {
        return distance * 1000;
    }

    public String showTimeInMinutesHours() {
        StringBuilder sb = new StringBuilder("Finaliza en ");
        int minutes = Integer.parseInt(timeRemaining.substring(3, 5));
        int hours = Integer.parseInt(timeRemaining.substring(0, 2));
        if(hours > 0) {
            sb.append(hours).append(" horas y ");
        }
        sb.append(minutes).append(" minutos");
        return sb.toString();
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
}

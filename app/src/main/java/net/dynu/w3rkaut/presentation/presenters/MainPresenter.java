package net.dynu.w3rkaut.presentation.presenters;

public interface MainPresenter {

    interface View {
        void onLocationAdded(String message);

        void onUserDeleted(String message);

        void onLocationDeleted(String message);
    }

    void addLocation(long userId, Double latitude, Double Longitude, String
            timeRemaining, String postedAt);

    void deleteLocation(long userId);

    void deleteUser(long userId);
}

package net.dynu.w3rkaut.presentation.presenters;

import net.dynu.w3rkaut.presentation.ui.BaseView;

public interface MainPresenter {

    interface View extends BaseView {
        void onLocationAdded(String message);

        void onUserDeleted(String message);

        void onLocationDeleted(String message);
    }

    void addLocation(Double latitude, Double Longitude, String
            timeRemaining, String postedAt);

    void deleteLocation(long userId);

    void deleteUser(long userId);
}

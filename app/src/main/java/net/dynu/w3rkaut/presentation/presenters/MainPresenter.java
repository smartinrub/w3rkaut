package net.dynu.w3rkaut.presentation.presenters;

import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.ui.BaseView;

public interface MainPresenter {

    interface View extends BaseView {
        void onLocationAdded(String message);

        void onUserDeleted(String message);

        void onClickDeleteLocation(Location location);

        void onLocationDeleted(String message);
    }

    void addLocation(Double latitude, Double Longitude, String time);

    void deleteLocation(long userId);

    void deleteUser(long userId);
}

package net.dynu.w3rkaut.presentation.presenters;

import net.dynu.w3rkaut.presentation.ui.BaseView;

public interface MainPresenter {

    interface View extends BaseView {
        void onLocationAdded(String message);
    }

    void addLocation(Double latitude, Double Longitude, String time);
}

package net.dynu.w3rkaut.presentation.presenters;

import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.ui.BaseView;

import java.util.List;

public interface LocationListPresenter {

    interface View extends BaseView {
        void onLocationsRetrieved(List<Location> locations);
    }

    void retriveLocations();
}

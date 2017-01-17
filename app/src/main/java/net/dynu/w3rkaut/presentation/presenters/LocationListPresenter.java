package net.dynu.w3rkaut.presentation.presenters;

import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.ui.BaseView;

import java.util.List;

public interface LocationListPresenter {

    interface View extends BaseView {
        void showLocations(List<RESTLocation> locations);

        void onClickDeleteLocation(Location location);

        void onLocationDeleted(String message);

    }

    void getAllLocations();

    void deleteLocation(long userId);
}

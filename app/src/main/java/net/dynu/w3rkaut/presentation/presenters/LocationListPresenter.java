package net.dynu.w3rkaut.presentation.presenters;

import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.ui.BaseView;

import java.util.List;

public interface LocationListPresenter {

    interface View extends BaseView {
        void showLocations(List<Location> locations);

        void onClickDeleteLocation(Location location);

        void onLocationDeleted();

    }

    void retriveLocations();
}

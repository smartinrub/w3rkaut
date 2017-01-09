package net.dynu.w3rkaut.presentation.presenters;

import net.dynu.w3rkaut.domain.model.Location;
import net.dynu.w3rkaut.presentation.ui.BaseView;

public interface MainPresenter {

    interface View extends BaseView{
        void onClickAddLocation(Location location);
    }

    void addLocation(Location location);
}

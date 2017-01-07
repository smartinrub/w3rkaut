package net.dynu.w3rkaut.presentation.presenters;

import net.dynu.w3rkaut.presentation.presenters.base.BasePresenter;
import net.dynu.w3rkaut.presentation.ui.BaseView;

/**
 * Created by sergio on 07/01/2017.
 */

public interface LoginPresenter extends BasePresenter {

    interface View extends BaseView {
        void onFacebookButtonClicked();
    }
}

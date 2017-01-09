package net.dynu.w3rkaut.presentation.presenters;

/**
 * Created by sergio on 07/01/2017.
 */

public interface LoginPresenter {

    interface View {
        void navigateToHome();

        void hideFacebookLoginButton();
    }
    void saveCredentials(long id, String email, String firstName, String
            lastName);
    void saveUserId(long id);
}

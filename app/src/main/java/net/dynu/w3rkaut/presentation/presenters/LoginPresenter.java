package net.dynu.w3rkaut.presentation.presenters;

public interface LoginPresenter {

    interface View {
        void navigateToHome();

        void hideFacebookLoginButton();
    }
    void saveCredentials(long id, String email, String firstName, String
            lastName);
}

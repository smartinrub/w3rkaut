package net.dynu.w3rkaut.presentation.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.executor.impl.ThreadExecutor;
import net.dynu.w3rkaut.presentation.presenters.LoginPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LoginPresenterImpl;
import net.dynu.w3rkaut.storage.UserRespositoryImpl;
import net.dynu.w3rkaut.threading.MainThreadImpl;

/**
 * Created by sergio on 07/01/2017.
 */

public class FacebookLoginFragment extends Fragment implements LoginPresenter.View {

    private LoginPresenter loginPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_facebook_login,
                container, false);
        init();
        return rootView;
    }

    private void init() {
        loginPresenter = new LoginPresenterImpl(ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(), this, new UserRespositoryImpl
                (getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.resume();
    }

    @Override
    public void onFacebookButtonClicked() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}

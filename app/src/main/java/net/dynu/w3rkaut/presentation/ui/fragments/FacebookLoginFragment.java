package net.dynu.w3rkaut.presentation.ui.fragments;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.presentation.LoginView;
import net.dynu.w3rkaut.presentation.presenters.LoginPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LoginPresenterImpl;
import net.dynu.w3rkaut.storage.UserRepositoryImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;


/**
 * Created by sergio on 07/01/2017.
 */



public class FacebookLoginFragment extends Fragment implements LoginView,
        FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback{

    private LoginPresenter presenter;
    private CallbackManager callbackManager;

    @Bind(R.id.facebook_login_button)
    LoginButton loginButton;

    public FacebookLoginFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_facebook_login,
                container, false);
        ButterKnife.bind(this, rootView);
        loginButton.setReadPermissions(Arrays.asList("public_profile",
                "email"));
        loginButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, this);

        presenter = new LoginPresenterImpl(this, new UserRepositoryImpl());
        return rootView;
    }


    @Override
    public void navigateToHome() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, email, first_name, last_name");
        request.setParameters(parameters);
        request.executeAsync();
        navigateToHome();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        Log.e("tag", "error");
    }

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        try {
            presenter.saveCredentials(object.getLong("id"), object.getString
                    ("email"), object.getString("first_name"), object
                    .getString("last_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

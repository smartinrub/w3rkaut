package net.dynu.w3rkaut.presentation.ui.fragments;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.presentation.presenters.LoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FacebookLoginFragment extends Fragment implements LoginPresenter.View,
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
        Timber.w("ONCREATE");

        init();
        return rootView;
    }

    private void init() {
        loginButton.setReadPermissions(Arrays.asList("public_profile",
                "email"));
        loginButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, this);

//        presenter = new LoginPresenterImpl(
//                ThreadExecutor.getInstance(),
//                MainThreadImpl.getInstance(),
//                new UserRepositoryImpl(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.w("ONRESUME");
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
    public void navigateToHome() {
        getActivity().finish();
    }

    @Override
    public void onCancel() {
        Toast.makeText(getActivity(), "Fallo al iniciar sesión", Toast
                .LENGTH_SHORT).show();
    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(getActivity(), "No se ha podido conectar con Facebook",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        try {
            long id = object.getLong("id");
            presenter.saveCredentials(id, object.getString
                    ("email"), object.getString("first_name"), object
                    .getString("last_name"));
            presenter.saveUserId(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        hideFacebookLoginButton();
    }

    @Override
    public void hideFacebookLoginButton() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loginButton.setVisibility(View.GONE);
        }
    }
}

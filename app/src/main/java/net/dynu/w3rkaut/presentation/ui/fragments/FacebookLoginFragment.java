package net.dynu.w3rkaut.presentation.ui.fragments;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.executor.impl.ThreadExecutor;
import net.dynu.w3rkaut.presentation.presenters.LoginPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LoginPresenterImpl;
import net.dynu.w3rkaut.storage.UserRepositoryImpl;
import net.dynu.w3rkaut.threading.MainThreadImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import timber.log.Timber;

/**
 * This class displays the content for the facebook fragment. It is the view
 * of the MVP pattern.
 *
 * @author Sergio Martin Rubio
 */
public class FacebookLoginFragment extends Fragment implements LoginPresenter.View,
        FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback {

    private static final String USER_AGREEMENT_URL =
            "https://w3rkaut.dynu.net/android/docs/user_agreement.html";
    private static final String PRIVACY_POLICY_URL =
            "https://w3rkaut.dynu.net/android/docs/privacy_policy.html";

    private LoginPresenter presenter;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    @Bind(R.id.facebook_login_button)
    FancyButton loginButton;

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
        setupFacebook();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.logInWithReadPermissions(FacebookLoginFragment
                        .this, Arrays.asList
                        ("public_profile",
                                "email"));
            }
        });

        presenter = new LoginPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                new UserRepositoryImpl(getActivity()));
    }

    private void setupFacebook() {
        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);
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

    @OnClick(R.id.text_view_login_policy)
    public void onClickPolicyText() {
        final CharSequence[] urls = {getString(R.string.user_agreement_label), getString(R.string.privacy_policy_label)};
        AlertDialog.Builder alerBuilder = new AlertDialog.Builder(getActivity());
        alerBuilder.setItems(urls, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent;
                intent = new Intent(Intent.ACTION_VIEW);
                if (urls[i].toString().equals(getString(R.string.user_agreement_label))) {
                    intent.setData(Uri.parse(USER_AGREEMENT_URL));
                } else {
                    intent.setData(Uri.parse(PRIVACY_POLICY_URL));
                }
                startActivity(intent);
            }
        }).create();
        alerBuilder.show();
    }
}

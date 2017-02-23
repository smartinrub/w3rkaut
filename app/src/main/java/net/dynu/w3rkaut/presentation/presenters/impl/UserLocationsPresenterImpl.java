package net.dynu.w3rkaut.presentation.presenters.impl;


import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import net.dynu.w3rkaut.domain.interactors.impl.GetAllUserLocationsInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.interfaces.GetAllUserLocationsInteractor;
import net.dynu.w3rkaut.domain.model.LocationRest;
import net.dynu.w3rkaut.presentation.presenters.interfaces.UserLocationsPresenter;
import net.dynu.w3rkaut.services.interfaces.LocationService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserLocationsPresenterImpl implements UserLocationsPresenter,
        LocationService.VolleyCallback {

    private static final String TAG = UserLocationsPresenterImpl.class.getSimpleName();

    private Context context;

    private UserLocationsPresenter.View view;

    public UserLocationsPresenterImpl(UserLocationsPresenter.View view, Context
            context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getAllUserLocations(long userId) {
        GetAllUserLocationsInteractor interactor = new
                GetAllUserLocationsInteractorImpl();
        interactor.getAllUserLocations(userId, this, context);
    }

    @Override
    public void notifySuccess(String response) {
        List<LocationRest> locations = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double lat = Double.parseDouble(jsonObject.getString("latitude"));
                double lng = Double.parseDouble(jsonObject.getString("longitude"));
                LocationRest location = new LocationRest(lat, lng);
                locations.add(location);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error: ", e.getMessage());
        }
        view.onLocationsRetrieved(locations);
    }

    @Override
    public void notifyError(VolleyError error) {
        Log.e(TAG, String.valueOf(error));
        view.onConnectionFailed(String.valueOf(error));
    }
}

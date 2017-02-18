package net.dynu.w3rkaut.presentation.presenters.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import net.dynu.w3rkaut.domain.interactors.interfaces.GetAllLocationsInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.GetAllLocationsInteractorImpl;
import net.dynu.w3rkaut.domain.model.LocationRest;
import net.dynu.w3rkaut.presentation.presenters.interfaces.LocationListPresenter;
import net.dynu.w3rkaut.services.interfaces.LocationService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter implementation which acts like a bridge between the interactors
 * for the recyclerview and the recyclerview fragment
 *
 * @author Sergio Martin Rubio
 */
public class LocationListPresenterImpl implements
        LocationListPresenter, LocationService.VolleyCallback {

    private static final String TAG = LocationListPresenterImpl.class.getSimpleName();

    private Context context;

    private LocationListPresenter.View view;

    public LocationListPresenterImpl(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getAllLocations() {
        GetAllLocationsInteractor interactor = new
                GetAllLocationsInteractorImpl();
        interactor.getAllLocation(this, context);
    }


    @Override
    public void notifySuccess(String response) {
        List<LocationRest> locations = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long userId = jsonObject.getLong("user_id");
                String firstName = jsonObject.getString("first_name");
                String lastName = jsonObject.getString("last_name");
                double lat = Double.parseDouble(jsonObject.getString("latitude"));
                double lng = Double.parseDouble(jsonObject.getString("longitude"));
                String timeRemaining = jsonObject.getString("time_remaining");
                String postedAt = jsonObject.getString("posted_at");
                LocationRest location = new LocationRest(userId, firstName,
                        lastName, lat, lng, timeRemaining, postedAt);

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

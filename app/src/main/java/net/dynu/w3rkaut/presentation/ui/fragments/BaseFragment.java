package net.dynu.w3rkaut.presentation.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;

import java.util.List;

public class BaseFragment extends Fragment implements
        LocationListPresenter.View {
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void showLocations(List<RESTLocation> locations) {

    }

    @Override
    public void showProgress() {
        Activity activity = getActivity();
        if (activity != null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Cargando...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }
}

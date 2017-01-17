package net.dynu.w3rkaut.presentation;

import net.dynu.w3rkaut.domain.interactors.AddLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.AddLocationInteractorImpl;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.presentation.presenters.MainPresenter;
import net.dynu.w3rkaut.storage.session.SharedPreferencesManager;

import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

public class MainPresenterTest {

//    private MainThread mainThread;
//
//    @Mocked
//    LocationRepository mockedLocationRepository;
//
//    @Mocked
//    MainPresenter.View mockedView;
//
//    @Mocked
//    Executor mExecutor;
//
//    @Mocked
//    SharedPreferencesManager mockedSharedPreferencesManager;
//
//
//    private MainPresenterImpl presenter;
//
//    @Before
//    public void setup() {
//        presenter = new MainPresenterImpl(mExecutor, mainThread,
//                mockedView, mockedLocationRepository, mockedSharedPreferencesManager);
//    }
//
//    @Test
//    public void isOnLocationAddedShowMessage() {
//
//        presenter.onLocationAdded("Response");
//
//        new Verifications() {{
//            mockedView.onLocationAdded(anyString); times=1;
//        }};
//    }
//
//    @Test
//    public void isOnLocationAddedExecuteInteractor () {
//
//
//
//        presenter.addLocation(0.0, 0.0, "00:00");
//    }



}

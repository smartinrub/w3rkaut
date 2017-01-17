package net.dynu.w3rkaut.domain.interactors;



import net.dynu.w3rkaut.domain.interactors.impl.AddLocationInteractorImpl;
import net.dynu.w3rkaut.domain.respository.LocationRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;

public class AddLocationTest {

    private static final long ID = 1;
    private static final Double LAT = 0.0;
    private static final Double LNG = 0.0;
    private static final Integer PARTICIPANTS = 1;
    private static final String POSTED_AT = "00:00";


    @Mocked LocationRepository mockedLocationRepository;
    @Mocked AddLocationInteractor.Callback mockedCallback;

//    @Test
//    public void isAddUserExecutedOneTime() {
//
//        new Expectations() {{
//            mockedLocationRepository.insert(ID, PARTICIPANTS, LAT, LNG,
//                    POSTED_AT); times=1;
//        }};
//
//        AddLocationInteractorImpl interactor = new AddLocationInteractorImpl
//                (mExecutor, mainThread, mockedLocationRepository,
//                        mockedCallback, ID, LAT, LNG, PARTICIPANTS, POSTED_AT);
//        interactor.run();
//
//    }


}

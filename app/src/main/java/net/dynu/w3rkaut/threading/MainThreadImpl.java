package net.dynu.w3rkaut.threading;

import android.os.Handler;
import android.os.Looper;

import net.dynu.w3rkaut.domain.executor.MainThread;


/**
 * This class makes sure that the runnable we provide will be run on the main UI thread.
 *
 * Created by sergio on 07/01/2017.
 */

public class MainThreadImpl implements MainThread {

    private static MainThread sMainThread;

    private Handler mHandler;

    private MainThreadImpl() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static MainThread getInstance() {
        if (sMainThread == null) {
            sMainThread = new MainThreadImpl();
        }

        return sMainThread;
    }
}

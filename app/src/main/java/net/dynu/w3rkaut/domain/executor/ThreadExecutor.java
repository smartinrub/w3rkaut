package net.dynu.w3rkaut.domain.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by sergio on 08/01/2017.
 */

public class ThreadExecutor implements Executor {

    private ExecutorService executorService;

    public ThreadExecutor() {
        executorService = Executors.newFixedThreadPool(3);
    }

    @Override
    public void execute(Runnable runnable) {
        executorService.submit(runnable);
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

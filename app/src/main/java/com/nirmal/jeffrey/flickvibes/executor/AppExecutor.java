package com.nirmal.jeffrey.flickvibes.executor;


import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

  private static AppExecutor instance;
  private final Executor mDiskIO = Executors.newSingleThreadExecutor();
  private final Executor mMainThreadExecutor = new MainThreadExecutor();


  public static AppExecutor getInstance() {
    if (instance == null) {
      instance = new AppExecutor();
    }
    return instance;
  }

  public Executor diskIO() {
    return mDiskIO;
  }

  public Executor mainThread() {
    return mMainThreadExecutor;
  }

  private class MainThreadExecutor implements Executor {

    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
      mainThreadHandler.post(runnable);
    }
  }


}

package com.nirmal.jeffrey.flickvibes.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutor {
  private static AppExecutor instance;
  private AppExecutor(){

  }
  public static AppExecutor getInstance(){
    if(instance==null){
      instance=new AppExecutor();
    }
    return instance;
  }
  private final ScheduledExecutorService networkIO = Executors.newScheduledThreadPool(3);
  public  ScheduledExecutorService getNetworkIO(){
    return networkIO;
  }

}

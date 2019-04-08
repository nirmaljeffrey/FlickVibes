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


}

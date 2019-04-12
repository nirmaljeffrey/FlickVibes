package com.nirmal.jeffrey.flickvibes.util;

import androidx.lifecycle.LiveData;
import com.nirmal.jeffrey.flickvibes.network.response.ApiResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

  /**
   * This method performs a number of checks and returns the Resource type for the retrofit requests
   * check 1) returnType returns LiveData
   * check 2) Check the Type the liveData is wrapping around, LiveData<T> is of ApiResponse.class
   * check 3) Make sure ApiResponse is parameterized(ApiResponse<T>)
   */

  @Override
  public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
    //Check 1
    if(CallAdapter.Factory.getRawType(returnType) != LiveData.class){
      return null;
    }
    //Check 2
    Type observableType = CallAdapter.Factory.getParameterUpperBound(0,(ParameterizedType) returnType);
    Type rawObservableType =CallAdapter.Factory.getRawType(observableType);
    if(rawObservableType!= ApiResponse.class){
      throw new IllegalArgumentException("Type must be a defined resource");
    }
    //Check 3
    if(!(observableType instanceof ParameterizedType)){
      throw  new IllegalArgumentException("resource must be parameterized");
    }
   Type bodyType = CallAdapter.Factory.getParameterUpperBound(0,(ParameterizedType) observableType);
    return new LiveDataCallAdapter<Type>(bodyType);
  }
}

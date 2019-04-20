package com.nirmal.jeffrey.flickvibes.util;


import android.util.Log;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.nirmal.jeffrey.flickvibes.executor.AppExecutor;
import com.nirmal.jeffrey.flickvibes.network.response.ApiResponse;

// CacheObject: Type for the Resource data.
// RequestObject: Type for the API response.
public abstract class NetworkBoundResource<CacheObject, RequestObject> {

  private static final String TAG = "NetworkBoundResource";
  private MediatorLiveData<Resource<CacheObject>> results = new MediatorLiveData<>();
  private AppExecutor appExecutor;

  public NetworkBoundResource(AppExecutor appExecutor) {
    this.appExecutor = appExecutor;
    init();
  }

  /**
   * 1) Update loading status and Observe Local database 2) if(condition) - stop observing the local
   * database - insert new data into th database from network - start observing the database 3) else
   * - Update success status with existing cacheObject
   */
  private void init() {
    //Update LiveData for loading status
    results.setValue((Resource<CacheObject>) Resource.loading(null));
    //observe liveData from the database
    final LiveData<CacheObject> dbSource = loadFromDb();
    // add the dbSource to mediator liveData
    results.addSource(dbSource, cacheObject -> {
      results.removeSource(dbSource);
      if (shouldFetch(cacheObject)) {
        //get data from the network
        fetchFromNetwork(dbSource);
      } else {
        results.addSource(dbSource, cacheObject1 -> setValue(Resource.success(cacheObject1)));
      }
    });

  }

  private void fetchFromNetwork(final LiveData<CacheObject> dbSource) {
    Log.d(TAG, "fetchFromNetwork: Called.");
    results.addSource(dbSource, cacheObject -> setValue((Resource.loading(cacheObject))));
    final LiveData<ApiResponse<RequestObject>> apiResponse = createCall();
    results.addSource(apiResponse, requestObjectApiResponse -> {
      results.removeSource(dbSource);
      results.removeSource(apiResponse);

      Log.d(TAG, "run: Attempting to refresh data from network");
      /*
       3 cases:
       1) ApiSuccessResponse
       2) ApiErrorResponse
       3) ApiEmptyResponse
       */
      if (requestObjectApiResponse instanceof ApiResponse.ApiSuccessResponse) {
        Log.d(TAG, "onChanged: onApiSucess");
        appExecutor.diskIO().execute(() -> {
          //save the response to local database
          saveCallResult((RequestObject) processResponse(
              (ApiResponse.ApiSuccessResponse) requestObjectApiResponse));
          appExecutor.mainThread().execute(() -> results.addSource(loadFromDb(),
              cacheObject -> setValue(Resource.success(cacheObject))));
        });


      } else if (requestObjectApiResponse instanceof ApiResponse.ApiEmptyResponse) {
        Log.d(TAG, "onChanged: onApiEmpty");
        appExecutor.mainThread().execute(() -> results.addSource(loadFromDb(),
            cacheObject -> setValue(Resource.success(cacheObject))));

      } else if (requestObjectApiResponse instanceof ApiResponse.ApiErrorResponse) {
        Log.d(TAG, "onChanged: onApiError");
        results.addSource(dbSource, cacheObject -> setValue(
            Resource.error(
                ((ApiResponse.ApiErrorResponse) requestObjectApiResponse).getErrorMessage(),
                cacheObject)));
      }
    });

  }

  private CacheObject processResponse(ApiResponse.ApiSuccessResponse response) {
    return (CacheObject) response.getBody();
  }

  private void setValue(Resource<CacheObject> newValue) {
    if (results.getValue() != newValue) {
      results.setValue(newValue);
    }
  }


  // Called to save the result of the API response into the database.
  @WorkerThread
  protected abstract void saveCallResult(@NonNull RequestObject item);

  // Called with the data in the database to decide whether to fetch
  // potentially updated data from the network.
  @MainThread
  protected abstract boolean shouldFetch(@Nullable CacheObject data);

  // Called to get the cached data from the database.
  @NonNull
  @MainThread
  protected abstract LiveData<CacheObject> loadFromDb();

  // Called to create the API call.
  @NonNull
  @MainThread
  protected abstract LiveData<ApiResponse<RequestObject>> createCall();


  // Returns a LiveData object that represents the resource that's implemented
  // in the base class.
  public final LiveData<Resource<CacheObject>> getAsLiveData() {
    return results;
  }

  ;
}


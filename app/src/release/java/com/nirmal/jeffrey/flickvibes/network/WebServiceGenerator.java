package com.nirmal.jeffrey.flickvibes.network;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nirmal.jeffrey.flickvibes.util.LiveDataCallAdapterFactory;
import com.nirmal.jeffrey.flickvibes.util.NetworkUtils;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceGenerator {


  private static OkHttpClient.Builder okHttpClient = new Builder().addInterceptor(
      chain -> {
        //Get the request
        Request original = chain.request();
        //Get the url from the request
        HttpUrl originalUrl = original.url();
        // Add api key as a query parameter to the url that we got from the request
        HttpUrl url = originalUrl.newBuilder()
            .addQueryParameter(NetworkUtils.API_KEY_PARAMETER, NetworkUtils.API_KEY_VALUE)
            .build();
        // Build a new request from the url appended with api key
        Request.Builder requestBuilder = original.newBuilder().url(url);
        Request request = requestBuilder.build();

        return chain.proceed(request);


      });

  //Create a Gson object to exclude the variables without annotation inside model class
  private static Gson gson = new GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation().create();


  private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
      .baseUrl(NetworkUtils.MOVIE_BASE_URL)
      //Interceptor to add api key as a query parameter to the base url
      //and to set Timeout for the network request
      .client(okHttpClient.build())
      .addConverterFactory(GsonConverterFactory.create(gson))
      .addCallAdapterFactory(new LiveDataCallAdapterFactory());

  private static Retrofit retrofit = retrofitBuilder.build();
  private static MovieApi movieApi = retrofit.create(MovieApi.class);

  public static MovieApi getMovieApi() {
    return movieApi;
  }


}







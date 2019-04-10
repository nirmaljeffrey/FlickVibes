package com.nirmal.jeffrey.flickvibes.network;


import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nirmal.jeffrey.flickvibes.util.Constants;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceGenerator {

  private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().addInterceptor(
      new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
          //Get the request
          Request original = chain.request();
          //Get the url from the request
          HttpUrl originalUrl = original.url();
          // Add api key as a query parameter to the url that we got from the request
          HttpUrl url = originalUrl.newBuilder()
              .addQueryParameter(Constants.API_KEY_PARAMETER, Constants.API_KEY_VALUE)
              .build();
          // Build a new request from the url appended with api key
          Request.Builder requestBuilder = original.newBuilder().url(url);
          Request request = requestBuilder.build();
          return chain.proceed(request);
        }
      })
      .connectTimeout(10, TimeUnit.SECONDS)
      .readTimeout(7, TimeUnit.SECONDS)
      .writeTimeout(7, TimeUnit.SECONDS)
      .addNetworkInterceptor(new StethoInterceptor());

  //Create a Gson object to exclude the variables without annotation inside model class
  private static Gson gson = new GsonBuilder()
      .excludeFieldsWithoutExposeAnnotation().create();


  private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
      .baseUrl(Constants.MOVIE_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      //Interceptor to add api key as a query parameter to the base url
      //and to set Timeout for the network request
      .client(okHttpClient.build());
  private static Retrofit retrofit = retrofitBuilder.build();
  private static MovieApi movieApi = retrofit.create(MovieApi.class);

  public static MovieApi getMovieApi() {
    return movieApi;
  }


}







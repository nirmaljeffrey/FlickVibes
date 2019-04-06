package com.nirmal.jeffrey.flickvibes.network;


import com.nirmal.jeffrey.flickvibes.network.NetworkUtil.NetworkConstants;


import java.io.IOException;
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
         HttpUrl url= originalUrl.newBuilder()
             .addQueryParameter(NetworkConstants.API_KEY_PARAMETER,NetworkConstants.API_KEY_VALUE)
             .build();
         // Build a new request from the url appended with api key
         Request.Builder requestBuilder = original.newBuilder().url(url);
        Request request= requestBuilder.build();
         return chain.proceed(request);
       }
     });


 private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
      .baseUrl(NetworkConstants.MOVIE_BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
     //Interceptor to add api key as a query parameter to the base url
      .client(okHttpClient.build());
  private static Retrofit retrofit = retrofitBuilder.build();
  private static MovieApi movieApi = retrofit.create(MovieApi.class);

  public static MovieApi getMovieApi() {
    return movieApi;
  }
}







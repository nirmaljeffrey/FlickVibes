package com.nirmal.jeffrey.flickvibes.network.response;

import java.io.IOException;
import retrofit2.Response;

public class ApiResponse<T> {

  public ApiResponse<T> create(Throwable error) {
    return new ApiErrorResponse<>(
        error.getMessage().equals("") ? "Unknown error \n Check the network Connection"
            : error.getMessage());
  }

  public ApiResponse<T> create(Response<T> response) {
    // check if the response is successful(HTTP CODES between 200 and 200)
    if (response.isSuccessful()) {
      T body = response.body();
      //Check for null response
      //204 is empty response code
      if (body == null || response.code() == 204) {
        return new ApiEmptyResponse<>();
      } else {
        //successful response
        return new ApiSuccessResponse<>(body);
      }

    } else {
      //Response is not successful
      //Try and catch the error message
      // return the error message as ApiErrorResponse
      String errorMessage = "";
      try {
        errorMessage = response.errorBody().string();
      } catch (IOException e) {
        errorMessage = response.message();
      }
      return new ApiErrorResponse<>(errorMessage);
    }

  }

  public class ApiSuccessResponse<T> extends ApiResponse<T> {

    private T body;

    ApiSuccessResponse(T body) {
      this.body = body;
    }

    public T getBody() {
      return body;
    }
  }

  public class ApiErrorResponse<eT> extends ApiResponse<T> {

    private String errorMessage;

    public ApiErrorResponse(String errorMessage) {
      this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
      return errorMessage;
    }
  }

  public class ApiEmptyResponse<T> extends ApiResponse<T> {

  }

}

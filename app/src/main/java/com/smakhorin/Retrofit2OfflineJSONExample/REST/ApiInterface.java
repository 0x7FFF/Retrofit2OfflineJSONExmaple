package com.smakhorin.Retrofit2OfflineJSONExample.REST;

import com.smakhorin.Retrofit2OfflineJSONExample.REST.DTO.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    String BASE_URL = "http://jsonplaceholder.typicode.com/";

    @GET("users")
    Call<List<UserData>> getAllUsers();
}

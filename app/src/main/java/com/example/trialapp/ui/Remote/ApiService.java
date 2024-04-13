package com.example.trialapp.ui.Remote;

import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

//    @PUT("/addUser")
//    Call<User> addUser(@Body ResponseRegisterClass responseRegisterClass);
//
//    @POST("/getUser")
//    Call<User> getUser(@Body ResponseRegisterClass responseRegisterClass);

    @POST("/register")
    Call<User> registerUser(@Body User user);

    @POST("/login")
    Call<User> loginUser(@Body Login login);

    @POST("/checkin")
    Call<Checkin> checkinUser(@Body Checkin checkin);

    @GET("/reservations/{username}")
    Call<List<Reservation>> getReservations(@Path("username") String username);

    @GET("/profile/{username}")
    Call<JsonObject> getUserProfile(@Path("username") String username);

    @GET("/activeReservations/{place_id}")
    Call<List<User>> getActiveReservations(@Path("place_id") String placeId);

    @POST("/forgotPassword")
    Call<?> forgotPasswordRequest(@Body ForgotPasswordRequest reqBody);


}



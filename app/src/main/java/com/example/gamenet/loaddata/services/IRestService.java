package com.example.gamenet.loaddata.services;

import com.example.gamenet.loaddata.object.ObjAccount;
import com.example.gamenet.loaddata.object.ObjData;
import com.example.gamenet.loaddata.object.ObjFullData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by GameNet on 3/13/2017.
 */

public interface IRestService {

    @FormUrlEncoded
    @POST("/api/token")
    Call<ObjAccount> login(
            @Field("grant_type") String grant_type,
            @Field("username") String username,
            @Field("password") String password);


    @Headers("Accept: application/vnd.app.atoms.mobile-v1+json")
    @GET("/api/stories")
    Call<ObjFullData> loadData(@Header("Authorization") String bearerToken);
}

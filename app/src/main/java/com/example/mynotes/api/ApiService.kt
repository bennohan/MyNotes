package com.example.mynotes.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //Request login
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): String

    //Request register//
    @FormUrlEncoded
    @POST("user")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("confirmPassword") confirmPassword: String?
    ): String

    //Create Note
    @FormUrlEncoded
    @POST("note/")
    suspend fun createNote():String

    //Get Token
    @GET("user/get-token")
    suspend fun getToken(): String

    //Get Profile
    @GET("profile")
    suspend fun getProfile(): String

    //Update Profile
    @PATCH("update-profile")
    suspend fun updateProfile()

    // Get Note
    @GET("note/")
    suspend fun getNote(): String

}
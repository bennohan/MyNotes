package com.example.mynotes.api

import retrofit2.http.*

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
    suspend fun createNote(
        @Field("title") title : String,
        @Field("content") content : String
    ):String

    //Get Token
    @GET("user/get-token")
    suspend fun getToken(): String

    //Get Profile
    @GET("profile")
    suspend fun getProfile(): String

    //Update Profile
    @FormUrlEncoded
    @PATCH("user/profile")
    suspend fun updateProfile(
        @Field("name") name: String,
    ): String

    // Get Note
    @GET("note/")
    suspend fun getNote(
        @Query("search") search: String?
    ): String

    //UpdateNote
    @FormUrlEncoded
    @PATCH("note/:id")
    suspend fun updateNote(
        @Field("title") title: String,
        @Field("content") content: String
    ) : String
}
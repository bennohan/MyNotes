package com.example.mynotes.api

import okhttp3.MultipartBody
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

    //Update Profile Photo
    @Multipart
    @PATCH("user/profile")
    suspend fun updateProfileWithPhoto(
        @Part("name") name: String?,
        @Part photo: MultipartBody.Part?
    ):String

    // Get Note
    @GET("note/")
    suspend fun getNote(
        @Query("search") search: String?
    ): String

    //UpdateNote
    @FormUrlEncoded
    @PATCH("note/{id}")
    suspend fun updateNote(
        @Path("id") id : String,
        @Field("title") title: String,
        @Field("content") content: String
    ) : String

    //Delete Note
    @DELETE("note/{id}")
    suspend fun deleteNote(
        @Path("id") id : String,
    ) : String
}
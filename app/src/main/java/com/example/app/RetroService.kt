package com.example.app

import retrofit2.Call
import retrofit2.http.*

interface RetroService {

    //https://gorest.co.in/public/v2/users
    @GET("users")
    @Headers("Accept:application/json","Content-Type:application/json",
        "Authorization: Bearer 196ef05b11637533b125a84181eb2293007f19d677f148a4e5378ad4ce471c4e")
    fun getUsersList(): Call<UserList>

    //https://gorest.co.in/public-api/users?name=a
    @GET("users")
    @Headers("Accept:application/json","Content-Type:application/json",
        "Authorization: Bearer 196ef05b11637533b125a84181eb2293007f19d677f148a4e5378ad4ce471c4e")
    fun searchUsers(@Query("name") searchText: String): Call<UserList>


    //https://gorest.co.in/public/v2/users
    @GET("users/{user_id}")
    @Headers("Accept:application/json","Content-Type:application/json",
        "Authorization: Bearer 196ef05b11637533b125a84181eb2293007f19d677f148a4e5378ad4ce471c4e")
    fun getUser(@Path("user_id") user_id: String): Call<UserResponse>


    @POST("users")
    @Headers("Accept:application/json", "Content-Type:application/json",
        "Authorization: Bearer 196ef05b11637533b125a84181eb2293007f19d677f148a4e5378ad4ce471c4e")
    fun createUser(@Body params: User): Call<UserResponse>

    @PATCH("users/{user_id}")
    @Headers("Accept:application/json", "Content-Type:application/json",
        "Authorization: Bearer 196ef05b11637533b125a84181eb2293007f19d677f148a4e5378ad4ce471c4e")
    fun updateUser(@Path("user_id") user_id: String, @Body params: User): Call<UserResponse>

    @DELETE("users/{user_id}")
    @Headers("Accept:application/json", "Content-Type:application/json",
        "Authorization: Bearer 196ef05b11637533b125a84181eb2293007f19d677f148a4e5378ad4ce471c4e")
    fun deleteUser(@Path("user_id") user_id: String): Call<UserResponse>

}
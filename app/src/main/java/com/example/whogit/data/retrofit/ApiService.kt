package com.example.whogit.data.retrofit

import com.example.whogit.BuildConfig
import com.example.whogit.data.model.DetailUserModel
import com.example.whogit.data.model.UserModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUser(
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<UserModel.ItemsItem>
    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username : String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): DetailUserModel

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username : String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<UserModel.ItemsItem>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username : String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<UserModel.ItemsItem>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun getSearchUser(
        @QueryMap params: Map<String, Any>,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): UserModel
}
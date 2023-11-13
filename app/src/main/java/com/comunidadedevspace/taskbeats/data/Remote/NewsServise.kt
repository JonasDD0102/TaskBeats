package com.comunidadedevspace.taskbeats.data.Remote


import com.comunidadedevspace.taskbeats.BuildConfig
import retrofit2.http.GET

interface NewsServise {

    //https://api.thenewsapi.com/v1/news/ top?api_token=&locale=us&limit=3
    @GET("top?api_token=${BuildConfig.API_KEY}&locale=us&limit=3")
    suspend fun fetTopNews():NewsResponce

    @GET("all?api_token=${BuildConfig.API_KEY}&locale=us&limit=3")
    suspend fun fetAllNews():NewsResponce
}
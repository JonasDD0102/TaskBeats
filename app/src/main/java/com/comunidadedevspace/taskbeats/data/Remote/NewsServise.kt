package com.comunidadedevspace.taskbeats.data.Remote


import retrofit2.http.GET

interface NewsServise {
    @GET("news?category=science")
    suspend fun fetchNews():NewsResponce
}
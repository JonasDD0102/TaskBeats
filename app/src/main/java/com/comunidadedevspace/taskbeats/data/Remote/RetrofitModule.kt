package com.comunidadedevspace.taskbeats.data.Remote

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//vou criar a retrofit
object RetrofitModule {
     fun createNewsService(): NewsServise {

         val logging = HttpLoggingInterceptor()
         logging.apply {
             HttpLoggingInterceptor.Level.BODY
         }

         val client: OkHttpClient = OkHttpClient.Builder()
             .addInterceptor(logging)
             .build()


         val retrofit = Retrofit
             .Builder()
             .client(client)
             .baseUrl("https://inshorts.deta.dev/")
             .addConverterFactory(GsonConverterFactory.create(Gson()))

       return retrofit
             .build()
             .create(NewsServise::class.java)
     }
}
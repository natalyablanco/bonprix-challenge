package com.example.bonprixchallenge.network

import com.example.bonprixchallenge.Secrets
import com.example.bonprixchallenge.domain.Categories
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface RetrofitService {

    @Headers("x-api-key:" + Secrets.API_KEY)
    @GET("navigation")
    suspend fun getAssortment(): Response<Categories>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://codechallenge.mobilelab.io/v1/bonprix/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}
package com.example.bonprixchallenge.data

import com.example.bonprixchallenge.domain.Categories
import com.example.bonprixchallenge.network.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getAssortment(): Categories? {
        val response = retrofitService.getAssortment()
        return if (response.isSuccessful) {
            response.body()
        } else {
            Categories(emptyList())
        }
    }

}
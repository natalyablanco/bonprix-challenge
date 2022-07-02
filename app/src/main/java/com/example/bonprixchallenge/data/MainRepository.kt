package com.example.bonprixchallenge.data

import com.example.bonprixchallenge.network.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getNavigation() = retrofitService.getNavigation()

}
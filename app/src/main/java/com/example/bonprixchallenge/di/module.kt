package com.example.bonprixchallenge.di

import com.example.bonprixchallenge.data.MainRepository
import com.example.bonprixchallenge.network.RetrofitService
import com.example.bonprixchallenge.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitService.getInstance()}

    single { MainRepository(get()) }

    viewModel { MainViewModel(get()) }
}
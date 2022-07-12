package com.example.bonprixchallenge.data

import com.example.bonprixchallenge.InstantExecutorExtension
import com.example.bonprixchallenge.network.RetrofitService
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
internal class MainRepositoryTest {

    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var apiService: RetrofitService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mainRepository = MainRepository(apiService)
    }

    @Test
    fun getAssortment() {
        runBlocking {
            mainRepository.getAssortment()
        }
    }
}
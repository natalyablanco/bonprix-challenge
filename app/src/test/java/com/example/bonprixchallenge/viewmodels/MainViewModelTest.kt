package com.example.bonprixchallenge.viewmodels

import com.example.bonprixchallenge.InstantExecutorExtension
import com.example.bonprixchallenge.data.MainRepository
import com.example.bonprixchallenge.domain.Categories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExtendWith(InstantExecutorExtension::class)
internal class MainViewModelTest {

    @Mock
    var repository: MainRepository = Mockito.mock(MainRepository::class.java)

    private lateinit var viewModel: MainViewModel

    @ExperimentalCoroutinesApi
    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(repository)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @ExperimentalCoroutinesApi
    @org.junit.jupiter.api.Test
    fun getCategoryList() {
        runBlocking{
            val categories = Categories(categories = emptyList())
            whenever(repository.getAssortment()).thenReturn(categories)
            viewModel = MainViewModel(repository)
            Assert.assertEquals(categories, viewModel.categoryList.value)
        }
    }

    @org.junit.jupiter.api.Test
    fun getUrlToDisplay() {
    }

    @org.junit.jupiter.api.Test
    fun getLabelToDisplay() {
    }

    @org.junit.jupiter.api.Test
    fun getCloseApp() {
    }

    @org.junit.jupiter.api.Test
    fun getAllLinks() {
    }

    @org.junit.jupiter.api.Test
    fun navigateTo() {
    }

    @org.junit.jupiter.api.Test
    fun navigateBack() {
    }
}
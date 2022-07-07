package com.example.bonprixchallenge.viewmodels

import com.example.bonprixchallenge.InstantExecutorExtension
import com.example.bonprixchallenge.data.MainRepository
import org.junit.Before
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
internal class MainViewModelTest {

    @Mock
    var repository: MainRepository = Mockito.mock(MainRepository::class.java)

    private lateinit var viewModel: MainViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(repository)
    }

    @org.junit.jupiter.api.Test
    fun getCategoryList() {
        //doReturn("categories").`when`(repository).getAssortment()
        viewModel.categoryList
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
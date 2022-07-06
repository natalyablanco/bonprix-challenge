package com.example.bonprixchallenge.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bonprixchallenge.data.MainRepository
import com.example.bonprixchallenge.domain.Categories
import com.example.bonprixchallenge.domain.Category
import com.example.bonprixchallenge.navigation.Navigation
import kotlinx.coroutines.*

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {
    val categoryList = MutableLiveData<Categories>()
    val urlToDisplay = MutableLiveData<String>()
    val labelToDisplay = MutableLiveData<String>()
    val closeApp = MutableLiveData(false)

    private val navigation = Navigation()

    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getAllLinks() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getNavigation()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    categoryList.postValue(response.body())
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun navigateTo(category: Category) {
        navigation.stack.add(
            Category(
                label = labelToDisplay.value ?: "",
                children = categoryList.value?.categories
            )
        )
        if (category.children === null){
            urlToDisplay.postValue(category.url)
            categoryList.postValue(null)
            labelToDisplay.postValue(null)
        } else {
            categoryList.postValue(Categories(category.children))
            labelToDisplay.postValue(category.label)
            urlToDisplay.postValue(null)
        }
    }

    fun navigateBack() {
        if (navigation.stack.isNotEmpty()){
            val lastItem = navigation.stack.removeLast()
            categoryList.postValue(lastItem.children?.let { Categories(it) })
            labelToDisplay.postValue(lastItem.label)
            urlToDisplay.postValue(null)
        } else {
            closeApp.postValue(true)
        }
    }

    private fun onError(message: String) {
       Log.e("ERROR", message)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

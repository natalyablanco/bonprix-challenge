package com.example.bonprixchallenge.interactors

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bonprixchallenge.data.MainRepository
import com.example.bonprixchallenge.domain.Categories
import kotlinx.coroutines.*

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {
    val categoryList = MutableLiveData<Categories>()

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

    private fun onError(message: String) {
       Log.e("ERROR", message)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

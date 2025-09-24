package com.unsoed.informatikamobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import android.util.Log
import retrofit2.Response
import com.unsoed.informatikamobile.data.network.RetrofitInstance
import com.unsoed.informatikamobile.data.model.BookDoc


class MainViewModel : ViewModel() {

    private val _books = MutableLiveData<List<BookDoc>>()
    val books: LiveData<List<BookDoc>> = _books

    fun fetchBooks(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchBooks(query, limit = 10)
                if (response.isSuccessful) {
                    val result = response.body()?.docs ?: emptyList()
                    _books.value = result
                    Log.d("SUCCESS_GET_DATA", "$result")
                } else {
                    Log.e("API_ERROR", "${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", e.localizedMessage ?: "Unknown error")
            }
        }
    }
}

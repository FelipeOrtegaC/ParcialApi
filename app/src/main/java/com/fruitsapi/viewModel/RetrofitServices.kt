package com.frutas.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitsapi.View.MainActivity
import com.frutas.model.ApiService
import com.frutas.model.Fruit
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@OptIn(DelicateCoroutinesApi::class)
class RetrofitService : ViewModel() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.fruityvice.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    private val _fruits = MutableLiveData<List<Fruit>>()
    val fruits: LiveData<List<Fruit>> get() = _fruits

    suspend fun fetchFruits(): List<Fruit> {
        return try {
            apiService.getFruits()
        } catch (e: Exception) {
            Log.e("Error fetching fruits:", "${e.message}")
            emptyList()
        }
    }
    fun iniciarProcesoAsincrono() {
        viewModelScope.launch {
            try {
                _fruits.value = fetchFruits()!!
            } catch (e: Exception) {
                Log.e("Error fetching fruits:", "${e.message}")
                _fruits.value = emptyList()
            }
        }
    }

    fun searchFruitsByName(name: String){
        viewModelScope.launch {
            try {
                fruits.value?.find { it.name.equals(name, false) }
            } catch (e: Exception) {
                Log.e("Error searching fruits:", "${e.message}")
                _fruits.value = emptyList()
            }
        }
    }

}
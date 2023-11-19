package com.frutas.viewModel
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
class RetrofitService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.fruityvice.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)
    var fruits by mutableStateOf<List<Fruit>>(emptyList())
    init {
        GlobalScope.launch(Dispatchers.IO) {
            fruits= fetchFruits()
        }
    }

    suspend fun fetchFruits(): List<Fruit> {
        return try {
            apiService.getFruits()
        } catch (e: Exception) {
            Log.e("Error fetching fruits:", "${e.message}")
            emptyList()
        }
    }
    private fun sorterByCalories():List<Fruit>{
        return fruits.sortedBy { it.nutritions.calories }
    }
    fun findFruitsByFilter(indexList:Int,order:Boolean):List<Fruit>{
        return if(order)
            when(indexList){
                1->fruits.sortedBy { it.nutritions.fat }.asReversed()
                2->fruits.sortedBy { it.nutritions.sugar }.asReversed()
                3->fruits.sortedBy { it.nutritions.carbohydrates }.asReversed()
                4->fruits.sortedBy { it.nutritions.protein }.asReversed()
                else -> fruits.sortedBy { it.nutritions.calories }.asReversed()
            }
        else
            when(indexList){
                1->fruits.sortedBy { it.nutritions.fat }
                2->fruits.sortedBy { it.nutritions.sugar }
                3->fruits.sortedBy { it.nutritions.carbohydrates }
                4->fruits.sortedBy { it.nutritions.protein }
                else -> fruits.sortedBy { it.nutritions.calories }
            }
    }
    fun fruitByName(name:String):List<Fruit>{
        fruits.find {it.name.uppercase() == name.uppercase() }.let {
            if(it == null)return emptyList() else return listOf(it)
        }
    }
}
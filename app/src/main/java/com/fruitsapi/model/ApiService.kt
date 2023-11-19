package com.frutas.model

import retrofit2.http.GET

interface ApiService {
    @GET("fruit/all")
    suspend fun getFruits(): List<Fruit>
}
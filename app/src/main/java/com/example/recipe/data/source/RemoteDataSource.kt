package com.example.recipe.data.source

import com.example.recipe.data.network.ApiServices
import com.example.recipe.models.register.BodyRegister
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: ApiServices) {

    suspend fun postRegister(apiKey: String, body: BodyRegister) = api.postRegister(apiKey, body)
    suspend fun getRecipes(queries: Map<String, String>) = api.getRecipes(queries)
    suspend fun getDetail(id: Int, apiKey: String) = api.getDetail(id, apiKey)
    suspend fun getSimilarRecipes(id: Int, apiKey: String) = api.getSimilarRecipes(id, apiKey)
    suspend fun getRandomRecipe(queries: Map<String, String>) = api.getRandomRecipe(queries)
}
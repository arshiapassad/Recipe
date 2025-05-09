package com.example.recipe.viewmodel

import academy.nouri.s4_recipeapp.data.database.entity.DetailEntity
import academy.nouri.s4_recipeapp.data.database.entity.FavoriteEntity
import academy.nouri.s4_recipeapp.data.repository.RecipeRepository
import academy.nouri.s4_recipeapp.models.detail.ResponseDetail
import academy.nouri.s4_recipeapp.models.detail.ResponseSimilar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe.utils.NetworkRequest
import com.example.recipe.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {
    //Api
    val detailData = MutableLiveData<NetworkRequest<ResponseDetail>>()
    fun callDetailApi(id: Int, apiKey: String) = viewModelScope.launch {
        detailData.value = NetworkRequest.Loading()
        val response = repository.remote.getDetail(id, apiKey)
        detailData.value = NetworkResponse(response).generalNetworkResponse()
        //Cache
        val cache = detailData.value?.data
        if (cache != null)
            cacheDetail(cache.id!!, cache)
    }

    //Local
    private fun saveDetail(entity: DetailEntity) = viewModelScope.launch {
        repository.local.saveDetail(entity)
    }

    fun readDetailFromDb(id: Int) = repository.local.loadDetail(id).asLiveData()

    val existsDetailData = MutableLiveData<Boolean>()
    fun existsDetail(id: Int) = viewModelScope.launch {
        repository.local.existsDetail(id).collect { existsDetailData.postValue(it) }
    }

    private fun cacheDetail(id: Int, response: ResponseDetail) {
        val entity = DetailEntity(id, response)
        saveDetail(entity)
    }

    //Similar
    val similarData = MutableLiveData<NetworkRequest<ResponseSimilar>>()
    fun callSimilarApi(id: Int, apiKey: String) = viewModelScope.launch {
        similarData.value = NetworkRequest.Loading()
        val response = repository.remote.getSimilarRecipes(id, apiKey)
        similarData.value = NetworkResponse(response).generalNetworkResponse()
    }

    //Favorite
    fun saveFavorite(entity: FavoriteEntity) = viewModelScope.launch {
        repository.local.saveFavorite(entity)
    }

    fun deleteFavorite(entity: FavoriteEntity) = viewModelScope.launch {
        repository.local.deleteFavorite(entity)
    }

    val existsFavoriteData = MutableLiveData<Boolean>()
    fun existsFavorite(id: Int) = viewModelScope.launch {
        repository.local.existsFavorite(id).collect { existsFavoriteData.postValue(it) }
    }
}
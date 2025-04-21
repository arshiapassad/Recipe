package com.example.recipe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.data.repository.RegisterRepository
import com.example.recipe.models.register.BodyRegister
import com.example.recipe.models.register.ResponseRegister
import com.example.recipe.utils.NetworkRequest
import com.example.recipe.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) : ViewModel() {

    //Api
    val registerData = MutableLiveData<NetworkRequest<ResponseRegister>>()
    fun callRegisterApi(apiKey: String, body: BodyRegister) = viewModelScope.launch {
        registerData.value = NetworkRequest.Loading()
        val response = repository.postRegister(apiKey, body)
        registerData.value = NetworkResponse(response).generalNetworkResponse()
    }

   //Stored data
   fun saveData(username: String, hash: String) = viewModelScope.launch {
       repository.saveRegisterData(username, hash)
   }

    val readData = repository.readRegisterData
}
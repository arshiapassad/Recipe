package academy.nouri.s4_recipeapp.viewmodel

import academy.nouri.s4_recipeapp.data.repository.SearchRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipe.models.recipe.ResponseRecipes
import com.example.recipe.utils.Constants
import com.example.recipe.utils.NetworkRequest
import com.example.recipe.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    fun searchQueries(search: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.API_KEY] = Constants.MY_API_KEY
        queries[Constants.NUMBER] = Constants.FULL_COUNT.toString()
        queries[Constants.ADD_RECIPE_INFORMATION] = Constants.TRUE
        queries[Constants.QUERY] = search
        return queries
    }

    val searchData = MutableLiveData<NetworkRequest<ResponseRecipes>>()

    fun callSearchApi(queries: Map<String, String>) = viewModelScope.launch {
        searchData.value = NetworkRequest.Loading()
        val response = repository.getSearchRecipe(queries)
        searchData.value = NetworkResponse(response).generalNetworkResponse()
    }
}
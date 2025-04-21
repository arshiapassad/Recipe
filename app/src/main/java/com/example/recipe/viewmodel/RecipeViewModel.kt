package academy.nouri.s4_recipeapp.viewmodel

import academy.nouri.s4_recipeapp.data.repository.MenuRepository
import academy.nouri.s4_recipeapp.data.repository.RecipeRepository
import androidx.lifecycle.*
import com.example.recipe.data.database.entity.RecipeEntity
import com.example.recipe.models.recipe.ResponseRecipes
import com.example.recipe.utils.Constants
import com.example.recipe.utils.NetworkRequest
import com.example.recipe.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/*suspend fun recentQueries(): HashMap<String, String> {
        viewModelScope.launch {
            menuRepository.readMenuData.collect {
                mealType = it.meal
                dietType = it.diet
            }
        }
        delay(500)
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.API_KEY] = Constants.MY_API_KEY
        queries[Constants.TYPE] = mealType
        queries[Constants.DIET] = dietType
        queries[Constants.NUMBER] = Constants.FULL_COUNT.toString()
        queries[Constants.ADD_RECIPE_INFORMATION] = Constants.TRUE
        return queries
    }*/

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {

    //---Popular---//
    //Queries
    fun popularQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.API_KEY] = Constants.MY_API_KEY
        queries[Constants.SORT] = Constants.POPULARITY
        queries[Constants.NUMBER] = Constants.LIMITED_COUNT.toString()
        queries[Constants.ADD_RECIPE_INFORMATION] = Constants.TRUE
        return queries
    }

    //Api
    val popularData = MutableLiveData<NetworkRequest<ResponseRecipes>>()
    fun callPopularApi(queries: Map<String, String>) = viewModelScope.launch {
        popularData.value = NetworkRequest.Loading()
        val response = repository.remote.getRecipes(queries)
        popularData.value = NetworkResponse(response).generalNetworkResponse()
        //Cache
        val cache = popularData.value?.data
        if (cache != null)
            offlinePopular(cache)
    }

    //Local
    private fun savePopular(entity: RecipeEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.saveRecipes(entity)
    }

    val readPopularFromDb = repository.local.loadRecipes().asLiveData()

    private fun offlinePopular(response: ResponseRecipes) {
        val entity = RecipeEntity(0, response)
        savePopular(entity)
    }

    //---Recent---//
    //Queries
    private var mealType = Constants.MAIN_COURSE
    private var dietType = Constants.GLUTEN_FREE

    suspend fun recentQueries(): HashMap<String, String> {
        viewModelScope.launch {
            menuRepository.readMenuData.collect {
                mealType = it.meal
                dietType = it.diet
            }
        }
        delay(500)
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.API_KEY] = Constants.MY_API_KEY
        queries[Constants.TYPE] = mealType
        queries[Constants.DIET] = dietType
        queries[Constants.NUMBER] = Constants.FULL_COUNT.toString()
        queries[Constants.ADD_RECIPE_INFORMATION] = Constants.TRUE
        return queries
    }

    //Api
    val recentData = MutableLiveData<NetworkRequest<ResponseRecipes>>()
    fun callRecentApi(queries: Map<String, String>) = viewModelScope.launch {
        recentData.value = NetworkRequest.Loading()
        val response = repository.remote.getRecipes(queries)
        recentData.value = recentNetworkResponse(response)
        //Cache
        val cache = recentData.value?.data
        if (cache != null)
            offlineRecent(cache)
    }

    //Local
    private fun saveRecent(entity: RecipeEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.saveRecipes(entity)
    }

    val readRecentFromDb = repository.local.loadRecipes().asLiveData()

    private fun offlineRecent(response: ResponseRecipes) {
        val entity = RecipeEntity(1, response)
        saveRecent(entity)
    }

    private fun recentNetworkResponse(response: Response<ResponseRecipes>): NetworkRequest<ResponseRecipes> {
        return when {
            response.message().contains("timeout") -> NetworkRequest.Error("Timeout")
            response.code() == 401 -> NetworkRequest.Error("You are not authorized")
            response.code() == 402 -> NetworkRequest.Error("Your free plan finished")
            response.code() == 422 -> NetworkRequest.Error("Api key not found!")
            response.code() == 500 -> NetworkRequest.Error("Try again")
            response.body()!!.results.isNullOrEmpty() -> NetworkRequest.Error("Not found any recipe!")
            response.isSuccessful -> NetworkRequest.Success(response.body()!!)
            else -> NetworkRequest.Error(response.message())
        }
    }
}
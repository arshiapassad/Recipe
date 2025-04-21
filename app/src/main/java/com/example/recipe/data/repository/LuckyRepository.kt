package academy.nouri.s4_recipeapp.data.repository

import com.example.recipe.data.source.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class LuckyRepository @Inject constructor(private val remote: RemoteDataSource) {
    suspend fun getRandomRecipe(queries: Map<String, String>) = remote.getRandomRecipe(queries)
}
package com.example.recipe.data.database

import academy.nouri.s4_recipeapp.data.database.entity.DetailEntity
import academy.nouri.s4_recipeapp.data.database.entity.FavoriteEntity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.recipe.data.database.entity.RecipeEntity
import com.example.recipe.utils.Constants
import kotlinx.coroutines.flow.Flow



@Dao
interface RecipeDao {
    //Recipe
    @Insert(onConflict = REPLACE)
    suspend fun saveRecipes(entity: RecipeEntity)

    @Query("SELECT * FROM ${Constants.RECIPE_TABLE_NAME} ORDER BY ID ASC")
    fun loadRecipes(): Flow<List<RecipeEntity>>

    //Detail
    @Insert(onConflict = REPLACE)
    suspend fun saveDetail(entity: DetailEntity)

    @Query("SELECT * FROM ${Constants.DETAIL_TABLE_NAME} WHERE id = :id")
    fun loadDetail(id: Int): Flow<DetailEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.DETAIL_TABLE_NAME} WHERE ID = :id)")
    fun existsDetail(id: Int): Flow<Boolean>

    //Favorite
    @Insert(onConflict = REPLACE)
    suspend fun saveFavorite(entity: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(entity: FavoriteEntity)

    @Query("SELECT * FROM ${Constants.FAVORITE_TABLE_NAME} ORDER BY ID ASC")
    fun loadFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.FAVORITE_TABLE_NAME} WHERE ID = :id)")
    fun existsFavorite(id: Int): Flow<Boolean>
}
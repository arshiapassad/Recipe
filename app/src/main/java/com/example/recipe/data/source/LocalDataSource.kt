package com.example.recipe.data.source

import academy.nouri.s4_recipeapp.data.database.entity.DetailEntity
import academy.nouri.s4_recipeapp.data.database.entity.FavoriteEntity
import com.example.recipe.data.database.RecipeDao
import com.example.recipe.data.database.entity.RecipeEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: RecipeDao){
    //Recipes
    suspend fun saveRecipes(entity: RecipeEntity) = dao.saveRecipes(entity)
    fun loadRecipes() = dao.loadRecipes()

    //Detail
    suspend fun saveDetail(entity: DetailEntity) = dao.saveDetail(entity)
    fun loadDetail(id: Int) = dao.loadDetail(id)
    fun existsDetail(id: Int) = dao.existsDetail(id)

    //Favorite
    suspend fun saveFavorite(entity: FavoriteEntity) = dao.saveFavorite(entity)
    suspend fun deleteFavorite(entity: FavoriteEntity) = dao.deleteFavorite(entity)
    fun loadFavorites() = dao.loadFavorites()
    fun existsFavorite(id: Int) = dao.existsFavorite(id)

}
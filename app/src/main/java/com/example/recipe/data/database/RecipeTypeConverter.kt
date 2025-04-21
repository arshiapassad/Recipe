package com.example.recipe.data.database

import academy.nouri.s4_recipeapp.models.detail.ResponseDetail
import androidx.room.TypeConverter
import com.example.recipe.models.recipe.ResponseRecipes
import com.google.gson.Gson

class RecipeTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun recipeToJson(recipe: ResponseRecipes): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipe(data: String): ResponseRecipes {
        return gson.fromJson(data, ResponseRecipes::class.java)
    }

    @TypeConverter
    fun detailToJson(recipe: ResponseDetail): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToDetail(data: String): ResponseDetail {
        return gson.fromJson(data, ResponseDetail::class.java)
    }
}
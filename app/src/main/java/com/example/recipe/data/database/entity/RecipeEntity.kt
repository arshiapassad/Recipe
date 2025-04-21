package com.example.recipe.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipe.models.recipe.ResponseRecipes
import com.example.recipe.utils.Constants

@Entity(tableName = Constants.RECIPE_TABLE_NAME)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    var response: ResponseRecipes
)
package com.example.recipe.data.database

import academy.nouri.s4_recipeapp.data.database.entity.DetailEntity
import academy.nouri.s4_recipeapp.data.database.entity.FavoriteEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipe.data.database.entity.RecipeEntity

@Database(entities = [RecipeEntity::class, DetailEntity::class, FavoriteEntity::class], version = 3, exportSchema = false)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun dao(): RecipeDao
}
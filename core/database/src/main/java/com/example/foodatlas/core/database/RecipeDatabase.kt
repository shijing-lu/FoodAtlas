package com.example.foodatlas.core.database

import android.content.Context
import androidx.room.*
import com.example.foodatlas.core.database.dao.RecipeDao
import com.example.foodatlas.core.database.model.IngredientEntity
import com.example.foodatlas.core.database.model.RecipeEntity
import com.example.foodatlas.core.database.model.StepEntity

@Database(
    entities = [RecipeEntity::class, IngredientEntity::class, StepEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "food_atlas_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

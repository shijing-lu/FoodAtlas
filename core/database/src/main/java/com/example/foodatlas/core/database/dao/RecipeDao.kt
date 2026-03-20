package com.example.foodatlas.core.database.dao

import androidx.room.*
import com.example.foodatlas.core.database.model.IngredientEntity
import com.example.foodatlas.core.database.model.RecipeEntity
import com.example.foodatlas.core.database.model.RecipeWithDetails
import com.example.foodatlas.core.database.model.StepEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Transaction
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeWithDetails>>

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeById(id: Long): Flow<RecipeWithDetails?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<IngredientEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(steps: List<StepEntity>)

    @Transaction
    suspend fun insertRecipeWithDetails(
        recipe: RecipeEntity,
        ingredients: List<IngredientEntity>,
        steps: List<StepEntity>
    ) {
        val recipeId = insertRecipe(recipe)
        insertIngredients(ingredients.map { it.copy(recipeId = recipeId) })
        insertSteps(steps.map { it.copy(recipeId = recipeId) })
    }

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}

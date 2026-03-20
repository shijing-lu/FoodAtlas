package com.example.foodatlas.core.data

import com.example.foodatlas.core.database.dao.RecipeDao
import com.example.foodatlas.core.database.mapper.asEntity
import com.example.foodatlas.core.database.mapper.asExternalModel
import com.example.foodatlas.core.database.model.IngredientEntity
import com.example.foodatlas.core.database.model.StepEntity
import com.example.foodatlas.core.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepository(private val recipeDao: RecipeDao) {

    fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes().map { list ->
            list.map { it.asExternalModel() }
        }
    }

    fun getRecipeById(id: String): Flow<Recipe?> {
        val recipeId = id.toLongOrNull() ?: return kotlinx.coroutines.flow.flowOf(null)
        return recipeDao.getRecipeById(recipeId).map { it?.asExternalModel() }
    }

    suspend fun saveRecipe(recipe: Recipe) {
        val entity = recipe.asEntity()
        val ingredients = recipe.ingredients.map { 
            IngredientEntity(name = it.name, amount = it.amount, recipeId = 0L) 
        }
        val steps = recipe.steps.mapIndexed { index, description -> 
            StepEntity(stepDescription = description, stepOrder = index, recipeId = 0L) 
        }
        
        recipeDao.insertRecipeWithDetails(entity, ingredients, steps)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe.asEntity())
    }
}

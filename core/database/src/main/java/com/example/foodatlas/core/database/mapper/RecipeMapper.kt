package com.example.foodatlas.core.database.mapper

import com.example.foodatlas.core.database.model.IngredientEntity
import com.example.foodatlas.core.database.model.RecipeEntity
import com.example.foodatlas.core.database.model.RecipeWithDetails
import com.example.foodatlas.core.database.model.StepEntity
import com.example.foodatlas.core.model.Ingredient
import com.example.foodatlas.core.model.Recipe

fun RecipeWithDetails.asExternalModel(): Recipe = Recipe(
    id = recipe.id.toString(),
    title = recipe.title,
    imageUrl = recipe.imageUrl,
    lastCookedInfo = recipe.lastCookedInfo,
    category = recipe.category,
    cookTime = recipe.cookTime,
    difficulty = recipe.difficulty,
    cookCount = recipe.cookCount,
    daysSinceLastCook = recipe.daysSinceLastCook,
    ingredients = ingredients.map { Ingredient(it.name, it.amount) },
    steps = steps.sortedBy { it.stepOrder }.map { it.stepDescription }
)

fun Recipe.asEntity(): RecipeEntity = RecipeEntity(
    id = id.toLongOrNull() ?: 0L,
    title = title,
    imageUrl = imageUrl,
    lastCookedInfo = lastCookedInfo,
    category = category,
    cookTime = cookTime,
    difficulty = difficulty,
    cookCount = cookCount,
    daysSinceLastCook = daysSinceLastCook
)

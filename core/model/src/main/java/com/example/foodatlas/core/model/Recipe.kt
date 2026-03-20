package com.example.foodatlas.core.model

data class Recipe(
    val id: String = "",
    val title: String,
    val imageUrl: String,
    val lastCookedInfo: String,
    val category: String,
    val cookTime: String = "",
    val difficulty: String = "",
    val cookCount: Int = 0,
    val daysSinceLastCook: Int? = null,
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<String> = emptyList()
)

data class Ingredient(
    val name: String,
    val amount: String
)

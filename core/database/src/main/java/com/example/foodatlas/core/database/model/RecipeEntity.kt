package com.example.foodatlas.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val imageUrl: String,
    val lastCookedInfo: String,
    val category: String,
    val cookTime: String,
    val difficulty: String,
    val cookCount: Int,
    val daysSinceLastCook: Int?,
    // steps and ingredients will be in separate tables or converted
    // For now let's use separate tables to be "pro"
)

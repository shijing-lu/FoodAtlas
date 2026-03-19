package com.example.foodatlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.foodatlas.core.designsystem.theme.FoodAtlasTheme
import com.example.foodatlas.feature.home.HomeScreen
import com.example.foodatlas.feature.home.Recipe
import com.example.foodatlas.feature.home.RecipeDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodAtlasTheme {
                FoodAtlasNavigation()
            }
        }
    }
}

@Composable
fun FoodAtlasNavigation() {
    // Simple in-memory navigation state until Navigation Compose is wired up
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    if (selectedRecipe == null) {
        HomeScreen(
            onRecipeClick = { recipe -> selectedRecipe = recipe }
        )
    } else {
        RecipeDetailScreen(
            recipe = selectedRecipe!!,
            onBack = { selectedRecipe = null }
        )
    }
}
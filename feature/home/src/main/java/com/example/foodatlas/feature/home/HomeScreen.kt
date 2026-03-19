package com.example.foodatlas.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.foodatlas.core.designsystem.theme.Primary
import com.example.foodatlas.core.designsystem.theme.OnPrimary

@Composable
fun HomeScreen(
    onRecipeClick: (Recipe) -> Unit = {},
    onAddRecipeClick: () -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf("全部") }

    val displayedRecipes = if (selectedCategory == "全部") {
        fakeRecipes
    } else {
        fakeRecipes.filter { it.category == selectedCategory }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = { FoodAtlasBottomBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddRecipeClick,
                containerColor = Color(0xFFCC5500), // Burnt orange from design spec
                contentColor = OnPrimary,
                shape = CircleShape
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "添加食谱",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->

        // Main scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding()),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Top Bar
            item {
                HomeTopBar()
            }

            // Search Bar
            item {
                Spacer(modifier = Modifier.height(4.dp))
                HomeSearchBar()
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Category Chips
            item {
                CategoryFilterRow(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Daily Inspiration Card
            item {
                DailyInspirationCard()
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Grid section
            item {
                RecipesGrid(
                    recipes = displayedRecipes,
                    onRecipeClick = onRecipeClick
                )
            }
        }
    }
}

@Composable
fun RecipesGrid(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val rows = recipes.chunked(2)
        rows.forEach { rowItems ->
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowItems.forEach { recipe ->
                    RecipeGridCard(
                        recipe = recipe,
                        modifier = Modifier.weight(1f),
                        onClick = { onRecipeClick(recipe) }
                    )
                }
                if (rowItems.size == 1) {
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

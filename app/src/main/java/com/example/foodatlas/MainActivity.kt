package com.example.foodatlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodatlas.core.designsystem.theme.FoodAtlasTheme
import com.example.foodatlas.feature.home.HomeScreen
import com.example.foodatlas.feature.home.RecipeDetailScreen
import com.example.foodatlas.feature.home.fakeRecipes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodAtlasTheme {
                FoodAtlasNavGraph()
            }
        }
    }
}

// Routes
object Route {
    const val HOME = "home"
    const val RECIPE_DETAIL = "recipe_detail/{recipeId}"
    
    fun createRecipeDetailRoute(recipeId: String): String = "recipe_detail/$recipeId"
}

@Composable
fun FoodAtlasNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HOME
    ) {
        // --- Home Screen ---
        composable(
            route = Route.HOME,
            enterTransition = { fadeIn(tween(300)) },
            exitTransition = { fadeOut(tween(300)) }
        ) {
            HomeScreen(
                onRecipeClick = { recipe ->
                    navController.navigate(Route.createRecipeDetailRoute(recipe.id))
                }
            )
        }

        // --- Recipe Detail Screen ---
        composable(
            route = Route.RECIPE_DETAIL,
            arguments = listOf(
                navArgument("recipeId") {
                    type = NavType.StringType
                }
            ),
            // Smooth entering animation matching Android 14+ feel
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                ) + fadeIn(tween(300))
            },
            // Smooth natural left-swipe pop animation
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                ) + fadeOut(tween(300))
            }
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")
            // Fetch recipe from our fake source (later this will be DB query in ViewModel)
            val recipe = fakeRecipes.find { it.id == recipeId }

            if (recipe != null) {
                RecipeDetailScreen(
                    recipe = recipe,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
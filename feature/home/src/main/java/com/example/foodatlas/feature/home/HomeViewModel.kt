package com.example.foodatlas.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodatlas.core.data.RecipeRepository
import com.example.foodatlas.core.model.Recipe
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("全部")
    
    val uiState: StateFlow<HomeUiState> = combine(
        repository.getAllRecipes(),
        _selectedCategory
    ) { recipes, selectedCategory ->
        HomeUiState(
            recipes = if (selectedCategory == "全部") recipes else recipes.filter { it.category == selectedCategory },
            selectedCategory = selectedCategory,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(isLoading = true)
    )

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }
}

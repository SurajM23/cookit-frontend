package com.example.cookit.ui.screens.addPost

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cookit.utils.NavigationConstants
import com.example.cookit.viewModel.HomeViewModel

@Composable
fun AddRecipePostScreen(homeViewModel: HomeViewModel, navController: NavHostController) {
    var step by rememberSaveable { mutableStateOf(AddRecipeStep.TITLE) }
    var draft by rememberSaveable(stateSaver = RecipeDraftSaver) { mutableStateOf(RecipeDraft()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (step) {
            AddRecipeStep.TITLE -> TitleStep(draft, onNext = { step = AddRecipeStep.INGREDIENTS })
            AddRecipeStep.INGREDIENTS -> IngredientsStep(
                draft,
                onNext = { step = AddRecipeStep.STEPS },
                onBack = { step = AddRecipeStep.TITLE })

            AddRecipeStep.STEPS -> StepsStep(
                draft,
                onNext = { step = AddRecipeStep.COOK_TIME },
                onBack = { step = AddRecipeStep.INGREDIENTS })

            AddRecipeStep.COOK_TIME -> CookTimeStep(
                draft,
                onNext = { step = AddRecipeStep.IMAGE },
                onBack = { step = AddRecipeStep.STEPS })

            AddRecipeStep.IMAGE -> ImageStep(
                draft,
                onNext = { step = AddRecipeStep.REVIEW },
                onBack = { step = AddRecipeStep.COOK_TIME })

            AddRecipeStep.REVIEW -> ReviewStep(
                homeViewModel,
                draft,
                onBack = { step = AddRecipeStep.IMAGE },
                onSuccessNavigate = {
                    navController.navigate(NavigationConstants.HOME_SCREEN) {
                        popUpTo(NavigationConstants.ADD_RECIPE_SCREEN) { inclusive = true }
                    }
                })
        }
    }
}
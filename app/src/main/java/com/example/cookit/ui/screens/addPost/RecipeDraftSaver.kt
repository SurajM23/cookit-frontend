package com.example.cookit.ui.screens.addPost

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver

val RecipeDraftSaver: Saver<RecipeDraft, Any> = listSaver(
    save = { listOf(it.title, it.ingredients, it.steps, it.cookTime, it.cookTimeUnit, it.imageUri) },
    restore = {
        RecipeDraft(
            title = it[0] as String,
            ingredients = it[1] as List<String>,
            steps = it[2] as List<String>,
            cookTime = it[3] as String,
            cookTimeUnit = it[4] as String,
            imageUri = it[5] as String?
        )
    }
)
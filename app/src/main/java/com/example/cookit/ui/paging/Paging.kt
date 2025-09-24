package com.example.cookit.ui.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cookit.data.network.HomeRepository
import com.example.cookit.model.Recipe
import kotlinx.coroutines.flow.Flow

fun HomeRepository.getRecipeFeedPager(): Flow<PagingData<Recipe>> {
    return Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { RecipeFeedPagingSource(this) }
    ).flow
}


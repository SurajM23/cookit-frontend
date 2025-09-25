package com.example.cookit.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cookit.data.network.ApiService
import com.example.cookit.data.network.HomeRepository
import com.example.cookit.model.Recipe

class RecipeFeedPagingSource(private val repository: HomeRepository) : PagingSource<Int, Recipe>() {
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                anchor
            )?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
            val page = params.key ?: 1
            val response = repository.getAllRecipe(page)

            if (response.isSuccessful) {
                val body = response.body()
                val items = body?.recipes ?: emptyList() // Adjust based on your response model

                LoadResult.Page(
                    data = items,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (items.isEmpty()) null else page + 1
                )
            } else if (response.code() == 401) {
                LoadResult.Error(Exception("Unauthorized"))
            } else {
                LoadResult.Error(Exception(response.message() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
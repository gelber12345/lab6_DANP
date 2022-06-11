package com.example.lab6.blood

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class BloodSource (private val repo: BloodRepository) : PagingSource<Int, Blood>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Blood> {
        return try {
            val page = params.key ?: 1
            val nationResponse = repo.getNation(page)
            LoadResult.Page(
                data = nationResponse,
                prevKey = if (page == 1) null else page - 1,
                nextKey =  if (nationResponse.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Blood>): Int? {
        return state.anchorPosition
    }
}
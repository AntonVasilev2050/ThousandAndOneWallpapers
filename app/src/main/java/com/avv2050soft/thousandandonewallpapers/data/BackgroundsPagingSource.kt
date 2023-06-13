package com.avv2050soft.thousandandonewallpapers.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.avv2050soft.thousandandonewallpapers.domain.models.Hit
import com.avv2050soft.thousandandonewallpapers.domain.repository.PixabayRepository
import javax.inject.Inject

class BackgroundsPagingSource @Inject constructor(
    private val repository: PixabayRepository
) : PagingSource<Int, Hit>() {
    override fun getRefreshKey(state: PagingState<Int, Hit>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getBackgrounds(q = query, page = page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.hits,
                    prevKey = null,
                    nextKey = if (it.hits.isEmpty()) null else page + 1
                )
            }, onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    companion object {
        private const val FIRST_PAGE = 1
        var query = ""
    }
}
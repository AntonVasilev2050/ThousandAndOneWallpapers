package com.avv2050soft.thousandandonewallpapers.presentation.ui.wallpapers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.avv2050soft.thousandandonewallpapers.data.BackgroundsPagingSource
import com.avv2050soft.thousandandonewallpapers.domain.models.apiresponse.Hit
import com.avv2050soft.thousandandonewallpapers.domain.repository.PixabayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WallpapersViewModel  @Inject constructor(
    private val repository: PixabayRepository
): ViewModel() {

    val pageWallpapers: Flow<PagingData<Hit>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { BackgroundsPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)
}
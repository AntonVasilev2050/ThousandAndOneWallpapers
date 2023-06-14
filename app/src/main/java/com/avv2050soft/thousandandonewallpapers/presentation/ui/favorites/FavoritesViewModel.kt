package com.avv2050soft.thousandandonewallpapers.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import com.avv2050soft.thousandandonewallpapers.data.objects.Favorites
import com.avv2050soft.thousandandonewallpapers.domain.models.apiresponse.Hit
import com.avv2050soft.thousandandonewallpapers.domain.repository.PixabayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: PixabayRepository
): ViewModel() {

    fun loadFavorites(): List<Hit>{
        return Favorites.list
    }

}
package com.avv2050soft.thousandandonewallpapers.presentation.ui.categories

import androidx.lifecycle.ViewModel
import com.avv2050soft.thousandandonewallpapers.data.objects.Categories
import com.avv2050soft.thousandandonewallpapers.domain.models.categories.Category
import com.avv2050soft.thousandandonewallpapers.domain.repository.PixabayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val repository: PixabayRepository
) : ViewModel() {


    fun loadCategories(): List<Category>{
        return Categories.list
    }
}
package com.avv2050soft.thousandandonewallpapers.presentation.ui.wallpapers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avv2050soft.thousandandonewallpapers.domain.repository.PixabayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WallpapersViewModel  @Inject constructor(
    private val repository: PixabayRepository
): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}
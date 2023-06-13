package com.avv2050soft.thousandandonewallpapers.data.repository

import com.avv2050soft.thousandandonewallpapers.data.api.PixabayApi
import com.avv2050soft.thousandandonewallpapers.domain.models.ApiResponse
import com.avv2050soft.thousandandonewallpapers.domain.repository.PixabayRepository
import javax.inject.Inject

class PixabayRepositoryImpl @Inject constructor() : PixabayRepository {
    override suspend fun getBackgrounds(q: String, page: Int): ApiResponse {
        return PixabayApi.create().getBackgrounds(q = q, page = page)
    }
}
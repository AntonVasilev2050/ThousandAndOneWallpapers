package com.avv2050soft.thousandandonewallpapers.domain.repository

import com.avv2050soft.thousandandonewallpapers.domain.models.apiresponse.ApiResponse

interface PixabayRepository {
    suspend fun getWallpapers(category: String, query: String, page: Int): ApiResponse
}
package com.avv2050soft.thousandandonewallpapers.domain.repository

import com.avv2050soft.thousandandonewallpapers.domain.models.ApiResponse

interface PixabayRepository {
    suspend fun getBackgrounds(q : String, page: Int) : ApiResponse
}
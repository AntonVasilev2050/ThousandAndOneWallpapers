package com.avv2050soft.thousandandonewallpapers.domain.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("hits")
    val hits: List<Hit>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)
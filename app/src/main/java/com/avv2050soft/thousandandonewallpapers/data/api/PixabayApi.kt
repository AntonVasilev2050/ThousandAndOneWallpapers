package com.avv2050soft.thousandandonewallpapers.data.api

import com.avv2050soft.thousandandonewallpapers.domain.models.ApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "37247757-dbe7f2506a135d3dce0dadfa6"

interface PixabayApi {

    @GET("/")
    suspend fun getBackgrounds(
        @Query("key") apiKey : String = API_KEY,
        @Query("orientation") orientation: String = "vertical",
        @Query("category") category: String = "backgrounds",
        @Query("") safesearch: Boolean = true,
        @Query("q") q: String = "",
        @Query("page") page : Int,
    ) : ApiResponse

    companion object {
        private const val BASE_URL = "https://pixabay.com/api"

        fun create(): PixabayApi {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PixabayApi::class.java)
        }
    }
}
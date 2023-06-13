package com.avv2050soft.thousandandonewallpapers.di

import com.avv2050soft.thousandandonewallpapers.data.repository.PixabayRepositoryImpl
import com.avv2050soft.thousandandonewallpapers.domain.repository.PixabayRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providePixabayRepository(): PixabayRepository {
        return PixabayRepositoryImpl()
    }
}
package com.mubin.starwars.base.di

import com.mubin.starwars.data.api.ApiService
import com.mubin.starwars.data.repo.AppRepository
import com.mubin.starwars.domain.repo.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideRepository(apiService: ApiService): AppRepository = AppRepositoryImpl(apiService)

}
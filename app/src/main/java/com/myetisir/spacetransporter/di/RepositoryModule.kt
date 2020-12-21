package com.myetisir.spacetransporter.di

import com.myetisir.spacetransporter.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Singleton
    @Binds
    abstract fun provideTransporterRepository(transporterRepositoryImpl: TransporterRepositoryImpl): TransporterRepository

    @Singleton
    @Binds
    abstract fun provideStationRepository(stationRepositoryImpl: StationRepositoryImpl): StationRepository
}
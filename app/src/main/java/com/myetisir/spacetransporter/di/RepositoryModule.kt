package com.myetisir.spacetransporter.di

import com.myetisir.spacetransporter.data.repository.MainRepository
import com.myetisir.spacetransporter.data.repository.MainRepositoryImpl
import com.myetisir.spacetransporter.data.repository.TransporterRepository
import com.myetisir.spacetransporter.data.repository.TransporterRepositoryImpl
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
}
package com.myetisir.spacetransporter.di

import com.myetisir.spacetransporter.data.source.local.SpaceTransporterLocalDataSource
import com.myetisir.spacetransporter.data.source.local.SpaceTransporterLocalDataSourceImpl
import com.myetisir.spacetransporter.data.source.remote.SpaceTransporterApiDataSource
import com.myetisir.spacetransporter.data.source.remote.SpaceTransporterApiDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindRemoteDataSource(spaceTransporterApiDataSourceImpl: SpaceTransporterApiDataSourceImpl): SpaceTransporterApiDataSource

    @Singleton
    @Binds
    abstract fun bindLocalDataSource(spaceTransporterLocalDataSourceImpl: SpaceTransporterLocalDataSourceImpl): SpaceTransporterLocalDataSource
}
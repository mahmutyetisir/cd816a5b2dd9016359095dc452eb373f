package com.myetisir.spacetransporter.di

import com.myetisir.spacetransporter.data.manager.TransportManager
import com.myetisir.spacetransporter.data.manager.TransportManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class ManagerModule {

    @Singleton
    @Binds
    abstract fun provideTransportManager(transportManagerImple: TransportManagerImpl): TransportManager
}
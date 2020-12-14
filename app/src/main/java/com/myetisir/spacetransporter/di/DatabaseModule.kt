package com.myetisir.spacetransporter.di

import android.content.Context
import androidx.room.Room
import com.myetisir.spacetransporter.data.source.local.room.AppDatabase
import com.myetisir.spacetransporter.data.source.local.room.StationDao
import com.myetisir.spacetransporter.data.source.local.room.TransporterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideStationDao(appDatabase: AppDatabase): StationDao {
        return appDatabase.stationDao()
    }

    @Provides
    fun provideTransporterDao(appDatabase: AppDatabase): TransporterDao {
        return appDatabase.transporterDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "SpaceTransporter"
        ).build()
    }
}
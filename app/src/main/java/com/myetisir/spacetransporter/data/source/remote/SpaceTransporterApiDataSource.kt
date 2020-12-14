package com.myetisir.spacetransporter.data.source.remote

import android.content.Context
import com.myetisir.spacetransporter.data.api.SpaceTransporterApi
import com.myetisir.spacetransporter.data.model.Station
import com.myetisir.spacetransporter.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface SpaceTransporterApiDataSource {

    suspend fun getStations(): Resource<List<Station>>
}

class SpaceTransporterApiDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val spaceTransporterApi: SpaceTransporterApi
) : BaseDataSource(context), SpaceTransporterApiDataSource {

    override suspend fun getStations(): Resource<List<Station>> {
        return getResult {
            spaceTransporterApi.getStations()
        }
    }

}
package com.myetisir.spacetransporter.data.api

import com.myetisir.spacetransporter.data.model.Station
import retrofit2.Response
import retrofit2.http.GET

interface SpaceTransporterApi {

    @GET("e7211664-cbb6-4357-9c9d-f12bf8bab2e2")
    suspend fun getStations(): Response<List<Station>>
}
package com.geopark.feature_locations.data.remote

import com.geopark.feature_locations.domain.model.Location
import retrofit2.http.GET

interface GeoparkApi {

    @GET("locations")
    suspend fun getLocations() : List<Location>


}
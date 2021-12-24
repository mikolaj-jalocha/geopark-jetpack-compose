package com.geopark.feature_locations_events.data.remote

import com.geopark.feature_locations_events.domain.model.Event
import com.geopark.feature_locations_events.domain.model.Location
import retrofit2.http.GET

interface GeoparkApi {

    @GET("locations")
    suspend fun getLocations() : List<Location>

    @GET("events")
    suspend fun getEvents() : List<Event>


}
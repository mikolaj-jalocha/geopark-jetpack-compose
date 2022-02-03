package com.geopark.feature_locations_events.data.remote

import com.geopark.feature_locations_events.data.local.entity.*
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.remote.dto.EventDto
import com.geopark.feature_locations_events.data.remote.dto.LocationDto
import retrofit2.http.GET

interface GeoparkApi {

    @GET("Locations")
    suspend fun getLocations() : List<LocationDto>

    @GET("Events")
    suspend fun getEvents() : List<EventDto>

    @GET("Organizers")
    suspend fun getOrganizers()  : List<OrganizerEntity>

    @GET("Categories")
    suspend fun getCategories() : List<CategoryEntity>

    @GET("Labels")
    suspend fun getLabels() : List<LabelEntity>

    @GET("Tags")
    suspend fun getTags() : List<TagEntity>

    @GET("Photos")
    suspend fun getPhotos() : List<PhotoEntity>


}
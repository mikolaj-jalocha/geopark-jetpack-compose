package com.geopark.feature_locations_events.domain.repository

interface CachingRepository {

    suspend fun cacheData()

}
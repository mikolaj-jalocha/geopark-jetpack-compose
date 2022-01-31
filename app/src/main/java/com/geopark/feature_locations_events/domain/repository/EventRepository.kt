package com.geopark.feature_locations_events.domain.repository

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents() : Flow<Resource<List<Event>>>
}
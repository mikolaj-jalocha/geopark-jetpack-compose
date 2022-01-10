package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.feature_locations_events.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventsLocations(
    private val repository: EventRepository
) {
    operator fun invoke(): Flow<List<String>> =
        repository.getEventsLocations()

}

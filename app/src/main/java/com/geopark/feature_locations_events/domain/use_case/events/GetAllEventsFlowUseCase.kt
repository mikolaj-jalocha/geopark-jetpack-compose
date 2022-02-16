package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.feature_locations_events.domain.repository.EventRepository

class GetAllEventsFlowUseCase(
    private val repository: EventRepository
) {
    operator fun invoke() = repository.getEventsFlow()
}


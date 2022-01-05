package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.repository.EventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetAllEvents(
    private val repository: EventRepository
) {
    operator fun invoke() = flow {
        repository.getEvents().collect { result ->
            when (result) {
                is Resource.Success -> {
                    emit(Resource.Success(data = result))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading(data = result))
                }
                is Resource.Error -> {
                    emit(Resource.Error(data = result, message = result.message))
                }
            }
        }
    }
}
package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.repository.EventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

// TODO: Add future that could distinct by selected field
class GetAllEventsDistinct(
    private val repository: EventRepository
) {
    operator fun invoke(
    ) = flow {

        repository.getEvents().collect { result ->

            val filteredResult = result.data.distinctBy { it.title }

            when (result) {
                is Resource.Success -> {
                    emit(Resource.Success(data = filteredResult))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading(data = filteredResult))
                }
                is Resource.Error -> {
                    emit(Resource.Error(data = filteredResult, message = result.message))
                }
            }
        }
    }
}

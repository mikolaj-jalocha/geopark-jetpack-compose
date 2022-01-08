package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.repository.EventRepository
import com.geopark.feature_locations_events.domain.util.EventCategory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

// TODO: Add future that could distinct by selected field
class GetAllEventsDistinct(
    private val repository: EventRepository
) {
    operator fun invoke(
        category: EventCategory,
        location : String = ".*"
    ) = flow {

        repository.getEvents().collect { result ->


            val filteredResult =
                if (category == EventCategory.ALL)
                    result.data.distinctBy { it.title }.filter { it.promoterName.matches(Regex(location)) }
                else
                    result.data.distinctBy { it.title }.filter { it.category.contains(category) && it.promoterName.matches(Regex(location)) }

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

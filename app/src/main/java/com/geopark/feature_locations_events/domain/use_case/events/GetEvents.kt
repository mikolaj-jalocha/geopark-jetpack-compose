package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.repository.EventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class GetEvents(
    private val repository : EventRepository
) {
    operator fun invoke() = flow{
      repository.getEvents().collect {  result ->
         emit(result)
      }
    }
}
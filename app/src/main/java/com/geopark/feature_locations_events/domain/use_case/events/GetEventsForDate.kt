package com.geopark.feature_locations_events.domain.use_case.events

import android.util.Log
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.model.Event
import com.geopark.feature_locations_events.domain.repository.EventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetEventsForDate(
    private val repository: EventRepository
) {
    operator fun invoke(
        date: LocalDate
    ) = flow {
        repository.getEvents().collect { result ->

            // TODO: Export to another file as constant
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

            val res: List<Event> =
                result.data.filter {
                    LocalDate.parse(it.date, formatter).isEqual(date)
                }

            when (result) {
                is Resource.Success -> {
                    emit(Resource.Success(data = res))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading(data = res))
                }
                is Resource.Error -> {
                    emit(Resource.Error(data = res, message = result.message))
                }
            }
        }
    }
}
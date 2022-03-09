package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.util.EventCategory
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetEventsForDateUseCase(val getEventsForCategory: GetEventsForCategoryUseCase) {

    operator fun invoke(eventCategory: EventCategory, date: LocalDate) =
        getEventsForCategory(eventCategory).map { result ->
            val filteredResult = result.data.filter { event ->
                event.event.eventDate.map { it.startDate }
                    .contains(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
            }
            if (result is Resource.Success)
                return@map Resource.Success(filteredResult)
            else
                return@map Resource.Loading(filteredResult)
        }

}
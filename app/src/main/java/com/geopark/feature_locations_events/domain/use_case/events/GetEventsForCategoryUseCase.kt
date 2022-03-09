package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.util.EventCategory
import kotlinx.coroutines.flow.map

class GetEventsForCategoryUseCase(
    private val getAllEvents: GetAllEventsFlowUseCase
) {
    operator fun invoke(eventCategory: EventCategory) = getFilteredEvents(eventCategory)


    private fun getFilteredEvents(eventCategory: EventCategory) =
        if (eventCategory == EventCategory.ALL) {
            getAllEvents()
        } else {
            getAllEvents().map { result ->
                val filteredResult = result.data.filter { event ->
                    event.categories.map { it.categoryId }.contains(eventCategory.categoryId)
                }
                if (result is Resource.Success)
                    return@map Resource.Success(filteredResult)
                else
                    return@map Resource.Loading(filteredResult)
            }
        }

}
package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.repository.EventRepository
import com.geopark.feature_locations_events.domain.util.EventCategory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


class GetAllEventsDistinct(
    private val repository: EventRepository
) {
    operator fun invoke(
        category: EventCategory,
    ) {


        }
    }


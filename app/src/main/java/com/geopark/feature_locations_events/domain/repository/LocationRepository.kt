package com.geopark.feature_locations_events.domain.repository

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

        fun getLocations() : Flow<Resource<List<Location>>>

        suspend fun getLocationByName(name : String) : Location?

}
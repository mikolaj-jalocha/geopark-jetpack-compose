package com.geopark.feature_locations.domain.repository

import com.geopark.core.util.Resource
import com.geopark.feature_locations.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

        fun getLocations() : Flow<Resource<List<Location>>>

        suspend fun updateLocation(location: Location)

        suspend fun getLocationByName(name : String) : Location?

        suspend fun insertLocation(location : Location)

        suspend fun deleteLocation(location: Location)

}
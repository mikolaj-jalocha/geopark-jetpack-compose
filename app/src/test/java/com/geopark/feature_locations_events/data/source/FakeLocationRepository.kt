package com.geopark.feature_locations_events.data.source

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.entity.CategoryEntity
import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocationRepository : LocationRepository {

    private var isLoading = false


    private val locations = mutableListOf<Location>()

    fun getLocationsSize() = locations.size


    fun insertLocation(location: Location){
        locations.add(location)
    }


    fun returnLoadingState() {
        isLoading = true
    }

    fun returnSuccessState() {
        isLoading = false
    }

    override fun getLocationsFlow() = flow {

        if (isLoading) {
            emit(Resource.Loading(data = locations.toList()))
        }
        else
              emit(Resource.Success(data = locations.toList()))

    }

    override suspend fun getLocationById(id: String): Location? {
            return  locations.find { it.location.locationId == id }
    }
}
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

    override fun getLocations(): Flow<Resource<List<Location>>> {
        TODO("Not yet implemented")
    }


    private var isLoading = false

     val data = listOf<Location>(
        Location(
            location = LocationEntity(locationId = "location_1",name = "location_1_name"),
            categories = listOf(
                CategoryEntity(categoryId = "gastronomia",""),
                CategoryEntity(categoryId = "noclegi","")
            )
        ),
        Location(
            location = LocationEntity(locationId = "location_2",name = "location_2_name"),
            categories = listOf(
                CategoryEntity(categoryId = "sport","")
            )
        ),
        Location(
            location = LocationEntity(locationId = "location_3",name = "location_3_name"),
            categories = listOf(
                CategoryEntity(categoryId = "gastronomia","")
            )
        ),

    )

    fun returnLoadingState() {
        isLoading = true
    }

    fun returnSuccessState() {
        isLoading = false
    }

    override fun getLocationsFlow() = flow {

        if (isLoading) {
            emit(Resource.Loading(data = data))
        }
        else
              emit(Resource.Success(data = data))

    }

    override suspend fun getLocationById(id: String): Location? {
            return  data.find { it.location.locationId == id }
    }
}
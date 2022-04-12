package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.source.FakeLocationRepository
import com.geopark.feature_locations_events.domain.repository.LocationRepository
import com.geopark.feature_locations_events.domain.util.EventCategory
import com.geopark.feature_locations_events.domain.util.LocationType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class GetLocationsByTypeUseCaseTest {


    private val repository: FakeLocationRepository = FakeLocationRepository()
    private val getLocationByType = GetLocationsByTypeUseCase(GetAllLocationsUseCase(repository))
    private val locationTypes = listOf(
        LocationType.Active,
        LocationType.Explore,
        LocationType.Hotel,
        LocationType.Restaurant
    )


    @Test
    fun `returned locations match given type`() = runBlocking {

        locationTypes.forEach { expectedType ->

            val filteredLocations = getLocationByType(expectedType).first().data

            filteredLocations.forEach { location ->
                val actualType = location.categories.map { it.categoryId }
                assert(actualType.contains(expectedType.toString()))
            }

        }
    }

    @Test
    fun `all locations are returned if passed type is ALL`() = runBlocking {

        val expectedLocationsSize = repository.data.size
        val actualLocationsSize = getLocationByType(LocationType.All).first().data.size

        assert(expectedLocationsSize == actualLocationsSize)

    }


    @Test
    fun `returned Result type is 'Loading' `() = runBlocking {

        repository.returnLoadingState()

        val actualReturnType = getLocationByType(LocationType.All).first()

        assert(actualReturnType is Resource.Loading)
    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val successResource = getLocationByType(LocationType.All).first()

        assert(successResource is Resource.Success)

    }


}